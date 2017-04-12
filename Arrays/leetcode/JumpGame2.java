/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

For example:
Given array A = [2,3,1,1,4]

The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)

Note:
You can assume that you can always reach the last index.

**/

public class Solution {
    public int jump(int[] A) {
        int jumps = 0, curEnd = 0, curFarthest = 0;
	for (int i = 0; i < A.length - 1; i++) {
		curFarthest = Math.max(curFarthest, i + A[i]);
		if (i == curEnd) {
			jumps++;
			curEnd = curFarthest;
		}
	}
	return jumps;
    }
    /*
    The main idea is based on greedy. Let's say the range of the current jump is [curBegin, curEnd], curFarthest is the farthest point that all points in [curBegin, curEnd] can reach. Once the current point reaches curEnd, then trigger another jump, and set the new curEnd with curFarthest, then keep the above steps, as the following:
 
    BFS
     public int jump(int[] nums) {
        if(nums.length<2) return 0;
        int level=0;        //count the level            
        int curmax=0;       //help the cur
        int nextmax=0;      //record the value of curmax for further use
        int i=0;          //working point of code
        while(nextmax>=i){  //how many numbers of point in this level ,then whether it can reach this level
            level++;
            for(;i<=nextmax;i++){                   //loop to this level, to get the max number of next level
                curmax=Math.max(curmax,nums[i]+i);  
                if(curmax>=nums.length-1) return level; //decide whether reach end
            }
            nextmax=curmax;
        }
        return 0;           //return 0 if can't reach end
    }
**/
}
