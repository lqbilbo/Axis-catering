package scratch;

public class CountSort {

    /**
     * 计数排序
     * @param array 待排序数组
     * @param max 待排序数组中的最大值
     * @return
     */
    public static int[] countingSort(int[] array, int max) {
        int[] result = new int[array.length];
        int[] temp = new int[max + 1];
        for (int i : array) {
            temp[i]++;
        }
        for (int i=1;i<temp.length;i++) {
            temp[i] += temp[i-1];
        }
        for (int i=array.length-1;i>-1;i--) {
            result[temp[array[i]] - 1] = array[i];
            temp[array[i]]--;
        }
        return result;
    }

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {8,9,6,13,5,99,100,230,1,0,18,22,3};
        printArray(countingSort(array, 230));
    }

}
