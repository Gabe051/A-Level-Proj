
public class UserStruct
{
    private String userID;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    
    public void setUserID(String userID){
        this.userID = userID;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getUserID(){
        return userID;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getFullName(){
        return (firstName +" "+lastName);
    }
    
    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getDateOfBirth(){
        return this.dateOfBirth;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getAddress(){
        return this.address;
    }
    
}
