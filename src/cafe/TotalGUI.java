package cafe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TotalGUI extends JFrame {
	private JTextField tfDbUsername, tfIdx, tfDate, tfSum, tfPassword;
	private JPasswordField pfDbPassword;
	private JTable table;
	private JPanel pSouth;
	private JPanel pWest;
	JButton btnInsert;
	JButton btnSelect;
	JButton btnUpdate;
	JButton btnDelete;
	
	public TotalGUI() {
		showFrame();
	}
	public void showFrame() {
		setTitle("Cafe Management System");
		setBounds(500, 300, 1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// ================= �ϴ� ��ư �г� ==================
		pSouth = new JPanel();
		getContentPane().add(pSouth, BorderLayout.SOUTH);
	
		btnInsert = new JButton("���� �߰�");
		btnUpdate = new JButton("���� ����");
		btnDelete = new JButton("���� ����");
		btnSelect = new JButton("���� ���");
		
		pSouth.add(btnInsert);
		pSouth.add(btnUpdate);
		pSouth.add(btnDelete);
		pSouth.add(btnSelect);
	
		// ================= ���� ȸ�� ���� �Է� �г� ==================
		pWest = new JPanel();
		getContentPane().add(pWest, BorderLayout.WEST);
		// �г� 5�� �� ���� ���� GridLayout(5, 1) ����
		pWest.setLayout(new GridLayout(5, 1));

		// �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
		JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pIdx);

		pIdx.add(new JLabel("��   ȣ"));
		tfIdx = new JTextField(10);
		tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
		pIdx.add(tfIdx);

		JPanel pDate= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pDate);
		pDate.add(new JLabel("�� ¥"));
		tfDate = new JTextField(10);
		pDate.add(tfDate);
		
		JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pSum);
		pSum.add(new JLabel("�� ��"));
		tfSum = new JTextField(10);
		pSum.add(tfSum);
		// ================= �߾� ȸ�� ���� ��� �г� ==================
		// ��ũ�ѹ� ����� ���� JScrollPane ��ü�� �����Ͽ� Center ������ ����
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		// JTable ��ü�� �����Ͽ� JScrollPane �� ViewPort ������ ����
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false); // �� �̵� �Ұ� ����
		scrollPane.setViewportView(table);

		// ���̺� �÷��� ǥ�ø� ���� Vector ��ü�� �÷����� ������ �� DefaultTableModel ��ü�� �߰�
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("��ȣ");
		columnNames.add("��¥");
		columnNames.add("����");
		
//		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // �� ���� �Ұ����ϵ��� ����
			}

		};

		// JTable �� DefaultTableModel ��ü �߰�
		table.setModel(dtm);

		// ���̺� ���� ���ڵ� �Ǵ� �÷� Ŭ�� �� �̺�Ʈ ó��
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// ���̺��� ���콺�� Ŭ���� ��� ȣ��Ǵ� �޼���
				// ���õ� �÷��� ��, �� ��ȣ ���
//				System.out.println(table.getSelectedRow() + ", " + table.getSelectedColumn());

				// ���õ� �÷��� ������ ���
//				System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));

				showTotalInfo(); // ���õ� ���� ��� �÷� �����͸� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
			}
		});
		setVisible(true);
	}


	// �����߰�
	public void insert() {
		
		String date=null;
		double sum=0;

		// �Է� �׸� üũ
		if (date==null) {
			JOptionPane.showMessageDialog(rootPane, "��¥ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		TotalDTO dto = new TotalDTO(0, date,sum);
		TotalDAO dao = TotalDAO.getInstance();
		int result = dao.insert(dto); // ȸ���߰� �� ����� ����

		// ���� �߰� ���� �Ǻ�
		if (result == 0) { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ �߰��� �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
			return;
		} else { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ �߰��Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ���� ���
	public void select() {
//		if(!isLogin) {
//			JOptionPane.showMessageDialog(
//					rootPane, "�α��� �ʿ�", "�α��� ����", JOptionPane.ERROR_MESSAGE);
//			tfDbUsername.requestFocus();
//			return;
//		} 

		TotalDAO dao = TotalDAO.getInstance();
		// ���� ��� ��ȸ �� ��ü ���ڵ带 Vector Ÿ������ �����Ͽ� ����
		Vector<Vector> data = dao.select();

		DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // �ٿ�ĳ����
//		 TableModel tm = table.getModel() ���·ε� ��� ����(�ٿ�ĳ���� ���� ���� ���)
		// => ��, addRow() ���� �޼��尡 ����

		dtm.setRowCount(0); // ù��° ����� ���ڵ带 �߰��ؾ��ϹǷ� Ŀ���� 0�� ������ �ű�

		// Vector ��ü�� ����� ���ڵ� �� ��ŭ �ݺ��ϸ鼭 ���ڵ� �����͸� �� ��ü�� �߰�(addRow())
		for (Vector rowData : data) {
			dtm.addRow(rowData);
		}

		invalidate(); // ������ ����(���� �׸���)

	}

	// �������
	public void delete() {
	
		// InputDialog ����Ͽ� ������ ���� ��ȣ �Է¹ޱ�
		String idx = JOptionPane.showInputDialog(rootPane, "������ ���� ��ȣ�� �Է��ϼ���.");
//		System.out.println(idx);

		while (idx == null || idx.length() == 0) {
			// ���(null) Ŭ�� �Ǵ� �ƹ��͵� �Է����� �ʰ� Ȯ�� Ŭ�� ��
			if (idx == null) { // ��� ��ư Ŭ������ ��� �ƹ� ���� X
				return; // ���� �޼��� ����
			}

			// �ƹ� ��ȣ�� �Է����� �ʰ�(�ν�Ʈ���� ����) Ȯ�� ��ư Ŭ������ ��� �޼��� ǥ��
			JOptionPane.showMessageDialog(rootPane, "��ȣ �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);

			// �ٽ� �Է¹ޱ�
			idx = JOptionPane.showInputDialog(rootPane, "������ ���� ��ȣ�� �Է��ϼ���.");
		}

		// ������ ��ȣ�� �Է��� ���
		// => ����ǥ������ ����Ͽ� ��ȣ�� �Է¹޵��� ó���� �� �ִ�!
		// => \\d : ����, {1,} : ��Ģ�� 1�� �̻� �ݺ� => \\d{1,} : ���� 1�ڸ� �̻�
		if (!Pattern.matches("\\d{1,}", idx)) {
			JOptionPane.showMessageDialog(rootPane, "���� �Է� �ʼ�!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
			return;
		}

		TotalDAO dao = TotalDAO.getInstance();

		int result = dao.delete(Integer.parseInt(idx));
		// ���� ���� ���� �Ǻ�
		if (result == 0) { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
			return;
		} else { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void update() {
		if (table.getSelectedRow() == -1) { // ���̺� �� ���� �ȵ��� ��� -1 ���ϵ�
			return;
		}

		// ���̺� �� �������� ��� â �� ������ �����Ͽ� ���õ� ȸ�� ���� ǥ��
		JFrame editFrame = new JFrame("���� ���� ����"); // �� ������ ����
		// ��ġ ���� �� ���� �θ� �������� ��ġ ��ǥ ���� �޾Ƽ� ���(doubleŸ���̹Ƿ� int�� ����ȯ)
		editFrame.setBounds((int) this.getLocation().getX(), (int) this.getLocation().getY(), 250, 300);
		editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ���� �����Ӹ� ����

		JPanel pWest = new JPanel();
		editFrame.add(pWest, BorderLayout.CENTER);
		// �г� 5�� �� ���� ���� GridLayout(5, 1) ����
		pWest.setLayout(new GridLayout(5, 1));

		// �� �ະ�� �Է� �׸� ���� JLabel + JTextField �� �г� ����
		JPanel pIdx = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pIdx);

		pIdx.add(new JLabel("��   ȣ"));
		JTextField tfIdx = new JTextField(10);
		tfIdx.setEditable(false); // �ؽ�Ʈ�ʵ� ���� �Ұ� ����
		pIdx.add(tfIdx);

		JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pDate);

		pDate.add(new JLabel("��¥"));
		JTextField tfName = new JTextField(10);
		pDate.add(tfName);

		JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pSum);

		pSum.add(new JLabel("�� �� ��"));
		JTextField tfSum = new JTextField(10);
		pSum.add(tfSum);

		JPanel pSouth = new JPanel();
		editFrame.add(pSouth, BorderLayout.SOUTH);

		JButton btnUpdate = new JButton("����");
		JButton btnCancel = new JButton("���");

		pSouth.add(btnUpdate);
		pSouth.add(btnCancel);

		// ��ư �� �� �����ϴ� ������
		ActionListener btnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnUpdate) {

				} else if (e.getSource() == btnCancel) {

				}
			}
		};
		// ��ư ������ ���� ����
		btnUpdate.addActionListener(btnListener);
		btnCancel.addActionListener(btnListener);

		editFrame.setVisible(true);
	}

	public void showTotalInfo() {
		// Ŭ���� �࿡ ���� ��� ���� �����ͼ� ���� WEST ���� �ؽ�Ʈ�ʵ忡 ǥ��
		int row = table.getSelectedRow();

		tfIdx.setText(table.getValueAt(row, 0) + ""); // Object(int) -> String Ÿ������ ����ȯ
		tfDate.setText(table.getValueAt(row, 1).toString()); // Object(String) -> String Ÿ������ ����ȯ
		tfSum.setText((String) table.getValueAt(row, 2)); // Object(double) -> String Ÿ������ ����ȯ
}

	public static void main(String[] args) {
		new TotalGUI();
	}
}