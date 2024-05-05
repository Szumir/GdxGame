package xyz.szumir.dungeongame.component;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {
    private ComponentMapper<TransformComponent> transformMap;

    public ZComparator() { transformMap = ComponentMapper.getFor(TransformComponent.class); }

    @Override
    public int compare(Entity entityA, Entity entityB) {
        return (int)transformMap.get(entityB).position.y -
                (int)transformMap.get(entityA).position.y;
    }
}