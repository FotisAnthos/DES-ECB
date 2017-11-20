import java.util.*;

public class Block {

	private ArrayList<Boolean> block;
	private String name;

	public Block(String name) {
		block = new ArrayList<Boolean>();
		this.name = name;
	}

	public String intToString(int number) {	//https://stackoverflow.com/questions/5263187/print-an-integer-in-binary-format-in-java
		StringBuilder result = new StringBuilder();			//https://stackoverflow.com/users/398316/m2x

		for(int i = 7; i >= 0 ; i--) {
			int mask = 1 << i;
			result.append((number & mask) != 0 ? "1" : "0");
		}
		return result.toString();
	}

	public void blockDisplay() {
		int i=0;
		int indic=100;
		if(block.size() == 64) indic = 8;
		else if(block.size() == 56) indic = 7;
		else if(block.size() == 48) indic = 6;
		//else System.err.println("Block.displayBlock() block size incorrect" + block.size());
		System.out.println(this.name + " Size: "+ block.size());
		for(boolean b : block) {
			if(i%indic == 0 && i!=0) System.out.print(" ");
			if(b) System.out.print("1");
			else  System.out.print("0");
			i++;
		}
		System.out.print("\n");
	}

	public void updateBlock(ArrayList<Boolean> block) {
		this.block.clear();
		for(Boolean b : block) {
			this.block.add(b);
		}
	}

	public Boolean getBlockElement(int i) {
		return this.block.get(i);
	}

	public void addElement(Boolean b) {
		this.block.add(b);
	}

	public int getSize() {
		return block.size();
	}

}



