package Clase14.Classes;

public class Contact {

    String FirstName;
    String LastName;
    String Title;
    String Phone;
    String Description;

    public Contact (String aFirstName, String aLastName, String aTitle, String aPhone, String aDescription){

        this.FirstName = aFirstName;
        this.LastName = aLastName;
        this.Title = aTitle;
        this.Phone = aPhone;
        this.Description = aDescription;

    }


    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getPhone() {
        return this.Phone;
    }

    public String getDescription() {
        return this.Description;
    }
}
