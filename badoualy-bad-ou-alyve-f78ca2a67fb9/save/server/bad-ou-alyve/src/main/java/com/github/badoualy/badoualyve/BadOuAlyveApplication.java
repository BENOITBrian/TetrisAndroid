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

import com.github.badoualy.badoualyve.model.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class BadOuAlyveApplication extends WebMvcConfigurerAdapter {

    private static String DB_PATH = System.getProperty("java.io.tmpdir") + "badoualyve_" + System.currentTimeMillis() + ".json";
    private static GameEngine gameHandler = new GameEngine(DB_PATH);
    private final String LOGGER_NAME = "BadOuAlyve";

    /** This method is called when accessing the API root, display usage */
    @RequestMapping("/")
    @ResponseBody
    public HomeMessage home() {
        Logger.getLogger(LOGGER_NAME).info("home()");

        HomeMessage homeMessage = new HomeMessage("Welcome to WANTED: Bad-ou-Alyve server!");
        homeMessage.addOperation("/connect/{name}", "Connect to the server as {name}");
        homeMessage.addOperation("/users/{name}", "Get self as {name} the partie");
        homeMessage.addOperation("/users/{name}/fight", "Fight as {name}");
        homeMessage.addOperation("/users/{name}/{detes}/addline", "addline as others ");
        homeMessage.addOperation("/users/{name}/listuser", "listuser as others ");
        homeMessage.addOperation("/users/{name}/logout", "logout as others ");
        homeMessage.addOperation("/users/{name}/deletedeteUser", "deleteUser as others ");
        return homeMessage;
    }

    /** Connect a new user on the server */
    @RequestMapping("/connect/{name}")
    @ResponseBody
    public Partie connect(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("connect(): " + name);
        Partie partie = gameHandler.generateNewUser(name);
        gameHandler.save();
        return partie;
    }



    /** Return the user's data after updating his stats */
    @RequestMapping("/users/{name}")
    @ResponseBody
    public Partie user(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("Getuser(): " + name);

        Partie partie = gameHandler.findPartie(name);

        return partie;
    }

    /** Return the user's data after updating his stats */
    @RequestMapping("/users/{name}/logout")
    @ResponseBody
    public Partie logout(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("LogOutUser(): " + name);

        User user = gameHandler.findUser(name);
        Partie partie = gameHandler.deleteUserwithpartie(user);
        partie.user = user;

        return partie;
    }

    /** Return the user's data after updating his stats */
    @RequestMapping("/users/{name}/deletedeteUser")
    @ResponseBody
    public Partie deletedeteUser(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("deletedeteUser(): " + name);

        User user = gameHandler.findUser(name);
        gameHandler.deleteUserdete(user);
        Partie partie = gameHandler.findPartie(name);
        return partie;
    }

    /** Return the user's data after updating his stats */
    @RequestMapping("/users/{name}/listuser")
    @ResponseBody
    public List<User> listuser(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("user(): " + name);

        User user = gameHandler.findUser(name);
        //gameHandler.updateUserStatsAndSave(user);

        List<User> listusong = gameHandler.findlistusers();
        return listusong;
    }
    /** ajouter le nombre de line  */
    @RequestMapping("/users/{name}/{detes}/addline")
    @ResponseBody
    public Partie addline(@PathVariable final String name,@PathVariable final int detes) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("detes(): " + detes);
        Logger.getLogger(LOGGER_NAME).info("user(): " + name);
        User user = gameHandler.findUser(name);

        gameHandler.addlinesforuserwithpartie(user, detes, 2);
       /* List<User> users = gameHandler.findlistusers();
        ListUser userlist = new ListUser();
        for (User usercurrr:users) {
            userlist.listUser.add(usercurrr);
        }*/
        Partie partie = gameHandler.findPartie(name);
        return partie;
    }

    @RequestMapping("/users/{name}/fight")
    @ResponseBody
    public FightResult userFight(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("userFight(): " + name);

        User user = gameHandler.findUser(name); // Retrieve user
        User opponent = gameHandler.findOpponent(user); // Match-making algorithm
        if (opponent == null){
            // return no opponent found
            gameHandler.updateUserStatsAndSave(user);
            return new FightResult(FightResult.NO_OPPONENT_FOUND, user);
        }

        Logger.getLogger(LOGGER_NAME).info("userFight(): " + name + " vs " + opponent.name);
        boolean wonFight = gameHandler.resolveFight(user, opponent); // Resolve fight
        gameHandler.save();

        Logger.getLogger(LOGGER_NAME).info("userFight(): won " + wonFight);
        return new FightResult(wonFight ? FightResult.VICTORY : FightResult.DEFEAT, user);
    }

    public static void main(String[] args) {
        SpringApplication.run(BadOuAlyveApplication.class, args);
        Logger.getAnonymousLogger().info("SERVER LAUNCHED");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Configure spring to use Gson to map object to json for response
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        converters.add(gsonHttpMessageConverter);
    }
}
