/**
 * This file is part of WANTED: Bad-ou-Alyve.
 *
 * WANTED: Bad-ou-Alyve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WANTED: Bad-ou-Alyve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WANTED: Bad-ou-Alyve.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.badoualy.badoualyve.Models;

public class User {

    public String name;
    public long detes;
    public long score;
    public long partieId;
    public long lastUpdateTime;

    public transient long currHp; // used to resolve fight

    public User copy() {
        User user = new User();
        user.name = name;
        user.score = score;
        user.detes = detes;
        user.partieId = partieId;


        user.lastUpdateTime = lastUpdateTime;

        return user;
    }
}
