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

        camera.position.x = (int)transform.position.x;
        camera.position.y = (int)transform.position.y;
        camera.update();
    }
}
