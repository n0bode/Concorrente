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

public class Waypoint{
  private Vec[] points = new Vec[0];
  private final Color color;
  static int a = 0;

  public Waypoint(){
    this(Color.WHITE);
  }

  public Waypoint(Color color){
    this.color = color;
  }

  public int add(Vec point){
    Vec[] saved = new Vec[points.length + 1];
    System.arraycopy(points, 0, saved, 0, points.length);
    saved[points.length] = point;
    points = saved;
    return points.length - 1;
  }

  public void scale(float scale){
    for(int i = 0; i < length(); i++){
      this.points[i] = this.points[i].mult(scale);
    }
  }

  public void translate(int dx, int dy){
    Vec offset = new Vec(dx, dy);
    for(int i = 0; i < length(); i++){
      this.points[i] = this.points[i].add(offset);
    }
  }

  public void rotate(float rotate){
    float rds = (rotate / 360f) * (float)Math.PI * 2;
    for(int i = 0; i < length(); i++){
      Vec p = this.points[i];
      int dx = (int)(p.x() * Math.sin(rds));
      int dy = (int)(p.y() * Math.cos(rds));
      this.points[i] = new Vec(dx, dy);
    }
  }

  public Vec getPosition(float progress){
    float t = progress > 0 ? progress % 1 : 1f +  (progress % 1);
    float p = length() * t;
    int i0 = (int)(p);
    int i1 = i0 + 1;

    Vec v0 = this.get(i0 % length());
    Vec v1 = this.get(i1 % length());

    float dt = (p - i0) / (float)(i1 - i0);
    return bezierCurve(v0, v1, dt);
  }

  public Vec get(int index){
    return this.points[index];
  }

  public int length(){
    return this.points.length;
  }

  private Vec bezierCurve(Vec a, Vec b, float t){
    return a.mult(1 - t).add(b.mult(t));
  }

  private Vec bezierCurve(Vec a, Vec tanA, Vec b, Vec tanB, float t){
    float ta = (1 - t);
    Vec a0 = a.mult(ta * ta * ta);
    Vec a1 = tanA.mult(3*t*(ta*ta));
    Vec a3 = b.mult(3*t*t*ta);
    Vec a4 = tanB.mult(t*t*t);
    return a0.add(a1.add(a3.add(a4)));
  }
}
