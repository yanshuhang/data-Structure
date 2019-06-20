package sort;

/**
 * 选择排序：
 * 先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置；
 * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 * 重复直到遍历完成
 */
public class SelectionSort {
    public static void sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int minIndex = i;
            for (int j = i; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = a[minIndex];
            a[minIndex] = a[i];
            a[i] = tmp;
        }
    }
}
