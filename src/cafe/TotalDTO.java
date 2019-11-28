package cafe;

public class TotalDTO {
	private int idx;
	private String date; // 날짜
	private double sum; // 총 매출액

	public TotalDTO(double sum) {
		this.sum=sum;
	}
	
	public TotalDTO(int idx,String date,double sum) {
		this.idx=idx;
		this.sum = sum;
		this.date = date;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
