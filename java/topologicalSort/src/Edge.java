/**
 * Created by FitzRoi on 10/19/15.
 */
public class Edge<Integer> {
    public final int to;
    public final int weight;
    public Edge(int to, int w) {
        this.to = to;
        this.weight = w;
    }

    public int getWeight(){
        return this.weight;
    }
}
