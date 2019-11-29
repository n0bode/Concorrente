	/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Rect
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/

public class Vec{
  protected int _x;
  protected int _y;

  public Vec(int x, int y){
    this._x = x;
    this._y = y;
  }
  
  public int x(){
    return this._x;
  }

  public int y(){
    return this._y;
  }

  public void setX(int x){
    this._x = x;
  }

  public void setY(int y){
    this._y = y;
  }

  public Vec sub(Vec v){
    return new Vec(this._x - v._x, this._y - v._y);
  }

  public Vec add(Vec v){
    return new Vec(this._x + v._x, this._y + v._y);
  }

  public Vec mult(float f){
    return new Vec((int)(this._x * f), (int)(this._y * f));
  }

  public Vec div(float f){
    return new Vec((int)(this._x / f), (int)(this._y / f));
  }

  public float magnitude(){
    return (float)Math.sqrt(this._x * this._x + this._y * this._y);
  }

  public Vec normalized(){
    return this.div(this.magnitude());
  }

  @Override
  public String toString(){
    return String.format("(%d, %d)", this._x, this._y);
  }

  public static Vec lerp(Vec a, Vec b, float t){
    return b.sub(a).mult(t).add(a);
  } 
}
