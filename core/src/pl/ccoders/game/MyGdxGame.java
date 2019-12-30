package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import pl.ccoders.game.Bubble;


public class MyGdxGame extends ApplicationAdapter {
	int maxUnits = 20;
	float unit;
	boolean wasTouched;
	Bubble currentBubble, nextBubble;
	ArrayList<Bubble> bubbles;
	ShapeRenderer shape;


	@Override
	public void create () {
		unit = Gdx.graphics.getWidth()/maxUnits;
		currentBubble = new Bubble(unit, maxUnits/2*unit,3/2*unit);
		currentBubble.size = 3*unit;
		currentBubble.inMotion = false;
		bubbles = new ArrayList<>();
		bubbles.add(currentBubble);
		nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY);
		bubbles.add(nextBubble);
		shape = new ShapeRenderer();
		wasTouched = false;
	}

	@Override
	public void render () {
		if(Gdx.input.isTouched()) {
			wasTouched = true;
			nextBubble.inMotion = false;
			nextBubble.velSpeed = 0f;
			if(nextBubble.size < 4*unit) {
				nextBubble.size += nextBubble.sizeIncSpeed;
			}

		}
		else if (!Gdx.input.isTouched() && wasTouched) {
			currentBubble = nextBubble;
			nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY);
			bubbles.add(nextBubble);
			wasTouched = false;
		}

		shape.begin(ShapeRenderer.ShapeType.Filled);
		nextBubble.move();
		nextBubble.posX = currentBubble.posX - currentBubble.size*(float)Math.cos(nextBubble.vel)-nextBubble.size*(float)Math.cos(nextBubble.vel);
		nextBubble.posY = currentBubble.posY + currentBubble.size*(float)Math.sin(nextBubble.vel)+nextBubble.size*(float)Math.sin(nextBubble.vel);
		for( Bubble bubble: bubbles) {
			bubble.draw(shape);
		}
		shape.end();

	}
	
	@Override
	public void dispose () {

	}
}
