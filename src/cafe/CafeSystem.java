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
	private Image screenImage; // ���� ���۸��� ���ؼ� ��ü ȭ�鿡 ����
	private Graphics screenGraphic; // �̹����� ��� �� �ν��Ͻ�

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

	private JButton exitButton;
	private int mouseX, mouseY;

	public CafeSystem() {
		setTitle("Cafe Management System"); // ���α׷� �̸�
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // ���α׷� â ����
		exitButton = new JButton(exitButtonEnteredImage);
		setUndecorated(true); // ���� �޴��ٸ� ������
		setResizable(false); // ���α׷� �ʺ�,���� ����ڰ� �� �����̰� ����
		setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� �������� �� ���α׷��� ����, �̰� ������ �����ص� ���α׷��� ��� ����
		setVisible(true); // ȭ���� ������µǵ��� ������, �⺻ ���� false
		setBackground(new Color(0, 0, 0, 0)); // ����� ȸ���� �ƴ϶� ���� �Ͼ������ �ٲ�

		
		Container c = getContentPane();
		c.setLayout(null); // ���̾ƿ��� Default ���̾ƿ����� null�� ����

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
				int x = e.getXOnScreen(); // �巡�� �Ҷ����� â ��ġ �ٲ���
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		c.add(menuBar);

		JLabel name = new JLabel("ID");
		name.setBounds(400, 150, 200, 190);
		c.add(name);

		JButton startOrderSystem = new JButton("�ֹ� �ý��� ����");
		startOrderSystem.setBounds(800, 300, 300, 100);
		startOrderSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new OrderSystem();
				
			}
		});
		c.add(startOrderSystem);

		JButton startAdminSystem = new JButton("������ �ý��� ����");
		startAdminSystem.setBounds(800, 500, 300, 100);
		startAdminSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerGUI();
			}
		});
		c.add(startAdminSystem);
	}

	public void paint(Graphics g) { // ���� ù������ �ʱ� ȭ�� �׷��ִ� �Լ�
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null); // ���׸�
		paintComponents(g); // JLabel���� �������� �������� �׷��� ,add �κ�
		this.repaint(); // ��ü ȭ���� �� �������� �׷���
	}
}