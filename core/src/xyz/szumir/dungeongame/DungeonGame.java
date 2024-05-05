package xyz.szumir.dungeongame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import xyz.szumir.dungeongame.assets.Assets;
import xyz.szumir.dungeongame.screen.GameScreen;

public class DungeonGame extends Game {
	public static DungeonGame INSTANCE;
	public SpriteBatch batch;
	@Override
	public void create () {
		INSTANCE = new DungeonGame();
		batch = new SpriteBatch();
		Assets.load();

		setScreen(new GameScreen());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();

	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
