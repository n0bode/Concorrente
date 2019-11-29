	/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Rect
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class Path{
  
  public class Trigger{
    protected String name;
    protected float pos;

    public Trigger(String name, float pos){
      this.name = name;
      this.pos = pos;
    }

    public float getPos(){
      return pos;
    }

    public String getName(){
      return name;
    }

    public boolean between(float prev, float next){
      if (next >= pos && prev <= pos){
        return true;
      }
      return false;
    }
  }

  private final ArrayList<Integer> edges = new ArrayList<Integer>();
  private final Waypoint waypoint;
  private final ArrayList<Trigger> triggers = new ArrayList<Trigger>();

  public Path(Waypoint waypoint){
    this.waypoint = waypoint;
  }

  public void add(int indexPoint){
    edges.add(indexPoint);
  }

  public void addTrigger(String name, float pos){
    triggers.add(new Trigger(name, pos));
  }

  public void rem(int indexPoint){
    edges.remove((Object)indexPoint);
  }

  public int get(int index){
    return edges.get(index);
  }

  public int length(){
    return this.edges.size();
  }

  public void draw(Graphics2D g){
    this.draw(g, Color.WHITE);
  }

  public int getWaypointIndex(int index){
    return this.get(index);
  }

  private float normalize(float v){
    if (v < 0)
      return 1f + (v % 1);
    return v % 1f; 
  }

  public Vec move(float prev, float t, ITrigger callback){
    float np = normalize(prev);
    float nt = normalize(t);

    if (callback != null){
      for(Trigger tri : triggers){
        if (t < 0){
          if (tri.between(nt, np))
            callback.onEnterTrigger(tri.getName());
        }else{
          if (tri.between(np, nt))
            callback.onEnterTrigger(tri.getName());
        }
      }
    }
    
    t = nt;
    prev = np;
    float c = t * this.length();
    int i0 = (int)c % length();
    int i1 = (i0 + 1) % length();

    Vec a = waypoint.get(this.get(i0));
    Vec b = waypoint.get(this.get(i1));
    return Vec.lerp(a, b, c % 1);
  }

  public Vec lerp(float t){
    t = normalize(t);
    float c = t * this.length();
    int i0 = (int)c % length();
    int i1 = (i0 + 1) % length();

    Vec a = waypoint.get(this.get(i0));
    Vec b = waypoint.get(this.get(i1));
    return Vec.lerp(a, b, c % 1);
  }

  public void draw(Graphics2D g, Color color){
    if (this.length() < 2)
      return;

    Color tmp = g.getColor();
    
    for(int i = 0; i < length(); i++){
      int i0 = this.get(i + 0);
      int i1 = this.get((i + 1) % length());

      Vec a = this.waypoint.get(i0);
      Vec b = this.waypoint.get(i1);
      g.setColor(color);
    
      g.fillRect(a.x() - 5, a.y() - 5, 10 + (i == 0? 5 : 0), 10 + (i == 0? 5 : 0));
      g.drawLine(a.x(), a.y(), b.x(), b.y());
    }

    g.setColor(color);
    for(Trigger trigger : triggers){
      Vec p0 = lerp(trigger.getPos());
      g.fillRoundRect(p0.x() - 3, p0.y() - 3, 6, 6, 3, 3);
    }
    g.setColor(tmp);
  }

}
