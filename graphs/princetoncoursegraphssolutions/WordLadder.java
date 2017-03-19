//http://algs4.cs.princeton.edu/41graph/WordLadder.java.html
/*
Word ladders. Write a program WordLadder.java that takes two 5 letter strings from the command line, 
and reads in a list of 5 letter words from standard input, and 
prints out a shortest word ladder connecting the two strings (if it exists). 
Two words can be connected in a word ladder chain if they differ in exactly one letter. 
As an example, the following word ladder connects green and brown.
green greet great groat groan grown brown
**/
public class WordLadder {

    // return true if two strings differ in exactly one letter
    public static boolean isNeighbor(String a, String b) {
        assert a.length() == b.length();
        int differ = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) differ++;
            if (differ > 1) return false;
        }
        return true;
    }

    public static void main(String[] args) {

       /*******************************************************************
        *  Read a list of strings, all of the same length.
        *******************************************************************/
        In in = new In(args[0]);
        IndexSET<String> words = new IndexSET<String>();
        while (!in.isEmpty()) {
            String word = in.readString();
            words.add(word);
        }
        System.err.println("Finished reading word list");

       /*******************************************************************
        *  Insert connections between neighboring words into graph.
        *  This construction process can be improved from LN^2 in the worst
        *  case to L^2 N in the worst case by L radix sorts where
        *  N = number of strings and L = length of each words.
        *
        *  We avoid inserting two copies of each edge by checking if
        *  word1.compareTo(word2) < 0
        *
        *******************************************************************/
        Graph G = new Graph(words.size());
        for (String word1 : words.keys()) {
            for (String word2 : words.keys()) {
                if (word1.length() != word2.length()) {
                    throw new RuntimeException("Words have different lengths");
                }
                if (word1.compareTo(word2) < 0 && isNeighbor(word1, word2)) {
                    G.addEdge(words.indexOf(word1), words.indexOf(word2));
                }
            }
        }
        System.err.println("Finished building graph");

       /*******************************************************************
        *  Run breadth first search
        *******************************************************************/
        while (!StdIn.isEmpty()) {
            String from = StdIn.readString();
            String to   = StdIn.readString();
            if (!words.contains(from)) throw new RuntimeException(from + " is not in word list");
            if (!words.contains(to))   throw new RuntimeException(to   + " is not in word list");

            BreadthFirstPaths bfs = new BreadthFirstPaths(G, words.indexOf(from));
            if (bfs.hasPathTo(words.indexOf(to))) {
                StdOut.println("length = " + bfs.distTo(words.indexOf(to)));
                for (int v : bfs.pathTo(words.indexOf(to))) {
                    StdOut.println(words.keyOf(v));
                }
            }
            else StdOut.println("NOT CONNECTED");
            StdOut.println();
        }
    }
}
