/*
A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. 
Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), 
write a program to output the skyline formed by these buildings collectively (Figure B).

 Buildings Skyline Contour
The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], 
where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. 
It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are 
perfect rectangles grounded on an absolutely flat surface at height 0.

For instance, the dimensions of all buildings in Figure A are recorded as: [
 [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .

The output is a list of "key points" (red dots in Figure B) in the format of 
[ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a 
horizontal line segment. Note that the last key point, where the rightmost building ends, 
is merely used to mark the termination of the skyline, and always has zero height. 
Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

Notes:

The number of buildings in any input list is guaranteed to be in the range [0, 10000].
The input list is already sorted in ascending order by the left x position Li.
The output list must be sorted by the x position.
There must be no consecutive horizontal lines of equal height in the output skyline. 
For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 
should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]

**/

public class Solution {
    public List<int[]> getSkyline(int[][] buildings) {
           List<int[]> result = new ArrayList<>();
    List<int[]> height = new ArrayList<>();
    for(int[] b:buildings) {
        // start point has negative height value
        height.add(new int[]{b[0], -b[2]});
        // end point has normal height value
        height.add(new int[]{b[1], b[2]}); 
    }

    // sort $height, based on the first value, if necessary, use the second to
    // break ties
    Collections.sort(height, (a, b) -> {
            if(a[0] != b[0]) 
                return a[0] - b[0];
            return a[1] - b[1];
    });

    // Use a maxHeap to store possible heights
    Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));

    // Provide a initial value to make it more consistent
    pq.offer(0);

    // Before starting, the previous max height is 0;
    int prev = 0;

    // visit all points in order
    for(int[] h:height) {
        if(h[1] < 0) { // a start point, add height
            pq.offer(-h[1]);
        } else {  // a end point, remove height
            pq.remove(h[1]);
        }
        int cur = pq.peek(); // current max height;
  
        // compare current max height with previous max height, update result and 
        // previous max height if necessary
        if(prev != cur) {
            result.add(new int[]{h[0], cur});
            prev = cur;
        }
    }
    return result;
}
    // https://discuss.leetcode.com/topic/28482/once-for-all-explanation-with-clean-java-code-o-n-2-time-o-n-space
    
    /*
Though I came up with a solution using PriorityQueue and BST, this problems still confuses me. 
To make it more clear, I went through it several times and investigated several good solutions on this forum.
Here is my explanation which tries to make understanding this easier and may help you write a bug-free solution quickly.

When visiting all start points and end points in order:

Observations:

If a position is shadowed by other buildings

 1. height of that building is larger than the building to which current
     position belong;
 2. the start point of that building must be smaller(or equal to) than this
     position;
 3. the end point of that building must be larger(or equal to) than this
     position;
Tus we have:

1. when you reach a start point, the height of current building immediately
    takes effect which means it could possibly affect the contour or shadow
    others when mixed with other following buildings;
2. when you reach a end point, the height of current building will stop its
    influences;
3. our target exists at the position where height change happens and there
    is nothing above it shadowing it;
Obviously, to implement the idea that 'current height takes effect' and 'find out whether current height is shadowed by other buildings', we need a mechanism to store current taking effect heights, meanwhile, figure out which one is the maximum, delete it if needed efficiently, which hints us to use a priority queue or BST.

Thus, our algorithm could be summarised in following pseudo code:

for position in sorted(all start points and all end points)
       if this position is a start point
              add its height
       else if this position is a end point
              delete its height
       compare current max height with previous max height, if different, add
       current position together with this new max height to our result, at the
       same time, update previous max height to current max height;
To implement this algorithm, here are some concrete examples:

In my implementation, I use a PriorityQueue to store end point values when visiting a start point, and store the [height, end point value] into a TreeMap. Thus:
when moving to next start point value, I can compare the next start point value with elements in PriorityQueue, thus achieving visiting all start points and end points in order(exploits the fact that start points are already sorted);
Meantime, I can get current max height from TreeMap in O(logn);
However, to delete a height when visiting a end point, I have to use 'map.values.remove()' which is a method defined in Collection interface and tends to be slower(O(n) is this case, plz correct me if I'm wrong);
My code can be found at https://leetcode.com/discuss/62617/short-and-clean-java-solution-heap-and-treemap

Following is wujin's implementation(plz refer to https://leetcode.com/discuss/54201/short-java-solution). This one is quite straightforward, clean and clever.
Firstly, please notice what we need to achieve:

  1. visit all start points and all end points in order;
  2. when visiting a point, we need to know whether it is a start point or a
      end point, based on which we can add a height or delete a height from
      our data structure;
To achieve this, his implementation:

  1. use a int[][] to collect all [start point, - height] and [end point, height]
      for every building;
  2. sort it, firstly based on the first value, then use the second to break
      ties;
Thus,

  1. we can visit all points in order;
  2. when points have the same value, higher height will shadow the lower one;
  3. we know whether current point is a start point or a end point based on the
      sign of its height;
    **/
    
}