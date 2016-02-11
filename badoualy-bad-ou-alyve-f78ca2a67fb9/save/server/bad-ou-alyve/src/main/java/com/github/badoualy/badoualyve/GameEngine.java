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
package com.github.badoualy.badoualyve;

import com.github.badoualy.badoualyve.model.ListPartie;
import com.github.badoualy.badoualyve.model.Partie;
import com.github.badoualy.badoualyve.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GameEngine {

    // Utils
    private static final Random random = new SecureRandom();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<User> userList;
    private List<Partie> partieList;
    private File saveFile;
    private int idofpartie;
    private int usersnumberofpartie;
    public GameEngine(String dbPath) {
        userList = new ArrayList<>();
        partieList = new ArrayList<Partie>();
        saveFile = new File(dbPath);
        idofpartie = 0;
        usersnumberofpartie = 2;// nombre de user dans une partie
        load();
    }

    public Partie getpartie(){
        System.out.println("getpartie(){Begin}");
        Partie partie = new Partie();

        if(!partieList.isEmpty()){
            System.out.println(" Partie partiecurent(){Begin}");

            Partie partiecurent = partieList.stream().filter(u -> !u.goPartie ).findFirst().orElseGet(null);

            System.out.println(" Partie partiecurent(){end}");
            if(partiecurent == null)
            {

                System.out.println("(partiecurent == null){Begin}");
                partie.goPartie = false;
                ++idofpartie;
                partie.partieId = idofpartie;
                partie.name = "partie_"+ idofpartie;

            }
            else
            {

                if (partiecurent.listUser.size() >= usersnumberofpartie) {
                    partiecurent.goPartie = true;
                    ++idofpartie;
                    partie.name = "partie_" + idofpartie;
                    partie.partieId = idofpartie;
                }
                else
                {

                    partie = partiecurent;
                   // if (partiecurent.listUser.size() >= 1) {  partie.goPartie = true;}

                }
            }

        }
        else
        {
            System.out.println("partiecurent == null(){Begin}");
            partie.goPartie = false;
            ++idofpartie;
            partie.partieId = idofpartie;
            partie.name = "partie_"+ partie.partieId;


        }
        partieList.add(partie);
        System.out.println("getpartie(){end}");
        return partie;
    }



    public Partie generateNewUser(String name) {

        Partie partie = getpartie();
        User user = new User();
        user.name = getFreeName(name);
        user.detes = 0;
        user.score = 0;
        user.partieId = partie.partieId;
        user.lastUpdateTime = System.currentTimeMillis();
        userList.add(user);
        partie.user = user;
        partie.listUser.add(user);
        System.out.println("teste of users (){"+partie.listUser.size()+"}");
        if(partie.listUser.size() >= usersnumberofpartie){
            partie.goPartie = true;
            Partie partieafter = new Partie();
            partieafter.goPartie = false;
            ++idofpartie;
            partieafter.partieId = idofpartie;
            partieafter.name = "Partie_"+idofpartie;
            partieList.add(partieafter);
        }




        return partie;
    }

    private String getFreeName(String preferredName) {
        String name = preferredName;
        while (userAlreadyExists(name))
            name = preferredName + random.nextInt(999);
        return name;
    }
    private String getFreeNamewithpartie(String preferredName, Partie partie) {
        String name = preferredName;
        while (userAlreadyExistswithpartie(name, partie))
            name = preferredName + random.nextInt(999);
        return name;
    }
//withpartie

    private boolean userAlreadyExistswithpartie(String name,Partie partie) {

        return partie.listUser.stream().anyMatch(u -> u.name.equalsIgnoreCase(name));
    }


    private boolean userAlreadyExists(String name) {
        return userList.stream().anyMatch(u -> u.name.equalsIgnoreCase(name));
    }

    public User findUser(String name) {
        return userList.stream().filter(u -> u.name.equalsIgnoreCase(name)).findFirst().orElseGet(null);
    }

    public Partie findPartie(String name) {

        User user = findUser(name);
        System.out.println("the name of this user is "+ user.name);
        Partie partie =  partieList.stream().filter( u -> u.listUser.contains(user)).findFirst().orElseGet(null);
        partie.user = user;
        return partie;
    }

    public void updateUserStatsAndSave(User user) {
        updateUserStats(user);
        save();
    }

    /**
     * Automatic evolution (time)
     */
    public void updateUserStats(User user) {
        long delta = System.currentTimeMillis() - user.lastUpdateTime;

        user.detes += user.detes + 1;// +20 every 500 ms


        user.lastUpdateTime = System.currentTimeMillis();
        save();
    }

    public void addlinesforuser(User user,long linenumber, long score) {

        for(User usong:userList  ) {
            if(usong != user) {
                usong.detes = usong.detes + linenumber;// +20 every 500 ms
                usong.score = score;
                usong.lastUpdateTime = System.currentTimeMillis();
            }
           // save();
        }

    }

    public void addlinesforuserwithpartie(User user,long linenumber, long score) {

       Partie partie =  partieList.stream().filter( u -> u.listUser.contains(user)).findFirst().orElseGet(null);

        for(User usong:partie.listUser  ) {
            if(usong != user) {
                usong.detes = usong.detes + linenumber;// +20 every 500 ms
                usong.score = score;
                usong.lastUpdateTime = System.currentTimeMillis();
            }
            // save();
        }

    }



    public void deleteUserdete(User user) {
            user.detes = 0;
            // save();
    }



    /**
     * Evolution after a fight won
     */
    private void evolveUser(User user) {

        updateUserStats(user); // Time evolution (will update lastUpdateTime)
    }

    /**
     * @return true if the attacker won
     */
    public boolean resolveFight(User attacker, User defenser) {
        updateUserStats(attacker);
        updateUserStats(defenser);


        boolean wonFight = attacker.currHp > 0;
        if (wonFight)
            evolveUser(attacker);

        updateUserStats(attacker);
        updateUserStats(defenser);
        return wonFight;
    }

    /**
     * @return true if the defenser lost the fight (hp are 0)
     **/
    private boolean resolveAttack(User attacker, User defenser) {
        //defenser.currHp -= attacker.attack - (0.5f * defenser.defense);
        if (defenser.currHp < 0)
            defenser.currHp = 0;
        return defenser.currHp == 0;
    }
public List<User> findlistusers(){
    return userList;
}

public void deleteUser(User user){
    userList.remove(user);
}

    public Partie deleteUserwithpartie(User user) {
        Partie partie =  partieList.stream().filter( u -> u.listUser.contains(user)).findFirst().orElseGet(null);
        partie.listUser.remove(user);
        userList.remove(user);
        return partie;
    }


    /**
     * Match-making algorithm
     */
    public User findOpponent(User user) {
        // Stupid algorithm, just looking for opponent with closest attack
       // return userList.stream().filter(u -> u != user).min((u1, u2) -> (int) Math.abs(u1.attack - u2.attack)).orElse(null);
        return user;
    }

    public void load() {
        try {
            String s = FileUtils.readFileToString(saveFile, "UTF-8");

            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            userList = new Gson().fromJson(s, listType);
        } catch (Exception e) {
        }
    }

    public void save() {
        try {
            FileUtils.writeStringToFile(saveFile, toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toJson() {
        return gson.toJson(userList);
    }
}
