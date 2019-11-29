/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 15/11/2019
* Ultima alteracao: 23/11/2019
* Nome: Entity
* Funcao: Sao os carrinhos
****************************************************************/
import java.awt.Graphics2D;
import java.awt.Color;

public class Entity implements Runnable{
	private float speed;
  private Path path;
  private Vec position;
  private float angle;
  private float time;
  private float prev;
  private float prevRot;
  private float direction;
  private ITrigger onEnterTrigger;
  private boolean started = true;

  private final Animation anim;
  private final Color color;

  public Entity(int s0, int s1, Color color){
    this.anim = new Animation(Icons.SPRITE, 16); //Pega a posicao da image do tank
    this.anim.setFrameInterval(s0, s1); //Nao ah intervalo
    this.color = color;
    this.position = new Vec(0, 0);
    this.speed = 1;
  }

  /*
    Funcao: setar o callback de quando o carro entra-se em um ponto critico
    Params:
      event: funcao chamada quando o carro entrar em um ponto critico
  */ 
  public void addEnterTrigger(ITrigger event){
    this.onEnterTrigger = event;
  }

  public void setSpeed(float speed){
    this.speed = speed;
  }

  public void setDirection(float dir){
    this.direction = (dir > 0 ? 1 : -1);
  }

  public void setPath(Path path){
    this.path = path;
  }

  public Path getPath(){
    return this.path;
  }

  /*
    * Funcao: move o carro no caminho dele
  */ 
  public void move(){
    prev = time; //Salva a posicao anteriro
    time += (this.speed * this.direction) / 1000f; //A proxima posicao
    position = path.move(started ? time : prev, time, onEnterTrigger); //Faz move no caminho

    //Para o carro nao bugar na rotacao
    if (Math.abs(time - prevRot) >= 0.001f){
      Vec prevPosition = path.lerp(prevRot); //Pega a posicao anteriro
      Vec dif = position.sub(prevPosition); //Faz um vetor resultante
      angle = (float)(Math.atan2(dif.y(), dif.x()) / Math.PI) * 180f; //Pega o angulo entre os pontos
      prevRot = prev; //Salva a ultima posicao de rotacao
    }
    started = false;
  }


  @Override
  public void run(){
    try{
      while(true){
        //Faz o carro andar
        //Faz indentifica se o carro entrou em um ponto critico
        this.move();
        Thread.sleep(Const.TICK);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /*
    Funcao: desenha o carro na tela
    Params:
      g: context de renderizacao
  */
  public void draw(Graphics2D g){
    if(path == null || position == null)
      return;

    path.draw(g, color);
    anim.draw(g, position.x(), position.y(), 38, 38, 90 + angle);
  }
}
