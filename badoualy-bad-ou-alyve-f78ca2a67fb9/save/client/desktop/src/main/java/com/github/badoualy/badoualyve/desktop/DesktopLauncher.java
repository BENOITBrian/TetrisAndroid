
package com.github.badoualy.badoualyve.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.badoualy.badoualyve.ui.TetrisGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // Customize your desktop application window

        config.title = TetrisGame.Settings.TITLE;
        config.width = TetrisGame.Settings.WIDTH_WINDOWS;
        config.height = TetrisGame.Settings.HEIGHT_WINDOWS;
        config.resizable = false;
        //config.addIcon("icon.png", Files.FileType.Internal);
        new LwjglApplication(new TetrisGame(), config);
    }
}
