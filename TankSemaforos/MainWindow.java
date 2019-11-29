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
import javax.swing.JToggleButton;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class MainWindow extends JFrame{
  public MainWindow(){
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Para poder fechar a janela
    this.setSize(Const.WINDOW_SIZE + 200, Const.WINDOW_SIZE);	//Seta o tamanho da janela
    this.initUI();  //Cria os elementos a UI
  }

  public void initUI(){
    Canvas canvas = new Canvas(this);		//Cria o canvas das animacoes
    JPanel toolbar = new JPanel(); 
    this.getContentPane().setBackground(Color.GRAY);
    toolbar.setBackground(Color.GRAY );
    toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));

    //Cria o slider laranja
    JSlider sliderOrange = new JSlider(JSlider.HORIZONTAL, 5, 500, 20);
    sliderOrange.setBackground(Color.ORANGE);

    //Cria o slider azul
    JSlider sliderBlue = new JSlider(JSlider.HORIZONTAL, 5, 500, 20);
    sliderBlue.setBackground(Color.BLUE);

    //Cria o slider verde
    JSlider sliderGreen = new JSlider(JSlider.HORIZONTAL, 5, 500, 20);
    sliderGreen.setBackground(Color.GREEN);

    //Cria o slider vermelho
    JSlider sliderRed = new JSlider(JSlider.HORIZONTAL, 5, 500, 20);
    sliderRed.setBackground(Color.RED);

    //Cria o slider branco
    JSlider sliderWhite = new JSlider(JSlider.HORIZONTAL, 5, 500, 20);
    sliderWhite.setBackground(Color.WHITE);

    //Buttao para mostrar o debug
    JToggleButton debug = new JToggleButton("DEBUG : OFF");
    
    //Adiciona o evento para On/Off debug
    debug.addItemListener((item) ->{
      Const.DEBUG = !Const.DEBUG;
      debug.setText("DEBUG : " + (Const.DEBUG ? "ON" : "OFF"));
    });

    JPanel layout = new JPanel();
    layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
    layout.setBackground(Color.GRAY);
    toolbar.add(layout);
    layout.add(new JLabel(new ImageIcon(getClass().getResource("assets/oroute.png"))));
		layout.add(sliderOrange);
    sliderOrange.addChangeListener(canvas::changeOrangeSpeed);

    layout = new JPanel();
    layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
    layout.setBackground(Color.GRAY);
    toolbar.add(layout);
    layout.add(new JLabel(new ImageIcon(getClass().getResource("assets/broute.png"))));
		layout.add(sliderBlue);
    sliderBlue.addChangeListener(canvas::changeBlueSpeed);

    layout = new JPanel();
    layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
    layout.setBackground(Color.GRAY);
    toolbar.add(layout);
    layout.add(new JLabel(new ImageIcon(getClass().getResource("assets/groute.png"))));
		layout.add(sliderGreen);
    sliderGreen.addChangeListener(canvas::changeGreenSpeed);

    layout = new JPanel();
    layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
    layout.setBackground(Color.GRAY);
    toolbar.add(layout);
    layout.add(new JLabel(new ImageIcon(getClass().getResource("assets/rroute.png"))));
		layout.add(sliderRed);
    sliderRed.addChangeListener(canvas::changeRedSpeed);

    layout = new JPanel();
    layout.setLayout(new BoxLayout(layout, BoxLayout.X_AXIS));
    layout.setBackground(Color.GRAY);
    toolbar.add(layout);
    layout.add(new JLabel(new ImageIcon(getClass().getResource("assets/wroute.png"))));
		layout.add(sliderWhite);
    sliderWhite.addChangeListener(canvas::changeWhiteSpeed);

    toolbar.add(debug);
		canvas.setPreferredSize(new Dimension(Const.WINDOW_SIZE, Const.WINDOW_SIZE)); //Deixa o canvas com tamanho fixo
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    this.add(canvas);		//Adiciona o canvas
    this.add(toolbar);	//Adiciona a toolbar
  }	
}