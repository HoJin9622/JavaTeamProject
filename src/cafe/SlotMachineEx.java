package cafe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

public class SlotMachineEx extends JFrame {
	private JLabel lblNum1, lblNum2, lblNum3;
	private JButton btn;

	public SlotMachineEx() {
		showFrame();
	}

	public void showFrame() {
		setTitle("���Ըӽ�");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 400, 300, 150);

		GridLayout gl_pCenter = new GridLayout(1, 3);
		JPanel pCenter = new JPanel(gl_pCenter);
		pCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		getContentPane().add(pCenter, BorderLayout.CENTER);

		JPanel pNum1 = new JPanel();
		pNum1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout = (FlowLayout) pNum1.getLayout();
		flowLayout.setVgap(20);
		pCenter.add(pNum1);
		lblNum1 = new JLabel("1");
		lblNum1.setFont(new Font("����", Font.BOLD, 30));
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		pNum1.add(lblNum1);

		JPanel pNum2 = new JPanel();
		pNum2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout_1 = (FlowLayout) pNum2.getLayout();
		flowLayout_1.setVgap(20);
		pCenter.add(pNum2);
		lblNum2 = new JLabel("1");
		lblNum2.setFont(new Font("����", Font.BOLD, 30));
		lblNum2.setHorizontalAlignment(SwingConstants.CENTER);
		pNum2.add(lblNum2);

		JPanel pNum3 = new JPanel();
		pNum3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout_2 = (FlowLayout) pNum3.getLayout();
		flowLayout_2.setVgap(20);
		pCenter.add(pNum3);
		lblNum3 = new JLabel("1");
		lblNum3.setFont(new Font("����", Font.BOLD, 30));
		lblNum3.setHorizontalAlignment(SwingConstants.CENTER);
		pNum3.add(lblNum3);

		btn = new JButton("Start");
		btn.setFont(new Font("����", Font.BOLD, 20));
		getContentPane().add(btn, BorderLayout.SOUTH);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ư �ؽ�Ʈ �Ǻ�
				// 1. "Start" ��ư�� ��� "Start" ���ڿ��� "Stop" ���� ����
				// 2. "Stop" ��ư�� ��� "Stop" ���ڿ��� "Start" �� ����
				// 3���� SlotSpin �ν��Ͻ� �����ϸ鼭 �� JLabel ��ü ����
				SlotSpin spin1 = new SlotSpin(lblNum1);
				SlotSpin spin2 = new SlotSpin(lblNum2);
				SlotSpin spin3 = new SlotSpin(lblNum3);

				if (btn.getText().equals("Start")) {
					btn.setText("Stop");
					// 3���� ������ ����
					// stopSignal �������� false �� ����
					spin1.start();
					spin2.start();
					spin3.start();
					
					spin1.stopSignal = false;
					spin2.stopSignal = false;
					spin3.stopSignal = false;
				} else if (btn.getText().equals("Stop")) {
					btn.setText("Start");

					// 3���� �����忡 ���� ��ȣ ������ ���� �� ������ �ν��Ͻ��� stopSignal �� true �� ����
					spin1.stopSignal = true;
					spin2.stopSignal = true;
					spin3.stopSignal = true;
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// ���� ȸ���� ���� �� ���� ��ġ ���� �Ǻ�
					// => 3���� ��� ��ġ�� ��� "1�� ��÷!" ���
					// ��, 3���� ���ڰ� ��� 7�� ��� "����!" ���
					// => 2���� ��ġ�� ��� "2�� ��÷!" ���
					String num1 = lblNum1.getText();
					String num2 = lblNum2.getText();
					String num3 = lblNum3.getText();

					if (num1.equals("7") && num2.equals("7") && num3.equals("7")) {
						JOptionPane.showMessageDialog(rootPane, "����!!!! ����ũ 1�� �����Դϴ� ~.~\n �˸��� �������� ī���ͷ� �������ּ���.");
					} else if (num1.equals(num2) && num1.equals(num3)) {
						JOptionPane.showMessageDialog(rootPane, "1�� ��÷! ���� 1�� �����Դϴ�\n �˸��� �������� ī���ͷ� �������ּ���. ");
					} else
						JOptionPane.showMessageDialog(rootPane, "���Դϴ�.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					setVisible(false);
				}
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new SlotMachineEx();
	}

	// 1���� ���Ըӽ��� 1�� ������ �����ϴ� SlotSpin Ŭ���� ���� - Thread Ŭ���� ���
	// => JLabel �̳� ������ȣ ���� � �����ϱ� ���� ����Ŭ���� ���·� ����
	class SlotSpin extends Thread {
		JLabel spinNum;
		boolean stopSignal; // �� �����庰�� ���� ��ȣ�� �����ϴ� ����

		// �߻���Ų ������ ǥ���ϱ� ���� ������ ������ JLabel ��ü�� �����ڷ� ���޹���
		public SlotSpin(JLabel spinNum) {
			this.spinNum = spinNum;
		}

		@Override
		public void run() {
			// 1 ~ 9 ������ ���� 1���� �����Ͽ�, JLabel ��ü�� ���� ǥ��
			// => boolean Ÿ�� ���� stopSignal �� false �� ���� �ݺ�
			// => ��, ������ ������ ���� JLabel ��ü�� ���ڿ� ������ ��� ���ο� ���� �߻�
			Random r = new Random();

		
					
			while (btn.getText().equals("Stop")) { // ��ư�� �ؽ�Ʈ�� "Stop" �� ���� �ݺ�
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

//				System.out.println(stopSignal);
				int rNum = r.nextInt(9) + 1; // 1 ~ 9 ������ ���� �߻�

				// ���� JLabel �� ���ڿ� ���� �߻���Ų ������ ���Ͽ� �ٸ� ��쿡�� ǥ��
				if (rNum != Integer.parseInt(spinNum.getText())) {
					spinNum.setText(rNum + "");
				}
			}
			
		}

	}
}
