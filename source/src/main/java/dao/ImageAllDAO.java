package dao;

import java.util.List;

import dto.Image;
import dto.TownAvatarElements;

public class ImageAllDAO {
    private ImageDAO imageDAO = new ImageDAO();

    /**
     * 街並み・アバター情報をまとめて取得する
     * 
     * @param caloriePoint ユーザーの摂取カロリーポイント
     * @param alcoholPoint ユーザーの飲酒ポイント
     * @param sleepPoint ユーザーの睡眠ポイント
     * @param noSmokeAchievementPoint 禁煙達成ポイント（人数算出用）
     * @param countryOrder 国番号
     * @return TownAvatarElements まとめた画像群
     */
    
    public TownAvatarElements select(int caloriePoint, int alcoholPoint, int sleepPoint, int noSmokeAchievementPoint, int countryOrder) {
        TownAvatarElements avatar = new TownAvatarElements();

        // 食事ポイントから服のステージ判定
        int clothStage = imageDAO.getEatStage(caloriePoint);

        // 飲酒ポイントから建物のステージ判定
        int buildingStage = imageDAO.getAlcoholStage(alcoholPoint);

        // 睡眠ポイントから顔色のステージ判定
        int faceStage = imageDAO.getSleepStage(sleepPoint);

        // 禁煙ポイントから人数を算出
        int peopleCount = imageDAO.getNoSmokingCount(noSmokeAchievementPoint);

        // 服・靴・帽子・民族衣装
        Image cloth = null;
        Image shoe = null;
        Image hat = null;
        Image costume = null;
        
        switch (clothStage) {
        case 1:
            cloth = imageDAO.getClothImage(clothStage);
            break;
        case 2:
            cloth = imageDAO.getClothImage(clothStage);
            shoe = imageDAO.getShoeImage(clothStage);
            break;
        case 3:
            cloth = imageDAO.getClothImage(clothStage);
            shoe = imageDAO.getShoeImage(clothStage);
            hat = imageDAO.getHatImage(clothStage);
            break;
        case 4:
            costume = imageDAO.getCountryCostumeImage(countryOrder);
            // 服の画像は設定しない（nullのまま）
            break;
    }

        // 建物画像リスト取得
        List<Image> buildings = imageDAO.getBuildingImages(buildingStage, countryOrder);

        // 顔色画像
        Image face = imageDAO.getFaceImage(faceStage);

        // 禁煙者画像
        Image peopleImage = imageDAO.getPeopleImage(countryOrder);

        // セット
        avatar.setCloth(cloth);
        avatar.setShoe(shoe);
        avatar.setHat(hat);
        avatar.setCostume(costume);
        avatar.setBuildings(buildings);
        avatar.setFace(face);
        avatar.setPeopleImage(peopleImage);
        avatar.setPeopleCount(peopleCount);

        return avatar;
    }
}