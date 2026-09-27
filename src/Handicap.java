import java.text.DecimalFormat;
import java.util.ArrayList;

public class Handicap {

    public double courseAdjustedScores(ArrayList<Integer> scores, ArrayList<Double> courseRating, ArrayList<Integer> slopeRating){
        double total = 0;
        for (int i = 0; i < scores.size(); i++){
            total += (((scores.get(i) - courseRating.get(i))/slopeRating.get(i)) * 113);
        }
        return total / 5;
    }

    public double playingHandicap(double handicap, double courseRating, int slopeRating, int par){
        return (handicap * (slopeRating/113.0)) + (courseRating - par);
    }

    public String handicapGetter(double avg){
        DecimalFormat df = new DecimalFormat("#.#");
        if (avg < 0) {
            double abs = Math.abs(avg);
            return "+"+df.format(abs);
        }
        else{
            return df.format(avg);
        }
    }

}