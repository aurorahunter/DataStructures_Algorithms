/*
Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:
Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].

Example 2:
Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].

This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].

**/

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
    if(intervals.size() == 0) {
        intervals.add(newInterval);
        return intervals;
    }
    
    int first=0, piv=0, count=intervals.size(), step = 0;
    while(count > 0) {  // lower bound of ends
        step = count/2; 
        piv = first + step;
        if(intervals.get(piv).end < newInterval.start) {
            first = ++piv;
            count -= step + 1;
        } 
        else 
            count = step;
    }
    
    int last = first;
    count = intervals.size() - first;
    while(count > 0) {  // upper bound of starts
        step = count/2; piv = last + step;
        if(intervals.get(piv).start <= newInterval.end) {
            last = ++piv;
            count -= step + 1;
        } else count = step;
    }
    
    if(last == first)
        intervals.add(first, newInterval);
    else {
        intervals.get(first).start = Math.min(newInterval.start, intervals.get(first).start);
        intervals.get(first).end   = Math.max(newInterval.end,   intervals.get(last-1).end);
        // intervals.remove((first+1),last);  
        intervals.subList(first+1, last).clear();
    }
    
    return intervals;
    }
}
