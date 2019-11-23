package cafe;

public class Menu {
	private String name;
	private int cost;
	private int count;
	
	public Menu() {
		count = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return cost;
	}
	
	public void setPrice(int cost) {
		this.cost = cost;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}