package dto;
import java.io.Serializable;

public class HealthExercise implements Serializable {
	private String userId; // ユーザーID
	private String date; // 日付
	private double calorieConsu; // 消費カロリー
	private String exerciseType; // 運動の種類
	private int exerciseTime; // 運動時間
	
	//コンストラクタ
	public HealthExercise(String userId, String date, double calorieConsu, String exerciseType, int exerciseTime) {
		super();
		this.userId = userId;
		this.date = date;
		this.calorieConsu = calorieConsu;
		this.exerciseType = exerciseType;
		this.exerciseTime = exerciseTime;
	}

	//ゲッターとセッター
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

	public double getCalorieConsu() {
		return calorieConsu;
	}

	public void setCalorieConsu(double calorieConsu) {
		this.calorieConsu = calorieConsu;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public int getExerciseTime() {
		return exerciseTime;
	}

	public void setExerciseTime(int exerciseTime) {
		this.exerciseTime = exerciseTime;
	}
	
	
}
