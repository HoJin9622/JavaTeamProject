package cafe;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CafeSystem extends JFrame {
	private Image screenImage; // ���� ���۸��� ���ؼ� ��ü ȭ�鿡 ����
	private Graphics screenGraphic; // �̹����� ��� �� �ν��Ͻ�

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));

	private int mouseX, mouseY;

	private CardLayout cards = new CardLayout();
	LoginScreenPanel LoginPanel;
	MainScreenPanel MainPanel;
	OrderSystem OS = new OrderSystem(this);

	public CafeSystem() {
		setTitle("Cafe Management System"); // ���α׷� �̸�
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // ���α׷� â ����
		Container c = getContentPane();
		c.setLayout(cards);
		LoginPanel = new LoginScreenPanel();
		MainPanel = new MainScreenPanel();
		c.add("Login", LoginPanel);
		c.add("Main", MainPanel);
		setUndecorated(true); // ���� �޴��ٸ� ������
		setResizable(false); // ���α׷� �ʺ�,���� ����ڰ� �� �����̰� ����
		setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� �������� �� ���α׷��� ����, �̰� ������ �����ص� ���α׷��� ��� ����
		setVisible(true); // ȭ���� ������µǵ��� ������, �⺻ ���� false
		setBackground(new Color(0, 0, 0, 0)); // ����� ȸ���� �ƴ϶� ���� �Ͼ������ �ٲ�

	}

	class MainScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/CafeBackground.jpg")).getImage();
		private ImageIcon playbutton = new ImageIcon(Main.class.getResource("../images/play.png"));
		private ImageIcon pausebutton = new ImageIcon(Main.class.getResource("../images/pause.png"));
		private ImageIcon orderbutton = new ImageIcon(Main.class.getResource("../images/order1.png"));
		private ImageIcon orderbutton1 = new ImageIcon(Main.class.getResource("../images/order2.png"));
		private ImageIcon orderbutton2 = new ImageIcon(Main.class.getResource("../images/order3.png"));
		private ImageIcon managerbutton = new ImageIcon(Main.class.getResource("../images/manager1.png"));
		private ImageIcon managerbutton1 = new ImageIcon(Main.class.getResource("../images/manager2.png"));
		private ImageIcon managerbutton2 = new ImageIcon(Main.class.getResource("../images/manager3.png"));
		private ImageIcon Onbutton = new ImageIcon(Main.class.getResource("../images/on.png"));
		private ImageIcon Offbutton = new ImageIcon(Main.class.getResource("../images/off.png"));

		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);
		private Music introMusic;
		
		JPanel OrderList;
		JScrollPane OrderScroll;
		JPanel SongList;
		JScrollPane SongScroll;
		private int OrderCount;
		public MainScreenPanel() {
			OrderCount = 1;
			orderbutton = new ImageIcon(orderbutton.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH)); // �̹���
																													// ������
																													// ũ��
																													// ����
			orderbutton1 = new ImageIcon(orderbutton1.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			orderbutton2 = new ImageIcon(orderbutton2.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton = new ImageIcon(managerbutton.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton1 = new ImageIcon(managerbutton1.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton2 = new ImageIcon(managerbutton2.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			playbutton = new ImageIcon(playbutton.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			pausebutton = new ImageIcon(pausebutton.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			Onbutton = new ImageIcon(Onbutton.getImage().getScaledInstance(96, 40, Image.SCALE_SMOOTH));
			Offbutton = new ImageIcon(Offbutton.getImage().getScaledInstance(96, 40, Image.SCALE_SMOOTH));
			setUndecorated(true); // ���� �޴��ٸ� ������
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);

			///
			///�뷡���� ���
			///
			SongList = new JPanel();
			SongScroll = new JScrollPane(SongList);
			SongList.setLayout(new GridBagLayout());
			GridBagConstraints gbcS = new GridBagConstraints();
			gbcS.fill = GridBagConstraints.HORIZONTAL;
	        gbcS.anchor = GridBagConstraints.PAGE_END;
			gbcS.weightx = 1.0;
			gbcS.weighty = 1.0;
			gbcS.gridx = 0;
			gbcS.gridy = 1000;
			SongList.add(new JLabel("<�뷡����>"),gbcS);
			SongScroll.setBounds(580,220,300,400);
			SongScroll.setOpaque(false);
			SongScroll.getViewport().setBackground(new Color(255,255,255,122));
			SongList.setBorder(new TitledBorder(new LineBorder((new Color(255,255,255,255)),3)));
			SongList.setOpaque(false);
			add(SongScroll);
			
			
			///
			///�ֹ� ���
			///
			OrderList = new JPanel();
			OrderScroll = new JScrollPane(OrderList);
			OrderList.setLayout(new GridBagLayout());
			GridBagConstraints gbcO = new GridBagConstraints();
			gbcO.fill = GridBagConstraints.HORIZONTAL;
	        gbcO.anchor = GridBagConstraints.PAGE_END;
			gbcO.weightx = 1.0;
			gbcO.weighty = 1.0;
			gbcO.gridx = 0;
			gbcO.gridy = 1000;
			OrderList.add(new JLabel("<�ֹ�����>"),gbcO);
			OrderScroll.setBounds(930,220,300,400);
			OrderScroll.setOpaque(false);
			OrderScroll.getViewport().setBackground(new Color(255,255,255,122));
			OrderList.setBorder(new TitledBorder(new LineBorder((new Color(255,255,255,255)),3)));
			OrderList.setOpaque(false);
			add(OrderScroll);
			

			JPanel songPanel = new JPanel(); // �뷡 ������ �ǳ�
			songPanel.setBounds(820, 120, 420, 50);
			songPanel.setBackground(new Color(255, 255, 255, 120));
			songPanel.setLayout(null);
			add(songPanel);
			
			Static.songLabel=new JLabel("");
			Static.songLabel.setFont(new Font("Verdana", Font.BOLD, 30));
			Static.songLabel.setForeground(Color.WHITE);
			Static.songLabel.setBounds(840, 130, 330, 30);
			add(Static.songLabel);
			
			JLabel TitleLabel = new JLabel("Cafe Mangerment"); // Ÿ��Ʋ ��
			TitleLabel.setFont(new Font("Verdana", Font.BOLD, 60));
			TitleLabel.setForeground(Color.WHITE);
			TitleLabel.setBounds(50, 55, 600, 80);
			add(TitleLabel);
			
			JButton songbutton = new JButton(playbutton); // �뷡 �����ư
			songbutton.setBorderPainted(false);
			songbutton.setContentAreaFilled(false);
			songbutton.setFocusPainted(false);
			songbutton.setBounds(750, 115, 50, 50);
			songbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// TODO Auto-generated method stub
					if (songbutton.getIcon() == playbutton) { // > ����� �뷡 ��� ��ư�� ������ ��
						songbutton.setIcon(pausebutton);
						Static.n=(int)(Math.random()*(Static.trackListAll.size())+0);
						introMusic = new Music(Static.trackListAll.get(Static.n).getListMusic(), Static.trackListAll, true);
						introMusic.start();
						Static.playingMusic=Static.trackListAll.get(Static.n).getListMusic();
						Static.songLabel.setText(Static.playingMusic);
					} else if (songbutton.getIcon() == pausebutton) {
						songbutton.setIcon(playbutton);
						introMusic.close();
						Static.songLabel.setText("");	
					}
				}
			});
			add(songbutton);

			JButton Orderbutton = new JButton(orderbutton); // �ֹ� ȭ�� ��ư
			Orderbutton.setBorderPainted(false);
			Orderbutton.setContentAreaFilled(false);
			Orderbutton.setFocusPainted(false);
			Orderbutton.setRolloverIcon(orderbutton1);
			Orderbutton.setPressedIcon(orderbutton2);
			Orderbutton.setBounds(100, 185, 350, 130);
			Orderbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OS.setVisible(true);
				}
			});
			add(Orderbutton);

			JButton Managerbutton = new JButton(managerbutton); // ���� ȭ�� ��ư
			Managerbutton.setBorderPainted(false);
			Managerbutton.setContentAreaFilled(false);
			Managerbutton.setFocusPainted(false);
			Managerbutton.setRolloverIcon(managerbutton1);
			Managerbutton.setPressedIcon(managerbutton2);
			Managerbutton.setBounds(100, 335, 350, 130);
			Managerbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new AdminGUI();
				}
			});
			add(Managerbutton);

			JButton OnOffbutton = new JButton(Onbutton);
			OnOffbutton.setBorderPainted(false);
			OnOffbutton.setContentAreaFilled(false);
			OnOffbutton.setFocusPainted(false);
			OnOffbutton.setBounds(1150, 640, 96, 40);
			OnOffbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// TODO Auto-generated method stub
					if (OnOffbutton.getIcon() == Offbutton) {
						OnOffbutton.setIcon(Onbutton);
						OS.OrderSystemEnable(1);
					} else if (OnOffbutton.getIcon() == Onbutton) {
						OnOffbutton.setIcon(Offbutton);
						OS.OrderSystemEnable(0);
					}
				}
			});
			add(OnOffbutton);

		}
		public void addorder(Menu [] m) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			gbc.gridx = 0;
			gbc.gridy = OrderCount;
			gbc.weighty = 0;
			gbc.anchor = GridBagConstraints.PAGE_START;
			boolean flag = false;
			StringBuffer s = new StringBuffer("�ֹ���ȣ" + Integer.toString(OrderCount) + ": ");
			for(int i = 0;i<6;i++) {
				if(m[i].getCount()>0) {
					if(flag) {
						s.append(", ");
					}
					s.append(m[i].getName()+Integer.toString(m[i].getCount()));
					flag = true;
				}
			}
			JLabel OrderLB = new JLabel(s.toString());
			gbc.gridy = OrderCount-1;
			OrderLB.setOpaque(false);
			OrderLB.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					OrderLB.setForeground(Color.RED);
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			OrderList.add(OrderLB, gbc);
			OrderCount++;
			OrderScroll.getVerticalScrollBar().setValue(OrderScroll.getVerticalScrollBar().getMaximum());
			validate();
			repaint();
		}
		public void addReserveMusic(Vector<String> reserveList) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			gbc.gridx = 0;
			gbc.gridy = OrderCount;
			gbc.weighty = 0;
			gbc.anchor = GridBagConstraints.PAGE_START;
			boolean flag = false;
			StringBuffer s = new StringBuffer("�ֹ���ȣ" + Integer.toString(OrderCount) + ": ");
				if(reserveList.size()>0) {
					if(flag) {
						s.append(", ");
					}
					s.append(reserveList.lastElement());
					flag = true;
				}
		
			JLabel OrderLB = new JLabel(s.toString());
			gbc.gridy = OrderCount-1;
			OrderLB.setOpaque(false);
			OrderLB.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					OrderLB.setForeground(Color.RED);
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			SongList.add(OrderLB, gbc);
			OrderCount++;
			SongScroll.getVerticalScrollBar().setValue(SongScroll.getVerticalScrollBar().getMaximum());
			validate();
			repaint();
		}
		public void paintComponent(Graphics g) { // �ʱ�ȭ�� ���
			g.drawImage(background, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	class LoginScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/CafeBackground.jpg")).getImage();

		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);

		private JPasswordField passText = new JPasswordField(20);
		private JTextField userText = new JTextField(20);
		private int result;
		
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
			
			JLabel TitleLabel = new JLabel("Cafe Mangerment");		//Ÿ��Ʋ ��
			TitleLabel.setFont(new Font("Verdana",Font.BOLD,60));
			TitleLabel.setForeground(Color.WHITE);
			TitleLabel.setBounds(50, 55, 600, 80);
			add(TitleLabel);
			
			JButton btnLogin = new JButton("�α���");
			btnLogin.setBounds(220, 34, 75, 75);
			btnLogin.addActionListener(new ActionListener() { 			// �α��� ��ư �̺�Ʈ ó��
				@Override
				public void actionPerformed(ActionEvent e) {
				//	dbLogin();		                            // �� ���̶� �� �ؿ��� �ּ� �����ϸ� ÷�� �α����ؾߵ�
					//if(result!=0 && result!=-1)
						ChangePanel("Main");
				}
			});
			LP.add(btnLogin);
		}
		// �α��� ��� �����ϴ� dbLogin() �޼��� ����
		 void dbLogin() {
			// DB ���̵�� �н����带 �����ͼ� DAO Ŭ������ login() �޼��� ȣ��
			// => �α��� ���� ����� ���Ϲ޾� "���̵� ����", "�н����� Ʋ��", "�α��� ����" �� ������ �з�
			// 0. �α��� ��ư vs �α׾ƿ� ��ư �Ǻ�
			// 1. �α��� ��ư�� ���
			// 1-1. ���̵� �Ǵ� �н����尡 �߸����� ��� ���޼��� ��� �� �ش� �ʵ忡 Ŀ�� ��û
			//Username, Password �ؽ�Ʈ�ʵ� �Է� ���� ����
			
			String username = new String(userText.getText());
			String password = new String(passText.getPassword());
				
				AdminDTO dto = new AdminDTO(username, password);
				AdminDAO dao = AdminDAO.getInstance();
				result = dao.login(dto);

				if (result == 0) { // ���̵� ���� ���
					JOptionPane.showMessageDialog(rootPane, "�������� �ʴ� �����Դϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					userText.requestFocus();
					return;
				} else if (result == -1) { // �н����尡 Ʋ���� ���
					JOptionPane.showMessageDialog(rootPane, "�н����尡 ��ġ���� �ʽ��ϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					passText.requestFocus();
					return;
				}	 
			}
		class LoginPanel extends JPanel {
			public LoginPanel() {
				setBackground(new Color(255, 255, 255, 120));
				setLayout(null);
			}
		}

		public void paintComponent(Graphics g) { // �ʱ�ȭ�� ���
			g.drawImage(background, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	public void MenuBar(JLabel menuBar, JButton exitButton) { // �޴��� ����
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