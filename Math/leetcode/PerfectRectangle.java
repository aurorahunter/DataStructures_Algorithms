/*

Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular region.

Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is represented as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).


Example 1:

rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [3,2,4,4],
  [1,3,2,4],
  [2,3,3,4]
]

Return true. All 5 rectangles together form an exact cover of a rectangular region.

Example 2:

rectangles = [
  [1,1,2,3],
  [1,3,2,4],
  [3,1,4,2],
  [3,2,4,4]
]

Return false. Because there is a gap between the two rectangular regions.

Example 3:

rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [1,3,2,4],
  [3,2,4,4]
]

Return false. Because there is a gap in the top center.

Example 4:

rectangles = [
  [1,1,3,3],
  [3,1,4,2],
  [1,3,2,4],
  [2,2,4,4]
]

Return false. Because two of the rectangles overlap with each other.
**/


public class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        if (rectangles.length == 0 || rectangles[0].length == 0) return false;

        int x1 = Integer.MAX_VALUE;
        int x2 = Integer.MIN_VALUE;
        int y1 = Integer.MAX_VALUE;
        int y2 = Integer.MIN_VALUE;
        
        HashSet<String> set = new HashSet<String>();
        int area = 0;
        
        for (int[] rect : rectangles) {
            x1 = Math.min(rect[0], x1);
            y1 = Math.min(rect[1], y1);
            x2 = Math.max(rect[2], x2);
            y2 = Math.max(rect[3], y2);
            
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            
            String s1 = rect[0] + " " + rect[1];
            String s2 = rect[0] + " " + rect[3];
            String s3 = rect[2] + " " + rect[3];
            String s4 = rect[2] + " " + rect[1];
            
            if (!set.add(s1)) set.remove(s1);
            if (!set.add(s2)) set.remove(s2);
            if (!set.add(s3)) set.remove(s3);
            if (!set.add(s4)) set.remove(s4);
        }
        
        if (!set.contains(x1 + " " + y1) || !set.contains(x1 + " " + y2) || !set.contains(x2 + " " + y1) || !set.contains(x2 + " " + y2) || set.size() != 4) return false;
        
        return area == (x2-x1) * (y2-y1);
    }
    
    /*
    
    The right answer must satisfy two conditions:

1) the large rectangle area should be equal to the sum of small rectangles
2) count of all the points should be even, and that of all the four corner points should be one


Type2:
This is an expanded version of my earlier post under the contest discussion board.
The following code passes through not only the OJ but also various test cases others have pointed out.

Idea

0_1472399247817_perfect_rectangle.jpg

Consider how the corners of all rectangles appear in the large rectangle if there's a perfect rectangular cover.
Rule 1: The local shape of the corner has to follow one of the three following patterns

Corner of the large rectangle (blue): it occurs only once among all rectangles
T-junctions (green): it occurs twice among all rectangles
Cross (red): it occurs four times among all rectangles
Rule 2: A point can only be the top-left corner of at most one sub-rectangle. Similarly it can be the top-right/bottom-left/bottom-right corner of at most one sub-rectangle. Otherwise overlaps occur.

Proof of correctness

Obviously, any perfect cover satisfies the above rules. So the main question is whether there exists an input which satisfy the above rules, yet does not compose a rectangle.

First, any overlap is not allowed based on the above rules because

aligned overlap like [[0, 0, 1, 1], [0, 0, 2, 2]] are rejected by Rule 2.
unaligned overlap will generate a corner in the interior of another sub-rectangle, so it will be rejected by Rule 1.
Second, consider the shape of boundary for the combined shape. The cross pattern does not create boundary. The corner pattern generates a straight angle on the boundary, and the T-junction generates a straight border.
So the shape of the union of rectangles has to be rectangle(s).

Finally, if there are more than two non-overlapping rectangles, at least 8 corners will be found, and cannot be matched to the 4 bounding box corners (be reminded we have shown that there is no chance of overlapping).
So the cover has to be a single rectangle if all above rules are satisfied.

Algorithm

Step1: Based on the above idea we maintain a mapping from (x, y)->mask by scanning the sub-rectangles from beginning to end.

(x, y) corresponds to corners of sub-rectangles
mask is a 4-bit binary mask. Each bit indicates whether there have been a sub-rectangle with a top-left/top-right/bottom-left/bottom-right corner at (x, y). If we see a conflict while updating mask, it suffice to return a false during the scan.
In the meantime we obtain the bounding box of all rectangles (which potentially be the rectangle cover) by getting the upper/lower bound of x/y values.
Step 2: Once the scan is done, we can just browse through the unordered_map to check the whether the mask corresponds to a T junction / cross, or corner if it is indeed a bounding box corner.
(note: my earlier implementation uses counts of bits in mask to justify corners, and this would not work with certain cases as @StefanPochmann points out).

Complexity

The scan in step 1 is O(n) because it loop through rectangles and inside the loop it updates bounding box and unordered_map in O(1) time.

Step2 visits 1 corner at a time with O(1) computations for at most 4n corners (actually much less because either corner overlap or early stopping occurs). So it's also O(n).
    **/
}