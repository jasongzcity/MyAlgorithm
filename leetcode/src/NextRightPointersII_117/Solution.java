package NextRightPointersII_117;

import TreeLinkedNode.TreeLinkNode;

/**
 * Follow up for problem "Populating Next Right Pointers in Each Node".
 * What if the given tree could be any binary tree? Would your previous solution
 * still work?
 *
 * Note:
 * You may only use constant extra space.
 */
public class Solution {
    // Notice: its just like null116 but we still need small
    // modifications
    // unaccepted solution
//    public static void connect(TreeLinkNode root) {
//        if(root==null||root.right==root.left) return;
//        TreeLinkNode n = root.left;
//        if(n!=null){
//            if(root.right!=null){
//                n.next = root.right;
//                n = n.next;
//            }
//        }else{
//            n = root.right;
//        }
//        TreeLinkNode next = root.next;
////        while(next!=null&&next.left==next.right) next = next.next;
//        if(next!=null){
//            if(next.left==next.right) next.left = n;
//            else if(next.left!=null) n.next = next.left;
//            else n.next = next.right;
//        }
//        connect(root.left);
//        connect(root.right);
//    }

    public static void main(String[] args) {
//        Integer[] a1 = new Integer[]{3,7,2,7,-4,1,null,-6,6,null,null,null,null
//                ,7,null,-5,6,null,-3,null,0,-7,null,8,null,-9
//                ,null,2,null,null,null,null,-8};
        Integer[] a2 = new Integer[]{1,2,3,4,null,null,5,6,null,null,7,null,8};
        TreeLinkNode t = new TreeLinkNode(a2);
        connect(t);
//        Integer[] a2 = new Integer[]{0,5,-1,3,2,null,11,1,null,null,null,null,13};
//        t = new TreeLinkNode(a2);
//        connect(t);
    }

    public static void connect(TreeLinkNode root){
        TreeLinkNode parent = root,head = null,prev = null;
        while(parent!=null){
            while(parent!=null){
                if(parent.left!=parent.right){ // not both null
                    if(prev!=null) prev.next = parent.left!= null?parent.left:parent.right;
                    else head = parent.left!=null?parent.left:parent.right;
                    if(parent.left!=null&&parent.right!=null){
                        parent.left.next = parent.right;
                        prev = parent.right;
                    }else if(parent.left!=null){
                        prev = parent.left;
                    }else{
                        prev = parent.right;
                    }
                }
                parent = parent.next;
            }
            // prepare for next level
            parent = head;
            prev = null;
            head = null;
        }
    }

    // simplified the solution above
    // it should be faster because the solution below has
    // less if-expressions(4) in each iteration.
    public static void connect2(TreeLinkNode root){
        TreeLinkNode parent = root,head = null,prev = null;
        while(parent!=null){
            while(parent!=null){
                if(parent.left!=null){
                    if(prev!=null) prev.next = parent.left;
                    else head = parent.left;
                    prev = parent.left;
                }
                if(parent.right!=null){
                    if(prev!=null) prev.next = parent.right;
                    else head = parent.right;
                    prev = parent.right;
                }
                parent = parent.next;
            }
            // prepare for next level
            parent = head;
            prev = null;
            head = null;
        }
    }
}
