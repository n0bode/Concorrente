/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Entity
* Funcao: Guardar as coodernadas x e y de algo
****************************************************************/

public abstract class Entity extends Rect{
	private int speed = 0;
	private int nextX = 0;
	private int nextY = 0;

	public void moveHorizontalTo(int x){
		this.moveTo(x, this.y);
	}

	public void moveVerticalTo(int y){
		this.moveTo(this.x, y);
	}

	public void moveTo(int x, int y){
		this.nextX = x;
		this.nextY = y;
	}

	public void setSpeedMove(int speed){
		this.speed = speed;
	}

	public void move(){
		int dx = moveTowards(this.getX(), this.nextX, this.speed);
		int dy = moveTowards(this.getY(), this.nextY, this.speed);
		this.setPosition(dx, dy);
	}

	private int moveTowards(int x0, int x1, int t){
		int dx = 0;
		if(x1 > x0){
			dx = x0 + t;
			if (dx > x1)
				return x1;
			return dx;
		}
		dx = x0 - t;
		if (dx < x1)
			return x1;
		return dx;
	}

	private int lerp(int x0, int x1, float t){
		return (int)(x0 + (x0 - x1) * t);
	}
}
