package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import inf112.skeleton.app.controller.GameController;
import inf112.skeleton.app.model.GameModel;
import inf112.skeleton.app.model.mapfactories.InsaneMapFactory;
import inf112.skeleton.app.view.screens.ScreenManager;

public class RomvsenIsolasjon extends Game {

	private GameModel model;
	private GameController controller;

    @Override
    public void create() {

        model = new GameModel(new InsaneMapFactory());
        ScreenManager.getInstance().initialize(this, model);

		controller = new GameController(model);
		Gdx.input.setInputProcessor(controller);
        Gdx.graphics.setForegroundFPS(60);

    }

    @Override
    public void dispose() {
        super.dispose();
        ScreenManager.getInstance().dispose();
    }
    
}
