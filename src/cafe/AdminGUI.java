package cafe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableModel;

public class CustomerGUI extends JFrame {
	JTextField tfDbIp, tfDbUsername, tfIdx, tfName, tfId, tfPassword, tfJumin;
	JPasswordField pfDbPassword;
	JTable table;
	JButton btnLogin;

	boolean isLogin; // 로그인 여부 표시할 변수

	public CustomerGUI() {
		showFrame();
	}

	public void showFrame() {
		setTitle("고객관리 프로그램");
		setBounds(500, 300, 800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ================= 상단 DB 접속 패널 ==================
		JPanel pNorth = new JPanel();
		getContentPane().add(pNorth, BorderLayout.NORTH);
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

		pDbUsername.add(new JLabel("Username"));
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

		// 로그인 버튼 이벤트 처리
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dbLogin();
			}
		});

		// ================= 좌측 회원 정보 입력 패널 ==================
		JPanel pWest = new JPanel();
		getContentPane().add(pWest, BorderLayout.WEST);
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

		JPanel pJumin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pJumin);

		pJumin.add(new JLabel("주민번호"));
		tfJumin = new JTextField(10);
		pJumin.add(tfJumin);

		// ================= 중앙 회원 정보 출력 패널 ==================
		// 스크롤바 기능을 위해 JScrollPane 객체를 생성하여 Center 영역에 부착
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

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
		columnNames.add("주민번호");

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

				showCustomerInfo(); // 선택된 행의 모든 컬럼 데이터를 WEST 영역 텍스트필드에 표시
			}
		});

		// ================= 하단 버튼 패널 ==================
		JPanel pSouth = new JPanel();
		getContentPane().add(pSouth, BorderLayout.SOUTH);

		JButton btnInsert = new JButton("관리자 추가");
		JButton btnSelect = new JButton("관리자 목록");
		JButton btnDelete = new JButton("관리자 삭제");
		JButton btnUpdate = new JButton("관리자 수정");

		JButton startAdminSystem = new JButton("주문 시스템 실행");
		startAdminSystem.setBounds(100, 100, 50, 50);
		startAdminSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new OrderSystem();
			}
		});

		pSouth.add(startAdminSystem);
		pSouth.add(btnInsert);
		pSouth.add(btnSelect);
		pSouth.add(btnDelete);
		pSouth.add(btnUpdate);

		// 버튼 세 개 구별하는 리스너
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

		// 세 개 버튼 리스너 동시 연결
		btnInsert.addActionListener(btnListener);
		btnSelect.addActionListener(btnListener);
		btnDelete.addActionListener(btnListener);
		btnUpdate.addActionListener(btnListener);

		setVisible(true);

	}

	// 로그인 기능 수행하는 dbLogin() 메서드 정의
	public void dbLogin() {
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

			CustomerDTO dto = new CustomerDTO(username, password);
			CustomerDAO dao = CustomerDAO.getInstance();
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

			isLogin = true; // 로그인 상태로 변경
		} else { // 로그인 상태일 경우(로그아웃 버튼을 클릭했을 경우)
			tfDbIp.setEditable(true);
			tfDbUsername.setEditable(true);
			pfDbPassword.setEditable(true);
			tfDbUsername.setText("");
			pfDbPassword.setText("");
			btnLogin.setText("로그인");

			isLogin = false; // 로그아웃 상태로 변경
		}

	}

	// 회원추가
	public void insert() {
		if (!isLogin) {
			JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
			tfDbUsername.requestFocus();
			return;
		}

		String name = tfName.getText();
		String id = tfId.getText();
		String password = tfPassword.getText();
		String jumin = tfJumin.getText();

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
		} else if (jumin.length() == 0) {
			JOptionPane.showMessageDialog(rootPane, "주민번호 입력 필수!", "입력 오류", JOptionPane.ERROR_MESSAGE);
			tfJumin.requestFocus();
			return;
		}

		CustomerDTO dto = new CustomerDTO(0, name, id, password, jumin);
		CustomerDAO dao = CustomerDAO.getInstance();
		int result = dao.insert(dto); // 회원추가 후 결과값 리턴

		// 회원 추가 여부 판별
		if (result == 0) { // 실패했을 경우
			JOptionPane.showMessageDialog(rootPane, "회원을 추가할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
			return;
		} else { // 성공했을 경우
			JOptionPane.showMessageDialog(rootPane, "회원을 추가하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	// 회원목록
	public void select() {
//		if(!isLogin) {
//			JOptionPane.showMessageDialog(
//					rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
//			tfDbUsername.requestFocus();
//			return;
//		} 

		CustomerDAO dao = CustomerDAO.getInstance();
		// 회원목록 조회 후 전체 레코드를 Vector 타입으로 저장하여 리턴
		Vector<Vector> data = dao.select();

		DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // 다운캐스팅
//		 TableModel tm = table.getModel() 형태로도 사용 가능(다운캐스팅 하지 않을 경우)
		// => 단, addRow() 등의 메서드가 없음

		dtm.setRowCount(0); // 첫번째 행부터 레코드를 추가해야하므로 커서를 0번 행으로 옮김

		// Vector 객체에 저장된 레코드 수 만큼 반복하면서 레코드 데이터를 모델 객체에 추가(addRow())
		for (Vector rowData : data) {
			dtm.addRow(rowData);
		}

		invalidate(); // 프레임 갱신(새로 그리기)

	}

	// 회원삭제
	public void delete() {
		if (!isLogin) {
			JOptionPane.showMessageDialog(rootPane, "로그인 필요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
			tfDbUsername.requestFocus();
			return;
		}

		// InputDialog 사용하여 삭제할 회원번호 입력받기
		String idx = JOptionPane.showInputDialog(rootPane, "삭제할 회원 번호를 입력하세요.");
//		System.out.println(idx);

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

		CustomerDAO dao = CustomerDAO.getInstance();

		int result = dao.delete(Integer.parseInt(idx));
		// 회원 삭제 여부 판별
		if (result == 0) { // 실패했을 경우
			JOptionPane.showMessageDialog(rootPane, "회원을 삭제할 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
			return;
		} else { // 성공했을 경우
			JOptionPane.showMessageDialog(rootPane, "회원을 삭제하였습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
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

		JPanel pJumin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pJumin);

		pJumin.add(new JLabel("주민번호"));
		JTextField tfJumin = new JTextField(10);
		pJumin.add(tfJumin);

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

	public void showCustomerInfo() {
		// 클릭한 행에 대한 모든 정보 가져와서 좌측 WEST 영역 텍스트필드에 표시
		int row = table.getSelectedRow();

		tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String 타입으로 형변환
		tfName.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String 타입으로 형변환
		tfId.setText((String) table.getValueAt(row, 2)); // Object(String) -> String 타입으로 형변환
		tfPassword.setText((String) table.getValueAt(row, 3)); // Object(String) -> String 타입으로 형변환
		tfJumin.setText((String) table.getValueAt(row, 4)); // Object(String) -> String 타입으로 형변환
	}

	public static void main(String[] args) {
		new CustomerGUI();
	}
}