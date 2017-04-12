/*

Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the empty string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
**/

public class Solution {
    public String minWindow(String s, String t) {
        if(s.length() < t.length()) {
        return "";
    }
    
    int[] counter = new int[256];
    for(int i = 0;i < t.length();i++) {
        counter[t.charAt(i)]++;
    }
    int minLen = Integer.MAX_VALUE;
    String minStr = "";
    int j = 0;
    for(int i = 0;i < s.length();i++) {
        while(j < s.length()) {
            if(!isValid(counter)) {
                counter[s.charAt(j)]--;
                j++;
            } else {
                break;
            }
        }
        if(isValid(counter) && j - i + 1 < minLen) {
            minLen = j - i + 1;
            minStr = s.substring(i, j);
        }
        counter[s.charAt(i)]++;
    }
    return minStr;
    
}

private boolean isValid(int[] counter) {
    for(int i = 0;i < counter.length;i++) {
        if(counter[i] > 0) {
            return false;
        }
    }
    return true;
}

/*

The idea is to keep track of number of missing characters in s.substring(b, e)

In the loop, do the following:

Increase end index if there are missing characters
Increase begin index if no characters are missing
Let's run the following test case for example:

String s = "ADOBECODEBANC";
String t = "ABC";
Here begin is inclusive, and end is exclusive.

begin: 0 end: 0 substring missing 3
begin: 0 end: 1 substring A missing 2
begin: 0 end: 2 substring AD missing 2
begin: 0 end: 3 substring ADO missing 2
begin: 0 end: 4 substring ADOB missing 1
begin: 0 end: 5 substring ADOBE missing 1
begin: 0 end: 6 substring ADOBEC missing 0
begin: 1 end: 6 substring DOBEC missing 1
begin: 1 end: 7 substring DOBECO missing 1
begin: 1 end: 8 substring DOBECOD missing 1
begin: 1 end: 9 substring DOBECODE missing 1
begin: 1 end: 10 substring DOBECODEB missing 1
begin: 1 end: 11 substring DOBECODEBA missing 0
begin: 2 end: 11 substring OBECODEBA missing 0
begin: 3 end: 11 substring BECODEBA missing 0
begin: 4 end: 11 substring ECODEBA missing 0
begin: 5 end: 11 substring CODEBA missing 0
begin: 6 end: 11 substring ODEBA missing 1
begin: 6 end: 12 substring ODEBAN missing 1
begin: 6 end: 13 substring ODEBANC missing 0
begin: 7 end: 13 substring DEBANC missing 0
begin: 8 end: 13 substring EBANC missing 0
begin: 9 end: 13 substring BANC missing 0
begin: 10 end: 13 substring ANC missing 1

Choose the shortest result marked with "missing 0"
**/

}