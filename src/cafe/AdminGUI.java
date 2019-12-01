package cafe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
class Static {
	 static int total = 0;
	 static String playingMusic;
	 static String pNum;
	 static int n;
	 static ArrayList<Track> trackListAll;
	 static JLabel songLabel;
}

public class AdminGUI extends JFrame{
	private JPanel AdminFrame = null;
	private JPanel UserFrame = null;
	private JPanel TotalFrame = null;
	private CardLayout cards = new CardLayout();
	public AdminGUI() {
		setTitle("Cafe Management System");
		setBounds(500, 300, 1280, 720);
		setLocationRelativeTo(null); // 프로그램이 정중앙에 뜸
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(cards);
		AdminFrame = new showFrame();
		TotalFrame = new TotalGUI();
		UserFrame = new UserGUI();
		add("Admin", AdminFrame);
		add("Total", TotalFrame);
		add("User", UserFrame);
		setVisible(true);
	}
	class showFrame extends JPanel  implements Cafe {
		private JTextField tfDbIp, tfDbUsername, tfIdx, tfName, tfId, tfPassword;
		private JPasswordField pfDbPassword;
		private JTable table;
		private JButton btnLogin;
		private JPanel pSouth;
		private JPanel pWest;
		JButton btnInsert;
		JButton btnSelect;
		JButton btnUpdate;
		JButton btnDelete;
		JButton btnTotal;
		JButton btnUser;
		boolean isLogin; // 로그인 여부 표시할 변수
		public showFrame() {
			setLayout(new BorderLayout());
		// ================= 상단 DB 접속 패널 ==================
		JPanel pNorth = new JPanel();
		add(pNorth, BorderLayout.PAGE_START);
		// GridLayout 으로 변경(4개의 칸 생성)
		pNorth.setLayout(new GridLayout(1, 4));

		// DB IP주소 입력 패널
		JPanel pDbIp = new JPanel();
		pNorth.add(pDbIp);

		pDbIp.add(new JLabel("IP"));
		tfDbIp = new JTextField(10);
		tfDbIp.setText("192.168.56.1"); // 임시로 IP 주소를 미리 입력
		pDbIp.add(tfDbIp);

		// DB Username 입력 패널
		JPanel pDbUsername = new JPanel();
		pNorth.add(pDbUsername);

		pDbUsername.add(new JLabel("name"));
		tfDbUsername = new JTextField(10);
		pDbUsername.add(tfDbUsername);

		// DB Password 입력 패널
		JPanel pDbPassword = new JPanel();
		pNorth.add(pDbPassword);

		pDbPassword.add(new JLabel("Password"));
		pfDbPassword = new JPasswordField(10);
		pDbPassword.add(pfDbPassword);

		// DB Login 버튼 패널
		JPanel pDbLogin = new JPanel();
		pNorth.add(pDbLogin);

		btnLogin = new JButton("로그인");
		pDbLogin.add(btnLogin);

		
		// ================= 하단 버튼 패널 ==================
		pSouth = new JPanel();
		add(pSouth, BorderLayout.PAGE_END);
	
		btnInsert = new JButton("관리자 추가");
		btnUpdate = new JButton("관리자 수정");
		btnDelete = new JButton("관리자 삭제");
		btnSelect = new JButton("관리자 목록");
		btnTotal=new JButton("매출 관리");
		btnUser=new JButton("회원 관리");
		
		pSouth.add(btnInsert);
		pSouth.add(btnUpdate);
		pSouth.add(btnDelete);
		pSouth.add(btnSelect);
		pSouth.add(btnTotal);
		pSouth.add(btnUser);
		
		btnInsert.setVisible(false);
		btnSelect.setVisible(false);
		btnUpdate.setVisible(false);
		btnDelete.setVisible(false);
		btnTotal.setVisible(false);
		btnUser.setVisible(false);
		
		// 로그인 버튼 이벤트 처리
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dbLogin();
			
				// 버튼 4개 구별하는 리스너
				ActionListener btnListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == btnInsert) {
							insert();
						} else if (e.getSource() == btnSelect) {
							select();
						} else if (e.getSource() == btnDelete) {
							delete();
						} else if (e.getSource() == btnUpdate) {
							update();
						}
					}
				};

				// 다섯 개 버튼 리스너 동시 연결
				btnInsert.addActionListener(btnListener);
				btnSelect.addActionListener(btnListener);
				btnDelete.addActionListener(btnListener);
				btnUpdate.addActionListener(btnListener);
				}
		});
		btnTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ChangePanel("Total");
			}
		});
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ChangePanel("User");
			}
		});
		// ================= 좌측 회원 정보 입력 패널 ==================
		pWest = new JPanel();
		add(pWest, BorderLayout.LINE_START);
		// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
		pWest.setLayout(new GridLayout(5, 1));

		// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
		JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pIdx);

		pIdx.add(new JLabel("번   호"));
		tfIdx = new JTextField(10);
		tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
		pIdx.add(tfIdx);

		JPanel pName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pName);
		pName.add(new JLabel("이   름"));
		tfName = new JTextField(10);
		pName.add(tfName);
		
		JPanel pId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pId);
		pId.add(new JLabel("아 이 디"));
		tfId = new JTextField(10);
		pId.add(tfId);

		JPanel pPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pPassword);
		pPassword.add(new JLabel("패스워드"));
		tfPassword = new JTextField(10);
		pPassword.add(tfPassword);

		// ================= 중앙 회원 정보 출력 패널 ==================
		// 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		// JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
		scrollPane.setViewportView(table);
		
		// 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("번호");
		columnNames.add("이름");
		columnNames.add("아이디");
		columnNames.add("패스워드");
		
//		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 셀 편집 불가능하도록 설정
			}

		};

		// JTable 에 DefaultTableModel 객체 추가
		table.setModel(dtm);

		// 테이블 내의 레코드 또는 컬럼 클릭 시 이벤트 처리
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 테이블을 마우스로 클릭할 경우 호출되는 메서드
				// 선택된 컬럼의 행, 열 번호 출력
//				System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

				// 선택된 컬럼의 데이터 출력
//				System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

				showAdminInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
			}
		});
		}
		// 로그인 기능 수행하는 dbLogin() 메서드 정의
		 void dbLogin() {
			// DB 아이디와 패스워드를 가져와서 DAO 클래스의 login() 메서드 호출
			// => 로그인 수행 결과를 리턴받아 "아이디 없음", "패스워드 틀림", "로그인 성공" 세 가지로 분류
			// 0. 로그인 버튼 vs 로그아웃 버튼 판별
			// 1. 로그인 버튼일 경우
			// 1-1. 아이디 또는 패스워드가 잘못됐을 경우 경고메세지 출력 후 해당 필드에 커서 요청
			// 1-2. 로그인 성공할 경우 "로그인" 버튼의 텍스트를 "로그아웃"으로 변경 후
			// IP, Username, Password 텍스트필드 입력 불가 설정
			// 2. 로그아웃 버튼일 경우
			// "로그아웃" 버튼 텍스트를 "로그인"으로 변경 후
			// IP, Username, Password 텍스트필드 입력 가능 설정
			
			if (!isLogin) { // 로그인 상태가 아닐 경우
				// ------------- 입력 체크 --------------
				String ip = tfDbIp.getText();
				String username = tfDbUsername.getText();
				String password = new String(pfDbPassword.getPassword());
				
				if (ip.length() == 0) {
					JOptionPane.showMessageDialog(rootPane, "DB 접속 주소 입력", "DB 정보 오류", JOptionPane.ERROR_MESSAGE);
					tfDbIp.requestFocus();
					return;
				} else if (username.length() == 0) {
					JOptionPane.showMessageDialog(rootPane, "DB 접속 계정명 입력", "DB 정보 오류", JOptionPane.ERROR_MESSAGE);
					tfDbUsername.requestFocus();
					return;
				} else if (password.length() == 0) {
					JOptionPane.showMessageDialog(rootPane, "DB 접속 암호 입력", "DB 정보 오류", JOptionPane.ERROR_MESSAGE);
					pfDbPassword.requestFocus();
					return;
				}
				AdminDTO dto = new AdminDTO(username, password);
				AdminDAO dao = AdminDAO.getInstance();
				int result = dao.login(dto);

				if (result == 0) { // 아이디가 없을 경우
					JOptionPane.showMessageDialog(rootPane, "존재하지 않는 계정입니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					tfDbUsername.requestFocus();
					return;
				} else if (result == -1) { // 패스워드가 틀렸을 경우
					JOptionPane.showMessageDialog(rootPane, "패스워드가 일치하지 않습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					pfDbPassword.requestFocus();
					return;
				}

				// 로그인 성공했을 경우
				tfDbIp.setEditable(false);
				tfDbUsername.setEditable(false);
				pfDbPassword.setEditable(false);
				btnLogin.setText("로그아웃");
				if(username.equals("admin")) {
					tfName.setVisible(true);
					tfId.setVisible(true);
					tfPassword.setVisible(true);
					
					btnInsert.setVisible(true);
					btnSelect.setVisible(true);
					btnUpdate.setVisible(true);
					btnDelete.setVisible(true);
					btnTotal.setVisible(true);
					table.setVisible(true);
					btnUser.setVisible(true);
				}else {
					btnTotal.setVisible(true);
					btnUser.setVisible(true);
				}
				isLogin = true; // 로그인 상태로 변경
			} else { // 로그인 상태일 경우(로그아웃 버튼을 클릭했을 경우)
				tfDbIp.setEditable(true);
				tfDbUsername.setEditable(true);
				pfDbPassword.setEditable(true);
				tfDbUsername.setText("");
				pfDbPassword.setText("");
				btnLogin.setText("로그인");
				btnInsert.setVisible(false);
				btnSelect.setVisible(false);
				btnUpdate.setVisible(false);
				btnDelete.setVisible(false);
				btnTotal.setVisible(false);
				table.setVisible(false);
				btnUser.setVisible(false);
				isLogin = false; // 로그아웃 상태로 변경
			}
		}

		// 관리자추가
		public void insert() {
			if (!isLogin) {
				JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				tfDbUsername.requestFocus();
				return;
			}
			
			String name = tfName.getText();
			String id = tfId.getText();
			String password = tfPassword.getText();

			// 입력 항목 체크
			if (name.length() == 0) {
				JOptionPane.showMessageDialog(rootPane, "이름 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				tfName.requestFocus();
				return;
			} else if (id.length() == 0) {
				JOptionPane.showMessageDialog(rootPane, "아이디 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				tfId.requestFocus();
				return;
			} else if (password.length() == 0) {
				JOptionPane.showMessageDialog(rootPane, "패스워드 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				tfPassword.requestFocus();
				return;
			}

			AdminDTO dto = new AdminDTO(0, name, id, password);
			AdminDAO dao = AdminDAO.getInstance();
			int result = dao.insert(dto); // 회원추가 후 결과값 리턴

			// 관리자 추가 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "관리자를 추가할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "관리자를 추가하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		// 관리자 목록
		public void select() {
//			if(!isLogin) {
//				JOptionPane.showMessageDialog(
//						rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
//				tfDbUsername.requestFocus();
//				return;
//			} 

			AdminDAO dao = AdminDAO.getInstance();
			// 관리자 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
			Vector<Vector> data = dao.select();

			DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
			// => 단, addRow() 등의 메서드가 없음

			dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

			// Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
			for (Vector rowData : data) {
				dtm.addRow(rowData);
			}

			invalidate(); // 프레임 갱신(새로 그리기)

		}

		// 관리자삭제
		public void delete() {
			if (!isLogin) {
				JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				tfDbUsername.requestFocus();
				return;
			}

			// InputDialog 사용하여 삭제할 관리자 번호 입력받기
			String idx = JOptionPane.showInputDialog(rootPane, "삭제할 관리자 번호를 입력하세요.");
//			System.out.println(idx);

			while (idx == null || idx.length() == 0) {
				// 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
				if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
					return; // 현재 메서드 종료
				}

				// 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
				JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

				// 다시 입력받기
				idx = JOptionPane.showInputDialog(rootPane, "삭제할 관리자 번호를 입력하세요.");
			}

			// 삭제할 번호를 입력할 경우
			// => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
			// => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
			if (!Pattern.matches("\\d{1,}", idx)) {
				JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			AdminDAO dao = AdminDAO.getInstance();

			int result = dao.delete(Integer.parseInt(idx));
			// 관리자 삭제 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "관리자를 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "관리자를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
			}

		}

		public void update() {
			if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
				return;
			}

			// 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 회원 정보 표시
			JFrame editFrame = new JFrame("관리자 정보 수정"); // 새 프레임 생성
			// 위치 설정 시 기존 부모 프레임의 위치 좌표 값을 받아서 사용(double타입이므로 int형 형변환)
			editFrame.setBounds((int) this.getLocation().getX(), (int) this.getLocation().getY(), 250, 300);
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 프레임만 종료

			JPanel pWest = new JPanel();
			editFrame.add(pWest, BorderLayout.CENTER);
			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
			pWest.setLayout(new GridLayout(5, 1));

			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pIdx);

			pIdx.add(new JLabel("번   호"));
			JTextField tfIdx = new JTextField(10);
			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
			pIdx.add(tfIdx);

			JPanel pName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pName);

			pName.add(new JLabel("이   름"));
			JTextField tfName = new JTextField(10);
			pName.add(tfName);

			JPanel pId = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pId);

			pId.add(new JLabel("아 이 디"));
			JTextField tfId = new JTextField(10);
			pId.add(tfId);

			JPanel pPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pPassword);

			pPassword.add(new JLabel("패스워드"));
			JTextField tfPassword = new JTextField(10);
			pPassword.add(tfPassword);

			JPanel pSouth = new JPanel();
			editFrame.add(pSouth, BorderLayout.SOUTH);

			JButton btnUpdate = new JButton("수정");
			JButton btnCancel = new JButton("취소");

			pSouth.add(btnUpdate);
			pSouth.add(btnCancel);

			// 버튼 세 개 구별하는 리스너
			ActionListener btnListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnUpdate) {

					} else if (e.getSource() == btnCancel) {

					}
				}
			};
			// 버튼 리스너 동시 연결
			btnUpdate.addActionListener(btnListener);
			btnCancel.addActionListener(btnListener);

			editFrame.setVisible(true);
		}
		public void showAdminInfo() {
			// 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
			int row = table.getSelectedRow();

			tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
			tfName.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
			tfId.setText((String) table.getValueAt(row, 2)); // Object(String) -> String 타입으로 형변환
			tfPassword.setText((String) table.getValueAt(row, 3)); // Object(String) -> String 타입으로 형변환
		}
	}

	
	///
	///매출관리 화면
	///
	
	
	public class TotalGUI extends JPanel implements Cafe {
		private JTextField tfIdx, tfDate, tfSum;
		private JTable table;
		private JPanel pSouth;
		private JPanel pWest;
		JButton btnInsert;
		JButton btnSelect;
		JButton btnUpdate;
		JButton btnDelete;
		JButton btnBack;

		public TotalGUI() {
			showFrame();
		}

		public void showFrame() {
			setLayout(new BorderLayout());
			
			// ================= 하단 버튼 패널 ==================
			pSouth = new JPanel();
			add(pSouth, BorderLayout.PAGE_END);

			btnInsert = new JButton("매출 정산");
			btnUpdate = new JButton("매출 수정");
			btnDelete = new JButton("매출 삭제");
			btnSelect = new JButton("매출 목록");
			btnBack = new JButton("뒤로 가기");

			pSouth.add(btnInsert);
			pSouth.add(btnUpdate);
			pSouth.add(btnDelete);
			pSouth.add(btnSelect);
			pSouth.add(btnBack);

			btnInsert.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					insert();
				}
			});
			btnUpdate.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					update();
				}
			});
			btnDelete.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					delete();
				}
			});
			btnSelect.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					select();
				}
			});
			btnBack.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					ChangePanel("Admin");
				}
			});

			// ================= 좌측 매출 정보 입력 패널 ==================
			pWest = new JPanel();
			add(pWest, BorderLayout.LINE_START);
			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
			pWest.setLayout(new GridLayout(5, 1));

			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pIdx);

			pIdx.add(new JLabel("번   호"));
			tfIdx = new JTextField(10);
			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
			pIdx.add(tfIdx);

			JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pDate);
			pDate.add(new JLabel("날   짜"));
			tfDate = new JTextField(10);
			pDate.add(tfDate);

			JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pSum);
			pSum.add(new JLabel("매   출"));
			tfSum = new JTextField(10);
			pSum.add(tfSum);

			// ================= 중앙 매출 정보 출력 패널 ==================
			// 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);

			// JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
			table = new JTable();
			table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
			scrollPane.setViewportView(table);

			// 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("번호");
			columnNames.add("날짜");
			columnNames.add("매출");

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // 셀 편집 불가능하도록 설정
				}

			};

			// JTable 에 DefaultTableModel 객체 추가
			table.setModel(dtm);

			// 테이블 내의 레코드 또는 컬럼 클릭 시 이벤트 처리
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// 테이블을 마우스로 클릭할 경우 호출되는 메서드
					// 선택된 컬럼의 행, 열 번호 출력
//					System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

					// 선택된 컬럼의 데이터 출력
//					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

					showTotalInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
				}
			});
			setVisible(true);
		}

		// 매출 정산
		public void insert() {

			String date = null;
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			date = format1.format(time);
			
			// 입력 항목 체크
			if (date==null) {
				JOptionPane.showMessageDialog(rootPane, "날짜 입력 오류!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			TotalDTO dto=new TotalDTO(date,Static.total);
			
			TotalDAO dao = TotalDAO.getInstance();
			int result = dao.insert(dto); // 매출정산 후 결과값 리턴

			// 매출 추가 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "매출를 정산할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "매출를 정산하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
				dto=new TotalDTO(0,date,0);
				Static.total=0;
			}
		}

		// 매출 목록 조회
		public void select() {
			TotalDAO dao = TotalDAO.getInstance();
			// 매출 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
			Vector<Vector> data = dao.select();

			DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
			// => 단, addRow() 등의 메서드가 없음

			dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

			// Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
			for (Vector rowData : data) {
				dtm.addRow(rowData);
			}
			invalidate(); // 프레임 갱신(새로 그리기)
		}

		// 매출 기록 삭제
		public void delete() {

			// InputDialog 사용하여 삭제할 매출 번호 입력받기
			String idx = JOptionPane.showInputDialog(rootPane, "삭제할 매출 번호를 입력하세요.");
//			System.out.println(idx);

			while (idx == null || idx.length() == 0) {
				// 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
				if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
					return; // 현재 메서드 종료
				}

				// 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
				JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

				// 다시 입력받기
				idx = JOptionPane.showInputDialog(rootPane, "삭제할 매출 번호를 입력하세요.");
			}

			// 삭제할 번호를 입력할 경우
			// => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
			// => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
			if (!Pattern.matches("\\d{1,}", idx)) {
				JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			TotalDAO dao = TotalDAO.getInstance();

			int result = dao.delete(Integer.parseInt(idx));
			// 매출 삭제 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "매출를 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "매출를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
			}

		}

		public void update() {
			if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
				return;
			}

			// 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 매출 정보 표시
			JFrame editFrame = new JFrame("매출 정보 수정"); // 새 프레임 생성
			// 위치 설정 시 기존 부모 프레임의 위치 좌표 값을 받아서 사용(double타입이므로 int형 형변환)
			editFrame.setBounds((int) this.getLocation().getX(), (int) this.getLocation().getY(), 250, 300);
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 프레임만 종료

			JPanel pWest = new JPanel();
			editFrame.add(pWest, BorderLayout.CENTER);
			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
			pWest.setLayout(new GridLayout(5, 1));

			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pIdx);

			pIdx.add(new JLabel("번   호"));
			JTextField tfIdx = new JTextField(10);
			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
			pIdx.add(tfIdx);

			JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pDate);

			pDate.add(new JLabel("날   짜"));
			JTextField tfDate = new JTextField(10);
			pDate.add(tfDate);

			JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pSum);

			pSum.add(new JLabel("매   출 "));
			JTextField tfSum = new JTextField(10);
			pSum.add(tfSum);

			JPanel pSouth = new JPanel();
			editFrame.add(pSouth, BorderLayout.SOUTH);

			JButton btnUpdate = new JButton("수정");
			JButton btnCancel = new JButton("취소");

			pSouth.add(btnUpdate);
			pSouth.add(btnCancel);

			// 버튼 세 개 구별하는 리스너
			ActionListener btnListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnUpdate) {

					} else if (e.getSource() == btnCancel) {
					}
				}
			};
			// 버튼 리스너 동시 연결
			btnUpdate.addActionListener(btnListener);
			btnCancel.addActionListener(btnListener);

			editFrame.setVisible(true);
		}

		public void showTotalInfo() {
			// 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
			int row = table.getSelectedRow();

			tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
			tfDate.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
			tfSum.setText((String) table.getValueAt(row, 2)); // Object(double) -> String 타입으로 형변환
		}

	}
	
	///
	///회원관리 화면
	///
	
	public class UserGUI extends JPanel implements Cafe {
		private JTextField tfIdx, tfpNum, tfPoint;
		private JTable table;
		private JPanel pSouth;
		private JPanel pWest;
		JButton btnInsert;
		JButton btnSelect;
		JButton btnUpdate;
		JButton btnDelete;
		JButton btnBack;
		public UserGUI() {
			showFrame();
		}

		public void showFrame() {
			setLayout(new BorderLayout());
			
			// ================= 하단 버튼 패널 ==================
			pSouth = new JPanel();
			add(pSouth, BorderLayout.PAGE_END);

			btnInsert = new JButton("회원 추가");
			btnUpdate = new JButton("회원 수정");
			btnDelete = new JButton("회원 삭제");
			btnSelect = new JButton("회원 목록");
			btnBack = new JButton("뒤로가기");

			pSouth.add(btnInsert);
			pSouth.add(btnUpdate);
			pSouth.add(btnDelete);
			pSouth.add(btnSelect);
			pSouth.add(btnBack);

			btnInsert.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					insert();
				}
			});
			btnUpdate.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					update();
				}
			});
			btnDelete.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					delete();
				}
			});
			btnSelect.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					select();
				}
			});
			btnBack.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					ChangePanel("Admin");
				}
			});
			// ================= 좌측 회원 정보 입력 패널 ==================
			pWest = new JPanel();
			add(pWest, BorderLayout.LINE_START);
			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
			pWest.setLayout(new GridLayout(5, 1));

			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pIdx);

			pIdx.add(new JLabel("번   호"));
			tfIdx = new JTextField(10);
			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
			pIdx.add(tfIdx);

			JPanel ppNum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(ppNum);
			ppNum.add(new JLabel("폰 번호"));
			tfpNum = new JTextField(10);
			ppNum.add(tfpNum);

			JPanel pPoint = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pPoint);
			pPoint.add(new JLabel("포 인 트"));
			tfPoint = new JTextField(10);
			pPoint.add(tfPoint);

			// ================= 중앙 회원 정보 출력 패널 ==================
			// 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);

			// JTable 객체를 생성하여 JScrollPane 의 ViewPort 영역에 부착
			table = new JTable();
			table.getTableHeader().setReorderingAllowed(false); // 셀 이동 불가 설정
			scrollPane.setViewportView(table);

			// 테이블 컬럼명 표시를 위해 Vector 객체에 컬럼명을 저장한 후 DefaultTableModel 객체에 추가
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("번호");
			columnNames.add("폰 번호");
			columnNames.add("포인트");

//			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
			DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // 셀 편집 불가능하도록 설정
				}

			};

			// JTable 에 DefaultTableModel 객체 추가
			table.setModel(dtm);

			// 테이블 내의 레코드 또는 컬럼 클릭 시 이벤트 처리
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// 테이블을 마우스로 클릭할 경우 호출되는 메서드
					// 선택된 컬럼의 행, 열 번호 출력
//					System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

					// 선택된 컬럼의 데이터 출력
//					System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

					showUserInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
				}
			});
			setVisible(true);
		}

		// 회원 및 포인트 추가
		public void insert() {
			
			// 입력 항목 체크
			if (Static.pNum==null) {
				JOptionPane.showMessageDialog(rootPane, "번호 오류!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			UserDTO dto=new UserDTO(Static.pNum);
			UserDAO dao = UserDAO.getInstance();
			
			int result = dao.insert(dto); // 회원추가 후 결과값 리턴

			// 회원 추가 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "회원을 추가할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "회원을 추가하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
				Static.pNum=null;
			}
		}

		// 회원 목록 조회
		public void select() {
			UserDAO dao = UserDAO.getInstance();
			
				
			// 회원 목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
			Vector<Vector> data = dao.select();

			DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//			 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
			// => 단, addRow() 등의 메서드가 없음

			dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

			// Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
			for (Vector rowData : data) {
				dtm.addRow(rowData);
			}
			invalidate(); // 프레임 갱신(새로 그리기)
		}

		// 회원 기록 삭제
		public void delete() {

			// InputDialog 사용하여 삭제할 회원 번호 입력받기
			String idx = JOptionPane.showInputDialog(rootPane, "삭제할 회원 번호를 입력하세요.");
//			System.out.println(idx);

			while (idx == null || idx.length() == 0) {
				// 취소(null) 클릭 또는 아무것도 입력하지 않고 확인 클릭 시
				if (idx == null) { // 취소 버튼 클릭했을 경우 아무 동작 X
					return; // 현재 메서드 종료
				}

				// 아무 번호도 입력하지 않고(널스트링값 전달) 확인 버튼 클릭했을 경우 메세지 표시
				JOptionPane.showMessageDialog(rootPane, "번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);

				// 다시 입력받기
				idx = JOptionPane.showInputDialog(rootPane, "삭제할 회원 번호를 입력하세요.");
			}

			// 삭제할 번호를 입력할 경우
			// => 정규표현식을 사용하여 번호만 입력받도록 처리할 수 있다!
			// => \\d : 숫자, {1,} : 규칙이 1번 이상 반복 => \\d{1,} : 숫자 1자리 이상
			if (!Pattern.matches("\\d{1,}", idx)) {
				JOptionPane.showMessageDialog(rootPane, "숫자 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			UserDAO dao = UserDAO.getInstance();

			int result = dao.delete(Integer.parseInt(idx));
			// 회원 삭제 여부 판별
			if (result == 0) { // 실패했을 경우
				JOptionPane.showMessageDialog(rootPane, "회원를 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
				return;
			} else { // 성공했을 경우
				JOptionPane.showMessageDialog(rootPane, "회원를 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
			}

		}

		public void update() {
			if (table.getSelectedRow() == -1) { // 테이블 셀 선택 안됐을 경우 -1 리턴됨
				return;
			}

			// 테이블 셀 선택했을 경우 창 새 프레임 생성하여 선택된 회원 정보 표시
			JFrame editFrame = new JFrame("회원 정보 수정"); // 새 프레임 생성
			// 위치 설정 시 기존 부모 프레임의 위치 좌표 값을 받아서 사용(double타입이므로 int형 형변환)
			editFrame.setBounds((int) this.getLocation().getX(), (int) this.getLocation().getY(), 250, 300);
			editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 프레임만 종료

			JPanel pWest = new JPanel();
			editFrame.add(pWest, BorderLayout.CENTER);
			// 패널 5개 행 생성 위해 GridLayout(5, 1) 설정
			pWest.setLayout(new GridLayout(5, 1));

			// 각 행별로 입력 항목에 대한 JLabel + JTextField 로 패널 구성
			JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(pIdx);

			pIdx.add(new JLabel("번   호"));
			JTextField tfIdx = new JTextField(10);
			tfIdx.setEditable(false); // 텍스트필드 편집 불가 설정
			pIdx.add(tfIdx);

			JPanel ppNum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pWest.add(ppNum);

			ppNum.add(new JLabel("폰 번 호"));
			JTextField tfpNum = new JTextField(10);
			ppNum.add(tfpNum);

			JPanel pSouth = new JPanel();
			editFrame.add(pSouth, BorderLayout.SOUTH);

			JButton btnUpdate = new JButton("수정");
			JButton btnCancel = new JButton("취소");

			pSouth.add(btnUpdate);
			pSouth.add(btnCancel);

			// 버튼 세 개 구별하는 리스너
			ActionListener btnListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnUpdate) {
						
					} else if (e.getSource() == btnCancel) {
					}
				}
			};
			// 버튼 리스너 동시 연결
			btnUpdate.addActionListener(btnListener);
			btnCancel.addActionListener(btnListener);

			editFrame.setVisible(true);
		}

		public void showUserInfo() {
			// 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
			int row = table.getSelectedRow();

			tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
			tfpNum.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
			tfPoint.setText((String) table.getValueAt(row, 2)); // Object(double) -> String 타입으로 형변환
		}
	}
	public void ChangePanel(String S) {
		cards.show(this.getContentPane(), S);
	}
	
	public static void main(String[] args) {
		new AdminGUI();
	}
}