/*

Given a group of two strings, you need to find the longest uncommon subsequence of this group of two strings. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

The input will be two strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

Example 1:
Input: "aba", "cdc"
Output: 3
Explanation: The longest uncommon subsequence is "aba" (or "cdc"), 
because "aba" is a subsequence of "aba", 
but not a subsequence of any other strings in the group of two strings. 
**/
public class LongestUncommonSequence1 {
    public int findLUSlength(String a, String b) {
         return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }
}

/*
Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

Example 1:
Input: "aba", "cdc", "eae"
Output: 3

**/

public class LongestUncommonSequence2 {
public int findLUSlength(String[] strs) {
        int len = strs.length;
        
        // reverse sorting array with length 
        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        
        for(int i=0; i<len; i++){
            int missMatchCount = strs.length - 1;
            for(int j=0; j<len; j++){
                if(i != j && !isSubSequence(strs[i], strs[j])){
                    missMatchCount --;
                }
            }
            
            // strs[i] is not a sub sequence of any other entry
            if(missMatchCount == 0){
                return strs[i].length();
            }
        }
        
        return -1;
    }
    
    private boolean isSubSequence(String s1, String s2){
        int idx = 0;
        for(char ch : s2.toCharArray()){
            if(idx < s1.length() && ch == s1.charAt(idx)){
                idx++;
            }
        }
        
        return idx == s1.length();
    }
}

/*
When we add a letter Y to our candidate longest uncommon subsequence answer of X, it only makes it strictly harder to find a common subsequence. Thus our candidate longest uncommon subsequences will be chosen from the group of words itself.

Suppose we have some candidate X. We only need to check whether X is not a subsequence of any of the other words Y. To save some time, we could have quickly ruled out Y when len(Y) < len(X), either by adding "if len(w1) > len(w2): return False" or enumerating over A[:i] (and checking neighbors for equality.) However, the problem has such small input constraints that this is not required.

We want the max length of all candidates with the desired property, so we check candidates in descending order of length. When we find a suitable one, we know it must be the best global answer.
**/