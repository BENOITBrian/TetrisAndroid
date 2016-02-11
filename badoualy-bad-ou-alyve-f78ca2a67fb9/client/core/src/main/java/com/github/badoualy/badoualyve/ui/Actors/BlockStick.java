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
public class BlockStick extends FormBase {

    public BlockStick(){
        super();
        this.GetBlock().Color = new Color(Color.GREEN);
        _entplacementR = BuildBlock(_currentShape);
        this._picture = AssetsSettings.BLOCK_PURPLE;

    }

    @Override
    protected ArrayList<Coord> BuildBlock(EnumShapes shape) {
        ArrayList<Coord> res = new ArrayList<Coord>();

        if(shape == EnumShapes.SHAPE_1) {
            res.add(new Coord(0, 0, _id));
            res.add(new Coord(0, 1, _id));
            res.add(new Coord(0, 2, _id));
            res.add(new Coord(0, 3, _id));
        }
        else{
            res.add(new Coord(0, 3, _id));
            res.add(new Coord(1, 3, _id));
            res.add(new Coord(2, 3, _id));
            res.add(new Coord(3, 3, _id));
        }

        return res;
    }

    @Override
    public void Rotate() {
        if(_currentShape == EnumShapes.SHAPE_1){
            _currentShape = EnumShapes.SHAPE_2;
            _entplacementR = BuildBlock(_currentShape);
        }
        else{
            _currentShape = EnumShapes.SHAPE_1;
            _entplacementR = BuildBlock(_currentShape);
        }
    }

    @Override
    public ArrayList<Coord> VirtualRotate() {
        if(_currentShape == EnumShapes.SHAPE_1){
            return BuildBlock(EnumShapes.SHAPE_2);
        }
        else{
            return BuildBlock(EnumShapes.SHAPE_1);
        }
    }
}
