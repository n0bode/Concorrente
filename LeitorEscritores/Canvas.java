/**************************************************************
	* Inicio: 25/10/2019
	* Ultima alteracao: 29/10/2019
	* Nome: Canvas
	* Funcao: Desenhar os elemtnos na tela
****************************************************************/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.lang.Math;

public class Canvas extends JPanel{
	public Semaphore mutex = new Semaphore(1);           	//Mutex para travar o acesso aos filosofos
	public Semaphore db = new Semaphore(1);           		//Mutex para travar o acesso aos filosofos
	public boolean lock = false; 													//Variavel para saber se os leitores ou escritores foram modificados

	private Leitor[] leitores = new Leitor[5]; 				//Array dos leitores
	private Escritor[] escritores = new Escritor[5]; 	//Array dos escritores
	public LinkedList fila = new LinkedList(); 				//List para saber qual posicao do estah disponivel
	public int rc = 0; 																//Sabe quantos leitores estao no banco

  public Canvas(MainWindow main){
    super();
    this.setBounds(0, 0, Const.WINDOW_SIZE,  Const.WINDOW_SIZE);       //Seta o tamanho do canvas
    this.init(); //Chama as threads
  }

  private void init(){
		this.genLeitores(2);
		this.genEscritores(5);
	}

	private void genLeitores(int tam){
		this.lock = true;
		this.leitores = new Leitor[tam];
		for(int i = 0; i < tam; i++){
			if (leitores[i] != null)
				leitores[i].stop();
			this.leitores[i] = new Leitor(this);
			this.leitores[i].setPosition(Const.WINDOW_SIZE + 128, Const.WINDOW_SIZE - 128 - 10);
			this.leitores[i].scale(-1, 1);
			Thread thread = new Thread(this.leitores[i]);
			thread.start();
		}
		this.mutex = new Semaphore(1);
		this.db = new Semaphore(1);
		this.fila.clear();
		this.rc = 0;
		this.lock = false;
	}

	private void genEscritores(int tam){
		this.lock = true;
		this.escritores = new Escritor[tam];
		for(int i = 0; i < tam; i++){
			this.escritores[i] = new Escritor(i, this);
			this.escritores[i].setPosition(-128, Const.WINDOW_SIZE - 128 - 10);
			Thread thread = new Thread(this.escritores[i]);
			thread.start();
		}
		this.lock = false;
	}
	
	public void changeReaderSpeedExit(int speed){
		Const.readerSpeedExit = speed;
		for(Leitor le : this.leitores){
			if (le.estado == 2)
				le.setSpeedMove(speed);
		}
	}

	public void changeReaderSpeedEnter(int speed){
		Const.readerSpeedEnter = speed;
		for(Leitor le : this.leitores){
			if (le.estado == 1)
				le.setSpeedMove(speed);
		}
	}

	public void changeWriterSpeedExit(int speed){
		Const.writerSpeedExit = speed;
		for(Escritor es : this.escritores){
			if (es.estado == 2)
				es.setSpeedMove(speed);
		}
	}

	public void changeWriterSpeedEnter(int speed){
		Const.writerSpeedEnter = speed;
		for(Escritor es : this.escritores){
			if (es.estado == 1)
				es.setSpeedMove(speed);
		}
	}

	public void changeWritersNumber(int number){
		Const.nWriters = number;
		this.genEscritores(number);
	}

	public void changeReadersNumber(int number){
		Const.nReaders = number;
		this.genLeitores(number);
	}

	private void drawCaldron(Graphics2D g2){
		int fy = Const.WINDOW_SIZE - 160;
		g2.drawImage(Icons.CALDRON, (Const.CENTER-128)-64, fy, (Const.CENTER-128)+64, fy+100, 0, 0, 32, 32, null);
	}

	private void drawDesk(Graphics2D g2){
		int s = 160;
		int sx = (int)Math.ceil(Const.WINDOW_SIZE / (float)s) + 5;	
		int fy = Const.WINDOW_SIZE - (s / 6);
		for(int i = 0; i < sx; i++){
			int x = i * (s / 2);
			g2.drawImage(Icons.DESK, x - s/2, fy - s/2,x + s/2, fy + s/2, 0, 0, 64, 64, null);
		}	
	}

	private void drawEscritores(Graphics2D g2){
		for(Escritor es : this.escritores)
			es.draw(g2);
	}
	
	private void drawLeitores(Graphics2D g2){
		for(Leitor le : this.leitores)
			le.draw(g2);
	}

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g; 
    g2.setColor(Color.green);						 	//Seta a cor da font
    g2.setFont(Const.FONT);						 		//Seta  a fonte
	  this.drawLeitores(g2);
	  this.drawEscritores(g2);
	  this.drawCaldron(g2);
	  this.drawDesk(g2);
	}
}
