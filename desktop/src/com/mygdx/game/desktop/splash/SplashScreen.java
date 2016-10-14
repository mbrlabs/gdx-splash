package com.mygdx.game.desktop.splash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import com.kotcrab.vis.ui.widget.VisTable;
import com.mygdx.game.desktop.LockFile;
import com.mygdx.game.desktop.MainWindow;

public class SplashScreen extends ApplicationAdapter {

    public static final int HEIGHT = 460;
    public static final int WIDTH = 700;

    private SpriteBatch batch;
    private Texture img;

    private Lwjgl3Window window;
    private LoadingTask loadingTask;

    private Stage stage;
    private VisProgressBar progressBar;

    @Override
    public void create () {
        VisUI.load();
        batch = new SpriteBatch();
        img = new Texture("MundusSplash.png");
        window = ((Lwjgl3Graphics)Gdx.graphics).getWindow();

        setupUI();

        // start loading
        loadingTask = new LoadingTask();
        try {
            loadingTask.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        stage = new Stage();
        VisTable root = new VisTable();
        stage.addActor(root);
        root.setFillParent(true);
        root.align(Align.bottom);

        progressBar = new VisProgressBar(0, 100, 1, false);
        root.add(progressBar).bottom().growX().row();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

        stage.act();
        stage.draw();

        Gdx.app.log(SplashScreen.class.getSimpleName(), "Loading Progress: " + loadingTask.getProgress());
        progressBar.setValue(loadingTask.getProgress());

        if(loadingTask.isDone()) {
            launchMainWindow();
        }
    }

    private void launchMainWindow() {
        // config for main window
        Lwjgl3Application app = (Lwjgl3Application) Gdx.app;
        Lwjgl3WindowConfiguration config = new Lwjgl3WindowConfiguration();
        Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
            config.setWindowedMode((int) (dm.width * 0.80f), (int) (dm.height * 0.80f));
        } else {
            config.setWindowedMode((int) (dm.width * 0.95f), (int) (dm.height * 0.95f));
        }

        // create lock file & schedule for deleting
        LockFile.create();
        LockFile.deleteOnExit();

        // start main window and close this one
        app.newWindow(new MainWindow(loadingTask.getTexture()), config);
        window.closeWindow();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        stage.dispose();
    }
}