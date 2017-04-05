/*
public String reverseStr(String s, int k) {
    if(s==null||k==1) return s;
    
    char[] chars = s.toCharArray();
    int left=0, len=s.length();

    while(left+2*k<=len){ //reverse every 2k
        reverse(chars, left, left+k-1);
        left += 2*k;
    }
    
    if(left+k<=len){ //reverse first k
        reverse(chars, left, left+k-1);
    }else{ //reverse all remaining
        reverse(chars, left, len-1);
    }
    
    return String.valueOf(chars);
}

private void reverse(char[] chars, int start, int end){
    while(start<end){
        char tmp = chars[start];
        chars[start++] = chars[end];
        chars[end--] = tmp;
    }
}
Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and left the other as original.
Example:
Input: s = "abcdefg", k = 2
Output: "bacdfeg"
Restrictions:
The string consists of lower English letters only.
Length of the given string and k will in the range [1, 10000]
**/

public class ReverseString2 {
    public String reverseStr(String s, int k) {
         StringBuilder sb = new StringBuilder();
        
        int i = 0, j = 0;
        while (i < s.length()) {
            // first k
            j = i + k <= s.length() ? i + k : s.length();
            sb.append((new StringBuilder(s.substring(i, j))).reverse().toString());
            
            if (j >= s.length()) break;
            
            // second k
            i = j;
            j = i + k <= s.length() ? i + k : s.length();
            sb.append(s.substring(i, j));
            
            i = j;
        }
        
        return sb.toString();
    }
}