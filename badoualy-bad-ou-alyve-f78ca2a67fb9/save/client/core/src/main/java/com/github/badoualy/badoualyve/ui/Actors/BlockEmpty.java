package com.github.badoualy.badoualyve.ui.Actors;

import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumShapes;

import java.util.ArrayList;

/**
 * Created by Benoit Brian on 29/01/2016.
 */
public class BlockEmpty extends FormBase {

    public BlockEmpty(){
        this.IsEmpty = true;
    }

    @Override
    protected ArrayList<Coord> BuildBlock(EnumShapes shape) {
        return null;
    }

    @Override
    public void Rotate() {

    }

    @Override
    public ArrayList<Coord> VirtualRotate() {
        return null;
    }
}
