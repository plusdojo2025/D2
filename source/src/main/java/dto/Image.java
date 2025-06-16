package dto;

public class Image {
  
    private String imagePath;

    // コンストラクタ
    public Image( String imagePath) {

        this.imagePath = imagePath;
    }

	

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
    
    
}