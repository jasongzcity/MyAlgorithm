package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/6.
 */
public class UndirectedGraphNode {
    public int label;
    public List<UndirectedGraphNode> neighbors;
    public UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<UndirectedGraphNode>();
    }

    public UndirectedGraphNode(List<Integer[]> list){
        Map<Integer,UndirectedGraphNode> map = new HashMap<>();
        for(int j=0;j<list.size();j++) {
            Integer[] a = list.get(j);
            UndirectedGraphNode head;
            if(j==0) {
                head = this;
                head.label = a[0];
                head.neighbors = new ArrayList<>();
            }
            else head = map.getOrDefault(a[0],new UndirectedGraphNode(a[0]));
            map.putIfAbsent(a[0],head);
            for (int i = 1; i < a.length; i++) {
                UndirectedGraphNode node = map.getOrDefault(a[i], new UndirectedGraphNode(a[i]));
                head.neighbors.add(node);
                map.putIfAbsent(a[i],node);
            }
        }
    }
};
