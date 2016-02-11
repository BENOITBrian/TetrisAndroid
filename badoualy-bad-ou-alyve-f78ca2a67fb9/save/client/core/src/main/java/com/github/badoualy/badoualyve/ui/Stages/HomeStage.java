package com.github.badoualy.badoualyve.ui.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.github.badoualy.badoualyve.GameEngine;
import com.github.badoualy.badoualyve.Models.Partie;
import com.github.badoualy.badoualyve.Models.User;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;
import com.github.badoualy.badoualyve.ui.Actors.Button;
import com.github.badoualy.badoualyve.ui.Actors.InputText;
import com.google.gson.Gson;

import static com.github.badoualy.badoualyve.ui.TetrisGame.gdxTools;

/**
 * Created by Benoit Brian on 02/02/2016.
 */
public class HomeStage extends Stage {
    public static final AssetManager assetManager = new AssetManager();
    private GameEngine _ge;
    private boolean connect;
    private BitmapFont font;
    OrthographicCamera cam;
    private Button _buttonNg;
    InputText _input;
    InputText _inputServer;
    SpriteBatch batch;
    Sprite _picture;
    public HomeStage(GameEngine ge) {
        this._ge = ge;
        batch = new SpriteBatch();
        _picture = new Sprite();
        connect = false;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GlobalSettings.WIDTH_WINDOWS, GlobalSettings.HEIGHT_WINDOWS);
        getViewport().setCamera(cam);
        _buttonNg = new Button("START");
        _buttonNg.setPosition(505, 200);

        _picture = new Sprite(GdxTools.getTexture(AssetsSettings.BC_HOME_PAGE));
        _picture.setPosition(0, 0);

        _input = new InputText("Username");
        _input.setPosition(295,200);

       _inputServer = new InputText("Status");
        // _inputServer.setInputValue("Wait 4/4");
        _inputServer.setPosition(80, 200);

        addActor(_input);
        addActor(_inputServer);
        addActor(_buttonNg);


    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void draw() {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        _picture.draw(batch);
        batch.end();

        if(GlobalSettings.USE_SERVER){
            getNbrUser();
        }

        super.draw();
    }

    private void getNbrUser(){
         if(_ge.getPartie() != null){
             _inputServer.setInputValue("Wait "+ _ge.getPartie().listUser.size() +"/4");
         }else
         {
             _inputServer.setInputValue("Hello...");
         }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public boolean keyTyped(char character) {
        if ((character >= 'a' && character <= 'z')
                || (character >= 'A' && character <= 'Z')
                || (character >= '0' && character <= '9')) {
            _input.keyTyped(character);
            return true;
        }
        return super.keyTyped(character);
    }

    public boolean keyUp(int keyCode) {
        if(keyCode == 67){
            _input.delete();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vec=new Vector3(screenX,screenY,0);
        cam.unproject(vec);
        screenX = (int)vec.x;
        screenY = (int)vec.y;

        if(button == Input.Buttons.LEFT) {

            if(GlobalSettings.USE_SERVER){
                String username = _input.getValue();
                if(username != null && username!= "" && !connect && _buttonNg.OnClick(screenX, screenY))
                {
                    System.out.println(username);
                    _ge.onSignIn(username);

                   while(_ge.getPartie() == null){
                       System.out.println("en attente de réponse ....");
                        if(_ge.getisconnected()) break;

                    }

                    connect = true;
                }
            }
			
            if(_input.OnClick(screenX, screenY)){
                Gdx.input.setOnscreenKeyboardVisible(true);
            }

            _ge.SetStepGame(_buttonNg.OnClick(screenX, screenY));

        }
        return false;
    }



}
