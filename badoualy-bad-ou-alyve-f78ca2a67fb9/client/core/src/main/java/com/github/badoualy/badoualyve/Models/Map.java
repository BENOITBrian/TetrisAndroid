package com.github.badoualy.badoualyve.Models;

import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ui.Actors.FormBase;

/**
 * Created by Benoit Brian on 28/01/2016.
 */
public class Map {
    private FormBase[][] _map;

    public Map() {
        _map = new FormBase[GlobalSettings.SIZE_ARRAY_X][GlobalSettings.SIZE_ARRAY_Y];
    }

    public void SetPosition(int x, int y, FormBase object) {
        _map[x][y] = object;
    }

    public FormBase GetObjectByCoord(int x, int y) {

        if (x < 0) {
            return null;
        }
        if (y < 0) {
            return null;
        }
        if (x >= GlobalSettings.SIZE_ARRAY_X) {
            return null;
        }
        if (y >= GlobalSettings.SIZE_ARRAY_Y) {
            return null;
        }

        return _map[x][y];
    }

    public EnumTypeObject GetTypeObjectByCoord(int x, int y) {

        if (x < 0) {
            return EnumTypeObject.OVER;
        }
        if (y < 0) {
            return EnumTypeObject.OVER;
        }
        if (x >= GlobalSettings.SIZE_ARRAY_X) {
            return EnumTypeObject.OVER;
        }
        if (y >= GlobalSettings.SIZE_ARRAY_Y) {
            return EnumTypeObject.OVER;
        }

        FormBase tmp = _map[x][y];
        if (tmp.IsEmpty) {
            return EnumTypeObject.EMPTY;
        }

        return EnumTypeObject.BLOCK;
    }

    public int FormMustTop(){
        for (int y = GlobalSettings.SIZE_ARRAY_Y - 1; y >= 0; y--) {
            for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++){
                if (GetTypeObjectByCoord(x, y) != EnumTypeObject.EMPTY) {
                    return y;
                }
            }
        }
        return 0;
    }

    public FormBase[][] GetMap() {
        return _map;
    }
}
