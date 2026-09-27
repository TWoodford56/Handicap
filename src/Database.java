import java.sql.*;
import java.util.ArrayList;

public class Database {
    ArrayList<Double> CRatings = new ArrayList<>();
    ArrayList<Integer> SRatings = new ArrayList<>();

    static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("HANDICAP_DB_URL", "jdbc:mysql://localhost:3306/course_info");
        String user = System.getenv("HANDICAP_DB_USER");
        String password = System.getenv("HANDICAP_DB_PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }

    public double courseRatingFinder(String courseName, String tees, Boolean mens){
        double rating = 0.0; // Default value if not found

        String sql = "SELECT course_rating FROM course_info.courseinfo WHERE course_name LIKE ? AND Tees LIKE ? AND Mens = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            preparedStatement.setString(1, "%" + courseName + "%");
            preparedStatement.setString(2, "%" + tees + "%");
            preparedStatement.setBoolean(3, mens);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rating = resultSet.getDouble("course_rating");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

    public int SlopeRatingFinder(String courseName, String tees, Boolean mens){
        int rating = 0; // Default value if not found

        String sql = "SELECT slope_rating FROM course_info.courseinfo WHERE course_name LIKE ? AND Tees LIKE ? AND Mens = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            preparedStatement.setString(1, "%" + courseName + "%");
            preparedStatement.setString(2, "%" + tees + "%");
            preparedStatement.setBoolean(3, mens);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rating = resultSet.getInt("slope_rating");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

    public void gatherCourseInfo(UserInfo UI){
        for (int i = 0; i < 5; i++) {
                double CR = courseRatingFinder(UI.names.get(i), UI.tees.get(i), UI.TF.get(i));
                int SR = SlopeRatingFinder(UI.names.get(i), UI.tees.get(i), UI.TF.get(i));
                SRatings.add(SR);
                CRatings.add(CR);
        }
    }

    public int ParFinder(String courseName, String tees, Boolean mens){
        int rating = 0; // Default value if not found

        String sql = "SELECT par FROM course_info.courseinfo WHERE course_name LIKE ? AND Tees LIKE ? AND Mens = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            preparedStatement.setString(1, "%" + courseName + "%");
            preparedStatement.setString(2, "%" + tees + "%");
            preparedStatement.setBoolean(3, mens);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rating = resultSet.getInt("par");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

    public boolean checkIfCourseExists(String courseName){
        boolean exists = false;

        String sql = "SELECT course_name FROM course_info.courseinfo WHERE course_name LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            preparedStatement.setString(1, "%" + courseName + "%");

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean checkIfTeesExists(String courseName, String tees, Boolean mens){
        boolean exists = false;

        String sql = "SELECT Tees FROM course_info.courseinfo WHERE course_name LIKE ? AND Tees LIKE ? AND Mens LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            preparedStatement.setString(1, "%" + courseName + "%");
            preparedStatement.setString(2, "%" + tees + "%");
            preparedStatement.setBoolean(3, mens);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}
