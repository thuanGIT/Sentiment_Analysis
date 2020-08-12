import java.util.*;
import java.io.*;

public class Analysis {
    public static void main(String[] args) {

        // Subproblem 1: get choice
        Scanner input = new Scanner(System.in);
        String menu  = 
            "\nWhat would you like to do?\n"+
                "1. Get the score of a word\n"+
                "2. Get the score of a sentence\n"+
                "3. See the top 5 most positive words with more than one occurrence\n"+
                "4. See the top 5 most negative words with more than one occurrence\n"+ 
                "5. Exit\n";
        int choice = getChoice(menu, input);


        // Subproblem 2: read in data
        Map<String, Rating> reviews = new HashMap<String, Rating>();

        try {
            readData("src/data.txt", reviews);
        } catch (IOException e) {
            System.out.println("File not found!");
            System.exit(1);
        }

        //Debugging
        //reviews.forEach((key,value) -> {System.out.println(key + ": " + value);});
        //System.out.println(reviews.get("movie").occurence);
        System.out.println("\n");


        while (choice!= 5) {

            if (choice == 1) {
                System.out.print("Enter a word: ");
                String word = input.nextLine().trim().toLowerCase();
                if (reviews.containsKey(word)) {
                    System.out.println("The word " + word + " appears " + reviews.get(word).occurence + " times.");
                    System.out.println("The average score for reviews containing the word " + word + ": " + reviews.get(word).averageRating());

                } else System.out.println("No word is detected!");

            }
            else if (choice == 2) {
                System.out.print("Enter a sentence: ");
                String sentence = input.nextLine();
                String[] words = sentence.split("[\\s+]");

                double score = 0;
                int validCount = 0;
                for (int i = 0; i < words.length; i++) {
                    if (words[i].length() > 0) {
                        if (reviews.containsKey(words[i])) {
                            validCount++;
                            score+= reviews.get(words[i]).averageRating();
                        } 
                    }
                }

                if (validCount > 0)
                    System.out.println("The average score for the sentence: " + (score/validCount));
                else
                    System.out.println("No words in the sentence are detected");

            } else if (choice == 3 || choice == 4) {
                ArrayList<Rating> ratingList = new ArrayList<Rating>(reviews.values());
                String title =  "Top 5 most " + ((choice == 3)? "positive": "negative") + " words with more than one occurrence";
                
                
                
                ratingList.sort((choice == 3)? new MaxComparator(): new MinComparator());
                System.out.println(ratingList);
                
                System.out.println(title);
                for (int start = ratingList.size()- 5; start <= ratingList.size() - 1; start ++) {
                    System.out.println(ratingList.get(start));
                }             
            }

            //Get the choice again
            choice = getChoice(menu, input);
        }

        System.out.println("Thank you. Good bye!");
        input.close();
        
    }

    public static int getChoice(String menu, Scanner input) {
        System.out.println(menu); 
        int choice = -1;
        boolean validChoice = false;

        do {
            try {
                choice = input.nextInt();
                if (choice <= 5 && choice >= 1) validChoice = true;
                else System.out.print("Invalid choice. Try again: ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Try again: ");
                input.nextLine();
            }
        } while (!validChoice);
        input.nextLine();
        return choice;
    }


    public static void readData(String fileName, Map<String,Rating> reviews) throws IOException {
            Scanner input = new Scanner(new File(fileName));

            while (input.hasNextLine()) {
                String[] words = input.nextLine().toLowerCase().split("[\\s+]");
                //Rating score for each sentence
                int rate = Integer.parseInt(words[0]);
                

                for (int i = 1; i < words.length; i++) {
                    if (words[i].length() > 0) {
                        // In case the map has the key
                        if (reviews.containsKey(words[i])) {
                            reviews.get(words[i]).appearOnceMore();
                            boolean duplicate = false;
                            for (int j = i - 1; j > 1; j--) {
                                if (words[j].equals(words[i])) {
                                    duplicate = true;
                                    break;
                                }
                            }
                            if (!duplicate) {
                                reviews.get(words[i]).addScore(rate);
                                reviews.get(words[i]).appearInOneMoreSentence();
                            }

                        } else { // The map does not have the key.
                            reviews.put(words[i], new Rating(rate, 1, 1, words[i]));
                        }

                    }
                }  

            }
    }
}
