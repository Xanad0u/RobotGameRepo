import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graalvm.compiler.java.LargeLocalLiveness;

public class StageFileManager {

	public boolean Create(int n) {

		boolean error = false;

		try {
			File stageFile = new File("Stage_" + n + ".txt");
			if (stageFile.createNewFile()) {
				System.out.println("File created: " + stageFile.getName());
			} else {
				System.out.println("File already exists.");
				error = true;
			}
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			error = true;
		}

		return error;
	}

	public boolean Write(int n, byte[] data) {
		
		boolean error = false;
		
	    try {
	        FileWriter writer = new FileWriter("Stage_" + n + ".txt");
	        for (int i = 0; i < data.length; i++) {
	        	writer.write(Integer.toString(data[i]));
	        }
	        
	        writer.close();
	        System.out.println("Successfully wrote to the file.");
	      } 
	    catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	        error = true;
	      }
	    
	    return error;
	}

	public byte[] Read(int n) {
		byte[] dataArray = {};
		try
	
		{
			File stageFile = new File("Stage_" + n + ".txt");
			Scanner reader = new Scanner(stageFile);
			String data = reader.nextLine();

			reader.close();
			
			dataArray = new byte[data.length()];
			for (int i = 0; i < data.length(); i++) { 
	            dataArray[i] = (byte) Character.getNumericValue(data.charAt(i));
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		return dataArray;
	}

	public boolean Delete(int n) {

		boolean error = false;
		File stageFile = new File("Stage_" + "1" + ".txt");
		if(stageFile.delete()) {
			System.out.println("Deleted the file: " + stageFile.getName());
		}
		else
		{
			System.out.println("Failed to delete the file.");
			error = true;
		}
		
		return error;
	}
	
	public Card[] getCards(int n) {
		byte[] data = Read(n);
		byte[] dataTrim = new byte[data.length - 66];
		
		Card[] cardsData = new Card[data.length - 66];
		
		for(int i = 0; i < data.length - 66; i++) {
			dataTrim[i] = data[i + 65];
		}
		
		for(int i = 0; i < cardsData.length; i++) {
			switch (dataTrim[i]) {
			case 1:
				cardsData[i] = Card.BACKCARD;
				break;
			case 2:
				cardsData[i] = Card.FORWARDCARD;
				break;
			case 3:
				cardsData[i] = Card.FASTFORWARDCARD;
				break;
			case 4:
				cardsData[i] = Card.RTURNCARD;
				break;
			case 5:
				cardsData[i] = Card.LTURNCARD;
				break;
			case 6:
				cardsData[i] = Card.UTURNCARD;
				break;
			case 7:
				cardsData[i] = Card.RCARD;
				break;
			}
		}
		
		return cardsData;
	}
	
	public Card[] getRealCards(int n) {
		System.out.println("called getRealCards");
		Card[] allCards = getCards(n);
		Card[] data = new Card[getCardAmount(n)];
		int shift = 0;
		
		if(getCardAmount(n) != 0) {
		data[0] = allCards[0];
		
		if(allCards.length > 1) {
			for(int i = 1; i < getCardAmount(n) - 1; i++) {
				if(allCards[i - 1] != Card.RCARD) data[i - shift] = allCards[i];
				else shift++;
				
				if(allCards[i - 1] != Card.RCARD) System.out.println(allCards[i]);
				else System.out.println("shift: " + shift);
			}
			return data;
			}
			else return null;
		}
		else return data;
	}
	
	public byte getCardAmount(int n) {
		Card[] cards = getCards(n);
		int r = 0;
		
		for(int i = 0; i < cards.length; i++) {
			if(cards[i] == Card.RCARD) r++;
		}
		
		return (byte) (cards.length - r);
	}
	
	public Tile[] getTiles(int n) {
		byte[] data = Read(n);
		Tile[] dataTiles = new Tile[64];
		
		for(int i = 0; i < 64; i++) {
			switch(data[i + 1]) {
			case 0:
				dataTiles[i] = Tile.EMPTY;
				break;
			case 1:
				dataTiles[i] = Tile.BLOCK;
				break;
			case 2:
				dataTiles[i] = Tile.HOLE;
				break;
			case 3:
				dataTiles[i] = Tile.START;
				break;
			case 4:
				dataTiles[i] = Tile.FLAG;
				break;
			}
		}

		return dataTiles;
	}
	
	public byte getTile(int n, int j) {
		byte[] data = Read(n);

		return data[j + 1];
	}
	
	public Rotation getInitRot(int n) {
		byte[] data = Read(n);

		switch (data[0]) {
		case 0:
			return Rotation.NORTH;
		case 1:
			return Rotation.EAST;
		case 2:
			return Rotation.SOUTH;
		case 3:
			return Rotation.WEST;
			
		default:
			return null;
		}
	}
	
	public byte[] getInitLoc(int n) {
		Tile[] data = getTiles(n);
		byte tile = 0;
		
		for(int i = 0; i < data.length; i++) {
			if(data[i] == Tile.START) tile = (byte) (i + 1);
		}

		return tileToPos(tile);
	}
	
	public byte[] tileToPos(int tile) {
		byte[] loc = new byte[2];
		
		loc[0] = (byte) ((tile - 1) % 8);
		loc[1] = (byte) (7 - Math.floor((double) tile / 8));
		
		return loc;
	}
	
	public byte[] tileIndexToPos(int tile) {
		byte[] loc = new byte[2];
		tile++;
		
		loc[0] = (byte) ((tile - 1) % 8);
		loc[1] = (byte) (7 - Math.floor((double) (tile - 1) / 8));
		
		return loc;
	}
	
	public int posToTile(byte[] pos) {
		return (pos[0] + 1 + 8 * (7 - pos[1]));
	}
	
	public int posToTileIndex(byte[] pos) {
		return (pos[0] + 8 * (7 - pos[1]));
	}
	
	public byte getSlots(int n) {
		byte[] data = Read(n);

		return data[data.length - 1];
	}
	
	public int getStageAmount() {
		int stageAmount = 0;
		try
		{
			File stageFile = new File("Stage_0.txt");
			Scanner reader = new Scanner(stageFile);
			String data = reader.nextLine();

			reader.close();
			
			char stageAmountChar = data.toCharArray()[0];
			stageAmount = Integer.parseInt( String.valueOf(stageAmountChar));
		}
		catch(FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		return stageAmount;
	}
	
	public void addStageToHolder() {
		try
		{
			File stageFile = new File("Stage_0.txt");
			Scanner reader = new Scanner(stageFile);
			String data = reader.nextLine();

			reader.close();
			
			char[] dataCharArray = data.toCharArray();
			int stageAmount = getStageAmount();
			byte[] modifiedDataArray = new byte[dataCharArray.length + 1];
			
			int largestIndex = modifiedDataArray.length - 1;
			
			for (int i = 0; i < modifiedDataArray.length; i++) {
				if(i == 0) modifiedDataArray[i] = (byte) (stageAmount + 1);
				else if(i == largestIndex) modifiedDataArray[i] = 0;
				else  modifiedDataArray[i] = (byte) Integer.parseInt( String.valueOf(dataCharArray[i]));
			}
			Write(0, modifiedDataArray);
		}
		catch(FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void saveStage() {
		int stageIndex = getStageAmount() + 1;
		Create(stageIndex);
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(Main.initRot.ordinal());
		for (int i = 0; i < Main.stageEditorFrame.tiles.length; i++) {
			data.add(Main.stageEditorFrame.tiles[i].ordinal());
		}
		for (int i = 0; i < Main.cardPane.cardList.size(); i++) {
			data.add(Main.cardPane.cardList.get(i).type.ordinal() + 1);
			if(Main.cardPane.cardList.get(i).type == Card.RCARD) data.add(Main.cardPane.cardList.get(i).rLoops);
		}
		data.add(Main.slotPane.slotList.size());
		
		byte[] byteData = new byte[data.size()];
		
		for (int i = 0; i < byteData.length; i++) {
			byteData[i] = data.get(i).byteValue();
		}
		
		Write(stageIndex, byteData);
		addStageToHolder();
	}
	
	public StageStatus getStageStatus(int n) {
		try
		{
			if(getStageAmount() >= n) {
				File stageFile = new File("Stage_0.txt");
				Scanner reader = new Scanner(stageFile);
				String data = reader.nextLine();
	
				reader.close();
				
				if(data.toCharArray()[n] == '1') return StageStatus.COMPLETE;
				else return StageStatus.NOTCOMPLETE;
			}
			else return StageStatus.NULL;
		}	
		catch(FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return StageStatus.NULL;
	}
	
	public void setStageStatus(int n, StageStatus status) {
		try
		{
			File stageFile = new File("Stage_0.txt");
			Scanner reader = new Scanner(stageFile);
			String data = reader.nextLine();

			reader.close();
			
			char[] dataCharArray = data.toCharArray();
			byte[] modifiedDataArray = new byte[dataCharArray.length];
			
			for (int i = 0; i < modifiedDataArray.length; i++) {
				if(i == n) modifiedDataArray[i] = (byte) (status.ordinal());
				else  modifiedDataArray[i] = (byte) Integer.parseInt( String.valueOf(dataCharArray[i]));
			}
			Write(0, modifiedDataArray);
		}
		catch(FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
