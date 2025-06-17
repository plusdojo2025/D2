package dto;
import java.io.Serializable;

public class HealthAlcohol implements Serializable {
	private String userId; // ユーザーID
	private String date; // 日付
	private double pureAlcoholConsumed;// 純アルコール摂取量
	private int alcoholConsumed; // お酒の摂取量
	private double alcoholContent; // アルコール度数
	
	//コンストラクタ
	public HealthAlcohol(String userId, String date, double pureAlcoholConsumed, int alcoholConsumed,
			double alcoholContent) {
		super();
		this.userId = userId;
		this.date = date;
		this.pureAlcoholConsumed = pureAlcoholConsumed;
		this.alcoholConsumed = alcoholConsumed;
		this.alcoholContent = alcoholContent;
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

	public double getPureAlcoholConsumed() {
		return pureAlcoholConsumed;
	}

	public void setPureAlcoholConsumed(double pureAlcoholConsumed) {
		this.pureAlcoholConsumed = pureAlcoholConsumed;
	}

	public int getAlcoholConsumed() {
		return alcoholConsumed;
	}

	public void setAlcoholConsumed(int alcoholConsumed) {
		this.alcoholConsumed = alcoholConsumed;
	}

	public double getAlcoholContent() {
		return alcoholContent;
	}

	public void setAlcoholContent(double alcoholContent) {
		this.alcoholContent = alcoholContent;
	}
}
