import java.util.Arrays;

/**
 * Created by FitzRoi on 9/28/15.
 */
public class Unknown {

    public Unknown(){}
    public void unknown(int level, int A[]){
        if (level == 3)System.out.println(Arrays.toString(A).replace(",", "").replace("[","").replace("]", ""));
        else {
            A[level] = 0;
            unknown(level + 1, A);
            A[level] = 1;
            unknown(level + 1, A);
        }
    }


}
