
public class TeacherStruct extends UserStruct
{
    private String subjectTaught;
    private char idCharIdentifier = 'T';
    private String recordsFilePath = "TextFiles\\TeacherRecords.txt";
    
    public TeacherStruct(String userID, String firstName, String lastName, String subjectTaught, String dateOfBirth){
        this.setUserID(userID); 
        this.setFirstName(firstName); 
        this.setLastName(lastName); 
        this.setSubject(subjectTaught);
        this.setDateOfBirth(dateOfBirth);
    }
    
    public void setSubject(String subjectTaught){
        this.subjectTaught = subjectTaught;
    }
    
    public String getSubject(){
        return this.subjectTaught;
    }
    
    @Override public String toString(){
        return this.getUserID() + "," + this.getFirstName() + "," + this.getLastName() + "," + this.getSubject() +"," + this.getDateOfBirth();
    }
}
