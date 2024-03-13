import java.util.*;
import java.io.*; 
import javax.swing.table.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.time.*;
import com.github.lgooddatepicker.components.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.*;
import java.awt.Window;
import javax.swing.SwingUtilities;

public class RecordsLogic
{
    final String idStudentIdentifier = "ScurrIdNum";
    final String idTeacherIdentifier = "TcurrIdNum";
    final String adminTeacherIdentifier = "AcurrIdNum";
    private boolean sortOrder = true;
    
    public void readRecords(ModelData recordData, DefaultTableModel studentModel, DefaultTableModel teacherModel, DefaultTableModel adminModel){
        clearRecords(recordData, studentModel, teacherModel, adminModel);
        
        for(String[] currParts : getRecordParts("TextFiles\\StudentRecords.txt")){
            StudentStruct currUser = new StudentStruct(currParts[0], currParts[1], currParts[2], currParts[3], currParts[4], currParts[5], currParts[6],
                                                       currParts[7], currParts[8]);
            recordData.addStudentRecord(currUser);
        }
        
        for(String[] currParts : getRecordParts("TextFiles\\TeacherRecords.txt")){
            TeacherStruct currUser = new TeacherStruct(currParts[0], currParts[1], currParts[2], currParts[3], currParts[4]);
            recordData.addTeacherRecord(currUser);
        }
        
        for(String[] currParts : getRecordParts("TextFiles\\AdminRecords.txt")){
            AdminStruct currUser = new AdminStruct(currParts[0], currParts[1], currParts[2], "");
            recordData.addAdminRecord(currUser);
        }
        
        updateRecordTables(recordData,studentModel, teacherModel, adminModel);
    }
    
    private ArrayList<String[]> getRecordParts(String filepath){
        ArrayList<String[]> parts = new ArrayList<String[]>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] readUser = nextLine.split(",");
                parts.add(readUser);
                nextLine = br.readLine();
            }
            br.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return parts;
    }
    
    public void updateRecordTables(ModelData recordData, DefaultTableModel studentModel, DefaultTableModel teacherModel, DefaultTableModel adminModel){
        studentModel.setRowCount(0); teacherModel.setRowCount(0); adminModel.setRowCount(0);
        
        for(StudentStruct student : recordData.getStudentRecords()){
            studentModel.addRow(student.toString().split(","));
        }
        for(TeacherStruct teacher : recordData.getTeacherRecords()){
            teacherModel.addRow(teacher.toString().split(","));
        }
        for(AdminStruct admin : recordData.getAdminRecords()){
            adminModel.addRow(admin.toString().split(","));
        }
        
    }
    
    public void clearRecords(ModelData recordData, DefaultTableModel studentModel, DefaultTableModel teacherModel, DefaultTableModel adminModel){
        recordData.clearStudentRecords(); recordData.clearTeacherRecords(); recordData.clearAdminRecords();
        studentModel.setRowCount(0); teacherModel.setRowCount(0); adminModel.setRowCount(0);
         
    }
    
    public void chatUsers(ModelData recordData, JComboBox userList){
        for(StudentStruct student : recordData.getStudentRecords()){
            userList.addItem(student.getUserID()+","+student.getFirstName()+","+student.getLastName());
        }
        for(TeacherStruct teacher : recordData.getTeacherRecords()){
            userList.addItem(teacher.getUserID()+","+teacher.getFirstName()+","+teacher.getLastName());
        }
        for(AdminStruct admin : recordData.getAdminRecords()){
            userList.addItem(admin.getUserID()+","+admin.getFirstName()+","+admin.getLastName());
        }
    }
    
    public void createStudentRecordPopup(){
        JOptionPane newRecordPopup = new JOptionPane();
        JPanel newRecordPane = new JPanel(null);
        newRecordPane.setSize(500,800);
        newRecordPane.setMinimumSize(new Dimension(500,800));
        newRecordPane.setMaximumSize(new Dimension(500,800));
        newRecordPane.setPreferredSize(new Dimension(500,800));
        
        // JTextField tfDoB = new JTextField();
        JLabel dobLbl = new JLabel("Date of Birth:");
    
        JTextField tfAddress = new JTextField();
        JLabel addressLbl = new JLabel("Address:");
    
        // JTextField tfYearGroup = new JTextField();
        JLabel yearGroupLbl = new JLabel("Year Group:");
        
        JComboBox cbYearGroup = new JComboBox();
        cbYearGroup.addItem("Year1");
        cbYearGroup.addItem("Year2");
        
        JComboBox cbYearLeaving = new JComboBox();
        JLabel yearLeavingLbl = new JLabel("Year Leaving:");
    
        JTextField tfPhoneNum = new JTextField();
        JLabel phoneNumLbl = new JLabel("Parent/Guardian Mobile Contact");
        
        
        JTextField tfFirstName = new JTextField();
        JLabel lblFirstName = new JLabel("First Name:");
        
        JTextField tfLastName = new JTextField();
        JLabel lblLastName = new JLabel("Last Name:");
        
        JTextField tfAttendance = new JTextField();
        JLabel lblAttendance = new JLabel ("Attendance:");
        
        
        tfFirstName.setSize(200,20); 
        tfFirstName.setLocation(10,40);
        lblFirstName.setSize(120,20); 
        lblFirstName.setLocation(10,10);
        newRecordPane.add(tfFirstName); 
        newRecordPane.add(lblFirstName);
        
        tfLastName.setSize(200,20); 
        tfLastName.setLocation(10,120);
        lblLastName.setSize(120,20); 
        lblLastName.setLocation(10,80);
        newRecordPane.add(tfLastName); 
        newRecordPane.add(lblLastName);
        
        tfAttendance.setSize(200,20); 
        tfAttendance.setLocation(10, 190);
        lblAttendance.setSize(120,20); 
        lblAttendance.setLocation(10,160);
        newRecordPane.add(tfAttendance); 
        newRecordPane.add(lblAttendance);
        
        // tfDoB.setSize(200,20);
        // tfDoB.setLocation(10,270);
        // 
        // newRecordPane.add(tfDoB);
        // 
        
        DatePicker setDateOfBirth = new DatePicker(); // DATE OF BIRTH DATE PICKER
        setDateOfBirth.setSize(200,24);
        setDateOfBirth.setLocation(10, 270);
        dobLbl.setSize(120,20);
        dobLbl.setLocation(10,230);
        newRecordPane.add(setDateOfBirth);
        newRecordPane.add(dobLbl);
        
        tfAddress.setSize(200,20);
        tfAddress.setLocation(10, 350);
        addressLbl.setSize(120,20);
        addressLbl.setLocation(10,310);
        newRecordPane.add(tfAddress);
        newRecordPane.add(addressLbl);
        
        
        
        cbYearGroup.setSize(200,20);
        cbYearGroup.setLocation(10,430);
        yearGroupLbl.setSize(120,20);
        yearGroupLbl.setLocation(10, 390);
        newRecordPane.add(cbYearGroup);
        newRecordPane.add(yearGroupLbl);
        
        
        Year currentYear = Year.now();
        int parsedYear = Integer.parseInt(currentYear.toString());
        cbYearLeaving.addItem(""+parsedYear);
        cbYearLeaving.addItem(""+(parsedYear + 1));
        cbYearLeaving.addItem(""+(parsedYear + 2));
        cbYearLeaving.setSize(200,20);
        cbYearLeaving.setLocation(10,510);
        yearLeavingLbl.setSize(120,20);
        yearLeavingLbl.setLocation(10,470);
        newRecordPane.add(cbYearLeaving);
        newRecordPane.add(yearLeavingLbl);
        
        tfPhoneNum.setSize(200,20);
        tfPhoneNum.setLocation(10,590);
        phoneNumLbl.setSize(200,20);
        phoneNumLbl.setLocation(10,550);
        newRecordPane.add(tfPhoneNum);
        newRecordPane.add(phoneNumLbl);
        
        int result = newRecordPopup.showConfirmDialog(null, newRecordPane, "Create Record", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            JOptionPane errorBox = new JOptionPane();
            ArrayList<String> textFieldContent = new ArrayList<String>();
            textFieldContent.add(tfFirstName.getText());
            textFieldContent.add(tfLastName.getText());
            textFieldContent.add(tfAttendance.getText());
            textFieldContent.add(setDateOfBirth.getText());
            textFieldContent.add(tfAddress.getText());
            textFieldContent.add(cbYearGroup.getSelectedItem().toString()); //REPLACE WITH COMBOBOX
            //CHANGE YEAR LEAVING TO A COMBOBOX
            textFieldContent.add(cbYearLeaving.getSelectedItem().toString()); //REAPLCE WITH COMBOBOX
            textFieldContent.add(tfPhoneNum.getText());
            
            for(int i = 0; i < textFieldContent.size(); i++){
                if(!(textFieldContent.get(i).length() > 0)){
                    errorBox.showMessageDialog(null, "All fields must be occupied before you can create a record", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(textFieldContent.get(i).contains(",")){
                    errorBox.showMessageDialog(null, "No text fields should contain any ',' characters", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            for(int i = 0; i < tfPhoneNum.getText().length(); i++){
                if( (!Character.isDigit(tfPhoneNum.getText().charAt(i)) && !Character.isWhitespace(tfPhoneNum.getText().charAt(i)))){
                    errorBox.showMessageDialog(null, "Phone number should only consist of digits", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            try{
                float parsedVal = Float.parseFloat(tfAttendance.getText());
                if( (parsedVal > 100.0) || (parsedVal < 0.0)){
                    errorBox.showMessageDialog(null, "Attendance must be within the range 0-100, if its a new record it should be set to 100", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }catch(Exception e){
                errorBox.showMessageDialog(null, "Attendance should be a decimal value", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
                
            createStudentRecord(textFieldContent.get(0), textFieldContent.get(1), textFieldContent.get(2), textFieldContent.get(3), textFieldContent.get(4), textFieldContent.get(5), textFieldContent.get(6), textFieldContent.get(7));
            
        }
        
    }
    
    public void createStudentRecord(String firstName, String lastName, String attendance, String dateOfBirth, String address, String yearGroup, String yearLeaving, String phoneNum){
        StudentStruct createdUser = new StudentStruct(generateID(idStudentIdentifier),firstName, lastName, attendance,dateOfBirth,address,yearGroup,yearLeaving,phoneNum);
        writeRecordToFile(createdUser.getRecordFilePath(),createdUser.toString().split(","));
    }
    
    private void writeRecordToFile(String filepath,String[] recordParts){
        try{
            FileWriter fw = new FileWriter(filepath, true);
            String recordLine = "";
            for(String part : recordParts){
                recordLine += (part + ",");
            }
            fw.write(recordLine.substring(0, recordLine.length() - 1));
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private String generateID(String idCountIdentifier){
        String foundCount = "";
        String genID = "";
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
            
            String nextLine = br.readLine();
            String fileContent = "";
            
            while(nextLine != null){
                String[] data = nextLine.split(",");
                if(data[0].equalsIgnoreCase(idCountIdentifier)){
                    foundCount = data[1];
                    genID = "" + (Integer.parseInt(data[1]) + 1);
                    while(genID.length() < 5){
                        genID = "0" + genID;
                    }
                    fileContent += (idCountIdentifier +","+ genID + "\n");
                    
                }else{
                    fileContent += (nextLine + "\n");
                }
                nextLine = br.readLine();
            }
            br.close();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\InitialisationData.txt", false));
            for(String line : fileContent.split("\n")){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        genID = idCountIdentifier.charAt(0) + genID;
        return genID;
    }
    
    public void searchStudents(ModelData recordData, String searchKey, DefaultTableModel studentModel){
        studentModel.setRowCount(0);
        if(!searchKey.equals("")){
            for(StudentStruct student : recordData.getStudentRecords()){
                if(student.toString().toLowerCase().contains(searchKey.toLowerCase())){
                    studentModel.addRow(student.toString().split(","));
                }
            }
        }else{
            for(StudentStruct student : recordData.getStudentRecords()){
                studentModel.addRow(student.toString().split(","));
            }
        }
    }
    
    public void searchTeachers(ModelData recordData, String searchKey, DefaultTableModel teacherModel){
        teacherModel.setRowCount(0);
        if(!searchKey.equals("")){
            for(TeacherStruct teacher : recordData.getTeacherRecords()){
                if(teacher.toString().toLowerCase().contains(searchKey.toLowerCase())){
                    teacherModel.addRow(teacher.toString().split(","));
                }
            }
        }else{
            for(TeacherStruct teacher : recordData.getTeacherRecords()){
                teacherModel.addRow(teacher.toString().split(","));
            }
        }
    }
    
    public void searchAdmins(ModelData recordData, String searchKey, DefaultTableModel adminModel){
        adminModel.setRowCount(0);
        if(!searchKey.equals("")){
            for(AdminStruct admin : recordData.getAdminRecords()){
                if(admin.toString().toLowerCase().contains(searchKey.toLowerCase())){
                    adminModel.addRow(admin.toString().split(","));
                }
            }
        }else{
            for(AdminStruct admin : recordData.getAdminRecords()){
                adminModel.addRow(admin.toString().split(","));
            }
        }
    }
    
    public void sortAdmins(ArrayList<AdminStruct> adminRecords, AdminRecordFields attribute){
        for(int k = 0; k < adminRecords.size(); k++){
            for(int i = 0; i < adminRecords.size() - 1; i++){
                if(compareAdminAttribute(adminRecords.get(i), adminRecords.get(i+1), attribute) ^ sortOrder){
                    AdminStruct tempAdmin = adminRecords.get(i);
                    adminRecords.set(i, adminRecords.get(i+1));
                    adminRecords.set(i+1, tempAdmin);
                }
            }
        }
        sortOrder = !sortOrder;
    }
    
    private boolean compareAdminAttribute(AdminStruct adminOne, AdminStruct adminTwo, AdminRecordFields attribute){
        switch(attribute){
            case ID:
                int ID1 = Integer.parseInt(adminOne.getUserID().substring(1));
                int ID2 = Integer.parseInt(adminTwo.getUserID().substring(1));
                return ID1 > ID2;
            case FIRST_NAME:
                return (adminOne.getFirstName().compareToIgnoreCase(adminTwo.getFirstName()) < 0);
            case LAST_NAME:
                return (adminOne.getLastName().compareToIgnoreCase(adminTwo.getLastName()) < 0);
                
        }
        return false;
    }
    
    public void sortTeachers(ArrayList<TeacherStruct> teacherRecords, TeacherRecordFields attribute){
        for(int k = 0; k < teacherRecords.size(); k++){
            for(int i = 0; i < teacherRecords.size() - 1; i++){
                if(compareTeacherAttribute(teacherRecords.get(i), teacherRecords.get(i+1), attribute) ^ sortOrder){
                    TeacherStruct tempTeacher = teacherRecords.get(i);
                    teacherRecords.set(i, teacherRecords.get(i+1));
                    teacherRecords.set(i+1, tempTeacher);
                }
            }
        }
        sortOrder = !sortOrder;
    }
    
    private boolean compareTeacherAttribute(TeacherStruct teacherOne, TeacherStruct teacherTwo, TeacherRecordFields attribute){
        switch(attribute){
            case ID:
                int ID1 = Integer.parseInt(teacherOne.getUserID().substring(1));
                int ID2 = Integer.parseInt(teacherTwo.getUserID().substring(1));
                return ID1 > ID2;
            case FIRST_NAME:
                return (teacherOne.getFirstName().compareToIgnoreCase(teacherTwo.getFirstName()) < 0);
            case LAST_NAME:
                return (teacherOne.getLastName().compareToIgnoreCase(teacherTwo.getLastName()) < 0);
            case SUBJECT:
                return (teacherOne.getSubject().compareToIgnoreCase(teacherTwo.getSubject()) < 0);
            case DOB:
                
        }
        return false;
    }
    
    public void sortStudents(ArrayList<StudentStruct> studentRecords, StudentRecordFields attribute){
        for(int k = 0; k < studentRecords.size(); k++){
            for(int i = 0; i < studentRecords.size() - 1; i++){
                if(compareStudentAttribute(studentRecords.get(i), studentRecords.get(i+1), attribute) ^ sortOrder){
                    StudentStruct tempStudent = studentRecords.get(i);
                    studentRecords.set(i, studentRecords.get(i+1));
                    studentRecords.set(i+1, tempStudent);
                }
            }
        }
        sortOrder = !sortOrder;
    }
    
    private boolean compareStudentAttribute(StudentStruct studentOne, StudentStruct studentTwo, StudentRecordFields attribute){
        switch(attribute){
            case ID:
                int ID1 = Integer.parseInt(studentOne.getUserID().substring(1));
                int ID2 = Integer.parseInt(studentTwo.getUserID().substring(1));
                return ID1 > ID2;
            case  FIRST_NAME:
                return (studentOne.getFirstName().compareToIgnoreCase(studentTwo.getFirstName()) < 0);
            case LAST_NAME:
                return (studentOne.getLastName().compareToIgnoreCase(studentTwo.getLastName()) < 0);
            case ATTENDANCE:
                return studentOne.getAttendance() > studentTwo.getAttendance();
            case DOB:
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date dateOne = null;
                Date dateTwo = null;
                try
                {
                    dateOne = formatter.parse(studentOne.getDateOfBirth());
                    dateTwo = formatter.parse(studentTwo.getDateOfBirth());
                }
                catch (java.text.ParseException pe)
                {
                    pe.printStackTrace();
                }
                return dateOne.after(dateTwo);
            case ADDRESS:
                return (studentOne.getAddress().compareToIgnoreCase(studentTwo.getAddress()) < 0);
            case YEAR_GROUP:
                return (studentOne.getYearGroup().compareToIgnoreCase(studentTwo.getYearGroup()) < 0);
            case YEAR_LEAVING:
                int year1 = Integer.parseInt(studentOne.getYearLeaving());
                int year2 = Integer.parseInt(studentTwo.getYearLeaving());
                return year1 > year2;
            case PHONE_NUM:
                return (studentOne.getPhoneNum().compareToIgnoreCase(studentTwo.getPhoneNum()) < 0 );
        }
        return false;
    }
    
    public void sortRecords(ArrayList<StudentStruct> studentRecords, UserRecordFields compareVal){
        for(int k = 0; k < studentRecords.size(); k++){
            for(int i = 0; i < studentRecords.size() - 1; i++){
                if(compareFields(studentRecords.get(i), studentRecords.get(i+1), compareVal) ^ sortOrder){
                    Collections.swap(studentRecords, i, i+1);
                }
            }
        }
        sortOrder = !sortOrder;
    }
    //# OVERRIDE WITH OTHER USER STRUCT TYPES IN FUTURE IMPLEMENTATIONS
    private boolean compareFields(StudentStruct studentOne, StudentStruct studentTwo,UserRecordFields compareVal){
        boolean comparisonResult = false;
        switch(compareVal){
            case ID:
                int ID1 = Integer.parseInt(studentOne.getUserID().substring(1));
                int ID2 = Integer.parseInt(studentTwo.getUserID().substring(1));
                return ID1 > ID2;
            case FIRSTNAME:
                return (studentOne.getFirstName().compareToIgnoreCase(studentTwo.getFirstName()) < 0);
            case LASTNAME:
                return (studentOne.getLastName().compareToIgnoreCase(studentTwo.getLastName()) < 0 );
        }
        return false;
    }
    
    public void editRecord(ModelData recordData, String recordID, DefaultTableModel studentModel){
        // FIRST, GET THE RECORD TO EDIT
        StudentStruct selStudent = null;
        for(StudentStruct student : recordData.getStudentRecords()){
            if(student.getUserID().equals(recordID)){
                selStudent = student;
            }
        }
        
        // NOW CREATE THE FIELDS TO EDIT THE DATA AND POPULATE IT WITH THE CURRENT DATA.
        
        JOptionPane editPopupBox = new JOptionPane();
        
        if(selStudent == null){
            editPopupBox.showMessageDialog(null, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel popupPane = new JPanel(null);
        popupPane.setSize(500,800);
        popupPane.setMinimumSize(new Dimension(500,800));
        popupPane.setMaximumSize(new Dimension(500,800));
        popupPane.setPreferredSize(new Dimension(500,800));
        
        JLabel dobLbl = new JLabel("Date of Birth:");
        
        DatePicker setDateOfBirth = new DatePicker();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setDateOfBirth.setDate(LocalDate.parse(selStudent.getDateOfBirth(), formatter));
        
        
        JLabel lblFirstName = new JLabel("First Name:");
        JTextField tfFirstName = new JTextField();
        tfFirstName.setText(selStudent.getFirstName());
        
        
        JLabel lblLastName = new JLabel("Last Name:");
        JTextField tfLastName = new JTextField();
        tfLastName.setText(selStudent.getLastName());
        
        JLabel addressLbl = new JLabel("Address:");
        JTextField tfAddress = new JTextField();
        tfAddress.setText(selStudent.getAddress());
        
        JLabel yearGroupLbl = new JLabel("Year Group:");
        JComboBox cbYearGroup = new JComboBox();
        cbYearGroup.addItem("Year1");
        cbYearGroup.addItem("Year2");
        if(selStudent.getYearGroup().equals("Year1")){
            cbYearGroup.setSelectedIndex(0);
        }else{
            cbYearGroup.setSelectedIndex(1);
        }
        
        
        
        JLabel yearLeavingLbl = new JLabel("Year Leaving:");
        JComboBox cbYearLeaving = new JComboBox();
        Year currentYear = Year.now();
        int parsedYear = Integer.parseInt(currentYear.toString());
        cbYearLeaving.addItem(""+parsedYear);
        cbYearLeaving.addItem(""+ (parsedYear + 1));
        cbYearLeaving.addItem("" + (parsedYear + 2));
        cbYearLeaving.setSelectedIndex(Integer.parseInt(selStudent.getYearLeaving()) % parsedYear);
        
        JTextField tfPhoneNum = new JTextField();
        JLabel phoneNumLbl = new JLabel("Parent/Guardian Mobile Contact:");
        tfPhoneNum.setText(selStudent.getPhoneNum());
        
        JLabel lblAttendance = new JLabel("Attendance");
        JTextField tfAttendance = new JTextField();
        tfAttendance.setText(""+selStudent.getAttendance());
        
        
        tfFirstName.setSize(200,20); 
        tfFirstName.setLocation(10,40);
        lblFirstName.setSize(120,20); 
        lblFirstName.setLocation(10,10);
        popupPane.add(tfFirstName); 
        popupPane.add(lblFirstName);
        
        
        tfLastName.setSize(200,20); 
        tfLastName.setLocation(10,120);
        lblLastName.setSize(120,20); 
        lblLastName.setLocation(10,80);
        popupPane.add(tfLastName); 
        popupPane.add(lblLastName);
        
        tfAttendance.setSize(200,20); 
        tfAttendance.setLocation(10, 190);
        lblAttendance.setSize(120,20); 
        lblAttendance.setLocation(10,160);
        popupPane.add(tfAttendance); 
        popupPane.add(lblAttendance);
        
        setDateOfBirth.setSize(200,24);
        setDateOfBirth.setLocation(10, 270);
        dobLbl.setSize(120,20);
        dobLbl.setLocation(10,230);
        popupPane.add(setDateOfBirth);
        popupPane.add(dobLbl);
        
        tfAddress.setSize(200,20);
        tfAddress.setLocation(10, 350);
        addressLbl.setSize(120,20);
        addressLbl.setLocation(10,310);
        popupPane.add(tfAddress);
        popupPane.add(addressLbl);
        
        
        
        cbYearGroup.setSize(200,20);
        cbYearGroup.setLocation(10,430);
        yearGroupLbl.setSize(120,20);
        yearGroupLbl.setLocation(10, 390);
        popupPane.add(cbYearGroup);
        popupPane.add(yearGroupLbl);
        
        cbYearLeaving.setSize(200,20);
        cbYearLeaving.setLocation(10,510);
        yearLeavingLbl.setSize(120,20);
        yearLeavingLbl.setLocation(10,470);
        popupPane.add(cbYearLeaving);
        popupPane.add(yearLeavingLbl);
        
        tfPhoneNum.setSize(200,20);
        tfPhoneNum.setLocation(10,590);
        phoneNumLbl.setSize(200,20);
        phoneNumLbl.setLocation(10,550);
        popupPane.add(tfPhoneNum);
        popupPane.add(phoneNumLbl);
        
        JButton deleteRecordBtn = new JButton("DELETE");
        deleteRecordBtn.setSize(80,30);
        deleteRecordBtn.setLocation(410,10);
        
        deleteRecordBtn.addActionListener(AL->deleteSelectedRecord(recordID, recordData, deleteRecordBtn, "TextFiles\\StudentRecords.txt", studentModel));
        popupPane.add(deleteRecordBtn);
        
        
        
        int result = editPopupBox.showConfirmDialog(null, popupPane, "Edit Record", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            JOptionPane errorBox = new JOptionPane();
            ArrayList<String> selRecordContent = new ArrayList<String>();
            //These need to be added to the arraylist in the order they appear in the file entry
            selRecordContent.add(tfFirstName.getText());
            selRecordContent.add(tfLastName.getText());
            selRecordContent.add(tfAttendance.getText());
            LocalDate dob = setDateOfBirth.getDate();
            selRecordContent.add(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            selRecordContent.add(tfAddress.getText());
            selRecordContent.add(cbYearGroup.getSelectedItem().toString());
            selRecordContent.add(cbYearLeaving.getSelectedItem().toString());
            selRecordContent.add(tfPhoneNum.getText());
            
            // VALIDATE THIS STUFF TO MAKE SURE IT IS VALID DATA
            
            for(int i = 0; i < selRecordContent.size(); i++){
                if(!(selRecordContent.get(i).length() > 0)){
                    errorBox.showMessageDialog(null, "All fields must be occupied before you can create a record","Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(selRecordContent.get(i).contains(",")){
                    errorBox.showMessageDialog(null, "No text fields can contain any ',' characters", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            for(int i = 0; i < tfPhoneNum.getText().length(); i++){
                if( (!Character.isDigit(tfPhoneNum.getText().charAt(i))) && !Character.isWhitespace(tfPhoneNum.getText().charAt(i))){
                    errorBox.showMessageDialog(null, "Phone number should only consist of digits", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            try{
                float parsedVal = Float.parseFloat(tfAttendance.getText());
                if( (parsedVal > 100.0) || (parsedVal < 0.0)){
                    errorBox.showMessageDialog(null, "Attendance value should be within the range 0 - 100", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }catch(Exception e){
                errorBox.showMessageDialog(null, "Attendance should be a decimal value", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //ADD THE STUDENT RECORD TO THE RECORD ARRAYLIST
            
            for(int i = 0; i < recordData.getStudentRecords().size(); i++){
                if(recordData.getStudentRecords().get(i).getUserID().equals(selStudent.getUserID())){
                    //OVERWRITING THE DATA OF THE STUDENT IN THE CURRENT FILE AS WELL FOR CURRENT APPLICATION INSTANCE
                    recordData.getStudentRecords().get(i).setFirstName(selRecordContent.get(0)); //SETS FIRST NAME
                    recordData.getStudentRecords().get(i).setLastName(selRecordContent.get(1)); //SETS LAST NAME
                    recordData.getStudentRecords().get(i).setAttendance(Float.parseFloat(selRecordContent.get(2))); //SETS ATTENDANCE
                    recordData.getStudentRecords().get(i).setDateOfBirth(selRecordContent.get(3));  //SETS DATE OF BIRTH
                    recordData.getStudentRecords().get(i).setAddress(selRecordContent.get(4)); //SETS ADDRESS
                    recordData.getStudentRecords().get(i).setYearGroup(selRecordContent.get(5)); //SETS YEAR GROUP SELECTED
                    recordData.getStudentRecords().get(i).setYearLeaving(selRecordContent.get(6)); //SETS YEAR LEAVING
                    recordData.getStudentRecords().get(i).setPhoneNum(selRecordContent.get(7)); //SETS PHONE NUM
                }
            }
            
            //Now edit the record with the matching student ID in the file with updated data
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\StudentRecords.txt"));
                ArrayList<String> fileContent = new ArrayList<String>();
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals(selStudent.getUserID())){
                        System.out.println("MATCHED");
                        fileContent.add(selStudent.getUserID() +","+ selRecordContent.get(0) +","+ selRecordContent.get(1) +","+ selRecordContent.get(2) +","+ selRecordContent.get(3) +","+ 
                                        selRecordContent.get(4) +","+ selRecordContent.get(5) +","+ selRecordContent.get(6) +","+ selRecordContent.get(7));
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\StudentRecords.txt"));
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                System.out.println("Written to file successfully");
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        //UPDATE THE TABLE WITH THE CHANGES
        
        studentModel.setRowCount(0);
        for(StudentStruct student : recordData.getStudentRecords()){
            String[] row = new String[9];
            // Set each row attribute like this because im scared to break other stuff that uses to string
            row[0] = student.getUserID();
            row[1] = student.getFirstName();
            row[2] = student.getLastName();
            row[3] = ""+student.getAttendance();
            row[4] = student.getDateOfBirth();
            row[5] = student.getAddress();
            row[6] = student.getYearGroup();
            row[7] = student.getYearLeaving();
            row[8] = student.getPhoneNum();
            studentModel.addRow(row);
        }
    }
    
    
    public void editTeacherRecord(ModelData recordData, String recordID, DefaultTableModel teacherModel){
        
        TeacherStruct selTeacher = null;
        for(TeacherStruct teacher : recordData.getTeacherRecords()){
            if(teacher.getUserID().equals(recordID)){
                System.out.println("FOUND");
                selTeacher = teacher;
            }
        }
        
        
        JOptionPane editPopupBox = new JOptionPane();
        
        if(selTeacher == null){
            editPopupBox.showMessageDialog(null, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel popupPane = new JPanel(null);
        popupPane.setSize(500,800);
        popupPane.setMinimumSize(new Dimension(500,800));
        popupPane.setMaximumSize(new Dimension(500,800));
        popupPane.setPreferredSize(new Dimension(500,800));
        
        JLabel dobLbl = new JLabel("Date of birth:");
        DatePicker setDateOfBirth = new DatePicker();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setDateOfBirth.setDate(LocalDate.parse(selTeacher.getDateOfBirth(),formatter));
        
        JLabel lblFirstName = new JLabel("First Name:");
        JTextField tfFirstName = new JTextField();
        tfFirstName.setText(selTeacher.getFirstName());
        
        JLabel lblLastName = new JLabel("Last Name:");
        JTextField tfLastName = new JTextField();
        tfLastName.setText(selTeacher.getLastName());
        
        JLabel subjectLbl = new JLabel("Subject:");
        JComboBox cbSubject = new JComboBox();
        DataUtil du = new DataUtil();
        int i = 0;
        for(String subject : du.getSubjects()){
            cbSubject.addItem(subject);
            if(selTeacher.getSubject().equals(subject)){
                cbSubject.setSelectedIndex(i);
            }
            i+=1;
        }
        
        
        JButton deleteRecordBtn = new JButton("DELETE");
        deleteRecordBtn.setSize(80,30);
        deleteRecordBtn.setLocation(410,10);
        
        tfFirstName.setSize(200,20);
        tfFirstName.setLocation(10,40);
        lblFirstName.setSize(120,20);
        lblFirstName.setLocation(10,10);
        popupPane.add(tfFirstName);
        popupPane.add(lblFirstName);
        
        tfLastName.setSize(200,20);
        tfLastName.setLocation(10,120);
        lblLastName.setSize(120,20);
        lblLastName.setLocation(10,80);
        popupPane.add(tfLastName);
        popupPane.add(lblLastName);
        
        setDateOfBirth.setSize(200,24);
        setDateOfBirth.setLocation(10,270);
        dobLbl.setSize(120,20);
        dobLbl.setLocation(10,230);
        popupPane.add(setDateOfBirth);
        popupPane.add(dobLbl);
        
        
        cbSubject.setSize(200,20);
        cbSubject.setLocation(10,190);
        subjectLbl.setSize(120,20);
        subjectLbl.setLocation(10,160);
        popupPane.add(cbSubject);
        popupPane.add(subjectLbl);
        
        
        deleteRecordBtn.addActionListener(AL->deleteSelectedRecord(recordID, recordData, deleteRecordBtn, "TextFiles\\TeacherRecords.txt", teacherModel));
        popupPane.add(deleteRecordBtn);
        
        int result = editPopupBox.showConfirmDialog(null, popupPane, "Edit Record", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            JOptionPane errorBox = new JOptionPane();
            ArrayList<String> selRecordContent = new ArrayList<String>();
            selRecordContent.add(tfFirstName.getText());
            selRecordContent.add(tfLastName.getText());
            selRecordContent.add(cbSubject.getSelectedItem().toString());
            LocalDate dob = setDateOfBirth.getDate();
            selRecordContent.add(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            for(int k = 0; k < selRecordContent.size(); k++){
                if(!(selRecordContent.get(k).length() > 0)){
                    errorBox.showMessageDialog(null,  "All fields must be occupied before you can create a record","Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(selRecordContent.get(k).contains(",")){
                    errorBox.showMessageDialog(null, "No text fields can contain any ',' characters", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
            }
            
            for(int k = 0; k < recordData.getTeacherRecords().size(); k++){
                //OVERWRITING THE DATA OF THE TEACHER IN THE CURRENT APPLICATION INSTANCE
                
                if(recordData.getTeacherRecords().get(k).getUserID().equals(selTeacher.getUserID())){
                    recordData.getTeacherRecords().get(k).setFirstName(selRecordContent.get(0)); //REPLACES FIRST NAME IN RECORD
                    recordData.getTeacherRecords().get(k).setLastName(selRecordContent.get(1)); //REPLACES LAST NAME IN RECORD
                    recordData.getTeacherRecords().get(k).setSubject(selRecordContent.get(2)); //REPLACES SUBJECT
                    recordData.getTeacherRecords().get(k).setDateOfBirth(selRecordContent.get(3)); //REPLACES DOB
                }
            }
            
            //EDIT RECORD IN THE FILE
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\TeacherRecords.txt"));
                ArrayList<String> fileContent = new ArrayList<String>();
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals(selTeacher.getUserID())){
                        fileContent.add(selTeacher.getUserID() +","+ selRecordContent.get(0) +","+ selRecordContent.get(1) +","+ selRecordContent.get(2) +","+ selRecordContent.get(3));
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\TeacherRecords.txt"));
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
            //UPDATE THE TABLE TO MATCH CURRENT
            
            teacherModel.setRowCount(0);
            for(TeacherStruct teacher : recordData.getTeacherRecords()){
                teacherModel.addRow(teacher.toString().split(","));
            }
            
            
        }else{
            //editPopupBox.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        
    }
    
    public void editAdminRecord(ModelData recordData, String recordID, DefaultTableModel adminModel){
        
        AdminStruct selAdmin = null;
        for(AdminStruct admin : recordData.getAdminRecords()){
            if(admin.getUserID().equals(recordID)){
                selAdmin = admin;
            }
        }
        
        JOptionPane editPopupBox = new JOptionPane();
        
        if(selAdmin == null){
            editPopupBox.showMessageDialog(null, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel popupPane = new JPanel(null);
        popupPane.setSize(500,800);
        popupPane.setMinimumSize(new Dimension(500,800));
        popupPane.setMaximumSize(new Dimension(500,800));
        popupPane.setPreferredSize(new Dimension(500,800));
        
        JLabel lblFirstName = new JLabel("First Name:");
        JTextField tfFirstName = new JTextField();
        tfFirstName.setText(selAdmin.getFirstName());
        
        JLabel lblLastName = new JLabel("Last Name:");
        JTextField tfLastName = new JTextField();
        tfLastName.setText(selAdmin.getLastName());
        
        JButton deleteRecordBtn = new JButton("DELETE");
        
        tfFirstName.setSize(200,20);
        tfFirstName.setLocation(10,40);
        lblFirstName.setSize(120,20);
        lblFirstName.setLocation(10,10);
        popupPane.add(tfFirstName);
        popupPane.add(lblFirstName);
        
        tfLastName.setSize(200,20);
        tfLastName.setLocation(10,120);
        lblLastName.setSize(120,20);
        lblLastName.setLocation(10,80);
        popupPane.add(tfLastName);
        popupPane.add(lblLastName);
        
        deleteRecordBtn.setSize(80,30);
        deleteRecordBtn.setLocation(410,10);
        deleteRecordBtn.addActionListener(AL->deleteSelectedRecord(recordID, recordData,deleteRecordBtn, "TextFiles\\AdminRecords.txt", adminModel));
        popupPane.add(deleteRecordBtn);
        
        int result = editPopupBox.showConfirmDialog(null, popupPane, "Edit Record", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            JOptionPane errorBox = new JOptionPane();
            
            String editedFirstName = tfFirstName.getText();
            String editedLastName = tfLastName.getText();
            
            if( !(editedFirstName.length() > 0) || !(editedLastName.length() > 0) ){
                errorBox.showMessageDialog(null, "All fields must be occupied before you can edit a record", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if( editedFirstName.contains(",") || editedLastName.contains(",") ){
                errorBox.showMessageDialog(null, "No text fields can contain any ',' characters", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            for(int i = 0; i < recordData.getAdminRecords().size(); i++){
                // OVERWRITE THE DATA FOR THIS INSTANCE OF THE APPLICATION
                if(recordData.getAdminRecords().get(i).getUserID().equals(selAdmin.getUserID())){
                    recordData.getAdminRecords().get(i).setFirstName(editedFirstName);
                    recordData.getAdminRecords().get(i).setLastName(editedLastName);
                }
            }
            
            //EDIT RECORD IN FILE
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\AdminRecords.txt"));
                ArrayList<String> fileContent = new ArrayList<String>();
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals(selAdmin.getUserID())){
                        fileContent.add(selAdmin.getUserID() +","+ editedFirstName +","+ editedLastName);
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\AdminRecords.txt"));
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
            //UPDATE TABLE TO MATCH CURRENT
            
            adminModel.setRowCount(0);
            for(AdminStruct admin : recordData.getAdminRecords()){
                adminModel.addRow(admin.toString().split(","));
            }
        }
    }
    
    private void deleteSelectedRecord(String userID, ModelData recordData, JButton deleteButton, String filePath, DefaultTableModel tableModel){
        
        JOptionPane confirmDeleteBox = new JOptionPane();
        
        int result = confirmDeleteBox.showConfirmDialog(null, "Are you sure you want to delete record: "+ userID +"?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        //CHECK IF THEY WANT TO DELETE THE RECORD IF ITS NOT YES THEN RETURN OTHERWISE KEEP GOING
        if(!(result == JOptionPane.YES_OPTION)){
            confirmDeleteBox.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //FIRST DELETE IT FROM THIS INSTANCE OF THE APPLICATION
        
        if(filePath.equals("TextFiles\\TeacherRecords.txt")){
                for(int i = 0; i < recordData.getTeacherRecords().size(); i++){
                    if(recordData.getTeacherRecords().get(i).getUserID().equals(userID)){
                        recordData.getTeacherRecords().remove(i);
                    }
                }
            }else if(filePath.equals("TextFiles\\StudentRecords.txt")){
                for(int i = 0; i < recordData.getStudentRecords().size(); i++){
                    if(recordData.getStudentRecords().get(i).getUserID().equals(userID)){
                        recordData.getStudentRecords().remove(i);
                    }
                }
            }else if(filePath.equals("TextFiles\\AdminRecords.txt")){
                for(int i = 0; i < recordData.getAdminRecords().size(); i++){
                    if(recordData.getAdminRecords().get(i).getUserID().equals(userID)){
                        recordData.getAdminRecords().remove(i);
                    }
                }
            }
            
        
        
        // NOW RE WRITE THE RECORD FILE SKIPPING THE DELETED RECORD
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String nextLine = br.readLine();
            ArrayList<String> fileContent = new ArrayList<String>();
            //IF THE LINE DOESNT CONTAIN THE RECORD TO BE DELETED'S ID THEN IT CAN BE WRITTEN TO THE OVERWRITTEN FILE OTHERWISE SKIP THE LINE            
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(!splitLine[0].equals(userID)){
                    fileContent.add(nextLine);
                }
                nextLine = br.readLine();
            }
            br.close();
            // WRITE DESIRED FILE CONTENT TO FRESH FILE, DELETED RECORD WONT BE CONTAINED IN THIS SO WILL BE SKIPPED
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for(String line : fileContent){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            //The button is used to get rid of the message dialog popup used to display the edit record popup by removing access to its container.
            
            
            //UPDATE TABLE
            
            if(filePath.equals("TextFiles\\TeacherRecords.txt")){
                System.out.println("UPDATING TEACHER TABLE WHEN DELETING");
                tableModel.setRowCount(0);
                for(TeacherStruct teacher : recordData.getTeacherRecords()){
                    tableModel.addRow(teacher.toString().split(","));
                }
            }else if(filePath.equals("TextFiles\\StudentRecords.txt")){
                tableModel.setRowCount(0);
                for(StudentStruct student : recordData.getStudentRecords()){
                    tableModel.addRow(student.toString().split(","));
                }
            }else if(filePath.equals("TextFiles\\AdminRecords.txt")){
                tableModel.setRowCount(0);
                for(AdminStruct admin : recordData.getAdminRecords()){
                    tableModel.addRow(admin.toString().split(","));
                }
            }
            
            SwingUtilities.getWindowAncestor(deleteButton).setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    
}
