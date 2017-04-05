/*

You need to construct a binary tree from a string consisting of parenthesis and integers.

The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis. The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.

You always start to construct the left child node of the parent first if it exists.

Example:
Input: "4(2(3)(1))(6(5))"
Output: return the tree root node representing the following tree:

       4
     /   \
    2     6
   / \   / 
  3   1 5   
Note:
There will only be '(', ')', '-' and '0' ~ '9' in the input string.
An empty tree is represented by "" instead of "()".
**/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode str2tree(String s) {
         if (s.length() == 0) return null;
        char[] tokens = s.toCharArray();
        int n = tokens.length;
        int start = -1;
        int mid = -1;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (tokens[i] == '(') {
                if (start == -1) start = i;
                count++;
            }
            else if (tokens[i] == ')') {
                count--;
                if (count == 0) {
                    mid = i;
                    break;
                }
            }
        }
        if (start == -1) return new TreeNode(Integer.parseInt(s));
        TreeNode root = new TreeNode(Integer.parseInt(s.substring(0, start)));
        root.left = str2tree(s.substring(start + 1, mid));
        if (mid + 2 < s.length() - 1) root.right = str2tree(s.substring(mid + 2, s.length() - 1));
        return root;

    }
}