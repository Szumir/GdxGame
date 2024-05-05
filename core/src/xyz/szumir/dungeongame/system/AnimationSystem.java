package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;
import xyz.szumir.dungeongame.component.AnimationComponent;
import xyz.szumir.dungeongame.component.ImageComponent;
import xyz.szumir.dungeongame.component.TransformComponent;
import xyz.szumir.dungeongame.helper.Anim;

public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationComponent;
    private ComponentMapper<ImageComponent> imageComponent;

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class, ImageComponent.class).get());

        this.animationComponent = ComponentMapper.getFor(AnimationComponent.class);
        this.imageComponent = ComponentMapper.getFor(ImageComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ImageComponent image = imageComponent.get(entity);
        AnimationComponent animation = animationComponent.get(entity);
        Anim anim = animation.getCurrentAnimation();
        Animation<TextureRegion> currenAnimation = anim.getAnimation();

        animation.update(deltaTime);

        currenAnimation.setPlayMode(anim.getPlayMode());

        image.image = currenAnimation.getKeyFrame(animation.state);


    }
}
