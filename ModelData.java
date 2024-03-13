import java.util.*;

public class ModelData
{
    private ArrayList<StudentStruct> studentRecords = new ArrayList<StudentStruct>();
    private ArrayList<TeacherStruct> teacherRecords = new ArrayList<TeacherStruct>();
    private ArrayList<AdminStruct> adminRecords = new ArrayList<AdminStruct>();
    
    
    public ArrayList<StudentStruct> getStudentRecords(){
        return studentRecords;
    }
    
    public ArrayList<TeacherStruct> getTeacherRecords(){
        return teacherRecords;
    }
    
    public ArrayList<AdminStruct> getAdminRecords(){
        return adminRecords;
    }
    
    public void clearStudentRecords(){
        studentRecords = new ArrayList<StudentStruct>();
    }
    
    public void clearTeacherRecords(){
        teacherRecords = new ArrayList<TeacherStruct>();
    }
    
    public void clearAdminRecords(){
        adminRecords = new ArrayList<AdminStruct>();
    }
    //Record keyword was used as variable name for a while for some reason
    public void addStudentRecord(StudentStruct studRecord){
        studentRecords.add(studRecord);
    }
    
    public void addTeacherRecord(TeacherStruct teachRecord){
        teacherRecords.add(teachRecord);
    }
    
    public void addAdminRecord(AdminStruct adminRecord){
        adminRecords.add(adminRecord);
    }
    
}
