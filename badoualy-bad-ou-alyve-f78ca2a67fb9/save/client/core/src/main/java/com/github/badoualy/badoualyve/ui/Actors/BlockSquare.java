package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumShapes;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.ToolsB.GlobalTools;

import java.util.ArrayList;

/**
 * Created by Benoit Brian on 31/01/2016.
 */
public class BlockSquare extends FormBase {

    public BlockSquare(){
        super();
        this.GetBlock().Color = new Color(Color.YELLOW);
        this._picture = AssetsSettings.BLOCK_GREENF;

        _entplacementR = BuildBlock(_currentShape);
    }

    @Override
    protected ArrayList<Coord> BuildBlock(EnumShapes shape) {
        ArrayList<Coord> res = new ArrayList<Coord>();
        res.add(new Coord(0, 0, _id));
        res.add(new Coord(0, 1, _id));
        res.add(new Coord(1, 0, _id));
        res.add(new Coord(1, 1, _id));
        return res;
    }

    @Override
    public void Rotate() {

    }

    @Override
    public ArrayList<Coord> VirtualRotate() {
        return null;
    }
}
