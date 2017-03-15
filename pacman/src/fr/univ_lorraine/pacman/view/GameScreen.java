package fr.univ_lorraine.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.univ_lorraine.pacman.PacmanGame;
import fr.univ_lorraine.pacman.controller.GameListener;
import fr.univ_lorraine.pacman.model.GameElement;
import fr.univ_lorraine.pacman.model.GameMoveableElement;
import fr.univ_lorraine.pacman.model.World;

public class GameScreen extends ScreenAdapter {

    private PacmanGame pg;
    private SpriteBatch batch;
    private World w;
    private FPSLogger fps;
    private OrthographicCamera cam;
    private Viewport fv;
    private GameListener gl;
    private int pathfindCounter = 0;

    public GameScreen(PacmanGame pacg){
        //Définition de la musique
        Music music = Gdx.audio.newMusic(Gdx.files.internal("theme.mp3"));
        music.setVolume(0.6f);
        music.setLooping(true);

        w = new World();
        pg = pacg;
        batch = new SpriteBatch();
        fps = new FPSLogger();

        //Défintion du listener
        gl = new GameListener(w);
        Gdx.input.setInputProcessor(gl);

        //Défintion de la caméra
        cam = new OrthographicCamera();
        fv = new FitViewport(w.getWorldWidth() * w.getPpux(), w.getWorldHeight() * w.getPpuy(), cam);
        cam.position.set(w.getWorldWidth() * w.getPpux() / 2f, w.getWorldHeight() * w.getPpuy() / 2f, 0);
        cam.update();

        //Lancement de la musique
        music.play();
    }

    @Override
    public void render(float delta) {
        //Tant que pas gameover
        if (!w.isFinished()){
            cam.update();
            batch.setProjectionMatrix(cam.combined);

            //Updates
            gl.updateAccelerometer();
            udpatePosMoveableElements(delta);
            updateCollisions();
            if (pathfindCounter == 0){
                updateGhostsPath();
                pathfindCounter = 20;
            }

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            for (GameElement gm : w){
                gm.updateBB(w.getPpux(), w.getPpuy());
                batch.draw(gm.getTexture(), gm.getPosition().x * w.getPpux(), gm.getPosition().y * w.getPpuy(), gm.getWidth() * w.getPpux(), gm.getHeight() * w.getPpuy());
            }
            /* Dessin du chemin du fantôme */
            for (GameElement gm : w.getBlinky()){
                gm.updateBB(w.getPpux(), w.getPpuy());
                batch.draw(gm.getTexture(), gm.getPosition().x * w.getPpux(), gm.getPosition().y * w.getPpuy(), gm.getWidth() * w.getPpux(), gm.getHeight() * w.getPpuy());
            }
            batch.end();
            fps.log();

            pathfindCounter--;
        } else {
            pg.setEndscreen(w.pacmanWon(), w.getPacman().getScore()); //Chargement de l'écran de fin
        }
    }

    // Update de la direction du fantôme
    private void updateGhostsPath(){
        w.getBlinky().updateDir();
    }

    //Update des positions des éléments mobiles
    private void udpatePosMoveableElements(float delta){
        w.getPacman().update(delta);
        w.getBlinky().update(delta);
        w.getPinky().update(delta);
        w.getInky().update(delta);
        w.getClyde().update(delta);
    }

    //Update des collisions des éléments mobiles
    private void updateCollisions(){
        GameElement ge = w.getPacman().prepareCollision();
        if (ge != null){
            w.getPacman().hasCollidedWith(ge);
        }
        ge = w.getBlinky().prepareCollision();
        if (ge != null){
            w.getBlinky().hasCollidedWith(ge);
        }
        ge = w.getPinky().prepareCollision();
        if (ge != null){
            w.getPinky().hasCollidedWith(ge);
        }
        ge = w.getInky().prepareCollision();
        if (ge != null){
            w.getInky().hasCollidedWith(ge);
        }
        ge = w.getClyde().prepareCollision();
        if (ge != null){
            w.getClyde().hasCollidedWith(ge);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        fv.update(width, height);
    }
}
