import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FitzRoi on 9/2/15.
 */
public class runKnapsackFiller {

    public static void main(String args[] ) throws IOException {

        File configFile = null;
        ArrayList<Tuple> objects = new ArrayList<Tuple>();

        if (args[0].equals("-config")) {
            if (1 < args.length) {
                configFile = new File(args[1]);
            }
        }

        if (configFile != null) {
            O1Knapsack knapsack=new O1Knapsack(configFile);
            String tuple="(?<tuple>\\(\\d+\\,\\s?\\d+\\))"; //eg: (2, 3)
            Pattern pattern = Pattern.compile(tuple);

            Matcher matcher = pattern.matcher(knapsack.getSetStr());
            while (matcher.find()) {
                String tupleArray[];
                String tupleStr=matcher.group(1).replace("(","").replace(")",""); //eg: 2, 3
                tupleArray=tupleStr.split(",");
                objects.add(new Tuple(Integer.parseInt(tupleArray[0].trim()), Integer.parseInt(tupleArray[1].trim())));
            }

            knapsack.init(objects);
            knapsack.fillKnapsack();
            knapsack.printP();
            knapsack.peekInside();
            System.out.println(knapsack.which(4,8));
//           knapsack.printObjects();
        }
    }
}
