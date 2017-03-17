/*

the below solution provides simple solution to finding out if a given graph
has a cycle or not.
If there is no cycle, print the topological ordering of vertices.

prerequisites - provides the vertices ordering
numCourses - number of vertices in the graph

Question link: https://leetcode.com/problems/course-schedule-ii/#/description
**/

public class TopologicalSort {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
         
        int[] inDegree = new int[numCourses];
        List<List<Integer>> adjs = new ArrayList<>(numCourses);
        int n = inDegree.length;
        while (n-- > 0) adjs.add(new ArrayList<>());
        for (int[] edge : prerequisites) {
            inDegree[edge[0]]++;
            adjs.get(edge[1]).add(edge[0]);
        }
        
        
        //BFS solution
        
        int[] order = new int[inDegree.length];
        Queue<Integer> toVisit = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) toVisit.offer(i);
        }
    int visited = 0;
    while (!toVisit.isEmpty()) {
        int curr = toVisit.poll();
        order[visited++] = curr;
        for (int to : adjs.get(curr)) {
            inDegree[to]--;
            if (inDegree[to] == 0) toVisit.offer(to);
        }
    } //  while finished 
    
    return visited == inDegree.length ? order : new int[0]; 
        
}