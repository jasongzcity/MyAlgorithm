package DesignTwitter_355;

import java.util.*;

/**
 * Design a simplified version of Twitter where users can post tweets,
 * follow/unfollow another user and
 * is able to see the 10 most recent tweets in the user's news feed.
 * Your design should support the following methods:
 *
 * postTweet(userId, tweetId): Compose a new tweet.
 *
 * getNewsFeed(userId): Retrieve the 10 most recent tweet ids
 * in the user's news feed.
 * Each item in the news feed must be posted by users who the user
 * followed or by the user herself.
 * Tweets must be ordered from most recent to least recent.
 *
 * follow(followerId, followeeId): Follower follows a followee.
 *
 * unfollow(followerId, followeeId): Follower unfollows a followee.
 *
 * Example:
 *
 * Twitter twitter = new Twitter();
 *
 * // User 1 posts a new tweet (id = 5).
 * twitter.postTweet(1, 5);
 *
 * // User 1's news feed should return a list with 1 tweet id -> [5].
 * twitter.getNewsFeed(1);
 *
 * // User 1 follows user 2.
 * twitter.follow(1, 2);
 *
 * // User 2 posts a new tweet (id = 6).
 * twitter.postTweet(2, 6);
 *
 * // User 1's news feed should return a list with 2 tweet ids -> [6, 5].
 * // Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
 * twitter.getNewsFeed(1);
 *
 * // User 1 unfollows user 2.
 * twitter.unfollow(1, 2);
 *
 * // User 1's news feed should return a list with 1 tweet id -> [5],
 * // since user 1 is no longer following user 2.
 * twitter.getNewsFeed(1);
 */
public class Twitter {

    private int twitTime = 0;
    private Map<Integer,User> users = new HashMap<>(128);
    private PriorityQueue<Twit> pq = new PriorityQueue<>(36,
            (t1,t2) -> -(t1.time-t2.time));

    /** Initialize your data structure here. */
    public Twitter() {}

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User u = users.getOrDefault(userId,new User(userId));
        Twit t = new Twit(tweetId,twitTime++,userId);
        // Add the twit to the head of the list.
        t.next = u.twits;
        u.twits = t;
        users.put(userId,u);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who
     * the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed2(int userId) {
        List<Integer> rs = new ArrayList<>(30);
        // must assure userId valid!
        User u = users.get(userId);
        if(u==null) return rs;
        Twit[] heads = new Twit[u.following.size()];
        int i=0,count=0,currentTime = Integer.MAX_VALUE;
        for(User f:u.following) heads[i++] = f.twits;
        // notice: this checking procedure can be very slow.
        // imagine everytime the user refresh the page,
        // and the server go check all his followees' latest
        // twits. And, to get 10 latest twits, server have to check
        // 10 times...
        // So maybe maintaining a "feeds" list at the user class
        // is a better solution
        while(count<10){
            int index = 0;
            for(int j=1;j<heads.length;j++){
                if(heads[j]!=null){
                    if(heads[j].time==currentTime-1){
                        // optimization, this must be the latest twit.
                        index = j;
                        break;
                    }
                    if(heads[index]==null||heads[j].time>heads[index].time){
                        index = j;
                    }
                }
            }
            if(heads[index]==null) break; // all null
            rs.add(heads[index].id);
            currentTime = heads[index].time;
            heads[index] = heads[index].next;
            ++count;
        }
        return rs;
    }

    // optimized solution based on the leetcode most voted
    // its most idea are the same with me, but use a
    // priority queue to compare tweets
    public List<Integer> getNewsFeed(int userId){
        List<Integer> rs = new ArrayList<>(30);
        // must assure userId valid!
        User u = users.get(userId);
        if(u==null) return rs;

        for(User followee:u.following)
            if(followee.twits!=null)
                pq.add(followee.twits);

        int count = 0;
        while(count<10&&pq.size()>0){
            Twit t = pq.poll();
            rs.add(t.id);
            if(t.next!=null) pq.add(t.next);
            ++count;
        }
        pq.clear();
        return rs;
    }

    /** Follower follows a followee.
     * If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        User follower = users.getOrDefault(followerId,new User(followerId));
        User followee = users.getOrDefault(followeeId,new User(followeeId));
        follower.following.add(followee);
        users.put(followerId,follower);
        users.put(followeeId,followee);
    }

    /** Follower unfollows a followee.
     * If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        User follower = users.get(followerId),followee = users.get(followeeId);
        if(follower==null||followee==null||followee==follower) return; // can't unfollow yourself
        follower.following.remove(followee);
    }

    class User{
        int id;
        Set<User> following;
        Twit twits;
        User(int id){
            this.id = id;
            following = new HashSet<>();
            following.add(this);
        }
    }

    class Twit{
        int id;
        int time;
        int userId;
        Twit next;
        Twit(int id,int time,int userId){
            this.id = id;
            this.time = time;
            this.userId = userId;
        }
    }
}
