package cafe;

public class Track {
	private String listImage; //노래 선택 창 표지 이미지
	private String listMusic; //노래 선택 창 음악
	
	public Track(String listMusic) {
		this.listMusic = listMusic;
	}
	public Track(String listImage, String listMusic) {
		this.listImage = listImage;
		this.listMusic = listMusic;
	}
	public String getListImage() {
		return listImage;
	}
	public void setListImage(String listImage) {
		this.listImage = listImage;
	}
	public String getListMusic() {
		return listMusic;
	}
	public void setListMusic(String listMusic) {
		this.listMusic = listMusic;
	}
}