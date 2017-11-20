import java.util.*;

public class Text {

	private ArrayList<Integer> ip;
	private List<Block> blockList;
	private ArrayList<Integer> ebit;
	private ArrayList<Block> L;
	private ArrayList<Block> R;
	private Key key;

	public Text(String initialtext, Key key) {
		this.key = key;
		init(initialtext);


		testText();
		//blockList.get(0).blockDisplay();
		blockList.get(0).permutate(ip);
		//blockList.get(0).blockDisplay();
		blockList.get(0).permutate(ebit);
		//blockList.get(0).blockDisplay();
		createSubSets(0);
		encrypt();
	}
	
	private void encrypt() {
		for(Block b : blockList) {
			encryptBlock(b);
		}
	}
	
	private void encryptBlock(Block b) {
		for(int i=1; i<17; i++) {
			
			
			
		}
		
	}

	private Block createBlock(byte[] bytes) {//plaintext Block
		Block block = new Block("Text");
		String temp;
		int i;//i for the place of the bit inside the byte, index for the place of the bit inside the block
		for(byte b : bytes) {
			temp = block.intToString(b);
			for(i=0;i<temp.length();i++) {
				if(temp.charAt(i) == '1')
					block.addElement(Boolean.TRUE);//when bit is 1
				else
					block.addElement(Boolean.FALSE);//when bit is 0	
			}
		}
		return block;
	}

	private void testText() {
		List<Integer> test = new ArrayList<Integer>();
		test.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1,
				0, 0, 1, 0, 0, 0, 1, 1,
				0, 1, 0, 0, 0, 1, 0, 1, 
				0, 1, 1, 0, 0, 1, 1, 1, 
				1, 0, 0, 0, 1, 0, 0, 1, 
				1, 0, 1, 0, 1, 0, 1, 1, 
				1, 1, 0, 0, 1, 1, 0, 1, 
				1, 1, 1, 0, 1, 1, 1, 1));
		ArrayList<Boolean> block = new ArrayList<Boolean>();
		for (int i = 0; i < test.size(); i++){
			//String temp = intToString(c);
			if(test.get(i) == 1)
				block.add(Boolean.TRUE);//when bit is 1
			else
				block.add(Boolean.FALSE);//when bit is 0	
		}
		blockList.get(0).updateBlock(block);	 
	}

	private void init(String initialtext) {		
		this.blockList = new ArrayList<Block>();
		this.L = new ArrayList<Block>();
		this.R = new ArrayList<Block>();
		ip = new ArrayList<Integer>();
		ebit = new ArrayList<Integer>();

		int i=0;
		String substring="";
		while(i!= initialtext.length()) {
			substring = substring.concat(initialtext.substring(i, i+1));
			//System.out.print(initialtext.substring(i, i+1) + " - ");
			if(i%7 == 0 && i!=0) {
				blockList.add(createBlock(substring.getBytes()));
				substring="";
			}
			i++;			
		}

		if(!substring.contentEquals("")) {
			while(substring.length()<=8) {
				substring = substring.concat(null);
			}
			blockList.add(createBlock(substring.getBytes()));
		}

		ip.addAll(Arrays.asList(
				58, 50, 42, 34, 26, 18, 10, 2,
				60, 52, 44, 36, 28, 20, 12, 4,
				62, 54, 46, 38, 30, 22, 14, 6,
				64, 56, 48, 40, 32, 24, 16, 8,
				57, 49, 41, 33, 25, 17, 9, 1,
				59, 51, 43, 35, 27, 19, 11, 3,
				61, 53, 45, 37, 29, 21, 13, 5,
				63, 55, 47, 39, 31, 23, 15, 7));
		ebit.addAll(Arrays.asList(
				58, 50, 42, 34, 26, 18, 10, 2,
				60, 52, 44, 36, 28, 20, 12, 4,
				62, 54, 46, 38, 30, 22, 14, 6,
				64, 56, 48, 40, 32, 24, 16, 8,
				57, 49, 41, 33, 25, 17, 9, 1,
				59, 51, 43, 35, 27, 19, 11, 3,
				61, 53, 45, 37, 29, 21, 13, 5,
				63, 55, 47, 39, 31, 23, 15, 7));
	}
	
	private void createSubSets(int index) {
		L.add(new Block("Left-0"));
		R.add(new Block("Right-0"));
		for(int i= 0; i<28; i++) {
			L.get(0).addElement(blockList.get(index).getBlockElement(i));
		}
		for(int i= 28; i<56; i++) {
			L.get(0).addElement(blockList.get(index).getBlockElement(i));
		}
	}




}
