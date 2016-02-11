package com.github.badoualy.badoualyve.ToolsB;

import java.util.Random;

/**
 * Created by Benoit Brian on 30/01/2016.
 */
public final class GlobalTools {

    private static int _id = 0;
    private static Random rand = new Random();
    public static int GetRecognition(){
        return _id;
    }
    public static void CreateRecognition(){
        _id++;
    }

    public static int GenerateRandomNumber(int min,int max){
        int nb = min + (int)(Math.random() * max);
        return  nb;
    }
}
