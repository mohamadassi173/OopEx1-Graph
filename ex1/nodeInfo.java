package ex1;

public class nodeInfo implements node_info{

	private int key;
	private static int id=0;
	private String info;
	private double tag;
	private double w;
	
	
	/**
	 * Basic constructor
	 */
	public nodeInfo() {
		key=id;
		id++;
	}
	
	/**
	 * Basic constructor
	 * @param key2 - the key of the node
	 */
	public nodeInfo(int key2) {
		key=key2;
	}


	////////////////// getters and setters
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}

	@Override
	public double getTag() {
		return tag;
	}

	@Override
	public void setTag(double t) {
		this.tag=t;
	}

	public double getW() {
		return w;
	}

	public void setW(double weighted) {
		this.w = weighted;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return key+"";
	}

}
