package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}
	
	public void checkEvent() {
		
		if(hit(23, 7, "up")) {teleportPit(gp.dialogueState);}
		if(hit(37, 9, "up") || hit(38, 9, "up")) {healingPool(gp.dialogueState);}
		
	}
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		eventRect.x = eventCol * gp.tileSize + eventRect.x;
		eventRect.y = eventRow * gp.tileSize + eventRect.y;
		
		if(gp.player.solidArea.intersects(eventRect)) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		
		return hit;
	}
	
	public void teleportPit(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "* You fell into a pit and got spat out\nsomewhere else! \n\nOuch!";
		gp.player.life -= 1;
		gp.player.worldX = gp.tileSize * 10;
		gp.player.worldY = gp.tileSize * 8;
	}
	
	public void healingPool(int gameState) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You drink the water. \nYou feel your life being restored!";
			gp.player.life = gp.player.maxLife;
		}
	}

}
