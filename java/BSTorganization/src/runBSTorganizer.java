import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FitzRoi on 9/7/15.
 */
public class runBSTorganizer {
    public static void main(String args[] ) throws IOException {

        File configFile = null;
        ArrayList<Integer> objects=new ArrayList<Integer>();

        if(args.length>0) {
            if (args[0].equals("-config")) {
                if (1 < args.length) {
                    configFile = new File(args[1]);
                }
            }
        }

        if (configFile != null) {
            BSTorganizer BSTo=new BSTorganizer(configFile);
            String key="(?<key>\\(\\s?\\d+\\s?\\))"; //eg: (4)
            Pattern pattern = Pattern.compile(key);

            Matcher matcher = pattern.matcher(BSTo.getBSTkeyStr());
            while (matcher.find()) {
                String keyStr=matcher.group(1).replace("(","").replace(")",""); //eg: 6
                objects.add(Integer.parseInt(keyStr));
            }

//            BSTo.init(objects);
//            BSTo.organizeBST();
//            BSTo.printP();

            BSTo.init(objects);
            BSTo.computeBSTMinCost();
            BSTo.printObjects();
            BSTo.printP();
//            System.out.println(BSTo.which(1,4));
            BSTo.printComputationTrace();
//           matrixChain.printObjects();
        }
    }
}
