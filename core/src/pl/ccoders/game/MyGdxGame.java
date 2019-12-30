package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import pl.ccoders.game.Bubble;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture ground;
	Texture bubble;
	int maxX = 20, maxY = 28;
	float unitW, unitH;
	float centerPosition;
	float movingSpeed=5;
	float size;
	float sizeIncSpeed;
	boolean wasTouched, stop = false;
	Bubble currentBubble;
	ArrayList<Bubble> bubbles;
	int index;
	boolean wasTouch, createNext;
	float currentPosX, currentPosY;
	Bubble bubbleNext;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ground = new Texture("ground.png");
		bubble = new Texture("bubble.png");
		unitW = Gdx.graphics.getWidth()/maxX;
		unitH = Gdx.graphics.getHeight()/maxY;
		currentBubble = new Bubble(unitW, (maxX/2-3)*unitW,3*unitW);
		currentBubble.size = 6*unitW;
		currentBubble.inMotion = false;
		bubbles = new ArrayList<Bubble>();
		bubbles.add(currentBubble);
		currentPosX = currentBubble.posX - currentBubble.size;
		currentPosY = currentBubble.posY - currentBubble.size/2 + unitW/2;
		createNext = true;
		wasTouch = false;
		bubbleNext = new Bubble(unitW, currentPosX, currentPosY);
		bubbles.add(bubbleNext);
//		DONT DELETE THIS
//		centerPosition=(maxX - 5)*unitW;
//		size = unitW;
//		sizeIncSpeed = unitW/25;
//		tryBubble = new Bubble(unitW, unitW*10-3*unitW, unitW*3);
//		tryBubble.size = 6*unitW;
//		bubbles.add(tryBubble);
	}

	@Override
	public void render () {
//		if(createNext && !wasTouch) {
//			Bubble bubble = new Bubble(unitW, currentPosX, currentPosY);
//			bubbles.add(bubble);
//			if (Gdx.input.isTouched()) {
//				wasTouch = true;
//				bubble.speedY = 0;
//				bubble.speedX = 0;
//				bubble.size += bubble.sizeIncSpeed;
//			}
//			else if (bubble.posX == currentPosX && bubble.posY == currentPosY) {
//				bubble.speedX = 5*unitW;
//				bubble.speedY = 5*unitW;
//			}
//			else if(bubble.posX==currentPosX+currentBubble.size && bubble.posY==currentPosY) {
//				bubble.speedX = -5*unitW;
//				bubble.speedY = 5*unitW;
//			}
//			else if(bubble.posX==currentPosX+currentBubble.size/2 && bubble.posY == currentPosY+currentBubble.size/2) {
//				bubble.speedY = -5*unitW;
//			}
//			else if( wasTouch ) {
//				this.currentPosX = bubble.posX - bubble.size;
//				this.currentPosY = bubble.posY - bubble.size/2+unitW/2;
//				createNext = false;
//			}
//			bubble.posX += bubble.speedX;
//			bubble.posY += bubble.speedY;
//
//		}
//		else {
//			createNext = true;
//			wasTouch = false;
//		}

//		for ( int i = 1; i<=index; i++) {
//			Bubble prev = bubbles.get(i-1);
//			Bubble bubble = new Bubble(unitW, prev.posX-prev.size, prev.posY-prev.size/2+unitW/2);
//			bubbles.add(bubble);
//			if ( !Gdx.input.isTouched() && bubble.inMotion ) {
//				if(bubble.posX == prev.posX-prev.size && bubble.posY == prev.posY-prev.size/2+unitW/2) {
//					bubble.speedX = 5*unitW;
//					bubble.speedY = 5*unitW;
//				}
//				else if(bubble.posX==prev.posX && bubble.posY==prev.posY-prev.size/2+unitW/2) {
//					bubble.speedX = -5*unitW;
//					bubble.speedY = 5*unitW;
//				}
//
//				else if(bubble.posX==prev.posX+prev.size/2 && bubble.posY == prev.posY+prev.size) {
//					bubble.speedY = -5*unitW;
//				}
//			}
//			else if(bubble.inSizeInc && Gdx.input.isTouched()) {
//				bubble.size += bubble.sizeIncSpeed;
//			}
//			else {
//				bubble.inSizeInc = true;
//				bubble.inMotion = false;
//				++index;
//			}
//			bubble.posX += bubble.speedX;
//			bubble.posY += bubble.speedY;
//		}
//      DONT DELETE THIS
//
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
		else if(stop) {
			Bubble bubble1 = new Bubble(unitW, centerPosition, 6*unitW);
			bubble1.size = 5*unitW;
			bubbles.add(bubble1);
		}
//		if( bubbles.get(1).vel < 0 ) {
//			bubbles.get(1).velSpeed = 1/20f;
//		}
//		else if( bubbles.get(1).vel > 3.1415) {
//			bubbles.get(1).velSpeed = -1/20f;
//		}
//		bubbles.get(1).vel += bubbles.get(1).velSpeed;
		batch.begin();
		batch.draw(ground, 0, 0,unitW*maxX,3*unitW);
		currentBubble.draw(batch);
		for(Bubble bubble: bubbles) {
			if(!bubble.equals(currentBubble)) {
				bubble.move();
				if(bubble.posX < currentBubble.posX + currentBubble.size/2) bubble.posX = currentBubble.posX - bubble.size + currentBubble.size/2*(1-(float)Math.cos(bubble.vel));
				else bubble.posX = currentBubble.posX + currentBubble.size/2*(1-(float)Math.cos(bubble.vel));
				bubble.posY = currentBubble.posY + currentBubble.size/2*(1+(float)Math.sin(bubble.vel));
			}
			bubble.draw(batch);
		}
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
