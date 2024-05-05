package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.bcel.internal.Const;
import xyz.szumir.dungeongame.component.BodyComponent;
import xyz.szumir.dungeongame.component.TransformComponent;
import xyz.szumir.dungeongame.helper.Constants;


public class PhysicSystem extends IteratingSystem {


    private ComponentMapper<TransformComponent> transformComponent = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<BodyComponent> bodyComponent = ComponentMapper.getFor(BodyComponent.class);

    private Array<Entity> entityQueue;
    private World world;
    public PhysicSystem(World world) {
        super(Family.all(TransformComponent.class, BodyComponent.class).get());
        this.world = world;
        this.entityQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        world.step(1/60f, 6, 2);

        for(Entity entity : entityQueue) {
            TransformComponent transform = entity.getComponent(TransformComponent.class);
            BodyComponent body = entity.getComponent(BodyComponent.class);
            Vector2 position = new Vector2(body.body.getPosition());

            transform.position.set(position.x*Constants.PPM, position.y*Constants.PPM);
        }

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(!entityQueue.contains(entity, false)) entityQueue.add(entity);
    }
}
