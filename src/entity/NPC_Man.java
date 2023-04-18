package entity;

import main.GamePanel;
import java.util.Random;


public class NPC_Man extends Entity {

	public NPC_Man(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
		setDialogue();
	}
	
	public void getNPCImage() {
		up1 = setup("/npc/Man_Back1");
		up2 = setup("/npc/Man_Back2");
		down1 = setup("/npc/Man_Front1");
		down2 = setup("/npc/Man_Front2");
		left1 = setup("/npc/Man_Left1");
		left2 = setup("/npc/Man_Left2");
		right1 = setup("/npc/Man_Right1");
		right2 = setup("/npc/Man_Right2");
	}
	public void setDialogue() {
		dialogues[0] = "This is test dialogue.";
		dialogues[1] = "How many words can I say before I have to \nmove onto the next line";
		dialogues[2] = "About that many, apparently";
		dialogues[3] = "More than I expected!";
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1; //picks number 1-100
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	public void speak() {
		
		super.speak();
	}
}

