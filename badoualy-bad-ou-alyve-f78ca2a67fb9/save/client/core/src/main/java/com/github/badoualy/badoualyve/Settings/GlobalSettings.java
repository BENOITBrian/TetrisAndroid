package com.github.badoualy.badoualyve.Settings;

/**
 * Created by Benoit Brian on 28/01/2016.
 */
public final class GlobalSettings {
    public static String TITLE = "Tétris";
    public static int SIZE_REAL_WIDTH = 680;
    public static int SIZE_REAL_HEIGHT = 400;
    // V_ for virtual, it's the camera size, not the real one
    public static int V_WIDTH = 190; // Default value, need tweak on android
    public static final int V_HEIGHT = 380;
    public static final int WIDTH_WINDOWS = 680;
    public static final int HEIGHT_WINDOWS = 400;
    public static final int FPS = 30;
    public static final int FPS_WAIT_FOR_START = 900;

    public static final int PLACE_X_CADRE_GAME = 330;
    public static final int PLACE_Y_CADRE_GAME = 6;
    public static final int PADDING_X_CADRE = 6;
    public static final int PADDING_Y_CADRE = 6;

    public static final int PLACE_X_CADRE_PANEL = 167;
    public static final int PLACE_Y_CADRE_PANEL = 6;

    public static final int PLACE_X_CADRE_STAT = 10;
    public static final int PLACE_Y_CADRE_STAT = 6;

    public static final int PLACE_X_MOD_SCORE = 173;
    public static final int PLACE_Y_MOD_SCORE = 10;

    public static final int PLACE_X_MOD_LINE = 173;
    public static final int PLACE_Y_MOD_LINE = 95;

    public static final int PLACE_X_PREVIEW = 182;
    public static final int PLACE_Y_PREVIEW = 280;

    public static final int PLACE_X_PAD = 550;
    public static final int PLACE_Y_PAD = 25;

    public static final int PLACE_X_MOD_USER = 16;

    public static final int SIZE_BLOCK = 19;
    public static final int SIZE_ARRAY_X = V_WIDTH / SIZE_BLOCK;
    public static final int SIZE_ARRAY_Y = V_HEIGHT / SIZE_BLOCK;

    public static final boolean USE_SERVER = false;

}
