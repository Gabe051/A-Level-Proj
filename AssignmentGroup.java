import java.util.ArrayList;
import java.io.*;

public class AssignmentGroup
{
    private String assignmentGroupID;
    private String teacherID;
    private ArrayList<String> studentIdList = new ArrayList<String>();
    private String groupName;
    private String teacherName;
    private ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
    
    public AssignmentGroup(){
        
    }
    
    public AssignmentGroup(String assignmentGroupID, String groupName, String teacherName, ArrayList<String> studentIdLIst){
        this.assignmentGroupID = assignmentGroupID;
        this.groupName = groupName;
        this.teacherName = teacherName;
        this.studentIdList = studentIdList;
    }
    
    public void addAssignment(Assignment assignmentToAdd){
        this.assignmentList.add(assignmentToAdd);
    }
    
    public ArrayList<Assignment> getAssignmentList(){
        return assignmentList;
    }
    
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    
    public String getGroupName(){
        return this.groupName;
    }
    
    public void setAssignmentGroupID(String assignmentGroupID){
        this.assignmentGroupID = assignmentGroupID;
    }
    
    public String getAssignmentGroupID(){
        return this.assignmentGroupID;
    }
    
    public void setTeacherID(String teacherID){
        this.teacherID = teacherID;
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\TeacherRecords.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(teacherID)){
                    this.teacherName = ""+splitLine[1]+" "+splitLine[2];
                }
                nextLine = br.readLine();
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public String getTeacherName(){
        return this.teacherName;
    }
    
    public String getTeacherID(){
        return this.teacherID;
    }
    
    public void addStudentID(String studentID){
        this.studentIdList.add(studentID);
    }
    
    public void setStudentIdList(ArrayList<String> studentIdList){
        this.studentIdList = studentIdList;
    }
    
    public ArrayList<String> getStudentIdList(){
        return this.studentIdList;
    }
}
