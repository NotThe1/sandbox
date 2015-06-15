package sandbox;

public class Core{
	private int[] stuff;
	
	public Core(Integer size){
		stuff = new int[size];		
	}//Constructor
	
	public Core(){
		stuff = new int[100];		
	}//Constructor
	
	public void setContent(int location, int value){
		if (location > stuff.length){
			System.out.printf("location %d is greater than the stugg %d%n",location,stuff.length);
		} else {
			stuff[location] = value;
		}
	}//setContent
	
	public Integer getContent(int location){
		Integer getContent;
		if (location > stuff.length){
			System.out.printf("location %d is greater than the stugg %d%n",location,stuff.length);
			getContent = null;
		} else {
			getContent =stuff[location] ;
		}	
		return getContent;
	}//getContent

}//class Mem 
