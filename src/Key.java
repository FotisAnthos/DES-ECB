import java.math.BigInteger;
import java.util.*;

public class Key {

	private ArrayList<Integer> pc1;
	private ArrayList<Integer> pc2;
	private ArrayList<Integer> shift;
	private ArrayList<Block> leftSubSet;
	private ArrayList<Block> rightSubSet;
	private ArrayList<Block> subKeys;
	private Block keyBlock;


	public Key(String keyString) {
		System.out.println("***Key***");
		keyBlock = createBlock(keyString);
		Block temp = createBlock(keyString);
		System.out.println("Key:");
		keyBlock.blockDisplay();
		pc1 = new ArrayList<Integer>();
		pc2 = new ArrayList<Integer>();
		pc1.addAll(Arrays.asList(
				57, 49, 41, 33, 25, 17, 9,
				1, 58, 50, 42, 34, 26, 18,
				10, 2, 59, 51, 43, 35, 27,
				19, 11, 3, 60, 52, 44, 36,
				63, 55, 47, 39, 31, 23, 15,
				7, 62, 54, 46, 38, 30, 22,
				14, 6, 61, 53, 45, 37, 29,
				21, 13, 5, 28, 20, 12, 4));
		pc2.addAll(Arrays.asList(
				14, 17, 11, 24, 1,   5,
				3,  28, 15, 6, 21,  10,
				23, 19, 12,  4, 26,  8,
				16,  7, 27, 20, 13,  2,
				41, 52, 31, 37, 47, 55,
				30, 40, 51, 45, 33, 48,
				44, 49, 39, 56, 34, 53,
				46, 42, 50, 36, 29, 32));
		//keyBlock.blockDisplay();
		temp = (keyBlock.permutate(this.pc1));
		keyBlock = temp;
		System.out.println("Key after pc1");
		keyBlock.blockDisplay();
		createSubSets();
		//leftSubSet.get(0).blockDisplay();
		//rightSubSet.get(0).blockDisplay();
		//leftSubSet.get(4).blockDisplay();
		//rightSubSet.get(4).blockDisplay();
		createSubKeys();
		System.out.println("Subkeys 1-16");
		
		for(int i=0; i<subKeys.size(); i++) {
			System.out.println("Key-" + (i+1) );
			subKeys.get(i).blockDisplay();
		}
		//subKeys.get(4).blockDisplay();

	}



	private Block createBlock(String key) {//key block
		Block block = new Block("Key");
		//String temp;
		String temp = new BigInteger(key, 16).toString(2);
		for (int i = 0; i < temp.length(); i++) {
			int tempInt = Character.digit(temp.charAt(i), 2);
			//String temp = intToString(c);
			if(tempInt == 1)
				block.addElement(Boolean.TRUE);//when bit is 1
			else
				block.addElement(Boolean.FALSE);//when bit is 0	
		}
		return block;
	}


	private void createSubSets() {

		leftSubSet = new  ArrayList<Block>();
		rightSubSet = new  ArrayList<Block>();
		leftSubSet.add(new Block("Left-0"));
		rightSubSet.add(new Block("Right-0"));
		for(int i= 0; i<28; i++) {
			leftSubSet.get(0).addElement(keyBlock.getBlockElement(i));
		}
		for(int i= 28; i<56; i++) {
			rightSubSet.get(0).addElement(keyBlock.getBlockElement(i));
		}

		shift = new ArrayList<Integer>();
		shift.addAll(Arrays.asList(1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1)); 
		for(int i=1; i<17; i++) {
			String leftName = "Left-" + i;
			String rightName = "Right-" + i;
			leftSubSet.add(new Block(leftName));
			rightSubSet.add(new Block(rightName));
			//left and right size is the same so it does not matter which one we use here, they both should be 28
			for(int j=shift.get(i-1); j< leftSubSet.get(i-1).getSize(); j++) {
				leftSubSet.get(i).addElement(leftSubSet.get(i-1).getBlockElement(j));
				rightSubSet.get(i).addElement(rightSubSet.get(i-1).getBlockElement(j));
			}
			for(int j=0; j< shift.get(i-1); j++) {
				leftSubSet.get(i).addElement(leftSubSet.get(i-1).getBlockElement(j));
				rightSubSet.get(i).addElement(rightSubSet.get(i-1).getBlockElement(j));
			}
		}
	}

	private void createSubKeys() {
		subKeys = new  ArrayList<Block>();
		
		for(int i=1; i<17; i++) {
			String subKeyName = "Key-" + i;
			Block temp = new  Block(subKeyName);
			for(int j=0; j< 28; j++) {
				temp.addElement(leftSubSet.get(i).getBlockElement(j));
			}
			for(int j=0; j< 28; j++) {
				temp.addElement(rightSubSet.get(i).getBlockElement(j));
			}
			subKeys.add(temp.permutate(this.pc2));
		}
	}

	public Block getKey(int i) {
		return subKeys.get(i);
	}
	public Boolean getKeyBits(int i, int j) {
		return subKeys.get(i).getBlockElement(j);
	}

	private void testKey() {
		List<Integer> test = new ArrayList<Integer>();
		test.addAll(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 1,
				0, 0, 1, 1, 0, 1, 0, 0,
				0, 1, 0, 1, 0, 1, 1, 1, 
				0, 1, 1, 1, 1, 0, 0, 1, 
				1, 0, 0, 1, 1, 0, 1, 1, 
				1, 0, 1, 1, 1, 1, 0, 0, 
				1, 1, 0, 1, 1, 1, 1, 1, 
				1, 1, 1, 1, 0, 0, 0, 1));
		ArrayList<Boolean> block = new ArrayList<Boolean>();
		for (int i = 0; i < test.size(); i++){
			//String temp = intToString(c);
			if(test.get(i) == 1)
				block.add(Boolean.TRUE);//when bit is 1
			else
				block.add(Boolean.FALSE);//when bit is 0	
		}
		keyBlock.updateBlock(block);
	}
}
