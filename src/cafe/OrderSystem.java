package cafe;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
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
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OrderSystem extends JFrame {
	private Image screenImage; // 더블 버퍼링을 위해서 전체 화면에 대한
	private Graphics screenGraphic; // 이미지를 담는 두 인스턴스

	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));
	
	private Music selectedMusic;
	private Image selectedImage;
	private int nowSelected = 0;
	
	private int mouseX, mouseY;
//	private boolean isSelectScreen = false; // 노래 신청 버튼을 눌렀을 때 true로
	ArrayList<Track> trackList = new ArrayList<Track>();
	private int n;
	private Music introMusic;

	private CardLayout cards = new CardLayout();

	public OrderSystem(CafeSystem CS) {
		CafeSystem cafesystem = CS;
		trackList.add(new Track("parisImage.png", "Lauv - Paris In The Rain.mp3"));
		trackList.add(new Track("boatImage.png", "George - Boat.mp3"));
		trackList.add(new Track("olderImage.png", "Sasha Sloan - Older.mp3"));

		setTitle("Cafe Management System"); // 프로그램 이름
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 창 설정
		Container c= getContentPane();
		c.setLayout(cards);
		c.add("Intro", new IntroScreenPanel());
		c.add("Song", new SongScreenPanel());	
		c.add("Order", new OrderScreenPanel());
		
		setResizable(false); // 프로그램 너비,높이 사용자가 못 움직이게 고정
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료했을 때 프로그램도 종료, 이게 없으면 종료해도 계속 진행
		setVisible(false); // 창이 정상출력되도록 도와줌, 기본 값은 false
		setBackground(new Color(0, 0, 0, 0)); // 배경이 회색이 아니라 전부 하얀색을 바뀜
		
		n = (int) (Math.random() * (trackList.size()) + 0);
		introMusic = new Music(trackList.get(n).getListMusic(), trackList, true);
		introMusic.start();
	}

	class IntroScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
		private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
		private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
		private ImageIcon orderButtonBasicImage = new ImageIcon(Main.class.getResource("../images/orderButtonBasic.png"));
		private ImageIcon orderButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/orderButtonEntered.png"));
		
		private JButton startButton = new JButton(startButtonBasicImage);
		private JButton orderButton = new JButton(orderButtonBasicImage);
				
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		
		public IntroScreenPanel() {			//시작 화면 판넬
			setUndecorated(true);	// 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			startButton.setBounds(100, 300, 400, 100);
			startButton.setBorderPainted(false);
			startButton.setContentAreaFilled(false);
			startButton.setFocusPainted(false);
			startButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					startButton.setIcon(startButtonEnteredImage); // 마우스가 올라가을때 enterImage로 변경
					startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					startButton.setIcon(startButtonBasicImage);
					startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				public void mousePressed(MouseEvent e) { // 노래신청 버튼을 누른 후의 이벤트 부분
					ChangePanel("Song");
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
					orderButton.setIcon(orderButtonEnteredImage); // 마우스가 올라가을때 enterImage로 변경
					orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					orderButton.setIcon(orderButtonBasicImage);
					orderButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
	
				@Override
				/// ///
				/// "그냥 주문" 클릭 버튼 이벤트 ///
				/// ///
				public void mousePressed(MouseEvent e) {
					// 주문입력 화면 메서드 ex) orderScreen();
					ChangePanel("Order");
				}
			});
			add(orderButton);
		}
		public void paintComponent(Graphics g) { // 초기화면 배경
			g.drawImage(background, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}
	
	
	
	class SongScreenPanel extends JPanel {			//노래 화면 판넬
		private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("../images/backButton.png"));
		
		private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
		private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
		private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
		private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
		private ImageIcon reserveButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/reserveButtonEntered.png"));
		private ImageIcon reserveButtonBasicImage = new ImageIcon(Main.class.getResource("../images/reserveButtonBasic.png"));
		
		private JButton backButton = new JButton(backButtonImage);
		private JButton leftButton = new JButton(leftButtonBasicImage);
		private JButton rightButton = new JButton(rightButtonBasicImage);
		private JButton reserveButton = new JButton(reserveButtonBasicImage);
		

		
		
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		public SongScreenPanel() {
			setUndecorated(true);	// 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			leftButton.setBounds(330, 330, 60, 60);
			leftButton.setBorderPainted(false);
			leftButton.setContentAreaFilled(false);
			leftButton.setFocusPainted(false);
			leftButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					leftButton.setIcon(leftButtonEnteredImage); // 마우스가 올라가을때 enterImage로 변경
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
				public void mousePressed(MouseEvent e) { // 노래신청 버튼을 누르면

				}
			});
			add(reserveButton);

			backButton.setVisible(true);
			backButton.setBounds(710, 620, 150, 80);
			backButton.setBorderPainted(false); // 버튼 테두리 설정
			backButton.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
			backButton.setFocusPainted(false); // 포커스 표시 설정
			backButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					backButton.setIcon(backButtonImage);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					backButton.setIcon(backButtonImage);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					ChangePanel("Intro");
				}
			});
			add(backButton);
			selectTrack(n);
		}

		public void paintComponent(Graphics g) { // 노래 배경
			g.drawImage(background, 0, 0, null);
			g.drawImage(selectedImage,400,110,null); //단순히 이미지를 화면에 출력해주기 위해 사용 + 역동적인 이미지를 보여줄 때도 주로 사용
			setOpaque(false);
			super.paintComponent(g);
			this.repaint();
		}
		public void selectTrack(int nowSelected) {
			if (selectedMusic != null) // 노래가 실행되고 있다면
				selectedMusic.close(); // 실행중인 노래 중지
			selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getListImage()))
					.getImage();
			selectedMusic = new Music(trackList.get(nowSelected).getListMusic(), trackList, true);
		}
		public void selectLeft() {
			if (nowSelected == 0)
				nowSelected = trackList.size() - 1;
			else
				nowSelected--;
			selectTrack(nowSelected);
		}

		public void selectRight() {
			if (nowSelected == trackList.size() - 1)
				nowSelected = 0;
			else
				nowSelected++;
			selectTrack(nowSelected);
		}
	}
	
	
	
	class OrderScreenPanel extends JPanel {			//주문 화면 판넬
		private Image background = new ImageIcon(Main.class.getResource("../images/white.png")).getImage();
		private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("../images/backButton.png"));
		
		private ImageIcon Img_coffee1 = new ImageIcon(Main.class.getResource("../images/img_coffee1.png"));
		private ImageIcon Img_coffee2 = new ImageIcon(Main.class.getResource("../images/img_coffee2.png"));
		private ImageIcon Img_coffee3 = new ImageIcon(Main.class.getResource("../images/img_coffee3.png"));
		private ImageIcon Img_coffee4 = new ImageIcon(Main.class.getResource("../images/img_coffee4.png"));
		private ImageIcon Img_coffee5 = new ImageIcon(Main.class.getResource("../images/img_coffee5.png"));
		private ImageIcon Img_Plus = new ImageIcon(Main.class.getResource("../images/Plus.png"));
		private ImageIcon Img_OrderButton = new ImageIcon(Main.class.getResource("../images/OrderButton.jpg"));
		private ImageIcon Img_bar = new ImageIcon(Main.class.getResource("../images/bar.png"));
		
		private JButton backButton = new JButton(backButtonImage);
		private JButton Btn_coffee1 = new JButton(Img_coffee1);
		private JButton Btn_coffee2 = new JButton(Img_coffee2);
		private JButton Btn_coffee3 = new JButton(Img_coffee3);
		private JButton Btn_coffee4 = new JButton(Img_coffee4);
		private JButton Btn_coffee5 = new JButton(Img_coffee5);
		private JButton Btn_Plus = new JButton(Img_Plus);
		private JButton Btn_OrderButton = new JButton(Img_OrderButton);

		private JLabel lbl_order = new JLabel("주문금액");
		private JLabel lbl_won = new JLabel("원");
		private JLabel lbl_price = new JLabel("0");
		private JLabel lbl_bar = new JLabel(Img_bar);
		
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		public OrderScreenPanel() {
			setUndecorated(true);	// 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			Btn_coffee1.setBounds(10, 40, 185, 177); /// setBounds(x좌표, y좌표, xy크기)
			Btn_coffee1.setBorderPainted(false);
			Btn_coffee1.setContentAreaFilled(false);
			Btn_coffee1.setFocusPainted(false);
			Btn_coffee1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee1.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
			});
			add(Btn_coffee1);
			Btn_coffee1.setVisible(true);

			Btn_coffee2.setBounds(310, 40, 185, 190);
			Btn_coffee2.setBorderPainted(false);
			Btn_coffee2.setContentAreaFilled(false);
			Btn_coffee2.setFocusPainted(false);
			Btn_coffee2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee2.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
			});
			add(Btn_coffee2);
			Btn_coffee2.setVisible(true);

			Btn_coffee3.setBounds(610, 40, 185, 177);
			Btn_coffee3.setBorderPainted(false);
			Btn_coffee3.setContentAreaFilled(false);
			Btn_coffee3.setFocusPainted(false);
			Btn_coffee3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee3.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
			});
			add(Btn_coffee3);
			Btn_coffee3.setVisible(true);

			Btn_coffee4.setBounds(10, 340, 185, 177);
			Btn_coffee4.setBorderPainted(false);
			Btn_coffee4.setContentAreaFilled(false);
			Btn_coffee4.setFocusPainted(false);
			Btn_coffee4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee4.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
			});
			add(Btn_coffee4);
			Btn_coffee4.setVisible(true);

			Btn_coffee5.setBounds(310, 340, 185, 190);
			Btn_coffee5.setBorderPainted(false);
			Btn_coffee5.setContentAreaFilled(false);
			Btn_coffee5.setFocusPainted(false);
			Btn_coffee5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee5.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
				}
			});
			add(Btn_coffee5);
			Btn_coffee5.setVisible(true);

			Btn_Plus.setBounds(0, 80, 185, 177);
			Btn_Plus.setBorderPainted(false);
			Btn_Plus.setContentAreaFilled(false);
			Btn_Plus.setFocusPainted(false);
			add(Btn_Plus);
			Btn_Plus.setVisible(true);

			Btn_OrderButton.setBounds(950, 580, 397, 127);
			Btn_OrderButton.setBorderPainted(false);
			Btn_OrderButton.setContentAreaFilled(false);
			Btn_OrderButton.setFocusPainted(false);
			add(Btn_OrderButton);
			Btn_OrderButton.setVisible(true);

			backButton.setVisible(true);
			backButton.setBounds(1150, 40, 120, 80);
			backButton.setBorderPainted(false); // 버튼 테두리 설정
			backButton.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
			backButton.setFocusPainted(false); // 포커스 표시 설정
			backButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					backButton.setIcon(backButtonImage);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					backButton.setIcon(backButtonImage);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					ChangePanel("Intro");
				}
			});
			add(backButton);

			lbl_order.setVisible(true);
			lbl_order.setBounds(1050, 400, 200, 200);
			lbl_order.setFont(new Font("Gothic", Font.BOLD, 30));
			add(lbl_order);

			lbl_won.setVisible(true);
			lbl_won.setBounds(1200, 450, 200, 200);
			lbl_won.setFont(new Font("Gothic", Font.BOLD, 30));
			add(lbl_won);

			lbl_price.setVisible(true);
			lbl_price.setBounds(1050, 450, 200, 200);
			lbl_price.setFont(new Font("Gothic", Font.BOLD, 30));
			add(lbl_price);

			lbl_bar.setVisible(true);
			lbl_bar.setBounds(1000, 25, 20, 700);
			add(lbl_bar);
		}
		public void paintComponent(Graphics g) { // 주문 화면 배경
			g.drawImage(background, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}	
	public void MenuBar(JLabel menuBar, JButton exitButton) {		//메뉴바 생성
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen(); // 마우스로 창을 드래그해서 끌 때마다 창의 x좌표 위치 변경을 위한 필드, 아래 줄은 y좌표 변경
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		exitButton.setBounds(1245, 0, 30, 30); // 화면 우측 상단
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage); // 마우스가 올라가을때 enterImage로 변경
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라갔을 때 손모양으로 변경
			}
	
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
	
			public void mousePressed(MouseEvent e) {
				setVisible(false);
			}
		});
	}
	public void ChangePanel(String S) {
		cards.show(this.getContentPane(), S);
	}
}