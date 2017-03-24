/*

https://leetcode.com/problems/matchsticks-to-square/#/description
Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has, please find out a way you can make one square by using up all those matchsticks. You should not break any stick, but you can link them up, and each matchstick must be used exactly one time.

Your input will be several matchsticks the girl has, represented with their stick length. Your output will either be true or false, to represent whether you could make one square using all the matchsticks the little match girl has.

Example 1:
Input: [1,1,2,2,2]
Output: true

Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
Example 2:
Input: [3,3,3,3,4]
Output: false

Explanation: You cannot find a way to form a square with all the matchsticks.
**/

public class MatchSticks {
    public boolean makesquare(int[] nums) {
        
       if (nums == null || nums.length < 4) return false;
    int total = 0;
    for (int num : nums) total += num;
    if (total % 4 != 0) return false;
    Arrays.sort(nums);
    long sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0;
    return dfs(nums, total / 4, sum1, sum2, sum3, sum4, nums.length - 1);
}
 
private boolean dfs(int[] nums, int target, long sum1, long sum2, long sum3, long sum4, int i) {
    if (sum1 > target || sum2 > target || sum3 > target || sum4 > target) return false;
    if (i == -1 && sum1 == target && sum2 == target && sum3 == target) return true;
    else if (i == -1) return false;
     
    return dfs(nums, target, sum1+nums[i], sum2, sum3, sum4, i-1) ||
           dfs(nums, target, sum1, sum2+nums[i], sum3, sum4, i-1) ||
           dfs(nums, target, sum1, sum2, sum3+nums[i], sum4, i-1) ||
           dfs(nums, target, sum1, sum2, sum3, sum4+nums[i], i-1);
}
}