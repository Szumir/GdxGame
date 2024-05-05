package xyz.szumir.dungeongame.helper;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RenderHelper {

    public static Texture extractPixmapFromTexture(Texture txt) {
        txt.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //            this.texture = txt;

        if(!txt.getTextureData().isPrepared())
            txt.getTextureData().prepare();

        Pixmap pixmap = new Pixmap(
                txt.getWidth(),
                txt.getHeight(),
                txt.getTextureData().getFormat()
        );
        pixmap.drawPixmap(
                txt.getTextureData().consumePixmap(), // The other Pixmap
                0, // The target x-coordinate (top left corner)
                0, // The target y-coordinate (top left corner)
                0, // The source x-coordinate (top left corner)
                0, // The source y-coordinate (top left corner)
                txt.getWidth(), // The width of the area from the other Pixmap in pixels
                txt.getHeight() // The height of the area from the other Pixmap in pixels
        );

        return new Texture(pixmap);
    }


}
