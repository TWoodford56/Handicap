import java.util.ArrayList;
import java.util.Scanner;

public class UserInfo {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> tees = new ArrayList<>();
    ArrayList<Boolean> TF = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();

    public void UsersRoundInfo(int i ) {
        LevDistance ld = new LevDistance();
        Scanner in = new Scanner(System.in);
        Database db = new Database();
        int j = i + 1;
        boolean valid = false;

        while(!valid) {
            System.out.println("Enter round " + j + ":");
            String lastRound = in.nextLine();
            String[] r1 = lastRound.split(",");
            in.nextLine();
            if (r1.length != 4) {
                System.out.println("Please enter the details in the format: Course,Tees,Mens or Womens,Score and make sure to include all elements");
                continue;
            }
            boolean r3;
            r3 = r1[2].equalsIgnoreCase("mens");
            if (!db.checkIfCourseExists(r1[0]) || !db.checkIfTeesExists(r1[0], r1[1], r3)) {
                if(!db.checkIfCourseExists(r1[0])) {
                    ld.autoCorrect(r1[0]);
                }
                if (!db.checkIfTeesExists(r1[0], r1[1], r3)) {
                    System.out.println("Could not find tees in course");
                }
            } else {
                System.out.println("details for your round" + j + ": Course - " + r1[0] + ",Tees - " + r1[1] + ", Mens - " + r1[2] + ", Score - " + r1[3]);
                System.out.println("Is this correct? y/n");
                String answer = in.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    names.add(r1[0]);
                    tees.add(r1[1]);
                    if (r1[2].equalsIgnoreCase("mens")) {
                        TF.add(true);
                    } else {
                        TF.add(false);
                    }
                    scores.add(Integer.valueOf(r1[3]));
                    valid = true;
                } else {
                    UsersRoundInfo(i);
                }
            }
        }
    }

    public CourseInfo getOneRoundInfo(String course, String tees, Boolean mens){
        Database db = new Database();
        int p = db.ParFinder(course, tees, mens);
        double cr = db.courseRatingFinder(course,tees,mens);
        int sr = db.SlopeRatingFinder(course,tees,mens);
        return new CourseInfo(course,sr,cr,p);

    }
}
