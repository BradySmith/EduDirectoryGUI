/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */

package personDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Controller {

    final static Controller controller = new Controller();
    static PersonDatabaseGUI gui = new PersonDatabaseGUI(controller);
    DataWriterReader file = new DataWriterReader();
    boolean saveLocked = false;
    String activeEdits = "";

    /* Method : extractUserFromList
     * Pre: selectedRow is a int that refers to the User that was selceted from the GUI list
     *      resultsList is a LinkedList in the GUI that represents all Users
     * Post: nothing is changed
     * Returns: a User
     */
    protected UserEntry extractUserFromList(int selectedRow, LinkedList resultsList) {
        for (int i = 0; i < selectedRow; i++) {
            resultsList = resultsList.nextList;
        }
        return resultsList.user;
    }

    /* Method : saveUserInfo
     * Pre: all the properties that belong to a Graduate Student
     * Post: the Users information is saved to a text file. as in changes to the properties will appear
     *       in the text file
     * Return: nothing
     */
    public void saveUserInfo(String userType, String name, String address, String workPhone, String homePhone, String email,
            String yearRegIn, String degreeProg, String yearInProg, String GPA, String totalCredit, String thesisTitle, String thesisArea,
            String thesisSupervisor, String scholarshipAmount, String degreeType, String dataNum) {

        // Create a temporary user to hold all the data
        UserEntry tempUser = new UserEntry();
        tempUser.setName(name);
        tempUser.setStreetAddress(address);
        tempUser.setPhoneWork(workPhone);
        tempUser.setPhoneHome(homePhone);
        tempUser.setEmail(email);
        tempUser.setYearReg(yearRegIn);
        tempUser.setDegreeProgram(degreeProg);
        tempUser.setYearInProgram(yearInProg);
        tempUser.setGPA(GPA);
        tempUser.setTotalCredit(totalCredit);
        tempUser.setThesisTitle(thesisTitle);
        tempUser.setThesisArea(thesisArea);
        tempUser.setThesisSupervisor(thesisSupervisor);
        tempUser.setScholarshipAmount(scholarshipAmount);
        tempUser.setDegreeType(degreeType);
        tempUser.setUserType(userType);
        tempUser.setDataNum(Integer.parseInt(dataNum));

        if (saveLocked) {
            gui.showError("Cannot save changes! Save lock is set!");
            System.out.println();
            return;
        }

        // Check to see if this is a new user
        if (tempUser.getDataNum() >= file.getNextNum()) {
            setSaveLocked(true);
            file.appendDataBase(tempUser);
            setSaveLocked(false);
        } // Not a new user so overwrite the existing user
        else {
            setSaveLocked(true);
            file.overWriteUser(tempUser);
            setSaveLocked(false);
        }

    }

    /* Method : tryAgainstLock
     * Pre: a UserEntry to check locked status
     * Post: if locked nothing, if unlocked enter the editMenu GUI for that user
     * Return: nothing
     */
    public void tryAgainstLock(UserEntry p) throws ParseException {
        if (activeEdits.contains("#" + p.getDataNum() + ",")) {
            gui.showError("That entry is already open in another window. Close that entry before you open another one.");
        } else {
            activeEdits = (activeEdits + "#" + p.getDataNum() + ",");
            gui.editMenu(p);
        }
    }

    /* Method : removeLock
     * Pre: a integer that represts a UserEntry in the database
     * Post: the user becomes unlocked
     * Return: nothing
     */
    public void removeLock(int num) {
        activeEdits = activeEdits.replace("#" + num + ",", "");
        System.out.println(activeEdits);
    }

    /* Method :writeNewUser
     * Pre: a UserEntry that is to be written to a file
     * Post: User is written to a file
     * Return: nothing
     */
    public void writeNewUser(UserEntry p) {
        file.appendDataBase(p);
    }

    /* Method :deleteUser
     * Pre: a UserEntry that is to be removed from a file
     * Post: UserEntry is removed
     * Return: nothing
     */
    public void deleteUser(UserEntry p) throws FileNotFoundException {
        file.deleteUser(p);
        file.swapTemporaryFiles();
    }

    /* Method :searchDataList
     * Pre: target is a String that is a Name or part of a Name of a UserEntry
     * that you would like to find
     *      searchResults is a linkedList that stores all the matched to target
     * Post: all matches are added to searchResults
     * Returns: nothing
     */
    public void searchDataList(String target, LinkedList searchResults) throws IOException {
        int maxFileSize;
        maxFileSize = file.getNextNum();
        UserEntry tempUser = new UserEntry();
        for (int numFound = 1; numFound < maxFileSize; numFound++) {
            tempUser = file.dataBaseReader(tempUser, numFound);
            if (tempUser.getName().contains(target)) {
                UserEntry p = new UserEntry();
                p.setDataNum(tempUser.getDataNum());
                p.setUserType(tempUser.getUserType());
                p.setName(tempUser.getName());
                p.setStreetAddress(tempUser.getStreetAddress());
                p.setPhoneHome(tempUser.getPhoneHome());
                p.setPhoneWork(tempUser.getPhoneWork());
                p.setEmail(tempUser.getEmail());
                p.setYearReg(tempUser.getYearReg());
                p.setDegreeProgram(tempUser.getDegreeProgram());
                p.setYearInProgram(tempUser.getYearInProgram());
                p.setGPA(tempUser.getGPA());
                p.setTotalCredit(tempUser.getTotalCredit());
                p.setThesisSupervisor(tempUser.getThesisSupervisor());
                p.setThesisTitle(tempUser.getThesisTitle());
                p.setThesisArea(tempUser.getThesisArea());
                p.setScholarshipAmount(tempUser.getScholarshipAmount());
                p.setDegreeType(tempUser.getDegreeType());
                searchResults.insertInfo(p);
                tempUser.setName("");
            }
        }
    }

    /* Method :getAllUsers
     * Pre: searchResults is a linkedList that stores all the UserEntrys that exist
     * Post: all UserEntrys are added to searchResults
     * Returns: nothing
     */
    @SuppressWarnings("static-access")
    public void getAllUsers(LinkedList searchResults) throws FileNotFoundException {
        int maxFileSize;
        try {
            maxFileSize = file.peopleInFile();
            file.grabAll(searchResults, maxFileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getSaveLocked() {
        return saveLocked;
    }

    public void setSaveLocked(boolean s) {
        saveLocked = s;
    }

    public static void main(String[] args) {
    }
}