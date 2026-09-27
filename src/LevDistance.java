import java.sql.*;
import java.util.*;

public class LevDistance {
    List<String> list = new ArrayList<>();
    List<String> courses = new ArrayList<>();

    public int lDistance(String x, String y){
        if(x.isEmpty() && y.isEmpty()){
            return 0;
        } else if(x.isEmpty()){
            return y.length();
        } else if(y.isEmpty()){
            return x.length();
        }
        return distance(x, y);
    }

    public int distance(String x, String y){
        int xl = x.length();
        int yl = y.length();
        String xlower = x.toLowerCase();
        String ylower = y.toLowerCase();
        int min = Math.min(xl, yl);
        int max = Math.max(xl, yl);
        int diff = max-min;
        int counter = 0;
        for(int i = 0; i < min; i++){
            if(xlower.charAt(i) != ylower.charAt(i)){
                counter++;
            }
        }
        return counter + diff;
    }

    public List<String> uniqueCourses(){
        String sql = "SELECT DISTINCT course_name FROM course_info.courseinfo";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             try(ResultSet resultSet = preparedStatement.executeQuery()){
                 while(resultSet.next()){
                     courses.add(resultSet.getString("course_name"));
                     list.add(resultSet.getString("course_name").replaceAll("[\\\\s.]+", ""));
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CourseLDistancePair> similarCourses(String course){
        uniqueCourses();
        List<CourseLDistancePair> similar = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            similar.add(new CourseLDistancePair(lDistance(list.get(i), course),courses.get(i)));
        }
        similar.sort(Comparator.comparingInt(CourseLDistancePair::lDistance));
        return similar;
    }

    public boolean autoCorrect(String course){
        List<CourseLDistancePair> similar = similarCourses(course);
        if (similar.isEmpty()) {
            System.out.println("We could not find " + course + " and no similar courses are available.");
            return false;
        }
        Scanner sc = new Scanner(System.in);
        String correct = similar.getFirst().name();
        System.out.println("We could not find " + course + "\nDid you mean " + correct + "?");
        String ans = sc.nextLine();
        return ans.equals(correct);
    }
}


