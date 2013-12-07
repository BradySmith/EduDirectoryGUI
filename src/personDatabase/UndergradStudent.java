/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

/*UndergradStudent is a type of UserEntry with all the properties 
 * of a Person and a Person as well as yearReg,
 * degreeProgram, yearInProgram, GPA, totalCredit
 * all methods related to UndergradStudent are setters and getters
 */
public class UndergradStudent extends Person {

    String yearReg;         
    String degreeProgram;   
    String yearInProgram;
    String GPA;
    String totalCredit;

    public UndergradStudent() {
        yearReg = "";
        degreeProgram = "";
        yearInProgram = "";
        GPA = "";
        totalCredit = "";
    }

    public String getYearReg() {
        return yearReg;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public String getYearInProgram() {
        return yearInProgram;
    }

    public String getGPA() {
        return GPA;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setYearReg(String yearReg) {
        this.yearReg = yearReg;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public void setYearInProgram(String yearInProgram) {
        this.yearInProgram = yearInProgram;
    }

    public void setGPA(String gPA) {
        GPA = gPA;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    //will display the graduate students information in the terminal
    // only used for testing purposes
    public void printUnderGradInfo() {
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

    }
}
