/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Entity
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/
import java.awt.Graphics2D;
import java.awt.Color;

public class Entity implements Runnable{
  public interface WalkPath{
    void onChangePath(float time, Entity car);
  }

	private float speed = 2;
  private final Animation anim;
  private Path path;
  private final Color color;
  private Vec position;
  private float angle;
  private float time = 0;
  private int next;
  private float prev;
  private WalkPath eventWalk;
  private ITrigger onEnterTrigger;

  public Entity(int s0, int s1, Color color){
    this.anim = new Animation(Icons.SPRITE, 16);
    this.anim.setFrameInterval(s0, s1);
    this.color = color;
    this.position = new Vec(0, 0);
  }

  public void addEnterTrigger(ITrigger event){
    this.onEnterTrigger = event;
  }

  public void setSpeed(float speed){
    this.speed = speed;
  }

  public void setPath(Path path){
    this.path = path;
  }

  public void addChangePath(WalkPath walk){
    this.eventWalk = walk;
  }

  public float move(){
    prev = time;
    time += this.speed / 1000f;

    Vec prevPosition = path.lerp(prev);
    position = path.move(prev, time, onEnterTrigger);
    
    Vec dif = position.sub(prevPosition);
    angle = (float)(Math.atan2(dif.y(), dif.x()) / Math.PI) * 180f;
    return time;
  }

  public int getNext(){
    return this.path.get(next);
  }

  @Override
  public void run(){
    try{
      while(true){
        this.move();
        Thread.sleep(Const.TICK);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g){
    if(path == null || position == null)
      return;

    path.draw(g, color);
    anim.draw(g, position.x(), position.y(), 40, 40, 90 + angle);
  }
}
