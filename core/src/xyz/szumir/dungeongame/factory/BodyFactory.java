package xyz.szumir.dungeongame.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import xyz.szumir.dungeongame.assets.Assets;
import xyz.szumir.dungeongame.component.*;
import xyz.szumir.dungeongame.helper.Anim;
import xyz.szumir.dungeongame.helper.Constants;

public class BodyFactory {

    public static final short WORLD_ENTITY = 0x1 << 1;
    public static final short PLAYER_ENTITY = 0x1 << 2;
    public static final short NPC_ENTITY = 0x1 << 3;
    public static Entity createPlayer(Engine engine, World world, float x, float y, float width, float height) {

        Entity entity = engine.createEntity();
        AnimationComponent animationComponent = new AnimationComponent();
        ImageComponent imageComponent = new ImageComponent();
        TransformComponent transformComponent = new TransformComponent();
        BodyComponent bodyComponent = new BodyComponent();
        Anim anim = new Anim( Assets.get("player.png", Texture.class),
                4, 2, 1, 1, 8, 12, 0.2F, Animation.PlayMode.LOOP_PINGPONG);
        Anim walk = new Anim( Assets.get("player.png", Texture.class),
                2, 1, 1, 3, 8, 12, 0.2F, Animation.PlayMode.LOOP_PINGPONG);

        animationComponent.addAnimation(AnimationComponent.AnimationType.IDLE, anim);
        animationComponent.addAnimation(AnimationComponent.AnimationType.WALK, walk);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.bullet = false;
        bodyDef.position.set(x/ Constants.PPM, y/ Constants.PPM);

        bodyComponent.setBody(world.createBody(bodyDef));
        float hx=width/ Constants.PPM;
        float hy=height/ Constants.PPM;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(hx,hy);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = PLAYER_ENTITY;
        fixtureDef.filter.maskBits = WORLD_ENTITY;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        bodyComponent.body.createFixture(fixtureDef);

        entity.add(new PlayerComponent());
        entity.add(animationComponent);
        entity.add(transformComponent);
        entity.add(imageComponent);
        entity.add(bodyComponent);
        return entity;
    }

}
