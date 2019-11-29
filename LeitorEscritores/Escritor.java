/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Filosofo
* Funcao: Classe do escritor
****************************************************************/

import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.util.Random;

public class Escritor extends Entity implements Runnable{
  private final Animation walkAnim; //Animacao de andando
  private final Animation cookAnim;	//Animacao de cozinhando

  public int estado; //Estado do escritor
  private Animation currentAnimation; //Animacao que esta rodando
	private final int index;  //Indice do escritor
	private final Canvas canvas;

  public Escritor (int index, Canvas can){
		this.index = index;
		this.canvas = can;
    this.cookAnim = new Animation(Icons.WRITER, 64); //Carrega a animacao
    this.cookAnim.setFrameInterval(2, 6);						//Seta os tiles que a animacao de comer
		this.cookAnim.repeat(2);                         //Apartir de que frame repete
    		
    this.walkAnim = new Animation(Icons.WRITER, 64); //Carrega a animacao
    this.walkAnim.setFrameInterval(7, 12); 					//Seta os tiles que a animacao pensar
		this.walkAnim.repeat(7);												//Apartir de frame repete
		this.walkAnim.setSpeed(1.5f);
		this.currentAnimation = walkAnim;								//Inicia em pensando
		this.setSize(256);
  }

  /* ***************************************************************
  * Metodo: cook
  * Funcao: muda a animacao do escritor para cozinhar
  *************************************************************** */
  public void cook(){
    this.currentAnimation = this.cookAnim; //Para a animacao de comer
    this.currentAnimation.reset();
  }

  /* ***************************************************************
  * Metodo: walk
  * Funcao: muda a animacao do escritor para andar
  *************************************************************** */
  public void walk(){
    this.currentAnimation = this.walkAnim; //Para animacao de comer
    this.currentAnimation.reset();
  }

  /* ***************************************************************
  * Metodo: setSpeedAnimation
  * Funcao: Muda a velocidade da animacao atual
  *************************************************************** */
  public void setSpeedAnimation(float speed){
    this.currentAnimation.setSpeed(speed); //Seta a velocidade de animacao
  }

  /* ***************************************************************
  * Metodo: draw
  * Funcao: desenha o hero na tela
  *************************************************************** */
  public void draw(Graphics2D g){
    this.currentAnimation.draw(g, this.x, this.y, this.w, this.h, this.sx, this.sy, this.angle); //Desenha a animacao na tela
  }

	@Override
	public void run(){
		Random rand = new Random();

		try{
			while(true){
				this.estado = 0;
				while(canvas.lock){}
				Thread.sleep(Const.writerTimeWait * 1000);
				System.out.println("aqui");
				canvas.db.acquire();
				this.setSpeedMove(Const.writerSpeedEnter);
				this.estado = 1;
				this.scale(1, 1);
				this.moveHorizontalTo(Const.CENTER - 128);
				while(this.getX() != Const.CENTER - 128){
					Thread.sleep(Const.TICK);
					this.move();	
				}
				this.cook();
				Thread.sleep(Const.writerTimeWrite * 1000);
				canvas.db.release();
				this.scale(-1, 1);
				this.estado = 2;
				this.setSpeedMove(Const.writerSpeedExit);
				this.walk();
				this.moveHorizontalTo(-80);
				while(this.getX() > -64){
					Thread.sleep(Const.TICK);
					this.move();
				}
				System.out.println("wait");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
