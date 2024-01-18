CREATE TABLE IF NOT EXISTS TASK_CATEGORIES
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(100),
    DESCRIPTION VARCHAR(500),
    UNIQUE (NAME)
);

CREATE TABLE IF NOT EXISTS TASKS
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(100)             NOT NULL,
    DESCRIPTION VARCHAR(500),
    DEADLINE    TIMESTAMP WITH TIME ZONE NOT NULL,
    CATEGORY_ID INT,
    FOREIGN KEY (CATEGORY_ID) REFERENCES TASK_CATEGORIES (ID)
);