/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anzymus.neogeo.hiscores.service;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Stateless;
import com.anzymus.neogeo.hiscores.domain.Game;

@Stateless
public class GameService {

    private static final Set<Game> games = new TreeSet<Game>();

    public void add(Game game) {
        games.add(game);
    }

    public Set<Game> findAll() {
        return Collections.unmodifiableSet(games);
    }

    public Game findByName(String gameName) {
        for(Game game:games) {
            if (gameName.equals(game.getName())) {
                return game;
            }
        }
        return null;
    }

    public Game findById(int gameId) {
        for(Game game:games) {
            if (gameId == game.getId()) {
                return game;
            }
        }
        return null;
    }

}
