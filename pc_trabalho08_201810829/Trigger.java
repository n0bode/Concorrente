/**************************************************************
  * Inicio: 14/11/2019
  * Ultima alteracao: 23/11/2019
  * Autor: Paulo Rodrigues Camacan    
  * Matricula: 201810829
	* Nome: Trigger
  * Funcao: Classe necessaria para saber se o carro passou por um ponto 
****************************************************************/

public class Trigger{
    final int id;
    protected String name;
    protected float pos;

    public Trigger(int id, String name, float pos){
      this.id = id;
      this.name = name;
      this.pos = pos;
    }

    public float getPos(){
      return pos;
    }

    public String getName(){
      return name;
    }

    public int getID(){
      return this.id;
    }

    /*
      * Nome: between
      * Funcao: saber se entre o intervalo prev e next o carro passou por esse trigger
      * Retorno: caso enteja entre o ponto
    */ 
    public boolean between(float prev, float next){
      return (next >= pos && prev < pos);
    }
  }