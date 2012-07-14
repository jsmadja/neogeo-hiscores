--
--     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
--
--     Licensed under the Apache License, Version 2.0 (the "License");
--     you may not use this file except in compliance with the License.
--     You may obtain a copy of the License at
--
--             http://www.apache.org/licenses/LICENSE-2.0
--
--     Unless required by applicable law or agreed to in writing, software
--     distributed under the License is distributed on an "AS IS" BASIS,
--     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--     See the License for the specific language governing permissions and
--     limitations under the License.
--

INSERT INTO TITLE VALUES (11, 'Finish 2 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer2TitleStrategy', 'All clearer! x2', 2002);
INSERT INTO TITLE VALUES (12, 'Finish 5 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer5TitleStrategy', 'All clearer! x5', 2005);
INSERT INTO TITLE VALUES (13, 'Finish 10 games with only one credit', 'com.anzymus.neogeo.hiscores.success.AllClearer10TitleStrategy', 'All clearer! x10', 2010);

ALTER TABLE GAME ADD COLUMN `CUSTOM_STAGE_VALUES` VARCHAR(255) NULL  AFTER `POSTID` ;

