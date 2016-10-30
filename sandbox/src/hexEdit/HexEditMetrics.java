package hexEdit;

public class HexEditMetrics {
	private int addressSize;
	private int addressEnd;
	private int addressBlockEnd;
	private int dataStart;
	private int dataEnd;
	private int dataBlockEnd;
	private int asciiStart;
	private int asciiEnd;
//	private String docHeader;
	
	public HexEditMetrics(long sourceSize){			
			if (sourceSize <= ADDRESS_4_MAX) {
				this.addressSize = ADDRESS_4;				
			} else if (sourceSize <= ADDRESS_6_MAX) {
				this.addressSize = ADDRESS_6;				
			} else if (sourceSize <= ADDRESS_8_MAX) {
				this.addressSize = ADDRESS_8;
			} else {
				this.addressSize = ADDRESS_4;
			} // if fileSize
			setValues();
	}//Constructor
	
	private void setValues(){
//		this.docHeader = getDocHeader();
		this.addressEnd =this.addressSize-1;
		this.addressBlockEnd = this.addressEnd + ADDRESS_PAD;
		this.dataStart = this.addressBlockEnd +1;
		this.dataEnd = this.dataStart + (3 * BYTES_PER_LINE)-1;
		this.dataBlockEnd = this.dataEnd + DATA_PAD;
		this.asciiStart = this.dataBlockEnd+1;
		this.asciiEnd = this.asciiStart + BYTES_PER_LINE-1;
	}//setValues
	
	public int getAddressSize(){
		return this.addressSize;
	}//getAddressSize
	public int getAddressEnd(){
		return this.addressEnd;
	}//getAddressEnd
	
	public int getAddressBlockEnd(){
		return this.addressBlockEnd;
	}//getAddressEnd
	
	public int getDataStart(){
		return this.dataStart;
	}//getDataStart
	
	public int getDataEnd(){
		return this.dataEnd;
	}//getDataEnd
	
	public int getDataBlockEnd(){
		return this.dataBlockEnd;
	}//getDataEnd
	
	public int getAsciiStart(){
		return this.asciiStart;
	}//getAsciiStart
	
	public int getAsciiEnd(){
		return this.asciiEnd;
	}//getDataStart
	
	public String getDocHeader(){
		 String  header= "       00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F  ";
		switch (addressSize){
		case ADDRESS_4:
			header = DOC_HEADER;
			break;
		case ADDRESS_6:
			header = SPACE + SPACE + DOC_HEADER;
			break;
		case ADDRESS_8:
			header = 	SPACE + SPACE +SPACE + SPACE + DOC_HEADER;
			break;
		default:
			header = DOC_HEADER;
		}//switch	
		return header;
	}//getDocHeader()
	
//----------------------------------------------------------------------------------
	
	public static final int BYTES_PER_LINE = 16;

	private static final int ADDRESS_4 = 4;
	private static final int ADDRESS_6 = 6;
	private static final int ADDRESS_8 = 8;

	private static final int ADDRESS_4_MAX = 0XFFFF; // FFFF - 65535
	private static final int ADDRESS_6_MAX = 0XFFFFFF; // FF FFFF - 16,777,215
	private static final int ADDRESS_8_MAX = Integer.MAX_VALUE; // 7FFF FFFF - 2,147,483,647
	
	private static final int ADDRESS_PAD = 3;
	private static final int DATA_PAD = 3;
	
	private static final String DOC_HEADER = "       00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F  ";
	private static final String SPACE = " ";


}//class HexEditMetric
