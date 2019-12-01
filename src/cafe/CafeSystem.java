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
	private Image screenImage; // 더블 버퍼링을 위해서 전체 화면에 대한
	private Graphics screenGraphic; // 이미지를 담는 두 인스턴스

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));

	private int mouseX, mouseY;

	private CardLayout cards = new CardLayout();
	LoginScreenPanel LoginPanel;
	MainScreenPanel MainPanel;
	OrderSystem OS = new OrderSystem(this);

	public CafeSystem() {
		setTitle("Cafe Management System"); // 프로그램 이름
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 프로그램 창 설정
		Container c = getContentPane();
		c.setLayout(cards);
		LoginPanel = new LoginScreenPanel();
		MainPanel = new MainScreenPanel();
		c.add("Login", LoginPanel);
		c.add("Main", MainPanel);
		setUndecorated(true); // 기존 메뉴바를 감춰줌
		setResizable(false); // 프로그램 너비,높이 사용자가 못 움직이게 고정
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 종료했을 때 프로그램도 종료, 이게 없으면 종료해도 프로그램이 계속 진행
		setVisible(true); // 화면이 정상출력되도록 도와줌, 기본 값은 false
		setBackground(new Color(0, 0, 0, 0)); // 배경이 회색이 아니라 전부 하얀색으로 바뀜

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
			orderbutton = new ImageIcon(orderbutton.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH)); // 이미지
																													// 아이콘
																													// 크기
																													// 조절
			orderbutton1 = new ImageIcon(orderbutton1.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			orderbutton2 = new ImageIcon(orderbutton2.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton = new ImageIcon(managerbutton.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton1 = new ImageIcon(managerbutton1.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			managerbutton2 = new ImageIcon(managerbutton2.getImage().getScaledInstance(350, 130, Image.SCALE_SMOOTH));
			playbutton = new ImageIcon(playbutton.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			pausebutton = new ImageIcon(pausebutton.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			Onbutton = new ImageIcon(Onbutton.getImage().getScaledInstance(96, 40, Image.SCALE_SMOOTH));
			Offbutton = new ImageIcon(Offbutton.getImage().getScaledInstance(96, 40, Image.SCALE_SMOOTH));
			setUndecorated(true); // 기존 메뉴바를 감춰줌
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);

			///
			///노래예약 목록
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
			SongList.add(new JLabel("<노래예약>"),gbcS);
			SongScroll.setBounds(580,220,300,400);
			SongScroll.setOpaque(false);
			SongScroll.getViewport().setBackground(new Color(255,255,255,122));
			SongList.setBorder(new TitledBorder(new LineBorder((new Color(255,255,255,255)),3)));
			SongList.setOpaque(false);
			add(SongScroll);
			
			
			///
			///주문 목록
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
			OrderList.add(new JLabel("<주문내역>"),gbcO);
			OrderScroll.setBounds(930,220,300,400);
			OrderScroll.setOpaque(false);
			OrderScroll.getViewport().setBackground(new Color(255,255,255,122));
			OrderList.setBorder(new TitledBorder(new LineBorder((new Color(255,255,255,255)),3)));
			OrderList.setOpaque(false);
			add(OrderScroll);
			

			JPanel songPanel = new JPanel(); // 노래 재생목록 판넬
			songPanel.setBounds(820, 120, 420, 50);
			songPanel.setBackground(new Color(255, 255, 255, 120));
			songPanel.setLayout(null);
			add(songPanel);
			
			Static.songLabel=new JLabel("");
			Static.songLabel.setFont(new Font("Verdana", Font.BOLD, 30));
			Static.songLabel.setForeground(Color.WHITE);
			Static.songLabel.setBounds(840, 130, 330, 30);
			add(Static.songLabel);
			
			JLabel TitleLabel = new JLabel("Cafe Mangerment"); // 타이틀 라벨
			TitleLabel.setFont(new Font("Verdana", Font.BOLD, 60));
			TitleLabel.setForeground(Color.WHITE);
			TitleLabel.setBounds(50, 55, 600, 80);
			add(TitleLabel);
			
			JButton songbutton = new JButton(playbutton); // 노래 재생버튼
			songbutton.setBorderPainted(false);
			songbutton.setContentAreaFilled(false);
			songbutton.setFocusPainted(false);
			songbutton.setBounds(750, 115, 50, 50);
			songbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// TODO Auto-generated method stub
					if (songbutton.getIcon() == playbutton) { // > 모양의 노래 재생 버튼을 눌렀을 때
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

			JButton Orderbutton = new JButton(orderbutton); // 주문 화면 버튼
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

			JButton Managerbutton = new JButton(managerbutton); // 관리 화면 버튼
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
			StringBuffer s = new StringBuffer("주문번호" + Integer.toString(OrderCount) + ": ");
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
			StringBuffer s = new StringBuffer("주문번호" + Integer.toString(OrderCount) + ": ");
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
		public void paintComponent(Graphics g) { // 초기화면 배경
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
			
			JLabel TitleLabel = new JLabel("Cafe Mangerment");		//타이틀 라벨
			TitleLabel.setFont(new Font("Verdana",Font.BOLD,60));
			TitleLabel.setForeground(Color.WHITE);
			TitleLabel.setBounds(50, 55, 600, 80);
			add(TitleLabel);
			
			JButton btnLogin = new JButton("로그인");
			btnLogin.setBounds(220, 34, 75, 75);
			btnLogin.addActionListener(new ActionListener() { 			// 로그인 버튼 이벤트 처리
				@Override
				public void actionPerformed(ActionEvent e) {
				//	dbLogin();		                            // 이 줄이랑 이 밑에줄 주석 해제하면 첨에 로그인해야됨
					//if(result!=0 && result!=-1)
						ChangePanel("Main");
				}
			});
			LP.add(btnLogin);
		}
		// 로그인 기능 수행하는 dbLogin() 메서드 정의
		 void dbLogin() {
			// DB 아이디와 패스워드를 가져와서 DAO 클래스의 login() 메서드 호출
			// => 로그인 수행 결과를 리턴받아 "아이디 없음", "패스워드 틀림", "로그인 성공" 세 가지로 분류
			// 0. 로그인 버튼 vs 로그아웃 버튼 판별
			// 1. 로그인 버튼일 경우
			// 1-1. 아이디 또는 패스워드가 잘못됐을 경우 경고메세지 출력 후 해당 필드에 커서 요청
			//Username, Password 텍스트필드 입력 가능 설정
			
			String username = new String(userText.getText());
			String password = new String(passText.getPassword());
				
				AdminDTO dto = new AdminDTO(username, password);
				AdminDAO dao = AdminDAO.getInstance();
				result = dao.login(dto);

				if (result == 0) { // 아이디가 없을 경우
					JOptionPane.showMessageDialog(rootPane, "존재하지 않는 계정입니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					userText.requestFocus();
					return;
				} else if (result == -1) { // 패스워드가 틀렸을 경우
					JOptionPane.showMessageDialog(rootPane, "패스워드가 일치하지 않습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
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

		public void paintComponent(Graphics g) { // 초기화면 배경
			g.drawImage(background, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	public void MenuBar(JLabel menuBar, JButton exitButton) { // 메뉴바 생성
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