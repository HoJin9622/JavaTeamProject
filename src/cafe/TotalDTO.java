package cafe;

public class TotalDTO {
	private int idx;
	private String date; // 날짜
	private int sum; // 총 매출액

	public TotalDTO(String date,int sum) {
		this.date=date; this.sum=sum;
	}
	
	public TotalDTO(int idx,String date,int sum) {
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
		if(date == "") {
			date = null;
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

}
