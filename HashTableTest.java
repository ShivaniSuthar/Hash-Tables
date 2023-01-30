
import static org.junit.Assert.*;

public class HashTableTest {

    HashTable table1;
    @org.junit.Before
    public void setUp() throws Exception {
        table1 = new HashTable(5);

    //tests for the first constructor
    }
    @org.junit.Test
    public void constructor1_1() {
        table1 = new HashTable();
        assert (table1.size() == 0);
    }

    @org.junit.Test
    public void constructor1_2() {
        table1 = new HashTable();
        assert (table1.capacity() == 15);
    }

    @org.junit.Test
    public void constructor1_3() {
        table1 = new HashTable();
        assert (table1.getStatsLog().equals(""));
    }

    //tests for the second constructor
    @org.junit.Test (expected = IllegalArgumentException.class)
    public void constructor2_1() {
        table1 = new HashTable(1);

    }

    @org.junit.Test
    public void constructor2_2() {
        table1 = new HashTable(5);
        assert(table1.capacity() == 5);

    }

    @org.junit.Test
    public void constructor2_3() {
        table1 = new HashTable(5);
        assert(table1.size() == 0);

    }

    //tests for insert
    @org.junit.Test(expected = NullPointerException.class)
    public void insert1() {
        table1.insert(null);
    }

    @org.junit.Test
    public void insert2() {
        assert(table1.insert("Hello"));

    }

    @org.junit.Test
    public void insert3() {
        table1.insert("Hello");
        assert(!table1.insert("Hello"));

    }

    @org.junit.Test
    public void insert4() {
        table1.insert("A");
        table1.insert("B");
        table1.insert("C");
        assert(table1.capacity() == 5);
        table1.insert("D");
        assert(table1.capacity() == 10);

    }

    //tests for delete
    @org.junit.Test (expected = NullPointerException.class)
    public void delete1() {
        table1.delete(null);

    }

    @org.junit.Test
    public void delete2() {
        assert(!table1.delete("Hello"));

    }

    @org.junit.Test
    public void delete3() {
        table1.insert("Hello");
        assert(table1.delete("Hello"));

    }

    //tests for lookup
    @org.junit.Test (expected = NullPointerException.class)
    public void lookup1() {
        table1.lookup(null);

    }

    @org.junit.Test
    public void lookup2() {
        assert(!table1.lookup("Hello"));

    }

    @org.junit.Test
    public void lookup3() {
        table1.insert("Hello");
        assert(table1.lookup("Hello"));

    }

    //tests for size
    @org.junit.Test
    public void size1() {
        table1.insert("Hey!");
        assert(table1.size() == 1);
    }

    @org.junit.Test
    public void size2() {
        assert(table1.size() == 0);
    }

    @org.junit.Test
    public void size3() {
        table1.insert("Hey!");
        table1.insert("Nay");
        table1.delete("Hey!");
        assert(table1.size() == 1);
    }

    //tests for capacity
    @org.junit.Test
    public void capacity1() {
        assert(table1.capacity() == 5);
    }

    @org.junit.Test
    public void capacity2() {
        table1.insert("Hey!");
        table1.insert("Nay");
        table1.insert("Ohh");
        table1.insert("tree");
        assert(table1.capacity() == 10);
    }

    @org.junit.Test
    public void capacity3() {
        table1.insert("Hey!");
        table1.insert("Nay");
        table1.insert("Ohh");
        table1.insert("tree");
        table1.insert("Xmas");
        table1.insert("cookies");
        table1.insert("mmmm");
        table1.insert("A");
        table1.insert("B");
        table1.insert("C");
        table1.insert("D");
        table1.insert("E");
        assert(table1.capacity() == 20);
    }

    //tests for getStatsLog
    @org.junit.Test
    public void getStatsLog1() {
        assert(table1.getStatsLog().equals(""));

    }

    @org.junit.Test
    public void getStatsLog2() {
        table1.insert("Hey!");
        table1.insert("Nay");
        table1.insert("Ohh");
        table1.insert("tree");
        assert (table1.getStatsLog().equals("Before rehash # 1: load factor 0.60, 0 " +
                "collision(s)\n"));

    }

    @org.junit.Test
    public void getStatsLog3() {
        table1.insert("Hey!");
        table1.insert("Nay");
        table1.insert("Ohh");
        table1.insert("tree");
        table1.insert("Xmas");
        table1.insert("cookies");
        table1.insert("mmmm");
        table1.insert("A");
        table1.insert("B");
        table1.insert("C");
        table1.insert("D");
        table1.insert("E");
        assert(table1.getStatsLog().equals("Before rehash # 1: load factor 0.60, " +
                "0 collision(s)\n" +
                "Before rehash # 2: load factor 0.60, 3 collision(s)\n"));
    }

    //test for toString
    @org.junit.Test
    public void testToString1() {
        assert(table1.toString().equals("[null, null, null, null, null]"));
    }

    @org.junit.Test
    public void testToString2() {
        table1.insert("Hey!");
        assert(table1.toString().equals("[null, null, null, Hey!, null]"));
    }

    @org.junit.Test
    public void testToString3() {
        table1.insert("Hey!");
        table1.insert("No");
        assert(table1.toString().equals("[null, null, null, Hey!, No]"));
    }
}