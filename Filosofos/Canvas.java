	/*/bin/bash: avac: comando não encontrado
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

public class Canvas extends JPanel{
	private final Filosofo[] filosofos = new Filosofo[Const.N];
	private final Semaphore[] array = new Semaphore[Const.N];
	private final Semaphore mutex = new Semaphore(1);

  public Canvas(MainWindow main){
    super();
    this.setBounds(0, 0, Const.WINDOW_SIZE,  Const.WINDOW_SIZE);       //Seta o tamanho do canvas
    this.init(); //Chama as threads
  }

  private void init(){
		for(int i = 0; i < Const.N; i++){
			float d = (i / (float)Const.N) * (float)Math.PI * 2;
			int x = Const.CENTER + (int)(Math.sin(d) * (Const.CENTER - 50) * (Const.N/20f))+5;
			int y = Const.CENTER + (int)(Math.cos(d) * (Const.CENTER - 25) * (Const.N/20f))+5;

			Filosofo filo = new Filosofo(i, this);
			filo.setPosition(x-75, y-75);
			filo.setSize(150, 150);
			filo.setAngle((float)Math.atan2(y-Const.CENTER, x-Const.CENTER) + (float)Math.PI/2);

			filosofos[i] = filo;
			array[i] = new Semaphore(0);

			Thread thr = new Thread(filo);
			thr.start();
		}
  }
	
	public void onChangeSlideValue(int sliderIndex, int value){
		filosofos[sliderIndex].setSpeed(value / (float)10);
	}

	public void pegarGarfos(int i){
		try{
			mutex.acquire();
			filosofos[i].estado = Estado.FOME;
			testarGarfos(i);
			mutex.release();
			array[i].acquire();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void testarGarfos(int i){
		Filosofo esq = filosofos[(i + Const.N - 1) % Const.N];
		Filosofo dir = filosofos[(i + 1) % Const.N];

		if (filosofos[i].estado == Estado.FOME && esq.estado != Estado.COMENDO && dir.estado != Estado.COMENDO){
			filosofos[i].estado = Estado.COMENDO;
			array[i].release();
		}
	}

	public void devolverGarfos(int i){
		try{
			mutex.acquire();
			filosofos[i].estado = Estado.PENSANDO;
			testarGarfos((i + 1) % Const.N);
			testarGarfos((i + Const.N - 1) % Const.N);
			mutex.release();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create(); 
    //g2.drawImage(Icons.BACKGROUND, 0, 0,  null); //Desenha o plano de fundo

    g2.setColor(Color.green);						 	//Seta a cor da font
    g2.setFont(Const.FONT);						 		//Seta  a fonte
		int w = (int)((Const.CENTER - 75) * (Const.N / 15.0f));
		g2.drawImage(Icons.DESK, Const.CENTER-w, Const.CENTER-w, Const.CENTER + w, Const.CENTER + w, 0, 0, 128, 128, null);
		for(int i = 0; i < Const.N; i++){
			Filosofo filo = filosofos[i];
			int x = filo.getX() + 75;
			int y = filo.getY() + 75;

			filo.draw(g2);
			g2.drawString(""+i, x, y);

			if(filosofos[i].estado != Estado.COMENDO && filosofos[(i + Const.N - 1) % Const.N].estado != Estado.COMENDO){
				Graphics2D s = (Graphics2D)g2.create();
				float a = filo.getAngle() + (float)Math.PI;

				s.translate(x, y); //Move o garfo para perto do filosofo
				s.rotate(a); //Rotaciona o garfo
				s.translate(-30, -45); //Coloca o garfo ao lado do filosofo
				s.rotate(Math.toRadians(30)); //Rotaciona para consertar
				s.drawImage(Icons.FORK, 0, 0, 16, 16, 0, 0, 8, 8, null); //Desenha o garfo
			}
		}
	}
}
