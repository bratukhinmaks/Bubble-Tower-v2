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

	float centerPosition;
	float movingSpeed=10;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		ground = new Texture("ground.png");
		bubble = new Texture("bubble.png");
		centerPosition=Gdx.graphics.getWidth()/2-150;
	}

	@Override
	public void render () {
		if(Gdx.input.justTouched()){
			movingSpeed=0;
		}else{
			if (centerPosition <= Gdx.graphics.getWidth()-400 && centerPosition>150){
				centerPosition-=movingSpeed;
			}else if (centerPosition<150){
				centerPosition=150;
				centerPosition+=movingSpeed;
			}

		}
		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(ground, 0, 0,Gdx.graphics.getWidth(),150);
		batch.draw(bubble, centerPosition, 50,300,200);
		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
