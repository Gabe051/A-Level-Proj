
public class StudentStruct extends UserStruct
{
    private float attendance;
    private String idCountIdentifier = "ScurrIdNum";
    private String recordsFilePath = "TextFiles\\StudentRecords.txt";
    private String yearLeaving;
    private String yearGroup;
    private String phoneNum;
    
    public StudentStruct(String userID, String firstName, String lastName, String attendance, 
                         String dateOfBirth, String address, String yearGroup, String yearLeaving, String phoneNum){
        this.setUserID(userID); 
        this.setFirstName(firstName); 
        this.setLastName(lastName); 
        this.setAttendance(Float.parseFloat(attendance));
        this.setDateOfBirth(dateOfBirth);
        this.setAddress(address);
        this.setYearGroup(yearGroup);
        this.setYearLeaving(yearLeaving);
        this.phoneNum = phoneNum;
    }
    
    
    
    public StudentStruct(String userID, String firstName, String lastName){
        this.setUserID(userID); 
        this.setFirstName(firstName); 
        this.setLastName(lastName);
    }
    
    public String getPhoneNum(){
        return this.phoneNum;
    }
    
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
    
    public String getRecordFilePath(){
        return recordsFilePath;
    }
    
    
    public void setAttendance(float attendance){
        this.attendance = attendance;
    }
    
    public float getAttendance(){
        return this.attendance;
    }
    
    public void setYearLeaving(String yearLeaving){
        this.yearLeaving = yearLeaving;
    }
    
    public String getYearLeaving(){
        return this.yearLeaving;
    }
    
    public void setYearGroup(String yearGroup){
        this.yearGroup = yearGroup;
    }
    
    public String getYearGroup(){
        return this.yearGroup;
    }
    
    @Override public String toString(){
        return this.getUserID() + "," + this.getFirstName() + "," + this.getLastName() + "," + this.getAttendance() + "," + this.getDateOfBirth() + "," + this.getAddress() +"," + this.getYearGroup() + "," + this.getYearLeaving() + "," + this.getPhoneNum();
    }
    
}
