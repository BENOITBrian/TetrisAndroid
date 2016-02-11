package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.Models.EnumDirections;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.ToolsB.GdxTools;

/**
 * Created by Benoit Brian on 10/02/2016.
 */
public class Pad extends Group {

    private Image _picUp;
    private Image _picDown;
    private Image _picLeft;
    private Image _picRight;
    private Image _picMid;

    private float posUpX;
    private float posUpY;

    private float posDownX;
    private float posDownY;

    private float posLeftX;
    private float posLeftY;

    private float posRightX;
    private float posRightY;

    private float posMidX;
    private float posMidY;

    public Pad(){
        setSize(111,111);
        _picUp = GdxTools.createImageFromTexture(AssetsSettings.PAD_UP);
        posUpX  = getX() + 45;
        posUpY = getY() + 87;
        _picUp.setPosition(posUpX,posUpY);

        _picDown = GdxTools.createImageFromTexture(AssetsSettings.PAD_DOWN);
        posDownX  = getX() + 45;
        posDownY = getY();
        _picDown.setPosition(posDownX,posDownY);

        _picLeft = GdxTools.createImageFromTexture(AssetsSettings.PAD_LEFT);
        posLeftX = getX();
        posLeftY = getY() + 45;
        _picLeft.setPosition(posLeftX,posLeftY);

        _picRight = GdxTools.createImageFromTexture(AssetsSettings.PAD_RIGHT);
        posRightX = getX() + 87;
        posRightY = getY() + 45;
        _picRight.setPosition(posRightX,posRightY);

        _picMid = GdxTools.createImageFromTexture(AssetsSettings.PAD_MID);
        posMidX = getX() + 40;
        posMidY = getY() + 39;
        _picMid.setPosition(posMidX,posMidY);

        addActor(_picUp);
        addActor(_picDown);
        addActor(_picLeft);
        addActor(_picRight);
        addActor(_picMid);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public EnumDirections OnClick(int screenX,int screenY){
        int sizePad = 25;

        if(screenX >= getX() + posUpX && screenX <= getX() + posUpX + sizePad
                && screenY >= getY() + posUpY && screenY <= getY() + posUpY + sizePad)
            return EnumDirections.ROTATE;

        if(screenX >= getX() + posLeftX && screenX <= getX() + posLeftX + sizePad
                && screenY >= getY() + posLeftY && screenY <= getY() + posLeftY + sizePad)
            return EnumDirections.LEFT;

        if(screenX >= getX() + posRightX && screenX <= getX() + posRightX + sizePad
                && screenY >= getY() + posRightY && screenY <= getY() + posRightY + sizePad)
            return EnumDirections.RIGHT;

        if(screenX >= getX() + posDownX && screenX <= getX() + posDownX + sizePad
                && screenY >= getY() + posDownY && screenY <= getY() + posDownY + sizePad)
            return EnumDirections.DOWN;

        if(screenX >= getX() + posMidX && screenX <= getX() + posMidX + 40
                && screenY >= getY() + posMidY && screenY <= getY() + posMidY + 40)
            return EnumDirections.ESPACE;

        return null;
    }

}
