package dto;

import java.io.Serializable;

public class TargetValue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String user_id;
    private int month;
    private float pure_alcohol_consumed;
    private float sleep_time;
    private int calorie_intake;
    private float target_weight;

    // コンストラクタ
    public TargetValue(String user_id, int month, float pure_alcohol_consumed, float sleep_time, 
    		int calorie_intake, float target_weight) {
        this.user_id = user_id;
        this.month = month;
        this.pure_alcohol_consumed = pure_alcohol_consumed;
        this.sleep_time = sleep_time;
        this.calorie_intake = calorie_intake;
        this.target_weight = target_weight;
    }

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

	public float getPure_alcohol_consumed() {
		return pure_alcohol_consumed;
	}

	public void setPure_alcohol_consumed(float pure_alcohol_consumed) {
		this.pure_alcohol_consumed = pure_alcohol_consumed;
	}

	public float getSleep_time() {
		return sleep_time;
	}

	public void setSleep_time(float sleep_time) {
		this.sleep_time = sleep_time;
	}

	public int getCalorie_intake() {
		return calorie_intake;
	}

	public void setCalorie_intake(int calorie_intake) {
		this.calorie_intake = calorie_intake;
	}

	public float getTarget_weight() {
		return target_weight;
	}

	public void setTarget_weight(float target_weight) {
		this.target_weight = target_weight;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
}