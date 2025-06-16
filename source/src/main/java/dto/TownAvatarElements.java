package dto;

import java.util.List;

public class TownAvatarElements {
	private List<String> imgPathSetBuild;
	private List<String> imgPathSetCloth;
	private String imgPathFace;
	private String imgPathPeople;
	private int peopleNum;
	
	public List<String> getImgPathSetBuild() {
		return imgPathSetBuild;
	}
	public void setImgPathSetBuild(List<String> imgPathSetBuild) {
		this.imgPathSetBuild = imgPathSetBuild;
	}
	public List<String> getImgPathSetCloth() {
		return imgPathSetCloth;
	}
	public void setImgPathSetCloth(List<String> imgPathSetCloth) {
		this.imgPathSetCloth = imgPathSetCloth;
	}
	public String getImgPathFace() {
		return imgPathFace;
	}
	public void setImgPathFace(String imgPathFace) {
		this.imgPathFace = imgPathFace;
	}
	public String getImgPathPeople() {
		return imgPathPeople;
	}
	public void setImgPathPeople(String imgPathPeople) {
		this.imgPathPeople = imgPathPeople;
	}
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	
	public TownAvatarElements(
			List<String> imgPathSetBuild, 
			List<String> imgPathSetCloth, 
			String imgPathFace,
			String imgPathPeople,
			int peopleNum) {
		this.imgPathSetBuild = imgPathSetBuild;
		this.imgPathSetCloth = imgPathSetCloth;
		this.imgPathFace = imgPathFace;
		this.imgPathPeople = imgPathPeople;
		this.peopleNum = peopleNum;		
	}
	
}
