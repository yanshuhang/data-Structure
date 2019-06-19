package sort;
//每一轮遍历都找出最大值放在最后面
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
