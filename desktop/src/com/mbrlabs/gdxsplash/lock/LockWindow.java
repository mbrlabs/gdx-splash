package com.mbrlabs.gdxsplash.lock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

public class LockWindow extends ApplicationAdapter {

    public static final int HEIGHT = 150;
    public static final int WIDTH = 500;

    private Stage stage;

    @Override
    public void create() {
        VisUI.load();
        setupUI();
    }

    private void setupUI() {
        stage = new Stage();
        VisTable root = new VisTable();
        stage.addActor(root);
        root.setFillParent(true);
        root.align(Align.center);

        VisLabel label = new VisLabel("It appears you have already opened an instance of this app.\n\n" +
                "To ensure data integrity this is not possible. If you are sure that no open app " +
                "instance exists delete the lock file ~/.gdxsplash/.lock and restart GdxSplash.");
        label.setAlignment(Align.center);
        label.setWrap(true);
        root.add(label).pad(10).grow().row();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}