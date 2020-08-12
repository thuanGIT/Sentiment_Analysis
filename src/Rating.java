import java.util.Comparator;

public class Rating {
    public int totalScore, occurence, sentenceOcc;
    public String word;

    public Rating(int rate, int occurence, int sentenceOcc, String word) {
        this.totalScore = rate;
        this.occurence = occurence;
        this.sentenceOcc = sentenceOcc;
        this.word = word;
    }
    public void addScore(int rate) {totalScore += rate;}
    public void appearOnceMore() {occurence++;}
    public void appearInOneMoreSentence() {sentenceOcc++;}
    public double averageRating() {return totalScore*1.0/sentenceOcc;}

    @Override
    public String toString() {
        return String.format("\nWord: %s\tRating: %.2f\tTotal occurrence: %d", word, averageRating(),occurence);

    }
    
}



class RatingComparator implements Comparator<Rating> {

    @Override
    public int compare(Rating o1, Rating o2) {
        if (o1.averageRating() > o2.averageRating())
            return 1;
        else if (o1.averageRating() < o2.averageRating())
            return -1;
        else
            return 0;
    }

}