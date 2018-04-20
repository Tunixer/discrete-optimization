package queue;
import java.io.*;
import java.math.*;
import java.util.Queue;
import java.util.LinkedList;

public class MaxLenQueue<T> extends LinkedList<T> implements Queue<T>{
	private int MaxLen;
	private int len;
	public MaxLenQueue(int size){
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
	public void Print(){
		for(T each:this){
			System.out.print(each+" ");
		}
		System.out.println("");
	}
}