import java.util.*;

public class Text {

	private ArrayList<Integer> ip;
	private ArrayList<Block> blockList;
	private ArrayList<Block> bBlockList;
	private ArrayList<Integer> ebit;
	private ArrayList<Block> L;
	private ArrayList<Block> R; 

	private ArrayList<Boolean> x;
	private Key key;
	private ArrayList<ArrayList<Integer>> sBoxes;
	private ArrayList<Integer> finalPerm;

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
		Boolean temp, temp1;
		Block t = new Block(null);
		for(int i=1; i<17; i++) {
			L.add(R.get(i-1));
			R.get(i-1).permutate(ebit);
			x.clear();

			for(int j=0; j<this.key.getKey(i).getSize(); j++ ) {
				temp = (this.key.getKey(i).getBlockElement(j) ^ R.get(i).getBlockElement(j));
				temp1 = L.get(i-1).getBlockElement(j) ^ temp;
				x.add(temp1);
				//Rn = (Ln-1) ^ ((Kn-1) ^ E(R(n-1))
			}
			
			t.updateBlock(x);
			bBlockList.add(t);
		}
		int sindex=0;
		ArrayList<Boolean> B = new ArrayList<>();
		ArrayList<Boolean> preFinal = new ArrayList<>();
		for(int i=1; i<= t.getSize(); i++) {
			B.add(t.getBlockElement(i-1));
			if(i%6==0) {
				preFinal.addAll(sBox(sindex, B));
				sindex++;
				B.clear();
			}
		}
		Block Final = new Block("Final");
		Final.updateBlock(preFinal);
		Final.permutate(finalPerm);
		Final.blockDisplay();

	}
	
	private ArrayList<Boolean> sBox(int sBoxIndex, ArrayList<Boolean> B) {
		ArrayList<Boolean> b = new ArrayList<>();
		b.add(B.get(0));
		b.add(B.get(1));
		int row = getValue(b);
		
		b.clear();
		b.add(B.get(3));
		b.add(B.get(4));
		b.add(B.get(5));
		b.add(B.get(6));
		int column = getValue(b);
		
		int ret = sBoxes.get(sBoxIndex).get(row*15 + column);
		
		ArrayList<Boolean> bits = new ArrayList<>();
	    for (int i = 3; i >= 0; i--) {
	        bits.add((ret & (1 << i)) != 0);
	    }
	    return bits;
	}
	
	private int getValue(ArrayList<Boolean> b) {//gets Bool array returns int
		int i=0;
		if(b.size() == 2) {
			if(b.get(0)) i+=2;
			if(b.get(1)) i+=1;
			return i;
		}
		else if(b.size() == 4) {
			if(b.get(0)) i+=8;
			if(b.get(1)) i+=4;
			if(b.get(2)) i+=2;
			if(b.get(4)) i+=1;
			return i;
		}
		else {
			System.err.println("Text.getVlaue() size neither 2 nor 4");
			return -1;
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

	private void init(String initialtext) {		
		this.blockList = new ArrayList<Block>();
		this.bBlockList = new ArrayList<Block>();
		this.sBoxes = new ArrayList<ArrayList<Integer>>() ;
		this.x = new ArrayList<Boolean>();
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

		//sBoxes ini
		ArrayList<Integer> sBox= new ArrayList<Integer>() ;
		//s1
		sBox.addAll(Arrays.asList(//s1
				14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
				0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
				4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
				15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13));
		sBoxes.add(sBox);
		sBox.clear();
		//s2
		sBox.addAll(Arrays.asList(15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,//s2
				3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
				0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
				13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9));
		sBoxes.add(sBox);
		sBox.clear();
		//s3
		sBox.addAll(Arrays.asList(10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
				13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
				13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
				1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12));
		sBoxes.add(sBox);
		sBox.clear();
		//s4
		sBox.addAll(Arrays.asList(7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
				13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
				10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
				3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14));
		sBoxes.add(sBox);
		sBox.clear();
		//s5
		sBox.addAll(Arrays.asList(2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
				14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
				4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
				11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3));
		sBoxes.add(sBox);
		sBox.clear();
		//s6
		sBox.addAll(Arrays.asList(12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
				10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
				9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
				4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13));
		sBoxes.add(sBox);
		sBox.clear();
		//s7
		sBox.addAll(Arrays.asList(4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
				13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
				1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
				6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12));
		sBoxes.add(sBox);
		sBox.clear();
		//s8
		sBox.addAll(Arrays.asList(13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
				1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
				7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
				2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11));
		sBoxes.add(sBox);
		sBox.clear();
		
		finalPerm= new ArrayList<Integer>() ;
		sBox.addAll(Arrays.asList(13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
				1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
				7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
				2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11));
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




}
