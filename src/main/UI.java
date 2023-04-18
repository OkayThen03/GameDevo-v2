package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;
import main.UtilityTool;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font pixelFont;
	BufferedImage fullHeart, halfHeart, emptyHeart;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	
	UtilityTool uTool = new UtilityTool();
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/font/PixelFont.ttf");
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		//CREATE HUD OBJECT
		SuperObject heart = new OBJ_Heart(gp);
		fullHeart = heart.image;
		halfHeart = heart.image2;
		emptyHeart = heart.image3;
	}
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(pixelFont);
		//ANTI ALIASING
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		//PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		//PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
	}
	
	public void drawPlayerLife() {
//		gp.player.life = 5;
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		//DRAW MAX HEALTH (BLANK HEARTS)
		while(i < gp.player.maxLife / 2) {
			g2.drawImage(emptyHeart, x, y, null);
			i++;
			x += gp.tileSize / 2;
		}
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;
		
		//DRAW CURRENT HEALTH
		while(i < gp.player.life) {
			g2.drawImage(halfHeart, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(fullHeart, x, y, null);
			}
			i++;
			x+= gp.tileSize / 2;
		}	
	}
	
	public void drawTitleScreen() {
		
		//TILE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96f));
		String text = "Tax Evasion";
		int x = getXForCenteredText(text);
		int y = gp.tileSize * 3;
		
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 5, y + 5);
		
		//MAIN COLOR
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
		
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize * 3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}
		
		text = "LOAD GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}
		
		text = "EXIT";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y - 5);
		}
		
	}
	
	public void drawPauseScreen() {
	
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		//WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 8;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize  * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
	}
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - (length / 2);
		return x;
	}
}
