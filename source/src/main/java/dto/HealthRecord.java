package dto;

import java.io.Serializable;

public class HealthRecord implements Serializable {
	private String userId; // ユーザーID
	private String date; // 日付
	private String exerciseType; // 運動の種類
	private int exerciseTime; // 運動時間
	private double nowWeight; // 現在の体重
	private double calorieConsu; // 消費カロリー
	private int nosmoke; // 禁煙できたか(0か1)
	private double alcoholContent; // アルコール度数
	private int alcoholConsumed; // お酒の摂取量
	private double pureAlcoholConsumed;// 純アルコール摂取量
	private double sleepHours; // 睡眠時間
	private int calorieIntake; // 摂取カロリー
	private String free; // 自由欄

	// コンストラクタ
	public HealthRecord(String userId, String date, String exerciseType, int exerciseTime, double nowWeight,
			double calorieConsu, int nosmoke, double alcoholContent, int alcoholConsumed,
			double pureAlcoholConsumed, double sleepHours, int calorieIntake, String free) {
		super();
		this.userId = userId;
		this.date = date;
		this.exerciseType = exerciseType;
		this.exerciseTime = exerciseTime;
		this.nowWeight = nowWeight;
		this.calorieConsu = calorieConsu;
		this.nosmoke = nosmoke;
		this.alcoholContent = alcoholContent;
		this.alcoholConsumed = alcoholConsumed;
		this.pureAlcoholConsumed = pureAlcoholConsumed;
		this.sleepHours = sleepHours;
		this.calorieIntake = calorieIntake;
		this.free = free;
	}

	// ユーザーIDのゲッターセッター
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// 日付のゲッターセッター
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// 運動の種類のゲッターセッター
	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	// 運動時間のゲッターセッター
	public int getExerciseTime() {
		return exerciseTime;
	}

	public void setExerciseTime(int exerciseTime) {
		this.exerciseTime = exerciseTime;
	}

	// 現在体重のゲッターセッター「
	public double getNowWeight() {
		return nowWeight;
	}

	public void setNowWeight(double nowWeight) {
		this.nowWeight = nowWeight;
	}

	// 消費カロリーのゲッターセッター
	public double getCalorieConsu() {
		return calorieConsu;
	}

	public void setCalorieConsu(double calorieConsu) {
		this.calorieConsu = calorieConsu;
	}

	// 禁煙できたかのゲッターセッター
	public int getNosmoke() {
		return nosmoke;
	}

	public void setNosmoke(int nosmoke) {
		this.nosmoke = nosmoke;
	}

	// アルコール度数のゲッターセッター
	public double getAlcoholContent() {
		return alcoholContent;
	}

	public void setAlcoholContent(double alcoholContent) {
		this.alcoholContent = alcoholContent;
	}

	// 飲酒量のゲッターセッター
	public int getAlcoholConsumed() {
		return alcoholConsumed;
	}

	public void setAlcoholConsumed(int alcoholConsumed) {
		this.alcoholConsumed = alcoholConsumed;
	}

	// 純アルコール摂取量のゲッターセッター
	public double getPureAlcoholConsumed() {
		return pureAlcoholConsumed;
	}

	public void setPureAlcoholConsumed(double pureAlcoholConsumed) {
		this.pureAlcoholConsumed = pureAlcoholConsumed;
	}

	// 睡眠時間のゲッターセッター
	public double getSleepHours() {
		return sleepHours;
	}

	public void setSleepHours(double sleepHours) {
		this.sleepHours = sleepHours;
	}

	// 摂取カロリーのゲッターセッター
	public int getCalorieIntake() {
		return calorieIntake;
	}

	public void setCalorieIntake(int calorieIntake) {
		this.calorieIntake = calorieIntake;
	}

	// 自由欄のゲッターセッター
	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

}
