import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Animation{
  private final BufferedImage src; //Imagem source da sprite
  private final int sizeX;         //Tamanho X do tile
  private final int sizeY;				 //Tamanho Y do tile

  private int startFrame = 0;      //Tile de inicio da animacao
  private int endFrame = 0;				 //Tile do final da animacao
  private int currentFrame = 0;    //Frame atual da animacao

  private float speed = 1;         //Velocidade da passagem de frames da animacao
  private float currentTime = 0;   //Tempo atual na qual se encontra o counter da animacao
  private boolean loop = true;     //Se a animacao faz um loop
	
  public Animation(BufferedImage src, int size){
		this.Animation(src, size, size);
	}

	public Animation(BufferedImage src, int sizeX, int sizeY){
    this.src = src;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }
	
	/*

	*/
  public void setFrameInterval(int start, int end){
    this.currentFrame = start;
    this.startFrame = start;
    this.endFrame = end;
  }
	
	/*
		nome: frameCount
		returno: pega a quantidade de frames na animacao
	*/
  public int frameCount(){
    return (this.endFrame - this.startFrame);
  }

  public void setSpeed(float speed){
    this.speed = speed;
    this.currentTime = 0;
  }

  public void setLoop(boolean loop){
    this.loop = loop;
  }
	
	public boolean isLooping(){
		return this.loop;
	}

  public void next(){
    if (currentFrame < this.endFrame){
      this.currentFrame++;
    }else if(loop && currentFrame == this.endFrame){
      this.currentFrame = startFrame;
    }
  }

  public void prev(){
    if (currentFrame > this.startFrame){
      this.currentFrame--;
    }else if(loop && currentFrame == this.startFrame){
      this.currentFrame = endFrame;
    }
  }

  public void reset(){
    this.currentFrame = this.startFrame; //Reseta o frame para o prime tile
  }

  public void draw(Graphics2D g, int sx, int sy, int w, int h){
    if(this.src == null){
      System.out.println("Animation missing image src");
      return;
    }

  	int maxTileX = this.src.getWidth() / sizeX; //Quantidade maxima de tiles em X
    int x = this.currentFrame % maxTileX; //Posicao x do tile
    int y = this.currentFrame / maxTileX; //Posicao y do tile
		
		/*
			---dy0----  
			|        |
			dx0    dx1
			|        |
			----dy1---
		*/
    int dx0 = x * sizeX; //Lado esquero do tile na image
    int dy0 = y * sizeY; //Lado superior do tile na image

    int dx1 = dx0 + sizeX; //Lado direito do tile
    int dy1 = dy0 + sizeY; //Lado inferior do tile

    if(currentTime >= Const.TICK_PER_SECONDS){ //Checkar se o time passou a quantidade de frames permitido
      for(int i = 0; i < (int)currentTime / Const.TICK_PER_SECONDS; i++) //Quantas vezes o o time possou
        this.next(); //Pular frame
      currentTime = 0; //Zera o time
    }
    currentTime += speed * Const.TICK_PER_SECONDS; //Adiciona o time a velocidade
    g.drawImage(this.src, sx, sy, sx + w, sy + h, dx0, dy0, dx1, dy1, null); //Mostra a image na posicao do tile
  }
}
