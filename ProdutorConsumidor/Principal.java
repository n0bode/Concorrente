public class Principal{
  public static void main(String[] args){
    MainWindow window = new MainWindow();
    window.setVisible(true);

    try{
      for(;;){
        Thread.sleep(Const.TICK);
        window.revalidate();
        window.repaint();
      }
    }catch(Exception e){

    }
    
  }
}