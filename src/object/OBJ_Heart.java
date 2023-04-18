package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;


public class OBJ_Heart extends SuperObject{
		
		GamePanel gp;
		
		public OBJ_Heart(GamePanel gp) {
			
		this.gp = gp;
			
		name = "Heart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/FullHeart.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/HalfHeart.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/EmptyHeart.png"));
			image = uTool.scaleImage(image, gp.tileSize / 2, gp.tileSize / 2);
			image2 = uTool.scaleImage(image2, gp.tileSize / 2, gp.tileSize / 2);
			image3 =uTool.scaleImage(image3, gp.tileSize / 2, gp.tileSize / 2);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}

