
import java.util.*;


public class Assignment
{
    private String title;//
    private String teacherID;//
    private String dueDate;//
    private String groupID;//
    private String assignmentID;//
    private String assignmentContent;//
    private String teacherName;
    private boolean isComplete;//
    private ArrayList<String> studentList = new ArrayList<String>();
    private ArrayList<String> docFilePaths;
    public Assignment(String assignmentID, String title, String dueDate, String teacherName, boolean isComplete, ArrayList<String> docFilePaths, String assignmentContent, ArrayList<String> studentList){
        this.assignmentID = assignmentID;
        this.dueDate = dueDate;
        this.title = title;
        this.teacherName = teacherName;
        this.isComplete = isComplete;
        this.assignmentContent = assignmentContent;
        this.studentList = studentList;
        this.docFilePaths = docFilePaths;
    }
    
    public ArrayList<String> getDocFilePaths(){
        return docFilePaths;
    }
    
    public void setStudentList(ArrayList studentList){
        this.studentList = studentList;
    }
    
    public ArrayList<String> getStudentList(){
        return studentList;
    }
    
    @Override
    public String toString(){
        return ""+ this.groupID + "," + this.title +"," + this.dueDate +"," +teacherName +","+ this.isComplete +"," + this.assignmentID;
    }
    
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
    
    public String getDueDate(){
        return this.dueDate;
    }
    
    public void setAssignmentID(String assignmentID){
        this.assignmentID = assignmentID;
    }
    
    public String getAssignmentID(){
        return this.assignmentID;
    }
    
    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }
    
    public String getTeacherName(){
        return this.teacherName;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setTeacherID(String teacherID){
        this.teacherID = teacherID;
    }
    
    public String getTeacherID(){
        return this.teacherID;
    }
    
    public void setGroupID(String groupID){
        this.groupID = groupID;
    }
    
    public String getGroupID(){
        return this.groupID;
    }
    
    public void setPostContent(String assignmentContent){
        this.assignmentContent = assignmentContent;
    }
    
    public String getAssignmentContent(){
        return this.assignmentContent;
    }
    
    public void setAsComplete(){
        this.isComplete = true;
    }
    
    public void setAsIncomplete(){
        this.isComplete = false;
    }
    
    public boolean isComplete(){
        return isComplete;
    }
    
}
