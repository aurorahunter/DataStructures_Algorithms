/*

Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
Could you do both operations in O(1) time complexity?
**/

public class LFUCache {
 HashMap<Integer, Integer> keyToValue;
    HashMap<Integer, Integer> keyToCounts;
    HashMap<Integer, LinkedHashSet<Integer>> countsToKey;
    int capacity;
    int minCount;
    public LFUCache(int capacity) {
         this.capacity = capacity;
        this.minCount = 0;
        this.keyToValue = new HashMap<Integer, Integer>();
        this.keyToCounts = new HashMap<Integer, Integer>();
        this.countsToKey = new HashMap<Integer, LinkedHashSet<Integer>>();
    }
    
    public int get(int key) {
          if (!keyToValue.containsKey(key)) return -1;
        increaseCount(key);
        return keyToValue.get(key);
    }
    
    public void put(int key, int value) {
         if (capacity <= 0) return;
        if (keyToValue.containsKey(key)) {
            keyToValue.put(key, value);
            increaseCount(key);
        }
        else {
            if (capacity <= keyToValue.size()) {
                removeTail();
            }
            keyToValue.put(key, value);
            keyToCounts.put(key, 1);
            minCount = 1;
            if (!countsToKey.containsKey(1)) countsToKey.put(1, new LinkedHashSet<Integer>());
            countsToKey.get(1).add(key);
        }
    }
    
    
     public void increaseCount(int key) {
        int count = keyToCounts.get(key);
        keyToCounts.put(key, count + 1);
        if (!countsToKey.containsKey(count + 1)) countsToKey.put(count + 1, new LinkedHashSet<Integer>());
        countsToKey.get(count + 1).add(key);
        countsToKey.get(count).remove(key);
        if (minCount == count && countsToKey.get(count).size() == 0) minCount++;
    }
    
    public void removeTail() {
        int toBeRemoved = countsToKey.get(minCount).iterator().next();
        keyToValue.remove(toBeRemoved);
        keyToCounts.remove(toBeRemoved);
        countsToKey.get(minCount).remove(toBeRemoved);        
    }

    
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 * 
 * 
 * The idea is to keep three hashmaps. One to map key to value, one to map key to count, and the last one maps count to a linkedhashset of keys. Also, I keep a integer min to keep track of the minimum count. Every time we meet a set or get operation, we just update each map, and check whether we need to change min. For example, in get operation, we check whether the key we are getting has the min count and whether that key is the only one with min count. If yes, we update min by 1. Same idea goes with set operation.
 */