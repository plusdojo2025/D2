package dto;

import java.sql.Date;

public class RewardDay {
	private String userId; /* ユーザーID */
	private Date date; /* 日付 */
	private String rewardExplain; /* 報酬説明 */

//	ゲッターセッター
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRewardExplain() {
		return rewardExplain;
	}

	public void setRewardExplain(String rewardExplain) {
		this.rewardExplain = rewardExplain;
	}

	// コンストラクタ
	public RewardDay(String userId, Date date, String rewardExplain) {
		super();
		this.userId = userId;
		this.date = date;
		this.rewardExplain = rewardExplain;
	}

	public RewardDay() {

		this.userId = "";
		this.date = null;
		this.rewardExplain = "";
	}
}
