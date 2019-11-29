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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cafe.Sum;

@SuppressWarnings("serial")

public class OrderSystem extends JFrame {
	private Image screenImage; // ���� ���۸��� ���ؼ� ��ü ȭ�鿡 ����
	private Graphics screenGraphic; // �̹����� ��� �� �ν��Ͻ�

	private ImageIcon breaktime = new ImageIcon(Main.class.getResource("../images/breaktime.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon menuBarImage = new ImageIcon(Main.class.getResource("../images/menuBar.png"));

	private Music selectedMusic;
	private Image selectedImage;
	private int nowSelected = 0;

	private int mouseX, mouseY;
//	private boolean isSelectScreen = false; // �뷡 ��û ��ư�� ������ �� true��
	ArrayList<Track> trackList = new ArrayList<Track>();
	private int n;
	private Music introMusic;
	private IntroScreenPanel IntroPanel = null;
	private SongScreenPanel SongPanel = null;
	private OrderScreenPanel OrderPanel = null;
	private CardLayout cards = new CardLayout();
	CafeSystem cafesystem;

	public OrderSystem(CafeSystem CS) {
		cafesystem = CS;
		trackList.add(new Track("parisImage.png", "Lauv - Paris In The Rain.mp3"));
		trackList.add(new Track("boatImage.png", "George - Boat.mp3"));
		trackList.add(new Track("olderImage.png", "Sasha Sloan - Older.mp3"));

		setTitle("Cafe Management System"); // ���α׷� �̸�
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // ���α׷� â ����
		Container c = getContentPane();
		c.setLayout(cards);
		IntroPanel = new IntroScreenPanel();
		SongPanel = new SongScreenPanel();
		OrderPanel = new OrderScreenPanel();
		c.add("Intro", IntroPanel);
		c.add("Song", SongPanel);
		c.add("Order", OrderPanel);

		setResizable(false); // ���α׷� �ʺ�,���� ����ڰ� �� �����̰� ����
		setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� �������� �� ���α׷��� ����, �̰� ������ �����ص� ��� ����
		setVisible(false); // â�� ������µǵ��� ������, �⺻ ���� false
		setBackground(new Color(0, 0, 0, 0)); // ����� ȸ���� �ƴ϶� ���� �Ͼ���� �ٲ�

		n = (int) (Math.random() * (trackList.size()) + 0);
		introMusic = new Music(trackList.get(n).getListMusic(), trackList, true);
		introMusic.start();
	}

	class IntroScreenPanel extends JPanel {
		private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg"))
				.getImage();
		private ImageIcon startButtonBasicImage = new ImageIcon(
				Main.class.getResource("../images/startButtonBasic.png"));
		private ImageIcon startButtonEnteredImage = new ImageIcon(
				Main.class.getResource("../images/startButtonEntered.png"));
		private ImageIcon orderButtonBasicImage = new ImageIcon(
				Main.class.getResource("../images/orderButtonBasic.png"));
		private ImageIcon orderButtonEnteredImage = new ImageIcon(
				Main.class.getResource("../images/orderButtonEntered.png"));

		private JButton startButton = new JButton(startButtonBasicImage);
		private JButton orderButton = new JButton(orderButtonBasicImage);

		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);

		private JLabel breaktimeLB = null;

		public IntroScreenPanel() { // ���� ȭ�� �ǳ�
			breaktime = new ImageIcon(breaktime.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
			setUndecorated(true); // ���� �޴��ٸ� ������
			setLayout(null);
			
			breaktimeLB = new JLabel(breaktime); // �Ͻ����� ��
			breaktimeLB.setBounds(340, 60, 600, 600);
			breaktimeLB.setVisible(false);
			add(breaktimeLB);

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
					startButton.setIcon(startButtonEnteredImage); // ���콺�� �ö����� enterImage�� ����
					startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				@Override
				public void mouseExited(MouseEvent e) {
					startButton.setIcon(startButtonBasicImage);
					startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) { // �뷡��û ��ư�� ���� ���� �̺�Ʈ �κ�
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
					orderButton.setIcon(orderButtonEnteredImage); // ���콺�� �ö����� enterImage�� ����
					orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				@Override
				public void mouseExited(MouseEvent e) {
					orderButton.setIcon(orderButtonBasicImage);
					orderButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				/// "�׳� �ֹ�" Ŭ�� ��ư �̺�Ʈ ///
				public void mousePressed(MouseEvent e) {
					// �ֹ��Է� ȭ�� �޼���
					ChangePanel("Order");
				}
			});
			add(orderButton);
		}

		public void paintComponent(Graphics g) { // �ʱ�ȭ�� ���
			g.drawImage(background, 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	class SongScreenPanel extends JPanel { // �뷡 ȭ�� �ǳ�
		private Image background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("../images/backButton.png"));

		private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
		private ImageIcon leftButtonEnteredImage = new ImageIcon(
				Main.class.getResource("../images/leftButtonEntered.png"));
		private ImageIcon rightButtonBasicImage = new ImageIcon(
				Main.class.getResource("../images/rightButtonBasic.png"));
		private ImageIcon rightButtonEnteredImage = new ImageIcon(
				Main.class.getResource("../images/rightButtonEntered.png"));
		private ImageIcon reserveButtonEnteredImage = new ImageIcon(
				Main.class.getResource("../images/reserveButtonEntered.png"));
		private ImageIcon reserveButtonBasicImage = new ImageIcon(
				Main.class.getResource("../images/reserveButtonBasic.png"));

		private JButton backButton = new JButton(backButtonImage);
		private JButton leftButton = new JButton(leftButtonBasicImage);
		private JButton rightButton = new JButton(rightButtonBasicImage);
		private JButton reserveButton = new JButton(reserveButtonBasicImage);

		private JLabel menuBar = new JLabel(menuBarImage);
		private JButton exitButton = new JButton(exitButtonBasicImage);

		public SongScreenPanel() {
			setUndecorated(true); // ���� �޴��ٸ� ������
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
					leftButton.setIcon(leftButtonEnteredImage); // ���콺�� �ö����� enterImage�� ����
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
				public void mousePressed(MouseEvent e) { // �뷡��û ��ư�� ������
					String name = trackList.get(nowSelected).getListMusic();

					JOptionPane.showMessageDialog(null, "�뷡 ��û�� �Ϸ�Ǿ����ϴ�.", "Message", JOptionPane.INFORMATION_MESSAGE);
					ChangePanel("Intro");
				}
			});
			add(reserveButton);

			backButton.setVisible(true);
			backButton.setBounds(710, 620, 150, 80);
			backButton.setBorderPainted(false); // ��ư �׵θ� ����
			backButton.setContentAreaFilled(false); // ��ư ���� ��� ǥ�� ����
			backButton.setFocusPainted(false); // ��Ŀ�� ǥ�� ����
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

		public void paintComponent(Graphics g) { // �뷡 ���
			g.drawImage(background, 0, 0, null);
			g.drawImage(selectedImage, 400, 110, null); // �ܼ��� �̹����� ȭ�鿡 ������ֱ� ���� ��� + �������� �̹����� ������ ���� �ַ� ���
			setOpaque(false);
			super.paintComponent(g);
			this.repaint();
		}

		public void selectTrack(int nowSelected) {
			// if (selectedMusic != null) // �뷡�� ����ǰ� �ִٸ�
			// selectedMusic.close(); // �������� �뷡 ����
			selectedImage = new ImageIcon(
					Main.class.getResource("../images/" + trackList.get(nowSelected).getListImage())).getImage();
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

	class OrderScreenPanel extends JPanel { // �ֹ� ȭ�� �ǳ�
		///
		/// �̹��� ����
		///
		private Image background = new ImageIcon(Main.class.getResource("../images/white.png")).getImage();
		private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("../images/backButton.png"));
		private ImageIcon Img_coffee1 = new ImageIcon(Main.class.getResource("../images/img_coffee1.png"));
		private ImageIcon Img_coffee2 = new ImageIcon(Main.class.getResource("../images/img_coffee2.png"));
		private ImageIcon Img_coffee3 = new ImageIcon(Main.class.getResource("../images/img_coffee3.png"));
		private ImageIcon Img_coffee4 = new ImageIcon(Main.class.getResource("../images/img_coffee4.png"));
		private ImageIcon Img_coffee5 = new ImageIcon(Main.class.getResource("../images/img_coffee5.png"));
		private ImageIcon Img_menu5 = new ImageIcon(Main.class.getResource("../images/img_menu5.png"));
		private ImageIcon Img_OrderButton = new ImageIcon(Main.class.getResource("../images/OrderButton.jpg"));
		private ImageIcon Img_bar = new ImageIcon(Main.class.getResource("../images/bar.png"));

		///
		/// ��ư ����
		///
		private JButton backButton = new JButton(backButtonImage);
		private JButton Btn_coffee1 = new JButton(Img_coffee1);
		private JButton Btn_coffee2 = new JButton(Img_coffee2);
		private JButton Btn_coffee3 = new JButton(Img_coffee3);
		private JButton Btn_coffee4 = new JButton(Img_coffee4);
		private JButton Btn_coffee5 = new JButton(Img_coffee5);
		private JButton Btn_menu5 = new JButton(Img_menu5);
		private JButton Btn_OrderButton = new JButton(Img_OrderButton);
		private JButton exitButton = new JButton(exitButtonBasicImage);

		///
		/// �� ����
		///
		private JLabel lbl_order = new JLabel("�ֹ��ݾ�");
		private JLabel lbl_won = new JLabel("��");
		private JLabel lbl_price = new JLabel("0"); // �ֹ� �ݾ� ��
		private JLabel lbl_bar = new JLabel(Img_bar);
		private JLabel menuBar = new JLabel(menuBarImage);
		private JLabel[] lbl_priceview; // �̹��� �� ���� ǥ�� �� ����
		private JLabel[] lbl_view; // ������ �޴� ǥ�� ��

		Menu[] m;

		public OrderScreenPanel() {

			///
			/// �޴� ��ü �迭 ���� �� ����, �̸� ����
			///
			m = new Menu[6];
			for (int i = 0; i < 6; i++)
				m[i] = new Menu();

			m[0].setPrice(1500);
			m[1].setPrice(1500);
			m[2].setPrice(1000);
			m[3].setPrice(2000);
			m[4].setPrice(2500);
			m[5].setPrice(4000);
			m[0].setName("Americano(Hot)");
			m[1].setName("Americano(Iced)");
			m[2].setName("Espresso");
			m[3].setName("Affogato");
			m[4].setName("Caramel_Macchiato");
			m[5].setName("Orange_Cake");

			///
			/// �޴� �̹��� �� ���� �� ���� ����
			///
			lbl_priceview = new JLabel[6];
			for (int i = 0; i < 6; i++)
				lbl_priceview[i] = new JLabel(Integer.toString(m[i].getPrice()) + "��");

			lbl_view = new JLabel[6];
			for (int i = 0; i < 6; i++)
				lbl_view[i] = new JLabel("");

			setUndecorated(true); // ���� �޴��ٸ� ������
			setLayout(null);
			add(exitButton);
			add(menuBar);
			MenuBar(menuBar, exitButton);

			///
			/// ù��° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_coffee1.setBounds(100, 90, 185, 177); /// setBounds(x��ǥ, y��ǥ, x,yũ��)
			Btn_coffee1.setBorderPainted(false);
			Btn_coffee1.setContentAreaFilled(false);
			Btn_coffee1.setFocusPainted(false);
			Btn_coffee1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee1.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[0].getPrice();
					lbl_price.setText(Integer.toString(price));
					m[0].setCount(m[0].getCount() + 1);

					String text = m[0].getName() + " x " + Integer.toString(m[0].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[0].getName() + " x " + Integer.toString(m[0].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[0].getName() + " x " + Integer.toString(m[0].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_coffee1);
			Btn_coffee1.setVisible(true);

			///
			/// ù��° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[0].setVisible(true);
			lbl_priceview[0].setBounds(155, 280, 73, 36);
			lbl_priceview[0].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[0]);

			///
			/// �ι�° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_coffee2.setBounds(400, 90, 185, 190);
			Btn_coffee2.setBorderPainted(false);
			Btn_coffee2.setContentAreaFilled(false);
			Btn_coffee2.setFocusPainted(false);
			Btn_coffee2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee2.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[1].getPrice();
					lbl_price.setText(Integer.toString(price));
					m[1].setCount(m[1].getCount() + 1);

					String text = m[1].getName() + " x " + Integer.toString(m[1].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[1].getName() + " x " + Integer.toString(m[1].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[1].getName() + " x " + Integer.toString(m[1].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_coffee2);
			Btn_coffee2.setVisible(true);

			///
			/// �ι�° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[1].setVisible(true);
			lbl_priceview[1].setBounds(455, 280, 73, 36);
			lbl_priceview[1].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[1]);

			///
			/// ����° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_coffee3.setBounds(700, 110, 185, 177);
			Btn_coffee3.setBorderPainted(false);
			Btn_coffee3.setContentAreaFilled(false);
			Btn_coffee3.setFocusPainted(false);
			Btn_coffee3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee3.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[2].getPrice();
					
					lbl_price.setText(Integer.toString(price));
					m[2].setCount(m[2].getCount() + 1);

					String text = m[2].getName() + " x " + Integer.toString(m[2].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[2].getName() + " x " + Integer.toString(m[2].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[2].getName() + " x " + Integer.toString(m[2].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_coffee3);
			Btn_coffee3.setVisible(true);

			///
			/// ����° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[2].setVisible(true);
			lbl_priceview[2].setBounds(755, 280, 73, 36);
			lbl_priceview[2].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[2]);

			///
			/// �׹�° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_coffee4.setBounds(100, 420, 185, 177);
			Btn_coffee4.setBorderPainted(false);
			Btn_coffee4.setContentAreaFilled(false);
			Btn_coffee4.setFocusPainted(false);
			Btn_coffee4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee4.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[3].getPrice();
					lbl_price.setText(Integer.toString(price));
					m[3].setCount(m[3].getCount() + 1);

					String text = m[3].getName() + " x " + Integer.toString(m[3].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[3].getName() + " x " + Integer.toString(m[3].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[3].getName() + " x " + Integer.toString(m[3].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_coffee4);
			Btn_coffee4.setVisible(true);

			///
			/// �׹�° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[3].setVisible(true);
			lbl_priceview[3].setBounds(155, 600, 73, 36);
			lbl_priceview[3].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[3]);

			///
			/// �ټ���° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_coffee5.setBounds(400, 390, 185, 190);
			Btn_coffee5.setBorderPainted(false);
			Btn_coffee5.setContentAreaFilled(false);
			Btn_coffee5.setFocusPainted(false);
			Btn_coffee5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_coffee5.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[4].getPrice();
					lbl_price.setText(Integer.toString(price));
					m[4].setCount(m[4].getCount() + 1);

					String text = m[4].getName() + " x " + Integer.toString(m[4].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[4].getName() + " x " + Integer.toString(m[4].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[4].getName() + " x " + Integer.toString(m[4].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_coffee5);
			Btn_coffee5.setVisible(true);

			///
			/// �ټ���° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[4].setVisible(true);
			lbl_priceview[4].setBounds(455, 600, 73, 36);
			lbl_priceview[4].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[4]);

			///
			/// ������° �޴� ��ư ũ��, ��ġ �� �̺�Ʈ ����
			///
			Btn_menu5.setBounds(700, 420, 210, 170);
			Btn_menu5.setBorderPainted(false);
			Btn_menu5.setContentAreaFilled(false);
			Btn_menu5.setFocusPainted(false);
			Btn_menu5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_menu5.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					int i = 0;
					int price = Integer.parseInt(lbl_price.getText());
					price += m[5].getPrice();
					lbl_price.setText(Integer.toString(price));
					m[5].setCount(m[5].getCount() + 1);

					String text = m[5].getName() + " x " + Integer.toString(m[5].getCount() - 1);
					for (i = 0; i < 6; i++) {
						if (lbl_view[i].getText().contentEquals("")) {
							lbl_view[i].setText(m[5].getName() + " x " + Integer.toString(m[5].getCount()));
							break;
						} else if (lbl_view[i].getText().contentEquals(text)) {
							lbl_view[i].setText(m[5].getName() + " x " + Integer.toString(m[5].getCount()));
							break;
						}
					}
				}
			});
			add(Btn_menu5);
			Btn_menu5.setVisible(true);

			///
			/// ������° �޴� �̹��� ��ư �� ���� �� ũ��, ��ġ ����
			///
			lbl_priceview[5].setVisible(true);
			lbl_priceview[5].setBounds(775, 600, 73, 36);
			lbl_priceview[5].setFont(new Font("Gothic", Font.BOLD, 20));
			add(lbl_priceview[5]);

			///
			/// OrderNow ��ư
			///
			Btn_OrderButton.setBounds(1030, 580, 209, 77);
			Btn_OrderButton.setBorderPainted(false);
			Btn_OrderButton.setContentAreaFilled(false);
			Btn_OrderButton.setFocusPainted(false);
			Btn_OrderButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Btn_OrderButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_price.getText().equals("0")) // �޴��� ���� �ʾ����� �޽��� �ڽ� Show
						JOptionPane.showMessageDialog(null, "�޴��� �������ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
					else {
						int result1 = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "����",
								JOptionPane.YES_NO_OPTION);
						if (result1 == JOptionPane.CLOSED_OPTION) { // ���� ���
							return;
						} else if (result1 == JOptionPane.YES_OPTION) {
							int result2 = JOptionPane.showConfirmDialog(null, "����Ʈ�� �����Ͻðڽ��ϱ�?", "����Ʈ ����",
									JOptionPane.YES_NO_OPTION);
							if (result2 == JOptionPane.CLOSED_OPTION) { // ����Ʈ ����X
								JOptionPane.showMessageDialog(null, "���� �Ϸ�", " ", JOptionPane.INFORMATION_MESSAGE);
								cafesystem.addorder(m);
								Sum.total+=Integer.parseInt(lbl_price.getText());
								// new SlotMachineEx();
								ChangePanel("Intro");
							} else if (result2 == JOptionPane.YES_OPTION) { // ����Ʈ ���� O
								String number;
								while (true) {
									number = JOptionPane.showInputDialog("��ȭ��ȣ�� �Է����ּ���( '-' ���� ��ȣ�� �Է�)");
									if (number.length() != 11)
										JOptionPane.showMessageDialog(null, "11�ڸ� ��ȣ�� �Է����ּ���", "����",
												JOptionPane.INFORMATION_MESSAGE);
									else { // ���⿡ ȸ�� DB �ҷ��ͼ� ���� �����Ͱ� ������ ���ݾ��� 1%����, ���� ������ ������ ���� ������ �߰�

										break;
									}
								}

							} else { // ����Ʈ ���� X
								cafesystem.addorder(m);
							}
						} else { // ���� ���
							return;
						}
					}
				}
			});
			add(Btn_OrderButton);
			Btn_OrderButton.setVisible(true);

			///
			/// �ڷΰ��� ��ư
			///
			backButton.setVisible(true);
			backButton.setBounds(1150, 40, 120, 80);
			backButton.setBorderPainted(false); // ��ư �׵θ� ����
			backButton.setContentAreaFilled(false); // ��ư ���� ��� ǥ�� ����
			backButton.setFocusPainted(false); // ��Ŀ�� ǥ�� ����
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

			int y = 160;
			for (int i = 0; i < 6; i++) {
				lbl_view[i].setVisible(true);
				lbl_view[i].setBounds(1030, y, 243, 47);
				lbl_view[i].setFont(new Font("Gothic", Font.BOLD, 20));
				add(lbl_view[i]);
				y += 47;
			}

			lbl_view[0].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[0].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[0].getText() == "")
						return;
					else {
						String text = lbl_view[0].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0) {
									lbl_view[0].setText(lbl_view[1].getText());
									lbl_view[1].setText(lbl_view[2].getText());
									lbl_view[2].setText(lbl_view[3].getText());
									lbl_view[3].setText(lbl_view[4].getText());
									lbl_view[4].setText(lbl_view[5].getText());
									lbl_view[5].setText("");
								} else
									lbl_view[0].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
			lbl_view[1].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[1].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[1].getText() == "")
						return;
					else {
						String text = lbl_view[1].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0) {
									lbl_view[1].setText(lbl_view[2].getText());
									lbl_view[2].setText(lbl_view[3].getText());
									lbl_view[3].setText(lbl_view[4].getText());
									lbl_view[4].setText(lbl_view[5].getText());
									lbl_view[5].setText("");
								} else
									lbl_view[1].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
			lbl_view[2].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[2].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[2].getText() == "")
						return;
					else {
						String text = lbl_view[2].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0) {
									lbl_view[2].setText(lbl_view[3].getText());
									lbl_view[3].setText(lbl_view[4].getText());
									lbl_view[4].setText(lbl_view[5].getText());
									lbl_view[5].setText("");
								} else
									lbl_view[2].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
			lbl_view[3].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[3].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[3].getText() == "")
						return;
					else {
						String text = lbl_view[3].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0) {
									lbl_view[3].setText(lbl_view[4].getText());
									lbl_view[4].setText(lbl_view[5].getText());
									lbl_view[5].setText("");
								} else
									lbl_view[3].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
			lbl_view[4].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[4].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[4].getText() == "")
						return;
					else {
						String text = lbl_view[4].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0) {
									lbl_view[4].setText(lbl_view[5].getText());
									lbl_view[5].setText("");
								} else
									lbl_view[4].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
			lbl_view[5].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					lbl_view[5].setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö��� �� �ո������ ����
				}

				public void mousePressed(MouseEvent e) {
					if (lbl_view[5].getText() == "")
						return;
					else {
						String text = lbl_view[5].getText();
						String[] text2 = text.split(" ");
						for (int i = 0; i < 6; i++) {
							if (text2[0].equals(m[i].getName())) {
								m[i].setCount(m[i].getCount() - 1);
								lbl_price.setText(
										Integer.toString(Integer.parseInt(lbl_price.getText()) - m[i].getPrice()));
								if (m[i].getCount() == 0)
									lbl_view[5].setText("");
								else
									lbl_view[5].setText(m[i].getName() + " x " + m[i].getCount());
							}
						}
					}
				}
			});
		}

		public void paintComponent(Graphics g) { // �ֹ� ȭ�� ���
			g.drawImage(background, 0, 0, null);
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
				setVisible(false);
			}
		});
	}

	public void OrderSystemEnable(int n) {
		if (n == 0) {
			ChangePanel("Intro");
			setEnabled(false);
			IntroPanel.breaktimeLB.setVisible(true);
		} else if (n == 1) {
			setEnabled(true);
			IntroPanel.breaktimeLB.setVisible(false);
		}
	}

	public void ChangePanel(String S) {
		cards.show(this.getContentPane(), S);
	}
}