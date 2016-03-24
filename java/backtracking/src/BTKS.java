import java.util.Arrays;

/**
 * Created by FitzRoi on 9/28/15.
 */
public class BTKS {
    private int W[] = {5, 3, 6, 7, 2}; //{(5, 3), (3, 30), (6, 44), (7, 56), (2,20)}
    private int P[] = {3, 30, 44, 56, 20};
    private int n = W.length-1;
    private int M = 8;

    private float total_profit;
    private float total_weight;
    private float optP = 0;
    private int optA[] = null;

    public BTKS(){}

    public int[] getW() {
        return W;
    }

    public int[] getInitA() {
        int a[]=new int[n];
//        for(int i=0;i<=n;i++)
//            a[i]=0;
        return a;
    }

    public void setW(int[] w) {
        W = w;
    }

    public int[] getP() {
        return P;
    }

    public void setP(int[] p) {
        P = p;
    }

    /** without optimization **/

    public void BTKS(int level, int A[]){

        if (level == n) {
            total_profit = sum(A,P);  // computing profit on each leaf
            System.out.print("Profit(" +Arrays.toString(Arrays.copyOfRange(A,0,n)).replace(",", "").replace("[","").replace("]", "") +") = ");
            System.out.print(total_profit);
//            System.out.println();
//            System.out.println(Arrays.toString(Arrays.copyOfRange(A,0,n)).replace(",", ""));
            System.out.println("\n");
        }
        else {

            A[level] = 0;     // level-th object excluded from the bag
//            System.out.print(Arrays.toString(Arrays.copyOfRange(A,0,n)).replace(",", "") +"\t\t");
            BTKS(level + 1, A);
            A[level] = 1;     // the object is included
//            System.out.print(Arrays.toString(Arrays.copyOfRange(A,0,n)).replace(",", "") +"\n");
            BTKS(level + 1, A);

        }
    }

    /** Step 1 of optimization **/

    public void BTKS_1(int level, int A[]){
        if (level == n) {
            total_profit = sum(A, P);  // computing profit on each leaf

            if(total_profit > optP) {        // optimizing
                optP = total_profit;
                optA = A;
                printResults();
            }

        }
        else {
            A[level] = 0;     // level-th object excluded from the bag
            BTKS_1(level + 1, A);
            A[level] = 1;     // the object is included
            BTKS_1(level + 1, A);
        }
    }


    /** Step 2 of optimization **/

    public void BTKS_2(int level, int A[]){
        if (level == n) {
            total_profit = sum(A, P);  // computing profit on each leaf
            total_weight = sum(A, this.getW());  // computing weight on each leaf

            if(total_weight <= M  && total_profit > optP) {        // optimizing
                optP = total_profit;
                optA = A;
                printResults();
            }

        }
        else {
            A[level] = 0;     // level-th object excluded from the bag
            BTKS_2(level + 1, A);
            A[level] = 1;     // the object is included
            BTKS_2(level + 1, A);
        }
    }


    /** Step 3 of optimization **/

    public void BTKS_3(int level, int A[]){
        if (level == n) {
            total_profit = sum(A, P);  // computing profit on each leaf

            if(total_profit > optP) {        // optimizing
                optP = total_profit;
                optA = A;
                printResults();
            }

        }
        else {
            A[level] = 0;     // level-th object excluded from the bag
            BTKS_3(level + 1, A);
            A[level] = 1;     // the object is included
            total_weight = sum(A, this.getW(), level);  // computing weight on each leaf
            if(total_weight <= M) //pruning branches
                BTKS_3(level + 1, A);
        }
    }


    /** Step 4 of optimization (pruning) **/

    public void BTKS_4(int level, int A[]){
        if (level == n) {
            total_profit = sum(A, P);  // computing profit on each leaf

            if(total_profit > optP) {        // optimizing
                optP = total_profit;
                optA = A;
                printResults();
            }

        }
        else {
            float B = sum(A,P,level-1) + RKS(A, level-1, M - sum(A, W,level-1));
            if(B >= optP) {
                A[level] = 1;     // the object is included
                total_weight = sum(A, this.getW(), level);  // computing weight on each leaf
                if (total_weight <= M) //pruning branches
                    BTKS_4(level + 1, A);
                if(B >= optP){
                    A[level] = 0;
                    BTKS_4(level + 1, A);
                }
            }
        }
    }

    public float RKS(int A[], int level, float w){
        return 0;
    }


    public float sum(int A[], int P[]){
        float sum=0;
        for(int i=0;i<n; i++)
            sum+=A[i]*P[i];
        return sum;
    }

    public float sum(int A[], int P[], int l){
        float sum=0;
        for(int i=0;i<l; i++)
            sum+=A[i]*P[i];
        return sum;
    }

    public void printResults(){
        System.out.print("Profit(" +Arrays.toString(Arrays.copyOfRange(optA,0,n)).replace(",", "").replace("[","").replace("]", "") +") = ");
        System.out.print(optP);
        System.out.println();
    }
}
