package LoggerRateLimiter_359;

import java.util.*;

/**
 * Design a logger system that receive stream of messages along with its timestamps,
 * each message should be printed if and only
 * if it is not printed in the last 10 seconds.
 *
 * Given a message and a timestamp (in seconds granularity),
 * return true if the message should be printed in the given timestamp,
 * otherwise returns false.
 *
 * It is possible that several messages arrive roughly at the same time.
 *
 * Example:
 *
 * Logger logger = new Logger();
 *
 * // logging string "foo" at timestamp 1
 * logger.shouldPrintMessage(1, "foo"); returns true;
 *
 * // logging string "bar" at timestamp 2
 * logger.shouldPrintMessage(2,"bar"); returns true;
 *
 * // logging string "foo" at timestamp 3
 * logger.shouldPrintMessage(3,"foo"); returns false;
 *
 * // logging string "bar" at timestamp 8
 * logger.shouldPrintMessage(8,"bar"); returns false;
 *
 * // logging string "foo" at timestamp 10
 * logger.shouldPrintMessage(10,"foo"); returns false;
 *
 * // logging string "foo" at timestamp 11
 * logger.shouldPrintMessage(11,"foo"); returns true;
 */
public class Logger {

//    private Set<String> set = new HashSet<>();
//    private Queue<Integer> time = new LinkedList<>();
//    private Queue<String> qs = new LinkedList<>();

    /** Initialize your data structure here. */
    public Logger(){}

    /**
     * Returns true if the message should be printed in the given timestamp,
     * otherwise returns false.
     * If this method returns false, the message will not be printed.
     * The timestamp is in seconds granularity.
     * */
//    public boolean shouldPrintMessage2(int timestamp, String message) {
//        while(time.size()!=0&&time.peek()<=timestamp-10){
//            time.poll();
//            set.remove(qs.poll());
//        }
//        if(time.size()==0||!set.contains(message)){
//            time.add(timestamp);
//            qs.add(message);
//            set.add(message);
//            return true;
//        }else return false;
//    }

    private Map<String,Integer> map = new HashMap<>(32);

    // Solution above is too complicated...
    public boolean shouldPrintMessage(int timestamp, String message){
        Integer time = map.get(message);
        if(time!=null&&time>timestamp-10) return false;
        else{
            map.put(message,timestamp);
            return true;
        }
    }



    public static void main(String[] args) {
        Logger l = new Logger();
        System.out.println(l.shouldPrintMessage(1,"foo"));
        System.out.println(l.shouldPrintMessage(2,"bar"));
        System.out.println(l.shouldPrintMessage(3,"foo"));
        System.out.println(l.shouldPrintMessage(8,"bar"));
        System.out.println(l.shouldPrintMessage(10,"foo"));
        System.out.println(l.shouldPrintMessage(11,"foo"));
    }
}
