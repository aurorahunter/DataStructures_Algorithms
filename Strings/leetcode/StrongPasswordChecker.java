/*
Hi, I just tried the same idea as you to solve the problem, but I was really stucked how to deal with the situation when 
the length of the s is more than 20. Your approach impresses me a lot. Here Let me give some additional comments here.
First, we have to know, replacement operation is most efficient to deal with three more duplicate case. 
3~5 duplicated character could be validated at one operation, for example, AAAAA, we could modified it to AA#AA. 
Replacement also helps supplement type lacked.
3~4 duplicated character could be validated at one Insertion operation, say AAAA, we could insert one to AA#AA. 
Insertion also helps supplement one type lacked.
As for deletion, if the length of duplicated sequence is 3, 6, 9... one deletion acts the same efficiency as replacement, 
if the length of duplicated sequence is 4,7,10... two deletion acts the same efficiency as replacement. 
if duplicated length is 5,8,11... three deletion acts the same as replacement

A password is considered strong if below conditions are all met:

It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, a
ssuming other conditions are met).
Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change 
required to make s a strong password. If s is already strong, return 0.

Insertion, deletion or replace of any one character are all considered as one change.

**/

public class StrongPasswordChecker {
    public int strongPasswordChecker(String s) {
        
        if(s.length()<2) return 6-s.length();
        
        //Initialize the states, including current ending character(end), existence of lowercase letter(lower), uppercase letter(upper), digit(digit) and number of replicates for ending character(end_rep)
        char end = s.charAt(0);
        boolean upper = end>='A'&&end<='Z', lower = end>='a'&&end<='z', digit = end>='0'&&end<='9'; 
        
        //Also initialize the number of modification for repeated characters, total number needed for eliminate all consequnce 3 same character by replacement(change), and potential maximun operation of deleting characters(delete). Note delete[0] means maximum number of reduce 1 replacement operation by 1 deletion operation, delete[1] means maximun number of reduce 1 replacement by 2 deletion operation, delete[2] is no use here. 
        int end_rep = 1; // replication count;
        int change = 0; // replace operation needed count;
        int[] delete = new int[3]; // to deal with three case by deletion: 1. duplicated length is 3/6/9... 2. duplicated length is 4/7/10... 3. duplicated length is 5/8/11
        
        for(int i = 1;i<s.length();++i){
            if(s.charAt(i)==end) ++end_rep;
            else{
                change+=end_rep/3;  
                if(end_rep/3>0) ++delete[end_rep%3];
                //updating the states
                end = s.charAt(i);
                upper = upper||end>='A'&&end<='Z';
                lower = lower||end>='a'&&end<='z';
                digit = digit||end>='0'&&end<='9';
                end_rep = 1;
            }
        }
        change+=end_rep/3;
        if(end_rep/3>0) ++delete[end_rep%3];
        
        //The number of replcement needed for missing of specific character(lower/upper/digit)
        int check_req = (upper?0:1)+(lower?0:1)+(digit?0:1); // it could be validated by replacement or insertion.
        
        if(s.length()>20){
            int del = s.length()-20;
            
            //Reduce the number of replacement operation by deletion, It is hard to explain, but you can use "AAAAAABBCCCDDDDEEEEE"(change = 5) del = 1, 2, 3, 4, 5, 8, 10... to run it. change here might less than zero finally if del is large enough
 
            if(del<=delete[0]) change-=del; 
            else if(del-delete[0]<=2*delete[1]) change-=delete[0]+(del-delete[0])/2;  
            else change-=delete[0]+delete[1]+(del-delete[0]-2*delete[1])/3;
            
            return del+Math.max(check_req,change);
        }
        else return Math.max(6-s.length(), Math.max(check_req, change));
    }
    //https://discuss.leetcode.com/topic/63185/java-easy-solution-with-explanation/6
}
