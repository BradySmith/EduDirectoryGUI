/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */

// This is a modified LinkedList file which works for UserEntries

package personDatabase;

public class LinkedList {
    // this class implements the standard abstract data type linked list as an object

    UserEntry user;
    LinkedList nextList;
    boolean amEmpty;

    public LinkedList() {
        user = null;
        nextList = null;
        amEmpty = true;
    }

    /* Method: isEmpty
     * Pre: none
     * Post: nothing changes
     * Returns: true if list is empty, else false
     */
    public boolean isEmpty() {
        return (amEmpty);
    }

    /* Method: insertInfo
     * Pre: a UserEntry
     * Post: will insert the userEntry into the Linked list
     * Returns: nothing
     */
    public void insertInfo(UserEntry inUser) {
        if (this.amEmpty) {
            LinkedList tempLList = new LinkedList();
            // insert info here and add a new empty at end
            user = inUser;
            nextList = tempLList;
            amEmpty = false;
        } else if (inUser.getName().compareTo(user.getName()) < 0) {
            UserEntry tempUser = user;
            user = inUser;
            this.nextList.insertInfo(tempUser);
        } else if (inUser.getName().compareTo(user.getName()) >= 0) {
            this.nextList.insertInfo(inUser);
        }
    }

    /* Method: deleteUser
     * Pre: a int that is the database Number of a UserEntry
     * Post: that UserEntry is removed from the LinkedList
     * Returns: nothing
     */
    public void deleteUser(int outUserNumber) {
        if (this.amEmpty) {
            return;
        }

        // check if this is the info to delete
        if (this.user.getDataNum() != outUserNumber) {
            //                System.out.println("Didn't match so looking further down list");
            this.nextList.deleteUser(outUserNumber);
            return;
        }
        // found it so delete it
        if (this.nextList.isEmpty()) {
            this.nextList = null;
            this.user = null;
            amEmpty = true;
            //                System.out.println("Deleting from last element");
        } else {
            this.user = this.nextList.user; // copy next info to current
            this.nextList.deleteUser(this.user.getDataNum());
            //                System.out.println("Shifted info forward and deleting down rest of list");

        }
    }

    /* Method: isNumberInList
     * Pre: a integer that is the data number
     * Post: nothing changes
     * Returns: true if the datanumber is a number of a user somewhere in the list
     */
    public boolean isNumberInList(int i) {
        if (amEmpty) {
            System.out.println("Am empty");
            return false;
        }
        if (this.user.getDataNum() == i) {
            System.out.println("Number matches");
            return true;
        }
        System.out.println("Got to then end, not in the list");
        return false;
    }
}