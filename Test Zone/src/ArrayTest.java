
public class ArrayTest {

	public static void main(String args[]) {
		int[] pis = {3, 1, 4, 1, 5};
		System.out.println(fizbin(pis));
	}

	public static int fizbin(int[] myArray) {

		   int start = 0;

		   for (int  counter = 1; counter < myArray.length; counter++) {

		     start = start + myArray[counter];

		    }

		 

		return start;

		}
}
