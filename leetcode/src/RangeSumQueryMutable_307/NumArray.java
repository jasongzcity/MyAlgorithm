package RangeSumQueryMutable_307;

/**
 * Given an integer array nums, find the sum of the elements between indices i and j
 * (i ≤ j), inclusive.
 *
 * The update(i, val) function modifies nums by updating the element at index i to val.
 *
 * Example:
 * Given nums = [1, 3, 5]
 *
 * sumRange(0, 2) -> 9
 * update(1, 2)
 * sumRange(0, 2) -> 8
 *
 * Note:
 * The array is only modifiable by the update function.
 * You may assume the number of calls to update and sumRange function is
 * distributed evenly.
 */
public class NumArray {

    // solution using segment tree
    class SegmentTreeNode{
        int begin;
        int end;
        SegmentTreeNode left;
        SegmentTreeNode right;
        int sum; // sum of the subtree
        SegmentTreeNode(int begin,int end){ this.begin = begin; this.end = end; }
    }

    private SegmentTreeNode root;

    private SegmentTreeNode buildTree(int[] a,int begin,int end){
        // assure begin<=end
        SegmentTreeNode n = new SegmentTreeNode(begin,end);
        if(begin==end){
            n.sum = a[begin];
        }else{
            int mid = (begin+end)>>1;
            n.left = buildTree(a,begin,mid);
            n.right = buildTree(a,mid+1,end);
            n.sum = n.left.sum+n.right.sum;
        }
        return n;
    }

    private void update(int index,int val,SegmentTreeNode root){
        if(root.left==root.right){
            // leaf node
            root.sum = val;
            return;
        }
        int mid = (root.begin+root.end)>>1;
        if(index<=mid) update(index,val,root.left);
        else update(index,val,root.right);
        root.sum = root.left.sum+root.right.sum;
    }

    private int sumRange(int begin,int end,SegmentTreeNode root){
        if(begin==root.begin&&end==root.end) return root.sum;
        int mid = (root.begin+root.end)>>1;
        if(begin<=mid&&end<=mid) return sumRange(begin,end,root.left);
        else if(begin>mid&&end>mid) return sumRange(begin,end,root.right);
        else return sumRange(begin,mid,root.left)+sumRange(mid+1,end,root.right);
    }

//    public NumArray(int[] nums) {
//        if(nums.length==0) return;
//        root = buildTree(nums,0,nums.length-1);
//    }

    public void update2(int i, int val) {
        update(i,val,root);
    }

    public int sumRange2(int i, int j) {
        return sumRange(i,j,root);
    }

    // Binary Index Tree solution
    // http://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
    private int lowestbit(int i){ return i&-i; }

    private int[] a;
    private int[] BIT;

    public NumArray(int[] nums) {
        this.a = nums;
        BIT = new int[nums.length+1];
        for(int i=0;i<nums.length;i++) updateBIT(i,nums[i]);
    }

    public void update(int i, int val) {
        int diff = val-a[i];
        a[i] = val;
        updateBIT(i,diff);
    }

    public int sumRange(int i, int j) {
        return sum(j)-sum(i-1);
    }

    private void updateBIT(int index,int diff){
        int ind = index+1;
        while(ind<BIT.length){
            BIT[ind]+=diff;
            ind+=lowestbit(ind);
        }
    }

    private int sum(int index){
        int i = index+1,sum = 0;
        while(i>0){
            sum+=BIT[i];
            i-=lowestbit(i);
        }
        return sum;
    }
}
