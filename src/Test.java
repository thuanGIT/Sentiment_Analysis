import java.util.*;
import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(new File("src/data.txt"));
        int count = 0;

        while (input.hasNextLine()) {
            String[] line = input.nextLine().toLowerCase().split("[\\s+\\p{P}*]");
            for (int i = 0; i < line.length; i++) {
                if (line[i].length() > 0 && line[i].equals("bad")) 
                {
                    count++;
                }
            }
        }

        System.out.println(count);


    }
}