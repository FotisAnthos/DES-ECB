import java.util.*;

public class Text {

	private String text;
	private List<Block> blockList;
	private int characterSize;//in bits

	public Text(String initialtext) {
		this.text = initialtext;
		this.characterSize = 8;
		this.blockList = new ArrayList<Block>();
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
		blockList.get(0).blockDisplay();
	}

	public Block createBlock(byte[] bytes) {//plaintext Block
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




}
