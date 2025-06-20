package dto;
import java.io.Serializable;

public class HealthWhole implements Serializable {
	private String userId; // ユーザーID
	private String date; // 日付
	private int nosmoke; // 禁煙できたか(0か1)
	private double sleepHours; // 睡眠時間
	private int calorieIntake; // 摂取カロリー
	private String free; // 自由欄
	private double nowWeight; 
	
	//コンストラクタ
	public HealthWhole(String userId, String date, int nosmoke, double sleepHours, int calorieIntake, String free, double nowWeight) {
		super();
		this.userId = userId;
		this.date = date;
		this.nosmoke = nosmoke;
		this.sleepHours = sleepHours;
		this.calorieIntake = calorieIntake;
		this.free = free;
		this.nowWeight = nowWeight;
	}
	
	//ゲッターセッター
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNosmoke() {
		return nosmoke;
	}

	public void setNosmoke(int nosmoke) {
		this.nosmoke = nosmoke;
	}

	public double getSleepHours() {
		return sleepHours;
	}

	public void setSleepHours(double sleepHours) {
		this.sleepHours = sleepHours;
	}

	public int getCalorieIntake() {
		return calorieIntake;
	}

	public void setCalorieIntake(int calorieIntake) {
		this.calorieIntake = calorieIntake;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}
	
	public double getNowWeight() {
		return nowWeight;
	}

	public void setNowWeight(double nowWeight) {
		this.nowWeight = nowWeight;
	}
	
}
