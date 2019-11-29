/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Filosofo
* Funcao: Classe do leitor
****************************************************************/

import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.util.Random;

public class Leitor extends Entity implements Runnable{
	private final Animation walkAnim; //Animacao de andando
	private final Animation viewAnim;	//Animacao de cozinhando
	private final Animation idleAnim;

	private Animation currentAnimation; //Animacao que esta rodando
	private float speed = 1;	//Velocidade do controlador de comer e pensar
	private Canvas canvas;
	private boolean running = true;
	public int estado = 0;

  public Leitor (Canvas can){
		this.canvas = can;
    this.viewAnim = new Animation(Icons.READER, 64); //Carrega a animacao
    this.viewAnim.setFrameInterval(7, 11);						//Seta os tiles que a animacao de comer

		this.idleAnim = new Animation(Icons.READER, 64);
		this.idleAnim.setFrameInterval(0, 0);
    		
		this.walkAnim = new Animation(Icons.READER, 64); //Carrega a animacao
    this.walkAnim.setFrameInterval(1, 6); 					//Seta os tiles que a animacao pensar
		idle();																					//Inicia em parado
		this.setSize(256, 256);
  }

  /* ***************************************************************
  * Metodo: view
  * Funcao: muda a animacao do leitor para cozinhar
  *************************************************************** */
  public void view(){
    this.currentAnimation = this.viewAnim; //Para a animacao de comer
    this.currentAnimation.reset();
  }

  /* ***************************************************************
  * Metodo: walk
  * Funcao: muda a animacao do leitor para andar
  *************************************************************** */
  public void walk(){
    this.currentAnimation = this.walkAnim; //Para animacao de comer
    this.currentAnimation.reset();
  }

 /* ***************************************************************
  * Metodo: idle
  * Funcao: muda a animacao do leitor para parado
  *************************************************************** */
	public void idle(){
		this.currentAnimation = this.idleAnim;
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
    this.currentAnimation.draw(g, this.x, this.y, this.w, this.h, this.sx, this.sy, this.angle); //Desenha a animacao na tela
  }

	public void stop(){
		this.running = false;
	}

	private int getEmptyIndex(){
		for(int j = 0; j < 5; j++){
			if (canvas.fila.indexOf(j) == -1)
				return j;
		}
		return 0;
	}

	@Override
	public void run(){
		Random rand = new Random();
		try{
			while(this.running){
				Thread.sleep(Const.readerTimeWait * 1000 + rand.nextInt(500));
				this.estado = 0;

				//Tenta acessar o banco
				while(canvas.lock){} //Enquanto esta modificando a interface
				canvas.mutex.acquire();
				canvas.rc++;
				if (canvas.rc == 1)
					canvas.db.acquire();
				int index = getEmptyIndex();
				canvas.fila.add(index);
				this.setX(Const.WINDOW_SIZE + (index + 1) * 64);
				int pos = (Const.CENTER - 128) + (index + 1) * 64;
				canvas.mutex.release();
				//Faz Animacao da ida
				this.scale(-1, 1);
				this.walk();
				this.setSpeedMove(Const.readerSpeedEnter);
				this.moveHorizontalTo(pos);
				this.estado = 1;
				while(this.getX() != pos){
					Thread.sleep(Const.TICK);
					this.move();
				}
				this.view();
				Thread.sleep(Const.readerTimeRead * 1000);

				//Desbloqueia a fila e DB
				canvas.mutex.acquire();
				canvas.rc--;
				canvas.fila.remove((Object)index);
				if (canvas.rc == 0){
					canvas.db.release();
				}
				canvas.mutex.release();

				//Faz a animacao da volta
				this.scale(1, 1);
				this.setSpeedMove(Const.readerSpeedExit);
				this.walk();
				this.estado = 2;
				this.moveHorizontalTo(Const.WINDOW_SIZE + 128);
				while(this.getX() != Const.WINDOW_SIZE + 128){
					Thread.sleep(Const.TICK);
					this.move();		
				}
				this.idle();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
