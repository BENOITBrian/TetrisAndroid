package com.github.badoualy.badoualyve.ToolsB;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.badoualy.badoualyve.Settings.AssetsSettings;

/** Utils class to makes it easy to load resources from assets */
public final class GdxTools {

    /** Game's asset manager */
    public static final AssetManager assetManager = new AssetManager();

    private static BitmapFont defaultFontSmall, defaultFont;

    public GdxTools() {}

    /** Preload all the assets necessary for the intro stage */
    public static void loadIntroAssets() {
        assetManager.load(AssetsSettings.ECRAN_ACCEUIL, Texture.class);
    }

    /** Preload all the assets necessary for the home stage */
    public static void loadHomeAssets() {
        assetManager.load(AssetsSettings.BC_HOME_PAGE, Texture.class);
        assetManager.load(AssetsSettings.BG_BUTTON, Texture.class);
        assetManager.load(AssetsSettings.CADRE_INPUT, Texture.class);
    }

    public static void loadBlockAssets(){
        assetManager.load(AssetsSettings.BLOCK_BLUE, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_BLUEF, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_RED, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_SKULL, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_PURPLE, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_YELLOW, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_GREEN, Texture.class);
        assetManager.load(AssetsSettings.BLOCK_GREENF, Texture.class);

        assetManager.load(AssetsSettings.CADRE_GAME, Texture.class);
        assetManager.load(AssetsSettings.CADRE_PANEL, Texture.class);
        assetManager.load(AssetsSettings.BG_MODULE, Texture.class);
        assetManager.load(AssetsSettings.BG_GAME, Texture.class);

        assetManager.load(AssetsSettings.PREVIEW_L, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_LREV, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_MID, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_SQUARE, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_STICK, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_Z, Texture.class);
        assetManager.load(AssetsSettings.PREVIEW_ZREV, Texture.class);

        assetManager.load(AssetsSettings.PAD_DOWN, Texture.class);
        assetManager.load(AssetsSettings.PAD_LEFT, Texture.class);
        assetManager.load(AssetsSettings.PAD_MID, Texture.class);
        assetManager.load(AssetsSettings.PAD_RIGHT, Texture.class);
        assetManager.load(AssetsSettings.PAD_UP, Texture.class);

    }

    /** Shortcut new Image(getTexture(name)) */
    public static Image createImageFromTexture(String name) {
        return new Image(getTexture(name));
    }

    /** Shortcut assetManager.get(name, Texture.class) */
    public static Texture getTexture(String name) {
        return assetManager.get(name, Texture.class);
    }

    /** Shortcut assetManager.get(name, TextureAtlas.class) */
    public static TextureAtlas getTextureAtlas(String name) {
        return assetManager.get(name, TextureAtlas.class);
    }

    /** Shortcut assetManager.get(name, BitmapFont.class) */
    public static BitmapFont getFont(String name) {
        return assetManager.get(name, BitmapFont.class);
    }

    private static void initDefaultFontIfNeeded(int size,int borderWidth) {

            // Use gdx-freetype extension to create the default font
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(AssetsSettings.VCR_FONT));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.color = new Color(Color.WHITE);

            parameter.borderWidth = borderWidth;
            parameter.borderColor = Color.BLACK;
            // Normal font
            parameter.size = size;
            defaultFont = generator.generateFont(parameter);

            generator.dispose(); // don't forget to dispose to avoid memory leaks!

    }

    public static BitmapFont getDefaultFont(int size,int borderWidth) {
        initDefaultFontIfNeeded(size,borderWidth);
        return defaultFont;
    }

/*    public static BitmapFont getDefaultFontSmall() {
        initDefaultFontIfNeeded();
        return defaultFontSmall;
    }*/
}