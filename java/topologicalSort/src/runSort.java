import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FitzRoi on 10/19/15.
 */
public class runSort {
    public static void main(String args[] ) throws IOException {

        File configFile = null;
        int from, to, weight;


        if(args.length>0) {
            if (args[0].equals("-config")) {
                if (1 < args.length) {
                    configFile = new File(args[1]);
                }
            }
        }

        if (configFile != null) {
            TopologicalSort ts=new TopologicalSort(configFile);
            int N = ts.getVstr().replace("{","").replace("}","").split(",").length;
            Graph g = new Graph(N);
            String key="(?<key>\\(v\\d+?\\s?\\,\\s?v\\d+?\\s?\\,\\s?\\d+\\))"; //eg: (2, 3)
            Pattern pattern = Pattern.compile(key);

            Matcher matcher = pattern.matcher(ts.getEstr());
            while (matcher.find()) {
                String keyStr=matcher.group(1).replace("v","").replace("(","").replace(")",""); //eg: (v1, v2, 10) becomes 1, 2, 10
                String vals[] = keyStr.split(",");
                if(vals.length>2) {

                    from=Integer.parseInt(vals[0].trim());
                    to=Integer.parseInt(vals[1].trim());
                    weight=Integer.parseInt(vals[2].trim());
//                    System.out.println(from + " " + to +" " + weight);
                    g.addEdge(from, to, weight);
//                    g.addEdge(to, from, weight);
                }
            }


            ts.init(g);
            ts.printAdjacencyList();
            ts.sort();
        }
    }
}
