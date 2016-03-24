import java.util.Arrays;

/**
 * Created by FitzRoi on 9/9/15.
 */
public class Quicksort {
    public  Quicksort(){}
    public void quicksort(int A[], int lo, int hi) {
        int p=hi;
//        System.out.println("Quick Sort called");

        if(lo<hi) {
            p = partition(A, lo, hi);
            System.out.println(Arrays.toString(A) +" pivot is at " + p);
        }
        else
        return;

        quicksort(A, lo, p - 1);
        quicksort(A, p + 1, hi);
    }

    public int partition(int A[], int lo, int hi) {
        int pivot = A[hi];
        int i = lo; //place for swapping
        for(int j = lo;j<=hi - 1;j++) {
            if(A[j] <= pivot) {
                swap(A, i, j);
                i = i + 1;
            }
        }
        swap(A,i,hi);
        return i;
    }

    void swap(int A[], int i, int j){
        int temp=A[i];
        A[i]=A[j];
        A[j]=temp;
    }

    public static void main(String args[]){
        int A[]={18,24,	19,	46,	47,	13,	33,	2,	40,	30,	43,	29,	42,	47,	23,	41,	40,	37,	3,	13,	28,	34,	13,	11,	49,	24,	20,	24,	23,	46,	6,	5};
        Quicksort q=new Quicksort();
        q.quicksort(A,0,A.length-1);

    }
}
