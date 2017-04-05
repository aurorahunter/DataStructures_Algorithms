/*
Given two strings representing two complex numbers.

You need to return a string representing their multiplication. Note i2 = -1 according to the definition.

Example 1:
Input: "1+1i", "1+1i"
Output: "0+2i"
Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
Example 2:
Input: "1+-1i", "1+-1i"
Output: "0+-2i"
Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.

 (a1+b1)*(a2+b2) = (a1a2 + b1b2 + (a1b2+b1a2))
**/

public class ComplexNumberMultiply {
    public String complexNumberMultiply(String a, String b) {
         String result = "";
    String[] A = a.split("\\+");
    String[] B = b.split("\\+");
    int a1 = Integer.parseInt(A[0]);
    int b1 = Integer.parseInt(A[1].replace("i",""));

    int a2 = Integer.parseInt(B[0]);
    int b2 = Integer.parseInt(B[1].replace("i",""));

    int a1a2 = a1 * a2;
    int b1b2 = b1 * b2;
    int a1b2a2b1 = (a1 * b2) + (b1 * a2);

    String afinal = (a1a2 + (-1 * b1b2)) + "";
    String bfinal = a1b2a2b1 + "i";
    result = afinal+"+"+bfinal;
    return result;
    }
}