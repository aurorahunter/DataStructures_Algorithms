/*

There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

Example 1:
Given the following words in dictionary,

[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]
The correct order is: "wertf".

Example 2:
Given the following words in dictionary,

[
  "z",
  "x"
]
The correct order is: "zx".

Example 3:
Given the following words in dictionary,

[
  "z",
  "x",
  "z"
]
The order is invalid, so return ""

**/

public class AlienDictionary {

public String alienOrder(String[] words) {
    StringBuilder result = new StringBuilder();
    int[] indegree = new int[26];
    Arrays.fill(indegree, -1);
    
    // Initialize the Adjacency List
    List<Set<Character>> adjacencyList = new ArrayList<>();
    for(int i = 0; i < 26; ++i){
        adjacencyList.add(new HashSet<>());
    }
    
    // Initialize the indegree with 0 if the letter exists.
    for(String word : words){
        for(char wordChar : word.toCharArray()){
            indegree[wordChar - 'a'] = 0;
        }
    }
    
    // Start populating the indegree and the adjacency list
    for(int i = 0; i < words.length - 1; ++i){
        String word1 = words[i];
        String word2 = words[i+1];
        int minLength = Math.min(word1.length(), word2.length());
        
        for(int j = 0; j < minLength; ++j){
            char c1 = word1.charAt(j);
            char c2 = word2.charAt(j);
            // look for the first mismatch in word1 and word2
            if(c1!= c2){
                if(!adjacencyList.get(c1 - 'a').contains(c2)){
                    adjacencyList.get(c1 - 'a').add(c2);
                    ++indegree[c2 - 'a'];
                }
                // first mismatch found we shouldn't go further as the char order after these in uncertain.
                break;
            }
        }
    }
    
    // Add to queue all the starting points(vertices with 0 indegree)
    Queue<Character> queue = new ArrayDeque<>();
    for(int i = 0; i < indegree.length; ++i){
        if(indegree[i] == 0){
            queue.offer((char)(i + 'a'));
        }
    }
    
    // If the indegree drops to 0 add to queue.
    while(!queue.isEmpty()){
        char newChar = queue.poll();
        result.append(newChar);
        
        for(char charAfter : adjacencyList.get(newChar - 'a')){
            indegree[charAfter - 'a']--;
            if(indegree[charAfter - 'a'] == 0){
                queue.offer(charAfter);
            }
        }
        
    }
    
    //check for cycles. In case of cycle, all degrees will not become 0.
    for(int degree : indegree){
        if(degree > 0){
            return "";
        }
    }
    
    return result.toString();
}

    }