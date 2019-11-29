package cafe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import javazoom.jl.player.Player;
import cafe.Static;

public class Music extends Thread{
	private Player player;
	private boolean isLoop; //���� ���� ���� �ݺ����� �ѹ��� ����ǰ� �������� üũ
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	ArrayList<Track> trackList=new ArrayList<Track>();
	
	
	public Music(String name,ArrayList<Track> trackList,boolean isLoop) {
		try {
			this.isLoop=isLoop;
			file=new File(Main.class.getResource("../music/"+name).toURI());
			fis=new FileInputStream(file);
			bis=new BufferedInputStream(fis); //�ش� ������ ���ۿ� ��Ƽ� �о�� �� �ֵ���
			player=new Player(bis); //�÷��̾�� �ش� ������ ���� �� �ֵ���
			this.trackList=trackList;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public int getTime() { //���� ����Ǵ� ������ ���� � ��ġ?���� ����Ǵ��� �˷��ش�
		if(player==null)
			return 0;
		return player.getPosition();
	}
	public void close() { //�������� ������ ��, �ڷΰ��⳪ ������ư�� Ŭ���ϸ� �뷡 ��� ���� 
		isLoop=false;
		player.close();
		this.interrupt(); //�ش� �����带 �������·� ����
	}
	@Override
	public void run() { //�����带 ��ӹ����� ������ ����ؾߵǴ� �Լ�
		try {
			do {
				player.play();
				int n=(int)(Math.random()*(trackList.size())+0);
				String name=trackList.get(n).getListMusic();
				Static.playingMusic=name;
				//Static.songLabel.setText(name);
				file=new File(Main.class.getResource("../music/"+name).toURI());
				fis=new FileInputStream(file);
				bis=new BufferedInputStream(fis); //�ش� ������ ���ۿ� ��Ƽ� �о�� �� �ֵ���
				player=new Player(bis); //�÷��̾�� �ش� ������ ���� �� �ֵ���
			} while (isLoop); 
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}