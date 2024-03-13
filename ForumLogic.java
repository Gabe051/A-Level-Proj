import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.time.*;
import java.awt.Component;
import javax.swing.table.*;
import java.awt.Color;
import javax.swing.border.*;
import javax.swing.BorderFactory;
//import java.time.format.DateTimeFormatter;



public class ForumLogic
{
    ArrayList<String> forumGroupIDs = new ArrayList<String>();
    String currGroupID;
    
    ArrayList<PostF> rootPosts = new ArrayList<PostF>();
    
    public void createRootPost(String currentGroup, String currUserID){
        //POPUP TO FILL OUT TO MAKE POST STUFF, THEN INCREMENT POST ID VALUE THEN WRITE TO FILE AND ADD TO CURRENT
        JOptionPane rootPostPane = new JOptionPane();
        JPanel textPanel = new JPanel(null);
        textPanel.setSize(640,240);
        textPanel.setMinimumSize(new Dimension(640,240));
        textPanel.setMaximumSize(new Dimension(640,240));
        textPanel.setPreferredSize(new Dimension(640,240));
        JTextArea ta = new JTextArea();
        ta.setSize(640,240);
        ta.setMinimumSize(new Dimension(640,240));
        ta.setMaximumSize(new Dimension(640,240));
        ta.setPreferredSize(new Dimension(640,240));
        textPanel.setLayout(new BorderLayout());
        textPanel.add(ta);
        
        int result = rootPostPane.showConfirmDialog(null, textPanel, "Write your post's text content", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            String textContent = ta.getText();
            // Increment the post ID and write new entry to the root post file
            
            String postID = "";
            ProfanityFilter pf = new ProfanityFilter();
            if(pf.isProfane(textContent)){
                JOptionPane.showMessageDialog(null, "Text contains profanity so will not be posted", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
                String nextLine = br.readLine();
                ArrayList<String> fileContent = new ArrayList<String>();
                
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals("PostFcount")){
                        //Increment value and rewrite
                        int currentCount = Integer.parseInt(splitLine[1]);
                        currentCount++;
                        String idNum = ""+currentCount;
                        while(idNum.length() < 5){
                            idNum = "0" + idNum;
                        }
                        fileContent.add("PostFcount,"+idNum);
                        postID = "P" + idNum;
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
                // WRITE POST ID TO THE CURRENT GROUP
                
                br = new BufferedReader(new FileReader("TextFiles\\fGroup-History.txt"));
                
                nextLine = br.readLine();
                
                fileContent = new ArrayList<String>();
                
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[0].equals(currentGroup)){
                        fileContent.add(nextLine+","+postID);
                    }else{
                        fileContent.add(nextLine);
                    }
                    nextLine = br.readLine();
                }
                br.close();
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\fGroup-History.txt"));
                for(String line : fileContent){
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                
                //NOW TO WRITE THE ROOT POST TO THE ROOT POST FILE ITSELF
                
                bw = new BufferedWriter(new FileWriter("TextFiles\\fPostRoots-Content.txt", true));
                
                
                LocalDateTime ldt = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedDateTime = ldt.format(dtf);
                
                bw.write(postID +"," + currUserID +"," + formattedDateTime + "~}@" +textContent);
                bw.newLine();
                bw.close();
                
                rootPostPane.showMessageDialog(null, "Posted");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            rootPostPane.showMessageDialog(null, "Cancelled post");
            return;
        }
    }
    
    public void createForumGroup(String teacherID, ArrayList<StudentStruct> studentRecords){
        JOptionPane createForumPane = new JOptionPane();
        
        JPanel panePanel = new JPanel(null);
        panePanel.setSize(1200,800);
        panePanel.setMinimumSize(new Dimension(1200,800));
        panePanel.setMaximumSize(new Dimension(1200,800));
        panePanel.setPreferredSize(new Dimension(1200,800));
        
        String[] columnNames = {"UserID" , "First-Name", "Last-Name"};
        
        ArrayList<StudentStruct> studentRecordsCopy = new ArrayList<StudentStruct>();
        
        ArrayList<StudentStruct> selectedStudentsArray = new ArrayList<StudentStruct>();
        
        for(StudentStruct tempStudent : studentRecords){
            studentRecordsCopy.add(tempStudent);
        }
        
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
        allStudentsTable.getTableHeader().setForeground(Color.WHITE);
        allStudentsTable.getTableHeader().setBackground(new Color(40,40,40));
        allStudentsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        allStudentsTable.setBackground(new Color(40,40,40));
        allStudentsTable.setFillsViewportHeight(true);
        
        JScrollPane allStudentsTableScroll = new JScrollPane(allStudentsTable);
        
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
        selectedStudentsTable.getTableHeader().setReorderingAllowed(false);
        selectedStudentsTable.getTableHeader().setForeground(Color.WHITE);
        selectedStudentsTable.getTableHeader().setBackground(new Color(40,40,40));
        selectedStudentsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        selectedStudentsTable.setBackground(new Color(40,40,40));
        selectedStudentsTable.setFillsViewportHeight(true);
        JScrollPane selectedStudentsTableScroll = new JScrollPane(selectedStudentsTable);
        
        
        allStudentsTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if( !(allStudentsTable.getSelectedRow() < 0)){
                    int index = allStudentsTable.getSelectedRow();
                    
                    StudentStruct clickedStudent = studentRecordsCopy.get(index);
                    
                    
                    
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
        });
        
        selectedStudentsTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if( !(selectedStudentsTable.getSelectedRow() < 0)){
                    int index = selectedStudentsTable.getSelectedRow();
                    
                    StudentStruct clickedStudent = selectedStudentsArray.get(index);
                    
                   
                    
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
        });
        
        JLabel allStudentsLbl = new JLabel("Student List:");
        allStudentsLbl.setSize(80,20);
        allStudentsLbl.setLocation(10,10);
        panePanel.add(allStudentsLbl);
        
        allStudentsTable.setSize(500,600);
        allStudentsTable.setMinimumSize(new Dimension(500,600));
        
        allStudentsTableScroll.setSize(500,600);
        allStudentsTableScroll.setMinimumSize(new Dimension(500,600));
        
        allStudentsTableScroll.setLocation(10,40);
        
        JLabel selectedStudentsLbl = new JLabel("Selected Students:");
        
        selectedStudentsLbl.setSize(120,20);
        selectedStudentsLbl.setLocation(540,10);
        panePanel.add(selectedStudentsLbl);
        
        selectedStudentsTable.setSize(500,600);
        selectedStudentsTable.setMinimumSize(new Dimension(500,600));
        
        selectedStudentsTableScroll.setSize(500,600);
        selectedStudentsTableScroll.setMinimumSize(new Dimension(500,600));
        
        selectedStudentsTableScroll.setLocation(540,40);
        
        panePanel.add(selectedStudentsTableScroll);
        panePanel.add(allStudentsTableScroll);
        
        allStudentsTableModel.setRowCount(0);
        for(StudentStruct student : studentRecordsCopy){
            String[] tempRow = { student.getUserID(), student.getFirstName(), student.getLastName() };
            allStudentsTableModel.addRow(tempRow);
        }
        
        JTextField tfGroupName = new JTextField();
        tfGroupName.setSize(400,30);
        tfGroupName.setMinimumSize(new Dimension(400,30));
        tfGroupName.setLocation(10,670);
        JLabel groupNameLbl = new JLabel("Group Name:");
        groupNameLbl.setSize(120,20);
        groupNameLbl.setMinimumSize(new Dimension(120,20));
        groupNameLbl.setLocation(10,650);
        panePanel.add(tfGroupName);
        panePanel.add(groupNameLbl);        
        
        int result = createForumPane.showConfirmDialog(null, panePanel, "Create Group", JOptionPane.OK_CANCEL_OPTION);
        String studentIdList = "";
        JOptionPane errorBox = new JOptionPane();
        
        if(result == JOptionPane.OK_OPTION && selectedStudentsArray.size() > 0){
            
            for(StudentStruct student : selectedStudentsArray){
                studentIdList += (student.getUserID() + ",");
            }
            
            studentIdList = studentIdList.substring(0,studentIdList.length() - 1);
            
            String groupName = tfGroupName.getText();
            String forumGroupID = "";
            
            if(!(groupName.length() > 0)){
                errorBox.showMessageDialog(null, "Enter a title", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(!(studentIdList.length() > 0)){
                errorBox.showMessageDialog(null, "Select Students to add to a group", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(groupName.contains(",")){
                errorBox.showMessageDialog(null, "Cannot contain the character ','", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ProfanityFilter pf = new ProfanityFilter();
            if(pf.isProfane(groupName)){
                errorBox.showMessageDialog(null, "Group name contains profanity", "Cancelled", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try{
                //Check if the name already exists
                
                BufferedReader br = new BufferedReader(new FileReader("TextFiles\\fGroup-UserID.txt"));
                String nextLine = br.readLine();
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    if(splitLine[1].equals(groupName)){
                        errorBox.showMessageDialog(null, "Group name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    nextLine = br.readLine();
                }
                br.close();
                //GET ID FOR THE GROUP BEING MADE
                br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
                nextLine = br.readLine();
                ArrayList<String> fileContent = new ArrayList<String>();
                
                while(nextLine != null){
                    String[] splitLine = nextLine.split(",");
                    
                    if(splitLine[0].equals("FGroupCount")){
                        int currentCount = Integer.parseInt(splitLine[1]);
                        currentCount++;
                        String idNum = "" + currentCount;
                        while(idNum.length() < 5){
                            idNum = "0" + idNum;
                        }
                        fileContent.add("FGroupCount,"+idNum);
                        forumGroupID = "G"+idNum;
                        
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
                
                //NOW CREATE THE GROUP AND WRITE IT TO THE FILE
                String groupLine = forumGroupID+"," + groupName +"," + teacherID +","+ studentIdList;
                
                BufferedWriter bw2 = new BufferedWriter(new FileWriter("TextFiles\\fGroup-UserID.txt", true));
                bw2.write(groupLine);
                bw2.newLine();
                bw2.close();
                
                
                //NOW WRITE THE GROUP TO GROUP HISTORY FILE
                
                bw2 = new BufferedWriter(new FileWriter("TextFiles\\fGroup-History.txt", true));
                System.out.println(forumGroupID+":");
                bw2.write(forumGroupID+":");
                bw2.newLine();
                bw2.close();
                
                //
                
                errorBox.showMessageDialog(null, "Group " + groupName +" created", "Complete", JOptionPane.INFORMATION_MESSAGE);
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }

    public ArrayList<String> getGroupUsers(String groupID){
        ArrayList<String> userIDs = new ArrayList<String>();
          try{
              BufferedReader br = new BufferedReader(new FileReader("TextFiles\\fGroup-UserID.txt"));
              String nextLine = br.readLine();
              while(nextLine != null){
                  String[] splitLine = nextLine.split(":");
                  if(splitLine[0].equals(groupID)){
                      for(String userID : splitLine[1].split(",")){
                          userIDs.add(userID);
                      }
                  }
                  nextLine = br.readLine();
              }
          }catch(Exception e){
              e.printStackTrace();
          }
          
          return userIDs;
    }
    
    public ArrayList<PostF> getPostHistory(String groupID){
        rootPosts = new ArrayList<PostF>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\fGroup-History.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(":");
                if(splitLine[0].equals(groupID)){
                    for(String postID : splitLine[1].split(",")){
                        
                        rootPosts.add(constructPost(postID,"TextFiles\\fPostRoots-Content.txt"));
                    }
                }
                nextLine = br.readLine();
            }
            
            for(PostF posts : rootPosts){
                
                String postString = "";
                
                for(int i = 0; i < posts.getPostContent().split("~}@").length; i++){
                        postString += (posts.getPostContent().split("~}@")[i]);
                }
                
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return rootPosts;
    }
    
    
    
    public PostF constructPost(String postID, String filePath){
        ArrayList<String> replyIDs = new ArrayList<String>();
        ArrayList<PostF> replyPosts = new ArrayList<PostF>();
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(postID)){
                    String opUserID = splitLine[1];
                    String dateTime = splitLine[2];
                   
                    if(splitLine.length > 3){
                        for(int i = 3; i < splitLine.length; i++){
                            if(i == splitLine.length-1){
                                
                                replyIDs.add(splitLine[i].split("~}@")[0]);
                            }else{

                                replyIDs.add(splitLine[i]);
                            }
                        }
                    }
                    
                    String postContent = "";
                    String[] newlineSplits = splitLine[splitLine.length-1].split("~}@");
                    
                    String[] lineArray = Arrays.copyOfRange(newlineSplits, 1, newlineSplits.length);
                    
                    for(int i = 0; i < lineArray.length; i++){
                        
                        postContent = (postContent + lineArray[i] + "\n");
                    }
                    
                    if(replyIDs.size() > 0){
                        for(String replyID: replyIDs){
                            

                            replyPosts.add(constructPost(replyID,"TextFiles\\fReplies-Content.txt"));
                            
                        }
                        
                    }else{ 
                        return new PostF(postID, opUserID, dateTime,postContent);
                    }
                    
                    return new PostF(postID, opUserID, dateTime,postContent,replyPosts);
                }
                nextLine = br.readLine();
            }
    
        }catch(Exception e){
            e.printStackTrace();
        }
        return new PostF();
    }
    
    public void writeReply(String textContent, String userID, PostF rootPost){
        int postCount = 0;
        String newPostCount = "";
        
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
            String nextLine = br.readLine();
            ArrayList<String> lineList = new ArrayList<String>();
            
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals("PostFcount")){
                    postCount = Integer.parseInt(splitLine[1]);
                    postCount++;
                    newPostCount = "" + postCount;
                    while(("" + newPostCount).length() < 5){
                        newPostCount = ("0" + newPostCount);
                    }
                    String writeLine = "PostFcount,"+newPostCount;
                    
                    lineList.add(writeLine);
                }else{
                    lineList.add(nextLine.trim());
                }
                nextLine = br.readLine();
            }
            br.close();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\InitialisationData.txt"));
            for(String line : lineList){
                bw.write(line);
                bw.newLine();
            }
            
            bw.close();
            br = new BufferedReader(new FileReader("TextFiles\\fReplies-Content.txt"));
            nextLine = br.readLine();
            lineList = new ArrayList<String>();
            while(nextLine != null){
                lineList.add(nextLine.trim());
                nextLine = br.readLine();
            }
            br.close();
            bw = new BufferedWriter(new FileWriter("TextFiles\\fReplies-Content.txt"));
            for(String line : lineList){
                bw.write(line);
                bw.newLine();
            }
            
            
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String newTextContent = "";
            for(String line : textContent.split("\n")){
                newTextContent += ("~}@" + line);
            }
            bw.write("P"+newPostCount + "," + userID +"," + dateFormat.format(LocalDate.now()) + newTextContent);
            bw.close();
            
            br = new BufferedReader(new FileReader("TextFiles\\fPostRoots-Content.txt"));
            nextLine = br.readLine();
            lineList = new ArrayList<String>();
            
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(rootPost.getPostID())){
                    String[] delimSplit = nextLine.split("~}@");
                    String restOfLine = "";
                    for(int i = 1; i < delimSplit.length; i++){
                        
                        if(i != (delimSplit.length - 1)){
                            restOfLine += (delimSplit[i].trim() + "~}@");
                        }else{
                            restOfLine += delimSplit[i].trim();
                        }
                    }
                    lineList.add(delimSplit[0] + ",P"+newPostCount+"~}@" + restOfLine);
                }else{
                    lineList.add(nextLine.trim());
                }
                nextLine = br.readLine();
            }
            br.close();
            
            bw = new BufferedWriter(new FileWriter("TextFiles\\fPostRoots-Content.txt"));
            for(String line : lineList){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
            br = new BufferedReader(new FileReader("TextFiles\\fReplies-Content.txt"));
            nextLine = br.readLine();
            lineList = new ArrayList<String>();
            while(nextLine != null){
               
                if(nextLine.split(",")[0].equals(rootPost.getPostID())){
                   
                    String[] delimSplit = nextLine.split("~}@");
                    String restOfLine = "";
                    for(int i = 1; i < delimSplit.length;i++){
                        if(i != (delimSplit.length - 1)){
                            restOfLine += (delimSplit[i].trim() + "~}@");
                        }else{
                            restOfLine += delimSplit[i].trim();
                        }
                    }
                    lineList.add(delimSplit[0] + ",P"+newPostCount+"~}@" + restOfLine);
                }else{
                    lineList.add(nextLine.trim());
                }
                nextLine = br.readLine();
            }
            br.close();
            bw = new BufferedWriter(new FileWriter("TextFiles\\fReplies-Content.txt"));
            for(String line : lineList){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        
    }
    
    public void openReplies(String postID){
        for(PostF post : rootPosts){
            
        }
    }
    
}
