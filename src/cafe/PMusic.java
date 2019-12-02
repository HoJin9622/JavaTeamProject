package cafe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PMusic {

    private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;
    private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
    // the player actually doing all the work
    private Player player;
    // locking object used to communicate with player thread
    private final Object playerLock = new Object();
    CafeSystem CS;
    String name;
    int num;
    // status variable what player thread is doing/supposed to do
    private int playerStatus = NOTSTARTED;
    public PMusic(CafeSystem c) throws JavaLayerException {
        try {  
        	num = 0;
        	CS = c;
        	if(CS.OS.reserveList.isEmpty()) {
        		if(num > 2) {
        			num = 0;
        		}
				name =Static.trackList.get(num).getListMusic();
				Static.playingMusic=name;
				file=new File(Main.class.getResource("../music/" + name).toURI());
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
				player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
				num++;
			}else {
				name = CS.OS.reserveList.remove(0);
				Static.playingMusic=name;
				file=new File(Main.class.getResource("../music/" + name).toURI());
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
				player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
				CS.MainPanel.SongList.remove(1);
			}
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
    }
    /**
     * Starts playback (resumes if paused)
     */
    public void play() throws JavaLayerException {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    t.start();
					CS.MainPanel.songLabel.setText(name);
					CS.repaint();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED.
     */
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING.
     */
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }

    private void playInternal() {
    	
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
    	try {
    			if(CS.OS.reserveList.isEmpty()) {
            		if(num >= Static.trackList.size()) {
            			num = 0;
            		}
					name =Static.trackList.get(num).getListMusic();
					Static.playingMusic=name;
					file=new File(Main.class.getResource("../music/" + name).toURI());
					fis=new FileInputStream(file);
					bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
					player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
					num++;
    			}else {
    				name = CS.OS.reserveList.remove(0);
    				Static.playingMusic=name;
					file=new File(Main.class.getResource("../music/" + name).toURI());
					fis=new FileInputStream(file);
					bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
					player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
					CS.MainPanel.SongList.remove(1);
    			}
				playerStatus = NOTSTARTED;
				play();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }

}
