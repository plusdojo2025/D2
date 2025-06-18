package dto;



public class Point {
	private String user_id;       		/* ユーザーID */
	private int year;					/* 年　*/
	private int month;					/*　月　*/
	private int total_calorie_consumed;	/* 合計消費カロリー　*/
	private int total_nosmoke;			/* 合計禁煙ポイント　*/
	private int total_alcohol_consumed;	/* 合計飲酒ポイント*/
	private int total_calorie_intake;	/* 合計摂取カロリーポイント*/
	private int total_sleeptime;		/* 合計睡眠ポイント*/
	
	//ゲッターセッター
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTotal_calorie_consumed() {
		return total_calorie_consumed;
	}
	public void setTotal_calorie_consumed(int total_calorie_consumed) {
		this.total_calorie_consumed = total_calorie_consumed;
	}
	public int getTotal_nosmoke() {
		return total_nosmoke;
	}
	public void setTotal_nosmoke(int total_nosmoke) {
		this.total_nosmoke = total_nosmoke;
	}
	public int getTotal_alcohol_consumed() {
		return total_alcohol_consumed;
	}
	public void setTotal_alcohol_consumed(int total_alcohol_consumed) {
		this.total_alcohol_consumed = total_alcohol_consumed;
	}
	public int getTotal_calorie_intake() {
		return total_calorie_intake;
	}
	public void setTotal_calorie_intake(int total_calorie_intake) {
		this.total_calorie_intake = total_calorie_intake;
	}
	public int getTotal_sleeptime() {
		return total_sleeptime;
	}
	public void setTotal_sleeptime(int total_sleeptime) {
		this.total_sleeptime = total_sleeptime;
	}
	/**コンストラクタ
	 * @param user_id
	 * @param month
	 * @param year
	 * @param total_calorie_consumed
	 * @param total_nosmoke
	 * @param total_alcohol_consumed
	 * @param total_calorie_intake
	 * @param total_sleeptime
	 */
	public Point(String user_id, int month, int year, int total_calorie_consumed, int total_nosmoke,
			int total_alcohol_consumed, int total_calorie_intake, int total_sleeptime) {
		super();
		this.user_id = user_id;
		this.month = month;
		this.year = year;
		this.total_calorie_consumed = total_calorie_consumed;
		this.total_nosmoke = total_nosmoke;
		this.total_alcohol_consumed = total_alcohol_consumed;
		this.total_calorie_intake = total_calorie_intake;
		this.total_sleeptime = total_sleeptime;
	}
	
	public Point() {
		
	}
	
	
	
}