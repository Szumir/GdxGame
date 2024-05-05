package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import xyz.szumir.dungeongame.component.BodyComponent;
import xyz.szumir.dungeongame.component.ImageComponent;
import xyz.szumir.dungeongame.component.TransformComponent;
import xyz.szumir.dungeongame.component.ZComparator;
import xyz.szumir.dungeongame.helper.Constants;

import java.util.Comparator;


public class RenderSystem extends IteratingSystem {

    private Array<Entity> renderQueue;
    private ZComparator zComparator;

    private ComponentMapper<TransformComponent> transformMap;
    private ComponentMapper<ImageComponent> imageMap;

    private SpriteBatch spriteBatch;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private OrthographicCamera camera;

    public RenderSystem(OrthographicCamera camera) {
        super(Family.all(TransformComponent.class, ImageComponent.class).get());

        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load("map.tmx");
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Constants.SCALE);

        this.spriteBatch = new SpriteBatch();
        this.renderQueue = new Array<>();
        this.zComparator = new ZComparator();
        this.transformMap = ComponentMapper.getFor(TransformComponent.class);
        this.imageMap = ComponentMapper.getFor(ImageComponent.class);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        renderQueue.sort(zComparator);

        for(Entity entity : renderQueue) {
            TransformComponent t = transformMap.get(entity);
            ImageComponent image = imageMap.get(entity);
            if(image.image == null) continue;

            spriteBatch.draw(image.image, t.position.x, t.position.y, image.image.getRegionWidth()*Constants.SCALE, image.image.getRegionHeight()*Constants.SCALE);
        }
        spriteBatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(!renderQueue.contains(entity, false)) renderQueue.add(entity);
    }

}
