import java.util.Arrays;

/**
 * Created by FitzRoi on 9/28/15.
 */
public class Run {
    public static void main(String args[] ) {
//        Unknown u = new Unknown();
//        u.unknown(0, new int[]{0,0,0});

        BTKS btks = new BTKS();
        btks.BTKS(0, btks.getInitA());

        System.out.println("\n-----------Optimization 1--------------------");
        btks = new BTKS();
        btks.BTKS_1(0, btks.getInitA());

        System.out.println("\n-----------Optimization 2--------------------");
        btks = new BTKS();
        btks.BTKS_2(0, btks.getInitA());


        System.out.println("\n-----------Optimization 3--------------------");
        btks = new BTKS();
        btks.BTKS_3(0, btks.getInitA());

    }
}
