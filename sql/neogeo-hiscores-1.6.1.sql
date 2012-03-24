ALTER TABLE GAME ADD COLUMN GENRE VARCHAR(45) NULL  AFTER IMPROVABLE;

CREATE TABLE `RELOCKED_TITLE` (
  `ID` bigint(20) NOT NULL,
  `RELOCK_DATE` datetime NOT NULL,
  `PLAYER_ID` bigint(20) NOT NULL,
  `RELOCKER_SCORE` bigint(20) NOT NULL,
  `TITLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
);

INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (14,'Good scorer in Beat''em all games','com.anzymus.neogeo.hiscores.success.BeaterTitleStrategy','Beater',5100);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (15,'Good scorer in Fighting games','com.anzymus.neogeo.hiscores.success.FighterTitleStrategy','Fighter',5110);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (16,'Good scorer in Shooter games','com.anzymus.neogeo.hiscores.success.ShooterTitleStrategy','Shooter',5120);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (17,'Good scorer in Racing games','com.anzymus.neogeo.hiscores.success.RacerTitleStrategy','Racer',5130);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (18,'Good scorer in Quiz games','com.anzymus.neogeo.hiscores.success.QuizerTitleStrategy','Quizer',5140);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (19,'Good scorer in Puzzle games','com.anzymus.neogeo.hiscores.success.ShooterTitleStrategy','Puzzler',5150);
INSERT INTO `TITLE` (`ID`,`DESCRIPTION`,`CLASSNAME`,`LABEL`,`POSITION`) VALUES (20,'Good scorer in Run n Gun games','com.anzymus.neogeo.hiscores.success.RunNGunTitleStrategy','Gunner',5160);

