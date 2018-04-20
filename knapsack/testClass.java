
//import queue.MaxLenQueue;
import java.io.*;
public class testClass{
	public static void main(String[] args) {
		MaxLenQueue<Integer> queue = new MaxLenQueue<Integer>(5);
		queue.Enqueue(3);
		queue.Enqueue(5);
		queue.Enqueue(7);
		queue.Print();
		queue.Enqueue(10);
		queue.Enqueue(12);
		queue.Print();
		queue.Enqueue(66);
		queue.Print();
    }

}