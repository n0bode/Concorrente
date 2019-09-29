/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Game
* Funcao: Desenha o 'game' na tela
****************************************************************/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;

public class Game extends JPanel{
  private final Semaphore empty = new Semaphore(Const.MAX_ZOMBIES); // Semaforo para criar zombies
  private final Semaphore full = new Semaphore(0); //Semaforo para zombies ja criados
  private final Semaphore mutex = new Semaphore(1); //Lock da variavel zIndex e Zombies list
  //private final Semaphore mutexDied = new Semaphore(1);

  private final LinkedList<Zombie> zombies = new LinkedList<Zombie>(); //Buffer para o zombies
  private volatile LinkedList<Zombie> dies = new LinkedList<Zombie>(); //Lista de zombies mortos

  private final MainWindow main; //Ponteiro para a janela principal
  private final Hero hero;       //O Unico sobrevivente do mundo logo apos o java ter consumido o mundo
  private int zIndex = 0;				 //Quantidade de zombies criados

  public Game(MainWindow main){
    super();
    this.main = main;
    this.hero = new Hero();
    this.hero.setPosition(Const.WINDOW_WIDTH / 6, (Const.WINDOW_HEIGHT - 200)); //Seta a posicao do hero na tela
    this.setBounds(0, 0, Const.WINDOW_WIDTH,  Const.WINDOW_HEIGHT - 100);       //Seta o tamanho do canvas
    this.init(); //Chama as threads
  }

  private void init(){
    new Thread(this::spawnZombie).start(); //Chamando a thread do produtor
    new Thread(this::killZombies).start(); //Chamando a thread do consumidor
  }

  //Thread Produtor
  public void spawnZombie(){
    try{
      for(;;){
        empty.acquire(); //Check se existe zombies pra criar
        mutex.acquire(); //Check se o buffer nao esta sendo usando
        //Area Critica
        Zombie zombie = new Zombie(); //Cria um zombizao lindao ai
        zombie.setPosition(Const.WINDOW_WIDTH - 150, Const.WINDOW_HEIGHT - 200); //Move ele para o spawn
        if (zIndex > 0){ //Caso se o ultimo zombie esteja longe spawn
          Zombie prev = zombies.get(zIndex - 1); //Pega o ultimo zombie
          if (prev.getX() > Const.WINDOW_WIDTH - 150){ //Se a posicao dele for maior que a do spawn
            zombie.setX(prev.getX() + 40); //Move ele para atras do ultimo zombie
          }
        }
        zIndex++; //Um zombie foi criado
        this.zombies.add(zombie);
        //Fim  Critica
        mutex.release(); //Desbloqueia a buffer
        full.release();  //Adiciona um zombie ao semaforo
        Thread.sleep((int)(2500 * main.speedIN())); //Coloca o produto para dormir
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  //Thread Consumidor
  public void killZombies(){
    try{
      for(;;){
        mutex.acquire(); //Bloqueia a buffer
        if(zIndex != 0){ //Check se existe algum zombie
          Zombie zombie = zombies.getFirst(); //Pega o primeiro zombie no buffer
          if (zombie.getX() <= Const.WINDOW_WIDTH ){ //Check se o zombie esta no raio de tiro
            hero.fire(); //Seta a animacao do hero para atirar
            hero.setSpeedAnimation((1 - main.speedOUT()) * 4f + 1f); //Muda a velocidade da animacao do tiro
            if(zombie.takeDamage()){ //Verdade se o zombie morrer
              full.acquire(); //Indica que um zombie foi removido
              //mutexDied.acquire();
              dies.add(zombie);	//Adicionar o zombie morto a lista de morto
              zombies.removeFirst(); //Remove o zombie da lista de zombies
              --zIndex; //Remove um zombie do tamanho do buffer
              //mutexDied.release();
              empty.release(); //Indica que um zombie pode ser criado
            }
          }else{
            hero.idle();
            //hero.setSpeedAnimation(0.1f);
          }
        }else{
          hero.idle();		//Faz com o hero fique esperando ateh aparecer um zombie
        }
        mutex.release(); //Libera o buffer
        Thread.sleep((int)(1500 * main.speedOUT())); //Coloca o consumidor para dormir
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    
    g2.drawImage(Icons.BACKGROUND, 0, 0,  null); //Desenha o plano de fundo

    g2.setColor(Color.red); 					 //Seta a cor da font
    g2.setFont(Const.FONT); 					 //Seta  a fonte
    g2.drawString("ALIVE : " + zIndex, 20, 20); //Mostra o texto na tela
    this.hero.draw(g2);	//Desenha o hero
		
		//Desenha a barricada
    g2.drawImage(Icons.BARRICADE, Const.WINDOW_WIDTH / 4, (Const.WINDOW_HEIGHT - 100) - 75, null);
    for(int i = 0; i < zIndex; i++){
      Zombie zombie = zombies.get(i);
      zombie.draw(g2); //Desenha o zombie

      if (i == 0){ //Se o zombie for o primeiro
        if(zombie.getX() <= Const.WINDOW_WIDTH / 4 + 10){ //Se o Zombie estiver na frente da barricada
          zombie.idle(); //Para ela
          continue;
        }
        //zombie.walk();
        if(!zombie.isDead()) //Se zombie nao estiver morto
          zombie.addPosition(-10, 0); //Move a zombie
      }else{
        Zombie prev = zombies.get(i - 1); //Pega o zombie da frente
        if(zombie.getX() > prev.getX() + 40){ //Se esse zombie estiver atras do zombie da frente
          zombie.addPosition(-10, 0); //Continua movendo
         // zombie.walk();
        }else{
          if(prev.state == "idle") //Se o zombie da frente estiver parado
            zombie.idle(); //Este para
          zombie.setX(prev.getX() + 40); //E vai para posicao anteiro ao da frente
        }
      }
    }
  	
		//Aqui mostra os zombies mortos, especificamente animacao
    for(int i = 0; i < dies.size(); i++){
      Zombie dead = dies.get(i);	
      if(dead.alpha <= 0){ //Remove caso o zombie desapareca
        this.dies.removeFirst();
        continue;
      }
      g2.setComposite(AlphaComposite.SrcOver.derive(dead.alpha)); //Seta que ira usar alpha
      dead.alpha -= 0.05f; //Diminui o alpha do zombie
      dead.draw(g2); //Mostra o zombie morto
    }
  }
}
