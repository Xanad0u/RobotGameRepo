import java.awt.image.BufferedImage;

public class EditorSlot extends Slot{
	
	public int index;
	
	
	public Link link;

	public EditorSlot(BufferedImage emptyImgIn, StageFrame host, int index, Position position) {
		super(emptyImgIn, host);

		this.position = position;
		
		this.host = host;
		this.index = index;
		
		state = State.EMPTY;
		link = Link.UNLINKED;
	}
	
	@Override
	public void LinkTo(EditorSlot linkingPartner) {
		this.linkingPartner = linkingPartner;
	}
	
	@Override
	public void updateLinked() {
		if(position == Position.MASTER) {
			if(linkingPartner != null) {
				if(linkingPartner.type != Card.RCARD) makeCard(linkingPartner.type);
				else makeCard(linkingPartner.rLoops);
				linkingPartner.host.repaint();
			}
		}
	}
	
	public void updateSlave() {
		if(link.getMaster().type != Card.RCARD) {
			makeCard(link.getMaster().type);
		}
		else makeCard(link.getMaster().rLoops);
		
		host.slotPane.repaint();
	}

	
	
	public void linkToAsSlave(EditorSlot master) {
		link = Link.SLAVE;
		link.setMaster(master);
		
		link.getMaster().link = Link.MASTER;
		link.getMaster().link.setSlave(this);
		
		updateSlave();
	}
	
	public void linkToAsMaster(int linkTo) {
		
	}
	
	public boolean isLinked() {
		if(link != Link.UNLINKED) return true;
		else return false;
	}
}
