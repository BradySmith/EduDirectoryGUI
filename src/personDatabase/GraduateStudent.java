/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

/*Graduate Student is a type of UserEntry with all the properties 
 * of a UndergradStudent and a Person as well as thesisSupervisor,
 * thesis, scholarshipAmount, degreeType
 * all methods related to GraduateStudent are setters and getters
 */
public class GraduateStudent extends UndergradStudent {

    String thesisSupervisor;
    Thesis thesis;
    String scholarshipAmount;
    String degreeType;

    public GraduateStudent() {
        thesisSupervisor = "";
        thesis = new Thesis();
        scholarshipAmount = "";
        degreeType = "";
    }

    public String getThesisSupervisor() {
        return thesisSupervisor;
    }

    public String getThesisTitle() {
        return thesis.getTitle();
    }

    public String getThesisArea() {
        return thesis.getArea();
    }

    public String getScholarshipAmount() {
        return scholarshipAmount;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setThesisSupervisor(String thesisSupervisor) {
        this.thesisSupervisor = thesisSupervisor;
    }

    public void setThesisTitle(String t) {
        this.thesis.setTitle(t);
    }

    public void setThesisArea(String t) {
        this.thesis.setArea(t);
    }

    public void setScholarshipAmount(String scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    //will display the graduate students information in the terminal
    // only used for testing purposes
    public void printGradInfo() {
        System.out.println(getName());
        System.out.println(getStreetAddress());
        System.out.println(getPhoneHome());
        System.out.println(getPhoneWork());
        System.out.println(getEmail());
        System.out.println(getYearReg());
        System.out.println(getDegreeProgram());
        System.out.println(getYearInProgram());
        System.out.println(getGPA());
        System.out.println(getTotalCredit());
        System.out.println(getThesisTitle());
        System.out.println(getThesisArea());
        System.out.println(getThesisSupervisor());
        System.out.println(getDegreeType());
        System.out.println(getScholarshipAmount());
    }
}
