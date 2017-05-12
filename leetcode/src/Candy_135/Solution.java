package Candy_135;

/**
 * There are N children standing in a line.
 * Each child is assigned a rating value.
 *
 * You are giving candies to these children subjected to the following requirements:
 *
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 *
 * What is the minimum candies you must give?
 */
public class Solution {
    public static int candy(int[] ratings) {
        if(ratings.length==0) return 0;
        int minSum = 1,current = 1;
        for(int i=1;i<ratings.length;i++){
            if(ratings[i]>ratings[i-1]){
                minSum+=++current;
            }else if(ratings[i]==ratings[i-1]){
                current = 1;
                minSum+=current;
            }else{
                int count = 0;
                while(i<ratings.length&&ratings[i]<ratings[i-1]){
                    count++;
                    i++;
                }
                for(int j=count;j>0;j--) minSum+=j;
                if(current<=count) minSum+=count+1-current;
                i--;
                current = 1;
            }
        }
        return minSum;
    }

    public static void main(String[] args) {
        int[] can = new int[]{5,4,3,2,2,2,0,-1,-2,-5,-8,9};
        System.out.println(candy(can));
    }

    // A more straight forward 3-pass solution
    public static int candy2(int[] r){
        int[] candies = new int[r.length];
        for(int i=1;i<r.length;i++)
            if(r[i]>r[i-1])
                candies[i] = candies[i-1]+1;
        for(int i=r.length-1;i>0;i--)
            if(r[i-1]>r[i]&&candies[i-1]<1+candies[i])
                candies[i-1] = 1+candies[i];
        int sum = r.length;
        for(int i:candies) sum+=i;
        return sum;
    }
}
