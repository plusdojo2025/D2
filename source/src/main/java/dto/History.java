package dto;

public class History{
	 private String userId;
    private int year;
    private int month;  
	private String filePath;

	public String getUserId() { return userId; }
    public void setId(String userId) { this.userId = userId; }
    public String getFileName() { return filePath; }
    public void setFileName(String filePath) { this.filePath = filePath; }
    public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
    
    public History() {
    	
    }
    
    public History(String userId, int year, int month, String filePath) {
    	this.userId= userId;
    	this.year = year;
    	this.month = month;
    	this.filePath = filePath;
	}
    
}