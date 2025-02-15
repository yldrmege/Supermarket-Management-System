
import java.util.InputMismatchException;
import java.util.Scanner;

public class Operation {
	public static void getCustomerInfo(HashTable<String,Customer>main_events, String customerId) {//Sorting date method
        //The purpose here is to sort the customer information captured with getValue according to date and write it to the output screen.
		int months[]=new int[]{31,28,31,30,31,30,31,31,30,31,30,31};		
	    ListInterface<Customer> customerList = main_events.getValue(customerId);// for keeping sorted  situation
	    if(customerList==null)
	    	System.out.println("Customer not found");
	    else {
	    	Date[] date=new Date[customerList.getLength()];
		    ListInterface<Date>datesortsituation=new LList<>();
		    int count=0;
		    for(int i=1;i<=customerList.getLength();i++) {
		    	String dateline=customerList.getEntry(i).getDate();
		    	String datelines[]=dateline.split("-");
		    	//Check for year and month situation
		    	if(Integer.parseInt(datelines[0])==2022||Integer.parseInt(datelines[0])==2023) {
		    		if((Integer.parseInt(datelines[0])==2022&&Integer.parseInt(datelines[1])>=11&&Integer.parseInt(datelines[1])<=12)
		    				||(Integer.parseInt(datelines[0])==2023)&&Integer.parseInt(datelines[1])<=11)
		    			 {
		    			if(Integer.parseInt(datelines[2])<=months[Integer.parseInt(datelines[1])-1]) {
		    				date[count]=new Date(Integer.parseInt(datelines[0]),Integer.parseInt(datelines[1]),Integer.parseInt(datelines[2]));
		    				count++;
		    			}	    			
		    		}
		    	}
		    }	   
		    int datelength=date.length;
		    int maxyear;
		    int maxmonth;
		    int maxday;
		    //Here the dates are sorted from largest to smallest.
		    for(int i=0;i<datelength;i++) {
		    	maxyear=0;
		    	maxmonth=0;
		    	maxday=0;
		    	for(int j=0;j<datelength;j++) {
		    		if(date[j]!=null) {
		    			if(date[j].getYear()>maxyear)
			    			maxyear=date[j].getYear();
		    		}	    		
		    	}
		    	for(int j=0;j<datelength;j++) {
		    		if(date[j]!=null) {
		    			if(date[j].getYear()==maxyear) {
		    				if(date[j].getMonth()>maxmonth)
		    					maxmonth=date[j].getMonth();
		    			}
		    		}
		    	}
		    	for(int j=0;j<datelength;j++) {
		    		if(date[j]!=null) {
		    			if(date[j].getYear()==maxyear&&date[j].getMonth()==maxmonth) {
		    				if(date[j].getDay()>maxday) {
		    					maxday=date[j].getDay();
		    				}
		    			}
		    		}
		    	}
		    	for(int j=0;j<datelength;j++) {
		    		if(date[j]!=null) {
		    			if(date[j].getYear()==maxyear&&date[j].getMonth()==maxmonth&&date[j].getDay()==maxday) {
			    			datesortsituation.add(date[j]);
			    			date[j]=null;
			    			break;
			    		}
		    		}	    		
		    	}
		    }	  
		    //After finding the sort notation we will print the list 
		    //Adding customer information to the new list and printing this list
		    if (customerList != null && !customerList.isEmpty()) {
		        System.out.println(customerList.getLength() + " transactions found for " + customerList.getEntry(1).getName());}        	      	       
		        String dateyear;
		        String datemonth;
		        String dateday;
		        String result;
		        String previous_result="";
		        for (int i=1;i<=datesortsituation.getLength();i++) {//Final Process for sorting
		        	 dateyear=String.valueOf(datesortsituation.getEntry(i).getYear());
		        	if(datesortsituation.getEntry(i).getMonth()<10) {
		        		 datemonth="0"+String.valueOf(datesortsituation.getEntry(i).getMonth());
		        	}
		        	else {
		        		 datemonth=String.valueOf(datesortsituation.getEntry(i).getMonth());
		        	}
		        	if(datesortsituation.getEntry(i).getDay()<10) {
		        		dateday="0"+String.valueOf(datesortsituation.getEntry(i).getDay());  
		        	}
		        	else {
		        		dateday=String.valueOf(datesortsituation.getEntry(i).getDay());
		        	}
		        	 result=dateyear+"-"+datemonth+"-"+dateday;	 	  
		        	 
		            for(int j=1;j<=customerList.getLength();j++) {//Printing in reverse chronological order			            	
		            	if(result.equals(customerList.getEntry(j).getDate())&&!previous_result.equals(result)) {		            		
		            		System.out.println(customerList.getEntry(j).getDate()+" "+customerList.getEntry(j).getProduct());	            		
		            	}	            		            	
		            }
		            previous_result=result;
		        }	
	    }
	}	
	
	public static double LOADFACTOR;// in order for the information collected from the user to be used in the hashTable class
	public static int HASHFUNCTION;// in order for the information collected from the user to be used in the hashTable class
	public static int COLLISIONHANDLING;//in order for the information collected from the user to be used in the hashTable class
	public static String KEY;
	public static void Initialization(int condition) {	//information is retrieved from the user one by one
		String question1="Choosing a load factor(0.5 or 0.8)";
		 double loadfactor=Operation.DoubleSituation(question1);//asking load factor
		 LOADFACTOR=loadfactor;
	    String question2="Choosing a hashfunction situation(write 1 for Simple Summation Function,write 2 for Polynomial Accumulation Function)";
	    int hashfunction=Operation.ChooseSituation(question2);//asking simple summation or polynomial accumulation
	    HASHFUNCTION=hashfunction;
	    String question3="Choosing a collision handling situation(write 1 for Linear Probing,write 2 for Double Hashing)";
	    int collisionhandling=Operation.ChooseSituation(question3);//asking linear probing or double hashing
	    COLLISIONHANDLING=collisionhandling;	    
	    System.out.println();
	    if(condition==1) {
	    	System.out.print("Search:");
		    String key=input.next();
		    KEY=key;
	    }	    	    
	}
	public static void printList(LList<String> list) {//To print Key Contains and KeyNote Contains
		for (int i = 1; i <= list.getLength(); i++) {
			System.out.println(list.getEntry(i));
		}
		System.out.println();
	}
	private static  Scanner input=new Scanner(System.in);
	public static int IntegerSituation(String question) {// Check whether the information received from the user is integer or not
		
		 int n = 0;
	        boolean isNumber = false;
	        while (!isNumber) {  //receiving input from user
	            try {
	            	System.out.println();
	            	System.out.println(question);		//check for the input about it is integer or not                
	                n = input.nextInt();
	                System.out.println();
	                isNumber = true;
	            } catch (InputMismatchException e) {
	                System.out.println("Please enter a integer.");//if not an integer, the value is re-entered.
	                input.nextLine();
	            }
	        }	        
	        return n;
	}
	private static int ChooseSituation(String question) {	//Checks whether the entered information is within the appropriate range.	
		int n=IntegerSituation(question);		
		if(n!=2&&n!=1) {
			while(n!=2||n!=1) {
				System.out.println("Enter a  value in the correct range(1 or 2).");
	        	System.out.print("Please enter : "); //if the range is wrong, it is re-entered.
	        	n=input.nextInt();
	        	if(n==2||n==1)
	        		break;
	        	System.out.println();
			}
		}			
		return n;
	}
	private static double DoubleSituation(String question) {//Special for load factor
		double n = 0;
        boolean isNumber = false;
        while (!isNumber) {  //receiving input from user
            try {
            	System.out.println();
            	System.out.println(question);		//check for the input about it is double or not                
                n = input.nextDouble();
                System.out.println();
                isNumber = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a double.");//if not an double, the value is re-entered.
                input.nextLine();
            }
        }
        if(n!=0.5&&n!=0.8) {
			while(n!=2||n!=1) {
				System.out.println("Enter a  value in the correct range(0,5 or 0,8).");
	        	System.out.print("Please enter : "); //if the range is wrong, it is re-entered.
	        	n=input.nextDouble();
	        	if(n==0.5||n==0.8)
	        		break;
	        	System.out.println();
			}
		}
        return n;
	}	
}
