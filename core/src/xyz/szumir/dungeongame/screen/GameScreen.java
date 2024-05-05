package xyz.szumir.dungeongame.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import xyz.szumir.dungeongame.factory.BodyFactory;
import xyz.szumir.dungeongame.system.*;

public class GameScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Texture texture = new Texture("assets/badlogic.jpg");

    private OrthographicCamera camera;
    private World world;
    Engine engine = new Engine();

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();

        world = new World(new Vector2(0,0), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280,720);
        camera.translate(256,0);

        engine.addEntity(BodyFactory.createPlayer(engine, world, 0,0, 28,32));

        engine.addSystem(new RenderSystem(camera));
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new PhysicSystem(world));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new MovementSystem());



    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override public void resize(int width, int height) {

    }

    @Override public void pause() { }

    @Override public void resume() { }

    @Override public void hide() { }

    @Override
    public void dispose() {

    }
}
