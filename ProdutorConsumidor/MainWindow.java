import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class MainWindow extends JFrame{
  private JSlider slideIN;
  private JSlider slideOUT;

  public MainWindow(){
    super();
   
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
    this.initUI();  
    this.setResizable(true);  
  }

  public void initUI(){
    JPanel toolbar = new JPanel();
      
    this.slideIN = new JSlider(JSlider.HORIZONTAL, 0, 99, 10);
    this.slideOUT = new JSlider(JSlider.HORIZONTAL, 0, 90, 10);
    
    this.slideIN.setBounds(0, 0, 300, 20);
    this.slideOUT.setBounds(0, 20, 300, 20);

    toolbar.add(new JLabel("Speed Out :"));
    toolbar.add(slideOUT);
    toolbar.add(new JLabel("Speed In : "));
    toolbar.add(slideIN);
    

    Game game = new Game(this);
    game.setBounds(0, 75, Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT - 100);
    this.add(game);
    this.add(toolbar);
  }

  public float speedIN(){
    return (1f - this.slideIN.getValue() / (float)100);
  }

  public float speedOUT(){
    return (1f - this.slideOUT.getValue() / (float)100);
 
  }
}