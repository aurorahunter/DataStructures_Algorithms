/*

Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for every word following rules below.

Begin with the first character and then the number of characters abbreviated, which followed by the last character.
If there are any conflict, that is more than one words share the same abbreviation, a longer prefix is used instead of only the first character until making the map from word to abbreviation become unique. In other words, a final abbreviation cannot map to more than one original words.
If the abbreviation doesn't make the word shorter, then keep it as original.
Example:
Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
**/

public class WordAbbreviation {
    class Trie {
        int count = 1;
        Trie[] nexts = new Trie[26];
        void add(char[] s, int i) {
            if (i < s.length) {
                int idx = s[i] -'a';
                if (nexts[idx] != null) nexts[idx].count++; // repeated prefix
                else nexts[idx] = new Trie();
                nexts[idx].add(s, i+1);
            }
        }
    }
    
    String abvWord(String word, Trie root){
        char[] cc = word.toCharArray();
        int idx = cc.length;
        for (int i=0; i<cc.length; i++) {
            root = root.nexts[cc[i]-'a'];
            if (root.count == 1 ) { // first non-repeated prefix
                idx=i;
                break;
            }
        }
        return cc.length-2-idx > 1? word.substring(0,idx+1)+ (cc.length-2-idx) +cc[cc.length-1] : word;
    }
    
    int getKey(String s) {
        return s.length()*10000 + (s.charAt(0)-'a')*100 + (s.charAt(s.length()-1)-'a');
    }
    
    public List<String> wordsAbbreviation(List<String> dict) {
        List<String> ret = new ArrayList<String>();
        if (dict == null || dict.size()==0) return ret;
        Map<Integer, Trie> trees = new HashMap<>(); // store the Trie root of each group
        for (String word: dict) { // build the trie by groups
            int key = getKey(word);
            if ( word.length() > 3 ) {
                trees.putIfAbsent(key, new Trie());
                trees.get(key).add(word.toCharArray(), 0);
            }
        }
        for (String word: dict) // find each abbreviation based on group 
            if ( word.length() > 3 ) ret.add(abvWord(word, trees.get(getKey(word))));
            else ret.add(word);
        return ret;
    }

    
    
    /*
    complexity: Java Trie Trees solution only one pass to build the trees and one pass to get abbreviations
The main idea is to build a multiple Trie trees grouped by (first char)+(length)+(last char).
Or, a "bucket" for group of words. For examlpe, "apple" and "adime" will in the same bucket of "a5e".
Instead of using "a5e" as the key for the bucket ( a HashMap ), I use integer of 5*10000+ ('a'-'a')*100+('e'-'a') as the key.
Then, we build a Trie tree for each bucket of words. While building the tree, we count how many time a trie node was passed repeatedly, which also indicates duplicated prefix in a group.
As a result, when calling String abvWord(String word, Trie root), we find the index of the first non-repeated prefix (Trie's count ==1) and start calculate the number of letters can be abbreviated.
    **/
}
    
