/*

Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

**/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

	PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>((a, b) -> a.val - b.val);
	for (ListNode list : lists) {
	   if (list != null) {
		   pq.offer(list); // only add non-null elements into the pq
	   }
	}

	ListNode dummy = new ListNode(0);
	ListNode ptr = dummy;
	// Loop over the pq and selectively add nodes
	while (!pq.isEmpty()) {
	   ListNode node = pq.poll();
           ptr.next = node;
	   if (node != null && node.next != null) {
		   // only add non-null elements into the pq
		   pq.offer(node.next);
	   }
	   ptr = ptr.next;
	}

	ptr.next = null; // terminate list

	return dummy.next;
    }
}
/*

Suppose the total number of nodes is n The total time complexity if (n * log k) .For a priority queue, insertion takes logK time
I think my code's complexity is also O(nlogk) and not using heap or priority queue, n means the total elements and 
k means the size of list.

The mergeTwoLists functiony in my code comes from the problem Merge Two Sorted Lists whose complexity obviously is O(n), 
n is the sum of length of l1 and l2.

To put it simpler, assume the k is 2^x, So the progress of combination is like a full binary tree, from bottom to top. 
So on every level of tree, the combination complexity is n, beacause every level have all n numbers without repetition. 
The level of tree is x, ie logk. So the complexity is O(nlogk).

public class Solution {
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null || lists.isEmpty()) return null;
        int n = lists.size() - 1;
        
        while (n > 0) {
            for (int i = 0; i < (n + 1) / 2; i++) {
                lists.set(i, merge(lists.get(i), lists.get(n - i)));
            }
            n /= 2;
        }
        
        return lists.get(0);
    }
    
    private ListNode merge(ListNode node1, ListNode node2) {
        ListNode virtual = new ListNode(0);
        ListNode node = virtual;
        
        while (node1 != null || node2 != null) {
            if (node2 == null || (node1 != null && node1.val < node2.val)) {
                node.next = node1;
                node1 = node1.next;
            } else {
                node.next = node2;
                node2 = node2.next;
            }
            node = node.next;
        }
        
        return virtual.next;
    }
}
*/