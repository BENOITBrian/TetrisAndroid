package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumShapes;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.ToolsB.GlobalTools;

import java.util.ArrayList;

/**
 * Created by Arezki on 03/02/16.
 */
public class BlockSkull extends FormBase{

    public BlockSkull(){
        super();
        this.GetBlock().Color = new Color(Color.BLACK);
        this._picture = AssetsSettings.BLOCK_SKULL;
        _entplacementR = BuildBlock(_currentShape);
    }

    @Override
    protected ArrayList<Coord> BuildBlock(EnumShapes shape) {
        ArrayList<Coord> res = new ArrayList<Coord>();
        res.add(new Coord(0, 0, GlobalTools.GetRecognition()));
        return res;
    }

    @Override
    public void Rotate() {}

    @Override
    public ArrayList<Coord> VirtualRotate() {
        return null;
    }
}