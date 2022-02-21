package Clase4.Ejercicio6;

public class Person {

    String firstName;
    String lastName;
    int age;
    String streetAddress;
    String city;
    String state;
    String postalCode;
    String mobilePhoneNumber;
    String homePhoneNumber;

    public Person( String aFirstName, String aLastName, int anAge, String aStreetAddress,
                   String aCity, String aState, String aPostalCode, String aMobilePhoneNumber, String aHomePhoneNumber){

        this.firstName = aFirstName;
        this.lastName = aLastName;
        this.age = anAge;
        this.streetAddress = aStreetAddress;
        this.city = aCity;
        this.state = aState;
        this.postalCode = aPostalCode;
        this.mobilePhoneNumber = aMobilePhoneNumber;
        this.homePhoneNumber = aHomePhoneNumber;

    }
}
