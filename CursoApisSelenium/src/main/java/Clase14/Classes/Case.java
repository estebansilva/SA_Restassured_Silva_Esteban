package Clase14.Classes;

public class Case {
    String Status;
    String Type;
    String Origin; //Case Origin
    String Subject;
    String Description;
    String Comments; //Internal Comments

    //Constructor
    public Case (String aStatus, String aType, String aOrigin, String aSubject, String aDescription, String Comment){

        this.Status = aStatus;
        this.Type = aType;
        this.Origin = aOrigin;
        this.Subject = aSubject;
        this.Description = aDescription;
        this.Comments = Comment;

    }


    public String getStatus() {
        return Status;
    }

    public String getType() {
        return Type;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getSubject() {
        return Subject;
    }

    public String getDescription() {
        return Description;
    }

    public String getComments() {
        return Comments;
    }




}
