INSERT INTO TITLE VALUES (11, 'Finish 2 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer2TitleStrategy', 'All clearer! x2', 2002);
INSERT INTO TITLE VALUES (12, 'Finish 5 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer5TitleStrategy', 'All clearer! x5', 2005);
INSERT INTO TITLE VALUES (13, 'Finish 10 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer10TitleStrategy', 'All clearer! x10', 2010);

ALTER TABLE GAME ADD COLUMN `CUSTOM_STAGE_VALUES` VARCHAR(255) NULL  AFTER `POSTID` ;

