package pl.ccoders.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicModel {
  private Music mBackgroundMusic, mPositiveBonusMusic, mNegativeBonusMusic;

  public void initMusic() {
    mBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));
    mBackgroundMusic.setLooping(true);
    mBackgroundMusic.setVolume(0.5f);
    mPositiveBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("posBonMus.mp3"));
    mPositiveBonusMusic.setLooping(false);
    mPositiveBonusMusic.setVolume(1f);
    mNegativeBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("negBonMus.wav"));
    mNegativeBonusMusic.setLooping(false);
    mNegativeBonusMusic.setVolume(1f);
  }

  public void stopBackgroundMusic() {
    mBackgroundMusic.stop();
  }

  public void playBackgroundMusic() {
    mBackgroundMusic.play();
  }

  public void playPositiveBonusMusic() {
    mPositiveBonusMusic.play();
  }

  public void playNegativeBonusMusic() {
    mNegativeBonusMusic.play();
  }
}
