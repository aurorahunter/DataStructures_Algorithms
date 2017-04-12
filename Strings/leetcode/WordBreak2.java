/*
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. You may assume the dictionary does not contain duplicate words.

Return all such possible sentences.

For example, given
s = "catsanddog",
dict = ["cat", "cats", "and", "sand", "dog"].

A solution is ["cats and dog", "cat sand dog"].

UPDATE (2017/1/4):
The wordDict parameter had been changed to a list of strings (instead of a set of strings). Please reload the code definition to get the latest changes.
**/

public class WordBreak2 {
    
    
    public List<String> wordBreak(String s, List<String> wordDict) {
    return DFS(s, wordDict, new HashMap<String, LinkedList<String>>());
}       

// DFS function returns an array including all substrings derived from s.
List<String> DFS(String s, List<String> wordDict, HashMap<String, LinkedList<String>>map) {
    if (map.containsKey(s)) 
        return map.get(s);
        
    LinkedList<String>res = new LinkedList<String>();     
    if (s.length() == 0) {
        res.add("");
        return res;
    }               
    for (String word : wordDict) {
        if (s.startsWith(word)) {
            List<String>sublist = DFS(s.substring(word.length()), wordDict, map);
            for (String sub : sublist) 
                res.add(word + (sub.isEmpty() ? "" : " ") + sub);               
        }
    }       
    map.put(s, res);
    return res;
}

/*
public List<String> findAllConcatenatedWordsInADict(String[] words) {
    List<String> list = new ArrayList<String>();
    Set<String> set = new HashSet(Arrays.asList(words));

    for(String word : words) {
        set.remove(word);
        if(dfs(word, set, "")) list.add(word);
        set.add(word);
    }
    return list;
}

private boolean dfs(String word, Set<String> set, String previous) {
    if(!previous.equals("")) set.add(previous);
    if(set.contains(word)) return true;
    
    for(int i = 1; i < word.length(); i++) {
        String prefix = word.substring(0,i);
        if(set.contains(prefix) && 
            dfs(word.substring(i,word.length()), set, previous+prefix)) {
            return true;
        }
    }
    return false;
}

//http://www.programcreek.com/2014/03/leetcode-word-break-ii-java/ 
**/
}
