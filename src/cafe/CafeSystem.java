package cafe;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cafe.OrderSystem.IntroScreenPanel;
import cafe.OrderSystem.OrderScreenPanel;
import cafe.OrderSystem.SongScreenPanel;
import cafe.AdminGUI;

public class CafeSystem extends JFrame {
	private Image screenImage; // 더블 버퍼링을 위해서 전체 화면에 대한
	private Graphics screenGraphic; // 이미지를 담는 두 인스턴스

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));
	

	private JButton exitButton;
	private int mouseX, mouseY;
	
	private CardLayout cards = new CardLayout();

	OrderSystem OS = new OrderSystem(this);
	
	public CafeSystem() {
		setTitle("Cafe Management System"); // 프로그램 이름
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 창 설정
		Container c= getContentPane();
		c.setLayout(cards);
		c.add("Login", new LoginScreenPanel());
		c.add("Main", new MainScreenPanel());
		setUndecorated(true); // 기존 메뉴바를 감춰줌
		setResizable(false); // 프로그램 너비,높이 사용자가 못 움직이게 고정
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료했을 때 프로그램도 종료, 이게 없으면 종료해도 프로그램이 계속 진행
		setVisible(true); // 화면이 정상출력되도록 도와줌, 기본 값은 false
		setBackground(new Color(0, 0, 0, 0)); // 배경이 회색이 아니라 전부 하얀색으로 바뀜

	}
	class MainScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		public MainScreenPanel() {
			setUndecorated(true);	// 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			

			JButton startOrderSystem = new JButton("주문 시스템 실행");
			startOrderSystem.setBounds(800, 300, 300, 100);
			startOrderSystem.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					OS.setVisible(true);
				}
			});
			add(startOrderSystem);

			JButton startAdminSystem = new JButton("관리자 시스템 실행");
			startAdminSystem.setBounds(800, 500, 300, 100);
			startAdminSystem.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new AdminGUI();
				}
			});
			add(startAdminSystem);
		}
		public void paintComponent(Graphics g) { // 초기화면 배경
			g.drawImage(background, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}
	
	class LoginScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		
		private JPasswordField passText = new JPasswordField(20);
	    private JTextField userText = new JTextField(20);
	    
		public LoginScreenPanel() {
			setUndecorated(true);	// 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			
			JPanel LP = new LoginPanel();
			LP.setBounds(490, 285, 315, 145);
			add(LP);
			JLabel IdLabel = new JLabel("아이디");
			IdLabel.setBounds(30, 40, 40, 20);
			LP.add(IdLabel);
			JLabel PwLabel = new JLabel("비밀번호");
			PwLabel.setBounds(20, 85, 50, 20);
			LP.add(PwLabel);
			userText.setBounds(80, 35, 130, 30);
			LP.add(userText);
			passText.setBounds(80, 80, 130, 30);
			LP.add(passText);			
			
			JButton LoginButton = new JButton("로그인");
			LoginButton.setBounds(220, 34, 75, 75);
			LoginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				
					ChangePanel("Main");
				}
			});
			LP.add(LoginButton);
		}
		class LoginPanel extends JPanel {
			public LoginPanel() {
				setBackground(new Color(255,255,255,240));
				setLayout(null);
			}
		}
		public void paintComponent(Graphics g) { // 초기화면 배경
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
				System.exit(0);
			}
		});
	}
	public void ChangePanel(String S) {
		cards.show(this.getContentPane(), S);
	}
}