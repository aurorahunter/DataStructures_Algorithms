public class BinaryMinHeap {
      private int[] data;
      private int heapSize;
 
      public BinaryMinHeap(int size) {
            data = new int[size];
            heapSize = 0;
      }
 
      public int getMinimum() {
            if (isEmpty())
                  throw new HeapException("Heap is empty");
            else
                  return data[0];
      }
 
      public boolean isEmpty() {
            return (heapSize == 0);
      }
 
 
      private int getLeftChildIndex(int nodeIndex) {
            return 2 * nodeIndex + 1;
      }
 
      private int getRightChildIndex(int nodeIndex) {
            return 2 * nodeIndex + 2;
      }
 
      private int getParentIndex(int nodeIndex) {
            return (nodeIndex - 1) / 2;
      }
 
      public class HeapException extends RuntimeException {
            public HeapException(String message) {
                  super(message);
            }
      }
      
      // Inserting into min heaps
      /*
      Insertion algorithm

Now, let us phrase general algorithm to insert a new element into a heap.

Add a new element to the end of an array;
Sift up the new element, while heap property is broken. 
Sifting is done as following: 
compare node's value with parent's value. If they are in wrong order, swap them.
      
      Complexity analysis

Complexity of the insertion operation is O(h), where h is heap's height. 
Taking into account completeness of the tree, O(h) = O(log n), where n is number of elements in a heap.
      **/
      
      
      public void insert(int value) {
            if (heapSize == data.length)
                  throw new HeapException("Heap's underlying storage is overflow");
            else {
                  heapSize++;
                  data[heapSize - 1] = value;
                  siftUp(heapSize - 1);
            }
      }    
 
     
private void siftUp(int nodeIndex) {
            int parentIndex, tmp;
            if (nodeIndex != 0) {
                  parentIndex = getParentIndex(nodeIndex);
                  if (data[parentIndex] > data[nodeIndex]) {
                        tmp = data[parentIndex];
                        data[parentIndex] = data[nodeIndex];
                        data[nodeIndex] = tmp;
                        siftUp(parentIndex);
                  }
            }
      }
      
     /*
     
     Removing the minimum from a heap

Removal operation uses the same idea as was used for insertion. 
Root's value, which is minimal by the heap property, is replaced by the last array's value. 
Then new value is sifted down, until it takes right position.

Removal algorithm

Copy the last value in the array to the root;
Decrease heap's size by 1;
Sift down root's value. Sifting is done as following:
if current node has no children, sifting is over;
if current node has one child: check, if heap property is broken, 
then swap current node's value and child value; sift down the child;
if current node has two children: find the smallest of them. 
If heap property is broken, then swap current node's value and selected child value; sift down the child.  

Complexity analysis

Complexity of the removal operation is O(h) = O(log n), where h is heap's height, n is number of elements in a heap. 
     **/ 
      
     public void removeMin() {
            if (isEmpty())
                  throw new HeapException("Heap is empty");
            else {
                  data[0] = data[heapSize - 1];
                  heapSize--;
                  if (heapSize > 0)
                        siftDown(0);
            }
      }
 
 
      private void siftDown(int nodeIndex) {
            int leftChildIndex, rightChildIndex, minIndex, tmp;
            leftChildIndex = getLeftChildIndex(nodeIndex);
            rightChildIndex = getRightChildIndex(nodeIndex);
            if (rightChildIndex >= heapSize) {
                  if (leftChildIndex >= heapSize)
                        return;
                  else
                        minIndex = leftChildIndex;
            } else {
                  if (data[leftChildIndex] <= data[rightChildIndex])
                        minIndex = leftChildIndex;
                  else
                        minIndex = rightChildIndex;
            }
            if (data[nodeIndex] > data[minIndex]) {
                  tmp = data[minIndex];
                  data[minIndex] = data[nodeIndex];
                  data[nodeIndex] = tmp;
                  siftDown(minIndex);
            }
      }
 
}
