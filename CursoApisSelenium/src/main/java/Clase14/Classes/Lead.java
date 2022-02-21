package Clase14.Classes;

public class Lead {


    private String LastName;
    private String Company;
    private String Course__c;

    //Constructor
    public Lead (String aName, String aCompany, String aCourse){

        this.LastName = aName;
        this.Company = aCompany;
        this.Course__c = aCourse;

    }
    //Constructor
    public Lead(String aName, String aCompany) {

        this.LastName = aName;
        this.Company = aCompany;
    }

    public String getLastName() {
        return LastName;
    }

    public String getCompany() {
        return Company;
    }

    public String getCourse__c() {
        return Course__c;
    }
}
