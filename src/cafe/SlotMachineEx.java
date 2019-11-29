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
		setTitle("슬롯머신");
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
		lblNum1.setFont(new Font("굴림", Font.BOLD, 30));
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		pNum1.add(lblNum1);

		JPanel pNum2 = new JPanel();
		pNum2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout_1 = (FlowLayout) pNum2.getLayout();
		flowLayout_1.setVgap(20);
		pCenter.add(pNum2);
		lblNum2 = new JLabel("1");
		lblNum2.setFont(new Font("굴림", Font.BOLD, 30));
		lblNum2.setHorizontalAlignment(SwingConstants.CENTER);
		pNum2.add(lblNum2);

		JPanel pNum3 = new JPanel();
		pNum3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout_2 = (FlowLayout) pNum3.getLayout();
		flowLayout_2.setVgap(20);
		pCenter.add(pNum3);
		lblNum3 = new JLabel("1");
		lblNum3.setFont(new Font("굴림", Font.BOLD, 30));
		lblNum3.setHorizontalAlignment(SwingConstants.CENTER);
		pNum3.add(lblNum3);

		btn = new JButton("Start");
		btn.setFont(new Font("굴림", Font.BOLD, 20));
		getContentPane().add(btn, BorderLayout.SOUTH);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼 텍스트 판별
				// 1. "Start" 버튼일 경우 "Start" 문자열을 "Stop" 으로 변경
				// 2. "Stop" 버튼일 경우 "Stop" 문자열을 "Start" 로 변경
				// 3개의 SlotSpin 인스턴스 생성하면서 각 JLabel 객체 전달
				SlotSpin spin1 = new SlotSpin(lblNum1);
				SlotSpin spin2 = new SlotSpin(lblNum2);
				SlotSpin spin3 = new SlotSpin(lblNum3);

				if (btn.getText().equals("Start")) {
					btn.setText("Stop");
					// 3개의 쓰레드 시작
					// stopSignal 변수값을 false 로 설정
					spin1.start();
					spin2.start();
					spin3.start();
					
					spin1.stopSignal = false;
					spin2.stopSignal = false;
					spin3.stopSignal = false;
				} else if (btn.getText().equals("Stop")) {
					btn.setText("Start");

					// 3개의 쓰레드에 정지 신호 전달을 위해 각 쓰레드 인스턴스의 stopSignal 을 true 로 변경
					spin1.stopSignal = true;
					spin2.stopSignal = true;
					spin3.stopSignal = true;
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// 슬롯 회전이 멈춘 후 숫자 일치 갯수 판별
					// => 3개가 모두 일치할 경우 "1등 당첨!" 출력
					// 단, 3개의 숫자가 모두 7일 경우 "잭팟!" 출력
					// => 2개가 일치할 경우 "2등 당첨!" 출력
					String num1 = lblNum1.getText();
					String num2 = lblNum2.getText();
					String num3 = lblNum3.getText();

					if (num1.equals("7") && num2.equals("7") && num3.equals("7")) {
						JOptionPane.showMessageDialog(rootPane, "잭팟!!!! 케이크 1개 무료입니다 ~.~\n 알림을 끄지말고 카운터로 문의해주세요.");
					} else if (num1.equals(num2) && num1.equals(num3)) {
						JOptionPane.showMessageDialog(rootPane, "1등 당첨! 음료 1개 무료입니다\n 알림을 끄지말고 카운터로 문의해주세요. ");
					} else
						JOptionPane.showMessageDialog(rootPane, "꽝입니다.");
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

	// 1개의 슬롯머신의 1개 슬롯을 구현하는 SlotSpin 클래스 정의 - Thread 클래스 상속
	// => JLabel 이나 정지신호 변수 등에 접근하기 위해 내부클래스 형태로 정의
	class SlotSpin extends Thread {
		JLabel spinNum;
		boolean stopSignal; // 각 쓰레드별로 정지 신호를 저장하는 변수

		// 발생시킨 난수를 표시하기 위해 기존에 생성된 JLabel 객체를 생성자로 전달받음
		public SlotSpin(JLabel spinNum) {
			this.spinNum = spinNum;
		}

		@Override
		public void run() {
			// 1 ~ 9 사이의 난수 1개를 생성하여, JLabel 객체에 숫자 표시
			// => boolean 타입 변수 stopSignal 이 false 일 동안 반복
			// => 단, 생성된 난수가 기존 JLabel 객체의 숫자와 동일할 경우 새로운 난수 발생
			Random r = new Random();

		
					
			while (btn.getText().equals("Stop")) { // 버튼의 텍스트가 "Stop" 일 동안 반복
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

//				System.out.println(stopSignal);
				int rNum = r.nextInt(9) + 1; // 1 ~ 9 사이의 난수 발생

				// 기존 JLabel 의 숫자와 새로 발생시킨 난수를 비교하여 다를 경우에만 표시
				if (rNum != Integer.parseInt(spinNum.getText())) {
					spinNum.setText(rNum + "");
				}
			}
			
		}

	}
}
