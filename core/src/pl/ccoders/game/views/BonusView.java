package pl.ccoders.game.views;

import com.badlogic.gdx.Gdx;

import pl.ccoders.game.utils.ConstUtils;

public class BonusView extends pl.ccoders.game.views.CircleView {

  private float speed;
  public boolean isPositive;
  public String type;

  public BonusView(float pUnit) {
    posX = (float) Math.random() * 8 * pUnit + 6 * pUnit;
    posY = Gdx.graphics.getHeight();
    speed = -pUnit / 7;
    size = pUnit * 1 / 2;
    isPositive = Math.random() > 0.5;
    blue = 0;
    red = isPositive ? 0 : 1;
    green = isPositive ? 1 : 0;
    type = isPositive ? (Math.random() > 0.5 ? ConstUtils.SCORE_INC : ConstUtils.NEEDLES_LENGTH_DEC)
            : (Math.random() > 0.5 ? ConstUtils.SCORE_DEC : ConstUtils.CREATE_OBSTACLE);
  }

  @Override
  public void move() {
    posY += speed;
  }

}