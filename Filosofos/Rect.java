/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Position
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/

public abstract class Rect{
  protected int x;
  protected int y;
	protected int w;
	protected int h;
	protected float angle;

  public void setPosition(int x, int y){
    this.x = x;
    this.y = y;
  }

	public void setSize(int w, int h){
		this.w = w;
		this.h = h;
	}

	public void setAngle(float angle){
		this.angle = angle;
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

	public float getAngle(){
		return this.angle;
	}

  public void setY(int y){
    this.y = y;
  }
}
