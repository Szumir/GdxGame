package xyz.szumir.dungeongame.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
public class BodyComponent implements Component {
    public Body body;
    private Vector2 velocity = new Vector2();

}
