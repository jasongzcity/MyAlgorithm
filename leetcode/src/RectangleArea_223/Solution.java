package RectangleArea_223;

/**
 * Find the total area covered
 * by two rectilinear rectangles in a 2D plane.
 *
 * Each rectangle is defined by its bottom left corner and
 * top right corner as shown in the figure.
 * Assume that the total area is never beyond the maximum possible value of int.
 */
public class Solution {
    // such brilliant way!!!!
    // notice that there are so many corner cases in this problem and
    // if you want to write code to cover them all will be very tough..
    // but this solution handle them trickly.
    // If you want to test this code on the white board, use both overlap
    // and unoverlap example.
    // key point is to calculate overlap area.
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int left = Math.max(A,E), right = Math.max(Math.min(C,G), left);
        int bottom = Math.max(B,F), top = Math.max(Math.min(D,H), bottom);
        return (C-A)*(D-B) - (right-left)*(top-bottom) + (G-E)*(H-F);
    }
}
