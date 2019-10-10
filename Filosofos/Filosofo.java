/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Filosofo
* Funcao: Classe do filosofo
****************************************************************/

import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.util.Random;

public class Filosofo extends Rect implements Runnable{
  private final Animation eatAnim; //Animacao de parado
  private final Animation thinkAnim;	//Animacao de tiro

  public Estado estado; 
  private Animation currentAnimation; //Animacao que esta rodando
	private float speed = 1;
	private final int index;
	private final Canvas canvas;

  public Filosofo (int index, Canvas can){
		this.canvas = can;
		this.index = index;
    this.eatAnim = new Animation(Icons.SPRITE, 32); //Carrega a animacao
    this.eatAnim.setFrameInterval(0, 7);							 //Seta os tiles que a animacao tem
		this.eatAnim.repeat(3);
    
    this.thinkAnim = new Animation(Icons.SPRITE, 32); //Carrega a animacao
    this.thinkAnim.setFrameInterval(9, 18); 						 //Seta os tiles que a animacao tem
		this.thinkAnim.repeat(14);
		this.currentAnimation = thinkAnim;
  }

  /* ***************************************************************
  * Metodo: eat
  * Funcao: muda a animacao do filosofo para comer
  *************************************************************** */
  public void eat(){
    this.currentAnimation = this.eatAnim; //Para a animacao de tiro
    this.currentAnimation.reset();
  }

  /* ***************************************************************
  * Metodo: think
  * Funcao: muda a animacao do filosofo para pensar
  *************************************************************** */
  public void think(){
    this.currentAnimation = this.thinkAnim; //Para animacao de parado
    this.currentAnimation.reset();
  }

  /* ***************************************************************
  * Metodo: setSpeedAnimation
  * Funcao: Muda a velocidade da animacao atual
  *************************************************************** */
  public void setSpeedAnimation(float speed){
    this.currentAnimation.setSpeed(speed); //Seta a velocidade de animacao
  }


	public void setSpeed(float speed){
		this.speed = speed;
	}

  /* ***************************************************************
  * Metodo: draw
  * Funcao: desenha o hero na tela
  *************************************************************** */
  public void draw(Graphics2D g){
    this.currentAnimation.draw(g, this.x, this.y, this.w, this.h, this.angle); //Desenha a animacao na tela
  }

	@Override
	public void run(){
		Random rand = new Random(); //Cria o random
		try{
			for(;!false;){
				think(); //Ativa a animacao de pensar
				Thread.sleep(100 + rand.nextInt(1+(int)(5000 * this.speed)));
				canvas.pegarGarfos(this.index);
				eat();
				Thread.sleep(100 + rand.nextInt(1+(int)(5000 * this.speed)));
				canvas.devolverGarfos(this.index);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
