import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by FitzRoi on 10/19/15.
 */
public class TopologicalSort{
    private String vStr;
    private String eStr;
    private static int V;
    private Graph G;
    private Queue<Integer> Q;





    public String getVstr() {
        return vStr;
    }

    public void setVstr(String setStr) {
        this.vStr = setStr;
    }

    public String getEstr() {
        return eStr;
    }

    public void setEstr(String setStr) {
        this.eStr = setStr;
    }
    public String toString(){
        return G.toString();
    }

    public void printAdjacencyList(){
        G.printAdjacencyList();
    }


    public void init(Graph g){
        this.G=g;
        V=g.getV();
        Q = new LinkedList<Integer>();
    }







//    public void printObjects(){
//        for(int i=0;i<this.objects.size();i++){
//            System.out.print(objects.get(i).row +",");
//            System.out.print(objects.get(i).col +"\n");
//        }
//    }



    public TopologicalSort(File configFile) throws IOException {

        if (configFile != null && configFile.isFile())
        {
            Properties properties = new Properties();
            FileInputStream input = null;
            try
            {
                input = new FileInputStream(configFile);
                properties.load(input);

                this.setVstr(properties.getProperty("V", "0").trim());
                this.setEstr(properties.getProperty("E", "0").trim());

            }
            finally
            {
                if (input != null)
                {
                    input.close();
                }
            }
        }
        else
        {
            System.out.println("Error reading config file.");
            System.exit(-1);
        }
    }

    public void sort(){
        int counter=0;
        int orderingId[] = new int[V];
        int [] inDegrees = new int[V];
        ArrayList<Edge>[] adj = this.G.getAdj();

//        System.out.println(Arrays.toString(inDegrees));

        //initialize indegrees
        for(int v=0;v<V;v++) {
            if (adj[v] != null) {
                for (Edge edge : adj[v]) {
                    inDegrees[edge.to]++;
                }

                if(inDegrees[v]==0)
                    Q.offer(v);
            }
        }

        counter = 0;
        while(!Q.isEmpty()) {
            // indegree becomes 0 once & only once for a node
            // so, each node goes to Q only once: (n)

            System.out.println(Arrays.toString(inDegrees));
            int v = Q.remove();
            orderingId[v] = ++counter;
//            V = V-v;

            if(adj[v]!=null)
            for(Edge e : adj[v]) {  //nodes adjacent to v
                // two loops together (max{N, |E|})
                // each edge scanned once and only once
                int w = e.to;
                System.out.println(Arrays.asList(adj[v].toArray()));

                adj[v].remove(w);
                inDegrees[w]--;
                if (inDegrees[w] == 0)
                    Q.offer(w);

                System.out.println(Q.size());
            }
        }

        if(counter != V)
            return;
            // not all nodes are numbered yet, but Q is empty
            // cycle detected;


        System.out.println(Arrays.toString(orderingId));

    }

    public int indegree(Vertex v){
        return 0;
    }

}
