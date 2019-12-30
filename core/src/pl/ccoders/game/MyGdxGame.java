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
	float posYSum;
	Bubble currentBubble, nextBubble;
	ArrayList<Bubble> bubbles;
	ShapeRenderer shape;

	public boolean checkBubblesCollision(Bubble bubble1, Bubble bubble2) {
		float distance = (float)Math.sqrt(Math.pow(Math.abs(bubble1.posX-bubble2.posX), 2)+Math.pow(Math.abs(bubble1.posY-bubble2.posY), 2));
		if( distance < bubble1.size + bubble2.size ) return true;
		return false;
	}


	@Override
	public void create () {
		unit = Gdx.graphics.getWidth()/maxUnits;
		currentBubble = new Bubble(unit, maxUnits/2*unit,3/2*unit, 0f, 0f);
		currentBubble.size = 3*unit;
		currentBubble.inMotion = false;
		bubbles = new ArrayList<>();
		bubbles.add(currentBubble);
		nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, 0f, 0f);
		bubbles.add(nextBubble);
		shape = new ShapeRenderer();
		wasTouched = false;
		posYSum = 0;
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
			for( Bubble bubble: bubbles) {
				if(!bubble.equals(nextBubble)&&!bubble.equals(currentBubble)) {
					if(checkBubblesCollision(nextBubble, bubble)) {
						nextBubble.red = 1;
						nextBubble.green = 0;
						nextBubble.blue = 0;
					}
				}
			}
		}
		else if (!Gdx.input.isTouched() && wasTouched) {
			currentBubble = nextBubble;
			nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, bubbles.size()*unit/200, bubbles.size()/50);
			bubbles.add(nextBubble);
			if(currentBubble.posY > 20*unit) {
				for(Bubble bubble: bubbles) {
					bubble.posY -= currentBubble.posY-20*unit;
				}
			}
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
