package cafe;

public class UserDTO extends AdminDTO{
	private String pNum;  // ȸ�� ��ȭ��ȣ
	private double point; // ȸ�� ����Ʈ
	
	public UserDTO(int idx,String pNum) {
		super(idx);
		this.pNum=pNum; this.point=0.0;
	}
}