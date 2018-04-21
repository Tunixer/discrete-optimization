package queue;
import java.util.*;
import java.io.*;
import java.util.LinkedList;

public class MaxLenQueue<T> extends LinkedList<T> {
	private long MaxLen;
	public long len;
	public MaxLenQueue(long size){
		super();
		this.MaxLen = size;
		this.len = 0;
	}
	public void Enqueue(T element){
		if(len == MaxLen){
			super.poll();
			len--;
		}
		super.offer(element);
		len++;
	}
	public T Dequeue(){
		len--;
		return super.poll();
	}
	public T TailDequeue(){
		 len--;
		 return super.pollLast();
	}
	public void Print(){
		for(T each:this){
			System.out.print(each+" ");
		}
		System.out.println("");
	}
}