import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.math.*;
import java.util.Collections;
import java.util.Comparator;

public class Search{
	ArrayList<Node> arr = new ArrayList<Node>();
	int lens;
	int[] SumLast;//SumLast[i] sum from i to the last;
	int optima;
	int capac;//capacity;
	public Stack<Integer> ans;
	class Node{
		public int weight;
		public int value;
		public int idx;
		public Node(int w,int v,int idx){
			this.weight = w;
			this.value = v;
			this.idx = idx;
		}
	}

	public class SortByDiv implements Comparator{
		public int compare(Object o1, Object o2){
			Node n1 = (Node)o1;
			Node n2 = (Node)o2;
			float temp1 = n1.value/n1.weight;
			float temp2 = n2.value/n2.weight;
			return (temp1>temp2)? 1:0;
		}
	}
	
	public Search(int[] w,int[] v,int[] taken,int items,int opt,int capacity){
		for(int i = 0; i < items; i++){
			if(taken[i] == 1){
				Node temp = new Node(w[i],v[i],i);
				//System.out.println(temp.weight);
				this.arr.add(temp);
			}
		}
		this.lens = items;
		this.optima = opt;
		this.capac = capacity;
	}

	public static void sumarr(int[] s,ArrayList<Node> _arr){
		int sum = 0;
		for(int i = _arr.size()-1; i >= 0; i--){
			sum += _arr.get(i).value;
			s[i] = sum;
		}
	}

	//search whether to choose idx
	public static int DFS(int opt,ArrayList<Node> _arr,int[] _sumLast,
			 int sumweight,int sumvalue, int idx,Stack<Integer> ta){
		if(idx>_arr.size()-1) return 0;
		if(sumvalue+_sumLast[idx]<opt){
			return 0;
		}
		if(sumvalue == opt){
			return 1;
		}
		//if(sumweight > capacity) return 0;
		int temp;
		temp = DFS(opt,_arr,_sumLast,sumweight,sumvalue,idx+1,ta);
		if(temp == 1) return 1;
		ta.push(idx);
		sumweight += _arr.get(idx).weight;
		sumvalue  += _arr.get(idx).value;
		temp = DFS(opt,_arr,_sumLast,sumweight,sumvalue,idx+1,ta);
		if(temp == 1) return 1;
		ta.pop();
		sumweight += _arr.get(idx).weight;
		sumvalue  += _arr.get(idx).value;
		return 0;
	}

	public void work(){
		SumLast = new int[this.lens];
		Collections.sort(arr,new SortByDiv());
		sumarr(SumLast,arr);
		ans = new Stack<Integer>();
		DFS(this.optima,arr,SumLast,0,0,0,ans);
	}
}