
import java.util.Arrays;

/**
 * Hash Table Class Implementation
 */
public class HashTable implements IHashTable {
    private static final int CAPACITY = 15;
    private static final int CAPACITY_EXCEPTION = 5;
    private static final int DOUBLING_CAPACITY = 2;
    private static final int LEFT_SHIFT_VALUE = 5;
    private static final int RIGHT_SHIFT_VALUE = 27;
    private static final double CAPACITY_MULTIPLIER = .55;

    /* the bridge for lazy deletion */
    private static final String BRIDGE = new String("[BRIDGE]".toCharArray());

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private int capacity; //table capacity
    private int collisionCount; //counts collisions
    private int rehashCount; //counts rehashing
    private String statsLog; //getting the statistics as a String

    /**
     * Initializes a hash table with default total capacity 15
     */
    public HashTable() {
        this(CAPACITY);
    }

    /**
     * Initializes a hash table with given total capacity, and all instance variables
     * @param capacity (int) The given total capacity
     * @throws IllegalArgumentException thrown if capacity is less than 5
     */
    public HashTable(int capacity) throws IllegalArgumentException {
        //throws IAE if capacity less than 5
        if (capacity < CAPACITY_EXCEPTION) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        table = new String[capacity];
        size = 0;
        collisionCount = 0;
        rehashCount = 0;
        statsLog = "";
    }

    /**
     * Inserts element "value" in the hash table -  Returns true if the item is inserted,
     * false if it already exists.
     * @param value (String) Given element to insert
     * @return (boolean) Returns true if the item is inserted, false if it already exists.
     * @throws NullPointerException thrown if value is null
     */
    @Override
    public boolean insert(String value) throws NullPointerException{
        //throws NPE if value is null
        if (value == null) {
            throw new NullPointerException();
        }
        //returns false if the value is already there
        if (lookup(value)) {
            return false;
        }
        //makes table bigger if too small
        if (size > CAPACITY_MULTIPLIER*capacity) {
            rehash();
        }
        int startPosition = hashString(value);
        for (int position = startPosition; true; position = (position + 1) % capacity) {
            //increments the number of collisions if the position is not the start
            // position and the value isn't null or BRIDGE
            if (position == startPosition && table[position] != null && table[position]
                    != BRIDGE) {
                collisionCount = collisionCount + 1;
            }
            //insert here if there is an empty spot or BRIDGE
            if (table[position] == null || table[position] == BRIDGE) {
                table[position] = value;
                size = size + 1;
                return true;
            }
        }
    }

    /**
     * Determines where value is, and delete it from the hash table. Returns true if the item
     * is deleted, or false if it can’t be deleted
     * @param value (String) Given element to delete
     * @return (boolean) Returns true if the item is deleted, or false if it can’t be deleted
     * @throws NullPointerException thrown if value is null
     */
    @Override
    public boolean delete(String value) throws NullPointerException {
        // throws NPE is value is null
        if (value == null) {
            throw new NullPointerException();
        }
        // returns false if we can't find the value
        if (!lookup(value)) {
            return false;
        }
        int startPosition = hashString(value);
        for (int position = startPosition; true; position = (position + 1) % capacity) {
            //checks if the value you're at was deleted
            if (table[position] == BRIDGE) {
                continue;
            }
            //if the value at the position is equal to the value, returns true
            if (table[position].equals(value)) {
                size = size - 1;
                table[position] = BRIDGE;
                return true;
            }
        }
    }

    /**
     * Determines if value is in the hash table. Returns true or false to indicate
     * whether the item exists
     * @param value (String) Given element to look up
     * @return (boolean) Returns true or false to indicate whether the item exists
     * @throws NullPointerException thrown if value is null
     */
    @Override
    public boolean lookup(String value) throws NullPointerException {
        //throws NPE if value is null
        if (value == null) {
            throw new NullPointerException();
        }
        boolean beyondStart = false;
        int startPosition = hashString(value);
        for (int position = startPosition; true; position = (position + 1) % capacity) {
            // checks if you've gone around the array and are back to the start position
            if (beyondStart && position == startPosition) {
                return false;
            }
            //if the value at the position is found to be null, return false
            if (table[position] == null) {
                return false;
            }
            //checks if the value you're at was deleted
            if (table[position] == BRIDGE) {
                continue;
            }
            //if the value at the position is equal to the value, returns true
            if (table[position].equals(value)) {
                return true;
            }
            beyondStart = true;
        }
    }

    /**
     * Returns the number of elements currently stored in the HashTable.
     * @return (int) Returns the number of elements currently stored in the HashTable.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the total capacity of the table in the HashTable.
     * @return (int) Returns the total capacity of the table in the HashTable.
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Returns the statistics log of the HashTable
     * @return Returns the statistics log of the HashTable
     */
    public String getStatsLog() {
        return statsLog;
    }

    /**
     * Rehashes the table by double the capacity and iterates through the old table
     * and re-inserts every valid element to the new table. Used when the load factor
     * is greater than 0.55.
     */
    private void rehash() {
        rehashCount = rehashCount + 1;
        statsLog = statsLog + String.format("Before rehash # %d: load factor %.2f, " +
                "%d collision(s)\n", rehashCount, (float) size/capacity, collisionCount);
        collisionCount = 0;
        String[] oldArray = table;
        capacity = capacity * DOUBLING_CAPACITY;
        table = new String[capacity];
        size = 0;
        for (String s: oldArray) {
            //inserts the value if not null or bridge
            if (s != null && s != BRIDGE) {
                insert(s);
            }
        }
    }

    /**
     * Returns the hash value of the given string
     * @param value (String) The given value
     * @return (int) Returns the hash value of the given string
     */
    private int hashString(String value) {
        int hashValue = 0;
        for (int i=0; i<value.length(); i++) {
            // left shift
            int leftShiftedValue = hashValue << LEFT_SHIFT_VALUE;
            // right shirt
            int rightShiftedValue = hashValue >>> RIGHT_SHIFT_VALUE;
            hashValue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        if (hashValue < 0) {
            hashValue *= -1;
        }
        return hashValue % capacity;

    }

    /**
     * Returns the string representation of the hash table.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}

