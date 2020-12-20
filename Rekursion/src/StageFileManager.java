import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	
	public byte[] getCards(int n) {
		byte[] data = Read(n);
		byte[] dataTrim = new byte[data.length - 66];
		
		for(int i = 0; i < data.length - 66; i++) {
			dataTrim[i] = data[i + 65];
		}
		
		return dataTrim;
	}
	
	public byte[] getRealCards(int n) {
		System.out.println("called getRealCards");
		byte[] allCards = getCards(n);
		byte[] data = new byte[getCardAmount(n)];
		int shift = 0;
		
		if(getCardAmount(n) != 0) {
		data[0] = allCards[0];
		
		for(int i = 1; i < getCardAmount(n) + 1; i++) {
			if(allCards[i - 1] != 7) data[i - shift] = allCards[i];
			else shift++;
			
			if(allCards[i - 1] != 7) System.out.println(allCards[i]);
			else System.out.println("shift: " + shift);
		}
		return data;
		}
		else return null;
	}
	
	public byte getCardAmount(int n) {
		byte[] cards = getCards(n);
		int r = 0;
		
		for(int i = 0; i < cards.length; i++) {
			if(cards[i] == 7) r++;
		}
		
		return (byte) (cards.length - r);
	}
	
	public byte[] getTiles(int n) {
		byte[] data = Read(n);
		byte[] dataTrim = new byte[64];
		
		for(int i = 0; i < 64; i++) {
			dataTrim[i] = data[i + 1];
		}
		
		return dataTrim;
	}
	
	public byte getTile(int n, int j) {
		byte[] data = Read(n);

		return data[j + 1];
	}
	
	public byte getInitRot(int n) {
		byte[] data = Read(n);

		return data[0];
	}
	
	public byte[] getInitLoc(int n) {
		byte[] data = getTiles(n);
		byte tile = 0;
		byte[] loc = new byte[2];
		
		for(int i = 0; i < data.length; i++) {
			if(data[i] == 3) tile = (byte) (i + 1);
		}
		
		System.out.println("tile: " + tile);

		loc[0] = (byte) ((tile - 1) % 8);
		loc[1] = (byte) (7 - Math.floor((double) tile / 8));
		
		System.out.println("tile / 8: " + (double) tile / 8);
		System.out.println("Math.floor: " + Math.floor((double) tile / 8));
		System.out.println("7 -: " + (7 - Math.floor(tile / 8)));
		
		System.out.println("x: " + loc[0]);
		System.out.println("y: " + loc[1]);
		
		return loc;
	}
	
	public byte getSlots(int n) {
		byte[] data = Read(n);

		return data[data.length - 1];
	}
}
