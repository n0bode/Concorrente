	/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: Path
* Funcao: Armazena o tragetohria no qual o carro vai passar
****************************************************************/
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class Path{
  //Variavel responsavel para guardar os dois pontos da reta   
  private final ArrayList<Integer> edges = new ArrayList<Integer>();
  //Waypoint classe que amarzena os pontos
  private final Waypoint waypoint;
  //Os pontos de evento nas retas
  private final ArrayList<Trigger> triggers = new ArrayList<Trigger>();

  public Path(Waypoint waypoint){
    this.waypoint = waypoint;
  }

  /*
    nome: add
    funcao: adiciona um ponto do reta
    param:
      indexPoint: endereco do ponto do waypoint
  */
  public void add(int indexPoint){
    edges.add(indexPoint);
  }

  /*
    nome: addTrigger
    funcao: adiciona um ponto de evento na reta
    params:
      name: nome do evento, no qual vai ser enviado pelo callback
      pos: valor entre 0 e 1, no qual indica a porcentagem do caminho
  */
  public void addTrigger(String name, float pos){
    triggers.add(new Trigger(triggers.size(), name, pos));
  }

  /*
    nome: get
    funcao: pega o index do endereco do ponto
    param:
      index: index do ponto da reta
    retorno: retorn o endereco do ponto no waypoint
  */  
  public int get(int index){
    return edges.get(index);
  }

  /*
    nome: length
    funcao: pega a quantidade de pontos nesse caminho
  */
  public int length(){
    return this.edges.size();
  }

  public void draw(Graphics2D g){
    this.draw(g, Color.WHITE);
  }

  /*
    nome: normalize
    funcao: normaliza um valor para entre 0 e 1
    params:
      v: um valor qualquer
    retorno: returna o valor normalizado
  */
  private float normalize(float v){
    if (v < 0) //Se for negativo
      return 1f + (v % 1); //Para nao gerar negativos
    return v % 1f;  //Retorna soh os decimais
  }

  /*
    nome: move
    funcao: localiza a posicao no qual o carro estah no next
    params:
      prev: valor anterior que o carro estava
      next: a proxima posicao que o carro vai estar
      callback: serah chamada quando o carro passar sobre um trigger
    retorno: proxima posicao do carro
  */
  public Vec move(float prev, float next, ITrigger callback){
    float np = normalize(prev); //normaliza o valor para entre 0 e 1
    float nn = normalize(next); //normaliza o valor para entre 0 e 1

    //Checa se ele passou pelos triggers
    if (callback != null){ // Se o callback is not null
      for(Trigger tri : triggers){ //Todo os triggets
        if (next < 0){ //Se o carrinho estiver correndo em anti-horahrio
          if (tri.between(nn, np)) //ve se ele entrou em um trigger
            callback.onEnterTrigger(tri.getName()); //Chama o evento
        }else{
          if (tri.between(np, nn)) //ve se ele entrou em um trigger
            callback.onEnterTrigger(tri.getName()); //Chama o evento
        }
      }
    }
    return lerp(next);
  }

  /*
    nome: lerp
    funcao: converte um valor de 0 a 1 em uma posicao do caminho
    params:
      t: valor entre 0 a 1, 0 significa o inicio e 1 o final do caminho
    retorno: a posicao que representa essa porcentagem
  */
  public Vec lerp(float t){
    t = normalize(t); //Cornverte o t, para um valor de 0 a 1
    float c = t * this.length(); //Pega o index do ponto nessa porcentagem
    int i0 = (int)c % length(); 
    int i1 = (i0 + 1) % length(); //Pega o index do proximo ponto

    Vec a = waypoint.get(this.get(i0)); //Pega o posicao do ponto
    Vec b = waypoint.get(this.get(i1)); //Pega a posicao do proximo ponto
    return Vec.lerp(a, b, c % 1);       //Pega o ponto que representa a porcentagem entre os dois pontos
  }

  /*
    nome: draw
    funcao: desenha o caminho na tela
    params:
      g: o graphics pai 
      color: a cor do caminho
  */
  public void draw(Graphics2D g, Color color){
    if (!Const.DEBUG) //Se o debug estiver ativo
      return;
      
    if (this.length() < 2) //Nao pode gerar retas soh com um ponto
      return;
      
    Color tmp = g.getColor(); //Salva a cor atual do context
    g.setColor(color); //Pinta com a nova cor
    for(int i = 0; i < length(); i++){
      int i0 = this.get(i + 0);
      int i1 = this.get((i + 1) % length());
      //Acima os pontos da reta

      Vec a = this.waypoint.get(i0);
      Vec b = this.waypoint.get(i1);
      
      //Desenha o ponto como quardado
      g.fillRect(a.x() - 5, a.y() - 5, 10 + (i == 0? 5 : 0), 10 + (i == 0? 5 : 0));
      //Desenha a reta entre os dois pontos
      g.drawLine(a.x(), a.y(), b.x(), b.y());
    }

    for(Trigger trigger : triggers){
      Vec p0 = lerp(trigger.getPos()); //Pega a posicao do trigger
      g.fillRoundRect(p0.x() - 3, p0.y() - 3, 6, 6, 3, 3); //Desenha-o como um circulo
    }
    g.setColor(tmp);
  }
}
