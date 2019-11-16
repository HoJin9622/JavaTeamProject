package cafe;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CafeSystem extends JFrame {
	private Image screenImage; // 더블 버퍼링을 위해서 전체 화면에 대한
	private Graphics screenGraphic; // 이미지를 담는 두 인스턴스

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

	private JButton exitButton;
	private int mouseX, mouseY;

	public CafeSystem() {
		setTitle("Cafe Management System"); // 프로그램 이름
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 창 설정
		exitButton = new JButton(exitButtonEnteredImage);
		setUndecorated(true); // 기존 메뉴바를 감춰줌
		setResizable(false); // 프로그램 너비,높이 사용자가 못 움직이게 고정
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료했을 때 프로그램도 종료, 이게 없으면 종료해도 프로그램이 계속 진행
		setVisible(true); // 화면이 정상출력되도록 도와줌, 기본 값은 false
		setBackground(new Color(0, 0, 0, 0)); // 배경이 회색이 아니라 전부 하얀색으로 바뀜

		
		Container c = getContentPane();
		c.setLayout(null); // 레이아웃을 Default 레이아웃에서 null로 설정

		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		c.add(exitButton);


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
				int x = e.getXOnScreen(); // 드래그 할때마다 창 위치 바꿔줌
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		c.add(menuBar);

		JLabel name = new JLabel("ID");
		name.setBounds(400, 150, 200, 190);
		c.add(name);

		JButton startOrderSystem = new JButton("주문 시스템 실행");
		startOrderSystem.setBounds(800, 300, 300, 100);
		startOrderSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new OrderSystem();
				
			}
		});
		c.add(startOrderSystem);

		JButton startAdminSystem = new JButton("관리자 시스템 실행");
		startAdminSystem.setBounds(800, 500, 300, 100);
		startAdminSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerGUI();
			}
		});
		c.add(startAdminSystem);
	}

	public void paint(Graphics g) { // 가장 첫번쨰로 초기 화면 그려주는 함수
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null); // 배경그림
		paintComponents(g); // JLabel같은 고정적인 프레임을 그려줌 ,add 부분
		this.repaint(); // 전체 화면을 매 순간마다 그려줌
	}
}