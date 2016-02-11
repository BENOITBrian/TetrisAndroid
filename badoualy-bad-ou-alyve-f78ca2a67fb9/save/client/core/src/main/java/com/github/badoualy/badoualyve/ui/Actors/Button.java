package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;

import static com.github.badoualy.badoualyve.ui.TetrisGame.gdxTools;

/**
 * Created by Benoit Brian on 05/02/2016.
 */
public class Button extends Group {

    private String _label = "";
    private int _widthButton = 120;
    private int _height = 50;
    private Image _picture;
    private  BitmapFont _font;

    public Button(String label){
        _label = label;
        _picture = GdxTools.createImageFromTexture(AssetsSettings.BG_BUTTON);
        setSize(_picture.getWidth(), _picture.getHeight());
        addActor(_picture);
        _font = gdxTools().getDefaultFont(26,3);
        _font.setColor(Color.WHITE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        _font.draw(batch,_label,getX() + 14,getY() + 30);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public boolean OnClick(int screenX,int screenY){
        int calcY = (int)getY() + _height;
        int calcX = (int)getX() + _widthButton;

        if(screenX >= (int)getX() && screenX <= calcX
                && screenY >= (int)getY() && screenY <= calcY)
            return true;

        return false;
    }
}
