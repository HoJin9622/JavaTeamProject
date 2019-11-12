package cafe;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OrderSystem extends JFrame {
	private Image screenImage; // 더블 버퍼링을 위해서 전체 화면에 대한
	private Graphics screenGraphic; // 이미지를 담는 두 인스턴스

	private ImageIcon exitButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage=new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon startButtonBasicImage=new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon startButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon orderButtonBasicImage=new ImageIcon(Main.class.getResource("../images/orderButtonBasic.png"));
	private ImageIcon orderButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/orderButtonEntered.png"));
	private ImageIcon leftButtonBasicImage=new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
	private ImageIcon rightButtonBasicImage=new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
	private ImageIcon reserveButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/reserveButtonEntered.png"));
	private ImageIcon reserveButtonBasicImage=new ImageIcon(Main.class.getResource("../images/reserveButtonBasic.png"));
	private ImageIcon Img_coffee1 = new ImageIcon(Main.class.getResource("../images/img_coffee1.png"));
	private ImageIcon Img_coffee2 = new ImageIcon(Main.class.getResource("../images/img_coffee2.png"));
	private ImageIcon Img_coffee3 = new ImageIcon(Main.class.getResource("../images/img_coffee3.png"));
	private ImageIcon Img_coffee4 = new ImageIcon(Main.class.getResource("../images/img_coffee4.png"));
	private ImageIcon Img_coffee5 = new ImageIcon(Main.class.getResource("../images/img_coffee5.png"));
	private ImageIcon Img_Plus = new ImageIcon(Main.class.getResource("../images/Plus.png"));
	private ImageIcon Img_OrderButton = new ImageIcon(Main.class.getResource("../images/OrderButton.jpg"));
		
	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg"))
			.getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton orderButton = new JButton(orderButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton reserveButton = new JButton(reserveButtonBasicImage);
	private JButton Btn_coffee1 = new JButton(Img_coffee1);
	private JButton Btn_coffee2 = new JButton(Img_coffee2);
	private JButton Btn_coffee3 = new JButton(Img_coffee3);
	private JButton Btn_coffee4 = new JButton(Img_coffee4);
	private JButton Btn_coffee5 = new JButton(Img_coffee5);
	private JButton Btn_Plus = new JButton(Img_Plus);
	private JButton Btn_OrderButton = new JButton(Img_OrderButton);
	
	private int mouseX,mouseY;
	private boolean isReserve=false; //노래 신청 버튼을 눌렀을 때 true로 	
	
	private Image selectedImage;
	private Music selectedMusic;
	private Music introMusic;
	private int nowSelected=0;
	ArrayList<Track> trackList=new ArrayList<Track>();
	
	public OrderSystem() {
		trackList.add(new Track("parisImage.png","Lauv - Paris In The Rain.mp3"));
		trackList.add(new Track("boatImage.png","George - Boat.mp3"));
		trackList.add(new Track("olderImage.png","Sasha Sloan - Older.mp3"));

		int n=(int)(Math.random()*(trackList.size())+0);
		introMusic = new Music(trackList.get(n).getListMusic(), true);
		introMusic.start();
		introScreen();
		
		
	}
	public void introScreen() {
		setTitle("Cafe Management System"); // 프로그램 이름
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 창 설정
		setUndecorated(true); // 기존 메뉴바를 감춰줌
		setResizable(false); // 프로그램 너비,높이 사용자가 못 움직이게 고정
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료했을 때 프로그램도 종료, 이게 없으면 종료해도 계속 진행
		setVisible(true); // 창이 정상출력되도록 도와줌, 기본 값은 false
		setBackground(new Color(0, 0, 0, 0)); // 배경이 회색이 아니라 전부 하얀색을 바뀜
		setLayout(null); // 버튼이나 jLabel 넣었을 때 위치? 그대로 넣어줌
		
		
		exitButton.setBounds(1245, 0, 30, 30); //화면 우측 상단
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage); //마우스가 올라가을때 enterImage로 변경
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		add(exitButton);
		
		startButton.setBounds(100, 300, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage); //마우스가 올라가을때 enterImage로 변경
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			@Override
			public void mousePressed(MouseEvent e) { //노래신청 버튼을 누른 후의 이벤트 부분 
				selectSongScreen();
				
			}
		});
		add(startButton);
		
		orderButton.setBounds(100, 450, 400, 100); 
		orderButton.setBorderPainted(false);
		orderButton.setContentAreaFilled(false);
		orderButton.setFocusPainted(false);
		orderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				orderButton.setIcon(orderButtonEnteredImage); //마우스가 올라가을때 enterImage로 변경
				orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
			}
			@Override
			public void mouseExited(MouseEvent e) {
				orderButton.setIcon(orderButtonBasicImage);
				orderButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			@Override
			///                     ///
			///"그냥 주문" 클릭 버튼 이벤트 ///
			///                     ///
			public void mousePressed(MouseEvent e) {
				//주문입력 화면 메서드 ex) orderScreen();
				background=new ImageIcon(Main.class.getResource("../images/white.png"))
						.getImage();
				
				startButton.setVisible(false); //버튼을 안보이게 만듬
				orderButton.setVisible(false);
				
				Btn_coffee1.setBounds(10, 40, 185, 177); /// setBounds(x좌표, y좌표, xy크기) 
				Btn_coffee1.setBorderPainted(false);
				Btn_coffee1.setContentAreaFilled(false);
				Btn_coffee1.setFocusPainted(false);
				Btn_coffee1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee1.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
					}
				});
				add(Btn_coffee1);
				
				Btn_coffee2.setBounds(310, 40, 185, 190); 
				Btn_coffee2.setBorderPainted(false);
				Btn_coffee2.setContentAreaFilled(false);
				Btn_coffee2.setFocusPainted(false);
				Btn_coffee2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee2.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
					}
				});
				add(Btn_coffee2);
				
				Btn_coffee3.setBounds(610, 40, 185, 177); 
				Btn_coffee3.setBorderPainted(false);
				Btn_coffee3.setContentAreaFilled(false);
				Btn_coffee3.setFocusPainted(false);
				Btn_coffee3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee3.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
					}
				});
				add(Btn_coffee3);
				
				Btn_coffee4.setBounds(920, 40, 185, 177); 
				Btn_coffee4.setBorderPainted(false);
				Btn_coffee4.setContentAreaFilled(false);
				Btn_coffee4.setFocusPainted(false);
				Btn_coffee4.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee4.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
					}
				});
				add(Btn_coffee4);
				
				Btn_coffee5.setBounds(10, 340, 185, 190); 
				Btn_coffee5.setBorderPainted(false);
				Btn_coffee5.setContentAreaFilled(false);
				Btn_coffee5.setFocusPainted(false);
				Btn_coffee5.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee5.setCursor(new Cursor(Cursor.HAND_CURSOR)); //마우스가 올라갔을 때 손모양으로 변경
					}
				});
				add(Btn_coffee5);
				
				Btn_Plus.setBounds(0,80, 185, 177); 
				Btn_Plus.setBorderPainted(false);
				Btn_Plus.setContentAreaFilled(false);
				Btn_Plus.setFocusPainted(false);
				add(Btn_Plus);
				
				Btn_OrderButton.setBounds(800,380, 397, 127); 
				Btn_OrderButton.setBorderPainted(false);
				Btn_OrderButton.setContentAreaFilled(false);
				Btn_OrderButton.setFocusPainted(false);
				add(Btn_OrderButton);
			}
		});
		add(orderButton);
		
		leftButton.setVisible(false);
		leftButton.setBounds(330, 330, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage); //마우스가 올라가을때 enterImage로 변경
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				selectLeft();
			}
		});
		add(leftButton);
		
		rightButton.setVisible(false);
		rightButton.setBounds(900, 330, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage); 
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				selectRight();
			}
		});
		add(rightButton);
		
		reserveButton.setVisible(false);
		reserveButton.setBounds(400, 620, 280, 67);
		reserveButton.setBorderPainted(false);
		reserveButton.setContentAreaFilled(false);
		reserveButton.setFocusPainted(false);
		reserveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				reserveButton.setIcon(reserveButtonEnteredImage); 
			}
			@Override
			public void mouseExited(MouseEvent e) {
				reserveButton.setIcon(reserveButtonBasicImage);
			}
			@Override
			public void mousePressed(MouseEvent e) {
			
			}
		});
		add(reserveButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX=e.getX();
				mouseY=e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x=e.getXOnScreen();  //마우스로 창을 드래그해서 끌 때마다 창의 x좌표 위치 변경을 위한 필드, 아래 줄은 y좌표 변경
				int y=e.getYOnScreen();
				setLocation(x-mouseX,y-mouseY);
			}
		});
		add(menuBar);
	}
	public void selectSongScreen() {
		introMusic.close();
		int n=(int)(Math.random()*(trackList.size())+0);
		selectTrack(n);
		startButton.setVisible(false); //버튼을 안보이게 만듬
		orderButton.setVisible(false);
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		reserveButton.setVisible(true);
		background=new ImageIcon(Main.class.getResource("../images/mainBackground.jpg"))
				.getImage();
		isReserve=true;
	}
	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null); //배경그림
		if(isReserve) {
			g.drawImage(selectedImage,400,110,null); //단순히 이미지를 화면에 출력해주기 위해 사용 + 역동적인 이미지를 보여줄 때도 주로 사용
		}
		paintComponents(g); // JLabel같은 고정적인 프레임을 그려줌 ,add 부분
		this.repaint(); // 전체 화면을 매 순간마다 그려줌
	}
	public void paint(Graphics g) { // 가장 첫번쨰로 초기 화면 그려주는 함수
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) //노래가 실행되고 있다면
			selectedMusic.close(); //실행중인 노래 중지
		selectedImage=new ImageIcon(Main.class.getResource("../images/"+trackList.get(nowSelected).getListImage())).getImage();
		selectedMusic=new Music(trackList.get(nowSelected).getListMusic(),true);
		selectedMusic.start();
	}
	public void selectLeft() {
		if(nowSelected==0)
			nowSelected=trackList.size()-1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight() {
		if(nowSelected == trackList.size()-1)
			nowSelected=0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}
}