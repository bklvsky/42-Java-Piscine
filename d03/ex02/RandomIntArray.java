import java.util.Random;

public class RandomIntArray {

    private Integer[] arr;

    public RandomIntArray(int size) {
        Random rd = new Random();
        arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rd.nextInt(2000) - 1000;
        }
    }

    public Integer[] getArr() {
        return this.arr;
    }
}
