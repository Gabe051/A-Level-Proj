
public class AdminStruct extends UserStruct
{
    private char idCharIdentifier = 'A';
    private String recordsFilePath = "TextFiles\\AdminRecords.txt";
    private String tempVar = "";
    
    public AdminStruct(String userID, String firstName, String lastName, String temp){
        this.setUserID(userID); this.setFirstName(firstName); this.setLastName(lastName); this.setTempVar(temp);
    }
    
    public void setTempVar(String temp){
        this.tempVar = temp;        
    }
    
    @Override public String toString(){
        return this.getUserID() + "," + this.getFirstName() + "," + this.getLastName();
    }
}
