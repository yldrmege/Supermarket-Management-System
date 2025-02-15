import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Test {

	public static void main(String[] args) {		
		String question="Do you want to search just one key or use customer_1K.txt?(Write 1 for search just one key)";		
		int condition=Operation.IntegerSituation(question);//The user is asked a question if condition is equals to 1 just one key situation
		Operation.Initialization(condition);			
		long addingstart=0;// it is for keeping time
		long addingfinish=0;// it is for keeping time
		Customer customer;//it is for transactions	
		HashTable<String,Customer > main_events=new HashTable<>();//HashTable variable	
		try {			// read excel file
			File file=new File("supermarket_dataset_50K.csv");
			Scanner scanner=new Scanner(file);
			scanner.nextLine();	
			  addingstart = System.currentTimeMillis();// add start time
			while(scanner.hasNext()) {//file reading process
				String line=scanner.nextLine();
				String[] lines=line.split(",");
				customer=new Customer(lines[1], lines[2], lines[3]);//Customer:Name,Date,Product										
                    main_events.add(lines[0],customer);                         
			}				
			 addingfinish = System.currentTimeMillis();	// add finish time
			 scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found");			
			//e.printStackTrace();			
		}				
		String[] searchList=new String[1000];//this is for search1k_txt
		int count=0;	
		try {			// read text file
			File file=new File("customer_1K.txt");
			Scanner scanner=new Scanner(file);						  
			while(scanner.hasNext()) {
				String line=scanner.nextLine();
				searchList[count]=line;
				count++;
			}								
			 scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");			
			//e.printStackTrace();			
		}	
		double average=0;// it is for average search time
		long result=0;// it is for keeping time
		long min=0;// minimum search time
		long max=0;// maximum search time
		long searchingstart=0;// it is for keeping time
		long searchingend=0;// it is for keeping time	
		LList<String> KeyContains=new LList<>();
		LList<String> KeyNotContains=new LList<>();
		if(condition!=1) {
			for(int i=0;i<searchList.length;i++) {
				searchingstart=System.nanoTime();//search time start
				if(main_events.getValue(searchList[i])==null)// if it is not in hashTable output will be KeyNotContains
					KeyNotContains.add(searchList[i]);
				else// if it is in hashTable output will be KeyContains
					KeyContains.add(searchList[i]);
				searchingend=System.nanoTime();//search time end
				result=(searchingend-searchingstart);//the difference between times
				average=average+result;//all time
				if(i==0) {
				   min=result;
				   max=result;
				}	
				if(i>0) {
					if(result<min) 
						min=result;				
					if(result>max)
						max=result;
				}
		}	
			System.out.println();
			//We thought that the user sees these keys and can choose among them if he or she wants to enter his or her own searching key.
			System.out.println("KeyNotContains:");
			Operation.printList(KeyNotContains);//	
			System.out.println("KeyContains:");
			Operation.printList(KeyContains);
		}
		average=average/1000;//average time for searching
		System.out.println();
		if(condition==1) {//if the user choose condition 1,he or she can enter a key.
			Operation.getCustomerInfo(main_events,Operation.KEY);		
			System.out.println("---------------------------------------------");
			System.out.println("If you choose -searching just one key situation- ,You can not see the search times for customer_1K.txt");
		}			
		System.out.println("---------------------------------------------");						
		System.out.println("Final TableSize:"+main_events.getSize());//final size
		System.out.println("Collision count:"+main_events.PrintCollisionNumber());
		System.out.println("Expanded time while loading transactions:"+((double)(addingfinish-addingstart)/1000)+" second");//adding process			
		System.out.println("Search Time Minimum:"+min+" nanosecond");// if the user choose condition 1 ,the answer will be 0
		System.out.println("Search Time Maximum:"+max+" nanosecond");//if the user choose condition 1 ,the answer will be 0
		String format = String.format("%.3f", average);//if the user choose condition 1 ,the answer will be 0;
		System.out.println("Search Time Average:"+format+" nanosecond");	//if the user choose condition 1 ,the answer will be 0	
	}	 			
}
