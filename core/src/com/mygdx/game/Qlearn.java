package com.mygdx.game;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Qlearn {
	 private final double alpha = 0.8; // Learning rate
	    private final double gamma = 0.9; // Eagerness - 0 looks in the near future, 1 looks in the distant future
	float reward;;
	int statesCount;
	int board[][];
	int reward_table[][];
	public  double[][] Q; 
	public Qlearn(){
	reward=0;
	 statesCount=10*20;
	 board=new int[statesCount][3]; 
	 reward_table=new int[statesCount][3]; 
	 Q = new double[statesCount][3];
	}
	
	public void init_Q_matrix(){
		
		for(int i=0;i<statesCount;i++){
			for(int j=0;j<3;j++){
				board[i][j]=0;
				Q[i][j]=0;
			}
		}
		
		for(int i=0;i<statesCount;i++){
			for(int j=0;j<3;j++){
				reward_table[i][j]=0;
			}
		}
		  
//        System.out.println();
//		 System.out.printf("%25s", "Action: ");
//	        System.out.printf("UP  DOWN LEFT RIGHT");
//	        
//	        System.out.println("]");
//
//	        for (int i = 0; i < statesCount; i++) {
//	            System.out.print("Possible states from " + i + " :[");
//	            for (int j = 0; j < 4; j++) {
//	                System.out.printf("%4s", board[i][j]);
//	            }
//	            System.out.println("]");
//	        }
	}
	
	public void init_R_matrix() throws IOException{
		Path filePath = Paths.get("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/maze3.txt");
		 Scanner scanner = new Scanner(filePath);
		 List<Integer> integers = new ArrayList<>();
		 int l=0;
		 System.out.println(scanner.next());
		 while (scanner.hasNext()) {
		     if (scanner.hasNextInt()) {
		         integers.add(scanner.nextInt());
		         l++;
		     } else {
		    	
		         scanner.next();
		     }
		 }
		 System.out.println("DLUGOSC"+l );
		 int k=0;
		  for (int i = 0; i < statesCount; i++) {
			  if(k>798)
	        	  break;
	            for (int j = 0; j < 4; j++) {
	               reward_table[i][j]=integers.get(k);
	               k++;
	            }
	         
	        }
	}
	
	public void init_Q_matrix2() throws IOException{
		Path filePath = Paths.get("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/maze5.txt");
		 Scanner scanner = new Scanner(filePath);
		 List<Double> integers = new ArrayList<>();
		 int l=0;
		 System.out.println(scanner.next());
		 while (scanner.hasNext()) {
		     if (scanner.hasNextDouble()) {
		    	
		     } else {
		    	 double d=Double.parseDouble(scanner.next());
		         integers.add(d);
		         l++;
		         scanner.next();
		     }
		 }
		 System.out.println("DLUGOSC"+l );
		 int k=0;
		  for (int i = 0; i < statesCount; i++) {
			  if(k>798)
	        	  break;
	            for (int j = 0; j < 3; j++) {
	               Q[i][j]=integers.get(k);
	               k++;
	            }
	         
	        }
	}
	
	void reset(){
		
		for(int i=0;i<MainGameScreen.szerokosc_planszy;i++){
			for(int j=0;j<MainGameScreen.szerokosc_planszy;j++){
				board[i][j]=0;
			}
		}
	}
	
	void iteration(){
		
		
	}
	
	 void printR(double[][] reward_table2) {
	        System.out.printf("%25s", "Action: ");
	        System.out.printf("UP  DOWfN LEFT RIGHT");
	        
	        System.out.println();

	        for (int i = 0; i < statesCount; i++) {
	            System.out.print("Possible states from " + i + " :[");
	            for (int j = 0; j < 3; j++) {
	                System.out.printf("%4s", reward_table2[i][j]);
	            }
	            System.out.println("]");
	        }
	    }
	 
	 void printQ(double[][] reward_table2) {
	        System.out.printf("%25s", "Action: ");
	        System.out.printf("UP  DOWfN LEFT RIGHT");
	        
	        System.out.println();

	        for (int i = 0; i < statesCount; i++) {
	            System.out.print("Possible states from " + i + " :[");
	            for (int j = 0; j < 3; j++) {
	                System.out.printf("%4s", reward_table2[i][j]);
	            }
	            System.out.println("]");
	        }
	    }
	 
	 int[] possibleActionsFromState(int state) {
	        ArrayList<Integer> result = new ArrayList<>();
	        for (int i = 0; i < 3; i++) {
	            if (reward_table[state][i] != -1) {
	                result.add(reward_table[state][i]);
	            }
	        }

	        return result.stream().mapToInt(i -> i).toArray();
	    }
	 
	 double maxQ(int nextState) {
	        int[] actionsFromState = possibleActionsFromState(nextState);
	        double maxValue =0.0;
	        for (int i=0;i<3;i++) {
	            double value = Q[nextState][i];

	            if (value > maxValue)
	                maxValue = value;
	        }
	        return maxValue;
	    }

	 int maxIndex(int nextState,double[][] Q){
		 int[] actionsFromState = possibleActionsFromState(nextState);
	        double maxValue = 0.0;
	        int maxIndex=0;
	        for (int i=0;i<3;i++) {
	            double value = Q[nextState][i];

	            if (value > maxValue){
	            	 maxValue = value;
	            	 maxIndex=i;
	            }
	                maxValue = value;
	        }
	        return maxIndex;
		 
	 }
	 
	 public void calculateQ(int state,int action,int nextState2,int reward) {
	        Random rand = new Random();

	      
	            int crtState = state;
	          
	           
	             

	              
	               

	                // Q(state,action)= Q(state,action) + alpha * (R(state,action) + gamma * Max(next state, all actions) - Q(state,action))
	                double q = Q[crtState][action];
	                System.out.println("VALUE PRZED OBLICZANIEM: "+q);
	                double maxQ = maxQ(nextState2);
	               // int r = reward_table[crtState][action];
	               

	               int r=reward;
	               
	               double value = q + alpha * (r + gamma * maxQ );
	               // double value=(1-alpha)*q+(alpha*((r+0.9)*maxQ));
	                Q[crtState][action] = value;
			        System.out.println("VALUE PO OBLICZENIACH: "+value);
	                
	                //zapisywanie Qmatrixa do pliku
//			        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//			                new FileOutputStream("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/maze5.txt"), "utf-8"))) {
//			        	
//			        	for (int i = 0; i < 200; i++) {
//					          
//					            for (int j = 0; j < 3; j++) {
//					            	  writer.write( new Double(Q[i][j]).toString()+ " ");
//					            }
//					           writer.write("\r\n");
//					          // writer.write();
//					        }
//			  } catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			       
	               // crtState = nextState;
	            
	        }
	 
	 
	    
}
