/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 25/10/2019
* Ultima alteracao: 29/10/2019
* Nome: MainWindow
* Funcao: Janela principal do programa
****************************************************************/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class MainWindow extends JFrame{
  private JSlider slideIN;  	//Slide para in
  private JSlider slideOUT;		//Slide para out

  public MainWindow(){
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Para poder fechar a janela
    this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);	//Seta o tamanho da janela
    this.initUI();  //Cria os elementos a UI
  }

  public void initUI(){
    JPanel toolbar = new JPanel(); //Cria um box para conter os slider
      
    this.slideIN = new JSlider(JSlider.HORIZONTAL, 0, 99, 10); 	//Criar um Slider
    this.slideOUT = new JSlider(JSlider.HORIZONTAL, 0, 90, 10);	//Criar um slider
    
    //this.slideIN.setBounds(0, 0, 300, 20); 		//Seta a posicao e o tamm
    //this.slideOUT.setBounds(0, 20, 300, 20);	//

    toolbar.add(new JLabel("Speed Out :")); //Cria a label
    toolbar.add(slideOUT);									//Adicionar o slider ao layout
    toolbar.add(new JLabel("Speed In : "));	//Cria a label
    toolbar.add(slideIN);										//Adiciona o slider ao layout
    

    Game game = new Game(this);		//Cria o canvas das animacoes
    game.setBounds(0, 75, Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT - 100); //Seta o tamnho do canvas
    this.add(game);			//Adiciona a main
    this.add(toolbar);	//Adiciona a main
  }

  public float speedIN(){
    return (1f - this.slideIN.getValue() / (float)100); //Inverte a barra
  }

  public float speedOUT(){
    return (1f - this.slideOUT.getValue() / (float)100); //Inverte a Barra
 
  }
}
