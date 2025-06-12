package dto;

import java.io.Serializable;

public class HealthRecord implements Serializable {
	private String exerciseKind; //運動の種類
	private int exerciseTime; // 運動時間
	private double nowWeight; //現在の体重
	private boolean isQuitSmoking; //禁煙できたか
	private double alcoholContent; //アルコール度数
	private int drinkingAmount; //お酒の摂取量
	private double sleepHours; //睡眠時間
	private int calorieIntake; //摂取カロリー
	private String remarks; // 自由欄
	
	
	
	public HealthRecord(String exerciseKind, int exerciseTime, double nowWeight, boolean isQuitSmoking,
			double alcoholContent, int drinkingAmount, double sleepHours, int calorieIntake, String remarks) {
		super();
		this.exerciseKind = exerciseKind;
		this.exerciseTime = exerciseTime;
		this.nowWeight = nowWeight;
		this.isQuitSmoking = isQuitSmoking;
		this.alcoholContent = alcoholContent;
		this.drinkingAmount = drinkingAmount;
		this.sleepHours = sleepHours;
		this.calorieIntake = calorieIntake;
		this.remarks = remarks;
	}
	
	
	//運動の種類のゲッターセッター
	public String getExerciseKind() {
		return exerciseKind;
	}
	public void setExerciseKind(String exerciseKind) {
		this.exerciseKind = exerciseKind;
	}
	
	//運動時間のゲッターセッター
	public int getExerciseTime() {
		return exerciseTime;
	}
	public void setExerciseTime(int exerciseTime) {
		this.exerciseTime = exerciseTime;
	}
	
	//現在体重のゲッターセッター「
	public double getNowWeight() {
		return nowWeight;
	}
	public void setNowWeight(double nowWeight) {
		this.nowWeight = nowWeight;
	}
	
	//禁煙できたかのゲッターセッター
	public boolean isQuitSmoking() {
		return isQuitSmoking;
	}
	public void setQuitSmoking(boolean isQuitSmoking) {
		this.isQuitSmoking = isQuitSmoking;
	}
	
	//アルコール度数のゲッターセッター
	public double getAlcoholContent() {
		return alcoholContent;
	}
	public void setAlcoholContent(double alcoholContent) {
		this.alcoholContent = alcoholContent;
	}
	
	//飲酒量のゲッターセッター
	public int getDrinkingAmount() {
		return drinkingAmount;
	}
	public void setDrinkingAmount(int drinkingAmount) {
		this.drinkingAmount = drinkingAmount;
	}
	
	//睡眠時間のゲッターセッター
	public double getSleepHours() {
		return sleepHours;
	}
	public void setSleepHours(double sleepHours) {
		this.sleepHours = sleepHours;
	}
	
	//摂取カロリーのゲッターセッター
	public int getCalorieIntake() {
		return calorieIntake;
	}
	public void setCalorieIntake(int calorieIntake) {
		this.calorieIntake = calorieIntake;
	}
	
	//自由欄のゲッターセッター
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
