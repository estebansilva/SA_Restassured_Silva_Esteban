package Clase13.Clases;

public class Lead {

    String LastName;
    String Company;

    public Lead (String aName, String aCompany){

        this.LastName = aName;
        this.Company = aCompany;

    }

    public String toString(){

        return ("Lead Name: " + this.LastName + " - " + "Lead Company: " + this.Company);

    }
}
