package xyz.szumir.dungeongame.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.szumir.dungeongame.helper.RenderHelper;

import java.util.HashMap;

public class Assets {

    private static AssetManager manager = new AssetManager();
    private static HashMap<String, Object> pixMap = new HashMap<>();

    public static void load() {
        manager.load("player.png", Texture.class);
        manager.load("items/chest_closed.png", Texture.class);
        manager.finishLoading();
    }

    public static <T> T get(String fileName, Class<T> type) {
        T t = manager.get(fileName, type);

        pixMap.put(fileName, ((t instanceof Texture) ?
                RenderHelper.extractPixmapFromTexture((Texture)t) : t
                ));

        return (T) pixMap.get(fileName);

    }

}
