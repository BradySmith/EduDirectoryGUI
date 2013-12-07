/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

/*
 * UserEntry is a class that represents all possible user types (Person,
 * UndergradStudent or GraduateStudent). the reason for the class is so we can
 * pass just this class when dealing with any user and it will act as any one
 * of the types. UserEntry contains all the classes that GraduateStudent
 * contains as well as dataNum, which is unique integer that is attached
 * to each entry, lock is used so the same user cant be modified twice at the 
 * same time, userType is either Person, UndergradStudent or GraduateStudent
 * all methds for UserEntry are setters and getters
 */
public class UserEntry extends GraduateStudent {

    int dataNum;
    String lock;
    String userType;

    public UserEntry() {
        dataNum = -1;
        lock = "unlocked";
        userType = "";
    }

    public String getUserInfo() {
        return name + "\n" + streetAddress + "\n" + phoneHome + "\n" + phoneWork + "\n" + email;
    }

    public String getLock() {
        return lock;
    }

    public String getUserType() {
        return userType;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setLocked(String s) {
        if (s.compareTo("locked") == 0) {
            lock = "locked";
        } else {
            lock = "unlocked";
        }
    }

    public void setDataNum(int i) {
        this.dataNum = i;
    }

    public void setUserType(String p) {
        this.userType = p;
    }
}
