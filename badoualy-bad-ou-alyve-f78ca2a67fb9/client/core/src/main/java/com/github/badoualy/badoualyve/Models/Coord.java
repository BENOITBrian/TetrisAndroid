package com.github.badoualy.badoualyve.Models;

/**
 * Created by Benoit Brian on 28/01/2016.
 */
public class Coord {

    private int _xRel;
    private int _yRel;
    private int _id;
    private int _vx;
    private int _vy;

    public Coord(int x,int y,int id){
        this._xRel = x;
        this._yRel = y;
        this._id = id;
    }

    public void SetRealCoord(int x,int y){
        this._vx = x;
        this._vy = y;
    }

    public int GetRealX(){
        return _vx;
    }

    public int GetRealY(){
        return _vy;
    }

    public int GetRecognition(){
        return _id;
    }

    public int GetRelativeX(){
       return _xRel;
    }

    public int GetRelativeY(){
        return _yRel;
    }

    public void SetRelativeCoord(int x,int y,int id){
        this._xRel = x;
        this._yRel = y;
    }
}
