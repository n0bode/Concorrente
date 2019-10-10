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
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;

public class MainWindow extends JFrame{
	public interface OnSlideChange{
		void onChange(int index, int value);
	}

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
			JSlider slider = new JSlider(0, 99, 10); //Cria um slider
			JLabel label = new JLabel(String.format("Speed (%d): %d", index, 10)); //Cria a label

			toolbar.add(label); //Adiciona a label ao toolbar
			toolbar.add(slider); //Adiciona o slider ao toolbar

			slider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
				label.setText(String.format("Speed (%d): %d", index, slider.getValue())); //Muda a label
				if (this.onSlideChange != null){ //Se existir evento
					this.onSlideChange.onChange(index, slider.getValue()); //Chama o evento
				}
			});
		}
    Canvas canvas = new Canvas(this);		//Cria o canvas das animacoes
		canvas.setPreferredSize(new Dimension(Const.WINDOW_SIZE, Const.WINDOW_SIZE));
		this.addSlideChange(canvas::onChangeSlideValue);

		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    this.add(canvas);			//Adiciona a main
    this.add(toolbar);	//Adiciona a main
  }

	public void addSlideChange(OnSlideChange event){
		this.onSlideChange = event;
	}
}
