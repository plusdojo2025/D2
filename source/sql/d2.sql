drop database d2;
create database d2;
use d2

-- -------------------------------------------
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
CREATE TABLE world_tour(
    country_order INT PRIMARY KEY,
    country VARCHAR(20),
    necessary_calorie INT
);

INSERT INTO world_tour(country_order, country) VALUES 
    (1, "日本"), 
    (2, "オーストラリア"),
    (3, "シンガポール"),
    (4, "インド"),
    (5, "中国");
    -- (6, ""),
    -- (7, ""),
    -- (8, ""),
    -- (9, ""),
    -- (10, ""),
    -- (11, ""),
    -- (12, ""),
    -- (13, ""),
    -- (14, ""),
    -- (15, ""),
    -- (16, ""),
    -- (17, "");

SELECT * FROM world_tour;

-- -------------------------------------------
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
    ("test1", "test1");

SELECT * FROM user;

-- TODO:update

-- -------------------------------------------
CREATE TABLE target_value(
    user_id VARCHAR(20) PRIMARY KEY, 
    month INT,
    pure_alcohol_consumed FLOAT,
    sleep_time FLOAT,
    calorie_intake INT,
    target_weight FLOAT,
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO target_value(user_id) VALUES 
    ("kazutoshi_t"), 
    ("test1");

SELECT * FROM target_value;

-- TODO:update

-- -------------------------------------------

CREATE TABLE point(
    user_id VARCHAR(20), 
    year INT, 
    month INT,
    total_calorie_consumed INT DEFAULT 0,
    total_nosmoke INT DEFAULT 0,
    total_alcohol_consumed INT DEFAULT 0,
    total_calorie_intake INT DEFAULT 0,
    total_sleeptime INT DEFAULT 0,
    PRIMARY KEY(user_id, year, month),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO point VALUES  ("kazutoshi_t", 2025, 5, 7200, 7, 300, 210, 5);
INSERT INTO point(user_id, year, month) VALUES  
    ("kazutoshi_t", 2025, 6), 
    ("test1", 2025, 6);

SELECT * FROM point;

-- TODO:update
-- TODO:delete

-- -------------------------------------------
CREATE TABLE reward_day(
    user_id VARCHAR(20), 
    date DATE, 
    reward_explain VARCHAR(30),
    PRIMARY KEY(user_id, date),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

INSERT INTO reward_day VALUES  
    ("kazutoshi_t", "2025-05-10", "人であふれた！"),
    ("kazutoshi_t", "2025-05-15", "建物が建った！"),
    ("kazutoshi_t", "2025-05-31", "民族衣装がもらえた！");

SELECT * FROM reward_day;

-- -------------------------------------------
CREATE TABLE health_whole(
    user_id VARCHAR(20), 
    date DATE, 
    nosmoke INT,
    sleep_time FLOAT,
    calorie_intake INT,
    free  VARCHAR(100)
    PRIMARY KEY(user_id, date),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

-- CREATE TABLE health_alcohol(
--     id INT PRIMARY KEY AUTO_INCREMENT, 
--     user_id VARCHAR(20), 
--     date DATE, 
--     nosmoke INT,
--     sleep_time FLOAT,
--     calorie_intake INT,
--     free  VARCHAR(100)
--     FOREIGN KEY(user_id) REFERENCES user(id)
-- );

-- CREATE TABLE health_exercise(
--     id INT PRIMARY KEY AUTO_INCREMENT, 
--     user_id VARCHAR(20), 
--     date DATE, 
--     calorie_consu FLOAT,
--     calorie_intake INT,
--     free  VARCHAR(100)
--     FOREIGN KEY(user_id) REFERENCES user(id)
-- );

-- -------------------------------------------
-- -------------------------------------------
