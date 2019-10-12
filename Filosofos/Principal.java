/**************************************************************
* Inicio: 06/10/2019
* Ultima alteracao: 11/10/2019
* Nome: Principal
* Funcao: Roda a janela principal
****************************************************************/

public class Principal{
  public static void main(String[] args){
    MainWindow window = new MainWindow(); //Cria a janela principal
    window.setVisible(true);  						//Mostra a janela principal
		
		//Forcando ao repaint
    try{
      for(;;){
        Thread.sleep(Const.TICK); //Dormindo por tick
        window.revalidate();      //Ravalidate
        window.repaint();         //Limpa a tela
      }
    }catch(Exception e){ }
  }
}
