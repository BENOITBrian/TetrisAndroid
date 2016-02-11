package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumShapes;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;
import com.github.badoualy.badoualyve.ToolsB.GlobalTools;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Benoit Brian on 28/01/2016.
 */
public class BlockL extends FormBase {
    public BlockL() {
        super();
        this.GetBlock().Color = new Color(Color.BLUE);
        this._picture = AssetsSettings.BLOCK_BLUE;
        _entplacementR = BuildBlock(_currentShape);
    }

    @Override
    protected ArrayList<Coord> BuildBlock(EnumShapes shape) {
        ArrayList<Coord> res = new ArrayList<Coord>();
        switch (shape) {
            case SHAPE_1:
                res.add(new Coord(0, 0, _id));
                res.add(new Coord(0, 1, _id));
                res.add(new Coord(0, 2, _id));
                res.add(new Coord(1, 2, _id));
                break;
            case SHAPE_2:
                res.add(new Coord(0, 0, _id));
                res.add(new Coord(1, 0, _id));
                res.add(new Coord(2, 0, _id));
                res.add(new Coord(2, -1, _id));
                break;
            case SHAPE_3:
                res.add(new Coord(0, 0, _id));
                res.add(new Coord(1, 0, _id));
                res.add(new Coord(1, 1, _id));
                res.add(new Coord(1, 2, _id));
                break;
            case SHAPE_4:
                res.add(new Coord(0, 0, _id));
                res.add(new Coord(0, 1, _id));
                res.add(new Coord(1, 0, _id));
                res.add(new Coord(2, 0, _id));
                break;
        }

        return res;
    }

    @Override
    public void Rotate() {

        switch (_currentShape) {
            case SHAPE_1:
                _currentShape = EnumShapes.SHAPE_2;
                break;
            case SHAPE_2:
                _currentShape = EnumShapes.SHAPE_3;
                break;
            case SHAPE_3:
                _currentShape = EnumShapes.SHAPE_4;
                break;
            case SHAPE_4:
                _currentShape = EnumShapes.SHAPE_1;
                break;
        }
        _entplacementR = BuildBlock(_currentShape);
    }

    @Override
    public ArrayList<Coord> VirtualRotate() {
        ArrayList<Coord> res = new ArrayList<Coord>();

        switch (_currentShape) {
            case SHAPE_1:
                return BuildBlock(EnumShapes.SHAPE_2);
            case SHAPE_2:
                return BuildBlock(EnumShapes.SHAPE_3);
            case SHAPE_3:
                return BuildBlock(EnumShapes.SHAPE_4);
            case SHAPE_4:
                return BuildBlock(EnumShapes.SHAPE_1);
        }

        return res;

    }
}
