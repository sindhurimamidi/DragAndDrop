package patternmatch;
import java.io.*;
import java.util.*;

	public class PatternMatching 
	{
		public static void main (String[] args) throws java.io.IOException
	    {
			//Getting the name of the files to be compared.
			BufferedReader br0 = new BufferedReader (new
					InputStreamReader(System.in));
			
			System.out.println("Enter 1st File name:");
	  		String str = br0.readLine();  //input
	  		System.out.println("Enter 2nd File name:");
	  		String str1 = br0.readLine();  //pattern
	  		System.out.println("Enter mode:");
	  		String mode = br0.readLine(); //mode
	  		
	  		System.out.println ("mode is " + mode);
	  	
	  		String y="",z="";

	        //Reading the contents of the files
	 		BufferedReader br = new BufferedReader (new FileReader (str));
			BufferedReader br_pat = new BufferedReader (new FileReader (str1));
	
			String[] pat_arr = new String[10000]; //can be changed to variable based on user-input
			for(int k=0;k<10000;k++) {
				pat_arr[k]="";  //load empty strings
			}
			
			int patLines = 0;
			while((z=br_pat.readLine())!=null) {
				pat_arr[patLines++] = z;	
			}
		   	//System.out.println("Stored " + patLines + " tokens from pattern file");
		   	//for(int k=0;k<patLines;k++) {
				//System.out.println("debug pat_arr[" + k + "]=" + pat_arr[k]);
			//}
		   	
		   	int inpLines = 0;
		   	String[] inp_arr = new String[100];
		   	while((y=br.readLine())!=null) {
		   		inp_arr[inpLines++] = y;
		   	}
	    
			int x=0;
	     	for(int m=0;m<inpLines;m++) {
	     		for(int n=0;n<patLines;n++) {
	     			String inp_str = inp_arr[m];
	     			String pat_str = pat_arr[n];	
	     			
	     			if ((mode.equals("1")) && (inp_str.equals(pat_str))) {
	     				x++;
	     				System.out.println("MODE 1: Found match='" + pat_arr[n] + "' for input line='" + inp_arr[m] + "'");	
	     			}
	     			else if(mode.equals("2")){	
	     				int inpLength = inp_arr[m].length();
	     		        int patLength = pat_arr[n].length();
	     		        if (inpLength >= patLength) {	
	     		        	boolean foundIt = false;
	     		        	for (int i = 0;i <= (inpLength - patLength);i++) {
	     		        		if (inp_arr[m].regionMatches(i, pat_arr[n], 0, patLength)) {
	     		        			foundIt = true;
	     		        			break;
	     		        		}
	     		        	}
	     		        	if (foundIt == true) {
	     		        		x++;
	     		        		System.out.println("MODE 2: Found match='" + pat_arr[n] + "' for input line='" + inp_arr[m] + "'");
	     		        	}
	     		        }
	     	     	} //mode 2	
	     			else if(mode.equals("3")){
	     				int edit_dist = computeEditDistance(inp_arr[m], pat_arr[n]);
	     				if (edit_dist <=1)
	     				{  x++;
	     					System.out.println("MODE 3: Found match='" + pat_arr[n] + "' for input line='" + inp_arr[m] + "'");
	     			    }   
	     			}
	     		}
	     	}
	     	System.out.println("MODE " + mode + " : found matches=" + x);
	    } //end of main
		
		public static int computeEditDistance(String s1, String s2) {
	        s1 = s1.toLowerCase();
	        s2 = s2.toLowerCase();

	        int[] costs = new int[s2.length() + 1];
	        for (int i = 0; i <= s1.length(); i++) {
	            int lastValue = i;
	            for (int j = 0; j <= s2.length(); j++) {
	                if (i == 0)
	                    costs[j] = j;
	                else {
	                    if (j > 0) {
	                        int newValue = costs[j - 1];
	                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
	                            newValue = Math.min(Math.min(newValue, lastValue),
	                                    costs[j]) + 1;
	                        costs[j - 1] = lastValue;
	                        lastValue = newValue;
	                    }
	                }
	            }
	            if (i > 0)
	                costs[s2.length()] = lastValue;
	        }
	        return costs[s2.length()];
	    }

	}
	     	
