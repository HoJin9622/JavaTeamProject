package cafe;

public class UserDTO extends AdminDTO{
	private String pNum;  // 회원 전화번호
	private double point; // 회원 포인트
	
	public UserDTO(int idx,String pNum) {
		super(idx);
		this.pNum=pNum; this.point=0.0;
	}
}