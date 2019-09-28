import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;

public class Zombie extends Position{
  private Animation idleAnim;
  private Animation walkAnim;

  private Animation dyingAnim;
  private Animation takeAnim;
  private Animation currentAnimation;

  private int life;
  public String state;
  public float alpha = 1;

  private final int maxLife = 5;

  public Zombie (){
    this.walkAnim = new Animation(Icons.SPRITE, 45, 45);
    this.walkAnim.setFrameInterval(0, 5);
    this.walkAnim.setSpeed(2);

    this.idleAnim = new Animation(Icons.SPRITE, 45, 45);
    this.idleAnim.setFrameInterval(19, 22);
    this.idleAnim.setSpeed(2);

    this.takeAnim = new Animation(Icons.SPRITE, 45, 45);
    this.takeAnim.setFrameInterval(5, 6);
    this.takeAnim.setSpeed(5);

    this.dyingAnim = new Animation(Icons.SPRITE, 45, 45);
    this.dyingAnim.setFrameInterval(5, 11);
    this.dyingAnim.setLoop(false);
    this.dyingAnim.setSpeed(2);

    this.currentAnimation = this.walkAnim;
    this.life = maxLife;
  }

  /* ***************************************************************
  * Metodo: takeDamage
  * Funcao: Retira a vida do zombie
  * Retorna: se true se morre e false se estiver vivo 
  *************************************************************** */
  public boolean takeDamage(){
    life--;
    if (life <= 0){
      life = 0;
      this.currentAnimation = dyingAnim; //Seta a animacao para morrer
      return true; //Returna true se morre
    }else{
      this.currentAnimation = takeAnim; //Seta a animacao para levar dano
      this.addPosition(10, 0); //Faz com que o zombie ande um pouco pra tras
      return false; //Retorna false se estiver vivo
    }
  }

  /* ***************************************************************
  * Metodo: isDead
  * Funcao: ver se o zombie esta morto
  * Retorna: se true se morre e false se estiver vivo 
  *************************************************************** */
  public boolean isDead(){
    return this.life == 0;
  }

  /* ***************************************************************
  * Metodo: setSpeed
  * Funcao: seta a velocidade da animacao atual
  *************************************************************** */
  public void setSpeed(float speed){
    this.currentAnimation.setSpeed(speed);
  }
  
  /* ***************************************************************
  * Metodo: idle
  * Funcao: seta animacao para idle
  *************************************************************** */
  public void idle(){
    this.state = "idle";
    this.currentAnimation = idleAnim;
  }

  /* ***************************************************************
  * Metodo: walk
  * Funcao: seta animacao para walk
  *************************************************************** */
  public void walk(){
    this.state = "walk";
    this.currentAnimation = walkAnim;
  }

  /* ***************************************************************
  * Metodo: draw
  * Funcao: desenha a animacao na tela
  *************************************************************** */
  public void draw(Graphics2D g){
    this.currentAnimation.draw(g, this.x, this.y, 90, 90);
    if(!this.isDead()){
      g.setColor(Color.BLACK);
      g.fillRect(this.x + 45 / 2, this.y - 4, 30, 3);
      g.setColor(Color.RED);
      g.fillRect(this.x + 45 / 2 + 1, this.y - 3, (int)(30 * this.life / (float)this.maxLife), 1);
    }
  }
}