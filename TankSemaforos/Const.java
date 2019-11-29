import java.awt.Font;

public class Const{
  public static float value;
  public static boolean DEBUG = false;
  public static final int WINDOW_SIZE = 600;	//Para o largura da janela
  public static final int CENTER = WINDOW_SIZE / 2;
  public static final int TICK = 10;						//|Velocidade qual a repaint eh chamado
  public static final int TICK_PER_SECONDS = 1000 / TICK; //Tick per seconds
  public static final Font FONT = new Font("Arial", Font.PLAIN, 12); //Fonte do draw canvas
}
