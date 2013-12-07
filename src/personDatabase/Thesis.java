/*
 * Authors      Nsid    ID
 * Max Gooding, mag501, 11087688
 * Brady Smith, bas453, 11135973
 */
package personDatabase;

//Used by GraduateStudent
// all methods related to Thesis are setters and getters
public class Thesis {

    String title;
    String area;

    public Thesis() {
        title = "";
        area = "";
    }

    public String getTitle() {
        return title;
    }

    public String getArea() {
        return area;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
