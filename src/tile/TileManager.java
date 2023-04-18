package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[50];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		String map = "/maps/world01.txt";
		getTileImage();
		loadMap(map);
	}

	public void getTileImage() {
		
		//PLACEHOLDER TILES
		setup(0, "000_Placeholder", false);
		//GRASS TILES
		setup(1, "001_Grass1", false);
		setup(2, "002_Grass2", false);
		setup(3, "003_Grass_BottomLeftCornerSand", false);
		setup(4, "004_Grass_BottomLeftSand", false);
		setup(5, "005_Grass_BottomRightCornerSand", false);
		setup(6, "006_Grass_BottomRightSand", false);
		setup(7, "007_Grass_BottomSand", false);
		setup(8, "008_Grass_LeftSand", false);
		setup(9, "009_Grass_RightSand", false);
		setup(10, "010_Grass_TopLeftCornerSand", false);
		setup(11, "011_Grass_TopLeftSand", false);
		setup(12, "012_Grass_TopRightCornerSand", false);
		setup(13, "013_Grass_TopRightSand", false);
		setup(14, "014_Grass_TopSand", false);
		
		//WATER TILES
		setup(15, "015_Water1", true);
		setup(16, "016_Water2", true);
		setup(17, "017_Water3", true);
		setup(18, "018_Water_BottomGrass", true);
		setup(19, "019_Water_BottomLeftCornerGrass", true);
		setup(20, "020_Water_BottomLeftGrass", true);
		setup(21, "021_Water_BottomRightCornerGrass", true);
		setup(22, "022_Water_BottomRightGrass", true);
		setup(23, "023_Water_LeftGrass", true);
		setup(24, "024_Water_RightGrass", true);
		setup(25, "025_Water_TopGrass", true);
		setup(26, "026_Water_TopLeftCornerGrass", true);
		setup(27, "027_Water_TopLeftGrass", true);
		setup(28, "028_Water_TopRightCornerGrass", true);
		setup(29, "029_Water_TopRightGrass", true);
		
		//SAND TILES
		setup(30, "030_Sand1", false);
	
		//MISC TILES
		setup(31, "031_Wall", true);
		setup(32, "032_Earth", false);
		setup(33, "033_Tree", true);
	
	}
	
	public void setup(int index, String imageName, boolean collision) {
	
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
