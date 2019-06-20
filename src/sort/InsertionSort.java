package sort;

/**
 * 插入排序 构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入
 * @author 严书航
 */
public class InsertionSort {
    public static void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            for (int j = i - 1; j >= 0; j--) {
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    a[j] = key;
                } else {
                    break;
                }
            }
        }
    }
}
