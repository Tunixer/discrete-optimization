import java.io.*;
import java.util.List;
import java.util.*;
import java.util.ArrayList;
import java.math.*;
/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getpath(int[][] arr,int item,int capacity,int[] cost,int[] taken){
        int i = item-1;
        int j = capacity;
        while(i>=0){
            if(arr[i][j] == i){
                taken[i] = 1;
                j = j-cost[i];
                i--;
            }
            else{
                i = arr[i][j];
            }
        }
    }
    public static void dp_solver2(int items,int capacity,List<String> lines){
        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        // a trivial greedy algorithm for filling the knapsack
        // it takes items in-order until the knapsack is full
        int value = 0;
        int weight = 0;
        int[] taken = new int[items];

        for(int i=0; i < items; i++){
            if(weight + weights[i] <= capacity){
                taken[i] = 1;
                value += values[i];
                weight += weights[i];
            } else {
                taken[i] = 0;
            }
        }
        
        // prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");  
    }
    public static void dp_solver1(int items,int capacity,List<String> lines){
        //System.out.println("Items: "+items);
        //System.out.println("Capacity: "+capacity);
        //System.out.println("Items*Capacity: "+items*capacity);
        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        int value = 0;
        int weight = 0;
        int[] taken = new int[items];

        int[][] dp = new int[items][capacity+1];
        int[][] path = new int[items][capacity+1];
        for(int i = 0; i < items; i++){
            for(int j = 0; j <= capacity; j++){
                path[i][j] = -1;
            }
        }
        for(int i = 0; i <= capacity; i++){
            if(i < weights[0]){
                dp[0][i] = 0;
                path[0][i] = -1;
            }
            else{
                dp[0][i] = values[0];
                path[0][i] = 0;
            }
        }
        
        for(int i = 1; i < items; i++){
            for(int j = 0; j <= capacity; j++){
                if(j-weights[i] < 0){
                    dp[i][j] = dp[i-1][j];
                    path[i][j] = path[i-1][j];//path[i-1][j] or i-1?
                    continue;
                }
                int val = dp[i-1][j-weights[i]]+values[i];
                if(dp[i-1][j]>val){
                    dp[i][j] = dp[i-1][j];
                    path[i][j] = path[i-1][j];
                }
                if(dp[i-1][j]<=val){
                    dp[i][j] = val;
                    path[i][j] = i;
                }
            }
        }

        Solver temp = new Solver();
        temp.getpath(path,items,capacity,weights,taken);
        
        value = dp[items-1][capacity];
        // prepare the solution in the specified output format
        System.out.println(value+" 1");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println(""); 
    }
     
    public static void dp_solver3(int items,int capacity,int[] values,int[] weights){
        //System.out.println("Items: "+items);
        //System.out.println("Capacity: "+capacity);
        //System.out.println("Items*Capacity: "+items*capacity);

        int value = 0;
        int weight = 0;
        int[] taken = new int[items];

        int[][] dp = new int[1][capacity+1];

        for(int i = 0; i <= capacity; i++){
            if(i< weights[0]){
                dp[0][i] = 0;
            }
            else{
                dp[0][i] = values[0];
                taken[0] = 1;
            }
        }
        int count = 0;
        for(int i = 1; i < items; i++){
            for(int j = capacity; j >= 1; j--){
                int val = j - weights[i];
                if((val >= 0)&&((dp[0][val]+values[i])>=dp[0][j])){
                    count++;
                    //System.out.println("(i, j) is ("+i+","+j+")");
                    taken[i] = 1;
                    dp[0][j] = dp[0][val]+values[i];
                    //System.out.println("DP("+i+","+j+") = "+dp[0][j]);
                }
            }
        }
        
        int count2 = 0;
        for(int i =0; i<items;i++){
            if(taken[i]==1) count2++;
        }
        value = dp[0][capacity];
        // prepare the solution in the specified output format
        System.out.println(value+" 1");

        for(int i=0; i < items; i++){
            //System.out.print(taken[i]+" ");
        }
        System.out.println("");
        System.out.println("Count = "+count); 
        System.out.println("Select = "+count2); 
    }


    public static void dp_solver4(int items,int capacity,int[] values,int[] weights){
        dpSolver sov = new dpSolver(items,capacity);
        ArrayList<Integer> Selected;
        int[] taken = new int[items];
        int sum_value = 0;

        Selected = sov.solve(weights,values);
        int isFinished = sov.nextCapacity;
        while(isFinished!=-1){
            //System.out.println("NextItems: "+Selected.get(Selected.size()-1)+" , nextCapacity: "+isFinished);
            sov = new dpSolver(Selected.get(Selected.size()-1),isFinished);
            Selected.addAll(sov.solve(weights,values));
            isFinished = sov.nextCapacity;
        }
        for(Integer Idx: Selected){
            taken[Idx] = 1;
            sum_value += values[Idx];
        }
        System.out.println(sum_value+" 1");

        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println(""); 

    }

    public static void dp_search_solver1(int items,int capacity,int[] values,int[] weights){
        int value = 0;
        int weight = 0;
        int[] taken = new int[items];

        int[][] dp = new int[1][capacity+1];
        int[][] dpw = new int[1][capacity+1];

        for(int i = 0; i <= capacity; i++){
            //System.out.println("Init");
            if(i< weights[0]){
                dp[0][i] = 0;
            }
            else{
                dp[0][i] = values[0];
                dpw[0][i] = weights[0];
                taken[0] = 1;
            }
        }
        int count = 0;
        for(int i = 1; i < items; i++){
            for(int j = capacity; j >= 1; j--){
                int val = j - weights[i];
                if((val >= 0)&&((dp[0][val]+values[i])>=dp[0][j])){
                    count++;
                    //System.out.println("(i, j) is ("+i+","+j+")");
                    taken[i] = 1;
                    dpw[0][j] = dpw[0][val]+weights[i];
                    dp[0][j] = dp[0][val]+values[i];
                    //System.out.println("DP("+i+","+j+") = "+dp[0][j]);
                }
            }
        }

        value = dp[0][capacity];
        int optW = dpw[0][capacity];

        Search s = new Search(weights,values,taken,items,value,optW);
        s.work();
        Stack<Integer> ans = s.ans;
        System.out.println(value+" 1");
        for(int i=0; i < items; i++){
            taken[i] = 0;
        }
        int sum_value = 0;
        for(Integer Idx: ans){
            taken[Idx] = 1;
            sum_value += values[Idx];
        }
        
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }

        System.out.println("");  
        if(sum_value == value){
            System.out.println("Equal!!!");
        }
        else{
            System.out.println("Not Equal!!!");
        }
    }
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String[] args) throws IOException {
        String fileName = null;

        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
        }
        if(fileName == null)
            return;

        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        //read lines one by one
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        //match any blank character
        //get(0) may mean that get the 0th element in lines[]
        String[] firstLine = lines.get(0).split("\\s+");
        int items = Integer.parseInt(firstLine[0]);
        int capacity = Integer.parseInt(firstLine[1]);

        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        BigInteger bi1 = new BigInteger("100000000");
        BigInteger bi2 = new BigInteger( String.valueOf(items));
        BigInteger bi3 = new BigInteger( String.valueOf(capacity));
/*
        if(bi1.compareTo(bi2.multiply(bi3))>=0){
            dp_solver1(items,capacity,lines);
        }
        else{
            dp_solver3(items,capacity,lines);
        }
        */
        //dp_solver3(items,capacity,values,weights);

        dp_solver4(items,capacity,values,weights);
        //dp_search_solver1(items,capacity,lines,values,weights);
       
    }
}