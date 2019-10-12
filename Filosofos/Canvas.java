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

public class Canvas extends JPanel{
	private final Filosofo[] filosofos = new Filosofo[Const.N]; //Analogico ao Array de Estados
	private final Semaphore[] array = new Semaphore[Const.N];   //Array de Semaphoro(x) = filosofo
	private final Semaphore mutex = new Semaphore(1);           //Mutex para travar o acesso aos filosofos

  public Canvas(MainWindow main){
    super();
    this.setBounds(0, 0, Const.WINDOW_SIZE,  Const.WINDOW_SIZE);       //Seta o tamanho do canvas
    this.init(); //Chama as threads
  }

  private void init(){
		for(int i = 0; i < Const.N; i++){ //Instancia os filosofos
			float da = (i / (float)Const.N) * (float)Math.PI * 2; //Calculo o radiano que o filosofo se encontra
			//Sin(a) * r + offsetX
			int x = Const.CENTER + (int)(Math.sin(da) * (Const.CENTER - 50) * (Const.N/20f))+5; //Calcula a posicao no circulo
			//Cos(a) * r + offsetY 
			int y = Const.CENTER + (int)(Math.cos(da) * (Const.CENTER - 50) * (Const.N/20f))+5; //Calcula a posicao no circiculor

			Filosofo filo = new Filosofo(i, this);
			filo.setPosition(x-75, y-75); //Regula a posicao do filosofo na tela
			filo.setSize(150, 150);				//Regula o tamanho do filosofo

			//Calcula o angulo que o filosofo esta olhando para o centro da mesa
			filo.setAngle((float)Math.PI - da);
			filosofos[i] = filo; //Seta o filosofo
			array[i] = new Semaphore(0); //Instancia o semaphore

			Thread thr = new Thread(filo); //Criar uma thread para o filosofo
			thr.start(); //Iniciar a thread do filosofo
		}
  }
	
	/*
		Nome: onChangeSlideValue
		Funcao: para quando um slider for alterado
		Parametros:
			sliderIndex: indice do slide, correspondente a um filosofo
			value: valor alterado
	*/
	public void onChangeSlideValue(int sliderIndex, int value){
		filosofos[sliderIndex].setSpeed(value / (float)10);
	}
	
	/*
		Nome: pegarGarfos
		Funcao: Indicar que um filosofo estah querendo acessar os garfos
		Parametro: i => index do filosofo
	*/
	public void pegarGarfos(int i){
		try{
			mutex.acquire();	//Trava o recurso compartilhado 
			filosofos[i].estado = Estado.FOME; //Indica que um filosofo quer comer
			testarGarfos(i); //Checa se os garfos ao lado estao disponiveis
			mutex.release(); //Destrava o recurso compartilhado
			array[i].acquire(); //Trava o filosofo caso os garfos tiverem ocupados
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
		Nome: testarGarfos
		Funcao: checa se os garfos estao disponiveis para tal filosofo, caso esteja, filosofo vai pra comendo
		Parametro: i => index do filosofo
	*/
	public void testarGarfos(int i){
		Filosofo esq = filosofos[(i + Const.N - 1) % Const.N]; //Pega o filosofo da esquerda
		Filosofo dir = filosofos[(i + 1) % Const.N]; //Pega o filosofo da direita

		if (filosofos[i].estado == Estado.FOME && esq.estado != Estado.COMENDO && dir.estado != Estado.COMENDO){
			filosofos[i].estado = Estado.COMENDO; //Caso os garfos estejam disponiveis, filosofo vai pra comendo
			array[i].release(); //Indicia que o filosofo pegou o garfos
		}
	}

	public void devolverGarfos(int i){
		try{
			mutex.acquire(); //Trava o recurso compartilhado
			filosofos[i].estado = Estado.PENSANDO; //Passa pra pensando
			testarGarfos((i + 1) % Const.N); //Pega o filosofo da direita
			testarGarfos((i + Const.N - 1) % Const.N); //Pega o filosofo da esquerda
			mutex.release(); //Destrava o recurso compartilhado
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
		g2.drawImage(Icons.DESK, Const.CENTER-w, Const.CENTER-w, Const.CENTER + w, Const.CENTER + w, 0, 0, 128, 128, null); //Desenha a mesa
		for(int i = 0; i < Const.N; i++){
			Filosofo filo = filosofos[i]; //Pega o filosofo
			int x = filo.getX() + 75; //Pega a posicao X do filosofo
			int y = filo.getY() + 75; //Pega a posicao Y do filosofo

			filo.draw(g2); //Desenha o filosofo
			g2.drawString(""+i, x, y); //Desenha o numero que indica o filosofo
			
			//Mostra se o garfo esta disponivel, caso esteja desenha 
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
