package cafe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import javazoom.jl.player.Player;
import cafe.Static;

public class Music extends Thread{
	private Player player;
	private boolean isLoop; //현재 곡이 무한 반복인지 한번만 재생되고 꺼지는지 체크
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	ArrayList<Track> trackList=new ArrayList<Track>();
	
	
	public Music(String name,ArrayList<Track> trackList,boolean isLoop) {
		try {
			this.isLoop=isLoop;
			file=new File(Main.class.getResource("../music/"+name).toURI());
			fis=new FileInputStream(file);
			bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
			player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
			this.trackList=trackList;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public int getTime() { //현재 실행되는 음악이 현재 어떤 위치?에서 실행되는지 알려준다
		if(player==null)
			return 0;
		return player.getPosition();
	}
	public void close() { //언제든지 중지할 때, 뒤로가기나 중지버튼을 클릭하면 노래 재생 중지 
		isLoop=false;
		player.close();
		this.interrupt(); //해당 스레드를 중지상태로 만듬
	}
	@Override
	public void run() { //스레드를 상속받으면 무조건 사용해야되는 함수
		try {
			do {
				player.play();
				int n=(int)(Math.random()*(trackList.size())+0);
				String name=trackList.get(n).getListMusic();
				Static.playingMusic=name;
				//Static.songLabel.setText(name);
				file=new File(Main.class.getResource("../music/"+name).toURI());
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis); //해당 파일을 버퍼에 담아서 읽어올 수 있도록
				player=new Player(bis); //플레이어는 해당 파일을 담을 수 있도록
			} while (isLoop); 
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}