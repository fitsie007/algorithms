import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by FitzRoi on 8/20/15.
 */
public class MSQS {
    /** Cubic maximum contiguous subsequence sum algorithm **/
    public static ArrayList<Integer[]> MSQS1(int A[]) {
        int N = A.length;
        int thisSum;
        int maxSum = Integer.MIN_VALUE;
        int indices[] = new int[N];
        int len, index;


        ArrayList<Integer[]> result=new ArrayList<Integer[]>(2);
       Integer sq[]=null;
        String seq="";
        String bestSeq="";

        for (int i = 0; i < N - 1; i++) {

            for (int j = i; j < N - 1; j++) {  // choice of subsequence i through j
                thisSum = 0;
                index=0;
                for (int k = i; k <= j; k++) {  // addition loop

                    if(k==i) { //determine the length of the sequence
                        len = ((j - i) + 1);
                        sq = new Integer[len];
                    }

                    thisSum = thisSum + A[k];    // O(N3

                    sq[index]=k;
                    index++;

                    if (thisSum > maxSum) {
                        maxSum = thisSum;        // O(N2)
                        result.add(0,new Integer[]{new Integer(maxSum)});
                        result.add(1,sq);
                    }

                }

            }
        }

        return result;

    }
    /** Quadratic maximum contiguous subsequence sum algorithm. **/
    public static int MSQS2(int A[]){
        int N = A.length;
        int thisSum;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i<N-1;i++) {
            thisSum = 0;
            for (int j = i; j < N - 1;j++){
                    thisSum = thisSum + A[j];  // reuse the partial sum from the previous iteration
                    if (thisSum > maxSum)
                            maxSum = thisSum;
                }
        }
        return maxSum;

    }

    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     */
    private static int maxSumRec( int [ ] a, int left, int right )
    {
        if( left == right )  // Base case
            if( a[ left ] > 0)
                return a[ left ];
            else
                return 0;

        int center = ( left + right ) / 2;
        int maxLeftSum  = maxSumRec( a, left, center );
        int maxRightSum = maxSumRec( a, center + 1, right );

        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for( int i = center; i >= left; i-- )
        {
            leftBorderSum += a[ i ];
            if( leftBorderSum > maxLeftBorderSum )
                maxLeftBorderSum = leftBorderSum;
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for( int i = center + 1; i <= right; i++ )
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
                maxRightBorderSum = rightBorderSum;
        }

        return max3( new int[]{maxLeftSum, maxRightSum,
                maxLeftBorderSum + maxRightBorderSum} );
    }

    public static int max3(int a[]){
        int max=a[0];
        for(int i=1;i<3;i++)
            if(a[i]>max)max=a[i];
        return max;
    }
    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     */
    public static int MSQS3( int [ ] a )
    {
        return maxSumRec( a, 0, a.length-1);
    }

    public static int MSQS4(int A[]){
        int N = A.length;
        int thisSum;
        int maxSum = Integer.MIN_VALUE;

        maxSum = 0;	thisSum = 0;
        for (int j = 0; j<N-1; j++)
        {	thisSum = thisSum + A[j];  // reuse the partial sum from the previous iteration
            if (thisSum > maxSum)
                    maxSum = thisSum;
            else if (thisSum < 0)
                    thisSum =0;	// ignore computations so far
        }
        return maxSum;

    }

    public static void main(String args[]){
        int A[]={3,  1, -7,  1,  9,  -2,  3, -1};
        ArrayList<Integer[]> result=MSQS1(A);
        System.out.println("====== Algorithm 1 -- O(N^3) =======");

        System.out.println("Max subseq sum of A" + Arrays.toString(A) +" = " +result.get(0)[0] +"\n");
        System.out.println("The indices=" + Arrays.toString(result.get(1)) +"\n");

        System.out.println("====== Algorithm 2 -- O(N^2) =======");
        System.out.println("Max subseq sum of A" + Arrays.toString(A) +" = " +MSQS2(A) +"\n");

        System.out.println("====== Algorithm 3 -- O(N LOG N) =======");
        System.out.println("Max subseq sum of A" + Arrays.toString(A) +" = " +MSQS3(A) +"\n");

        System.out.println("====== Algorithm 4 -- O(N) =======");
        System.out.println("Max subseq sum of A" + Arrays.toString(A) +" = " +MSQS4(A) +"\n");


    }
}
