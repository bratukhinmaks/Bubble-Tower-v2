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
	int maxX = 20, maxY = 28;
	float unitW, unitH;
	float centerPosition;
	float movingSpeed=5;
	float size;
	float sizeIncSpeed;
	boolean wasTouched, stop = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		ground = new Texture("ground.png");
		bubble = new Texture("bubble.png");
		unitW = Gdx.graphics.getWidth()/maxX;
		unitH = Gdx.graphics.getHeight()/maxY;
		centerPosition=(maxX - 5)*unitW;
		size = unitW;
		sizeIncSpeed = unitW/25;
	}

	@Override
	public void render () {
		if(Gdx.input.isTouched() && !wasTouched && size < 8*unitW){
			size+= sizeIncSpeed;
			stop = true;
		}
		else if( !stop ){

			if (centerPosition == unitW*(maxX-5)) {
				movingSpeed = -5;
			}
			else if ( centerPosition == 5*unitW) {
				movingSpeed = 5;
			}

			centerPosition += movingSpeed;
		}
		batch.begin();
		batch.draw(ground, 0, 0,unitW*maxX,150);
		batch.draw(bubble, centerPosition-size/2, 50,size,size);
		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
