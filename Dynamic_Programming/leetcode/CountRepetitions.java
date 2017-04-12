/*
Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] ="abcabcabc".

On the other hand, we define that string s1 can be obtained from string s2 if we can remove some characters from s2 such 
that it becomes s1. For example, “abc” can be obtained from “abdbec” based on our definition, but it can not be obtained f
rom “acbbe”.

You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 0 ≤ n1 ≤ 106 
and 1 ≤ n2 ≤ 106. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. 
Find the maximum integer M such that [S2,M] can be obtained from S1.
**/

public class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
          if (s1.length() == 0 || s2.length() == 0 || n2 == 0 || n1 == 0) return 0;
    int n2count = 0, n1count = 0;
    int i = 0;
    int j = 0;
    while (n1count < n1) {
        if (s1.charAt(i) == s2.charAt(j)) j++;
        i++;
        
        if (i == s1.length()) {
            i = 0;
            n1count++;
        }
        if (j == s2.length()) {
            j = 0;
            n2count++;
        }
        if (i == 0 && j == 0) break;
    }
    return (int)((long)n1*n2count/n1count/n2);
    }
    
    /*
    @zaim said in Nice O(len(s1)*len(s2)) worst case:

        if (i == 0 && j == 0) break;
    }
    return (int)((long)n1*n2count/n1count/n2);
Thanks for sharing the trick, but why does it work? I understand it passes the test cases but it looks suspicious to compute the total s2 counts by n1*n2count/n1count when n1 is not a multiple of n1count. Any analysis about this?
Besides, this is brute force with O(len(s1)n
    **/
}