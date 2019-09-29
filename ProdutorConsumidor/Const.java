import java.awt.Font;

public class Const{
    public static final int MAX_ZOMBIES = 10; //Constante para o tamanho maximo de zombies
    public static final int WINDOW_WIDTH = 1000;	//Para o largura da janela
    public static final int WINDOW_HEIGHT = 500;	//Para a altura da janela
    public static final int TICK = 100;						//|Velocidade qual a repaint eh chamado
    public static final int TICK_PER_SECONDS = 1000 / TICK; //Tick per seconds
		public static final Font FONT = new Font("Arial", Font.PLAIN, 12); //Fonte do draw canvas
}
