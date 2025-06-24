package dto;

import java.util.List;

public class TownAvatarElements {
    private Image cloth;
    private Image shoe;
    private Image hat;
    private Image costume;
    private List<Image> buildings;
    private Image face;
    private Image peopleImage;
    private int peopleCount;
    private int year;
    private int month;

	// ゲッター・セッター
    public int getYear() {
    	return year;
    }
    public void setYear(int year) {
    	this.year = year;
    }
    public int getMonth() {
    	return month;
    }
    public void setMonth(int month) {
    	this.month = month;
    }
    public Image getCloth() { return cloth; }
    public void setCloth(Image cloth) { this.cloth = cloth; }

    public Image getShoe() { return shoe; }
    public void setShoe(Image shoe) { this.shoe = shoe; }

    public Image getHat() { return hat; }
    public void setHat(Image hat) { this.hat = hat; }

    public Image getCostume() { return costume; }
    public void setCostume(Image costume) { this.costume = costume; }

    public List<Image> getBuildings() { return buildings; }
    public void setBuildings(List<Image> buildings) { this.buildings = buildings; }

    public Image getFace() { return face; }
    public void setFace(Image face) { this.face = face; }

    public Image getPeopleImage() { return peopleImage; }
    public void setPeopleImage(Image peopleImage) { this.peopleImage = peopleImage; }

    public int getPeopleCount() { return peopleCount; }
    public void setPeopleCount(int peopleCount) { this.peopleCount = peopleCount; }
}

