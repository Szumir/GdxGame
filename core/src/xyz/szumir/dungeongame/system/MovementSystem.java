package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import xyz.szumir.dungeongame.component.AnimationComponent;
import xyz.szumir.dungeongame.component.BodyComponent;
import xyz.szumir.dungeongame.component.PlayerComponent;

import java.util.Arrays;

public class MovementSystem extends IteratingSystem {

    private Array<Entity> entityQueue;
    public MovementSystem() {
        super(Family.all(BodyComponent.class, PlayerComponent.class, AnimationComponent.class).get());
        this.entityQueue = new Array<>();
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity entity : entityQueue) {
            BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
            AnimationComponent animationComponent = entity.getComponent(AnimationComponent.class);

            Body body = bodyComponent.body;

            float[] vel = getVelocity();
            float speed = 8F;

            body.setLinearVelocity(
                    vel[0] * speed,
                    vel[1] * speed
            );
                animationComponent.setCurrentAnimation((
                        Math.abs(vel[0]) > 0 ||  Math.abs(vel[1]) > 0 ?
                                AnimationComponent.AnimationType.WALK :
                                AnimationComponent.AnimationType.IDLE)
                );

                Arrays.stream(animationComponent.getCurrentAnimation().getAnimation().getKeyFrames()).forEach(e -> {
                    if(!e.isFlipX() && vel[0] < 0) e.flip(true, false);
                    if(e.isFlipX()&& vel[0] > 0) e.flip(true, false);
                });


        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(!entityQueue.contains(entity, false)) entityQueue.add(entity);
    }

    private float[] getVelocity() {
        int xFactor = 0;
        int yFactor = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) yFactor=1;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) yFactor=-1;

        if(Gdx.input.isKeyPressed(Input.Keys.A)) xFactor=-1;
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) xFactor=1;

        return new float[] {xFactor, yFactor};

    }
}
