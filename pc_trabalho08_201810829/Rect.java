	/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Rect
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/

public abstract class Rect{
  protected int x;
  protected int y;
	protected int w;
	protected int h;
	protected float sx = 1;
	protected float sy = 1;
	protected float angle;

	public void set(int x, int y, int w, int h){
		this.setPosition(x, y);
		this.setSize(w, h);
	}

  public void setPosition(int x, int y){
    this.x = x;
    this.y = y;
  }

	public void setSize(int s){
		this.w = s;
		this.h = s;
	}

	public void setSize(int w, int h){
		this.w = w;
		this.h = h;
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

	public float getAngle(){
		return this.angle;
	}
	
	public void rotate(float angle){
		this.angle = angle;
	}

	public void scale(float s){
		this.sx = s;
		this.sy = s;
	}

	public void scale(float sx, float sy){
		this.sx = sx;
		this.sy = sy;
	}
  
	public void moveTo(int x, int y){
		
	}
}
