package VerifyPreorderSequenceInBST_255;

/**
 * Given an array of numbers,
 * verify whether it is the correct preorder traversal sequence of a binary search tree.
 *
 * You may assume each number in the sequence is unique.
 *
 * Follow up:
 * Could you do it using only constant space complexity?
 */
public class Solution {
    // the intuitive way is to solve the question with a stack
    // O(n)
    // but we could use the array itself as a stack
    public boolean verifyPreorder(int[] preorder) {
        if(preorder.length<2) return true;
        int top = 0,p = 1,lowerBound = Integer.MIN_VALUE;
        for(;p<preorder.length;p++){
            if(preorder[p]<lowerBound) return false;
            if(preorder[top]<preorder[p]){
                while(top>-1&&preorder[top]<preorder[p]) --top; // "poping"
                lowerBound = preorder[top+1];
            }
            preorder[++top] = preorder[p];
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.verifyPreorder(new int[]{3,2,1}));
    }
}
