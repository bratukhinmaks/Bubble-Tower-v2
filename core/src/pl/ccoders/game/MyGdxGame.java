package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture ground;
	Texture bubble;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		ground = new Texture("ground.png");
		bubble = new Texture("bubble.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(ground, 0, 0,Gdx.graphics.getWidth(),150);
		batch.draw(bubble, Gdx.graphics.getWidth()/2-150, 50,300,200);
		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
