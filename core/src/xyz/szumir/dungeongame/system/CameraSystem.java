package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import xyz.szumir.dungeongame.component.ImageComponent;
import xyz.szumir.dungeongame.component.PlayerComponent;
import xyz.szumir.dungeongame.component.TransformComponent;

public class CameraSystem extends IteratingSystem {
    private OrthographicCamera camera;
    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = entity.getComponent(TransformComponent.class);

        camera.position.set(
                (int)transform.position.x,
                (int)transform.position.y,
                camera.position.z
        );
        camera.update();
    }


    private void fixBounds() {
        float scaledViewportWidthHalfExtent = camera.viewportWidth * camera.zoom * 0.5f;
        float scaledViewportHeightHalfExtent = camera.viewportHeight * camera.zoom * 0.5f;

        int xmax = 1280;
        int ymax = 720;
        if (camera.position.x < scaledViewportWidthHalfExtent)
            camera.position.x = scaledViewportWidthHalfExtent;
        else if (camera.position.x > xmax - scaledViewportWidthHalfExtent)
            camera.position.x = xmax - scaledViewportWidthHalfExtent;

        // Vertical
        if (camera.position.y < scaledViewportHeightHalfExtent)
            camera.position.y = scaledViewportHeightHalfExtent;
        else if (camera.position.y > ymax - scaledViewportHeightHalfExtent)
            camera.position.y = ymax - scaledViewportHeightHalfExtent;
    }

}
