/****************************************************************
* Autor: Paulo Rodrigues Camacan    
* Matricula: 201810829
* Inicio: 12/08/2019
* Ultima alteracao: 29/09/2019
* Nome: Icons
* Funcao: Guardar alguns icons que serao usado no jogo
****************************************************************/

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public final class Icons{
  public static final BufferedImage BACKGROUND  = loadImage("assets/background.png"); //backgrundp
  public static final BufferedImage SPRITE      = loadImage("assets/spritesheet.png"); //Sprite do zombie e do hero
  public static final BufferedImage BARRICADE   = loadImage("assets/barricade.png"); //Barricada

  /* ***************************************************************
  * Metodo: loadImage
  * Funcao: Usado para carregar a arquivo do icon
  *************************************************************** */
  public static BufferedImage loadImage(String path){
    try{
      File file = new File(path); //Faz um stream do arquivo
      BufferedImage buffed = ImageIO.read(file); //Converte o arquivo em uma data de imagagem
      return buffed; //retona o data da imagem
    }catch(Exception e){
      return null;
    }
  }
}
