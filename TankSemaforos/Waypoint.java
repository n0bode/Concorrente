	/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Path
* Funcao: Armazena os pontos que do grid
****************************************************************/

public class Waypoint{
  private Vec[] points = new Vec[0];

  public int add(Vec point){
    Vec[] saved = new Vec[points.length + 1];
    System.arraycopy(points, 0, saved, 0, points.length);
    saved[points.length] = point;
    points = saved;
    return points.length - 1;
  }

  public Vec get(int index){
    return this.points[index];
  }

  public int length(){
    return this.points.length;
  }
}
