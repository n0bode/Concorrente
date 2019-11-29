/**************************************************************
  * Inicio: 14/11/2019
  * Ultima alteracao: 23/11/2019
  * Autor: Paulo Rodrigues Camacan    
  * Matricula: 201810829
	* Nome: Canvas
	* Funcao: Desenhar os elemtonos na tela
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
import java.util.Random;

public class Canvas extends JPanel{
  final Waypoint circuit = new Waypoint();
  final ArrayList<Entity> cars = new ArrayList<Entity>();
  final Semaphore[] semas = new Semaphore[16];
  final Random rand = new Random();
  private long timer = 0;
  final int[] tileScenes = new int[]{
    16, 41, 68, 67
  };

  public Canvas(MainWindow main){
    super();
    this.setBounds(0, 0, Const.WINDOW_SIZE,  Const.WINDOW_SIZE); //Seta o tamanho do canvas

    //Inicia os semaphores
    for(int i = 0; i < semas.length; i++){
      semas[i] = new Semaphore(1);
    }

    //Esses comecam bloqueados
    semas[4] = new Semaphore(0); 
    semas[5] = new Semaphore(0);
    this.init(); //Chama as threads
  }
  
  /*
    * Nome: init
    * Funcao: inicia tudo
  */
  private void init(){
    //Cria o Waypoint
    setupCircuit();

    //Inicia os carros
    Entity carO = new Entity(0, 0, Color.ORANGE);
    Entity carB = new Entity(8, 8, Color.BLUE);
    Entity carG = new Entity(200, 200, Color.GREEN);
    Entity carR = new Entity(208, 208, Color.RED);
    Entity carW = new Entity(1, 0, Color.WHITE);

    //Cria a tragetoria laranja e adiciona os triggers
    carO.setPath(createPathOrange());
    addOrangeTriggers(carO.getPath());

    //Cria a tragetoria azul e adiciona os triggers
    carB.setPath(createPathBlue());
    addBlueTriggers(carB.getPath());

    //Cria a tragetoria verde e adiciona os triggers
    carG.setPath(createPathGreen());
    addGreenTriggers(carG.getPath());

    //Cria a tragetoria vermelha e adiciona os triggers
    carR.setPath(createPathRed());
    addRedTriggers(carR.getPath());

    //Cria a tragetoria branca e adiciona os triggers
    carW.setPath(createPathWhite());
    addWhiteTriggers(carW.getPath());

    //Seta a direcao do carro
    carO.setDirection(-1); //Anti-horario
    carB.setDirection(-1); //Anti-horario
    carG.setDirection(1);  //Horario
    carR.setDirection(-1); //Anti-horario
    carW.setDirection(-1); //Anti-horario

    //Adiciona a funcao callback para o triggers
    //Serao chamadas sempre que o carros passar pelos pontos
    carO.addEnterTrigger(this::onEnterTriggerOrange);
    carB.addEnterTrigger(this::onEnterTriggerBlue);
    carG.addEnterTrigger(this::onEnterTriggerGreen);
    carR.addEnterTrigger(this::onEnterTriggerRed);
    carW.addEnterTrigger(this::onEnterTriggerWhite);

    //Adiciona os carros para a variavel carros
    cars.add(carO);
    cars.add(carB);
    cars.add(carG);
    cars.add(carR);
    cars.add(carW);
    
    //Chama os thread
    new Thread(carO).start();
    new Thread(carB).start();
    new Thread(carG).start();
    new Thread(carR).start();
    new Thread(carW).start();
	}


  /*
    * Nome: calculateIndexPoint
    * Funcao: calcula o index baseado na posicao do grid
    * Params:
      x: posicao x do grid
      y: posicao y do grid
    * Retorno: o index calculado
  */
  private int calculateIndexPoint(int x, int y){
    return y * 6 + x;
  }


  /*
    * Nome: setupCircuito
    * Funcao: instancia o circuito
  */
  private void setupCircuit(){
    for(int j = 0; j <= 5; j++){
      for(int i = 0; i <= 5; i++){
        circuit.add(new Vec(50 + i * 100, 50 + j * 100));
      }
    }
  }

  /*
    * Nome: changeOrangeSpeed
    * Funcao: chamado quando altera-se o slider
  */  
  public void changeOrangeSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(0).setSpeed(value);
  }


  /*
    * Nome: changeBlueSpeed
    * Funcao: chamado quando altera-se o slider
  */  
  public void changeBlueSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(1).setSpeed(value);
  }


  /*
    * Nome: changeGreenSpeed
    * Funcao: chamado quando altera-se o slider
  */  
  public void changeGreenSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(2).setSpeed(value);
  }


  /*
    * Nome: changeRedSpeed
    * Funcao: chamado quando altera-se o slider
  */  
  public void changeRedSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(3).setSpeed(value);
  }


  /*
    * Nome: changeWhiteSpeed
    * Funcao: chamado quando altera-se o slider
  */  
  public void changeWhiteSpeed(ChangeEvent e){
    float value = ((JSlider)e.getSource()).getValue() / 100f;
    cars.get(4).setSpeed(value);
  }

  /*
    * Nome: createPathOrange
    * Funcao: criar a tragetoria laranja laranja
  */  
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
    return path;
  }

  /*
    * Nome: addOrangeTriggers
    * Funcao: adiciona os triggers para a tragetoria laranja
  */  
  private void addOrangeTriggers(Path path){
    /* Os nomes dos trigger e um formula
      XY:
        X: indica o index do semaforo em hex decimal
        Y: pode assumir U (UNLOCK) ou L (LOCK)
      EX: 1L, significa Semaforo 1 bloqueia
          AU, significa Semaforo 10 desbloqueia
    */
    //Add Triggers
    path.addTrigger("1L", 0.17f);
    path.addTrigger("1U", 0.83f);
    path.addTrigger("4L", 0.38f);
    path.addTrigger("4U", 0.13f);
    path.addTrigger("5L", 0.87f);
    path.addTrigger("5U", 0.63f);
    path.addTrigger("7L", 0.62f);
    path.addTrigger("7U", 0.39f);
    path.addTrigger("6L", 0.68f);
    path.addTrigger("6U", 0.58f);
    path.addTrigger("9L", 0.42f);
    path.addTrigger("9U", 0.33f);
    path.addTrigger("AL", 0.915f);
    path.addTrigger("AU", 0.83f);
  }

  /*
    * Nome: createPathBlue
    * Funcao: criar a tragetoria do caminho azul
    * Retorno: Tragetoria
  */  
  private Path createPathBlue(){
    Path path = new Path(circuit);
    for(int x = 0; x <= 5; x++)
      path.add(calculateIndexPoint(x, 3));
  
    path.add(calculateIndexPoint(5, 4));
    for(int x = 5; x >= 0; x--)
      path.add(calculateIndexPoint(x, 5));
    path.add(calculateIndexPoint(0, 4));
    return path;
  }

  private void addBlueTriggers(Path path){
    /* Os nomes dos trigger e um formula
      XY:
        X: indica o index do semaforo em hex decimal
        Y: pode assumir U (UNLOCK) ou L (LOCK)
      EX: 1L, significa Semaforo 1 bloqueia
          AU, significa Semaforo 10 desbloqueia
    */
    path.addTrigger("2L", 0.24f); 
    path.addTrigger("2U", 0.98f);
    path.addTrigger("3L", 0.38f);
    path.addTrigger("3U", 0.18f);
    path.addTrigger("5L", 0.03f);
    path.addTrigger("5U", 0.67f);
    path.addTrigger("6L", 0.74f);
    path.addTrigger("6U", 0.62f);
    path.addTrigger("7L", 0.67f);
    path.addTrigger("7U", 0.33f);
    path.addTrigger("BL", 0.24f);
    path.addTrigger("BU", 0.18f);
  }


  /*
    * Nome: createPathGreen
    * Funcao: criar a tragetoria do caminho verde
    * Retorno: Tragetoria
  */
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
    return path;
  }

  private void addGreenTriggers(Path path){
    /* Os nomes dos trigger e um formula
      XY:
        X: indica o index do semaforo em hex decimal
        Y: pode assumir U (UNLOCK) ou L (LOCK)
      EX: 1L, significa Semaforo 1 bloqueia
          AU, significa Semaforo 10 desbloqueia
    */
    path.addTrigger("0L", 0.14f);
    path.addTrigger("0U", 0.55f);
    path.addTrigger("1L", 0.55f);
    path.addTrigger("1U", 0.27f);
    path.addTrigger("2L", 0.47f);
    path.addTrigger("2U", 0.78f);
    path.addTrigger("AL", 0.55f);
    path.addTrigger("AU", 0.86f);
    path.addTrigger("BL", 0.47f);
    path.addTrigger("BU", 0.55f);
  }

  /*
    * Nome: createPathRed
    * Funcao: criar a tragetoria do caminho vermelho
    * Retorno: Tragetoria
  */
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
    return path;
  }

  private void addRedTriggers(Path path){
    /* Os nomes dos trigger e um formula
      XY:
        X: indica o index do semaforo em hex decimal
        Y: pode assumir U (UNLOCK) ou L (LOCK)
      EX: 1L, significa Semaforo 1 bloqueia
          AU, significa Semaforo 10 desbloqueia
    */
    //Add Triggers
    path.addTrigger("3U", 0.28f);
    path.addTrigger("3L", 0.42f);
    path.addTrigger("0L", 0.17f);
    path.addTrigger("0U", 0.98f);
    path.addTrigger("2L", 0.82f);
    path.addTrigger("2U", 0.635f);
    path.addTrigger("6L", 0.57f);
    path.addTrigger("6U", 0.485f);
    path.addTrigger("8L", 0.57f);
    path.addTrigger("8U", 0.38f);
    path.addTrigger("9L", 0.32f);
    path.addTrigger("9U", 0.23f);
    path.addTrigger("AL", 0.82f);
    path.addTrigger("AU", 0.72f);
    path.addTrigger("BL", 0.42f);
    path.addTrigger("BU", 0.38f);
  }

  /*
    * Nome: createPathWhite
    * Funcao: criar a tragetoria do caminho branco
    * Retorno: Tragetoria
  */
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
    return path;
  }

  private void addWhiteTriggers(Path path){
    /* Os nomes dos trigger e um formula
      XY:
        X: indica o index do semaforo em hex decimal
        Y: pode assumir U (UNLOCK) ou L (LOCK)
      EX: 1L, significa Semaforo 1 bloqueia
          AU, significa Semaforo 10 desbloqueia
    */
    //Add Triggersv
    path.addTrigger("0L", 0.03f);
    path.addTrigger("0U", 0.76f);
    path.addTrigger("4L", 0.30f);
    path.addTrigger("4U", 0.97f);
    path.addTrigger("8L", 0.81f);
    path.addTrigger("8U", 0.62f);
    path.addTrigger("7L", 0.67f);
    path.addTrigger("7U", 0.33f);
    path.addTrigger("9L", 0.39f);
    path.addTrigger("9U", 0.26f);
    path.addTrigger("BL", 0.81f);
    path.addTrigger("BU", 0.76f);
  }

  /*
    * Nome: chatToDec
    * Funcao: converte um char para um valor decimal
    * Params: c => caractere
    * Retorno: valor decimal
  */ 
  private int charToDec(char c){
    if (c >= 'A' && c <= 'F')
      return 10 + (c - 'A');
    return c - '0';
  }
  

  /*
    Nome: decodeEventName
    Funcao: converte a nome trigger para um numero decimal
    Params:
      name: nome do evento
    Retorno: numero decimal convertido
  */
  private void decodeEventName(String name){
    try{
      int index = charToDec(name.charAt(0));
      if (name.charAt(1) == 'L'){ //Se for lock
        semas[index].acquire();
      }else if(semas[index].availablePermits() < 1){ //Caso jah foi desbloqueado
        semas[index].release(1);
      } 
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /*
    Nome: onEnterTriggerGreen
    Funcao: chamado quando o carro verde entra em um trigger
    Params:
      name: nome do evento
  */
  public void onEnterTriggerGreen(String name){
    if(Const.DEBUG)
      System.out.println("Green : " + name);
    decodeEventName(name);
  }

  /*
    Nome: onEnterTriggerWhite
    Funcao: chamado quando o carro branco entra em um trigger
    Params:
      name: nome do evento
  */
  public void onEnterTriggerWhite(String name){
    if(Const.DEBUG)
      System.out.println("White : " + name);
    decodeEventName(name);
  }


  /*
    Nome: onEnterTriggerBlue
    Funcao: chamado quando o carro azul entra em um trigger
    Params:
      name: nome do evento
  */
  public void onEnterTriggerBlue(String name){
    if(Const.DEBUG)
      System.out.println("Blue : " + name);
    decodeEventName(name);
  }


  /*
    Nome: onEnterTriggerRed
    Funcao: chamado quando o carro vermelho entra em um trigger
    Params:
      name: nome do evento
  */
  public void onEnterTriggerRed(String name){
    if(Const.DEBUG)
      System.out.println("Red : " + name);
    decodeEventName(name);
  }


  /*
    Nome: onEnterTriggerOrange
    Funcao: chamado quando o carro laranja entra em um trigger
    Params:
      name: nome do evento
  */
  public void onEnterTriggerOrange(String name){
    if(Const.DEBUG)
      System.out.println("Orange : " + name);
    decodeEventName(name);
  }

  /*
    Nome: drawTile
    Funcao: desenha uma parte da image
    Params:
      g: contexto
      ti: index do tile
      x: posicao do tile na tela
      y: posicao do tile na tela
      size: tamanho a imagem na tela
  */
  private void drawTile(Graphics2D g, int ti, int x, int y, int size){
    int mx = Icons.SPRITE.getWidth() / 16;
    
    int tx = ti % mx;
    int ty = ti / mx;

    int sx0 = tx * 16;
    int sy0 = ty * 16;
    int sx1 = (tx + 1) * 16;
    int sy1 = (ty + 1) * 16;
    g.drawImage(Icons.SPRITE, x, y, x + size, y + size, sx0, sy0, sx1, sy1, null);
  }

  /*
    Nome: drawBlock
    Funcao: desnha os blocos entre os caminhos
    Params:
      Params:
      g: contexto
      x: posicao do tile na tela
      y: posicao do tile na tela
  */
  private void drawBlock(Graphics2D g, int x, int y){
    rand.setSeed(y * 5 + x);
    for(int dy = 0; dy < 2; dy++){
      for(int dx = 0; dx < 2; dx++){
        drawTile(g, (y -x == x) ? 17 : 16, 25 + x + dx * 25, 25 + y + dy * 25, 25);
      }
    }
  }

  /*
    Nome: drawBackground
    Funcao: desenha do plano de fundo
    Params:
      Params:
      g: contexto
      x: posicao do tile na tela
      y: posicao do tile na tela
  */
  private void drawBackground(Graphics2D g, int x, int y){
    for(int dy = 0; dy <= 550 / 25 / 2; dy++){
      for(int dx = 0; dx < 550 / 25; dx++){
        //Condicao para existencia do rio
        if (dx == dy  || dx == dy - 1|| dx == dy + 1 || dx == dy + 2 || dx - 1 == dy){
          drawTile(g, ((timer / 25 + 0 + dy) % 2) == 0 ? 66+25 : 67 + 25, x + dx * 25 , y + dy * 50, 25);
          drawTile(g, ((timer / 25 + 1 + dy) % 2) == 0 ? 66+25 : 67 + 25, x + dx * 25 , 25 + y + dy * 50, 25);
        }else{
          //Plano de areia
          drawTile(g, 16 + 52, x + dx * 25 , y + dy * 50, 25);
          drawTile(g, 16 + 52, x + dx * 25 , y + dy * 50 + 25, 25);
        }
      }
    }
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.GRAY);
    g2.fillRect(0, 0, this.getWidth(), this.getHeight()); 
    g2.setColor(Color.BLACK);
    g2.fillRect(25, 25, 550, 550);

    //Desenha o background
    drawBackground(g2, 25, 25);
    //Desenha os blocos
    for(int dy = 0; dy < 5; dy++){
      for(int dx= 0; dx < 5; dx++){
        drawBlock(g2, 50 + dx * 100, 50 + dy * 100);
      }
    }

    //Desenha os carros  
    for(Entity car : cars){
     car.draw(g2);
    }
    timer += 1; //Nessario para animar o rio
	}
}