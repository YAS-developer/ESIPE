package fr.uge.net.udp.ex2;


import java.util.BitSet;



final class Client{
	private final BitSet nbOpBitSet;
	private final int TotOper;
	private long opValue;
	
	
	
	
	public Client(int nbOp, int TotOper, long opValue) {
		this.TotOper = TotOper;
		this.nbOpBitSet = new BitSet(TotOper);
		this.nbOpBitSet.set(nbOp);
		this.opValue=opValue;
		
	}
	
	
	
	public void receive(int nbOp) {
		 nbOpBitSet.set(nbOp);
	}
	
	public boolean checkReceive(int nbOp) {
		return nbOpBitSet.get(nbOp);
	}
	
	public boolean Allreceive() {
		var allReceive = true;
			
		for(int i=0; i<TotOper; i++) {
			if(!nbOpBitSet.get(i)) {
				allReceive = false;
				break;
			}
		}
			
		return allReceive;
	}
	
	
	public void addToOpValue(int newValue) {
		if(newValue <= 0) {
			throw new IllegalArgumentException();
		}
		this.opValue+=newValue;
	}
	
	public long getOpvalue() {
		return this.opValue;
	}
}
