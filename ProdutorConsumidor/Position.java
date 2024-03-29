/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Position
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/

public abstract class Position{
  protected int x;
  protected int y;

  public void setPosition(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void addPosition(int dx, int dy){
    this.x += dx;
    this.y += dy;
  }

  public void mult(float s){
    this.x *= s;
    this.y *= s;
  }

  public int getX(){
    return this.x;
  }

  public void setX(int x){
    this.x = x;
  }

  public int getY(){
    return this.y;
  }

  public void setY(int y){
    this.y = y;
  }
}
