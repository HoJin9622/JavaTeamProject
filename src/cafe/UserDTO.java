package cafe;

public class UserDTO {
	private int idx;
	private String pNum;  // ȸ�� ��ȭ��ȣ
	private int point=0; // ȸ�� ����Ʈ
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getpNum() {
		return pNum;
	}

	public void setpNum(String pNum) {
		this.pNum = pNum;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public UserDTO(String pNum) {
		this.idx=0; this.pNum=pNum; 
	}
}