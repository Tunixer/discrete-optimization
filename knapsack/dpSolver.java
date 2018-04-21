import queue.MaxLenQueue;
import java.util.ArrayList;
import java.math.*;
import java.lang.Math;
class dpSolver{
	int item;
	public int nextCapacity;
	int capacity;
	long BuffLen;
	int[][] dp;
    int[][] dpw;
	MaxLenQueue<Pairs> trace;
	class Pairs{
		int item;
		int sumWeight;
		public Pairs(int i, int j){
			this.item = i;
			this.sumWeight = j;
		}
	}

	public dpSolver(int _item, int _cap){
		//System.out.println("Items Num: "+ _item+", Capacity: "+ _cap);
		this.item = _item;
		this.capacity = _cap;
		dp = new int[1][_cap+1];
        dpw = new int[1][_cap+1];
		//this.BuffLen = Math.min(10000000,5*_cap);
		this.BuffLen = 10000000;
		//System.out.println("Queue Len:"+this.BuffLen);
		trace = new MaxLenQueue<Pairs>(this.BuffLen);
		this.nextCapacity = -1;
	}

	public ArrayList<Integer> solve(int[] weights, int[] values){
        for(int i = 0; i <= this.capacity; i++){
            if(i< weights[0]){
                dp[0][i] = 0;
            }
            else{
                dp[0][i] = values[0];
                dpw[0][i] = weights[0];
            }
        }

        for(int i = 1; i < this.item; i++){
            for(int j = this.capacity; j >= 0; j--){
                int val = j - weights[i];
                if((val >= 0)&&((dp[0][val]+values[i])>=dp[0][j])){
                    dp[0][j] = dp[0][val]+values[i];
                    dpw[0][j] = dpw[0][val]+weights[i];
                    //System.out.println("DP("+i+","+j+") = "+dp[0][j]);
                    Pairs temp = new Pairs(i,dpw[0][j]);
                    trace.Enqueue(temp);
                }
            }
        }
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int optima = dpw[0][this.capacity];
        //int CountWeight = this.capacity;
        Pairs temp = trace.TailDequeue();
        
        while(temp.sumWeight!=optima){
        	temp = trace.TailDequeue();
/*
maybe there is no pairs s.t. temp.sumValue == optima, we will add dynamic scheme later
*/
            if(temp == null) return ans;
        }

        ans.add(temp.item);
        //CountWeight -= weights[temp.item];
        optima = temp.sumWeight - weights[temp.item];
        while(!trace.isEmpty()){
        	temp = trace.TailDequeue();
        	if(temp.sumWeight==optima&&ans.get(ans.size()-1)>temp.item){
				ans.add(temp.item);
				//CountWeight -= weights[temp.item];
				optima = temp.sumWeight - weights[temp.item];
        	}
        }
        if(optima == weights[0]){
        	//CountWeight -= weights[temp.item];
        	ans.add(0);
        	return ans;
        }
        if(optima > 0){
        	this.nextCapacity = optima;
        	return ans;
        }
        return ans;
	}
}