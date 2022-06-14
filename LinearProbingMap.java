import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * No need for description.
 * @param <K> no need for description.
 * @param <V> no need for description.
 */	
public class LinearProbingMap<K, V> implements Map<K, V>
{
	/**
	 * No need for description.
	 * @param <K> no need for description.
	 * @param <V> no need for description.
	 */	
	class Pair<K,V> {
		/**
		* No need for a description.
		*/
		private K key;
		/**
		* No need for a description.
		*/	
		private V value;
		/**
		* No need for a description.
		* @param key no need for a description
		* @param value no need for a description
		*/	
		public Pair(K key, V value){
			this.key = key;
			this.value = value;
		}
		/**
		* No need for a description.
		*/	
		public Pair(){
			this.key = null;
			this.value = null;
		}

		/**
		 * Returns key.
		 * @return key
		 */
		public K getKey(){ return key; }

		/**
		 * Returns value.
		 * @return value
		 */
		public V getValue(){ return value; }

		/**
		 * Sets key to the value inputted.
		 * @param key key
		 */
		public void setKey(K key){ this.key = key; }

		/**
		 * sets value to the value inputted.
		 * @param value value
		 */
		public void setValue(V value){ this.value = value; }

		/**
		 * Returns the hashcode of a key.
		 * @return hashcode value
		 */
		@Override public int hashCode() {  
			return key.hashCode(); 
		}

		/**
		 * Checks if the inputted obj is equal to the object invoked.
		 * @param obj object
		 * @return true if equal, otherwise false.
		 */
		@Override public boolean equals(Object obj) {  
			if (obj == null) return false;
			if (!(obj instanceof Pair)) return false;
			Pair pair = (Pair)obj;
			return pair.key.equals(key); 
		}
	}

	/**
	 * No need for a description.
	 */	
	private static final int DEFAULT_CAPACITY = 400000;

	/**
	 * No need for a description.
	 */	
	private int size;

	/**
	 * No need for a description.
	 */	
	private int capacity;

	/**
	 * No need for a description.
	 */	
	private Pair<K, V>[] table;

	/**
	 * No need for a description.
	 */	
	private Pair<K, V> tombstone;  // has no impact since we are not implementing remove()


	/**
	 * No need for a description.
	 * @param capacity no need for a description.
	 */	
	@SuppressWarnings("unchecked")
	public LinearProbingMap(int capacity)
	{
		this.capacity = capacity;
		size = 0;
		table = (Pair<K, V>[])new Pair[capacity];
		tombstone = (Pair<K, V>)new Pair();	// has no impact since we are not implementing remove()
	}
	
	/**
	 * No need for a description.
	 */	
	public LinearProbingMap() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * No need for a description.
	 * @return no need for a description.
	 */	
	public int size() { 
		return size; 
	}

	/**
	 * No need for a description.
	 * @return no need for a description.
	 */		
	public boolean isEmpty() { 
		return size == 0; 
	}

	/**
	 * No need for a description.
	 */	
	public void clear() {
		size = 0;
		for (int i = 0; i < capacity; i++) {
			table[i] = null;
		}
	}


	/**
	 * Returns the value associated with key, or null if no mapping exists for key.
	 * @param key key of which its value is returned
	 * @return the value or null if nonexistent.
	 */			
	public V get(Object key) {
		int hashKey = computeHash(key);
		while(table[hashKey] != null && !table[hashKey].key.equals(key)){
			hashKey++;
			hashKey = hashKey % table.length;
		}
		if(table[hashKey]==null)
			return null;

		return table[hashKey].value;
	}
	
	/**
	 * Associates the specified value with the specified key. If the map previously contained a mapping
	 * for the key, the old value is replaced by the specified value.
	 * @param key key.
	 * @param value value.
	 * @return value
	 */	
	public V put(K key, V value) {

		Pair<K,V> p = new Pair<>(key,value);

		if(checkTable(table, key, value) == true){
			size++;
			p.key = key;
			p.value = value;
		}


		return p.value;
	}

	/**
	 * Helper method that checks whether the specified index is null or occupied. It checks for any collision
	 * and performs linear probing if computed hashCode is already occupied.
	 * @param table array with (key,value) pair
	 * @param key key
	 * @param value value
	 * @return true if successful, otherwise false.
	 */
	private boolean checkTable(Pair<K,V>[] table, K key, V value) {

		Pair<K, V> thePair = new Pair<>(key, value); //assign the pair inputted to a new Pair<K,V>
		int hashKey = computeHash(key); //compute the hashCode of the key

		if (table[hashKey] != null) {
			while (table[hashKey] != null) {

				if (table[hashKey].key.equals(key)) {
					table[hashKey].value = value;
					return false;
				}
				hashKey++; //try next spot
				hashKey = hashKey % capacity; //Linear probing until a suitable spot is found
			}
		}
		table[hashKey] = thePair; //if no collision, put (key,value) into the table at index of its computed hashKey.
		return true;
	}

	/**
	 * No need for a description.
	 * @param key no need for a description.
	 * @return no need for a description.
	 */	
	public V remove(Object key) {
		return null; // DO NOT IMPLEMENT
	}
	
	/**
	 * No need for a description.
	 * @param key no need for a description.
	 * @return no need for a description.
	 */	
	private int computeHash(Object key)
	{
		int hash = Math.abs(key.hashCode()) % capacity;
		return hash;
	}

	/**
	 * No need for a description.
	 * @return no need for a description.
	 */	
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				st.append("(" + table[i].key + ", " + table[i].value + ")");
			}
		}
		return st.toString();
	}

	/**
	 * No need for a description.
	 * @param key no need for a description.
	 * @return no need for a description.
	 */	
	public boolean containsKey(Object key) {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != tombstone && table[i] != null) {
				if (table[i].key.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * No need for a description.
	 * @param value no need for a description.
	 * @return no need for a description.
	 */	
	public boolean containsValue(Object value) {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != tombstone && table[i] != null) {
				if (table[i].value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * No need for a description.
	 * @return no need for a description.
	 */	
	public Set<K> keySet() {
		HashSet<K> set = new HashSet<K>();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != tombstone && table[i] != null) {
				set.add(table[i].key);
			}
		}
		return set;
	}	
	
	/**
	 * No need for a description.
	 * @return no need for a description.
	 */	
	public Collection<V> values() {
		ArrayList<V> list = new ArrayList<V>();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != tombstone && table[i] != null) {
				list.add(table[i].value);
			}
		}
		return list;
	}

	/**
	 * No need for a description.
	 * @return no need for a description.
	 */		
	public Set<Map.Entry<K,V>>	entrySet() {
		return null;
	}

	/**
	 * No need for a description.
	 * @param m no need for a description.
	 */	
	public void putAll(Map<? extends K,? extends V> m) {
	}
	
	/**
	 *  Main Method For Your Testing -- Edit all you want.
	 *  
	 *  @param args not used
	 */
	public static void main(String[] args)
	{
		int n = 10;
		LinearProbingMap<String, Integer> dict = new LinearProbingMap<>(n*2);
		
		for (int i = 1; i <= n; i++) {
			dict.put(""+i, i);
		}
		if (dict.size() == 10) {
			System.out.println("Yay1");
		}
		if (dict.get("5").equals(5)) {
			System.out.println("Yay2");
		}
		
		dict.put("6", 60);
		dict.put("10", 100);
		dict.put("20", 200);
		if (dict.get("6") == 60) {
			System.out.println("Yay3");
		}
		if (dict.get("10") == 100) {
			System.out.println("Yay4");
		}
		if (dict.get("20") == 200) {
			System.out.println("Yay5");
		}
		if (dict.size() == 11) {
			System.out.println("Yay6");
		}
		
		if (dict.get("200") == null) {
			System.out.println("Yay7");
		}


	}
}
