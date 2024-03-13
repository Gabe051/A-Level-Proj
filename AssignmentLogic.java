import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Desktop;
import java.time.*;
import java.time.format.DateTimeFormatter;
import com.github.lgooddatepicker.components.*; // Date picker, external library from github used for a drop down calendar to select a date
import javax.swing.filechooser.*;
import javax.swing.JFileChooser;
import java.awt.Component;
import javax.swing.table.*;


public class AssignmentLogic
{
    JOptionPane assignmentPane = new JOptionPane();

    
    
    private ArrayList<AssignmentGroup> assignmentGroupList = new ArrayList<AssignmentGroup>();
    
    public ArrayList<AssignmentGroup> getAssignmentGroupList(){
        return this.assignmentGroupList;
    }
    
    String[] content;
    
    public void setAssignment(){
        JTextArea ta = new JTextArea();
        JPanel panePanel = new JPanel(null);
        //panePanel.setLayout(new BorderLayout());
        
        JLabel assignmentContentLbl = new JLabel("Assignment Details:");
        assignmentContentLbl.setSize(200,30);
        assignmentContentLbl.setLocation(10,110);
        panePanel.add(assignmentContentLbl);
        ta.setSize(750,600);
        ta.setMinimumSize(new Dimension(750,600));
        ta.setMaximumSize(new Dimension(750,600));
        ta.setPreferredSize(new Dimension(750,600));
        ta.setLocation(10,140);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        panePanel.add(ta);
        
        
        //JScrollPane taScroll = new JScrollPane(ta);
        //taScroll.setSize(700,400);
        //taScroll.setMinimumSize(new Dimension(700,400));
        //taScroll.setMaximumSize(new Dimension(700,400));
        //taScroll.setPreferredSize(new Dimension(700,400));
        //taScroll.setLocation(10,140);
        //panePanel.add(taScroll);
        String[] comboBoxArray = new String[assignmentGroupList.size()];
        for(int i = 0; i < assignmentGroupList.size();i++){
            comboBoxArray[i] = assignmentGroupList.get(i).getGroupName();
            System.out.println(assignmentGroupList.get(i).getGroupName());
        }
        
        JComboBox cbClassList = new JComboBox(comboBoxArray);
        
        cbClassList.setSize(150,30);
        cbClassList.setLocation(580,50);
        cbClassList.setBackground(new Color(40,40,40));
        cbClassList.setForeground(Color.WHITE);
        cbClassList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panePanel.add(cbClassList);
        
        JLabel classListLbl = new JLabel("Class:");
        classListLbl.setSize(150,40);
        classListLbl.setLocation(580,20);
        panePanel.add(classListLbl);
        
        JButton wordDocBtn = new JButton("Attach Word Doc");
        wordDocBtn.setSize(150,40);
        wordDocBtn.setLocation(770,150);
        
        panePanel.add(wordDocBtn);
        
        String[] attachedHeading = {"Attached"};
        DefaultTableModel attachedModel = new DefaultTableModel(attachedHeading, 0);
        JTable attachedTable = new JTable(attachedModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer,row,column);
                comp.setBackground(new Color(40,40,40));
                comp.setForeground(Color.WHITE);
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        attachedTable.setSize(350, 500);
        attachedTable.setMinimumSize(new Dimension(350,500));
        attachedTable.setMaximumSize(new Dimension(350,500));
        attachedTable.setPreferredSize(new Dimension(350,500)); 
        attachedTable.getTableHeader().setBackground(new Color(40,40,40));
        attachedTable.getTableHeader().setForeground(Color.WHITE);
        attachedTable.setBackground(new Color(40,40,40));
        attachedTable.setFillsViewportHeight(true);
        JScrollPane attachedScroll = new JScrollPane(attachedTable);
        attachedScroll.setMinimumSize(new Dimension(350,525));
        attachedScroll.setSize(350,525);
        attachedScroll.setPreferredSize(new Dimension(350,525));
        attachedScroll.setLocation(770,210);
        panePanel.add(attachedScroll);
        
        wordDocBtn.addActionListener(AL->attachWordDoc(attachedModel));
        
        JLabel titleLbl = new JLabel("Title: ");
        titleLbl.setSize(50,30);
        titleLbl.setLocation(10,20);
        JTextField tfTitle = new JTextField();
        tfTitle.setSize(300,30);
        tfTitle.setLocation(10,50);
        panePanel.add(titleLbl);
        panePanel.add(tfTitle);
        
        JLabel dueLbl = new JLabel("Due:");
        dueLbl.setSize(50,30);
        dueLbl.setLocation(330, 20);
        panePanel.add(dueLbl);
        
        DatePicker setDatePicker = new DatePicker();
        setDatePicker.setSize(200,30);
        setDatePicker.setLocation(330, 50);
        panePanel.add(setDatePicker);
        
        
        panePanel.setSize(1200,800);
        panePanel.setMinimumSize(new Dimension(1200,800));
        panePanel.setMaximumSize(new Dimension(1200,800));
        panePanel.setPreferredSize(new Dimension(1200,800));
        
        //Set assignID to generate from init data then write to file after with teacher & student list
        String assignID = "";
        int result = assignmentPane.showConfirmDialog(null, panePanel, "Create Assignment", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION){
            // PARSE TEXT CONTENT AND WRITE ASSIGNMENT TO FILE
            JOptionPane errorPane = new JOptionPane();
            String enteredTitle = tfTitle.getText();
            String assignmentContent = ta.getText();
            LocalDate selDate = setDatePicker.getDate();
            
            if(enteredTitle.length() < 1){
                errorPane.showMessageDialog(null,"Enter a valid title for the assignment", "Title Error", JOptionPane.ERROR_MESSAGE);
                return;
            }else if(selDate.isBefore(LocalDate.now())){
                errorPane.showMessageDialog(null, "Enter a valid date that is not in the past", "Due Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String dateString = selDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            AssignmentGroup selGroup = assignmentGroupList.get(cbClassList.getSelectedIndex());
            String selectedClass = selGroup.getAssignmentGroupID();
            
            //INCREMENT THE CLASS ID VALUE IN THE INITIALISATION DATA AND PREPEND IT WITH X
            
            try{
                //GET CURRENT ASSIGNMENT COUNT AND GENERATE A NEW ID
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
                ArrayList<String> fileContent = new ArrayList<String>();
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals("AssignmentCount")){
                        int newCount = Integer.parseInt(splitLine[1]) + 1;
                        String tempCode = ""+newCount;
                        while(tempCode.length() < 5){
                            tempCode = "0"+tempCode;
                        }
                        assignID = "X"+tempCode;
                        fileContent.add("AssignmentCount,"+tempCode);
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\InitialisationData.txt")); //UPDATE INITIALISATION DATA
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                
                bw.close();
                
                // WRITE THE ASSIGNMENT TO GROUP ENTRY
                
                br = new BufferedReader(new FileReader("TextFiles\\Group-Assignment.txt"));
                fileContent = new ArrayList<String>();
                nextLine = br.readLine();
                boolean foundClass = false; //FLAG SO MAKE NEW ENTRY IF NOT ADDED TO EXISTING
                while(nextLine != null){
                    String[] splitContent = nextLine.split(",");
                    if(splitContent[0].equals(selectedClass)){ //CLASS SELECTED EXISTS
                        foundClass = true;
                        fileContent.add(nextLine+"," + assignID); //WRITE LINE THEN APPEND NEW ID
                    }else{
                        fileContent.add(nextLine);    
                    }
                    
                    nextLine = br.readLine();
                }
                br.close();
                
                if(foundClass == false){
                    System.out.println("Class not found, creating new class - assignment line");
                    fileContent.add(selectedClass +"," +assignID); //IF CLASS DOESNT EXIST IN FILE MAKE A NEW ENTRY
                }
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\Group-Assignment.txt"));
                //WRITE GENERATED FILE CONTENT COPY WITH NEW ENTRY TO FILE
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                
                
                // WRITE THE ASSIGNMENT CONTENT TO THE CONTENT FILE
                
                br = new BufferedReader(new FileReader("TextFiles\\Assignment-Content.txt")); // READ ASSIGNMENT CONTENT
                nextLine = br.readLine();
                fileContent = new ArrayList<String>();
                
                while(nextLine != null){ //COPY ASSIGNMENT CONTENT FILE
                    fileContent.add(nextLine);  
                    nextLine = br.readLine();
                }
                
                String attachedFileNames = "";
                
                for(int i = 0; i < attachedModel.getRowCount(); i++){
                    String fileName = attachedModel.getValueAt(i,0).toString();
                    attachedFileNames = attachedFileNames + ("," + fileName);
                }
                
                fileContent.add(assignID+"," + enteredTitle+"," + dateString + "," + selGroup.getTeacherName() +",0" + attachedFileNames +"~}@" + assignmentContent); //ADD NEW ENTRY
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\Assignment-Content.txt")); //NEW FILEWRITER FOR NEW CONTENT
                
                for(String line : fileContent){ //WRITE EXISTING CONTENT + NEW ENTRY
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                
                //WRITE THE ASSIGNMENT STATUS FOR ALL STUDENTS IN THE GROUP
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\Assignment-Student-Completed.txt", true));
                
                for(String studentID : selGroup.getStudentIdList()){
                    bw.write(assignID+"," + studentID +",0");
                    bw.newLine();
                }
                bw.close();
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\Assignment-Student-Completed.txt", true));
                for(String studentID : selGroup.getStudentIdList()){
                    bw.write(assignID +"," + studentID +",0");
                    bw.newLine();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            assignmentPane.showMessageDialog(null, "Cancelled");
        }
            
    }
    
    
    
    public void attachWordDoc(DefaultTableModel docTable){
        // LOCATE WORD DOC WITH JFILECHOOSER AND DUPLICATE IT INTO A RELATIVE FOLDER SCOPE
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter docx = new FileNameExtensionFilter("Microsoft Word Document (.docx)", "docx");
        fileChooser.setFileFilter(docx);
        
        JOptionPane errorBox = new JOptionPane();
        fileChooser.setCurrentDirectory(new File("WordDocs"));
        
        int returnVal = fileChooser.showOpenDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            javax.swing.filechooser.FileFilter fileFilter = fileChooser.getFileFilter();
            File file = fileChooser.getSelectedFile();
            
            
            if(fileFilter.accept(file)){
                System.out.println("Accepted");
                try{
                    if(!file.exists()){
                        return;
                    }
                    String[] splitFile = file.toString().split("\\\\");
                    File destination = new File("WordDocs\\" + splitFile[splitFile.length -1]);
                    
                    
                    if(!destination.exists()){
                        FileInputStream wordDocStream = new FileInputStream(file);
                        FileOutputStream writeDocStream = new FileOutputStream(destination);
                        int bufferSize;
                        byte[] buffer = new byte[512];
                        while((bufferSize = wordDocStream.read(buffer)) > 0){
                            writeDocStream.write(buffer, 0 , bufferSize);
                        }
                        wordDocStream.close();
                        writeDocStream.close();
                    }else{
                        System.out.println("Already Exists");
                    }
                    
                    
                    String[] row = new String[1];
                    row[0] = splitFile[splitFile.length - 1];
                    docTable.addRow(row);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                errorBox.showMessageDialog(null, "Error, make sure the file type is a word document .docx file", "CANCELLED", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            errorBox.showMessageDialog(null,"Cancelled", "CANCELLED", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void viewAssignmentTeacher(String assignmentID){
        Assignment selectedAssignment = null;
        for(AssignmentGroup group : assignmentGroupList){
            for(Assignment assignment : group.getAssignmentList()){
                if(assignment.getAssignmentID().equals(assignmentID)){
                    selectedAssignment = assignment;
                }
            }
        }
        JOptionPane errorBox = new JOptionPane();
        if(selectedAssignment == null){
            errorBox.showMessageDialog(null, "ASSIGNMENT NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panePanel = new JPanel(null);
        panePanel.setSize(1200,800);
        panePanel.setMinimumSize(new Dimension(1200,800));
        panePanel.setMaximumSize(new Dimension(1200,800));
        panePanel.setPreferredSize(new Dimension(1200,800));
        
        JLabel assignmentTitleLbl = new JLabel("Title: " + selectedAssignment.getTitle());
        assignmentTitleLbl.setSize(700, 20);
        assignmentTitleLbl.setLocation(10,30);
        panePanel.add(assignmentTitleLbl);
        
        JLabel dueDateLbl = new JLabel("Due: " + selectedAssignment.getDueDate());
        dueDateLbl.setSize(120,20);
        dueDateLbl.setLocation(10,60);
        panePanel.add(dueDateLbl);
        
        //# ADD EDIT ASSIGNMENT AND DELETE ASSIGNMENT BUTTON
        
        JTextArea ta = new JTextArea();
        ta.setSize(900,600);
        ta.setMinimumSize(new Dimension(900,600));
        ta.setMaximumSize(new Dimension(900,600));
        ta.setPreferredSize(new Dimension(900,600));
        ta.setLocation(10,140);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        panePanel.add(ta);
        
        ta.setText(selectedAssignment.getAssignmentContent());
        
        JLabel attachedLbl = new JLabel("Attached Docs:");
        attachedLbl.setSize(150,30);
        attachedLbl.setMinimumSize(new Dimension(150,30));
        attachedLbl.setLocation(930,140);
        panePanel.add(attachedLbl);
        
        JButton viewAttachedDocBtn = new JButton("Open Doc");
        viewAttachedDocBtn.setSize(100,20);
        viewAttachedDocBtn.setLocation(930,210);
        String[] pathBoxItems = new String[selectedAssignment.getDocFilePaths().size()];
        for(int i = 0; i < pathBoxItems.length;i++){
            pathBoxItems[i] = selectedAssignment.getDocFilePaths().get(i);
        }
        
        JComboBox pathBox = new JComboBox(pathBoxItems);
        
        pathBox.setSize(200,30);
        pathBox.setMinimumSize(new Dimension(200,30));
        pathBox.setLocation(930,170);
        panePanel.add(pathBox);
        //# NEXT ON LIST - FIX THIS TO OPEN SELECTED FILE PATH
        viewAttachedDocBtn.addActionListener(AL->this.openWordDoc(pathBox.getSelectedItem().toString()));
        
        
        panePanel.add(viewAttachedDocBtn);
        
        JButton submissionStatusBtn = new JButton("Class Submissions");
        submissionStatusBtn.setSize(150, 30);
        submissionStatusBtn.setMinimumSize(new Dimension(50,30));
        submissionStatusBtn.setLocation(1050, 10);
        submissionStatusBtn.addActionListener(AL->showSubmissions(assignmentID));
        panePanel.add(submissionStatusBtn);
        
        
        
        assignmentPane.showMessageDialog(null, panePanel, "Viewing Assignment", JOptionPane.INFORMATION_MESSAGE);
        
    }
    JTable statusTable = new JTable();
    
    public void showSubmissions(String assignmentID){
        JOptionPane submissionsPane = new JOptionPane();
        JPanel panePanel = new JPanel(null);
        panePanel.setSize(600,810);
        panePanel.setMinimumSize(new Dimension(600,810));
        panePanel.setPreferredSize(new Dimension(600,810));
        panePanel.setMaximumSize(new Dimension(600,810));
        
        String[] statusHeadings = {"StudentID", "Submission", "AttachedWork"};
        DefaultTableModel statusModel = new DefaultTableModel(statusHeadings,0);
        
        statusTable = new JTable(statusModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(new Color(40,40,40));
                comp.setForeground(Color.WHITE);
                if(statusTable != null ){
                    if(statusTable.getValueAt(row, 1).equals("Not Handed In")){
                        comp.setBackground(new Color(120,10,2));
                    }else{
                        comp.setBackground(new Color(61,143,3));
                    }
                }
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        statusTable.getTableHeader().setReorderingAllowed(false);
        statusTable.getTableHeader().setBackground(new Color(40,40,40));
        statusTable.getTableHeader().setForeground(Color.WHITE);
        statusTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        statusTable.setSize(600,770);
        statusTable.setMinimumSize(new Dimension(600,770));
        statusTable.setPreferredSize(new Dimension(600,770));
        statusTable.setMaximumSize(new Dimension(600,770));
        statusTable.setBackground(new Color(40,40,40));
        JScrollPane statusTableScroll = new JScrollPane(statusTable);
        statusTableScroll.getViewport().setBackground(new Color(40,40,40));
        statusTableScroll.setSize(600,770);
        statusTableScroll.setMinimumSize(new Dimension(600,770));
        statusTableScroll.setPreferredSize(new Dimension(600,770));
        statusTableScroll.setMaximumSize(new Dimension(600,770));
        statusTableScroll.setLocation(0,0);
        
        panePanel.add(statusTableScroll);
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Assignment-Student-Completed.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(assignmentID)){
                    String[] row = new String[3];
                    row[0] = splitLine[1];
                    // value of "1" indicates the assignment has been handed in, "0" indicates it has not
                    if(splitLine[2].equals("1")){
                        row[1] = "Handed In";
                    }else{
                        row[1] = "Not Handed In";
                    }
                    
                    if(splitLine.length == 3){
                        row[2] = "No Attachments";
                    }else if(splitLine.length > 4){
                        row[2] = "Several Attachments";
                    }else{
                        row[2] = splitLine[3];
                    }
                    statusModel.addRow(row);
                }
                nextLine = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        submissionsPane.showMessageDialog(null, panePanel, "Submissions", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void openWordDoc(String filePath){
        // Opens the word document from its filepath using the default desktop app for opening .docx files
        // FIX later to only take the parts of the filepath that are in the scope of the project folder and add copying the word file into the project folder
        try{
            System.out.println(filePath);
            Desktop.getDesktop().open(new File("WordDocs\\"+filePath));
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane op = new JOptionPane();
            op.showMessageDialog(null, "Attached work document is not a text file for testing purposes", "", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void newAssignmentGroup(ArrayList<StudentStruct> studentRecords, String passedID){
        //HAVE A POPUP PANEL WITH
        //SCROLLABLE TABLE CONTAINING ALL THE STUDENT RECORDS
        //CLICK ON STUDENT IN TABLE THEN CONFIRM AND THEY WILL BE MOVED TO A TABLE ON THE RIGHT FOR ADDED USERS
        //CAN CLICK ON RIGHT TABLE TO REMOVE USER AND PUT BACK INTO THE ORIGINAL LIST
        //CREATE A COPY OF THE STUDENT RECORD ARRAY LIST AND HAVE 2 ARRAYLISTS -> IN GROUP AND OUT OF GROUP
        //WHEN SELECTING A USER TO ADD, REMOVE FROM OUT OF GROUP AND PUT INTO IN GROUP (OUT OF GROUP IS THE COPY OF ALL RECORDS)
        //WHEN CONFIRMED, WRITE A NEW GROUP WITH ALL THE USERS
        //More memory efficient methods will exist but this is simplest for this feature
        
        ArrayList<StudentStruct> studentRecordsCopy = new ArrayList<StudentStruct>();
        ArrayList<StudentStruct> selectedStudentsArray = new ArrayList<StudentStruct>();
        
        for(StudentStruct tempStudent : studentRecords){
            studentRecordsCopy.add(tempStudent);
        }
        
        ArrayList<StudentStruct> selectedStudents = new ArrayList<StudentStruct>();
        
        JPanel panePanel = new JPanel(null);
        panePanel.setSize(1200,800);
        panePanel.setMinimumSize(new Dimension(1200,800));
        panePanel.setMaximumSize(new Dimension(1200,800));
        panePanel.setPreferredSize(new Dimension(1200,800));
        
        String[] columnNames = {"UserID", "First-Name", "Last-Name"};
        
        DefaultTableModel allStudentsTableModel = new DefaultTableModel(columnNames, 0);
        JTable allStudentsTable = new JTable(allStudentsTableModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer,row,column);
                comp.setBackground(new Color(40,40,40));
                comp.setForeground(Color.WHITE);
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        allStudentsTable.getTableHeader().setReorderingAllowed(false);
        allStudentsTable.getTableHeader().setBackground(new Color(40,40,40));
        allStudentsTable.getTableHeader().setForeground(Color.WHITE);
        allStudentsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        allStudentsTable.setBackground(new Color(40,40,40));
        allStudentsTable.setFillsViewportHeight(true);
        
        DefaultTableModel selectedStudentsTableModel = new DefaultTableModel(columnNames, 0);
        JTable selectedStudentsTable = new JTable(selectedStudentsTableModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer,row,column);
                comp.setBackground(new Color(40,40,40));
                comp.setForeground(Color.WHITE);
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        selectedStudentsTable.getTableHeader().setBackground(new Color(40,40,40));
        selectedStudentsTable.getTableHeader().setForeground(Color.WHITE);
        selectedStudentsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        selectedStudentsTable.setBackground(new Color(40,40,40));
        selectedStudentsTable.setFillsViewportHeight(true);
        selectedStudentsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane selectedStudentsScroll = new JScrollPane(selectedStudentsTable);
        
        allStudentsTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
                
            }
            @Override
            public void mousePressed(MouseEvent e){
                if (!(allStudentsTable.getSelectedRow() < 0)){
                    int index = allStudentsTable.getSelectedRow();
                    
                    StudentStruct clickedStudent = studentRecordsCopy.get(index);
                    
                    selectedStudents.add(clickedStudent);
                    
                    String[] tempRow = new String[3];
                    tempRow[0] = clickedStudent.getUserID();
                    tempRow[1] = clickedStudent.getFirstName();
                    tempRow[2] = clickedStudent.getLastName();
                    
                    selectedStudentsTableModel.addRow(tempRow);
                    selectedStudentsArray.add(clickedStudent);
                    allStudentsTableModel.removeRow(index);
                    studentRecordsCopy.remove(index);
                    
                    
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                
            }
            @Override
            public void mouseExited(MouseEvent e){
                
            }
            @Override
            public void mouseClicked(MouseEvent e){
                
            }
        });
        
        selectedStudentsTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
                
            }
            @Override
            public void mousePressed(MouseEvent e){
                if (!(selectedStudentsTable.getSelectedRow() < 0)){
                    int index = selectedStudentsTable.getSelectedRow();
                    
                    StudentStruct clickedStudent = selectedStudentsArray.get(index);
                    
                    selectedStudents.add(clickedStudent);
                    
                    String[] tempRow = new String[3];
                    tempRow[0] = clickedStudent.getUserID();
                    tempRow[1] = clickedStudent.getFirstName();
                    tempRow[2] = clickedStudent.getLastName();
                    
                    allStudentsTableModel.addRow(tempRow);
                    selectedStudentsTableModel.removeRow(index);
                    selectedStudentsArray.remove(index);
                    studentRecordsCopy.add(clickedStudent);
                    
                    
                    
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                
            }
            @Override
            public void mouseExited(MouseEvent e){
                
            }
            @Override
            public void mouseClicked(MouseEvent e){
                
            }
        });
        
        JScrollPane allStudentsScroll = new JScrollPane(allStudentsTable);
        
        
        
        JLabel allStudentsLbl = new JLabel("Student List:");
        allStudentsLbl.setSize(80,20);
        allStudentsLbl.setLocation(10,10);
        panePanel.add(allStudentsLbl);
        
        
        allStudentsTable.setSize(500,600);
        allStudentsTable.setMinimumSize(new Dimension(500,600));
        
        
        allStudentsScroll.setSize(500,600);
        allStudentsScroll.setMinimumSize(new Dimension(500,600));
        
        
        allStudentsTable.setLocation(10,40);
        allStudentsScroll.setLocation(10,40);
        
        JLabel selectedStudentsLbl = new JLabel("Group Member List:");
        selectedStudentsLbl.setSize(120,20);
        selectedStudentsLbl.setLocation(540,10);
        panePanel.add(selectedStudentsLbl);
        
        selectedStudentsTable.setSize(500,600);
        selectedStudentsTable.setMinimumSize(new Dimension(500,600));
        
        
        selectedStudentsScroll.setSize(500,600);
        selectedStudentsScroll.setMinimumSize(new Dimension(500,600));
        ;
        
        selectedStudentsTable.setLocation(540,40);
        selectedStudentsScroll.setLocation(540,40);
        
        panePanel.add(selectedStudentsScroll);
        panePanel.add(allStudentsScroll);
        
        for (StudentStruct student : studentRecordsCopy){
            String[] tempRow = { student.getUserID(), student.getFirstName(), student.getLastName() };
            
            allStudentsTableModel.addRow(tempRow);
            
                
        }
        
        selectedStudentsTable.setDefaultEditor(Object.class,null);
        allStudentsTable.setDefaultEditor(Object.class,null);
        
        JTextField groupNameField = new JTextField();
        groupNameField.setSize(400,30);
        groupNameField.setMinimumSize(new Dimension(400,30));
        groupNameField.setLocation(10,670);
        JLabel groupNameLbl = new JLabel("Group Name:");
        groupNameLbl.setSize(120,20);
        groupNameLbl.setMinimumSize(new Dimension(120,20));
        groupNameLbl.setLocation(10,650);
        panePanel.add(groupNameField);
        panePanel.add(groupNameLbl);        
        
        //LATER SET TABLE CELLS TO NOT BE HIGHLIGHTED
        
        
        //panePanel.add(allStudentsTable);
        //panePanel.add(selectedStudentsTable);
        
        //JButton confirmGroupCreateBtn = new JButton("Create Group");
        //confirmGroupCreateBtn.setSize(150,30);
        //confirmGroupCreateBtn.setLocation(525,650);
        //panePanel.add(confirmGroupCreateBtn);
         
        int result = assignmentPane.showConfirmDialog(null, panePanel, "Create Group", JOptionPane.OK_CANCEL_OPTION);
        String studentIdList = "";
        JOptionPane errorBox = new JOptionPane();
        
        if(result == JOptionPane.OK_OPTION && selectedStudentsArray.size() > 0){
            //PLAN:
            //Get all content from tables and text fields, and validate the data.
            //If valid then increment the group ID value and update the text file
            //Link the new group ID, the teacher ID which is the current user, all of the student IDs in the selected table, and instantiate a new assignment group and add it to the
            //arraylist
            //Write to file
            //Read files again
            
            //Get the IDs from selected list
            
            for(StudentStruct student : selectedStudentsArray){
                studentIdList += (student.getUserID() + ",");
            }
            
            studentIdList = studentIdList.substring(0, studentIdList.length() - 1);
            
            String groupName = groupNameField.getText();
            String assignmentGroupID = "";
            
            if(!(groupName.length() > 0)){
                errorBox.showMessageDialog(null, "Enter a title", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(groupName.contains(",")){
                errorBox.showMessageDialog(null, "Group names cannot contains a ',' character", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ProfanityFilter pf = new ProfanityFilter();
            if(pf.isProfane(groupName)){
                errorBox.showMessageDialog(null, "Group name contains profanity", "Cancelled", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!(studentIdList.length() > 0 )){
                errorBox.showMessageDialog(null, "Select Students to add to a group", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            try{
                //Check if the name already exists
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\AssignmentGroup-Teacher-Student.txt"));
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[1].equals(groupName)){
                        errorBox.showMessageDialog(null, "Group name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        br.close();
                        return;
                    }
                    nextLine = br.readLine();
                }
                br.close();
                //If the name doesnt exist then we can write it to file
                
                //Increment the groupID value and turn it into an ID
                
                br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
                nextLine = br.readLine();
                ArrayList<String> fileContent = new ArrayList<String>();
                
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals("AssignmentGroupCount")){
                        int currentCount = Integer.parseInt(splitLine[1]);
                        currentCount += 1;
                        // Create the count with zeroes in front
                        String idNum = ""+currentCount;
                        while(idNum.length() < 5){
                            idNum = "0" + idNum;
                        }
                        fileContent.add("AssignmentGroupCount,"+idNum);
                        assignmentGroupID = "L" + idNum;
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\InitialisationData.txt"));
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                String teacherID = passedID; //# CHANGE TO CURRENT ID LATER
                FileWriter fw = new FileWriter("TextFiles\\AssignmentGroup-Teacher-Student.txt", true);
                fw.write(assignmentGroupID +","+ groupName +"," +teacherID + "," + studentIdList+"\n");
                System.out.println("WroteLine: "+assignmentGroupID+","+groupName+","+studentIdList);
                fw.close();
                
                ArrayList<String> tempIdList = new ArrayList<String>();
                
                for(String student : studentIdList.split(",")){
                    tempIdList.add(student);
                }
                //# REPLACE T00000 WITH CURRENT USER ID LATER!!!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                AssignmentGroup newGroup = new AssignmentGroup(assignmentGroupID, groupName, passedID, tempIdList);
                
                assignmentGroupList.add(newGroup);
                
            }catch(Exception e){
                e.printStackTrace();
            }   
        }
    }
    
    //# TEMPORARY METHOD TO SPLIT TYPES OF ASSIGNMENT VIEW UNTIL ACCESS LEVELS ARE IMPLEMENTED.
    
    //# ADD REMOVING ATTACHED DOCUMENTS
    
    public void viewAssignmentStudent(String assignmentID, String currentUserID){
        //# ADD UNDO HAND IN FOR WHEN COMPLETED IS TRUE.
        
        // Get selected assignment
        Assignment selectedAssignment = null;
        
        for(AssignmentGroup group : assignmentGroupList){
            for(Assignment assignment : group.getAssignmentList()){
                if(assignment.getAssignmentID().equals(assignmentID)){
                    selectedAssignment = assignment;
                }
            }
        }
        
        JOptionPane errorBox = new JOptionPane();
        if(selectedAssignment == null){
            errorBox.showMessageDialog(null, "ASSIGNMENT NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panePanel = new JPanel(null);
        panePanel.setSize(1200,800);
        panePanel.setMinimumSize(new Dimension(1200,800));
        panePanel.setMaximumSize(new Dimension(1200,800));
        panePanel.setPreferredSize(new Dimension(1200,800));
        
        JLabel assignmentTitleLbl = new JLabel ("Title: " + selectedAssignment.getTitle());
        assignmentTitleLbl.setSize(700,20);
        assignmentTitleLbl.setLocation(10,30);
        panePanel.add(assignmentTitleLbl);
        
        JLabel dueDateLbl = new JLabel("Due: "+selectedAssignment.getDueDate());
        dueDateLbl.setSize(120,20);
        dueDateLbl.setLocation(10,60);
        panePanel.add(dueDateLbl);
        
        JTextArea ta = new JTextArea();
        ta.setSize(900,600);
        ta.setMinimumSize(new Dimension(900,600));
        ta.setMaximumSize(new Dimension(900,600));
        ta.setPreferredSize(new Dimension(900,600));
        ta.setLocation(10,140);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        panePanel.add(ta);
        
        ta.setText(selectedAssignment.getAssignmentContent());
        
        JLabel attachedLbl = new JLabel("Attached Docs:");
        attachedLbl.setSize(150,30);
        attachedLbl.setMinimumSize(new Dimension(150,30));
        attachedLbl.setLocation(930,140);
        panePanel.add(attachedLbl);
        
        JButton viewAttachedDocBtn = new JButton("Open Doc");
        viewAttachedDocBtn.setSize(100,20);
        viewAttachedDocBtn.setLocation(930,210);
        String[] pathBoxItems = new String[selectedAssignment.getDocFilePaths().size()];
        for(int i = 0; i < pathBoxItems.length;i++){
            String currentItem = selectedAssignment.getDocFilePaths().get(i);
            String[] splitItem = currentItem.split("\\\\");
            
            pathBoxItems[i] = splitItem[(splitItem.length) - 1];
            
        }
        
        JComboBox pathBox = new JComboBox(pathBoxItems);
        pathBox.setBackground(new Color(40,40,40));
        pathBox.setForeground(Color.WHITE);
        pathBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        pathBox.setSize(200,30);
        pathBox.setMinimumSize(new Dimension(200,30));
        pathBox.setLocation(930,170);
        panePanel.add(pathBox);
        
        viewAttachedDocBtn.addActionListener(AL->this.openWordDoc(pathBox.getSelectedItem().toString()));
        panePanel.add(viewAttachedDocBtn);
        
        //GOING TO NEED TO HAVE HAND IN ASSIGNMENT BUTTON IF ITS INCOMPLETE< OR UNDO HAND IN IF ITS COMPLETE.
        
        JButton handInAssignmentBtn = new JButton("Hand In");
        JButton undoHandInAssignmentBtn = new JButton("Undo Hand-In");
        
        JLabel attachWorkLabel = new JLabel("Attach Work:");
        String[] attachWorkHeadings = {"Attached Work"};
        DefaultTableModel attachWorkModel = new DefaultTableModel(attachWorkHeadings,0);
        JTable attachWorkTable = new JTable(attachWorkModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(new Color(40,40,40));
                comp.setForeground(Color.WHITE);
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        attachWorkTable.getTableHeader().setReorderingAllowed(false);
        JButton attachWorkBtn = new JButton("Attach Work");
        
        
        
        //FIND IF ASSIGNMENT IS HANDED IN OR NOT (NOT BEST PLACE TO DO THIS BUT ITLL DO)
        
        
        handInAssignmentBtn.setSize(120,30);
        handInAssignmentBtn.setMinimumSize(new Dimension(200,30));
        handInAssignmentBtn.setLocation(1050,10);
        handInAssignmentBtn.addActionListener(AL->handInAssignment(attachWorkModel, currentUserID, assignmentID, handInAssignmentBtn, undoHandInAssignmentBtn)); 
        panePanel.add(handInAssignmentBtn);
        
        undoHandInAssignmentBtn.setSize(120,30);
        undoHandInAssignmentBtn.setMinimumSize(new Dimension(200,30));
        undoHandInAssignmentBtn.setLocation(1050,10);
        undoHandInAssignmentBtn.addActionListener(AL->undoHandInAssignment(currentUserID, assignmentID, attachWorkTable, handInAssignmentBtn, undoHandInAssignmentBtn)); 
        panePanel.add(undoHandInAssignmentBtn);
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Assignment-Student-Completed.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                
                if(splitLine[0].equals(assignmentID) && splitLine[1].equals(currentUserID)){
                    
                    if(splitLine[2].equals("0")){
                        handInAssignmentBtn.setVisible(true);
                        undoHandInAssignmentBtn.setVisible(false);
                    }else if(splitLine[2].equals("1")){
                        undoHandInAssignmentBtn.setVisible(true);
                        handInAssignmentBtn.setVisible(false);
                    }
                    
                }
                
                
                nextLine = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //attachWorkLabel.setSize(120,30);
        //attachWorkLabel.setLocation(930,290);
        //panePanel.add(attachWorkLabel);
        
        attachWorkBtn.setSize(120,20);
        attachWorkBtn.setLocation(930,290);
        attachWorkBtn.addActionListener(AL->{handInAssignmentBtn.setVisible(true); undoHandInAssignmentBtn.setVisible(false);});
        attachWorkBtn.addActionListener(AL->attachWordDoc(attachWorkModel));
        panePanel.add(attachWorkBtn);
        
        
        
        attachWorkTable.setSize(200,400);
        attachWorkTable.setMinimumSize(new Dimension(200,400));
        JOptionPane deleteRowPopup = new JOptionPane();
        attachWorkTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
                
            }
            @Override
            public void mousePressed(MouseEvent e){
                int result = deleteRowPopup.showConfirmDialog(null, "Remove " + attachWorkModel.getValueAt(attachWorkTable.getSelectedRow(), 0) +"?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION){
                    attachWorkModel.removeRow(attachWorkTable.getSelectedRow());
                    deleteRowPopup.showMessageDialog(null, "Hand in work again to update", "", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    deleteRowPopup.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            @Override
            public void mouseReleased(MouseEvent e){
                
            }
            @Override
            public void mouseExited(MouseEvent e){
                
            }
            @Override
            public void mouseClicked(MouseEvent e){
                
            }
        });
        JScrollPane attachWorkTableScroll = new JScrollPane(attachWorkTable);
        
        attachWorkTableScroll.setSize(200,420);
        attachWorkTableScroll.setLocation(930,320);
        
        attachWorkTable.getTableHeader().setBackground(new Color(40,40,40));
        attachWorkTable.getTableHeader().setForeground(Color.WHITE);
        attachWorkTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        attachWorkTable.setBackground(new Color(40,40,40));
        attachWorkTableScroll.setBackground(new Color(40,40,40));
        attachWorkTableScroll.getViewport().setBackground(new Color(40,40,40));
        panePanel.add(attachWorkTableScroll);
        
        // READ EXISTING ASSIGNMENT'S ATTACHED DOCUMENTS FROM FILE INTO THE TABLE
        
        //# ADD TABLE MOUSE LISTENER TO DELETE ROWS
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Assignment-Student-Completed.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(assignmentID) && splitLine[1].equals(currentUserID)){ 
                    //# CHANGE TO CURRENT USER ID LATER
                    for(int i = 3; i < splitLine.length; i++){
                        String[] row = new String[1];
                        row[0] = splitLine[i];
                        attachWorkModel.addRow(row);
                        
                    }
                }
                nextLine = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        assignmentPane.showMessageDialog(null, panePanel, "Viewing Assignment", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    public void undoHandInAssignment(String userID, String assignmentID, JTable attachedWorkTable, JButton handInAssignmentBtn, JButton undoHandInAssignmentBtn){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Assignment-Student-Completed.txt"));
            ArrayList<String> fileContent = new ArrayList<String>();
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                
                if(splitLine[0].equals(assignmentID) && splitLine[1].equals(userID)){
                    String attachedWork = "";
                    for(int i = 0; i < attachedWorkTable.getRowCount(); i++){
                        attachedWork += ","+attachedWorkTable.getValueAt(i,0).toString() ;
                    }
                    fileContent.add( assignmentID +"," + userID +",0"+attachedWork);
                    // if(attachedWork.length() > 1){
                        
                    // }else{
                        // fileContent.add( assignmentID +","+ userID +",0" );
                    // }
                    
                }else{
                    fileContent.add(nextLine);
                }
                nextLine = br.readLine();
            }
            br.close();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\Assignment-Student-Completed.txt"));
            for(String line : fileContent){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
            handInAssignmentBtn.setVisible(true);
            undoHandInAssignmentBtn.setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void handInAssignment(DefaultTableModel workTable, String userID, String assignmentID, JButton handInAssignmentBtn, JButton undoHandInAssignmentBtn){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Assignment-Student-Completed.txt"));
            String nextLine = br.readLine();
            ArrayList<String> fileContent = new ArrayList<String>();
            System.out.println("Started Method");
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(assignmentID) && splitLine[1].equals(userID)){
                    System.out.println("Found File");
                    String lineContent = "";
                    lineContent += assignmentID + ",";
                    lineContent += userID + ",";
                    lineContent += "1,";
                    for(int i = 0; i < workTable.getRowCount(); i++){
                        String docPath = workTable.getValueAt(i,0).toString();
                        lineContent += docPath+",";
                    }
                    lineContent = lineContent.substring(0, lineContent.length() - 2);
                    fileContent.add(lineContent);
                }else{
                    fileContent.add(nextLine);
                }
                nextLine = br.readLine();
            }
            br.close();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\Assignment-Student-Completed.txt"));
            for(String line : fileContent){
                System.out.println("WRitign lines");
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
            handInAssignmentBtn.setVisible(false);
            undoHandInAssignmentBtn.setVisible(true);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public void readAssignmentsStudent(DefaultTableModel table, JComboBox box, String passedUserID){
    try{
        
        //CREATE NEW ASSIGNMENT LISTS
        assignmentGroupList = new ArrayList<AssignmentGroup>();
        table.setRowCount(0);
        //OPEN FILE THAT LINKS GROUPS TEACHERS AND STUDENTS
        BufferedReader br = new BufferedReader(new FileReader("TextFiles\\AssignmentGroup-Teacher-Student.txt"));

        String nextLine = br.readLine();

        while(nextLine != null){
            
            //ITERATE OVER ALL THE LINES OF FILE SPLIT BY COMMA
            String[] splitLine = nextLine.split(",");
            for(int i = 3; i < splitLine.length; i++){
                //IF THE SPLITLINE CONTAINS THE CURRENT USERID THEY ARE PART OF THIS GROUP
                if(splitLine[i].equals(passedUserID)){
                    // MAKE GROUP AND ADD TO LIST AND TABLE
                    String groupID = splitLine[0];
                    String groupName = splitLine[1];
                    String teacherID = splitLine[2];
                    ArrayList<String> studentIDs = new ArrayList<String>();
                    for(int k = 3; k < splitLine.length; k++){
                        studentIDs.add(splitLine[k]);
                    }
                    AssignmentGroup foundGroup = new AssignmentGroup();
                    foundGroup.setAssignmentGroupID(groupID);
                    foundGroup.setGroupName(groupName);
                    foundGroup.setTeacherID(teacherID);
                    foundGroup.setStudentIdList(studentIDs);
                    assignmentGroupList.add(foundGroup);
                    
                }
            }
            nextLine = br.readLine();
        }
        br.close();

        // NOW WE NEED TO POPULATE THE ASSIGNMENT GROUPS

        br = new BufferedReader(new FileReader("TextFiles\\Group-Assignment.txt"));

        nextLine = br.readLine();
        while(nextLine != null){
            String[] splitLine = nextLine.split(",");
            //IF LINE HAS A GROUP ID IN THE ASSIGNMENT GROUP LIST, ADD CONTENT TO GROUP IT MATCHES

            for(AssignmentGroup group : assignmentGroupList){
                // INDEX 0 IS GROUP ID
                if(group.getAssignmentGroupID().equals(splitLine[0])){
                    // INSTANTIATE ALL ASSIGNMENTS IN THE LINE SPLIT AND ADD TO THE GROUP

                    for(int i = 1; i < splitLine.length; i++){
                        //READER FOR ASSIGNMENTS SEPERATELY
                        BufferedReader br2 = new BufferedReader(new FileReader("TextFiles\\Assignment-Content.txt"));
                        String nextLine2 = br2.readLine();
                        while(nextLine2 != null){
                            String[] splitLine2 = nextLine2.split("~}@")[0].split(",");
                            if(splitLine2[0].equals(splitLine[i])){
                                // GET ATTRIBUTES FROM LINE
                                String foundAssignmentID = splitLine2[0];
                                String foundTitle = splitLine2[1];
                                String foundDate = splitLine2[2];
                                String foundTeacherName = splitLine2[3];
                                String foundCompletion = splitLine2[4];
                                ArrayList<String> docFilePaths = new ArrayList<String>();
                                for(int j = 5; j < splitLine2.length; j++){
                                    docFilePaths.add(splitLine2[j]);
                                }
                                String assignmentContent = nextLine2.split("~}@")[1];
                                // Constructor for created assignment
                                Assignment foundAssignment = new Assignment(foundAssignmentID, foundTitle, foundDate,
                                                                            foundTeacherName, Boolean.parseBoolean(foundCompletion),
                                                                            docFilePaths, assignmentContent,
                                                                            group.getStudentIdList());

                                group.addAssignment(foundAssignment);
                            }
                            nextLine2 = br2.readLine();
                        }
                    }

                }
            }
            nextLine = br.readLine();
        }

        for(AssignmentGroup group : assignmentGroupList){
            for(Assignment assignment : group.getAssignmentList()){
                String[] row = new String[5];
                //row[0] = assignment.getTitle();
                //row[1] = assignment.getAssignmentID();
                row[0] = group.getGroupName();
                row[1] = assignment.getTitle();
                row[2] = assignment.getDueDate();
                ArrayList<String> fileList = assignment.getDocFilePaths();
                if(fileList.size() == 1){
                    String[] fileSplit = fileList.get(0).split("\\\\");
                    row[3] = fileSplit[fileSplit.length - 1];
                }else if(fileList.size() == 0){
                    row[3] = "None Attached";
                }else{
                    row[3] = "Several Attachments";
                }

                row[4] = assignment.getAssignmentID();
                table.addRow(row);
            }
        }
        // MAYBE REMOVE THIS 
        box.removeAllItems();
        for(AssignmentGroup group : assignmentGroupList){
            
            box.addItem(group.getGroupName().toString());
        }
        
    }catch(Exception e){
        e.printStackTrace();
    }
}
    
    //# TEMPORARY ID FOR IS IT READING TEACHER OR STUDENT
    public void readAssignments(DefaultTableModel table, String passedUserID){
        try{
            
            assignmentGroupList = new ArrayList<AssignmentGroup>();
            table.setRowCount(0);
            
            //FIRST WE NEED TO CREATE THE GROUPS THAT ARE ASSOCIATED WITH THE CURRENT USER
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\AssignmentGroup-Teacher-Student.txt"));
    
            String nextLine = br.readLine();
            
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                //# REPLACE WITH GETTING CURRENT USER ID LATER
                // If we found a group associated with the user, get its user IDs and instantiate a new group
                if(splitLine[2].equals(passedUserID)){
                    AssignmentGroup currGroup = new AssignmentGroup();
                    currGroup.setTeacherID(passedUserID); // REPLACE WITH CURRENT USER ID LATER
                    currGroup.setAssignmentGroupID(splitLine[0]);
                    currGroup.setGroupName(splitLine[1]);
                    for(int i = 3; i < splitLine.length; i++){
                        currGroup.addStudentID(splitLine[i]);
                    }
                    assignmentGroupList.add(currGroup);
                }
                nextLine = br.readLine();
            }
            br.close();
            
            //NEXT WE NEED TO FIND THE ASSIGNMENTS ASSOCIATED WITH THE GROUP

            br = new BufferedReader(new FileReader("TextFiles\\Group-Assignment.txt"));
            nextLine = br.readLine();
            
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                //IF LINE HAS A GROUPID IN THE LIST ADD THE FILES TO THE GROUP
    
                for(AssignmentGroup group : assignmentGroupList){
                    if(group.getAssignmentGroupID().equals(splitLine[0])){
                        //IF THE FIRST ITEM MATCHES THE GROUP ID INSTANTIATE ALL THE ASSIGNMENTS AND ADD IT TO ITS LIST
    
                        for(int i = 1; i < splitLine.length; i++){
                            //NOW CREATE THE ASSIGNMENT OBJECT FOR EACH ID
                            BufferedReader br2 = new BufferedReader(new FileReader("TextFiles\\Assignment-Content.txt"));
                            String nextLine2 = br2.readLine();
                            while(nextLine2 != null){
                                String[] splitLine2 = nextLine2.split(",");
                                //IF THE CURR ASSIGNMENT ID FROM SPLITLINE1 EQUALS THE CURR ASSIGNMENTID IN SPLITLINE 2 THEN
                                //USE THE DATA ON THIS LINE FROM SPLITLINE2 TO CREATE AN ASSIGNMENT OBJECT
                                if(splitLine2[0].equals(splitLine[i])){
                                    //GET THE FIRST 5 ASSIGNMENT ATTRIBUTES
                                    String foundAssignmentID = splitLine2[0];
                                    String foundTitle = splitLine2[1];
                                    String foundDate = splitLine2[2];
                                    String foundTeacherName = splitLine2[3];
                                    String foundCompletion = splitLine2[4];
                                    //GETTING THE FILEPATHS IS A LITTLE TRICKER DUE TO ITS VARIABLE QUANTITIES
                                    //SPLIT BEFORE FILE CONTENT AND THEN GET FROM INDEX 5 TO END FOR ALL PATHS
                                    ArrayList<String> docFilePaths = new ArrayList<String>();
                                    String firstHalf = nextLine2.split("~}@")[0];
                                    String[] splitFirstHalf = firstHalf.split(",");
                                    for(int j = 5; j < splitFirstHalf.length; j++){
                                        docFilePaths.add(splitFirstHalf[j]);
                                    }
                                    String assignmentContent = nextLine2.split("~}@")[1];
                                    // Constructor for created assignment
                                    Assignment foundAssignment = new Assignment(foundAssignmentID, foundTitle, foundDate,
                                                                                foundTeacherName, Boolean.parseBoolean(foundCompletion),
                                                                                docFilePaths, assignmentContent,
                                                                                group.getStudentIdList());
    
                                    group.addAssignment(foundAssignment);
                                }
                                nextLine2 = br2.readLine();
                            }
    
                        }
                    }
                }
                nextLine = br.readLine();
            }
            
            //NOW ITERATE OVER ALL ASSIGNMENTS OF EACH GROUP AND ADD THEM AS A ROW TO THE TABLE

            for(AssignmentGroup group : assignmentGroupList){
                for(Assignment assignment : group.getAssignmentList()){
                    String[] row = new String[5];
                    row[0] = group.getGroupName();
                    row[1] = assignment.getTitle();
                    row[2] = assignment.getDueDate();
                    ArrayList<String> fileList = assignment.getDocFilePaths();
                    if(fileList.size() == 1){
                        String[] fileSplit = fileList.get(0).split("\\\\");
                        row[3] = fileSplit[fileSplit.length - 1];
                    }else if(fileList.size() == 0){
                        row[3] = "None Attached";
                    }else{
                        row[3] = "Several Attachments";
                    }
    
                    row[4] = assignment.getAssignmentID();
                    table.addRow(row);
                
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
}
