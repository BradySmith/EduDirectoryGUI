/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */

package personDatabase;

import java.io.*;

public class DataWriterReader {
    /* Method: appendDataBase
     * Pre: a UserEntry
     * Post: UserEntry is added to a file
     * Returns: nothing
     */

    public void appendDataBase(UserEntry p) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("./personDatabase.txt", true));

            // Writes Database Number First
            writer.println("#" + p.getDataNum());

            // Write Lock Status
            writer.println(p.getLock());

            // Write User Type
            writer.println(p.getUserType());

            // Writes Person Data
            writer.println(p.getName());
            writer.println(p.getStreetAddress());
            writer.println(p.getPhoneHome());
            writer.println(p.getPhoneWork());
            writer.println(p.getEmail());

            // Write Undergraduate Info
            writer.println(p.getYearReg());
            writer.println(p.getDegreeProgram());
            writer.println(p.getYearInProgram());
            writer.println(p.getGPA());
            writer.println(p.getTotalCredit());

            // Write GradStud Info
            writer.println(p.getThesisSupervisor());
            writer.println(p.getThesisTitle());
            writer.println(p.getThesisArea());
            writer.println(p.getScholarshipAmount());
            writer.println(p.getDegreeType());


        } catch (IOException expt) {
            System.err.println(expt);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /* Method: overWriteUser
     * Pre: a UserEnrty
     * Post: replaces a UserEntry with new info. saves changes to a UserEntry
     * Returns: nothing
     */
    public void overWriteUser(UserEntry p) {
        PrintWriter writer = null;
        File tempFile = new File("./tempPersonDatabase.txt");
        File originalFile = new File("./personDatabase.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            writer = new PrintWriter(new FileWriter(tempFile, true));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.compareTo("#" + p.getDataNum()) == 0) {
                    // Writes Database Number First
                    writer.println("#" + p.getDataNum());

                    // Write Lock Status
                    writer.println(p.getLock());

                    // Write User Type
                    writer.println(p.getUserType());

                    // Writes Person Data
                    writer.println(p.getName());
                    writer.println(p.getStreetAddress());
                    writer.println(p.getPhoneHome());
                    writer.println(p.getPhoneWork());
                    writer.println(p.getEmail());

                    // Write Undergraduate Info
                    writer.println(p.getYearReg());
                    writer.println(p.getDegreeProgram());
                    writer.println(p.getYearInProgram());
                    writer.println(p.getGPA());
                    writer.println(p.getTotalCredit());

                    // Write GradStud Info
                    writer.println(p.getThesisSupervisor());
                    writer.println(p.getThesisTitle());
                    writer.println(p.getThesisArea());
                    writer.println(p.getScholarshipAmount());
                    writer.println(p.getDegreeType());

                    for (int i = 0; i < 17; i++) {
                        tempString = reader.readLine();
                    }

                } else {
                    writer.println(tempString);
                }
            }
            reader.close();
            writer.close();
        } catch (IOException expt) {
            System.err.println(expt);
        } finally {
            originalFile.delete();
            tempFile.renameTo(originalFile);
        }
    }

    /* Method: deleteUser
     * Pre: a UserEntry
     * Post: removes the UserEntry from a file
     * Returns: nothing
     */
    public void deleteUser(UserEntry p) throws FileNotFoundException {
        PrintWriter writer = null;
        File tempFile = new File("tempPersonDatabase.txt");
        File originalFile = new File("personDatabase.txt");
        tempFile.setWritable(true);
        originalFile.setWritable(true);
        BufferedReader reader = new BufferedReader(new FileReader(originalFile));
        try {
            writer = new PrintWriter(new FileWriter(tempFile, true));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.compareTo("#" + p.getDataNum()) == 0) {
                    for (int i = 0; i < 17; i++) {
                        tempString = reader.readLine();
                    }
                } else {
                    writer.println(tempString);
                }
            }
            reader.close();
            writer.close();
        } catch (IOException expt) {
            System.err.println(expt);
        } finally {
            originalFile.delete();
            tempFile.renameTo(originalFile);
        }
    }

    /* Method: swapTemporaryFiles
     * Pre: none
     * Post: the temp file that is used when writing is renamed back to the original
     * file. this way we only have one file. but if the program crashed during a write
     * we will not lose any information
     * Returns: nothing
     */
    public void swapTemporaryFiles() {
        File tempFile = new File("tempPersonDatabase.txt");
        File originalFile = new File("personDatabase.txt");
        tempFile.renameTo(originalFile);
    }

    /* Method: grabAll
     * Pre: a LinkedList for storing UserEntrys
     *      a int that is how many UserEntrys are in the file
     * Post: all UserEntrys are added to the linkedList
     * Returns: nothing
     */
    public void grabAll(LinkedList allPeople, int peopleInFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./personDatabase.txt"));
            String tempString;
            for (int i = 0; i < peopleInFile; i++) {
                UserEntry tempUser = new UserEntry();
                tempString = reader.readLine();
                String[] stringParts = tempString.split("#");
                tempUser.setDataNum(Integer.parseInt(stringParts[1]));
                tempString = reader.readLine();
                tempUser.setLocked(tempString);
                tempString = reader.readLine();
                tempUser.setUserType(tempString);
                tempString = reader.readLine();
                tempUser.setName(tempString);
                tempString = reader.readLine();
                tempUser.setStreetAddress(tempString);
                tempString = reader.readLine();
                tempUser.setPhoneHome(tempString);
                tempString = reader.readLine();
                tempUser.setPhoneWork(tempString);
                tempString = reader.readLine();
                tempUser.setEmail(tempString);
                tempString = reader.readLine();
                tempUser.setYearReg(tempString);
                tempString = reader.readLine();
                tempUser.setDegreeProgram(tempString);
                tempString = reader.readLine();
                tempUser.setYearInProgram(tempString);
                tempString = reader.readLine();
                tempUser.setGPA(tempString);
                tempString = reader.readLine();
                tempUser.setTotalCredit(tempString);
                tempString = reader.readLine();
                tempUser.setThesisSupervisor(tempString);
                tempString = reader.readLine();
                tempUser.setThesisTitle(tempString);
                tempString = reader.readLine();
                tempUser.setThesisArea(tempString);
                tempString = reader.readLine();
                tempUser.setScholarshipAmount(tempString);
                tempString = reader.readLine();
                tempUser.setDegreeType(tempString);

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

                allPeople.insertInfo(p);
            }
            reader.close();
        } catch (IOException expt) {
            System.err.println(expt);
        }
    }

    /* Method: dataBaseReader
     * Pre: a UserEntry that will contain the info of a User if found in a file
     *      a int that is the database number of a user that you are looking for
     * Post: nothing changes
     * Returns: a UserEntry with the appropriate fields filled in
     */
    public UserEntry dataBaseReader(UserEntry p, int i) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./personDatabase.txt"));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.compareTo("#" + i) == 0) {
                    String[] stringParts = tempString.split("#");
                    p.setDataNum(Integer.parseInt(stringParts[1]));
                    tempString = reader.readLine();
                    for (int x = 0; x < 18; x++) {
                        if (x == 0) {
                            p.setLocked(tempString);
                        }
                        if (x == 1) {

                            p.setUserType(tempString);
                        }
                        if (x == 2) {

                            p.setName(tempString);
                        }
                        if (x == 3) {
                            p.setStreetAddress(tempString);
                        }
                        if (x == 4) {

                            p.setPhoneHome(tempString);
                        }
                        if (x == 5) {

                            p.setPhoneWork(tempString);
                        }
                        if (x == 6) {

                            p.setEmail(tempString);
                        }
                        if (x == 7) {

                            p.setYearReg(tempString);
                        }
                        if (x == 8) {

                            p.setDegreeProgram(tempString);
                        }
                        if (x == 9) {

                            p.setYearInProgram(tempString);
                        }
                        if (x == 10) {

                            p.setGPA(tempString);
                        }
                        if (x == 11) {

                            p.setTotalCredit(tempString);
                        }
                        if (x == 12) {

                            p.setThesisSupervisor(tempString);
                        }
                        if (x == 13) {

                            p.setThesisTitle(tempString);
                        }
                        if (x == 14) {

                            p.setThesisArea(tempString);
                        }
                        if (x == 15) {

                            p.setScholarshipAmount(tempString);
                        }
                        if (x == 16) {

                            p.setDegreeType(tempString);
                        }
                        tempString = reader.readLine();
                    }
                }
            }
            reader.close();
        } catch (IOException expt) {
            System.err.println(expt);
        }
        return p;
    }
 
    /* Method: getNextNum
     * Pre: none
     * Post: nothing changes
     * Returns: a integer that is what the next data base number will be
     */
    public int getNextNum() {
        try {
            String lastNumber = null;
            int lastNum;
            BufferedReader reader = new BufferedReader(new FileReader("./personDatabase.txt"));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("#")) {
                    lastNumber = tempString;
                }
            }
            String[] stringParts = lastNumber.split("#");
            lastNum = Integer.parseInt(stringParts[1]);
            reader.close();
            return lastNum + 1;
        } catch (IOException expt) {
            System.err.println(expt);
            return -1;
        }
    }

    /* Method: peopleInFile
     * Pre: none
     * Post: changes nothing
     * Returns: a integer that says how many UserEntrys are in the file
     */
    public static int peopleInFile() throws IOException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(new File("./personDatabase.txt")));
            while ((reader.readLine()) != null);
            return (reader.getLineNumber() / 18);
        } catch (Exception ex) {
            return -1;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
