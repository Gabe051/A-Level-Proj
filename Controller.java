    import java.io.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.JComboBox;
public class Controller
{
    private ModelData recordData = new ModelData();
    
    private RecordsLogic rl = new RecordsLogic();
    private ChatLogic cl = new ChatLogic();
    private ReportLogic rp = new ReportLogic();
    private LoginLogic ll = new LoginLogic();
    private TimetableLogic tl = new TimetableLogic();
    private TransactionLogic trl = new TransactionLogic();
    private StudentLogLogic sll = new StudentLogLogic();
    private ForumLogic frl = new ForumLogic();
    private AssignmentLogic al = new AssignmentLogic();
    private StudentStruct studentUser;
    private TeacherStruct teacherUser;
    private AdminStruct adminUser;
    
    private UserAuthEnum userAuth;
    
    public StudentStruct getStudentUser(){
        return this.studentUser;
    }
    
    public TeacherStruct getTeacherUser(){
        return this.teacherUser;
    }
    
    public AdminStruct getAdminUser(){
        return this.adminUser;
    }
    
    public UserAuthEnum getUserAuth(){
        return this.userAuth;
    }
    
    public String getCurrUserID(){
        switch(userAuth){
            case STUDENT:
                return this.studentUser.getUserID();
            case TEACHER:
                return this.teacherUser.getUserID();
            case ADMIN:
                return this.adminUser.getUserID();
            default:
                return "";
        }
    }
    
    public void setUserAuth(UserAuthEnum enteredAuth){
        this.userAuth = enteredAuth;
    }
    
    public void setStudentUser(String userID, String firstName, String lastName, String attendance, 
                               String dateOfBirth, String address, String yearGroup, String yearLeaving, String phoneNum){
        studentUser = new StudentStruct(userID, firstName, lastName, attendance, dateOfBirth, address, yearGroup, yearLeaving, phoneNum);
    }
    
    public void setTeacherUser(String userID, String firstName, String lastName, String subject, String dateOfBirth){
        teacherUser = new TeacherStruct(userID, firstName, lastName, subject, dateOfBirth);
    }
    
    public void setAdminUser(String userID, String firstName, String lastName,String temp){
        adminUser = new AdminStruct(userID, firstName, lastName, temp);
    }
    
    public void populateComboBoxStudents(JComboBox studentBox){
        for(StudentStruct student : recordData.getStudentRecords()){
            studentBox.addItem( student.getFullName()+","+student.getUserID() );
        }
    }
    
    public UserStruct getUserBase(){
        switch(userAuth){
            case STUDENT:
                return (UserStruct)studentUser;
            case TEACHER:
                return (UserStruct)teacherUser;
            case ADMIN:
                return(UserStruct)adminUser;
        }
        return studentUser;
    }
    
    public ModelData getRecordData(){
        return this.recordData;
    }
    
    public RecordsLogic getRecordsLogic(){
        return this.rl;
    }
    
    public ChatLogic getChatLogic(){
        return this.cl;
    }
    
    public ReportLogic getReportLogic(){
        return this.rp;
    }
    
    public LoginLogic getLoginLogic(){
        return this.ll;
    }
    
    public TimetableLogic getTimetableLogic(){
        return this.tl;
    }
    
    public TransactionLogic getTransactionLogic(){
        return this.trl;
    }
    
    public StudentLogLogic getStudentLogLogic(){
        return this.sll;
    }
    
    public ForumLogic getForumLogic(){
        return this.frl;
    }
    
    public AssignmentLogic getAssignmentLogic(){
        return this.al;
    }
}
