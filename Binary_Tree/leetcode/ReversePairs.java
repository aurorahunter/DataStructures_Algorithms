/*
 
It looks like a host of solutions are out there (BST-based, BIT-based, Merge-sort-based). Here I'd like to focus on the general principles behind these solutions and its possible application to a number of similar problems.

The fundamental idea is very simple: break down the array and solve for the subproblems.

A breakdown of an array naturally reminds us of subarrays. To smoothen our following discussion, let's assume the input array is nums, with a total of n elements. 
Let nums[i, j] denote the subarray starting from index i to index j (both inclusive), T(i, j) as the same problem applied to this subarray 
(for example, for Reverse Pairs, T(i, j) will represent the total number of important reverse pairs for subarray nums[i, j]).

With the definition above, it's straightforward to identify our original problem as T(0, n - 1). 
Now the key point is how to construct solutions to the original problem from its subproblems. 
This is essentially equivalent to building recurrence relations for T(i, j). 
Since if we can find solutions to T(i, j) from its subproblems, we surely can build solutions to larger subarrays 
until eventually the whole array is spanned.

While there may be many ways for establishing recurrence relations for T(i, j), here I will only introduce the 
following two common ones:

T(i, j) = T(i, j - 1) + C, i.e., elements will be processed sequentially and C denotes the subproblem for processing the 
last element of subarray nums[i, j]. We will call this sequential recurrence relation.
T(i, j) = T(i, m) + T(m + 1, j) + C where m = (i+j)/2, i.e., subarray nums[i, j] will be further partitioned into 
two parts and C denotes the subproblem for combining the two parts. We will call this partition recurrence relation.
For either case, the nature of the subproblem C will depend on the problem under consideration, and it will determine 
the overall time complexity of the original problem. So usually it's crucial to find efficient algorithm for solving 
this subproblem in order to have better time performance. Also pay attention to possibilities of overlapping subproblems, 
in which case a dynamic programming (DP) approach would be preferred.

Next, I will apply these two recurrence relations to this problem "Reverse Pairs" and list some solutions for your reference.

I -- Sequential recurrence relation

Again we assume the input array is nums with n elements and T(i, j) denotes the total number of important reverse pairs for subarray nums[i, j]. For sequential recurrence relation, we can set i = 0, i.e., the subarray always starts from the beginning. Therefore we end up with:

T(0, j) = T(0, j - 1) + C

where the subproblem C now becomes "find the number of important reverse pairs with the first element of the pair coming 
from subarray nums[i, j - 1] while the second element of the pair being nums[j]".

Note that for a pair (p, q) to be an important reverse pair, it has to satisfy the following two conditions:

p < q: the first element must come before the second element;
nums[p] > 2 * nums[q]: the first element has to be greater than twice of the second element.
For subproblem C, the first condition is met automatically; so we only need to consider the second condition, 
which is equivalent to searching for all elements within subarray nums[i, j - 1] that are greater than twice of nums[j].

The straightforward way of searching would be a linear scan of the subarray, which runs at the order of O(j). 
From the sequential recurrence relation, this leads to the naive O(n^2) solution.

To improve the searching efficiency, a key observation is that the order of elements in the subarray does not matter, 
since we are only interested in the total number of important reverse pairs. 
This suggests we may sort those elements and do a binary search instead of a plain linear scan.

If the searching space (formed by elements over which the search will be done) is "static" (it does not vary from run to run), 
placing the elements into an array would be perfect for us to do the binary search. 
However, this is not the case here. After the j-th element is processed, we need to add it to the searching space so that it 
becomes searchable for later elements, which renders the searching space expanding as more and more elements are processed.

Therefore we'd like to strike a balance between searching and insertion operations. 
This is where data structures like binary search tree (BST) or binary indexed tree (BIT) prevail, 
which offers relatively fast performance for both operations.

1. BST-based solution

we will define the tree node as follows, where val is the node value and cnt is the total number of elements in the subtree rooted at current node that are greater than or equal to val:

class Node {
    int val, cnt;
    Node left, right;
        
    Node(int val) {
        this.val = val;
        this.cnt = 1;
    }
}
The searching and insertion operations can be done as follows:

private int search(Node root, long val) {
    if (root == null) {
    	return 0;
    } else if (val == root.val) {
    	return root.cnt;
    } else if (val < root.val) {
    	return root.cnt + search(root.left, val);
    } else {
    	return search(root.right, val);
    }
}

private Node insert(Node root, int val) {
    if (root == null) {
        root = new Node(val);
    } else if (val == root.val) {
        root.cnt++;
    } else if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.cnt++;
        root.right = insert(root.right, val);
    }
    
    return root;
}
And finally the main program, in which we will search for all elements no less than twice of current element plus 1 
(converted to long type to avoid overflow) while insert the element itself into the BST.

Note: this homemade BST is not self-balanced and the time complexity can go as bad as O(n^2) 
(in fact you will get TLE if you copy and paste the solution here). To guarantee O(nlogn) performance, 
use one of the self-balanced BST's (e.g. Red-black tree, AVL tree, etc.).

public int reversePairs(int[] nums) {
    int res = 0;
    Node root = null;
    	
    for (int ele : nums) {
        res += search(root, 2L * ele + 1);
        root = insert(root, ele);
    }
    
    return res;
}
2. BIT-based solution

For BIT, the searching and insertion operations are:

private int search(int[] bit, int i) {
    int sum = 0;
    
    while (i < bit.length) {
        sum += bit[i];
        i += i & -i;
    }

    return sum;
}

private void insert(int[] bit, int i) {
    while (i > 0) {
        bit[i] += 1;
        i -= i & -i;
    }
}
And the main program, where again we will search for all elements greater than twice of current element 
while insert the element itself into the BIT. For each element, the "index" function will return its index in the BIT. 
Unlike the BST-based solution, this is guaranteed to run at O(nlogn).

public int reversePairs(int[] nums) {
    int res = 0;
    int[] copy = Arrays.copyOf(nums, nums.length);
    int[] bit = new int[copy.length + 1];
    
    Arrays.sort(copy);
    
    for (int ele : nums) {
        res += search(bit, index(copy, 2L * ele + 1));
        insert(bit, index(copy, ele));
    }
    
    return res;
}

private int index(int[] arr, long val) {
    int l = 0, r = arr.length - 1, m = 0;
    	
    while (l <= r) {
    	m = l + ((r - l) >> 1);
    		
    	if (arr[m] >= val) {
    	    r = m - 1;
    	} else {
    	    l = m + 1;
    	}
    }
    
    return l + 1;
}
More explanation for the BIT-based solution:

We want the elements to be sorted so there is a sorted version of the input array which is copy.

The bit is built upon this sorted array. Its length is one greater than that of the copy array to account for the root.

Initially the bit is empty and we start doing a sequential scan of the input array. 
For each element being scanned, we first search the bit to find all elements greater than twice of it and add the result to res. We then insert the element itself into the bit for future search.

Note that conventionally searching of the bit involves traversing towards the root from some index of the bit, 
which will yield a predefined running total of the copy array up to the corresponding index. 
For insertion, the traversing direction will be opposite and go from some index towards the end of the bit array.

For each scanned element of the input array, its searching index will be given by the index of the first element in 
the copy array that is greater than twice of it (shifted up by 1 to account for the root), 
while its insertion index will be the index of the first element in the copy array that is no less than itself 
(again shifted up by 1). This is what the index function is for.

For our case, the running total is simply the number of elements encountered during the traversal process. 
If we stick to the convention above, the running total will be the number of elements smaller than the one at the given index, 
since the copy array is sorted in ascending order. 
However, we'd actually like to find the number of elements greater than some value (i.e., twice of the element being scanned), 
therefore we need to flip the convention. This is what you see inside the search and insert functions: 
the former traversing towards the end of the bit while the latter towards the root.

II -- Partition recurrence relation

For partition recurrence relation, setting i = 0, j = n - 1, m = (n-1)/2, we have:

T(0, n - 1) = T(0, m) + T(m + 1, n - 1) + C

where the subproblem C now reads "find the number of important reverse pairs with the first element of the pair 
coming from the left subarray nums[0, m] while the second element of the pair coming from the right subarray nums[m + 1, n - 1]".

Again for this subproblem, the first of the two aforementioned conditions is met automatically. 
As for the second condition, we have as usual this plain linear scan algorithm, applied for each element in the 
left (or right) subarray. This, to no surprise, leads to the O(n^2) naive solution.

Fortunately the observation holds true here that the order of elements in the left or right subarray does not matter, 
which prompts sorting of elements in both subarrays. 
With both subarrays sorted, the number of important reverse pairs can be found in linear time by employing 
the so-called two-pointer technique: one pointing to elements in the left subarray while the other to those in the 
right subarray and both pointers will go only in one direction due to the ordering of the elements.

The last question is which algorithm is best here to sort the subarrays. 
Since we need to partition the array into halves anyway, it is most natural to adapt it into a Merge-sort. 
Another point in favor of Merge-sort is that the searching process above can be embedded seamlessly into its merging stage.

So here is the Merge-sort-based solution, where the function "reversePairsSub" will return the total number of 
important reverse pairs within subarray nums[l, r]. The two-pointer searching process is represented by the 
nested while loop involving variable p, while the rest is the standard merging algorithm.

public int reversePairs(int[] nums) {
    return reversePairsSub(nums, 0, nums.length - 1);
}
    
private int reversePairsSub(int[] nums, int l, int r) {
    if (l >= r) return 0;
        
    int m = l + ((r - l) >> 1);
    int res = reversePairsSub(nums, l, m) + reversePairsSub(nums, m + 1, r);
        
    int i = l, j = m + 1, k = 0, p = m + 1;
    int[] merge = new int[r - l + 1];
        
    while (i <= m) {
        while (p <= r && nums[i] > 2L * nums[p]) p++;
        res += p - (m + 1);
        	
        while (j <= r && nums[i] >= nums[j]) merge[k++] = nums[j++];
        merge[k++] = nums[i++];
    }
        
    while (j <= r) merge[k++] = nums[j++];
        
    System.arraycopy(merge, 0, nums, l, merge.length);
        
    return res;
}
III -- Summary

Many problems involving arrays can be solved by breaking down the problem into subproblems applied on subarrays and 
then link the solution to the original problem with those of the subproblems, to which we have sequential recurrence 
relation and partition recurrence relation. For either case, it's crucial to identify the subproblem C and find 
efficient algorithm for approaching it.

If the subproblem C involves searching on "dynamic searching space", try to consider data structures that 
support relatively fast operations on both searching and updating (such as self-balanced BST, BIT, Segment tree, ...).
If the subproblem C of partition recurrence relation involves sorting, Merge-sort would be a nice sorting algorithm to use. 
Also, the code could be made more elegant if the solution to the subproblem can be embedded into the merging process.
If there are overlapping among the subproblems T(i, j), it's preferable to save the intermediate results for future lookup.
Last let me name a few leetcode problems that fall into the patterns described above and thus can be solved with similar ideas.

315. Count of Smaller Numbers After Self
327. Count of Range Sum

For leetcode 315, applying the sequential recurrence relation (with j fixed), the subproblem C reads: 
find the number of elements out of visited ones that are smaller than current element, 
which involves searching on "dynamic searching space"; applying the partition recurrence relation, 
we have a subproblem C: for each element in the left half, find the number of elements in the right half that are 
smaller than it, which can be embedded into the merging process by noting that these elements are exactly those 
swapped to its left during the merging process.

For leetcode 327, applying the sequential recurrence relation (with j fixed) on the pre-sum array, 
the subproblem C reads: find the number of elements out of visited ones that are within the given range, 
which again involves searching on "dynamic searching space"; applying the partition recurrence relation, 
we have a subproblem C: for each element in the left half, find the number of elements in the right half 
that are within the given range, which can be embedded into the merging process using the two-pointer technique.

Anyway, hope these ideas can sharpen your skills for solving array-related problems.


Let me add more explanations for why the BIT approach is
"i += i&(-i)" for search, and
"i -= i &(-i)" for insert.
which is contrary to the "commonly" used way for BIT, where
"i += (-i)" for insert, and
"i -= i&(-i)" for search

First, the concept of "search(i)" here should be explained as "getSum(i)", which is to get the accumulative frequency from the starting index to i (inclusively), where i is an index in BIT.

In this problem, we want to get how many elements that are greater than 2 * nums[j], (num[j] is the current value that we are visiting). Therefore, instead of "searching down", here we need to "searching up".

Based on the classical BIT format, where:
"i += (-i)" for insert
"i -= i&(-i)" for search

One possible way is to use the getRange approach,
so we can use getSum(MaxNumberOfValue) - getSum(index(2*nums[j])) to get the number of elements that are greater than 2 * nums[j].

Another possible way is just like @fun4LeetCode implements,
we can reverse the direction for insert and search in BIT
so what we get is always the number greater than query values by using single search method.


Share my AVL Tree Implementation:
This is a somewhat long solution, but a good practice to use an AVL Tree, LOL...
Ref:
(1) https://discuss.leetcode.com/topic/83451/share-my-avl-tree-solution-o-nlgn-time
(2) http://www.geeksforgeeks.org/avl-tree-set-1-insertion/

public class Solution {

public int reversePairs(int[] nums) {
    
    // Algo thinking: building a BST, go left when node.val <= 2 * root.val, right otherwise
    // But need to keep it balanced -> AVL Tree or Red-Black Tree
    // time = O(NlgN), space = O(N)
    
    if (nums == null || nums.length == 0) return 0;
    
    int n = nums.length;
    
    TreeNode root = new TreeNode(nums[0]);
    int ans = 0;
    for (int i = 1; i < nums.length; i++) {
        ans += search(root, (long) nums[i] * 2);
        root = insert(root, (long) nums[i]);
        // preOrder(root);
        // System.out.println();
    }
    
    return ans;
        
}

private int search(TreeNode root, long key) {
    
    if (root == null) return 0;
    
    if (key < root.val) {       // key < root.val:  go left
        return root.rightCount + search(root.left, key);
    } else {                    // key >= root.val: go right
        return search(root.right, key);
    }
}

private TreeNode insert(TreeNode root, long key) {
    
    if (root == null) return new TreeNode(key);
    
    if (key < root.val) {   // key < root.val:  go left
        root.left = insert(root.left, key);
    } else if (key == root.val){
        root.rightCount++;
        return root;
    } else {
        root.rightCount++;
        root.right = insert(root.right, key);
    }
    
    root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    
    int balance = getBalance(root);
    
    // System.out.println(root.val + " balance " + balance);
    
    // case 1 left left 
    if (balance > 1 && getHeight(root.left.left) > getHeight(root.left.right)) {
        return rightRotate(root);
    }
    
    // case 2 left right 
    if (balance > 1 && getHeight(root.left.left) < getHeight(root.left.right)) {
        root.left = leftRotate(root.left);
        return  rightRotate(root);
    }
    
    // case 3 right right
    if (balance < -1 && getHeight(root.right.left) < getHeight(root.right.right)) {
        return leftRotate(root);
    }
    
    // case 4 right left 
    if (balance < -1 && getHeight(root.right.left) > getHeight(root.right.right)) {
        root.right = rightRotate(root.right);
        return leftRotate(root);
    }
    
    return root;
}

private TreeNode leftRotate(TreeNode root) {
    
    // setp 1: take care of nodes
    TreeNode newRoot = root.right;
    TreeNode b = newRoot.left;
    
    newRoot.left = root;
    root.right = b;
    
    // step 2: take care of height
    root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
    
    // step 3: take care of rightCount
    root.rightCount -= getRightCount(newRoot);

    return newRoot;
}

private TreeNode rightRotate(TreeNode root) {
    
    // setp 1: take care of nodes
    TreeNode newRoot = root.left;
    TreeNode b = newRoot.right;
    
    newRoot.right = root;
    root.left = b;
    
    // step 2: take care of height
    root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
    
    // step 3: take care of rightCount
    newRoot.rightCount += getRightCount(root);
    
    return newRoot;
}


private int getHeight(TreeNode node) {
    return node == null ? 0 : node.height;
}

private int getBalance(TreeNode node) {
    return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
}

private int getRightCount(TreeNode node) {
    return node == null ? 0 : node.rightCount;
}

private void preOrder(TreeNode root) {
    
    if (root == null) {
        System.out.print("NIL ");
        return;
    }
    
    System.out.print(root.val + " ");
    preOrder(root.left);
    preOrder(root.right);
}

class TreeNode {
    
    long val;
    int rightCount;
    int height;
    TreeNode left;
    TreeNode right;
    public TreeNode(long val) {
        this.val = val;
        height = 1;
        rightCount = 1;
    }
}
}

**/

/*
Problem statement:
Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

You need to return the number of important reverse pairs in the given array.

Example1:

Input: [1,3,2,3,1]
Output: 2
Example2:

Input: [2,4,3,5,1]
Output: 3

**/

public class ReversePairs {
    int[] helper;
    public int reversePairs(int[] nums) {
        this.helper = new int[nums.length];
        return mergeSort(nums, 0, nums.length-1);
    }
    private int mergeSort(int[] nums, int s, int e){
        if(s>=e) return 0; 
        int mid = s + (e-s)/2; 
        int cnt = mergeSort(nums, s, mid) + mergeSort(nums, mid+1, e); 
        for(int i = s, j = mid+1; i<=mid; i++){
            while(j<=e && nums[i]/2.0 > nums[j]) j++; 
            cnt += j-(mid+1); 
        }
        //Arrays.sort(nums, s, e+1); 
        myMerge(nums, s, mid, e);
        return cnt; 
    }
    
    private void myMerge(int[] nums, int s, int mid, int e){
        for(int i = s; i<=e; i++) helper[i] = nums[i];
        int p1 = s;//pointer for left part
        int p2 = mid+1;//pointer for rigth part
        int i = s;//pointer for sorted array
        while(p1<=mid || p2<=e){
            if(p1>mid || (p2<=e && helper[p1] >= helper[p2])){
                nums[i++] = helper[p2++];
            }else{
                nums[i++] = helper[p1++];
            }
        }
    }
    
    // Please document " General principle behind this"
    //https://discuss.leetcode.com/topic/79227/general-principles-behind-problems-similar-to-reverse-pairs