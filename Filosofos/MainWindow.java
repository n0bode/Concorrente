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
	//Cria um evento para quando o slider for modificado	
	public interface OnSlideChange{
		void onChange(int index, int value);
	}
	
	//Variavel para o evento
	private OnSlideChange onSlideChange;

  public MainWindow(){
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Para poder fechar a janela
    this.setSize(Const.WINDOW_SIZE + 200, Const.WINDOW_SIZE);	//Seta o tamanho da janela
    this.initUI();  //Cria os elementos a UI
  }

  public void initUI(){
    JPanel toolbar = new JPanel(); //Cria um box para conter os slider
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS)); //Adiciona vertical layouy
		
   	for(int i = 0; i < Const.N; i++){
			int index = i; //Salva o index em um local var
			JSlider slider = new JSlider(2, 99, 10); //Cria um slider
			JLabel label = new JLabel(String.format("Demora (%d): %.2fs", index, 1 / 10f * 5)); //Cria a label

			toolbar.add(label); //Adiciona a label ao toolbar
			toolbar.add(slider); //Adiciona o slider ao toolbar

			slider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
				label.setText(String.format("Demora (%d): %.2fs", index, (slider.getValue()/100f) * 5)); //Muda a label
				if (this.onSlideChange != null){ //Se existir evento
					this.onSlideChange.onChange(index, slider.getValue()); //Chama o evento
				}
			});
		}
    Canvas canvas = new Canvas(this);		//Cria o canvas das animacoes
		canvas.setPreferredSize(new Dimension(Const.WINDOW_SIZE, Const.WINDOW_SIZE)); //Deixa o canvas com tamanho fixo
		this.addSlideChange(canvas::onChangeSlideValue); //Adiciona o evento das mudanca de SLider
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    this.add(canvas);			//Adiciona a main
    this.add(toolbar);	//Adiciona a main
  }
	
	/*
	Nome: addSlideChange
	Parametros: event, o evento que sera chamado quando o slider for modificado
	*/
	public void addSlideChange(OnSlideChange event){
		this.onSlideChange = event;
	}
}
