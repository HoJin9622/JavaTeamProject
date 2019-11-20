package cafe;

public class UserDTO extends AdminDTO{
	protected String name;
	protected int stamp;
	//protected String pNum;
	                 
	public UserDTO(String name) {
		this.name=name; this.stamp=0;
	}
}