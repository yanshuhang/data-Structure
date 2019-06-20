package sort;

/**
 * 冒泡排序：
 * 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
 * 针对所有的元素重复以上的步骤，除了最后已排序的；
 * 重复步骤1~3，直到排序完成。
 */
public class BubbleSort {
    //遍历，比较大小交换数值
    public static void sort1(int[] a) {
        //遍历的边界
        int k = a.length - 1;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    //增加标记，若有一趟遍历没有交换值说明排序完成;排序可能在遍历完之前完成
    public static void sort2(int[] a) {
        int k = a.length - 1;
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < k; i++) {
                if (a[i] > a[i + 1]) {
                    int tmp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = tmp;
                    flag = true;
                }
            }
            k--;
        }
    }
}
