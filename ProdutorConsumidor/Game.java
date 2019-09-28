import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

import java.awt.Font;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;

public class Game extends JPanel{
  private final Semaphore empty = new Semaphore(Const.MAX_ZOMBIES); // Semaforo para criar zombies
  private final Semaphore full = new Semaphore(0); //Semaforo para zombies ja criados
  private final Semaphore mutex = new Semaphore(1); //Lock da variavel zIndex e Zombies list
  //private final Semaphore mutexDied = new Semaphore(1);

  private final LinkedList<Zombie> zombies = new LinkedList<Zombie>(); //ZombieList
  private volatile LinkedList<Zombie> dies = new LinkedList<Zombie>();

  private final MainWindow main;
  private final Hero hero;

  private int zIndex = 0;

  public Game(MainWindow main){
    super();
    this.main = main;
    this.hero = new Hero();
    this.hero.setPosition(Const.WINDOW_WIDTH / 6, (Const.WINDOW_HEIGHT - 200));
    this.setBounds(0, 0, Const.WINDOW_WIDTH,  Const.WINDOW_HEIGHT - 100);
    this.init();
  }

  private void init(){
    new Thread(this::spawnZombie).start();
    new Thread(this::killZombies).start();
  }

  //Thread Produtor
  public void spawnZombie(){
    try{
      for(;;){
        empty.acquire();
        mutex.acquire();
        //Area Critica
        Zombie zombie = new Zombie();
        zombie.setPosition(Const.WINDOW_WIDTH - 150, Const.WINDOW_HEIGHT - 200);
        if (zIndex > 0){
          Zombie prev = zombies.get(zIndex - 1);
          if (prev.getX() > Const.WINDOW_WIDTH - 150){
            zombie.setX(prev.getX() + 40);
          }
        }
        zIndex++;
        this.zombies.add(zombie);
        //Fim  Critica
        mutex.release();
        full.release();
        Thread.sleep((int)(2500 * main.speedIN()));
      }
    }catch(Exception e){
      System.out.println(e.getMessage());;
    }
  }

  //Thread Consumidor
  public void killZombies(){
    try{
      for(;;){
        mutex.acquire();
        if(zIndex != 0){
          Zombie zombie = zombies.getFirst();
          if (zombie.getX() <= Const.WINDOW_WIDTH ){
        
            hero.fire();
            hero.setSpeedAnimation((1 - main.speedOUT()) * 4f + 1f);
            if(zombie.takeDamage()){
              full.acquire();
              //mutexDied.acquire();
              dies.add(zombie);
              zombies.removeFirst();
              --zIndex;
              //mutexDied.release();
              empty.release();
            }
          }else{
            hero.idle();
            //hero.setSpeedAnimation(0.1f);
          }
        }else{
          hero.idle();
          hero.setSpeedAnimation(0.1f);
        }
        mutex.release();
        Thread.sleep((int)(1500 * main.speedOUT()));
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    
    g2.drawImage(Icons.BACKGROUND, 0, 0,  null);
    g2.setColor(Color.red);

    g2.setFont(new Font("Arial", Font.PLAIN, 12));
    g2.drawString("ALIVE : " + zIndex, 20, 20);
    this.hero.draw(g2);

    g2.drawImage(Icons.BARRICADE, Const.WINDOW_WIDTH / 4, (Const.WINDOW_HEIGHT - 100) - 75, null);;
    for(int i = 0; i < zIndex; i++){
      Zombie zombie = zombies.get(i);
      zombie.draw(g2);

      if (zombie == zombies.getFirst()){
        if(zombie.getX() <= Const.WINDOW_WIDTH / 4 + 10){
          zombie.idle();
          continue;
        }
        //zombie.walk();
        if(!zombie.isDead())
          zombie.addPosition(-10, 0);
      }else{
        Zombie prev = zombies.get(i - 1);
        if(zombie.getX() > prev.getX() + 40){
          zombie.addPosition(-10, 0);
         // zombie.walk();
        }else{
          if(prev.state == "idle")
            zombie.idle();
          zombie.setX(prev.getX() + 40);
        }
      }
    }
  
    for(int i = 0; i < dies.size(); i++){
      Zombie dead = dies.get(i);
      if(dead.alpha <= 0){
        this.dies.removeFirst();
        continue;
      }
      g2.setComposite(AlphaComposite.SrcOver.derive(dead.alpha));
      dead.alpha -= 0.05f;
      dead.draw(g2);
    }
  }
}