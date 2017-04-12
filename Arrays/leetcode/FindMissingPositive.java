/*

Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.

Your algorithm should run in O(n) time and uses constant space.
**/


public class FindMissingPositive {
    public int firstMissingPositive(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] <= 0 || nums[i] > nums.length) nums[i] = nums.length + 1;
    }
    for (int i = 0; i < nums.length; i++) {
        int abs = Math.abs(nums[i]);
        if (abs <= nums.length) {
            nums[abs - 1] = -Math.abs(nums[abs - 1]);
        }
    }
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] >= 0) return i + 1;
    }
    return nums.length + 1;
}
}