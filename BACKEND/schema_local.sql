-- Canonical local MySQL schema for LanguagePlatformApplication backend
-- Generated from current JPA model (Spring Boot)

CREATE DATABASE IF NOT EXISTS `languagelearning`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE `languagelearning`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `PracticeSessionFlashCard`;
DROP TABLE IF EXISTS `QuizResult`;
DROP TABLE IF EXISTS `PracticeSession`;
DROP TABLE IF EXISTS `FlashCard`;
DROP TABLE IF EXISTS `User`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `User` (
  `userID` BIGINT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(50) NOT NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('STUDENT','ADMIN') NOT NULL DEFAULT 'STUDENT',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `UK_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `FlashCard` (
  `flashCardID` BIGINT NOT NULL AUTO_INCREMENT,
  `sentence` TEXT NOT NULL,
  `difficultyLevel` ENUM('BEGINNER','INTERMEDIATE','ADVANCED') NOT NULL,
  `optionA` VARCHAR(255) NOT NULL,
  `optionB` VARCHAR(255) NOT NULL,
  `optionC` VARCHAR(255) NOT NULL,
  `optionD` VARCHAR(255) NOT NULL,
  `correctAnswer` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`flashCardID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `PracticeSession` (
  `sessionID` BIGINT NOT NULL AUTO_INCREMENT,
  `userID` BIGINT NOT NULL,
  `startTime` DATETIME(6) NOT NULL,
  `endTime` DATETIME(6) DEFAULT NULL,
  `sessionStatus` ENUM('ONGOING','COMPLETED') NOT NULL DEFAULT 'ONGOING',
  PRIMARY KEY (`sessionID`),
  KEY `IDX_practice_session_user` (`userID`),
  CONSTRAINT `FK_practice_session_user`
    FOREIGN KEY (`userID`) REFERENCES `User` (`userID`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `QuizResult` (
  `resultID` BIGINT NOT NULL AUTO_INCREMENT,
  `userID` BIGINT NOT NULL,
  `sessionID` BIGINT DEFAULT NULL,
  `difficultyLevel` ENUM('BEGINNER','INTERMEDIATE','ADVANCED') NOT NULL,
  `totalQuestions` INT NOT NULL,
  `correctAnswers` INT NOT NULL,
  `completionTime` DATETIME(6) NOT NULL,
  `scorePercentage` DOUBLE NOT NULL,
  PRIMARY KEY (`resultID`),
  KEY `IDX_quiz_result_user` (`userID`),
  KEY `IDX_quiz_result_session` (`sessionID`),
  CONSTRAINT `FK_quiz_result_user`
    FOREIGN KEY (`userID`) REFERENCES `User` (`userID`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT `FK_quiz_result_session`
    FOREIGN KEY (`sessionID`) REFERENCES `PracticeSession` (`sessionID`)
    ON UPDATE CASCADE
    ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `PracticeSessionFlashCard` (
  `sessionID` BIGINT NOT NULL,
  `flashCardID` BIGINT NOT NULL,
  `positionInQueue` INT NOT NULL,
  `rating` ENUM('EASY','MEDIUM','HARD','DONT_KNOW') NOT NULL DEFAULT 'DONT_KNOW',
  PRIMARY KEY (`sessionID`, `flashCardID`),
  KEY `IDX_psfc_flashcard` (`flashCardID`),
  CONSTRAINT `FK_psfc_session`
    FOREIGN KEY (`sessionID`) REFERENCES `PracticeSession` (`sessionID`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `FK_psfc_flashcard`
    FOREIGN KEY (`flashCardID`) REFERENCES `FlashCard` (`flashCardID`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
