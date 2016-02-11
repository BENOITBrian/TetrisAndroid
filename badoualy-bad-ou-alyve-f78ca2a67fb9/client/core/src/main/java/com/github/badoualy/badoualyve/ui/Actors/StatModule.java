package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;

import static com.github.badoualy.badoualyve.ui.TetrisGame.gdxTools;

/**
 * Created by Benoit Brian on 11/02/2016.
 */
public class StatModule extends Group {
    private String _userName;
    private long _score;
    private long _lines;
    private Image _picture;
    private BitmapFont _fontUser;
    private float _titleWidth;
    private boolean _isActive;

    private BitmapFont _fontInfo;

    public StatModule(String userName){

        _userName = userName;

        _fontUser = gdxTools().getDefaultFont(22,0);
        _fontUser.setColor(0f, 216f, 255f, 1f);

        _fontInfo = gdxTools().getDefaultFont(16,0);

        GlyphLayout layout = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout.setText(_fontUser, _userName);
        _titleWidth = layout.width;


        _picture = GdxTools.createImageFromTexture(AssetsSettings.BG_MODULE);
        setSize(_picture.getWidth(), _picture.getHeight());
        addActor(_picture);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float xt =  getX() + getWidth() / 2 - _titleWidth / 2;

        _fontUser.draw(batch, _userName, xt, getTop() - 10);

        _fontInfo.draw(batch, "Score :" + _score, getX() + 10, getY() + 40);
        _fontInfo.draw(batch, "Lines :" + _lines, getX() + 10, getY() + 20);
    }

    public void SetInfo(long score,long lines){
        _score = score;
        _lines = lines;
    }

}
