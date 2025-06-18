package dto;

public class AchievementPoint {
	// ステージと達成ポイントのDTO
	private int stage;
	private int achievementPoint;
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getAchievementPoint() {
		return achievementPoint;
	}
	public void setAchievementPoint(int achievementPoint) {
		this.achievementPoint = achievementPoint;
	}
	
	public AchievementPoint() {
		
	}
	public AchievementPoint(int stage, int achievementPoint) {
		this.stage = stage;
		this.achievementPoint = achievementPoint;
	}
}
