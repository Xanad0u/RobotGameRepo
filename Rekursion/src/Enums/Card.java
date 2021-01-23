package Enums;

import java.awt.image.BufferedImage;

import Globals.Main;

public enum Card {
	BACKCARD, FORWARDCARD, FASTFORWARDCARD, RTURNCARD, LTURNCARD, UTURNCARD, RCARD, EMPTY, NUll;
	
	public Card change(int dir) {
		int newPos = ordinal() + dir;
		newPos = (newPos + 8) % 8;

		return values()[newPos];  
	}
	
	public BufferedImage getImg() {
		
		switch(this) {
		case BACKCARD:
			return Main.backCard;
		case FORWARDCARD:
			return Main.forwardCard;
		case FASTFORWARDCARD:
			return Main.fastForwardCard;
		case LTURNCARD:
			return Main.lTurnCard;
		case RTURNCARD:
			return Main.rTurnCard;
		case UTURNCARD:
			return Main.uTurnCard;
		case EMPTY:
			return Main.cardSlot;
		case RCARD:
			return Main.rCard;
			
		default:	//Should not be called
			System.out.println("<ERROR> likely cause: card is of type null");
			break;
		}
		return null;
	}
}