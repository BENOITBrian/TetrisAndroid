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
 * Created by Benoit Brian on 01/02/2016.
 */
public class Dialog extends Group {

    private String _title = "";
    private BitmapFont _fontTitle;
    private BitmapFont _fontMessage;
    private float _titleWidth = 0f;
    private float _msgWidth = 0f;
    private float counter = 0f;
    private boolean displaying = true;
    private String _message;
    private Image _picture;

    public Dialog(String title,String msg) {
        _title = title;
        _message = msg;
        _fontTitle = gdxTools().getDefaultFont(22,0);
        _fontTitle.setColor(0f,216f,255f,1f);

        GlyphLayout layout = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout.setText(_fontTitle, title);
        _titleWidth = layout.width;

        _fontMessage = gdxTools().getDefaultFont(22,0);

        GlyphLayout layout2 = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout2.setText(_fontMessage, title);
        _msgWidth = layout2.width;

        _picture = GdxTools.createImageFromTexture(AssetsSettings.BG_MODULE);
        setSize(_picture.getWidth(), _picture.getHeight());
        addActor(_picture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Center top title
        float xt =  getX() + getWidth() / 2 - _titleWidth / 2;
        float xM =  getX() + getWidth() / 2 - _msgWidth / 2;

        _fontTitle.draw(batch, _title,xt, getTop() - 10);

        _fontMessage.draw(batch, _message,xM, getTop() - 45);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Used to blink the message
        counter += delta;
        if (counter >= 0.44f) {
            counter = 0;
            displaying = !displaying;
        }
    }

    public void setMessage(String msg){
        _message = msg;
        GlyphLayout layout2 = new GlyphLayout(); // Don't do this every frame! Store it as member
        layout2.setText(_fontMessage, msg);
        _msgWidth = layout2.width;
    }

}
