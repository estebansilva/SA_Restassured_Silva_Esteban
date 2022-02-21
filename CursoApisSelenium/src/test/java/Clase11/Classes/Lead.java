package Clase11.Classes;

public class Lead {

    String LastName;
    String Status;
    String Company;
    String Course__c;
    String Email;

    public Lead (String aLastName, String aStatus, String aCompany){

        this.LastName = aLastName;
        this.Status = aStatus;
        this.Company = aCompany;
    }

    public Lead (String aLastName, String aStatus, String aCompany, String aCourse, String aEmail){

        this.LastName = aLastName;
        this.Status = aStatus;
        this.Company = aCompany;
        this.Course__c = aCourse;
        this.Email = aEmail;
    }

}
