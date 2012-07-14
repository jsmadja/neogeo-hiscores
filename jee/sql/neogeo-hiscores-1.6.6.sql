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

INSERT INTO TITLE
(`ID`,
`DESCRIPTION`,
`CLASSNAME`,
`LABEL`,
`POSITION`)
VALUES
(
25,
'Have a score in each Metal Slug games',
'com.anzymus.neogeo.hiscores.success.SlugerTitleStrategy',
'Sluger',
5001
);

INSERT INTO TITLE VALUES
(
26,
'Have a score in platfrom games',
'com.anzymus.neogeo.hiscores.success.PlatformerTitleStrategy',
'Platformer',
5002
);


