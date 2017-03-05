package com.mygdx.game.desktop.splash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.desktop.MainWindow;

public class SplashScreen extends ApplicationAdapter {

    public static final int SCREEN_HEIGHT = 460;
    public static final int SCREEN_WIDTH = 700;

    private static final int PROGRESS_BAR_HEIGHT = 10;
    private static final Color TEAL = new Color(0x00b695ff);
    private static final Color WHITE = Color.WHITE.cpy();

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Texture img;

    private Lwjgl3Window window;
    private LoadingTask loadingTask;


    @Override
    public void create () {
        VisUI.load();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("MundusSplash.png");
        window = ((Lwjgl3Graphics)Gdx.graphics).getWindow();

        // start loading
        loadingTask = new LoadingTask();
        try {
            loadingTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw background image
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();


        // draw progress bar
        int width = (int) (Gdx.graphics.getWidth() * (loadingTask.getProgress() / 100f));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(WHITE);
        shapeRenderer.rect(0, 0, width, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();

        // check if done loading
        if(loadingTask.isDone()) {
            launchMainWindow();
        }

        Gdx.app.log(SplashScreen.class.getSimpleName(), "Loading Progress: " + loadingTask.getProgress());
    }

    private void launchMainWindow() {
        // config for main window
        Lwjgl3Application app = (Lwjgl3Application) Gdx.app;
        Lwjgl3WindowConfiguration config = new Lwjgl3WindowConfiguration();
        config.setTitle("Mundus");
        Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
            config.setWindowedMode((int) (dm.width * 0.80f), (int) (dm.height * 0.80f));
        } else {
            config.setWindowedMode((int) (dm.width * 0.95f), (int) (dm.height * 0.95f));
        }

        // start main window and close this one
        app.newWindow(new MainWindow(loadingTask.getTexture()), config);
        window.closeWindow();
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        batch.dispose();
        img.dispose();
    }
}