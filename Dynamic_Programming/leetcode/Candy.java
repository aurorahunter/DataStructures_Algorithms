/*
There are N children standing in a line. Each child is assigned a rating value.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
What is the minimum candies you must give?
**/

public class Candy {
    public int candy(int[] ratings) {
              int sum=0;
        int[] a=new int[ratings.length];
        for(int i=0;i<a.length;i++)
        {
            a[i]=1;
        }
        for(int i=0;i<ratings.length-1;i++)
        {
            if(ratings[i+1]>ratings[i])
            {
                a[i+1]=a[i]+1;
            }
        }
        for(int i=ratings.length-1;i>0;i--)
        {
            if(ratings[i-1]>ratings[i])
            {
                if(a[i-1]<(a[i]+1))
                {
                    a[i-1]=a[i]+1;
                }
            }
        }
        for(int i=0;i<a.length;i++)
        {
            sum+=a[i];
        }
        return sum;
    }
    
    
    /*
    We take ratings array as [5, 6, 2, 2, 4, 8, 9, 5, 4, 0, 5, 1]
In the given problem each student will have at least 1 candy. So distribute 1 candy to each.

ratings:     [5, 6, 2, 2, 4, 8, 9, 5, 4, 0, 5, 1]
candies:     [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
Now traverse the array from left to right. If the rating of (n+1) child is greater than (n) child then set the candy of (n+1) child as one candy more than the (n) child candies.

ratings:     [5, 6, 2, 2, 4, 8, 9, 5, 4, 0, 5, 1]
candies:     [1, 2, 1, 1, 2, 3, 4, 1, 1, 1, 2, 1]
Now traverse the array from right to left. If the (n) child rating is more than (n+1) child and (n) child candies is less than one more than (n+1) child candies then update the candies of (n) child as 1+ (n+1) candies.

ratings:     [5, 6, 2, 2, 4, 8, 9, 5, 4, 0, 5, 1]
candies:     [1, 2, 1, 1, 2, 3, 4, 3, 2, 1, 2, 1]
    
    **/
}