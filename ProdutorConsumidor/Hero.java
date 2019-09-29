/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Hero
* Funcao: Classe do hero
****************************************************************/

import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Hero extends Position{
  private Animation idleAnim; //Animacao de parado
  private Animation fireAnim;	//Animacao de tiro

  private int state; 
  private Animation currentAnimation; //Animacao que esta rodando

  public Hero (){
    this.idleAnim = new Animation(Icons.SPRITE, 45, 45); //Carrega a animacao
    this.idleAnim.setFrameInterval(12, 15);							 //Seta os tiles que a animacao tem
    this.idleAnim.setSpeed(2); 													 //Seta a velocidade da animacao
    
    this.fireAnim = new Animation(Icons.SPRITE, 45, 45); //Carrega a animacao
    this.fireAnim.setFrameInterval(16, 18); 						 //Seta os tiles que a animacao tem
    this.currentAnimation = idleAnim; 									 //inicia com ele em idle
  }

  public void fire(){
    this.currentAnimation = this.fireAnim; //Para a animacao de tiro
    this.currentAnimation.reset();
  }
  
  public void idle(){
    this.currentAnimation = this.idleAnim; //Para animacao de parado
    this.currentAnimation.reset();
  }

  public void setSpeedAnimation(float speed){
    this.currentAnimation.setSpeed(speed); //Seta a velocidade de animacao
  }

  public void draw(Graphics2D g){
    this.currentAnimation.draw(g, this.x, this.y, 90, 90); //Desenha a animacao na tela
  }
}
