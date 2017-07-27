package KillProcess_582;

import java.util.*;

/**
 * Given n processes, each process has a unique PID (process id)
 * and its PPID (parent process id).
 *
 * Each process only has one parent process,
 * but may have one or more children processes.
 * This is just like a tree structure.
 * Only one process has PPID that is 0,
 * which means this process has no parent process.
 * All the PIDs will be distinct positive integers.
 *
 * We use two list of integers to represent a list of processes,
 * where the first list contains PID for each process and
 * the second list contains the corresponding PPID.
 *
 * Now given the two lists, and a PID representing a process you want to kill,
 * return a list of PIDs of processes that will be killed in the end.
 * You should assume that when a process is killed,
 * all its children processes will be killed. No order is required for the final answer.
 *
 * Example 1:
 * Input:
 * pid =  [1, 3, 10, 5]
 * ppid = [3, 0, 5, 3]
 * kill = 5
 * Output: [5,10]
 *
 * Explanation:
 *       3
 *     /   \
 *    1     5
 *         /
 *        10
 * Kill 5 will also kill 10.
 * Note:
 * The given kill id is guaranteed to be one of the given PIDs.
 * n >= 1.
 */
public class Solution {
    public List<Integer> killProcess2(List<Integer> pid, List<Integer> ppid, int kill) {
        // build tree first
        Map<Integer,ProcessTreeNode> map = new HashMap<>(pid.size()<<1);
        for(int i=0;i<pid.size();i++){
            int processid = pid.get(i),processparent = ppid.get(i);
            ProcessTreeNode p = map.get(processid),parent = map.get(processparent);
            if(p==null){
                p = new ProcessTreeNode(processid);
                map.put(processid,p);
            }
            if(parent==null){
                parent = new ProcessTreeNode(processparent);
                map.put(processparent,parent);
            }
            parent.children.add(p);
        }
        List<Integer> rs = new ArrayList<>(pid.size());
        helper(map.get(kill),rs);
        return rs;
    }

    private void helper(ProcessTreeNode node,List<Integer> rs){
        for(ProcessTreeNode n:node.children) helper(n,rs);
        rs.add(node.pid);
    }

    class ProcessTreeNode{
        int pid;
        List<ProcessTreeNode> children = new LinkedList<>();
        ProcessTreeNode(int pid){ this.pid = pid; }
    }

    // we don't need to build tree actually..
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill){
        Map<Integer,List<Integer>> map = new HashMap<>(pid.size()<<1);
        for(int i=0;i<pid.size();i++){
            int processid = pid.get(i), processparent = ppid.get(i);
            List<Integer> parent = map.get(processparent);
            if(parent==null) {
                parent = new LinkedList<>();
                map.put(processparent,parent);
            }
            parent.add(processid);
        }
        // use BFS to add up processes.
        List<Integer> rs = new ArrayList<>(pid.size());
        rs.add(kill);
        int begin = 0,end = 0;
        while(begin<=end){
            for(int i=begin;i<=end;i++){
                List<Integer> child = map.get(rs.get(i));
                if(child!=null)
                    for(int p:child)
                        rs.add(p);
            }
            begin = end+1;
            end = rs.size()-1;
        }
        return rs;
    }
}
