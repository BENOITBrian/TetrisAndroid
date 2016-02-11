package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;

/**
 * Created by Benoit Brian on 07/02/2016.
 */
public class Preview extends Group {

    private Image _img;
    private ShapeRenderer sr;
    public Preview(String name){
        sr = new ShapeRenderer();
        sr.setColor(Color.BROWN);
        _img = GdxTools.createImageFromTexture(name);
        setSize(_img.getWidth(), _img.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setWidth(96);
        setHeight(96);
        float xImg =  getX() + getWidth() / 2 - _img.getWidth() / 2;
        float yImg =  getY() + getHeight() / 2 - _img.getHeight() / 2;
        _img.setPosition(xImg,yImg);
        _img.draw(batch,parentAlpha);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void SetTypePicture(String name){
        _img = GdxTools.createImageFromTexture(name);
        setSize(_img.getWidth(), _img.getHeight());
    }
}
