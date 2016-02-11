package com.github.badoualy.badoualyve.ui.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.badoualy.badoualyve.GameEngine;
import com.github.badoualy.badoualyve.Models.EnumDirections;
import com.github.badoualy.badoualyve.Models.User;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;
import com.github.badoualy.badoualyve.ui.Actors.BlockEmpty;
import com.github.badoualy.badoualyve.ui.Actors.BlockL;
import com.github.badoualy.badoualyve.ui.Actors.BlockLRev;
import com.github.badoualy.badoualyve.ui.Actors.BlockMid;
import com.github.badoualy.badoualyve.ui.Actors.BlockSquare;
import com.github.badoualy.badoualyve.ui.Actors.BlockStick;
import com.github.badoualy.badoualyve.ui.Actors.BlockZ;
import com.github.badoualy.badoualyve.ui.Actors.BlockZRev;
import com.github.badoualy.badoualyve.ui.Actors.Dialog;
import com.github.badoualy.badoualyve.ui.Actors.FormBase;
import com.github.badoualy.badoualyve.ui.Actors.Pad;
import com.github.badoualy.badoualyve.ui.Actors.Preview;
import com.github.badoualy.badoualyve.ui.Actors.StatModule;
import com.google.gson.Gson;

import static com.github.badoualy.badoualyve.ui.TetrisGame.gdxTools;

public class IntroStage extends Stage {
    OrthographicCamera cam;
    private ShapeRenderer _squareAnimate;
    private boolean testflag;
    private Music _startSound;
    private BitmapFont font;
    private SpriteBatch batch;
    private GameEngine _ge;
    private Dialog score;
    private Dialog line;
    private boolean _pressContinue = false;
    private int _keyCode;
    private Sprite _picture;
    private Sprite _bordure;
    private int cptFps = 1;
    private int _cptFpsTouch = 0;
    private BitmapFont _font;
    private boolean _isMoving = false;
    private int cpt = 1;
    private boolean _isAnimate = false;
    private int _stepAnimate = 0;
    private int _colAnimate = 0;
    private int _ligneAnimate = 0;
    private int _indexEncours = 0;
    private int _cptStart = 0;
    private Preview _preview;
    private Pad _pad;
    private StatModule userMod1;
    private StatModule userMod2;
    private StatModule userMod3;
    private StatModule userMod4;
    public IntroStage(GameEngine ge) {
        _ge = ge;
        testflag = false;
        initActors();
        Gdx.input.setOnscreenKeyboardVisible(false);
    }

    @Override
    public void dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false);
        font.dispose();
        batch.dispose();
        super.dispose();
    }

    private void initActors() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GlobalSettings.WIDTH_WINDOWS, GlobalSettings.HEIGHT_WINDOWS);
        getViewport().setCamera(cam);
        batch = new SpriteBatch();
        font = new BitmapFont();

        _font = gdxTools().getDefaultFont(22,0);
        _font.setColor(0f,216f,255f,1f);
        _startSound = Gdx.audio.newMusic(Gdx.files.internal(AssetsSettings.SOUND_START));
        _startSound.play();
        _squareAnimate = new ShapeRenderer();
        _squareAnimate.setColor(Color.BLACK);

        score = new Dialog("SCORE","0");
        score.setPosition(GlobalSettings.PLACE_X_MOD_SCORE, GlobalSettings.PLACE_Y_MOD_SCORE);

        line = new Dialog("LINES","0");
        line.setPosition(GlobalSettings.PLACE_X_MOD_LINE, GlobalSettings.PLACE_Y_MOD_LINE);
        _preview = new Preview(AssetsSettings.PREVIEW_MID);
        _preview.setPosition(GlobalSettings.PLACE_X_PREVIEW, GlobalSettings.PLACE_Y_PREVIEW);
        _pad = new Pad();
        _pad.setPosition(GlobalSettings.PLACE_X_PAD, GlobalSettings.PLACE_Y_PAD);
        addActor(_pad);
        addActor(_preview);
        addActor(score);
        addActor(line);

        if(GlobalSettings.USE_SERVER) {
            int yLine = 295;

            for (int i = 1; i <= _ge.getPartie().listUser.size(); i++) {

                if (i == 1) {
                    userMod1 = new StatModule(_ge.getPartie().listUser.get(i).name);
                    userMod1.setPosition(GlobalSettings.PLACE_X_MOD_USER, yLine);
                    userMod1.SetInfo(_ge.getPartie().listUser.get(i).score, _ge.getPartie().listUser.get(i).detes);
                    addActor(userMod1);
                }

                if (i == 2) {
                    userMod2 = new StatModule(_ge.getPartie().listUser.get(i).name);
                    userMod2.setPosition(GlobalSettings.PLACE_X_MOD_USER, yLine);
                    userMod2.SetInfo(_ge.getPartie().listUser.get(i).score, _ge.getPartie().listUser.get(i).detes);
                    addActor(userMod2);
                }

                if (i == 3) {
                    userMod3 = new StatModule(_ge.getPartie().listUser.get(i).name);
                    userMod3.setPosition(GlobalSettings.PLACE_X_MOD_USER, yLine);
                    userMod3.SetInfo(_ge.getPartie().listUser.get(i).score, _ge.getPartie().listUser.get(i).detes);
                    addActor(userMod3);
                }

                if (i == 4) {
                    userMod4 = new StatModule(_ge.getPartie().listUser.get(i).name);
                    userMod4.setPosition(GlobalSettings.PLACE_X_MOD_USER, yLine);
                    userMod4.SetInfo(_ge.getPartie().listUser.get(i).score, _ge.getPartie().listUser.get(i).detes);
                    addActor(userMod1);
                }

                yLine -= 90;
            }
        }

    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_ACTIVE_TEXTURE);

        display(AssetsSettings.BG_GAME, 0, 0);
        display(AssetsSettings.CADRE_GAME, GlobalSettings.PLACE_X_CADRE_GAME, GlobalSettings.PLACE_Y_CADRE_GAME);
        display(AssetsSettings.CADRE_PANEL, GlobalSettings.PLACE_X_CADRE_PANEL, GlobalSettings.PLACE_Y_CADRE_PANEL);
        display(AssetsSettings.CADRE_PANEL, GlobalSettings.PLACE_X_CADRE_STAT, GlobalSettings.PLACE_Y_CADRE_STAT);
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        _font.draw(batch, "NEXT", 208, 380);
        batch.end();

        UpdateScreen();

        if(GlobalSettings.USE_SERVER) {
            stat();
            Winner();
        }

        score.setMessage(_ge.GetScore().toString());
        line.setMessage(_ge.GetLines().toString());
        super.draw();
    }

    public void display(String texture, int x, int y) {

        _bordure = new Sprite(GdxTools.getTexture(texture));
        _bordure.setPosition(x, y);
        batch.begin();
        _bordure.draw(batch);
        batch.end();
    }

    @Override
    public void act(float delta) {

        if(_pressContinue){
            cpt++;
        }

        if(_cptStart <= GlobalSettings.FPS_WAIT_FOR_START){
            _cptStart++;
            return;
        }

        if (_isAnimate) {
            if (_colAnimate < GlobalSettings.SIZE_ARRAY_X) {
                _ge.GetMap().SetPosition(_colAnimate, _ligneAnimate, new BlockEmpty());
                _colAnimate++;
            } else {
                if (_indexEncours == 0) {
                    _isAnimate = false;
                    _ligneAnimate = 0;
                    _colAnimate = 0;
                    if(GlobalSettings.USE_SERVER) {
                        _ge.addline(_ge.Getuser());
                    }
                    _ge._deleteLine();
                } else {
                    _indexEncours--;
                    _ligneAnimate = _ge.GetLineAnimate().get(_indexEncours);
                    _colAnimate = 0;
                }
            }
            return;
        }

        if (_isMoving) {
            boolean res = _ge.Move(EnumDirections.DOWN, true);

            if (!res && _ge._checkDeleteLine()) {
                _isAnimate = true;
                _indexEncours = _ge.GetLineAnimate().size() - 1;
                _ligneAnimate = _ge.GetLineAnimate().get(_indexEncours);
            } else {
                _isMoving = _ge.Move(EnumDirections.DOWN, false);
            }
            return;
        }

        if (cpt >= 8 && _pressContinue) {
            _action(_keyCode);
        }

        if (cptFps % GlobalSettings.FPS == 0) {
            boolean res = _ge.Move(EnumDirections.DOWN, true);
            if (!res && _ge._checkDeleteLine()) {
                _isAnimate = true;
                _indexEncours = _ge.GetLineAnimate().size() - 1;
                _ligneAnimate = _ge.GetLineAnimate().get(_indexEncours);
            } else {
                _ge.Move(EnumDirections.DOWN, false);
                cptFps = 0;
            }
        }

        cptFps++;
        super.act(delta);
    }


    private void stat(){

        for(int i = 1; i <= _ge.getPartie().listUser.size();i++){
            if(i == 1){
                userMod1.SetInfo(_ge.getPartie().listUser.get(i - 1).score, _ge.getPartie().listUser.get(i - 1).detes);
            }

            if(i == 2){
                userMod2.SetInfo(_ge.getPartie().listUser.get(i - 1).score, _ge.getPartie().listUser.get(i - 1).detes);
            }

            if(i == 3){
                userMod3.SetInfo(_ge.getPartie().listUser.get(i - 1).score, _ge.getPartie().listUser.get(i - 1).detes);
            }

            if(i == 4){
                userMod4.SetInfo(_ge.getPartie().listUser.get(i - 1).score, _ge.getPartie().listUser.get(i - 1).detes);
            }
        }
    }

    private void Winner(){
        if(_ge.getPartie().listUser.size() <= 1) {
            batch.begin();
            font.draw(batch, "Gagné 1 2 3 viva algeria", GlobalSettings.V_WIDTH / 2, GlobalSettings.V_HEIGHT / 2);
            batch.end();
        }
    }


    private void UpdateScreen() {

        if(_cptStart <= GlobalSettings.FPS_WAIT_FOR_START)
            return;

        int addPixelX =  GlobalSettings.PLACE_X_CADRE_GAME + GlobalSettings.PADDING_X_CADRE;
        int addPixelY =  GlobalSettings.PLACE_Y_CADRE_GAME + GlobalSettings.PADDING_Y_CADRE;




        for (int y = GlobalSettings.SIZE_ARRAY_Y - 1; y >= 0; y--) {

            for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {

                if (!_ge.GetMap().GetObjectByCoord(x, y).IsEmpty) {

                    if (_ge.IsGameOver()) {
                        batch.begin();
                        font.draw(batch, "Game over", GlobalSettings.V_WIDTH / 2, GlobalSettings.V_HEIGHT / 2);
                        batch.end();
                    }

                    _picture = new Sprite(GdxTools.getTexture(_ge.GetMap().GetObjectByCoord(x, y).GetPicture()));
                    _picture.setPosition((x * GlobalSettings.SIZE_BLOCK) + addPixelX, (y * GlobalSettings.SIZE_BLOCK) + addPixelY);
                    batch.begin();
                    _picture.draw(batch);
                    batch.end();
                }

            }
        }
        FormBase fb = _ge.GetNextBlock(0, 0);
        _displayPreview(fb);
    }

    private void _displayPreview(FormBase fb){

        if(fb instanceof BlockL ){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_L);
        }

        if(fb instanceof BlockLRev){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_LREV);
        }

        if(fb instanceof BlockZ){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_Z);
        }

        if(fb instanceof BlockZRev){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_ZREV);
        }

        if(fb instanceof BlockMid){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_MID);
        }

        if(fb instanceof BlockSquare){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_SQUARE);
        }

        if(fb instanceof BlockStick){
            _preview.SetTypePicture(AssetsSettings.PREVIEW_STICK);
        }
    }

    @Override
    public boolean keyDown(int keyCode) {

        if (keyCode == Input.Keys.UP) {
                _action(keyCode);
        } else {
            _action(keyCode);
            _pressContinue = true;
        }

        _keyCode = keyCode;
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        _pressContinue = false;
        cpt = 1;
        return false;
    }

    private void _action(int keyCode) {
        if (keyCode == Input.Keys.UP) {
            _ge.Rotate();
        }

        if (keyCode == Input.Keys.LEFT) {
            _ge.Move(EnumDirections.LEFT, false);
        }

        if (keyCode == Input.Keys.RIGHT) {
            _ge.Move(EnumDirections.RIGHT, false);
        }

        if (keyCode == Input.Keys.DOWN) {
            _ge.Move(EnumDirections.DOWN, false);
        }

        if (keyCode == 62) {
            _isMoving = _ge.Move(EnumDirections.DOWN, false);
        }
    }

    private void _action(EnumDirections dir) {
        switch (dir) {
            case DOWN:
                _ge.Move(EnumDirections.DOWN, false);
                _keyCode = Input.Keys.DOWN;
                break;
            case LEFT:
                _ge.Move(EnumDirections.LEFT, false);
                _keyCode = Input.Keys.LEFT;
                break;
            case RIGHT:
                _ge.Move(EnumDirections.RIGHT, false);
                _keyCode = Input.Keys.RIGHT;
                break;
            case ROTATE:
                _ge.Rotate();
                break;
            case ESPACE:
                _isMoving = _ge.Move(EnumDirections.DOWN, false);
                break;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vec=new Vector3(screenX,screenY,0);
        cam.unproject(vec);
        screenX = (int)vec.x;
        screenY = (int)vec.y;

        if(button == Input.Buttons.LEFT) {
            EnumDirections dir =_pad.OnClick(screenX,screenY);
            if(dir != null){
              _action(_pad.OnClick(screenX,screenY));
                if(dir != EnumDirections.ROTATE && dir != EnumDirections.ESPACE){
                    _pressContinue = true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        _pressContinue = false;
        cpt = 1;
        return false;
    }
}
