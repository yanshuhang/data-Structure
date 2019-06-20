package sort;

import java.util.Random;

/**
 * 快速排序
 * 通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序
 */
public class QuickSort {

    public static void sort(int[] a) {
        quickSrot(a, 0, a.length - 1);
    }

    private static void quickSrot(int[] a, int start, int end) {
        int smallInedx = partition(a, start, end);
        if (smallInedx-1 > start) {
            quickSrot(a, start, smallInedx - 1);
        }
        if (smallInedx+1 < end) {
            quickSrot(a, smallInedx + 1, end);
        }
    }

    //分割数组为较大和较小两部分
    private static int partition(int[] a, int start, int end) {
        int smallIndex = start - 1;
        for (int i = start; i <= end; i++) {
            if (a[i] <= a[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    //将小于分割值的替换的前面
                    swap(a, i, smallIndex);
                }
            }
        }
        //返回较小和较大的分割索引
        return smallIndex;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
