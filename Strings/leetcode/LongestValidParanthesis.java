/*
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

For "(()", the longest valid parentheses substring is "()", which has length = 2.

Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
**/

public class LongestValidParanthesis {
    public int longestValidParentheses(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right >= left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxlength;
    }
    
    /*
    In this approach, we make use of two counters leftleft and rightright. First, we start traversing the string from the left towards the right and for every 
‘(’
‘(’ encountered, we increment the leftleft counter and for every 
‘)’
‘)’ encountered, we increment the rightright counter. Whenever leftleft becomes equal to rightright, we calculate the length of the current valid string and keep track of maximum length substring found so far. If rightright becomes greater than leftleft we reset leftleft and rightright to 00.

Next, we start traversing the string from right to left and similar procedure is applied.
Complexity Analysis

Time complexity : O(n). Two traversals of the string.

Space complexity : O(1). Only two extra variables leftleft and rightright are needed.
    **/
    
    
    /*
    Solution2 using Stacks, require extra O(n) space
    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }
    Algorithm

Instead of finding every possible string and checking its validity, we can make use of stack while scanning the given string to check if the string scanned so far is valid, and also the length of the longest valid string. In order to do so, we start by pushing -1−1 onto the stack.

For every 
‘(’
‘(’ encountered, we push its index onto the stack.

For every 
‘)’
‘)’ encountered, we pop the topmost element and subtract the current element's index from the 
top element of the stack, which gives the length of the currently encountered valid string of 
parentheses. If while popping the element, the stack becomes empty, we push the current element's 
index onto the stack. In this way, we keep on calculating the lengths of the valid substrings, 
and return the length of the longest valid string at the end.
    
}