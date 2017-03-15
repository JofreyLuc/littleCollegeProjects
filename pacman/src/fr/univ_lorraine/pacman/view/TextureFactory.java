package fr.univ_lorraine.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.univ_lorraine.pacman.model.VisualChars;

public class TextureFactory {
    private static TextureFactory instance = new TextureFactory();
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));
    private static final float WALKING_FRAME_DURATION = 0.06f;

    public static TextureFactory getInstance(){
        return instance;
    }

    private TextureRegion block = atlas.findRegion("bloc");
    private TextureRegion pellet = atlas.findRegion("pellet");
    private TextureRegion superPellet = atlas.findRegion("superPellet");
    private TextureRegion blinky = atlas.findRegion("ghost1");
    private TextureRegion pinky = atlas.findRegion("ghost2");
    private TextureRegion inky = atlas.findRegion("ghost3");
    private TextureRegion clyde = atlas.findRegion("ghost4");
    private TextureRegion deadGhost = atlas.findRegion("ghostDead");
    private TextureRegion huntedGhost = atlas.findRegion("ghostHunted");
    private TextureRegion red = atlas.findRegion("redsquare");
    private TextureRegion pink = atlas.findRegion("pinksquare");
    private TextureRegion blue = atlas.findRegion("bluesquare");
    private TextureRegion orange = atlas.findRegion("orangesquare");

    private TextureRegion[] walkLeftFrames = new TextureRegion[4], walkRightFrames = new TextureRegion[4], walkUpFrames = new TextureRegion[4], walkDownFrames = new TextureRegion[4];
    private Animation walkLeftAnimation, walkRightAnimation, walkUpAnimation, walkDownAnimation;

    public TextureFactory(){
        walkDownFrames[0] = atlas.findRegion("pacmanDown");
        walkDownFrames[1] = atlas.findRegion("pacmanDown-2");
        walkDownFrames[2] = atlas.findRegion("pacmanDown");
        walkDownFrames[3] = atlas.findRegion("pacman-3");

        walkUpFrames[0] = atlas.findRegion("pacmanUp");
        walkUpFrames[1] = atlas.findRegion("pacmanUp-2");
        walkUpFrames[2] = atlas.findRegion("pacmanUp");
        walkUpFrames[3] = atlas.findRegion("pacman-3");

        walkLeftFrames[0] = atlas.findRegion("pacmanLeft");
        walkLeftFrames[1] = atlas.findRegion("pacmanLeft-2");
        walkLeftFrames[2] = atlas.findRegion("pacmanLeft");
        walkLeftFrames[3] = atlas.findRegion("pacman-3");

        walkRightFrames[0] = atlas.findRegion("pacmanRight");
        walkRightFrames[1] = atlas.findRegion("pacmanRight-2");
        walkRightFrames[2] = atlas.findRegion("pacmanRight");
        walkRightFrames[3] = atlas.findRegion("pacman-3");

        walkLeftAnimation = new Animation(WALKING_FRAME_DURATION, walkLeftFrames);
        walkRightAnimation = new Animation(WALKING_FRAME_DURATION, walkRightFrames);
        walkUpAnimation = new Animation(WALKING_FRAME_DURATION, walkUpFrames);
        walkDownAnimation = new Animation(WALKING_FRAME_DURATION, walkDownFrames);
    }


    public VisualChars getVisuPacman(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)walkLeftFrames[0].getRegionWidth(), (float)walkLeftFrames[0].getRegionHeight()), walkLeftFrames[0]);
    }

    public void updateVisuPacman(int dir, float stateTime, VisualChars vc){
        switch (dir) {
            case 1:
                vc.setTexture(walkLeftAnimation.getKeyFrame(stateTime, true));
                break;
            case 2:
                vc.setTexture(walkUpAnimation.getKeyFrame(stateTime, true));
                break;
            case 3:
                vc.setTexture(walkRightAnimation.getKeyFrame(stateTime, true));
                break;
            default:
                vc.setTexture(walkDownAnimation.getKeyFrame(stateTime, true));
                break;
        }
    }

    public VisualChars getVisuRed(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)red.getRegionWidth(), (float)red.getRegionHeight()), red);
    }

    public VisualChars getVisuPink(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)pink.getRegionWidth(), (float)pink.getRegionHeight()), pink);
    }

    public VisualChars getVisuBlue(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)blue.getRegionWidth(), (float)blue.getRegionHeight()), blue);
    }

    public VisualChars getVisuOrange(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)orange.getRegionWidth(), (float)orange.getRegionHeight()), orange);
    }

    public VisualChars getVisuBlock(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)block.getRegionWidth(), (float)block.getRegionHeight()), block);
    }

    public VisualChars getVisuPellet(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)pellet.getRegionWidth(), (float)pellet.getRegionHeight()), pellet);
    }

    public VisualChars getVisuSPellet(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)superPellet.getRegionWidth(), (float)superPellet.getRegionHeight()), superPellet);
    }

    public VisualChars getVisuBlinky(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)blinky.getRegionWidth(), (float)blinky.getRegionHeight()), blinky);
    }
    public VisualChars getVisuPinky(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)pinky.getRegionWidth(), (float)pinky.getRegionHeight()), pinky);
    }
    public VisualChars getVisuInky(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)inky.getRegionWidth(), (float)inky.getRegionHeight()), inky);
    }
    public VisualChars getVisuClyde(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)clyde.getRegionWidth(), (float)clyde.getRegionHeight()), clyde);
    }

    public VisualChars getVisuDeadGhost(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)deadGhost.getRegionWidth(), (float)deadGhost.getRegionHeight()), deadGhost);
    }

    public VisualChars getVisuHuntedGhost(float x, float y){
        return new VisualChars(new Rectangle(x, y, (float)huntedGhost.getRegionWidth(), (float)huntedGhost.getRegionHeight()), huntedGhost);
    }
}
