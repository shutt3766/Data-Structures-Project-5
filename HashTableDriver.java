import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashTableDriver {
    public static void main(String[] args) {
        // Create instances of each hash table
        Table<String, Integer> table = new Table<>(31);
        TableDoubleHash<String, Integer> doubleHashTable = new TableDoubleHash<>(31);
        TableChainHash<String, Integer> chainHashTable = new TableChainHash<>(31);

        // Read names and numbers from the text file and insert into hash tables
        try (BufferedReader br = new BufferedReader(new FileReader("name.txt"))) {
            String line;
            int attempt = 0;
            int linearCollisions = 0;
            int doubleCollisions = 0;
            int chainCollisions = 0;

            while ((line = br.readLine()) != null) {
                attempt++;
                String[] parts = line.split(" ");
                String name = parts[0];
                int number = Integer.parseInt(parts[1]);

                // Insert into each hash table and keep track of collisions
                linearCollisions += table.put(name, number) == null ? 0 : 1;
                doubleCollisions += doubleHashTable.put(name, number) == null ? 0 : 1;
                chainCollisions += chainHashTable.put(name, number) == null ? 0 : 1;

                // Print results per attempt
                System.out.printf("%5d%9d%9d%9d%n", attempt, linearCollisions, doubleCollisions, chainCollisions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Print average collisions
        System.out.println("linear average = " + table.getCollisions() / 100.0);
        System.out.println("double average = " + doubleHashTable.getCollisions() / 100.0);
        System.out.println("chain average = " + chainHashTable.getCollisions() / 100.0);
    }
}
