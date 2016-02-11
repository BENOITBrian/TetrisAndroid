package com.github.badoualy.badoualyve.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.GameEngine;
import com.github.badoualy.badoualyve.Listener.GetPlayerListener;
import com.github.badoualy.badoualyve.Listener.OnFightFinishedListener;
import com.github.badoualy.badoualyve.Listener.OnSignedListener;
import com.github.badoualy.badoualyve.Models.Partie;
import com.github.badoualy.badoualyve.Models.User;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;
import com.github.badoualy.badoualyve.ui.HelpScreen.FixedFpsScreen;
import com.github.badoualy.badoualyve.ui.Stages.HomeStage;
import com.github.badoualy.badoualyve.ui.Stages.IntroStage;
import com.google.gson.Gson;

import de.tomgrill.gdxdialogs.core.GDXDialogs;


/**
 * Created by Benoit Brian on 28/01/2016.
 */
public class TetrisGame extends Game implements OnSignedListener, OnFightFinishedListener, GetPlayerListener {


    private GameEngine gameEngine;
    private GdxTools _gdxTools;
    private GDXDialogs dialogs;
    private boolean _step2 = false;
    public static GlobalSettings Settings;

    OrthographicCamera camera;
    private GdxTools gdxTools;
    private boolean _isFirst = true;

    private int _cptFps = 1;

    @Override
    public void create() {
        gdxTools = new GdxTools();
        Texture.setAssetManager(gdxTools.assetManager);

        gameEngine = new GameEngine(this, this, this);
        if(GlobalSettings.USE_SERVER) {
            gameEngine.print();
        }
        displayHomeScreen();
    }

    @Override
    public void dispose() {
        super.dispose();
        _gdxTools.assetManager.dispose();
    }


    @Override
    public void render() {

        if (gameEngine.GetStepGame()) {
            if (_isFirst) {
                if(GlobalSettings.USE_SERVER) {

                    Partie partie = gameEngine.getPartie();
                    Gson gson = new Gson();
                    System.out.println("connect() {" + gson.toJson(partie) + "}");
                    if (partie != null && partie.goPartie) {
                        displayGameScreen();
                        _isFirst = false;
                    }
                }
                else{
                    displayGameScreen();
                    _isFirst = false;
                }
            }
        }

        // This call will render the currently displaying screen
        super.render();
    }

    private void displayGameScreen() {
        gdxTools.loadBlockAssets();
        gdxTools.assetManager.finishLoading();

        setScreen(new FixedFpsScreen(new IntroStage(gameEngine), GlobalSettings.FPS));
    }

    private void displayHomeScreen() {
        gdxTools.loadHomeAssets();
        gdxTools.assetManager.finishLoading();
        setScreen(new FixedFpsScreen(new HomeStage(gameEngine), GlobalSettings.FPS));
    }

    public static GdxTools gdxTools() {
        return ((TetrisGame) Gdx.app.getApplicationListener())._gdxTools;
    }

    @Override
    public void onSignedIn(User player) {}
    @Override
    public void onFightFinished(int result) {}
    @Override
    public void getPlayer(User name){}
    @Override
    public void addline(User player){}
    @Override
    public void deletedeteUser(User player){}
    @Override
    public void logout(User player){}
    @Override
    public void listuser(User player){}


}
