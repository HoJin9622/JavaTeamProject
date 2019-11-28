package cafe;

public class AdminDTO {
	// Getter/Setter
	private int idx;
	private String name;
	private String id;
	private String password;
	
	public AdminDTO() {}
	public AdminDTO(int idx) {this.idx=idx;}
	public AdminDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}
	public AdminDTO(int idx, String name, String id, String password) {
		this.idx = idx;
		this.name = name;
		this.id = id;
		this.password = password;
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
}