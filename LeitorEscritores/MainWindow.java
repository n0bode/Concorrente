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
  public MainWindow(){
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Para poder fechar a janela
    this.setSize(Const.WINDOW_SIZE + 200, Const.WINDOW_SIZE);	//Seta o tamanho da janela
    this.initUI();  //Cria os elementos a UI
  }

  public void initUI(){
    Canvas canvas = new Canvas(this);		//Cria o canvas das animacoes
    JPanel toolbar = new JPanel(); //Cria um box para conter os slider
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS)); //Adiciona vertical layouy

		toolbar.add(new JLabel("Numero de escritores:")); //Adiciona a label ao toolbar
		JSlider writerSlider = new JSlider(1, 5);
		toolbar.add(writerSlider);

		toolbar.add(new JLabel("Numero de leitores:")); //Adiciona a label ao toolbar
		JSlider readerSlider = new JSlider(1, 5);
		toolbar.add(readerSlider);

		toolbar.add(new JLabel("Escritor velocidade entrada:")); //Adiciona a label ao toolbar
		JSlider writerSpeedEnterSlider = new JSlider(1, 20, Const.writerSpeedEnter);
		toolbar.add(writerSpeedEnterSlider);

		toolbar.add(new JLabel("Escritor velocidade saida:")); //Adiciona a label ao toolbar
		JSlider writerSpeedExitSlider = new JSlider(1, 20, Const.writerSpeedExit);
		toolbar.add(writerSpeedExitSlider);

		toolbar.add(new JLabel("Escritor tempo de espera:")); //Adiciona a label ao toolbar
		JSlider writerTimeWaitSlider = new JSlider(0, 5, Const.writerTimeWait);
		toolbar.add(writerTimeWaitSlider);

		toolbar.add(new JLabel("Escritor tempo de escrita:")); //Adiciona a label ao toolbar
		JSlider writerTimeWriteSlider = new JSlider(0, 5, Const.writerTimeWrite);
		toolbar.add(writerTimeWriteSlider);

		toolbar.add(new JLabel("Leitor velocidade entrada:")); //Adiciona a label ao toolbar
		JSlider readerSpeedEnterSlider = new JSlider(1, 20, Const.readerSpeedEnter);
		toolbar.add(readerSpeedEnterSlider);

		toolbar.add(new JLabel("Leitor velocidade saida:")); //Adiciona a label ao toolbar
		JSlider readerSpeedExitSlider = new JSlider(1, 20, Const.readerSpeedExit);
		toolbar.add(readerSpeedExitSlider);

		toolbar.add(new JLabel("Leitor tempo de espera:")); //Adiciona a label ao toolbar
		JSlider readerTimeWaitSlider = new JSlider(0, 5, Const.readerTimeWait);
		toolbar.add(readerTimeWaitSlider);

		toolbar.add(new JLabel("Leitor tempo de leitura:")); //Adiciona a label ao toolbar
		JSlider readerTimeReadSlider = new JSlider(0, 5, Const.readerTimeRead);
		toolbar.add(readerTimeReadSlider);

		readerSpeedEnterSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			canvas.changeReaderSpeedEnter(readerSpeedEnterSlider.getValue());
		});

		readerSpeedExitSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			canvas.changeReaderSpeedExit(readerSpeedExitSlider.getValue());
		});

		writerSpeedEnterSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			canvas.changeWriterSpeedEnter(writerSpeedEnterSlider.getValue());
		});

		writerSpeedExitSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			canvas.changeWriterSpeedExit(writerSpeedExitSlider.getValue());
		});

		readerSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			canvas.changeReadersNumber(readerSlider.getValue());
		});

		readerTimeReadSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			Const.readerTimeRead = readerTimeReadSlider.getValue();
		});

		readerTimeWaitSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			Const.readerTimeWait = readerTimeWaitSlider.getValue();
		});
		
		writerTimeWriteSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			Const.writerTimeWrite = writerTimeWriteSlider.getValue();
		});

		writerTimeWaitSlider.addChangeListener((ChangeEvent e) ->{ //Quando o Slider for modificado
			Const.writerTimeWait = writerTimeWaitSlider.getValue();
		});
		
		canvas.setPreferredSize(new Dimension(Const.WINDOW_SIZE, Const.WINDOW_SIZE)); //Deixa o canvas com tamanho fixo
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    this.add(canvas);			//Adiciona a main
    this.add(toolbar);	//Adiciona a main
  }	
}
