
public class HashTable<K, V> implements DictionaryInterface<K, V> {
	private int numberOfEntries;
	private static final int DEFAULT_CAPACİTY =12;//next prime:13
	private HashEntry<K, V>[] hashTable;
	//private int tableSize;	
	private static final double MAX_LOAD_FACTOR=Operation.LOADFACTOR;//Load factor information from the user
	private static final int HASH_FUNCTION=Operation.HASHFUNCTION;// HashFunction information from the user
	private static final int COLLISION_HANDLING=Operation.COLLISIONHANDLING;// Collision Handling information from the user
	private static long COLLISIONCOUNTS=0;	
	public HashTable() {//first constructor
		this(DEFAULT_CAPACİTY);
	}
	public HashTable(int initial_capacity) {// second constructor
		numberOfEntries = 0;
		int tableSize=getNextPrime(initial_capacity);

		HashEntry<K, V>[] tempHashTables = (HashEntry<K, V>[]) new HashEntry[tableSize];
		hashTable = tempHashTables;

	}	
	// Simple Summation Function
	private int ssf(String keyString) {//Generates values ​​according to ASCII codes
		int hash = 0;
		for (int i = 0; i < keyString.length(); i++) {
			hash += keyString.charAt(i);
		}
		return hash;
	}
	// Polynomial Accumulation Function
	private int paf(String key, int z) {//Generates value according to a special formula
		int n = key.length();
		int hash = 0;

		for (int i = 0; i < n; i++) {
			char ch = key.charAt(i);
			int charValue = getCharValue(ch);
			hash += charValue * Math.pow(z, n - i - 1);
		}

		return hash;
	}

	private int getCharValue(char ch) {
		ch = Character.toLowerCase(ch);

		// 'A' is 1, 'B' is 2 and so on....
		return ch - 'A' + 1;
	}

	private int FirstHashFunction(String key) {//Method that runs SSF
		int first = ssf(key);
		//int first=key.hashCode();
		first=first%hashTable.length;
		if(first<0)
			first=first+hashTable.length;
		return first;	
		

	}

	private int SecondHashFunction(String key) {//Method that runs PAF
		int second = paf(key, 37);
		second=second%hashTable.length;
		if(second<0)
			second=second+hashTable.length;
		return second;				
	}

	public LList<V> getValue(K key) {//Since the user has more than one value, it returns as a value list.
		LList<V> result = null;

		int index=-1;
		if(HASH_FUNCTION==1) {//SSF
			index=FirstHashFunction(key.toString());
		}
		if(HASH_FUNCTION==2) {//PAF
			index=SecondHashFunction(key.toString());
		}
		index = locate(index, key);
		if (index != -1) {
			result = hashTable[index].getValue();
		}
		return result;

	}
	
	public LList<V>remove(K key) {////Since the user has more than one value, it returns as a value list.

		LList<V> removedValue = null;

		int index=-1;
		if(HASH_FUNCTION==1) {//SSF
			index=FirstHashFunction(key.toString());
		}
		if(HASH_FUNCTION==2) {//PAF
			index=SecondHashFunction(key.toString());
		}
		if (index != -1) {
			index = locate(index, key);
			removedValue=hashTable[index].getValue();
			hashTable[index].setKey(null);
			hashTable[index].setValue(null);
			hashTable[index].setState(HashEntry.State.REMOVED);
			numberOfEntries--;
		}
		return removedValue;
	}

	private int locate(int index, K key) {//Finds which specific index the key is in
		int constant=index;
		boolean found = false;		
		while (!found &&hashTable[index]!=null) {
			if (isIn(index) && key.equals(hashTable[index].getKey())) {
				found = true;
			} else {
				if(COLLISION_HANDLING==1)//Linear probing
					index = (index + 1) % hashTable.length;
				if(COLLISION_HANDLING==2) {// double hashing
					index=(index+(11-constant%11))%hashTable.length;
				}
			}
		}
		int result = -1;
		if (found)
			result = index;
		return result;
	}

	public boolean isIn(int index) {//it checks whether there is a key in that index or not.
		boolean flag = false;
		if (hashTable[index].getState() == HashEntry.State.CURRENT) {
			flag = true;
		}
		return flag;
	}
	public int getSize() {//returns size
		return hashTable.length;
	}
	public long PrintCollisionNumber() {//returns total collision number
		return COLLISIONCOUNTS;
	}

	public V add(K key, V value) {

		if ((key == null) || (value == null))
			throw new IllegalArgumentException("Cannot add null to a dictionary.");
		else {
			V oldValue; // Value to return
			int index=-1;
			if(HASH_FUNCTION==1) {//SSF
				index=FirstHashFunction(key.toString());				
			}
			if(HASH_FUNCTION==2) {//PAF
				index=SecondHashFunction(key.toString());
			}
			index = probe(index, key); // Check for and resolve collision
			// Assertion: index is within legal range for hashTable			
			if ( (hashTable[index] == null) || hashTable[index].getState()==HashEntry.State.REMOVED )
			//if a key has not been added before, it adds the key from scratch.
			{  LList<V> values = new LList<>();
	        values.add(value);
	        hashTable[index] = new HashEntry<>(key, values);
	        hashTable[index].setState(HashEntry.State.CURRENT);
	        numberOfEntries++;
	        oldValue = null;
			}
			else
			{ // Key found; get old value for return and then replace it(with list format)
				LList<V> values = hashTable[index].getValue();
		        values.add(value);		       
		        hashTable[index].setValue(values);
		        oldValue=values.getEntry(1);
			}
			// Ensure that hash table is large enough for another add
			if (isHashTableTooFull()) EnlargeHashTable();
			return oldValue;
		} // end if
	} // end add

	private boolean isHashTableTooFull()
	{
		return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
	}	
	private int probe(int index, K key) {				
		boolean found = false;
		int constant=index;
		int removedStateIndex = -1; // index of first location in removed state
		while ( !found && (hashTable[index] != null) )
		{
		if (isIn(index))
		{
		if (key.equals(hashTable[index].getKey()))
		found = true; // Key found
		else // Follow probe sequence
		{ 
			 if (COLLISION_HANDLING == 1) {//Linear probing
				 index = (index + 1) % hashTable.length;
				 COLLISIONCOUNTS++;
			 }
                
            if (COLLISION_HANDLING == 2) {// double hashing
            	 index = (index + (11 - constant % 11)) % hashTable.length;
            	 COLLISIONCOUNTS++;
             }            
		}                
		}
		else // Skip entries that were removed
		{
		// Save index of first location in removed state
		if (removedStateIndex == -1)
		removedStateIndex = index;
		if (COLLISION_HANDLING == 1) // Linear probing
            index = (index + 1) % hashTable.length;
       if (COLLISION_HANDLING == 2)  // double hashing
            index = (index + (11 - constant % 11)) % hashTable.length;
		}
		} // end while
		// Assertion: Either key or null is found at hashTable[index]
		if (found || (removedStateIndex == -1) )
		return index; // index of either key or null
		else
		return removedStateIndex; // index of an available location
		}
	
	
	private void EnlargeHashTable() {
		//The purpose is to increase the size of the table and allow new entries to be entered.
		HashEntry<K, V>[] oldTable=hashTable;
		int oldsize=hashTable.length;
		int newSize=getNextPrime(oldsize+oldsize);		
		HashEntry<K, V>[] temp=(HashEntry<K, V>[]) new HashEntry[newSize];
		hashTable=temp;
		numberOfEntries=0;		
		for(int index=0;index<oldsize;index++) {			
			if (oldTable[index]!=null&&oldTable[index].getState()==HashEntry.State.CURRENT) {
				LList<V> values=oldTable[index].getValue();
				for(int i=1;i<=values.getLength();i++) {
					add(oldTable[index].getKey(),values.getEntry(i));
				}
			}
		}										
	}
	private int getNextPrime(int integer)//greater than that number produces the smallest prime
	{
		// if even, add 1 to make odd
		if (integer % 2 == 0)
		{
			integer++;
		} // end if

		// test odd integers
		while (!isPrime(integer))
		{
			integer = integer + 2;
		} // end while

		return integer;
	} // end getNextPrime

	// Returns true if the given integer is prime.
	private boolean isPrime(int integer)
	{
		boolean result;
		boolean done = false;

		// 1 and even numbers are not prime
		if ( (integer == 1) || (integer % 2 == 0) )
		{
			result = false; 
		}

		// 2 and 3 are prime
		else if ( (integer == 2) || (integer == 3) )
		{
			result = true;
		}

		else // integer is odd and >= 5
		{
			assert (integer % 2 != 0) && (integer >= 5);

			// a prime is odd and not divisible by every odd integer up to its square root
			result = true; // assume prime
			for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2)
			{
				if (integer % divisor == 0)
				{
					result = false; // divisible; not prime
					done = true;
				} // end if
			} // end for
		} // end if

		return result;
	} // end isPrime
	

	private static class HashEntry<K,V> {//The type of HashTable indexes is hash entry

	    private K key;
	    private LList<V> value;//The aim here is to keep all the key values ​​in one place.
	    private   State state;
	    private enum State {CURRENT,REMOVED};
	   
	    
	    
	    
	    private HashEntry(K key, LList<V> value) {
	          this.key = key;
	          this.value = value;

	          state=State.CURRENT;
	    }


		private  K getKey() {
			return key;
		}


	    private void setKey(K key) {
			this.key = key;
		}


		private LList<V> getValue() {
			return value;
		}


		private void setValue(LList<V> value) {
			this.value = value;
		}


		private State getState() {
			return state;
		}


		public void setState(State state) {
			this.state = state;
		}     

	    

	}


}


