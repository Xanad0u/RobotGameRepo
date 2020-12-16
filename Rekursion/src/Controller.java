public class Controller {
	
	static byte[] pos;
	static byte rot;
	static Robot robot = new Robot();
	static StageFileManager fileManager = new StageFileManager();

	public static void main(String[] args) {
		
		Print();
		robot.Move((byte) 1);
		Print();
		robot.Move((byte) 2);
		Print();
		robot.Turn((byte) 1);
		Print();
		robot.Move((byte) 1);
		Print();
		robot.Move((byte) 2);
		Print();
		robot.Turn((byte) 5);
		Print();
		robot.Move((byte) 1);
		Print();
		robot.Move((byte) 2);
		Print();
		robot.Turn((byte) 1);
		Print();
		robot.Move((byte) 1);
		Print();
		robot.Move((byte) 2);
		Print();
		robot.Turn((byte) 5);
		Print();
		
		
		
		byte[] data = fileManager.Read(0);
		
		System.out.print("Ctrl: ");
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
		}
		System.out.println();
		/*
		fileManager.Write(1, data);
		*/
		
		byte[] dataCards = fileManager.getCards(0);
		byte[] dataTiles = fileManager.getTiles(0);
		byte dataInitRot = fileManager.getInitRot(0);
		byte dataSlots = fileManager.getSlots(0);
		
		System.out.print("Cards: ");
		for(int i = 0; i < dataCards.length; i++) {
			System.out.print(dataCards[i]);
		}
		System.out.println();
		
		System.out.print("Tiles: ");
		for(int i = 0; i < dataTiles.length; i++) {
			System.out.print(dataTiles[i]);
		}
		System.out.println();
		
		System.out.println("InitRot: " + dataInitRot);
		
		System.out.println("Slots: " + dataSlots);
	}
	
	private static void Print() {
		pos = robot.GetLoc();
		rot = robot.GetRot();
		System.out.println("X: " + pos[0] + "  Y: " + pos[1] + "  Rot: " + rot);
	}

}
