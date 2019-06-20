package sort;

import sun.security.util.Length;

import java.util.Arrays;

/**
 * 堆排序
 * 建立最大堆、最大值跟最后要给值替换，调整最大堆
 */
public class HeapSort {

    public static void sort(int[] a) {
        createMaxHeap(a);
        //循环：没替换一次 将最有一个节点从堆中去除，并从索引0开始调整堆，直到堆大小为1
        for (int i = a.length - 1; i > 0 ; i--) {
            swap(a, 0, i);
            adjustMaxHeap(a,0, i);
        }
    }

    /**
     * 创建最大堆
     * 从最后一个非叶节点开始调整其为顶点的堆，往上遍历
     * @param a
     */
    private static void createMaxHeap(int[] a) {
        for (int i = (a.length/2) -1 ; i > 0; i--) {
            adjustMaxHeap(a,i, a.length);
        }
    }

    /**
     * 调整最大堆, 跟左右节点比较 不是最大就替换，继续往下调整
     * @param a 数组
     * @param i 起始索引
     * @param j 结尾索引
     */
    private static void adjustMaxHeap(int[] a, int i, int j) {
        int tmp = a[i];
        for (int k = i*2 +1; k < j; k = k*2+1) {
            //左右节点比较出较大的
            if (k+1 < j && a[k] < a[k+1]) {
                k++;
            }
            //较大的跟tmp比较，更大则互换，继续for循环
            if (a[k] > tmp) {
                swap(a,i,k);
                i = k;
            } else { //tmp比较大 直接中断 已调整好
                break;
            }
        }
    }

    /**
     * 交换数组索引值
     * @param a
     * @param i
     * @param j
     */
    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
