import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FitzRoi on 9/5/15.
 */
public class runMatrixChainMultiplier {

    public static void main(String args[] ) throws IOException {

        File configFile = null;
        ArrayList<Tuple> objects = new ArrayList<Tuple>();

        if(args.length>0) {
            if (args[0].equals("-config")) {
                if (1 < args.length) {
                    configFile = new File(args[1]);
                }
            }
        }

        if (configFile != null) {
            MatrixChain matrixChain=new MatrixChain(configFile);
            String tuple="(?<tuple>\\(\\d+x\\s?\\d+\\s?\\))"; //eg: (4x6)
            Pattern pattern = Pattern.compile(tuple);

            Matcher matcher = pattern.matcher(matrixChain.getMatrixInputStr());
            while (matcher.find()) {
                String tupleArray[];
                String tupleStr=matcher.group(1).replace("(","").replace(")",""); //eg: 4x6
                tupleArray=tupleStr.split("x");
                objects.add(new Tuple(Integer.parseInt(tupleArray[0].trim()), Integer.parseInt(tupleArray[1].trim())));
            }

            matrixChain.init(objects);
            matrixChain.computeMatrixChain();
            matrixChain.printP();
            matrixChain.printOptimalParenthesizations();
            matrixChain.printBreakPoints();
//            System.out.println(matrixChain.which(1,4));
//           matrixChain.printObjects();
        }
    }
}
