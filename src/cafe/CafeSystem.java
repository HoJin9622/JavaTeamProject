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
	private Image screenImage; // ���� ���۸��� ���ؼ� ��ü ȭ�鿡 ����
	private Graphics screenGraphic; // �̹����� ��� �� �ν��Ͻ�

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));
	

	private JButton exitButton;
	private int mouseX, mouseY;
	
	private CardLayout cards = new CardLayout();

	OrderSystem OS = new OrderSystem(this);
	
	public CafeSystem() {
		setTitle("Cafe Management System"); // ���α׷� �̸�
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // ���α׷� â ����
		Container c= getContentPane();
		c.setLayout(cards);
		c.add("Login", new LoginScreenPanel());
		c.add("Main", new MainScreenPanel());
		setUndecorated(true); // ���� �޴��ٸ� ������
		setResizable(false); // ���α׷� �ʺ�,���� ����ڰ� �� �����̰� ����
		setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� �������� �� ���α׷��� ����, �̰� ������ �����ص� ���α׷��� ��� ����
		setVisible(true); // ȭ���� ������µǵ��� ������, �⺻ ���� false
		setBackground(new Color(0, 0, 0, 0)); // ����� ȸ���� �ƴ϶� ���� �Ͼ������ �ٲ�

	}
	class MainScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		
		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		public MainScreenPanel() {
			setUndecorated(true);	// ���� �޴��ٸ� ������
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			

			JButton startOrderSystem = new JButton("�ֹ� �ý��� ����");
			startOrderSystem.setBounds(800, 300, 300, 100);
			startOrderSystem.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					OS.setVisible(true);
				}
			});
			add(startOrderSystem);

			JButton startAdminSystem = new JButton("������ �ý��� ����");
			startAdminSystem.setBounds(800, 500, 300, 100);
			startAdminSystem.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new AdminGUI();
				}
			});
			add(startAdminSystem);
		}
		public void paintComponent(Graphics g) { // �ʱ�ȭ�� ���
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
			setUndecorated(true);	// ���� �޴��ٸ� ������
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);
			
			JPanel LP = new LoginPanel();
			LP.setBounds(490, 285, 315, 145);
			add(LP);
			JLabel IdLabel = new JLabel("���̵�");
			IdLabel.setBounds(30, 40, 40, 20);
			LP.add(IdLabel);
			JLabel PwLabel = new JLabel("��й�ȣ");
			PwLabel.setBounds(20, 85, 50, 20);
			LP.add(PwLabel);
			userText.setBounds(80, 35, 130, 30);
			LP.add(userText);
			passText.setBounds(80, 80, 130, 30);
			LP.add(passText);			
			
			JButton LoginButton = new JButton("�α���");
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
		public void paintComponent(Graphics g) { // �ʱ�ȭ�� ���
			g.drawImage(background, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}
	
	
	
	
	public void MenuBar(JLabel menuBar, JButton exitButton) {		//�޴��� ����
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
				int x = e.getXOnScreen(); // ���콺�� â�� �巡���ؼ� �� ������ â�� x��ǥ ��ġ ������ ���� �ʵ�, �Ʒ� ���� y��ǥ ����
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		exitButton.setBounds(1245, 0, 30, 30); // ȭ�� ���� ���
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage); // ���콺�� �ö����� enterImage�� ����
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
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