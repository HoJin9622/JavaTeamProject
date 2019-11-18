package cafe;

public class CustomerDTO {
	// 硫ㅻ�?蹂��닔 5媛�
	// �깮�꽦�옄 - 湲곕?���깮�꽦�옄, id&password �쟾�떖諛쏅?�� �깮�꽦�옄, �쟾泥�?�� �엯�젰諛쏅?�� �깮�꽦�옄
	// Getter/Setter
	private int idx;
	private String name;
	private String id;
	private String password;
	private String jumin;
	
	public CustomerDTO() {}

	public CustomerDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public CustomerDTO(int idx, String name, String id, String password, String jumin) {
		this.idx = idx;
		this.name = name;
		this.id = id;
		this.password = password;
		this.jumin = jumin;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	
	
}
