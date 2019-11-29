/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 12/08/2019
* Ultima alteracao: 29/09/2019
* Nome: Animation
* Funcao: Desenha a sequencia de tiles do sprite
****************************************************************/
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Animation{
  private final BufferedImage src; //Imagem source da sprite
  private final int sizeX;         //Tamanho X do tile
  private final int sizeY;				 //Tamanho Y do tile

  private int startFrame = 0;      //Tile de inicio da animacao
  private int endFrame = 0;				 //Tile do final da animacao
  private int currentFrame = 0;    //Frame atual da animacao
	private int frameRepeat = 0;

  private float speed = 1;         //Velocidade da passagem de frames da animacao
  private float currentTime = 0;   //Tempo atual na qual se encontra o counter da animacao
  private boolean loop = true;     //Se a animacao faz um loop
	
  public Animation(BufferedImage src, int size){
		this(src, size, size);
	}

	public Animation(BufferedImage src, int sizeX, int sizeY){
    this.src = src;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }
	
	/*

	*/
  public void setFrameInterval(int start, int end){
    this.currentFrame = start; //Frame atual para o frame inicio
    this.startFrame = start; //Frame inical
    this.endFrame = end; //Ultimo frame
		this.frameRepeat = start;
  }
	
	/*
		nome: frameCount
		returno: pega a quantidade de frames na animacao
		funcao: conta a quantidade de frames que a
	*/
  public int frameCount(){
    return (this.endFrame - this.startFrame);
  }

  public void setSpeed(float speed){
    this.speed = speed;
    this.currentTime = 0;
  }

  public void setLoop(boolean loop){
		if (loop) this.frameRepeat = this.startFrame;
		else this.frameRepeat = this.endFrame;
    this.loop = loop;
  }
	
	public boolean isLooping(){
		return this.loop;
	}

  public void next(){
    if (currentFrame < this.endFrame){ //Se o frame for menor que o ultimo frame
      this.currentFrame++; //Pula um frame
    }else if(currentFrame == this.endFrame){ //vai pro frame inicial
      this.currentFrame = frameRepeat;
    }
  }

  public void prev(){
    if (currentFrame < this.endFrame){ //Se o frame atual maio que frame de inicio
      this.currentFrame--; //Decrementa
    }else if(loop && currentFrame == this.endFrame){ //Se ele for igual ao inical ele loop
      this.currentFrame = startFrame; //Vai pra o ultimo frame
    }
  }

	public void repeat(int frame){
		if (frame < this.startFrame)
			frame = this.startFrame;
		if (frame > this.endFrame)
			frame = this.endFrame;
		this.frameRepeat = frame;
	}

  public void reset(){
    this.currentFrame = this.startFrame; //Reseta o frame para o prime tile
  }

  public void draw (Graphics2D g, int px, int py, int w, int h){
    this.draw(g, px, py, w, h, 0);
  }

  public void draw(Graphics2D g, int px, int py, int w, int h, float angle){
    this.draw(g, px, py, w, h, 1, 1, angle);
  }

  public void draw(Graphics2D g, int px, int py, int w, int h, float sx, float sy, float angle){
    if(this.src == null){
      System.out.println("Animation missing image src");
      return;
    }

		int maxTileX = this.src.getWidth() / sizeX; //Quantidade maxima de tiles em X
    int x = this.currentFrame % maxTileX; //Posicao x do tile
    int y = this.currentFrame / maxTileX; //Posicao y do tile
		
		/*
		:	---dy0----  
			|        |
			dx0    dx1
			|        |
			----dy1---
		*/
    int dx0 = x * sizeX; //Lado esquero do tile na image
    int dy0 = y * sizeY; //Lado superior do tile na image
		
		angle = (angle / 360f) * (float)Math.PI * 2;

		int dx1 = dx0 + sizeX; //Lado direito do tile
    int dy1 = dy0 + sizeY; //Lado inferior do tile
    if(currentTime >= Const.TICK_PER_SECONDS){ 								//Checkar se o time passou a quantidade de frames permitido
      for(int i = 0; i < (int)currentTime / Const.TICK_PER_SECONDS; i++){ //Quantas vezes o o time possou
				if(this.startFrame < this.endFrame)
        	this.next(); //Pular frame
				else
					this.prev();
			}
      currentTime = 0; //Zera o time
    }
    currentTime += speed * Const.TICK_PER_SECONDS; //Adiciona o time a velocidade

		BufferedImage tile = this.src.getSubimage(dx0, dy0, this.sizeX, this.sizeY); 
		Graphics2D cg = (Graphics2D)g.create();
		cg.rotate(angle , px, py);
		int x0 = (sx > 0) ? px - w / 2 : px + w / 2;
		int x1 = (sx > 0) ? px + w / 2 : px - w / 2;
		int y0 = (sy > 0) ? py - h / 2 : py + h / 2;
		int y1 = (sy > 0) ? py + h / 2 : py - h / 2;	
    cg.drawImage(tile, x0, y0, x1, y1, 0, 0, sizeX, sizeY, null); //Mostra a image na posicao do tile
  }
}
