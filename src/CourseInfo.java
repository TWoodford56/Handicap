public class CourseInfo implements ICourseInfo{
    private final String courseName;
    private final int SRating;
    private final double CRating;
    private final int Par;

    public CourseInfo(String courseName, int SRating, double CRating, int Par) {
        this.courseName = courseName;
        this.SRating = SRating;
        this.CRating = CRating;
        this.Par = Par;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getSRating() {
        return SRating;
    }

    public double getCRating() {
        return CRating;
    }

    public int getPar() {
        return Par;
    }

    @Override
    public String toString(){
        return "CourseInfo{" +
                "name=" + courseName +
                ", SRating='" + SRating +
                ", CRating=" + CRating +
                ", Par=" + Par +
                '}';
    }
}
