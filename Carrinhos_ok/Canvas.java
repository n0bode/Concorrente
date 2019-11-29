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
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;
import java.util.LinkedList;
import java.lang.Math;
import java.util.ArrayList;

public class Canvas extends JPanel{
  final Waypoint circuit = new Waypoint();
  final ArrayList<Entity> cars = new ArrayList<Entity>();

  final Animation tank = new Animation(Icons.SPRITE, 16, 16);
  float progress = 0;
  final Semaphore[] semas = new Semaphore[10];
  
  public Canvas(MainWindow main){
    super();
    this.setBounds(0, 0, Const.WINDOW_SIZE,  Const.WINDOW_SIZE);       //Seta o tamanho do canvas
    for(int i = 0; i < semas.length; i++){
      semas[i] = new Semaphore(1);
    }
    semas[0] = new Semaphore(-1);
    semas[1] = new Semaphore(-1);
    semas[2] = new Semaphore(-1);
    semas[4] = new Semaphore(0);
    semas[5] = new Semaphore(0);
    this.init(); //Chama as threads

  }

  private void init(){
    setupCircuit();

    Entity car0 = new Entity(0, 0, Color.ORANGE);
    Entity car1 = new Entity(8, 8, Color.BLUE);
    Entity car2 = new Entity(200, 200, Color.GREEN);
    Entity car3 = new Entity(208, 208, Color.RED);
    Entity car4 = new Entity(0, 0, Color.WHITE);

    car0.setPath(createPathOrange());
    car1.setPath(createPathBlue());
    car2.setPath(createPathGreen());
    car3.setPath(createPathRed());
    car4.setPath(createPathWhite());

    car0.addChangePath(this::circuitOrange);
    car1.addChangePath(this::circuitBlue);
    car2.addChangePath(this::circuitGreen);
    car3.addChangePath(this::circuitRed);
    car4.addChangePath(this::circuitWhite);

    car0.setSpeed(-1);
    car1.setSpeed(-1);
    car3.setSpeed(-1);
    car4.setSpeed(-1);

    car1.addEnterTrigger(this::onEnterTriggerBlue);
    car2.addEnterTrigger(this::onEnterTriggerGreen);
    car3.addEnterTrigger(this::onEnterTriggerRed);
    car4.addEnterTrigger(this::onEnterTriggerWhite);
    car0.addEnterTrigger(this::onEnterTriggerOrange);

    cars.add(car0);
    cars.add(car1);
    cars.add(car2);
    cars.add(car3);
    cars.add(car4);
    
  
    new Thread(car0).start();
    new Thread(car1).start();
    new Thread(car2).start();
    new Thread(car3).start();
    new Thread(car4).start();

    car0.move();
    car1.move();
	}

  private int calculateIndexPoint(int x, int y){
    return y * 6 + x;
  }

  private void setupCircuit(){
    for(int j = 0; j <= 5; j++){
      for(int i = 0; i <= 5; i++){
        circuit.add(new Vec(50 + i * 100, 50 + j * 100));
      }
    }
  }
  
  public void changeOrangeSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(0).setSpeed(value * -10);
  }

  public void changeBlueSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(1).setSpeed(value * -10);
  }

  public void changeGreenSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(2).setSpeed(value * 10);
  }

  public void changeRedSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(3).setSpeed(value * -10);
  }

  public void changeWhiteSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(4).setSpeed(value * -10);
  }

  private Path createPathOrange(){
    Path path = new Path(circuit);
    for(int x = 0; x <= 5; x++)
      path.add(calculateIndexPoint(x, 0));
    
    for(int y = 1; y <= 4; y++)
      path.add(calculateIndexPoint(5, y));

    for(int x = 5; x >= 0; x--)
      path.add(calculateIndexPoint(x, 5));

    for(int y = 4; y >= 1; y--)
      path.add(calculateIndexPoint(0, y));

    //Add Triggers
    path.addTrigger("1L", 0.17f);
    path.addTrigger("1U", 0.83f);
    path.addTrigger("4L", 0.37f);
    path.addTrigger("4U", 0.13f);
    path.addTrigger("5L", 0.87f);
    path.addTrigger("5U", 0.63f);
    path.addTrigger("7L", 0.62f);
    path.addTrigger("7U", 0.39f);
    path.addTrigger("6L", 0.68f);
    path.addTrigger("6U", 0.58f);
    path.addTrigger("9L", 0.42f);
    path.addTrigger("9U", 0.33f);
      /*while(car.getNext() != 3){
        car.move();
      }
      semas[1].acquire();
      while(car.getNext() != 24){
        car.move();
      }
      semas[1].release();*/
    return path;
  }

  private Path createPathBlue(){
    Path path = new Path(circuit);
    for(int x = 0; x <= 5; x++)
      path.add(calculateIndexPoint(x, 3));
  
    path.add(calculateIndexPoint(5, 4));
    for(int x = 5; x >= 0; x--)
      path.add(calculateIndexPoint(x, 5));
    path.add(calculateIndexPoint(0, 4));
    //Add Trigger
    path.addTrigger("2L", 0.23f);
    path.addTrigger("2U", 0.98f);
    path.addTrigger("3L", 0.38f);
    path.addTrigger("3U", 0.18f);
    path.addTrigger("5L", 0.03f);
    path.addTrigger("5U", 0.69f);
    path.addTrigger("6L", 0.74f);
    path.addTrigger("6U", 0.62f);
    path.addTrigger("7L", 0.74f);
    path.addTrigger("7U", 0.33f);
    return path;
  }

  private Path createPathGreen(){
    Path path = new Path(circuit);
    for(int x = 0; x <= 3; x++)
      path.add(calculateIndexPoint(x, 0));
    
    for(int y = 1; y <= 2; y++)
      path.add(calculateIndexPoint(3, y));

    for(int x = 3; x >= 0; x--)
      path.add(calculateIndexPoint(x, 3));
    
    for(int y = 2; y >= 1; y--)
      path.add(calculateIndexPoint(0, y));

    //Add Triggers
    path.addTrigger("0L", 0.14f);
    path.addTrigger("0U", 0.86f);
    path.addTrigger("1L", 0.72f);
    path.addTrigger("1U", 0.27f);
    path.addTrigger("2L", 0.47f);
    path.addTrigger("2U", 0.78f);
    return path;
  }

  private Path createPathRed(){
    Path path = new Path(circuit);
    path.add(calculateIndexPoint(2, 0));
    path.add(calculateIndexPoint(3, 0));
    path.add(calculateIndexPoint(3, 1));
    path.add(calculateIndexPoint(3, 2));
    path.add(calculateIndexPoint(4, 2));
    path.add(calculateIndexPoint(5, 2));
    path.add(calculateIndexPoint(5, 3));
    path.add(calculateIndexPoint(4, 3));
    path.add(calculateIndexPoint(3, 3));
    path.add(calculateIndexPoint(3, 4));
    path.add(calculateIndexPoint(3, 5));
    path.add(calculateIndexPoint(2, 5));
    path.add(calculateIndexPoint(2, 4));
    path.add(calculateIndexPoint(2, 3));
    path.add(calculateIndexPoint(1, 3));
    path.add(calculateIndexPoint(0, 3));
    path.add(calculateIndexPoint(0, 2));
    path.add(calculateIndexPoint(1, 2));
    path.add(calculateIndexPoint(2, 2));
    path.add(calculateIndexPoint(2, 1));

    //Add Triggers
    path.addTrigger("3U", 0.28f);
    path.addTrigger("3L", 0.413f);
    path.addTrigger("0L", 0.165f);
    path.addTrigger("0U", 0.635f);
    path.addTrigger("2L", 0.77f);
    path.addTrigger("2U", 0.635f);
    path.addTrigger("6L", 0.57f);
    path.addTrigger("6U", 0.485f);
    path.addTrigger("8L", 0.57f);
    path.addTrigger("8U", 0.38f);
    path.addTrigger("9L", 0.32f);
    path.addTrigger("9U", 0.23f);
    return path;
  }

  private Path createPathWhite(){
    Path path = new Path(circuit);
    for(int x = 3; x <= 5; x++)
      path.add(calculateIndexPoint(x, 0));
    
    for(int y = 1; y <= 4; y++)
      path.add(calculateIndexPoint(5, y));

    for(int x = 5; x >= 3; x--)
      path.add(calculateIndexPoint(x, 5));
    
    for(int y = 4; y >= 1; y--)
      path.add(calculateIndexPoint(3, y));
    
    //Add Triggersv
    path.addTrigger("0L", 0.02f);
    path.addTrigger("0U", 0.77f);
    path.addTrigger("4L", 0.30f);
    path.addTrigger("4U", 0.97f);
    path.addTrigger("8L", 0.81f);
    path.addTrigger("8U", 0.62f);
    path.addTrigger("7L", 0.67f);
    path.addTrigger("7U", 0.33f);
    path.addTrigger("9L", 0.39f);
    path.addTrigger("9U", 0.26f);
    return path;
  }

  private void circuitOrange(float time, Entity car){
    try{
      car.move();
    }catch(Exception e){}
  }

  private void circuitBlue(float time, Entity car){
    try{
      car.move();
      /*
      while(car.getNext() != 21){
        car.move();
      }
      semas[2].acquire();
      while(car.getNext() != 24){
        car.move();
      }
      semas[2].release();*/
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void circuitGreen(float time, Entity car){
    try{
      car.move();
      /*while(car.getNext() != 2){
        car.move();  
      }
      semas[0].acquire();

      while(car.getNext() != 3){
        car.move();
      }
      semas[1].release();
      System.out.println("UNLOCK");
      while(car.getNext() != 21){
        car.move();
      }
      semas[2].acquire();

      while(car.getNext() != 18){
        car.move();
      }
      semas[1].acquire();
      System.out.println("LOCK");

      while(car.getNext() != 12){
        car.move();
      }
      semas[0].release();
      semas[2].release();*/
    }catch(Exception e){}
  }

  private void circuitRed(float time, Entity car){
    try{
      car.move();
     /* while(car.getNext() != 15){
        car.move();
      }
    
      semas[0].acquire();
      while(car.getNext() != 18){
        car.move();
      }
      semas[2].acquire();

      while(car.getNext() != 26){
        car.move();
      }
      semas[0].release();
      semas[2].release();*/
    }catch(Exception e){}
  }

  private void circuitWhite(float time, Entity car){
    try{
      /*while(car.getNext() != 3){
        car.move();
      }
      semas[0].acquire();
      while(car.getNext() != 27){
        car.move();
      }
      semas[0].release();*/
      car.move();
    }catch(Exception e){}
  }

  private int todigit(char c){
    if (c >= 'A' && c <= 'F')
      return 10 + (c - 'A');

    return c - '0';
  }

  private void lockByEventName(String name){
    try{
      int index = todigit(name.charAt(0));
      if (name.charAt(1) == 'L'){
        semas[index].acquire();
      }else{
        semas[index].release();
      } 
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void onEnterTriggerGreen(String name){
    System.out.println("Green : " + name);
    lockByEventName(name);
  }

  public void onEnterTriggerWhite(String name){
    System.out.println("White : " + name);
    try{
      int index = Integer.parseInt(""+name.charAt(0));
      if (name.charAt(1) == 'U'){
        semas[index].release();
      }else{
        semas[index].acquire();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void onEnterTriggerBlue(String name){
    System.out.println("Blue : " + name);
    lockByEventName(name);
  }

  public void onEnterTriggerRed(String name){
    System.out.println("Red : " + name);
    lockByEventName(name);
  }

  public void onEnterTriggerOrange(String name){
    System.out.println("Orange : " + name);
    lockByEventName(name);
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g; 
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, 1200, 1200);

   for(Entity car : cars){
     car.draw(g2);
   }
	}
}
