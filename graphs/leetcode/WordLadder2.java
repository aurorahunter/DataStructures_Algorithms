/*
Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
    
Algorithm:
* construct a graph, BFS graph to find out all the list of nodes, its vertices.
To do so, we need a 
* "queue" for BFS, 
* visited/unvisited sets for transformation to avoid duplicates, 
* HashMap for adjacency List/matrix

Once the graph is constructed, do a DFS/backtrace to compute *ALL* possible transformation
start from "end" to "word" 

Reason for children to be a key in hashtable:
And for memory use. 
Storing all the children for a word will always get MLE because a parent can have length*26 children 
while a child can only have at most 2 or 3 parents. Parent select children first!

Time complexity for BFS: for word length k, and searching in dictionary takes O(1), and the depth of trasation is d
time complexity is O((k*26)^d), space complexity is O((k*26)^d)

[2] To find analysis of time complexity of solution[O(len*N*(26^(d)))],
    please check my stackoverflow,
    "http://stackoverflow.com/questions/24596436/whats-time-complexity-of-this-algorithm-for-getting-all-word-ladders"
**/

public class WordLadder2 {

    List<List<String>> results;
	List<String> list;
	Map<String,List<String>> map;
    public List<List<String>> findLadders(String start, String end, List<String> dict) {
            results= new ArrayList<List<String>>();
	        if (dict.size() == 0)
				return results;
	        if(!dict.contains(end)) 
	        return results ; 
	        int curr=1,next=0;	        
	        boolean found=false;	        
	        list = new LinkedList<String>();	       
			map = new HashMap<String,List<String>>();
			
			Queue<String> queue= new ArrayDeque<String>();
			Set<String> unvisited = new HashSet<String>(dict);
			Set<String> visited = new HashSet<String>();
			
			queue.add(start);			
			unvisited.add(end);
			unvisited.remove(start);
			//BFS
			while (!queue.isEmpty()) {
			   
				String word = queue.poll();
				curr--;				
				for (int i = 0; i < word.length(); i++){
				   StringBuilder builder = new StringBuilder(word); 
					for (char ch='a';  ch <= 'z'; ch++){
						builder.setCharAt(i,ch);
						String new_word=builder.toString();	
						if (unvisited.contains(new_word)){
							//Handle queue
							if (visited.add(new_word)){//Key statement,Avoid Duplicate queue insertion
								next++;
								queue.add(new_word);
							}
							
							if (map.containsKey(new_word))//Build Adjacent Graph
								map.get(new_word).add(word);
							else{
								List<String> l= new LinkedList<String>();
								l.add(word);
								map.put(new_word, l);
							}
							
							if (new_word.equals(end)&&!found) found=true;		
														
						}

					}//End:Iteration from 'a' to 'z'
				}//End:Iteration from the first to the last
				if (curr==0){
					if (found) break;
					curr=next;
					next=0;
					unvisited.removeAll(visited);
					visited.clear();
				}
			}//End While

			backTrace(end,start);
			
			return results; 
    }
    
        private void backTrace(String word,String start){
	    	if (word.equals(start)){
	    		list.add(0,start);
	    		results.add(new ArrayList<String>(list));
	    		list.remove(0);
	    		return;
	    	}
	    	list.add(0,word);
	    	if (map.get(word)!=null)
	    		for (String s:map.get(word))
	    			backTrace(s,start);
	    	list.remove(0);
	    }
}