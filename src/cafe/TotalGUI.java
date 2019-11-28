package cafe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import cafe.TotalDTO;
public class TotalGUI extends JFrame {
	private JTextField tfDbUsername, tfIdx, tfDate, tfSum;
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

		btnInsert = new JButton("���� ����");
		btnUpdate = new JButton("���� ����");
		btnDelete = new JButton("���� ����");
		btnSelect = new JButton("���� ���");

		pSouth.add(btnInsert);
		pSouth.add(btnUpdate);
		pSouth.add(btnDelete);
		pSouth.add(btnSelect);

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

		// ================= ���� ���� ���� �Է� �г� ==================
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

		JPanel pDate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pDate);
		pDate.add(new JLabel("��   ¥"));
		tfDate = new JTextField(10);
		pDate.add(tfDate);

		JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pSum);
		pSum.add(new JLabel("��   ��"));
		tfSum = new JTextField(10);
		pSum.add(tfSum);

		// ================= �߾� ���� ���� ��� �г� ==================
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

	// ���� ����
	public void insert(TotalDTO dto) {

		String date = null;
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		date = format1.format(time);
		
		// �Է� �׸� üũ
		if (date==null) {
			JOptionPane.showMessageDialog(rootPane, "��¥ �Է� ����!", "�Է� ����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		dto.setDate(date); 
		TotalDAO dao = TotalDAO.getInstance();
		int result = dao.insert(dto); // �������� �� ����� ����

		// ���� �߰� ���� �Ǻ�
		if (result == 0) { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ ������ �� �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
			return;
		} else { // �������� ���
			JOptionPane.showMessageDialog(rootPane, "���⸦ �����Ͽ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
			dto=new TotalDTO(0,date,0);
		}
	}

	// ���� ��� ��ȸ
	public void select() {
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

	// ���� ��� ����
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

		// ���̺� �� �������� ��� â �� ������ �����Ͽ� ���õ� ���� ���� ǥ��
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

		pDate.add(new JLabel("��   ¥"));
		JTextField tfDate = new JTextField(10);
		pDate.add(tfDate);

		JPanel pSum = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pWest.add(pSum);

		pSum.add(new JLabel("��   �� "));
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