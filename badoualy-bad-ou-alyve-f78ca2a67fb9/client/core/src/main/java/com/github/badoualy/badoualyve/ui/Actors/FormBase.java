package com.github.badoualy.badoualyve.ui.Actors;

import com.badlogic.gdx.graphics.Color;
import com.github.badoualy.badoualyve.Models.Block;
import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumShapes;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GlobalTools;

import java.util.ArrayList;

/**
 * Created by Benoit Brian on 28/01/2016.
 */
public abstract class FormBase {

    public boolean IsEmpty = false;
    protected int _id;
    protected EnumShapes _currentShape;
    protected ArrayList<Coord> _entplacementR;
    private Block _block;
    protected String _picture;
    FormBase(){
        _currentShape = EnumShapes.SHAPE_1;
        _block = new Block();
        GlobalTools.CreateRecognition();
        _id = GlobalTools.GetRecognition();
    }

    public ArrayList<Coord> GetEntplacement(){
        return _entplacementR;
    }

    public Block GetBlock(){
        return _block;
    }

    /*public Color GetColor(){
        return _block.Color;
    }*/

    protected abstract ArrayList<Coord> BuildBlock(EnumShapes shape);
    public abstract void Rotate();
    public abstract ArrayList<Coord> VirtualRotate();
    public String GetPicture(){
        return _picture;
    }

}
