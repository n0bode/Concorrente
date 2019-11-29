import java.awt.Font;

public class Const{
  public static int nReaders = 5; //Constante para o tamanho maximo de filosofos
	public static int nWriters = 5;
	public static int writerSpeedEnter = 3;
	public static int writerSpeedExit = 3;
	public static int writerTimeWait = 1;
	public static int writerTimeWrite = 1;

	public static int readerSpeedEnter = 3;
	public static int readerSpeedExit = 3;
	public static int readerTimeWait = 1;
	public static int readerTimeRead = 1;

  public static final int WINDOW_SIZE = 600;	//Para o largura da janela
	public static final int CENTER = WINDOW_SIZE / 2;
  public static final int TICK = 100;						//|Velocidade qual a repaint eh chamado
  public static final int TICK_PER_SECONDS = 1000 / TICK; //Tick per seconds
	public static final Font FONT = new Font("Arial", Font.PLAIN, 12); //Fonte do draw canvas
}
