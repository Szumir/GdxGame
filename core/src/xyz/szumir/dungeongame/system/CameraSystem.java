package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import jdk.vm.ci.meta.Constant;
import xyz.szumir.dungeongame.component.ImageComponent;
import xyz.szumir.dungeongame.component.PlayerComponent;
import xyz.szumir.dungeongame.component.TransformComponent;
import xyz.szumir.dungeongame.helper.Constants;

public class CameraSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private RenderSystem renderSystem;
    private int mapWidth, mapHeight;
    public CameraSystem(OrthographicCamera camera, RenderSystem renderSystem) {
        super(Family.all(PlayerComponent.class).get());
        this.camera = camera;
        this.renderSystem = renderSystem;

        MapProperties prop = renderSystem.getTiledMap().getProperties();
        this.mapWidth = prop.get("width", Integer.class);
        this.mapHeight = prop.get("height", Integer.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = entity.getComponent(TransformComponent.class);

        camera.position.set(
                (int)transform.position.x,
                (int)transform.position.y,
                camera.position.z
        );
        fixBounds();
        camera.update();
    }

    private void fixBounds() {
        float scaledViewportWidthHalfExtent = camera.viewportWidth * camera.zoom * 0.5f;
        float scaledViewportHeightHalfExtent = camera.viewportHeight * camera.zoom * 0.5f;

        int maxX = (int) (mapWidth*16*Constants.SCALE);
        int maxY = (int) (mapHeight*16*Constants.SCALE);
        if (camera.position.x < scaledViewportWidthHalfExtent)
            camera.position.x = scaledViewportWidthHalfExtent;
        else if (camera.position.x > maxX - scaledViewportWidthHalfExtent)
            camera.position.x = maxX - scaledViewportWidthHalfExtent;

        // Vertical
        if (camera.position.y < scaledViewportHeightHalfExtent)
            camera.position.y = scaledViewportHeightHalfExtent;
        else if (camera.position.y > maxY - scaledViewportHeightHalfExtent)
            camera.position.y = maxY - scaledViewportHeightHalfExtent;
    }

}
