import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Animation{
  private final BufferedImage src;
  private final int sizeX;
  private final int sizeY;

  private int startFrame = 0;
  private int endFrame = 0;
  private int currentFrame = 0;

  private float speed = 1;
  private float currentTime = 0;
  private boolean loop = true;

  public Animation(BufferedImage src, int sizeX, int sizeY){
    this.src = src;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  public void setFrameInterval(int start, int end){
    this.currentFrame = start;
    this.endFrame = end;
    this.startFrame = start;
  }

  public int frameCount(){
    return (this.endFrame - this.startFrame);
  }

  public void setSpeed(float speed){
    this.speed = speed;
    this.currentTime = 0;
  }

  public void setLoop(boolean loop){
    this.loop = loop;
  }

  public void next(){
    if (currentFrame < this.endFrame){
      this.currentFrame++;
    }else if(loop && currentFrame == this.endFrame){
      this.currentFrame = startFrame;
    }
  }

  public void prev(){
    if (currentFrame > this.startFrame){
      this.currentFrame--;
    }else if(loop && currentFrame == this.startFrame){
      this.currentFrame = endFrame;
    }
  }

  public void reset(){
    //this.currentFrame = this.startFrame;
  }

  public void draw(Graphics2D g, int sx, int sy, int w, int h){
    if(src == null){
      System.out.println("Animation missing image src");
      return;
    }
    
    int x = this.currentFrame % (this.src.getWidth() / sizeX);
    int y = this.currentFrame / (this.src.getWidth() / sizeX);

    int dx0 = x * sizeX;
    int dy0 = y * sizeY;

    int dx1 = dx0 + sizeX;
    int dy1 = dy0 + sizeY;

    if(currentTime >= Const.TICK_PER_SECONDS){
      for(int i = 0; i < currentTime / Const.TICK_PER_SECONDS; i++)
        this.next();
      currentTime = 0;
    }
    currentTime += speed * Const.TICK_PER_SECONDS;
    g.drawImage(this.src, sx, sy, sx + w, sy + h, dx0, dy0, dx1, dy1, null);
  }
}