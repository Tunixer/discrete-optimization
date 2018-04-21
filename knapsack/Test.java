import queue.*;
import java.util.*;
import java.math.*;
import java.io.*;

public class Test {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        MaxLenQueue<Integer> mq = new MaxLenQueue<Integer>(5);
        mq.Enqueue(5);
        mq.Enqueue(6);
        mq.Enqueue(8);
        mq.Enqueue(9);
        mq.Print();
        mq.Enqueue(10);
        mq.Print();
        mq.Enqueue(12);
        mq.Print();
        int temp;
        temp = mq.TailDequeue().intValue();
        System.out.println("TailDequeue:"+temp);
        mq.Print();
    }
}