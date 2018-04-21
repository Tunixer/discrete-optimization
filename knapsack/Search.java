import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.math.*;
import java.util.Collections;
import java.util.Comparator;

public class Search{
	ArrayList<Node> arr = new ArrayList<Node>();
	int lens;
	int[] SumValLast;//SumLast[i] sum from i to the last;
	int[] SumWeightLast;
	int optimaVal;
	int optimaWeight;//MaxWeight;
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
	
	public Search(int[] w,int[] v,int[] taken,int items,int optVal,int optW){
		for(int i = 0; i < items; i++){
			if(taken[i] == 1){
				Node temp = new Node(w[i],v[i],i);
				//System.out.println(temp.weight);
				this.arr.add(temp);
			}
		}
		this.lens = items;
		this.optimaVal = optVal;
		this.optimaWeight = optW;
	}

	public void sumValArr(int[] s,ArrayList<Node> _arr){
		int sum = 0;
		for(int i = _arr.size()-1; i >= 0; i--){
			sum += _arr.get(i).value;
			s[i] = sum;
		}
	}
	public void sumWeightArr(int[] s,ArrayList<Node> _arr){
		int sum = 0;
		for(int i = _arr.size()-1; i >= 0; i--){
			sum += _arr.get(i).weight;
			s[i] = sum;
		}
	}
	//search whether to choose idx
	//prefer choose items[idx] first so as to take advantage of bound
	public int DFS(int sumweight,int sumvalue, int idx,Stack<Integer> ta){
		//System.out.println("Sum Weight: "+sumweight);
		//System.out.println("Sum Value: "+sumvalue);
		//System.out.println("Index: "+idx);
		//System.out.println("SumWeight: "+sumweight+" , SumValue: "+sumvalue);
		//this if statement must be put first so as to detect the target optima
		if((sumweight > this.optimaWeight) || (sumvalue > this.optimaVal) || (idx>arr.size()-1) ){
			//System.out.println("Cut at "+idx);
			return 0;
		} 
		if(sumvalue == this.optimaVal) return 1;
		if(sumvalue+SumValLast[idx] < this.optimaVal || sumweight+SumWeightLast[idx] < this.optimaWeight){
			//System.out.println("Cut at "+idx);
			return 0;
		} 
		
		int temp;
		ta.push(idx);
		//System.out.println("Push "+idx);
		sumweight += arr.get(idx).weight;
		sumvalue  += arr.get(idx).value;

		temp = DFS(sumweight,sumvalue,idx+1,ta);
		if(temp == 1){
			//System.out.println("Choose: "+idx+"!!!");
			return 1;
		} 
		ta.pop();
		//System.out.println("Pop "+idx);
		sumweight -= arr.get(idx).weight;
		sumvalue  -= arr.get(idx).value;
	
		temp = DFS(sumweight,sumvalue,idx+1,ta);
		if(temp == 1) return 1;
		return 0;
	}

	public void PrintArray(int[] _arr){
		for(int i = 0; i < _arr.length; i++){
			System.out.print(_arr[i]+" ");
		}
		System.out.println("");
	}
	public void PrintArray(ArrayList<Node> _arr){
		for(int i = 0; i < _arr.size(); i++){
			System.out.println("Weight: "+_arr.get(i).weight + " , Value: "+_arr.get(i).value +" , Index: "+_arr.get(i).idx);
		}
		System.out.println("");
	}
	public void work(){
		SumValLast = new int[this.lens];
		SumWeightLast = new int[this.lens];
		Collections.sort(arr,new SortByDiv());
		//PrintArray(arr);

		sumValArr(SumValLast,arr);
		sumWeightArr(SumWeightLast,arr);
		//PrintArray(SumValLast);
		//PrintArray(SumWeightLast);
		System.out.println("Optima Value: " + this.optimaVal);
		System.out.println("Optima Weight: "+ this.optimaWeight);
		ans = new Stack<Integer>();

		
		DFS(0,0,0,ans);
	}
}