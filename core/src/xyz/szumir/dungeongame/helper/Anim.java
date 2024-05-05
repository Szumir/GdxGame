package xyz.szumir.dungeongame.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;

@Getter
    public class Anim {

        private final Texture sheet;
        private final int xOffset, yOffset;
        private final int columns, rows;
        private final int animationWidth, animationHeight;
        private final float frameDuration;
        private final Animation.PlayMode playMode;
         private Animation<TextureRegion> animation;

        public Anim(Texture sheet, int xOffset, int yOffset, int animationWidth, int animationHeight, int columns, int rows, float frameDuration, Animation.PlayMode playMode) {
            this.sheet = sheet;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.columns = columns;
            this.rows = rows;
            this.animationWidth = animationWidth;
            this.animationHeight = animationHeight;
            this.frameDuration = frameDuration;
            this.playMode = playMode;
            initAnimation();
        }
        void initAnimation() {
            TextureRegion[][] tmp = TextureRegion.split(sheet,
                    sheet.getWidth() / columns,
                    sheet.getHeight() / rows);
            TextureRegion[] frames = new TextureRegion[(animationWidth * animationHeight)];
            int index = 0;
            for (int i = yOffset; i < yOffset+animationHeight; i++) {
                for (int j = xOffset; j < xOffset+animationWidth; j++) {
                    if(tmp[i][j] == null) continue;
                    frames[index++] = tmp[i][j];
                }
            }
            animation = new Animation<>(frameDuration, frames);
        }
    }