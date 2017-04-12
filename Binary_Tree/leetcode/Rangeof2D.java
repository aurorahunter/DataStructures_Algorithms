/*
Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
update(3, 2, 2)
sumRegion(2, 1, 4, 3) -> 10
Note:
The matrix is only modifiable by the update function.
You may assume the number of calls to update and sumRegion function is distributed evenly.
You may assume that row1 ≤ row2 and col1 ≤ col2.
**/
public class Rangeof2D {
    // Using 2D Binary Indexed Tree, 2D BIT Def:
    // bit[i][j] saves the rangeSum of [i-(i&-i), i] x [j-(j&-j), j]
    // note bit index == matrix index + 1
    int n, m;
    int[][] bit, a;

    public NumMatrix(int[][] matrix) {
        if (matrix.length < 1) return;
        n = matrix.length; m = matrix[0].length;
        bit = new int[n + 1][m + 1]; a = new int[n][m];
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                update(i, j, matrix[i][j]);
    }

    public void update(int row, int col, int val) {
        int diff = val - a[row][col];
        a[row][col] = val;
        for (int i = row + 1; i <= n; i += i & -i)
            for (int j = col + 1; j <= m; j += j & -j)
                bit[i][j] += diff;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) { // O(1)
        return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
    }
    
    public int sum(int row, int col) {
        int tot = 0;
        for (int i = row + 1; i > 0; i -= i & -i)
            for (int j = col + 1; j > 0; j -= j & -j)
                tot += bit[i][j];
        return tot;
    }
}
//https://discuss.leetcode.com/topic/63059/13ms-update-o-n-sumregion-o-m-time-row-sum-java-solution 

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */