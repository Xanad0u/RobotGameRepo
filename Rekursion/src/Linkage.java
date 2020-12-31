@Deprecated
public class Linkage {

	private Link master;
	private Link slave;
	
	public Linkage(Link master, Link slave) {
		this.master = master;
		this.slave = slave;
	}
	
	public Link getMaster() {
		return master;
	}
	
	public Link getSlave() {
		return slave;
	}
	
	public int getMasterIndex() {
		return master.getIndex();
	}
	
	public int getSlaveIndex() {
		return slave.getIndex();
	}
	
	public void updateSlave() {
		
	}
}
