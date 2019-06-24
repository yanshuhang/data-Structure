package sort;

import java.util.Arrays;

/**
 * 归并排序
 * 将已有序的子序列合并，得到完全有序的序列
 */
public class MergeSort {

    /**
     * 递归排序子序列
     * @param a
     * @return
     */
    public static int[] sort(int[] a) {
        if (a.length < 2) {
            return a;
        }
        int mid = a.length/2;
        int[] left = Arrays.copyOfRange(a, 0, mid);
        int[] right = Arrays.copyOfRange(a, mid, a.length);
        return merge(sort(left), sort(right));
    }

    /**
     * 将两个有序数组合并成一个有序数组
     * @param left
     * @param right
     * @return
     */
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < result.length; k++) {
            if (i >= left.length) {
                result[k] = right[j++];
            } else if (j >= right.length) {
                result[k] =left[i++];
            } else if (left[i] < right[j]) {
                result[k] = left[i++];
            } else {
                result[k] = right[j++];
            }
        }
        return result;
    }
}
