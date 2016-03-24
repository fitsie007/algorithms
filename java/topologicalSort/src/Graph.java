import java.util.ArrayList;

/**
 * Created by FitzRoi on 10/19/15.
 */
public class Graph {
    private int V; // number of vertices
    private int E; // number of edges
    private ArrayList<Edge>[] adj; // for every vertex there is a list
    // of other vertices adjacent to it (adjacency list representation of
    // a graph)
    public Graph(int V) {
        // Initialize a graph with V vertices and 0 edges
        this.V=V;
        this.E=0;
        adj = new ArrayList[V];
    }

    public Graph(String filename) {

    }

    public String toString() {
        // return printable representation of a Graph
        return "";
    }

    public Graph bfs(int s) {
        // breadth-first search from a single source s
        return this;
    }

    public Graph dfs(int s) {
        // depth-first search from a single source s
        return this;
    }

    public void addEdge(int from, int to, int w) {
        // create an edge from the vertex v to the vertex w (edge is undirected)
        if(this.adj[from-1]==null)
            this.adj[from-1]=new ArrayList<Edge>();
        this.adj[from-1].add(new Edge(to - 1, w));
    }

    public int getV(){
        return V;
    }

    public ArrayList[] getAdj(){
        return this.adj;
    }

    /**
     * Override equals methods for graphs: compare number of edges,
     * vertices and the
     * adjacency lists correspondence. Nothing to implement here ( will be
     * used for grading)
     */

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Graph)) {
            return false;
        }
        Graph that = (Graph) other;
        boolean isAdjSame = true;
        // iterate over adjacency list to check if they are the same
        try {
            for (int i = 0; i < Math.max(this.adj.length, that.adj.length); i++) {
                // sort so that order doesn’t matter
//                Collections.sort(this.adj[i]);
//                Collections.sort(that.adj[i]);
                for (int j = 0; j < Math.max(this.adj[i].size(), that.adj[i].size()); j++) {
                    if (this.adj[i].get(j) != that.adj[i].get(j)) {
                        isAdjSame=false;
                        // once at least one is found there is no need to continue
                        break;
                    }
                    if (!isAdjSame) break;
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
                isAdjSame=false;
            }
            // if graphs are the same all should match
            return this.V==(that.V)&& this.E==(that.E)&&isAdjSame;
    }

    public void printAdjacencyList(){
        for(int i=0;i<V;i++) {
            if (adj[i] != null) {
                System.out.print("v" + (i + 1) + ":");
                for (Edge edge : adj[i]) {
                    System.out.print("(v" + (edge.to + 1) + "," + edge.weight + ") ");
                }
                System.out.println();
            }
        }

    }
}
