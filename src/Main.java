import java.util.List;
import java.util.Scanner;

public class Main {

    public static void calcHandicap() {
        UserInfo UI = new UserInfo();
        Database DB = new Database();
        Handicap hc = new Handicap();
        System.out.println("You will be prompted to enter your last 5 rounds with the following information Course Name, Tees, Male or Female, Score");
        for(int i = 0 ; i < 5 ; i++) {
            UI.UsersRoundInfo(i);
        }
        DB.gatherCourseInfo(UI);
        double h = hc.courseAdjustedScores(UI.scores,DB.CRatings,DB.SRatings);
        String hh = hc.handicapGetter(h);
        System.out.println(hh);
    }

    public static void calcCourseHandicap(){
        Handicap hc = new Handicap();
        UserInfo UI = new UserInfo();
        Database DB = new Database();
        LevDistance ld = new LevDistance();
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your handicap?");
        double handicap;
        while(true) {
            try {
                handicap = sc.nextDouble();
                sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid handicap");
                sc.nextLine();
            }
        }
        System.out.println("What course are you playing?");
        String course = sc.nextLine();
        if(!DB.checkIfCourseExists(course)){
            if(!ld.autoCorrect(course)) {
                course = displayCourse(course);
            }
        }
        System.out.println("What Tees are you playing(name and mens or womens)?");
        String tees = null;
        boolean mens = false;
        while (true) {
            String tees1 = sc.nextLine();
            String[] tees2 = tees1.split(",");
            if (tees2.length != 2) {
                System.out.println("Please enter the tees in the format: Name,Mens or Name,Womens");
                continue;
            }
            tees = tees2[0];
            mens = tees2[1].equalsIgnoreCase("mens");
            break;
        }
        if(!DB.checkIfTeesExists(course,tees,mens)){
            System.out.println("Invalid input please try inputting the correct tees");
        }
        CourseInfo ci = UI.getOneRoundInfo(course,tees,mens);
        double phc = hc.playingHandicap(handicap,ci.getCRating(), ci.getSRating(), ci.getPar());
        String pphc = hc.handicapGetter(phc);
        System.out.println("You're course adjusted handicap is " + pphc);
        sc.close();
    }

    public static void menuScreen(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Menu:
                1. Calculate Handicap
                2. Calculate Course Handicap"""
        );
        String choice = sc.nextLine();
        if(choice.equalsIgnoreCase("1")) {
            calcHandicap();
        } else if(choice.equalsIgnoreCase("2")) {
            calcCourseHandicap();
        } else {
            menuScreen();
        }
    }



    public static String displayCourse(String course){
        Scanner sc = new Scanner(System.in);
        LevDistance ld = new LevDistance();
        List<CourseLDistancePair> list = ld.similarCourses(course);
        int options = Math.min(3, list.size());
        if (options == 0) {
            System.out.println("No similar courses were found.");
            return course;
        }
        System.out.println("Here are the " + options + " most similar courses: \n");
        for (int i = 1; i <= options; i++) {
            System.out.println("Course " + i + " = " + list.get(i-1).name());
        }
        System.out.println("Please input the number of your desired course");
        while(true) {
            int ans = sc.nextInt();
            if (ans >= 1 && ans <= options) {
                course = list.get(ans - 1).name();
                break;
            } else {
                System.out.println("invalid selection, please select again");
            }
        }
        return course;
    }

    public static void main(String[] args) {
        menuScreen();
    }
}
