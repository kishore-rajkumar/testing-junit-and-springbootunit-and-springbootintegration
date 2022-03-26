package source;

public class ArrayMethods {
	int findIndex(int[] array, int number) {
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == number)
				return i;
		}
		return index;
	}

}