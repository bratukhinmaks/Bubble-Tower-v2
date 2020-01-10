package pl.ccoders.game.views;

public class BubbleView extends pl.ccoders.game.views.CircleView {

  public float sizeIncSpeed;
  public boolean inMotion;
  private boolean inSizeInc;
  public float vel, velSpeed;
  private float sizeIncSpeedIncrease;
  private float velSpeedIncrease;

  public BubbleView(float pUnit, float pPosX, float pPosY, float pSizeIncSpeedIncrease, float pVelSpeedIncrease) {
    posX = pPosX;
    posY = pPosY;
    inMotion = true;
    inSizeInc = false;
    sizeIncSpeedIncrease = pSizeIncSpeedIncrease;
    velSpeedIncrease = pVelSpeedIncrease;
    sizeIncSpeed = pUnit / 30 + pSizeIncSpeedIncrease;
    size = pUnit / 2;
    vel = 0f;
    velSpeed = 1 / 10f + pVelSpeedIncrease;
    red = (float) Math.random();
    blue = (float) Math.random();
    green = (float) Math.random();
    if (red + green + blue < 0.5) {
      red = (float) Math.random();
      blue = (float) Math.random();
      green = (float) Math.random();
    }
  }

  @Override
  public void move() {
    if (inMotion) {
      if (vel < 0) {
        velSpeed = 1 / 15f + velSpeedIncrease;
      } else if (vel > 3.1415) {
        velSpeed = -1 / 15f - velSpeedIncrease;
      }
    }
    vel += velSpeed;
  }
}
