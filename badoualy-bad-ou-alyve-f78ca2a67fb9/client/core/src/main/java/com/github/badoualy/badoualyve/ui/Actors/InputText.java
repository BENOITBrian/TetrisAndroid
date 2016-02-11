package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;

import static com.github.badoualy.badoualyve.ui.TetrisGame.gdxTools;

/**
 * Created by Benoit Brian on 02/02/2016.
 */
public class InputText extends Group {

    private String _fieldName = "";
    private String _value = "";
    private float inputWidth = 0f;
    private BitmapFont _font;
    private BitmapFont _fontText;
    private Image _picture;
    public InputText(String fieldName){
        _font = new BitmapFont();
        _fieldName = fieldName;

        _picture = GdxTools.createImageFromTexture(AssetsSettings.CADRE_INPUT);
        setSize(_picture.getWidth(), _picture.getHeight());
        addActor(_picture);

        GlyphLayout layout = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout.setText(_font, _value);
        inputWidth = layout.width;

        _font = gdxTools().getDefaultFont(17,2);
        _font.setColor(Color.WHITE);

        _fontText = gdxTools().getDefaultFont(17,0);
        _fontText.setColor(Color.WHITE);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

        // Center top title
        _font.draw(batch, _fieldName, getX() + 10, getY() + 45);

        // Current input
        _fontText.draw(batch, _value, getX() + 13, getY() + 27);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setInputValue(String value){
        _value = value;
        GlyphLayout layout = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout.setText(_fontText, value);
        inputWidth = layout.width;
    }

    public String getValue(){
        return _value;
    }

    public void keyTyped(char c) {
        if(_value.length() <= 8){
            _value += c;
        }
        // Compute new input width
        GlyphLayout layout = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout.setText(_fontText, _value);
        inputWidth = layout.width;
    }

    public boolean OnClick(int screenX,int screenY){

        if(screenX >= getX() && screenX <= getX() + _picture.getWidth()
                && screenY >= getY() && screenY <= getY() + _picture.getHeight())
            return true;

        return false;
    }


    public void delete(){
        if(_value.length()> 0){
            _value = _value.substring(0,_value.length() - 1);
        }
    }

}
