package cafe;

import java.awt.Color;
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

@SuppressWarnings("serial")
public class OrderSystem extends JFrame {
	private Image screenImage; // ���� ���۸��� ���ؼ� ��ü ȭ�鿡 ����
	private Graphics screenGraphic; // �̹����� ��� �� �ν��Ͻ�

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
	private ImageIcon backButtonImage=new ImageIcon(Main.class.getResource("../images/backButton.png"));
	
	private ImageIcon reserveButtonEnteredImage=new ImageIcon(Main.class.getResource("../images/reserveButtonEntered.png"));
	private ImageIcon reserveButtonBasicImage=new ImageIcon(Main.class.getResource("../images/reserveButtonBasic.png"));
	private ImageIcon Img_coffee1 = new ImageIcon(Main.class.getResource("../images/img_coffee1.png"));
	private ImageIcon Img_coffee2 = new ImageIcon(Main.class.getResource("../images/img_coffee2.png"));
	private ImageIcon Img_coffee3 = new ImageIcon(Main.class.getResource("../images/img_coffee3.png"));
	private ImageIcon Img_coffee4 = new ImageIcon(Main.class.getResource("../images/img_coffee4.png"));
	private ImageIcon Img_coffee5 = new ImageIcon(Main.class.getResource("../images/img_coffee5.png"));
	private ImageIcon Img_Plus = new ImageIcon(Main.class.getResource("../images/Plus.png"));
	private ImageIcon Img_OrderButton = new ImageIcon(Main.class.getResource("../images/OrderButton.jpg"));
	private ImageIcon Img_bar = new ImageIcon(Main.class.getResource("../images/bar.png"));
		
	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg"))
			.getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JLabel lbl_order = new JLabel("�ֹ��ݾ�");
	private JLabel lbl_won = new JLabel("��");
	private JLabel lbl_price = new JLabel("0");
	private JLabel lbl_bar = new JLabel(Img_bar);
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton orderButton = new JButton(orderButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton backButton = new JButton(backButtonImage);
	
	private JButton reserveButton = new JButton(reserveButtonBasicImage);
	private JButton Btn_coffee1 = new JButton(Img_coffee1);
	private JButton Btn_coffee2 = new JButton(Img_coffee2);
	private JButton Btn_coffee3 = new JButton(Img_coffee3);
	private JButton Btn_coffee4 = new JButton(Img_coffee4);
	private JButton Btn_coffee5 = new JButton(Img_coffee5);
	private JButton Btn_Plus = new JButton(Img_Plus);
	private JButton Btn_OrderButton = new JButton(Img_OrderButton);
	
	private int mouseX,mouseY;
	private boolean isSelectScreen=false; //�뷡 ��û ��ư�� ������ �� true�� 	
	
	private Image selectedImage;
	private Music selectedMusic;
	ArrayList<Track> trackList=new ArrayList<Track>();
	
	private int n=(int)(Math.random()*(trackList.size())+0);
	private Music introMusic;
	private int nowSelected=0;
	
	public OrderSystem() {
		trackList.add(new Track("parisImage.png","Lauv - Paris In The Rain.mp3"));
		trackList.add(new Track("boatImage.png","George - Boat.mp3"));
		trackList.add(new Track("olderImage.png","Sasha Sloan - Older.mp3"));

		introMusic=new Music(trackList.get(n).getListMusic(), trackList,true);
		introMusic.start();
		introScreen();
		
	}
	public void introScreen() {
		setTitle("Cafe Management System"); // ���α׷� �̸�
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // ���α׷� â ����
		setUndecorated(true); // ���� �޴��ٸ� ������
		setResizable(false); // ���α׷� �ʺ�,���� ����ڰ� �� �����̰� ����
		setLocationRelativeTo(null); // ���α׷��� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� �������� �� ���α׷��� ����, �̰� ������ �����ص� ��� ����
	
		setVisible(true); // â�� ������µǵ��� ������, �⺻ ���� false
		
		setBackground(new Color(0, 0, 0, 0)); // ����� ȸ���� �ƴ϶� ���� �Ͼ���� �ٲ�
		setLayout(null); // ��ư�̳� jLabel �־��� �� ��ġ �״�� �־���
		
		exitButton.setBounds(1245, 0, 30, 30); //ȭ�� ���� ���
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage); //���콺�� �ö����� enterImage�� ����
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
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
				startButton.setIcon(startButtonEnteredImage); //���콺�� �ö����� enterImage�� ����
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			@Override
			public void mousePressed(MouseEvent e) { //�뷡��û ��ư�� ���� ���� �̺�Ʈ �κ� 
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
				orderButton.setIcon(orderButtonEnteredImage); //���콺�� �ö����� enterImage�� ����
				orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
			}
			@Override
			public void mouseExited(MouseEvent e) {
				orderButton.setIcon(orderButtonBasicImage);
				orderButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
			@Override
			///                     ///
			///"�׳� �ֹ�" Ŭ�� ��ư �̺�Ʈ ///
			///                     ///
			public void mousePressed(MouseEvent e) {
				//�ֹ��Է� ȭ�� �޼��� ex) orderScreen();
				background=new ImageIcon(Main.class.getResource("../images/white.png"))
						.getImage();
				startButton.setVisible(false); //��ư�� �Ⱥ��̰� ����
				orderButton.setVisible(false);
				
				Btn_coffee1.setBounds(10, 40, 185, 177); /// setBounds(x��ǥ, y��ǥ, xyũ��) 
				Btn_coffee1.setBorderPainted(false);
				Btn_coffee1.setContentAreaFilled(false);
				Btn_coffee1.setFocusPainted(false);
				Btn_coffee1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						Btn_coffee1.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
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
						Btn_coffee2.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
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
						Btn_coffee3.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
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
						Btn_coffee4.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
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
						Btn_coffee5.setCursor(new Cursor(Cursor.HAND_CURSOR)); //���콺�� �ö��� �� �ո������ ����
					}
				});
				add(Btn_coffee5);
				Btn_coffee5.setVisible(true);
				
				Btn_Plus.setBounds(0,80, 185, 177); 
				Btn_Plus.setBorderPainted(false);
				Btn_Plus.setContentAreaFilled(false);
				Btn_Plus.setFocusPainted(false);
				add(Btn_Plus);
				Btn_Plus.setVisible(true);
				
				Btn_OrderButton.setBounds(950,580, 397, 127); 
				Btn_OrderButton.setBorderPainted(false);
				Btn_OrderButton.setContentAreaFilled(false);
				Btn_OrderButton.setFocusPainted(false);
				add(Btn_OrderButton);
				Btn_OrderButton.setVisible(true);
				
				backButton.setVisible(true);
				backButton.setBounds(1150, 40, 120, 80);
				backButton.setBorderPainted(false); //��ư �׵θ� ����
				backButton.setContentAreaFilled(false); //��ư ���� ��� ǥ�� ����
				backButton.setFocusPainted(false); //��Ŀ�� ǥ�� ����
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
						backMain();
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
		});
		add(orderButton);
		
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
				int x=e.getXOnScreen();  //���콺�� â�� �巡���ؼ� �� ������ â�� x��ǥ ��ġ ������ ���� �ʵ�, �Ʒ� ���� y��ǥ ����
				int y=e.getYOnScreen();
				setLocation(x-mouseX,y-mouseY);
			}
		});
		add(menuBar);
	}
	public void selectSongScreen() {
		leftButton.setBounds(330, 330, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage); //���콺�� �ö����� enterImage�� ����
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
			public void mousePressed(MouseEvent e) {
				
			}
		});
		add(reserveButton);
		
		backButton.setVisible(true);
		backButton.setBounds(710, 620, 150, 80);
		backButton.setBorderPainted(false); //��ư �׵θ� ����
		backButton.setContentAreaFilled(false); //��ư ���� ��� ǥ�� ����
		backButton.setFocusPainted(false); //��Ŀ�� ǥ�� ����
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
				backMain();
			}
		});
		add(backButton);
		
		isSelectScreen=true;
		selectTrack(n);
		
		startButton.setVisible(false); 
		orderButton.setVisible(false);
		background=new ImageIcon(Main.class.getResource("../images/mainBackground.jpg"))
				.getImage();
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		reserveButton.setVisible(true);
		
	}
	public void backMain() {
		isSelectScreen=false;
		startButton.setVisible(true);
		orderButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg"))
				.getImage();
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		reserveButton.setVisible(false);
		backButton.setVisible(false);
		Btn_coffee1.setVisible(false);
		Btn_coffee2.setVisible(false);
		Btn_coffee3.setVisible(false);
		Btn_coffee4.setVisible(false);
		Btn_coffee5.setVisible(false);
		Btn_OrderButton.setVisible(false);
		backButton.setVisible(false);
		Btn_Plus.setVisible(false);
		lbl_order.setVisible(false);
		lbl_won.setVisible(false);
		lbl_price.setVisible(false);
		lbl_bar.setVisible(false);
		
	}
	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null); //���׸�
		if(isSelectScreen) {
			g.drawImage(selectedImage,400,110,null); //�ܼ��� �̹����� ȭ�鿡 ������ֱ� ���� ��� + �������� �̹����� ������ ���� �ַ� ���
		}
		paintComponents(g); // JLabel���� �������� �������� �׷��� ,add �κ�
		this.repaint(); // ��ü ȭ���� �� �������� �׷���
	}
	public void paint(Graphics g) { // ���� ù������ �ʱ� ȭ�� �׷��ִ� �Լ�
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) //�뷡�� ����ǰ� �ִٸ�
			selectedMusic.close(); //�������� �뷡 ����
		selectedImage=new ImageIcon(Main.class.getResource("../images/"+trackList.get(nowSelected).getListImage())).getImage();
		selectedMusic=new Music(trackList.get(nowSelected).getListMusic(),trackList,true);
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