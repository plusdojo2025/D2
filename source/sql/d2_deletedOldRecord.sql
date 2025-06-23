drop database d2;
create database d2;
use d2

-- -------------------------------------------
-- 活動レベル
CREATE TABLE act_level(
    id INT PRIMARY KEY AUTO_INCREMENT,
    value FLOAT,
    explanation VARCHAR(20)
);

INSERT INTO act_level(value, explanation) VALUES 
    (1.2, "非常に低い（デスクワーク中心）"), 
    (1.375, "低い（軽い日常活動）"), 
    (1.55, "中程度（週3〜5回運動）"), 
    (1.725, "高い（毎日運動・肉体労働）"), 
    (1.9, "非常に高い（アスリートレベル）"); 

SELECT * FROM act_level;

-- -------------------------------------------
-- 世界旅行
CREATE TABLE world_tour(
    country_order INT PRIMARY KEY,
    country VARCHAR(20),
    necessary_calorie INT
);

INSERT INTO world_tour VALUES 
    (0, null, 0),
    (1, "日本", 0), 
    (2, "オーストラリア", 3600),
    (3, "シンガポール", 7200),
    (4, "インド", 10800),
    (5, "中国", 14400),
    (6, "韓国", 18000),
    (7, "モンゴル", 21600),
    (8, "ロシア", 25200),
    (9, "フランス", 28800),
    (10, "イギリス", 32400),
    (11, "イタリア", 36000),
    (12, "エジプト", 39600),
    (13, "マダガスカル", 43200),
    (14, "ブラジル", 46800),
    (15, "メキシコ", 50400),
    (16, "アメリカ", 54000),
    (17, "宇宙", 57600);

SELECT * FROM world_tour;

-- -------------------------------------------
-- ユーザー
CREATE TABLE user(
    id VARCHAR(20) PRIMARY KEY, 
    pw VARCHAR(20),
    weight FLOAT,
    height FLOAT,
    sex CHAR(1),
    age INT,
    act_level_id INT DEFAULT 1,
    reach_point INT DEFAULT 1,
    FOREIGN KEY(act_level_id) REFERENCES act_level(id),
    FOREIGN KEY(reach_point) REFERENCES world_tour(country_order)
);

INSERT INTO user(id, pw) VALUES 
    ("kazutoshi_t", "password"), 
    ("test1", "test1"),
    ("dojouser1", "#SEplus2025SEplus"),
    ("dojouser2", "#SEplus2025SEplus"),
    ("dojouser3", "#SEplus2025SEplus"),
    ("dojouser4", "#SEplus2025SEplus"),
    ("dojouser5", "#SEplus2025SEplus");

SELECT * FROM user;

-- TODO:update
-- UPDATE user SET weight=70.0, height=170.0, sex="男", age=53, act_level_id=2 WHERE id="kazutoshi_t";

-- -------------------------------------------
-- 目標値
CREATE TABLE target_value(
    user_id VARCHAR(20) PRIMARY KEY, 
    month INT,
    pure_alcohol_consumed FLOAT,
    sleep_time FLOAT,
    calorie_intake INT,
    target_weight FLOAT,
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO target_value(user_id) VALUES ("kazutoshi_t"),("test1");

UPDATE target_value SET month=5, pure_alcohol_consumed=50, sleep_time=8, calorie_intake=3000, target_weight=60 WHERE user_id="kazutoshi_t";

SELECT * FROM target_value;

-- TODO:update

-- -------------------------------------------
-- ポイント
CREATE TABLE point(
    user_id VARCHAR(20), 
    year INT, 
    month INT,
    total_calorie_consumed INT DEFAULT 0,
    total_nosmoke INT DEFAULT 0,
    total_alcohol_consumed INT DEFAULT 0,
    total_calorie_intake INT DEFAULT 0,
    total_sleeptime INT DEFAULT 1,
    PRIMARY KEY(user_id, year, month),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO point VALUES  ("kazutoshi_t", 2025, 6, 7200, 7, 300, 300, 4);
INSERT INTO point VALUES  ("kazutoshi_t", 2025, 5, 5000, 7, 300, 300, 3);
INSERT INTO point VALUES  ("kazutoshi_t", 2025, 4, 57600, 7, 300, 300, 1);
INSERT INTO point VALUES  ("kazutoshi_t", 2025, 3, 54000, 7, 300, 300, 2);
INSERT INTO point VALUES  ("kazutoshi_t", 2025, 2, 50400, 7, 300, 300, 3);
INSERT INTO point VALUES  ("kazutoshi_t", 2025, 1, 46800, 7, 300, 300, 4);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 12, 43200, 7, 300, 300, 5);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 11, 39600, 7, 300, 300, 1);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 10, 36000, 7, 300, 300, 2);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 9, 32400, 7, 300, 300, 3);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 8, 28800, 7, 300, 300, 4);
INSERT INTO point VALUES  ("kazutoshi_t", 2024, 7, 25200, 7, 300, 300, 5);

INSERT INTO point(user_id, year, month) VALUES  ("test1", 2025, 6);

SELECT * FROM point;

-- TODO:update
-- TODO:delete

-- -------------------------------------------
-- 報酬日
CREATE TABLE reward_day(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    user_id VARCHAR(20), 
    date DATE, 
    reward_explain VARCHAR(30),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO reward_day(user_id, date, reward_explain) VALUES  
    ("kazutoshi_t", "2025-05-10", "人であふれた！"),
    ("kazutoshi_t", "2025-05-15", "建物が建った！"),
    ("kazutoshi_t", "2025-05-31", "民族衣装がもらえた！"),
    ("kazutoshi_t", "2025-06-10", "人であふれた！"),
    ("kazutoshi_t", "2025-06-15", "建物が建った！"),
    ("kazutoshi_t", "2025-06-15", "建物が建った！"),
    ("kazutoshi_t", "2025-06-30", "民族衣装がもらえた！");

SELECT * FROM reward_day;

-- -------------------------------------------
-- 健康記録(全体)
-- 健康記録(飲酒)
-- 健康記録(運動)
CREATE TABLE health_whole(
    user_id VARCHAR(20), 
    date DATE, 
    nosmoke INT,
    sleep_time FLOAT,
    calorie_intake INT,
    free  VARCHAR(100),
    now_weight FLOAT,
    PRIMARY KEY(user_id, date),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE health_alcohol(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    user_id VARCHAR(20), 
    date DATE, 
    pure_alcohol_consumed FLOAT,
    alcohol_consumed INT,
    alcohol_content FLOAT,
    FOREIGN KEY(user_id, date) REFERENCES health_whole(user_id, date)
);

CREATE TABLE health_exercise(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    user_id VARCHAR(20), 
    date DATE, 
    calorie_consu FLOAT,
    exercise_type VARCHAR(30),
    exercise_time  INT, 
    FOREIGN KEY(user_id, date) REFERENCES health_whole(user_id, date)
);

-- 2025/0601
INSERT INTO health_whole VALUES ("kazutoshi_t", "2025-06-01", 0, 8, 2000, "test0601", 60);
INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content) VALUES ("kazutoshi_t", "2025-06-01", 30, 300, 5);
INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content) VALUES ("kazutoshi_t", "2025-06-01", 30, 100, 10);
INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time) VALUES ("kazutoshi_t", "2025-06-01", 300, "ウォーキング", 30);
INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time) VALUES ("kazutoshi_t", "2025-06-01", 300, "サイクリング", 60);
-- 2025/0602
INSERT INTO health_whole VALUES ("kazutoshi_t", "2025-06-02", 1, 7.5, 2500, "test0602", 60);
INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content) VALUES ("kazutoshi_t", "2025-06-02", 30, 300, 5);
INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time) VALUES ("kazutoshi_t", "2025-06-02", 900, "ランニング", 30);
-- 2025/0603
INSERT INTO health_whole VALUES ("kazutoshi_t", "2025-06-03", 0, 8, 3000, "test0603", 60);
INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time) VALUES ("kazutoshi_t", "2025-06-03", 600, "ウォーキング", 60);

SELECT * FROM health_whole;
SELECT * FROM health_alcohol;
SELECT * FROM health_exercise;

-- TODO:update

-- -------------------------------------------
-- 達成ポイント(食事)
-- 達成ポイント(飲酒)
-- 達成ポイント(禁煙)
-- 達成ポイント(睡眠)

CREATE TABLE point_eat(
    stage INT PRIMARY KEY, 
    achievement_point INT
);

CREATE TABLE point_alcohol(
    stage INT PRIMARY KEY,
    achievement_point INT
    );

CREATE TABLE point_smoke(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    achievement_point INT,
    people INT
);

CREATE TABLE point_sleep(
    stage INT PRIMARY KEY,
    achievement_point INT
);

INSERT INTO point_eat(achievement_point, stage) VALUES 
    (70, 1),
    (140, 2),
    (210, 3),
    (300, 4);

INSERT INTO point_alcohol(achievement_point, stage) VALUES 
    (100, 1),
    (200, 2),
    (300, 3);

INSERT INTO point_smoke(achievement_point, people) VALUES 
    (0, 0),
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7);

INSERT INTO point_sleep(achievement_point, stage) VALUES 
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

SELECT * FROM point_eat;
SELECT * FROM point_alcohol;
SELECT * FROM point_smoke;
SELECT * FROM point_sleep;

-- -------------------------------------------
-- 報酬(衣服)
CREATE TABLE reward_cloth(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    stage INT, 
    country_order INT, 
    image_path VARCHAR(50),
    FOREIGN KEY(stage) REFERENCES point_eat(stage), 
    FOREIGN KEY(country_order) REFERENCES world_tour(country_order)
);

INSERT INTO reward_cloth(stage, country_order, image_path) VALUES
    (1, 0, "img/cloth1.png"),
    (2, 0, "img/cloth2.png"),
    (3, 0, "img/cloth3.png"),
    (4, 1, "img/jp_cloth4.png"), -- 日本
    (4, 2, "img/au_cloth4.png"), -- オーストラリア
    (4, 3, "img/sg_cloth4.png"), -- シンガポール
    (4, 4, "img/in_cloth4.png"), -- インド
    (4, 5, "img/cn_cloth4.png"), -- 中国
    (4, 6, "img/kr_cloth4.png"), -- 韓国
    (4, 7, "img/mn_cloth4.png"), -- モンゴル
    (4, 8, "img/ru_cloth4.png"), -- ロシア
    (4, 9, "img/fr_cloth4.png"), -- フランス
    (4, 10, "img/gb_cloth4.png"), -- イギリス
    (4, 11, "img/it_cloth4.png"), -- イタリア
    (4, 12, "img/eg_cloth4.png"), -- エジプト
    (4, 13, "img/mg_cloth4.png"), -- マダガスカル
    (4, 14, "img/br_cloth4.png"), -- ブラジル
    (4, 15, "img/mx_cloth4.png"), -- メキシコ
    (4, 16, "img/us_cloth4.png"), -- アメリカ
    (4, 17, "img/universe_cloth4.png"); -- 宇宙

SELECT * FROM reward_cloth;

-- -------------------------------------------
-- 報酬(建物)
CREATE TABLE reward_build(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    stage INT, 
    country_order INT, 
    image_path VARCHAR(50),
    FOREIGN KEY(stage) REFERENCES point_alcohol(stage), 
    FOREIGN KEY(country_order) REFERENCES world_tour(country_order)
);

INSERT INTO reward_build(stage, country_order, image_path) VALUES
    (1, 1, "img/jp_build1.png"),
    (2, 1, "img/jp_build2.png"),
    (3, 1, "img/jp_build3.png"),
    (1, 2, "img/au_build1.png"),
    (2, 2, "img/au_build2.png"),
    (3, 2, "img/au_build3.png"),
    (1, 3, "img/sg_build1.png"),
    (2, 3, "img/sg_build2.png"),
    (3, 3, "img/sg_build3.png"),
    (1, 4, "img/in_build1.png"),
    (2, 4, "img/in_build2.png"),
    (3, 4, "img/in_build3.png"),
    (1, 5, "img/cn_build1.png"),
    (2, 5, "img/cn_build2.png"),
    (3, 5, "img/cn_build3.png"),
    (1, 6, "img/kr_build1.png"),
    (2, 6, "img/kr_build2.png"),
    (3, 6, "img/kr_build3.png"),
    (1, 7, "img/mn_build1.png"),
    (2, 7, "img/mn_build2.png"),
    (3, 7, "img/mn_build3.png"),
    (1, 8, "img/ru_build1.png"),
    (2, 8, "img/ru_build2.png"),
    (3, 8, "img/ru_build3.png"),
    (1, 9, "img/fr_build1.png"),
    (2, 9, "img/fr_build2.png"),
    (3, 9, "img/fr_build3.png"),
    (1, 10, "img/gb_build1.png"),
    (2, 10, "img/gb_build2.png"),
    (3, 10, "img/gb_build3.png"),
    (1, 11, "img/it_build1.png"),
    (2, 11, "img/it_build2.png"),
    (3, 11, "img/it_build3.png"),
    (1, 12, "img/eg_build1.png"),
    (2, 12, "img/eg_build2.png"),
    (3, 12, "img/eg_build3.png"),
    (1, 13, "img/mg_build1.png"),
    (2, 13, "img/mg_build2.png"),
    (3, 13, "img/mg_build3.png"),
    (1, 14, "img/br_build1.png"),
    (2, 14, "img/br_build2.png"),
    (3, 14, "img/br_build3.png"),
    (1, 15, "img/mx_build1.png"),
    (2, 15, "img/mx_build2.png"),
    (3, 15, "img/mx_build3.png"),
    (1, 16, "img/us_build1.png"),
    (2, 16, "img/us_build2.png"),
    (3, 16, "img/us_build3.png"),
    (1, 17, "img/universe_build1.png"),
    (2, 17, "img/universe_build2.png"),
    (3, 17, "img/universe_build3.png");

SELECT * FROM reward_build;

-- -------------------------------------------
-- 報酬(人)
CREATE TABLE reward_people(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    country_order INT, 
    image_path VARCHAR(50),
    FOREIGN KEY(country_order) REFERENCES world_tour(country_order)
);

INSERT INTO reward_people(country_order, image_path) VALUES
    (1, "img/jp_people.png"),
    (2, "img/au_people.png"),
    (3, "img/sg_people.png"),
    (4, "img/in_people.png"),
    (5, "img/cn_people.png"),
    (6, "img/kr_people.png"),
    (7, "img/mn_people.png"),
    (8, "img/ru_people.png"),
    (9, "img/fr_people.png"),
    (10, "img/gb_people.png"),
    (11, "img/it_people.png"),
    (12, "img/eg_people.png"),
    (13, "img/mg_people.png"),
    (14, "img/br_people.png"),
    (15, "img/mx_people.png"),
    (16, "img/us_people.png"),
    (17, "img/universe_people.png");

SELECT * FROM reward_people;

-- -------------------------------------------
-- 報酬(顔色)
CREATE TABLE reward_face(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    stage INT, 
    image_path VARCHAR(50),
    FOREIGN KEY(stage) REFERENCES point_sleep(stage)
);

INSERT INTO reward_face(stage, image_path) VALUES
    (1, "img/face1.png"),
    (2, "img/face2.png"),
    (3, "img/face3.png"),
    (4, "img/face4.png"),
    (5, "img/face5.png");

SELECT * FROM reward_face;



-- -------------------------------------------
-- 過去ファイル
CREATE TABLE history(
    user_id VARCHAR(20),
    year INT,
    month INT,
    file_path VARCHAR(50),
    PRIMARY KEY(user_id, year, month),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

-- INSERT INTO history VALUES
--     ("kazutoshi_t", 2023, 2, "history/kazutoshi_t/2024-2.txt"),
--     ("kazutoshi_t", 2023, 3, "history/kazutoshi_t/2024-3.txt"),
--     ("kazutoshi_t", 2023, 4, "history/kazutoshi_t/2024-4.txt"),
--     ("kazutoshi_t", 2023, 5, "history/kazutoshi_t/2024-5.txt"),
--     ("kazutoshi_t", 2023, 6, "history/kazutoshi_t/2024-6.txt");

SELECT * FROM history;
