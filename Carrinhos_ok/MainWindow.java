/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 05/10/2019
* Ultima alteracao: 11/10/2019
* Nome: MainWindow
* Funcao: Janela principal do programa
****************************************************************/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;

public class MainWindow extends JFrame{
  JLabel label = new JLabel();

  public MainWindow(){
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Para poder fechar a janela
    this.setSize(Const.WINDOW_SIZE + 200, Const.WINDOW_SIZE);	//Seta o tamanho da janela
    this.initUI();  //Cria os elementos a UI
  }

  public void onChangeValue(ChangeEvent e){
    JSlider slider = (JSlider)e.getSource();
    Const.value = slider.getValue() / 100f;
    System.out.println(Const.value);
  }

  public void initUI(){
    Canvas canvas = new Canvas(this);		//Cria o canvas das animacoes
    JPanel toolbar = new JPanel(); 
    toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
    JSlider sliderOrange = new JSlider(JSlider.HORIZONTAL, 10, 1000, 10);
    JSlider sliderBlue = new JSlider(JSlider.HORIZONTAL, 10, 1000, 10);
    JSlider sliderGreen = new JSlider(JSlider.HORIZONTAL, 10, 1000, 10);
    JSlider sliderRed = new JSlider(JSlider.HORIZONTAL, 10, 1000, 10);
    JSlider sliderWhite = new JSlider(JSlider.HORIZONTAL, 10, 1000, 10);


    toolbar.add(new JLabel("Orange:"));//Cria um box para conter os slider
		toolbar.add(sliderOrange);
    sliderOrange.addChangeListener(canvas::changeOrangeSpeed);

    toolbar.add(new JLabel("Blue:"));//Cria um box para conter os slider
		toolbar.add(sliderBlue);
    sliderBlue.addChangeListener(canvas::changeBlueSpeed);

    toolbar.add(new JLabel("Green:"));//Cria um box para conter os slider
		toolbar.add(sliderGreen);
    sliderGreen.addChangeListener(canvas::changeGreenSpeed);

    toolbar.add(new JLabel("Red:"));//Cria um box para conter os slider
		toolbar.add(sliderRed);
    sliderRed.addChangeListener(canvas::changeRedSpeed);

    toolbar.add(new JLabel("White:"));//Cria um box para conter os slider
		toolbar.add(sliderWhite);
    sliderWhite.addChangeListener(canvas::changeWhiteSpeed);


		canvas.setPreferredSize(new Dimension(Const.WINDOW_SIZE, Const.WINDOW_SIZE)); //Deixa o canvas com tamanho fixo
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    this.add(canvas);			//Adiciona a main
    this.add(toolbar);	//Adiciona a main
  }	
}
