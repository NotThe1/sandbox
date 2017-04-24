package assemblerStuff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class OpCodeNode implements Iterator<OpCodeNode>{
	
	private ArrayList<OpCodeNode> opCodeNodes  =  new ArrayList<OpCodeNode>();;
	private Pattern pattern  = null;
	private String opCodeDetail  = null;
	
	private int iteratorCount = 0;
	
	public OpCodeNode(){
	}//Constructor
	
	public OpCodeNode(Pattern pattern,String opCodeVariation ){
		this.pattern = pattern;
		this.opCodeDetail = opCodeVariation;
	}//Constructor
	
	public void addNode(OpCodeNode opCodeNode){
		opCodeNodes.add(opCodeNode);
	}//addNode
	public void addBranch(OpCodeNode[] opCodes){
		this.addNode(opCodes[0]);
		for (int i = 1; i< opCodes.length;i++){
			opCodes[0].addNode(opCodes[i]);
		}//for
	}//addBranch
	
	public Pattern getPattern(){
		return this.pattern;
	}//getPattern
	
	public String getOpCodeVariation(){
		return this.opCodeDetail;
	}//getOpCodeVariation

	@Override
	public boolean hasNext() {
		return iteratorCount<opCodeNodes.size();
	}//hasNext

	@Override
	public OpCodeNode next() {
		return opCodeNodes.get(iteratorCount++);
	}//next
	
	public void resetIterator(){
		iteratorCount = 0;
	}//resetIterator
	
}//class OpCodeNode
