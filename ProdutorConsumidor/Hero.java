import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Hero extends Position{
  private Animation idleAnim;
  private Animation fireAnim;

  private int state;
  private Animation currentAnimation;
  public Hero (){
    this.idleAnim = new Animation(Icons.SPRITE, 45, 45);
    this.idleAnim.setFrameInterval(12, 15);
    this.idleAnim.setSpeed(2);
    
    this.fireAnim = new Animation(Icons.SPRITE, 45, 45);
    this.fireAnim.setFrameInterval(16, 18);
    this.currentAnimation = idleAnim;
  }

  public void fire(){
    this.currentAnimation = this.fireAnim;
    this.currentAnimation.reset();
  }
  
  public void idle(){
    this.currentAnimation = this.idleAnim;
    this.currentAnimation.reset();
  }

  public void setSpeedAnimation(float speed){
    this.currentAnimation.setSpeed(speed);
  }
  public void draw(Graphics2D g){
    this.currentAnimation.draw(g, this.x, this.y, 90, 90);
  }
}