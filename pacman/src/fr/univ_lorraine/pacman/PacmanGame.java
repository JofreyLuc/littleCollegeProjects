package fr.univ_lorraine.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.univ_lorraine.pacman.view.EndScreen;
import fr.univ_lorraine.pacman.view.GameScreen;
import fr.univ_lorraine.pacman.view.SplashScreen;

public class PacmanGame extends Game {
	GameScreen gamescreen;
	SplashScreen splashscreen;
	
	@Override
	public void create () {
		gamescreen = new GameScreen(this);
		splashscreen = new SplashScreen(this);
		setSplashscreen();
	}

	public void setGameScreen(){
		setScreen(gamescreen);
	}

	public void setSplashscreen(){
		setScreen(splashscreen);
	}

	public void setEndscreen(boolean v, int s) { setScreen(new EndScreen(this, v, s));}
}
