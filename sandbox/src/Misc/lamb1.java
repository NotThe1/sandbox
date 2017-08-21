package Misc;

import java.util.function.Function;

public class lamb1 {

	public static void main(String[] args) {
		Function apply = (a)->(int) a * (int) a;
		System.out.printf("apply(a) = %d%n", operate(apply, 5));
	}//main
	
	public static int operate(Function f, int a) {
		return (int) f.apply(a);
	};
	

}//class lamb1
