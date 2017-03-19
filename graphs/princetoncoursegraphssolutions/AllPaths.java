/*
http://algs4.cs.princeton.edu/41graph/AllPaths.java.html
All paths in a graph. Write a program AllPaths.java that enumerates all simple paths in a graph 
between two specified vertices. 
Hint: use DFS and backtracking. Warning: there many be exponentially many simple paths in a graph, 
so no algorithm can run efficiently for large graphs.
**/
public class AllPaths {
    private boolean[] onPath;        // vertices in current path
    private Stack<Integer> path;     // the current path
    private int numberOfPaths;       // number of simple path

    // show all simple paths from s to t - use DFS
    public AllPaths(Graph G, int s, int t) {
        onPath = new boolean[G.V()];
        path   = new Stack<Integer>();
        dfs(G, s, t);
    }


    // use DFS
    private void dfs(Graph G, int v, int t) {

        // add v to current path
        path.push(v);
        onPath[v] = true;

        // found path from s to t
        if (v == t) {
            processCurrentPath();
            numberOfPaths++;
        }

        // consider all neighbors that would continue path with repeating a node
        else {
            for (int w : G.adj(v)) {
                if (!onPath[w])
                    dfs(G, w, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath[v] = false;
    }

    // this implementation just prints the path to standard output
    private void processCurrentPath() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : path)
            reverse.push(v);
        if (reverse.size() >= 1)
            StdOut.print(reverse.pop());
        while (!reverse.isEmpty())
            StdOut.print("-" + reverse.pop());
        StdOut.println();
    }

    // return number of simple paths between s and t
    public int numberOfPaths() {
        return numberOfPaths;
    }


    // test client
    public static void main(String[] args) {
        Graph G = new Graph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(2, 5);
        G.addEdge(1, 5);
        G.addEdge(5, 4);
        G.addEdge(3, 6);
        G.addEdge(4, 6);
        StdOut.println(G);

        StdOut.println();
        StdOut.println("all simple paths between 0 and 6:");
        AllPaths allpaths1 = new AllPaths(G, 0, 6);
        StdOut.println("# paths = " + allpaths1.numberOfPaths());

        StdOut.println();
        StdOut.println("all simple paths between 1 and 5:");
        AllPaths allpaths2 = new AllPaths(G, 1, 5);
        StdOut.println("# paths = " + allpaths2.numberOfPaths());
    }
}