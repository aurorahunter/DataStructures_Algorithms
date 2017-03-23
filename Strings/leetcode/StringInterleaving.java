// https://leetcode.com/problems/interleaving-string/#/description 
/*
Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

For example,
Given:
s1 = "aabcc",
s2 = "dbbca",

When s3 = "aadbbcbcac", return true.
When s3 = "aadbbbaccc", return false.

**/

public class StringInterleave {
    public boolean isInterleave(String str1, String str2, String str3) {
   boolean T[][] = new boolean[str1.length() +1][str2.length() +1];
        
        if(str1.length() + str2.length() != str3.length()){
            return false;
        }
        
        for(int i=0; i < T.length; i++){
            for(int j=0; j < T[i].length; j++){
                int l = i + j -1;
                if(i == 0 && j == 0){
                    T[i][j] = true;
                }
                else if(i == 0){
                    if(str3.charAt(l) == str2.charAt(j-1)){
                        T[i][j] = T[i][j-1];
                    }
                }
                else if(j == 0){
                    if(str1.charAt(i-1) == str3.charAt(l)){
                        T[i][j] = T[i-1][j];
                    }
                }
                else{
                    T[i][j] = (str1.charAt(i-1) == str3.charAt(l) ? T[i-1][j] : false) || (str2.charAt(j-1) == str3.charAt(l) ? T[i][j-1] : false);
                }
            }
        }
        return T[str1.length()][str2.length()];
 
 
 
}
    
}