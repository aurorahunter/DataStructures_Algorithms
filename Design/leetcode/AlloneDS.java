/*
Implement a data structure supporting the following operations:

Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1. Key is guaranteed to be a non-empty string.
Dec(Key) - If Key's value is 1, remove it from the data structure. Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing. Key is guaranteed to be a non-empty string.
GetMaxKey() - Returns one of the keys with maximal value. If no element exists, return an empty string "".
GetMinKey() - Returns one of the keys with minimal value. If no element exists, return an empty string "".
Challenge: Perform all these in O(1) time complexity.

**/

public class AllOne {
 /** Initialize your data structure here. */
    class LinkedNode{
        LinkedNode left, right;
        String key;
        int val;
        public LinkedNode(String key, int val){ this.key = key; this.val = val;}
    }
    
    LinkedNode head, tail;
    Map<String, LinkedNode> maps;
    /** Initialize your data structure here. */
    public AllOne() {
            head = new LinkedNode("", Integer.MAX_VALUE);
        tail = new LinkedNode("", Integer.MIN_VALUE);
        head.right = tail;
        tail.left = head;
        maps = new HashMap<>();
    }
    private void moveToHead(LinkedNode node){
        while(node.val>node.left.val){
            LinkedNode leftNode = node.left;
            
            node.left = leftNode.left;
            leftNode.right = node.right;
            
            node.left.right = node;
            leftNode.right.left = leftNode;
            
            node.right = leftNode;
            leftNode.left = node;
        }
    }
    
    private void addToHead(LinkedNode node){
        head.right.left = node;
        node.right = head.right;
        
        node.left = head;
        head.right = node;
    }
    
    private void moveToTail(LinkedNode node){
        while(node.val<node.right.val){
            LinkedNode rightNode = node.right;
            
            node.right = rightNode.right;
            rightNode.left = node.left;
            
            node.right.left = node;
            rightNode.left.right = rightNode;
            
            node.left = rightNode;
            rightNode.right = node;
        }
    }
    
    private void addToTail(LinkedNode node){
        tail.left.right = node;
        node.left = tail.left;
        
        node.right = tail;
        tail.left = node;
    }
    
    private void remove(LinkedNode node){
        node.right.left = node.left;
        node.left.right = node.right;
        node.left = null;
        node.right = null;
    }
    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
          if(maps.containsKey(key)){
            LinkedNode node = maps.get(key);
            node.val++;
            if(node.val > head.right.val){
                remove(node);
                addToHead(node);
            }else{
                moveToHead(node);
            }
            
        }else{
            LinkedNode node = new LinkedNode(key,1);
            maps.put(key, node);
            addToTail(node);
        }
    }
    
    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
           if(!maps.containsKey(key)) return;
        LinkedNode node = maps.get(key);
        if(node.val == 1){
            maps.remove(key);
            remove(node);
        }else{
            node.val--;
            if(node.val < tail.left.val){
                remove(node);
                addToTail(node);
            }else{
                moveToTail(node);
            }
            
        }
    }
    
    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
                return head.right.key;

    }
    
    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
                return tail.left.key;

    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */