/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

/*Person is a type of UserEntry
 * all methods related to Person are setters and getters
 */
public class Person {

    String name;
    String streetAddress;
    String phoneHome;
    String phoneWork;
    String email;
    Boolean isModifiable;

    public Person() {
        name = "";
        streetAddress = "";
        phoneHome = "";
        phoneWork = "";
        email = "";
        isModifiable = true;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        String[] names = name.split(" ");
        if (name.length() < 2) {
            return "";
        }
        return names[0];
    }

    public String getMidName() {
        String[] names = name.split(" ");
        if (name.length() < 2) {
            return "";
        }
        return names[1];
    }

    public String getLastName() {
        String[] names = name.split(" ");
        if (name.length() < 2) {
            return "";
        }
        return names[2];
    }

    public String getAddress() {
        if (streetAddress.compareTo("") == 0 || streetAddress.compareTo("%%") == 0 || streetAddress.charAt(0) == '%' || streetAddress.charAt(streetAddress.length() - 1) == '%') {
            return "";
        }
        String[] names = streetAddress.split("%");
        if (names[0].length() < 1) {
            return "";
        }
        return names[0];
    }

    public String getCity() {
        if (streetAddress.compareTo("") == 0 || streetAddress.compareTo("%%") == 0 || streetAddress.charAt(0) == '%' || streetAddress.charAt(streetAddress.length() - 1) == '%') {
            return "";
        }
        String[] names = streetAddress.split("%");
        if (names[1].length() < 1) {
            return "";
        }
        return names[1];
    }

    public String getCountry() {
        if (streetAddress.compareTo("") == 0 || streetAddress.compareTo("%%") == 0 || streetAddress.charAt(0) == '%' || streetAddress.charAt(streetAddress.length() - 1) == '%') {
            return "";
        }
        String[] names = streetAddress.split("%");
        if (names[2].length() < 1) {
            return "";
        }
        return names[2];
    }

    public String getZip() {
        if (streetAddress.compareTo("") == 0 || streetAddress.compareTo("%%") == 0 || streetAddress.charAt(0) == '%' || streetAddress.charAt(streetAddress.length() - 1) == '%') {
            return "";
        }
        String[] names = streetAddress.split("%");
        if (names[3].length() < 1) {
            return "";
        }
        return names[3];
    }

    public String getStreetAddress() {
        if (streetAddress.compareTo("") == 0) {
            return "";
        }
        return streetAddress;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsModifiable() {
        return isModifiable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsModifiable(Boolean isModifiable) {
        this.isModifiable = isModifiable;
    }

    //will display the Persons information in the terminal
    // only used for testing purposes
    public void printPersonInfo() {
        System.out.println(getName());
        System.out.println(getStreetAddress());
        System.out.println(getPhoneHome());
        System.out.println(getPhoneWork());
        System.out.println(getEmail());
    }
}
