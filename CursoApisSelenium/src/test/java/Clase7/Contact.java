package Clase7;

public class Contact {

    String LastName;
    String FirstName;
    String Phone;
    String Email;
    String Title;
    String Description;


    public Contact(String aLastName, String aFirstName, String aPhone, String aEmail, String aTitle, String adescription){

        this.LastName = aLastName;
        this.FirstName = aFirstName;
        this.Phone = aPhone;
        this.Email = aEmail;
        this.Title = aTitle;
        this.Description = adescription;

    }

    public String getContact_LastName(){
        return LastName;
    }

    public String getContact_FirstName(){
        return FirstName;
    }

    public String getContact_Phone(){
        return Phone;
    }

    public String getContact_Email(){
        return Email;
    }

    public String getContact_Title(){
        return Title;
    }

    public String getContact_description(){
        return Description;
    }



}
