import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUI
{
    //# NOTES:
    //# ADDING SEVERAL ACTION LISTENERS TO A SINGLE COMPONENT IS LIFO
    
    //# LOGIC
    
    Controller controller;
    
    public GUI(Controller theController){
        this.controller = theController;
    }
    

    //# Data
    
    Color backgroundCol = new Color(40,40,40);
    Color foregroundCol = Color.WHITE;
    Color cellCol = new Color(83,83,83);
    String[] timetableColumns = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    
    String[] userTypes = {"Students", "Teachers", "Admins"};
    
    //# Main window and tabs:
    
    JFrame prototypeWindow = new JFrame();
    JTabbedPane screenTabs = new JTabbedPane();
    
    //# Panels:
    
    JPanel homeTestPanel = new JPanel(null);
    JPanel recordsPagePanel = new JPanel(null);
    JPanel chatPanel = new JPanel(null);
    JPanel reportPanel = new JPanel(null);
    JPanel loginPanel = new JPanel(null);
    JPanel viewReportsPanel = new JPanel(null);
    JPanel viewTimetablePanel = new JPanel(null);
    JPanel canteenPanel = new JPanel(null);
    JPanel addStudentLogPanel = new JPanel(null);
    JPanel viewStudentLogPanel = new JPanel(null);
    JPanel forumPanel = new JPanel(null);
    JPanel setAssignmentPanel = new JPanel(null);
    JPanel viewAssignmentsPanel = new JPanel(null);
    //# Home page buttons
    
    JButton chatPageBtn = new JButton("Chat");
    JButton reportPageBtn = new JButton();
    JButton viewTimetableBtn = new JButton("View Timetable");
    JButton getBalanceBtn = new JButton("Get Balance");
    JButton addBalanceBtn = new JButton("Add Balance");
    JButton canteenPageBtn = new JButton("Make Transaction");
    JButton viewStudentLogBtn = new JButton("View Student Logs");
    JButton addStudentLogBtn = new JButton("Add Student Logs");
    JButton openForumBtn = new JButton("View Forum");
    JButton openSetAssignmentBtn = new JButton("Set Assignments");
    JButton createForumGroupBtn = new JButton("Create Forum Group");
    JLabel homeLogoLbl = new JLabel();
    Font homeFont = new Font("Arial", Font.PLAIN, 40);
    //# Teacher Admin Staff Home Buttons:
    
    JButton recordsPageBtn = new JButton("Records Page");
    JButton viewReportsPageBtn = new JButton("View Reports");
    
    //# Records Page Content:
    //textfield
    
    //label
    JButton recordsHomeBtn = new JButton();
    
    //# CHAT PAGE CONTENT
    
    JTextArea chatBox = new JTextArea();
    JScrollPane chatScroll = new JScrollPane(chatBox);
    JTextArea msgBox = new JTextArea();
    JScrollPane msgScroll = new JScrollPane(msgBox);
    JButton sendMsgBtn = new JButton("Send");
    JButton loadConvoBtn = new JButton("Open Chat");
    JComboBox cbUsers = new JComboBox();
    JScrollPane cbUsersScroll = new JScrollPane(cbUsers);
    JButton chatToHomeBtn = new JButton();
    
    //# CREATE_REPORT PAGE CONTENT
    
    JTextArea taReportContent = new JTextArea();
    JScrollPane reportContentScroll = new JScrollPane(taReportContent);
    JButton createReportBtn = new JButton("Create Report");
    JButton createReportHomeBtn = new JButton();
    //# VIEW_REPORT PAGE CONTENT
    
    JTextArea taViewReport = new JTextArea();
    JScrollPane viewReportScroll = new JScrollPane(taViewReport);
    JComboBox cbReportList = new JComboBox();
    JButton openReportBtn = new JButton("Open Report");
    JButton viewReportHomeBtn = new JButton();
    
    //# LOGIN PAGE CONTENT
    
    JTextField tfLogUsername = new JTextField();
    JPasswordField tfLogPassword = new JPasswordField();
    JLabel lblLogUsername = new JLabel("Username");
    JLabel lblLogPassword = new JLabel("Password");
    JButton logInBtn = new JButton("Login");
    JLabel loginLogoLbl = new JLabel();
    //# VIEW TIMETABLE CONTENT
    
    DefaultTableModel timetableModel = new DefaultTableModel(timetableColumns,0);
    JTable userTimetable = new JTable(timetableModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            Component comp = super.prepareRenderer(renderer, row, column);
            
            String value = userTimetable.getValueAt(row,column).toString();
           
            JTextPane tpCell = new JTextPane();
            tpCell.setText(value);
            StyledDocument doc = tpCell.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(),center, false);
            tpCell.setBackground(backgroundCol);
            tpCell.setForeground(foregroundCol);
            tpCell.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            return tpCell;
        }
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JButton viewTimetableHomeBtn = new JButton();
    
    //# CANTEEN PAGE CONTENT
    
    JTextArea taItemsAdded = new JTextArea();
    
    JLabel lblItem1 = new JLabel("Item 1: £2.70");
    JButton addItem1 = new JButton("Add");
    
    JLabel lblItem2 = new JLabel("Item 2: £3.50");
    JButton addItem2 = new JButton("Add");
    
    JLabel lblItem3 = new JLabel("Item 3: £1.80");
    JButton addItem3 = new JButton("Add");
    
    JLabel lblTotal = new JLabel("Total,£0.00");
    JButton finishTransactionBtn = new JButton("Pay");
    
    JButton makeTransactionHomeBtn = new JButton();
    
    //# VIEW STUDENT LOG PAGE CONTENT
    
    JComboBox cbViewLogList = new JComboBox();
    JTextArea taStudentLogs = new JTextArea();
    JButton openSelectedUserLogBtn = new JButton("Open");
    JButton viewUserLogHomeBtn = new JButton();
    //# ADD STUDENT LOG PAGE CONTENT
    JComboBox cbAddLogList = new JComboBox();
    JTextArea taAddStudentLog = new JTextArea();
    JButton addSelectedUserLogBtn = new JButton("Add");
    JButton addUserLogHomeBtn = new JButton();
    
    //# FORUM PAGE CONTENT
    ArrayList<PostF> forumContent;
    JButton tempRead = new JButton("Read");
    JPanel postBox = new JPanel();
    
    JScrollPane pbScroll = new JScrollPane();
    
    JPanel testPost = new JPanel(null);
    JPanel testPost2 = new JPanel();
    JPanel testPost3 = new JPanel();
    JPanel testPost4 = new JPanel();
    JPanel testPost5 = new JPanel();
    JPanel testPost6 = new JPanel();
    JButton testButton = new JButton("TEST");
    JPanel postBoxFrame = new JPanel();
    
    JFrame replyPopup = new JFrame("Replies");
    
    
    JOptionPane replyInputPane = new JOptionPane();
    
    JScrollPane dialogScroll;
    
    ArrayList<PostF> replyStack = new ArrayList<PostF>();
    int replyIndex = 0;
    
    String selectedForumGroup = "G00000";
    
    JButton forumHomeBtn = new JButton();
    JButton postRootBtn = new JButton("Create Post");
    
    //# SET ASSIGNMENT PAGE CONTENT
    
    JButton viewAssignmentHomeBtn = new JButton();
    
    String[] setAssignmentTableHeadings = {"Class", "Assignment Title", "Due", "Attached","AssignmentID"};
    
    DefaultTableModel setAssignmentModel = new DefaultTableModel(setAssignmentTableHeadings,0){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };  
    
    JTable setAssignmentTable = new JTable(setAssignmentModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            Component comp = super.prepareRenderer(renderer,row,column);
            comp.setBackground(backgroundCol);
            comp.setForeground(foregroundCol);
            return comp;
        }
        
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane setAssignmentTableScroll = new JScrollPane(setAssignmentTable);
    JLabel setAssignmentLbl = new JLabel("Assignments");
    JButton readAssignmentsBtn = new JButton("Read");
    
    JButton setAssignmentBtn = new JButton("Set Assignment");
    
    JButton assignmentHomeBtn = new JButton();
    
    JButton createNewAssignmentGroupBtn = new JButton("New Assignment Group");

    //# VIEW ASSIGNMENT PAGE CONTENT
    
    //# UI Manager
    
    private UIManager UI = new UIManager();
    
    //# ICONS
    
    private ImageIcon westLancsLogo;
    private ImageIcon bugReportIcon;
    private ImageIcon homeBtnIcon;
    
    //# Initialisation of GUI elements
    
    public void setDefaultCol(){
        UI.put("OptionPane.background", backgroundCol);
        UI.put("OptionPane.messageForeground", foregroundCol);
        UI.put("Button.background", backgroundCol);
        UI.put("Button.foreground", foregroundCol);
        UI.put("Button.border", BorderFactory.createLineBorder(foregroundCol));
        UI.put("Panel.background", backgroundCol);
        UI.put("Label.foreground", foregroundCol);
        UI.put("TextArea.foreground", foregroundCol);
        UI.put("TextArea.background", backgroundCol);
        UI.put("TextArea.border", BorderFactory.createLineBorder(foregroundCol));
        UI.put("TextField.background", backgroundCol);
        UI.put("TextField.foreground", foregroundCol);
        UI.put("TextField.border", BorderFactory.createLineBorder(foregroundCol));
        
        westLancsLogo = new ImageIcon("Images\\westLancsLogo.PNG");
        Image westLancsImage = westLancsLogo.getImage();
        westLancsImage = westLancsImage.getScaledInstance(250,250, java.awt.Image.SCALE_SMOOTH);
        westLancsLogo = new ImageIcon(westLancsImage);
        
        bugReportIcon = new ImageIcon("Images\\bugReportIcon.PNG");
        Image bugReportImage = bugReportIcon.getImage();
        bugReportImage = bugReportImage.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
        bugReportIcon = new ImageIcon(bugReportImage);
        
        homeBtnIcon = new ImageIcon("Images\\Home.PNG");
        Image homeBtnImage = homeBtnIcon.getImage();
        homeBtnImage = homeBtnImage.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
        homeBtnIcon = new ImageIcon(homeBtnImage);
        
        setButtonPaint(viewReportsPageBtn);
        
        assignmentHomeBtn.setIcon(homeBtnIcon);
        
        assignmentHomeBtn.setMargin(new Insets(0,0,0,0));
        assignmentHomeBtn.setBackground(backgroundCol);
        assignmentHomeBtn.setBorderPainted(false);
        assignmentHomeBtn.setFocusPainted(false);

        forumHomeBtn.setIcon(homeBtnIcon);
        
        forumHomeBtn.setMargin(new Insets(0,0,0,0));
        forumHomeBtn.setBackground(backgroundCol);
        forumHomeBtn.setBorderPainted(false);
        forumHomeBtn.setFocusPainted(false);
        
        addUserLogHomeBtn.setIcon(homeBtnIcon);
        
        addUserLogHomeBtn.setMargin(new Insets(0,0,0,0));
        addUserLogHomeBtn.setBackground(backgroundCol);
        addUserLogHomeBtn.setBorderPainted(false);
        addUserLogHomeBtn.setFocusPainted(false);

        viewUserLogHomeBtn.setIcon(homeBtnIcon);
        
        viewUserLogHomeBtn.setMargin(new Insets(0,0,0,0));
        viewUserLogHomeBtn.setBackground(backgroundCol);
        viewUserLogHomeBtn.setBorderPainted(false);
        viewUserLogHomeBtn.setFocusPainted(false);
        
        transactionHomeBtn.setIcon(homeBtnIcon);
        
        transactionHomeBtn.setMargin(new Insets(0,0,0,0));
        transactionHomeBtn.setBackground(backgroundCol);
        transactionHomeBtn.setBorderPainted(false);
        transactionHomeBtn.setFocusPainted(false);
        
        viewTimetableHomeBtn.setIcon(homeBtnIcon);
        
        viewTimetableHomeBtn.setMargin(new Insets(0,0,0,0));
        viewTimetableHomeBtn.setBackground(backgroundCol);
        viewTimetableHomeBtn.setBorderPainted(false);
        viewTimetableHomeBtn.setFocusPainted(false);
        
        viewReportHomeBtn.setIcon(homeBtnIcon);
        
        viewReportHomeBtn.setMargin(new Insets(0,0,0,0));
        viewReportHomeBtn.setBackground(backgroundCol);
        viewReportHomeBtn.setBorderPainted(false);
        viewReportHomeBtn.setFocusPainted(false);
        
        createReportHomeBtn.setIcon(homeBtnIcon);
        
        createReportHomeBtn.setMargin(new Insets(0,0,0,0));
        createReportHomeBtn.setBackground(backgroundCol);
        createReportHomeBtn.setBorderPainted(false);
        createReportHomeBtn.setFocusPainted(false);
        
        
        
        chatToHomeBtn.setIcon(homeBtnIcon);
        
        chatToHomeBtn.setMargin(new Insets(0,0,0,0));
        chatToHomeBtn.setBackground(backgroundCol);
        chatToHomeBtn.setBorderPainted(false);
        chatToHomeBtn.setFocusPainted(false);
        
        recordsHomeBtn.setIcon(homeBtnIcon);
        
        recordsHomeBtn.setMargin(new Insets(0,0,0,0));
        recordsHomeBtn.setBackground(backgroundCol);
        recordsHomeBtn.setBorderPainted(false);
        recordsHomeBtn.setFocusPainted(false);
        
        viewAssignmentHomeBtn.setIcon(homeBtnIcon);
        
        viewAssignmentHomeBtn.setMargin(new Insets(0,0,0,0));
        viewAssignmentHomeBtn.setBackground(backgroundCol);
        viewAssignmentHomeBtn.setBorderPainted(false);
        viewAssignmentHomeBtn.setFocusPainted(false);
    }
    
    
    
    public void initFrame(){
        
        javax.swing.ToolTipManager.sharedInstance().setInitialDelay(100); // Adjusts how long to hold over GUI element before its associated tooltip displays on screen
        prototypeWindow.add(screenTabs);
        prototypeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prototypeWindow.setSize(1920,1080);
        
        
        
        initTabs();
        setDefaultCol();
        initLoginPanel();
        
        screenTabs.setSelectedIndex(PageTabIndex.LOGIN_PAGE.ordinal());
        screenTabs.setFocusable(false);
        screenTabs.setEnabled(false);
        //prototypeWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); //# Fullscreens the JFrame window
        //prototypeWindow.setUndecorated(true);                    //# Sets undecorated to remove the frame and border of the window
        prototypeWindow.setVisible(true); 
    }
    
    public void initTabs(){
        screenTabs.setUI(new BasicTabbedPaneUI(){ //#- Sets the JTabbedPane UI as an overridden BasicTabbedPaneUI with its Insets set to 0, so no space between the border and the tab, therefore removing the border from the tab
           private final Insets borderInsets = new Insets(0,0,0,0);
           @Override
           protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex){//# Overrides the paintContentBorder method to be empty so no border is painted on the tab
           }
           @Override
           protected Insets getContentBorderInsets(int tabPlacement){ //# When an attempt to retrieve the border insets is made, it returns the overridden value of (0,0,0,0) so there is no gap left for a border
               return borderInsets;
           }
           @Override
           protected int calculateTabAreaHeight(int tab_placement, int run_count, int max_tab_height){ //# returns a height of 0 to hide the tabs
               return 0;
           }
        });
        
        screenTabs.addTab("HomeScreen", homeTestPanel);
        screenTabs.addTab("RecordsPage", recordsPagePanel);
        screenTabs.addTab("ChatPage", chatPanel);
        screenTabs.addTab("ReportPage", reportPanel);
        screenTabs.addTab("LoginPage", loginPanel);
        screenTabs.addTab("ViewReports", viewReportsPanel);
        screenTabs.addTab("ViewTimetable", viewTimetablePanel);
        screenTabs.addTab("CanteenPage", canteenPanel);
        screenTabs.addTab("ViewStudentLog", viewStudentLogPanel);
        screenTabs.addTab("AddStudentLog", addStudentLogPanel);
        screenTabs.addTab("ForumPage", forumPanel);
        screenTabs.addTab("SetAssignmentsPage", setAssignmentPanel);
        screenTabs.addTab("ViewAssignmentsPage", viewAssignmentsPanel);
        
        
    }
    
    JButton openViewAssignmentsBtn = new JButton("View Assignments");
    public void selectForumGroup(String userID){
        JOptionPane selectPopup = new JOptionPane();
        
        JPanel popupPane = new JPanel(null);
        popupPane.setSize(500,800);
        popupPane.setMaximumSize(new Dimension(500,800));
        popupPane.setMinimumSize(new Dimension(500,800));
        popupPane.setPreferredSize(new Dimension(500,800));
        
        String[] groupColumns = {"Group ID", "Group Name", "TeacherID"};
        DefaultTableModel groupModel = new DefaultTableModel(groupColumns, 0);
        JTable groupTable = new JTable(groupModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component comp = super.prepareRenderer(renderer,row,column);
                comp.setBackground(backgroundCol);
                comp.setForeground(foregroundCol);
                return comp;
            }
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        groupTable.getTableHeader().setReorderingAllowed(false);
        groupTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        groupTable.getTableHeader().setBackground(backgroundCol);
        groupTable.getTableHeader().setForeground(foregroundCol);
        groupTable.setFillsViewportHeight(true);
        groupTable.setBackground(backgroundCol);
        groupTable.setSize(400,600);
        //groupTable.setLocation(50,100);
        groupTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JOptionPane selectedGroupPopup = new JOptionPane();
                
                String groupName = groupTable.getValueAt(groupTable.getSelectedRow(),1).toString();
                String groupID = groupTable.getValueAt(groupTable.getSelectedRow(), 0).toString();
                
                int result = selectedGroupPopup.showConfirmDialog(null, "Open the group: " + groupName +"?", "Open Group", JOptionPane.OK_CANCEL_OPTION);
                
                if(result == JOptionPane.OK_OPTION){
                    selectedForumGroup = groupID;
                    renderForum();
                    screenTabs.setSelectedIndex(PageTabIndex.FORUM_PAGE.ordinal());
                }else{
                    selectedGroupPopup.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        JScrollPane groupTableScroll = new JScrollPane(groupTable);
        groupTableScroll.setSize(400,700);
        groupTableScroll.setLocation(50,10);
        
        popupPane.add(groupTableScroll);
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\fGroup-UserID.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                for(int i = 2; i < splitLine.length; i++){
                    if(splitLine[i].equals(userID)){
                        String[] row = new String[3];
                        row[0] = splitLine[0];
                        row[1] = splitLine[1];
                        //GET TEACHER NAME FROM ID
                        BufferedReader br2 = new BufferedReader(new FileReader("TextFiles\\TeacherRecords.txt"));
                        String nextLine2 = br2.readLine();
                        while(nextLine2 != null){
                            String[] splitLine2 = nextLine2.split(",");
                            if(splitLine2[0].equals(splitLine[2])){
                                row[2] = splitLine2[1] +" "+splitLine2[2];
                            }
                            nextLine2 = br2.readLine();
                        }
                        br2.close();
                        //
                        groupModel.addRow(row);
                    }
                }
                nextLine = br.readLine();
            }
            br.close();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        selectPopup.showMessageDialog(null, popupPane, "Open Group", JOptionPane.INFORMATION_MESSAGE);
        
        
    }
    
    public void setButtonPaint(JButton button){
        button.setBackground(backgroundCol);
        button.setForeground(foregroundCol);
        button.setBorder(BorderFactory.createLineBorder(foregroundCol));
        button.setFocusPainted(false);
    }
    
    public void initHomePanel(){
        // Records Page Button
        homeTestPanel.setBackground(backgroundCol);
        homeTestPanel.setLayout(null);
        homeLogoLbl.setSize(250,250);
        homeLogoLbl.setLocation(1640,0);
        homeLogoLbl.setIcon(westLancsLogo);
        homeTestPanel.add(homeLogoLbl);
        
        recordsPageBtn.setSize(382,190); 
        recordsPageBtn.setLocation(30,440);
        recordsPageBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.RECORDS_PAGE.ordinal()));
        setButtonPaint(recordsPageBtn);
        recordsPageBtn.setFont(homeFont);
        homeTestPanel.add(recordsPageBtn);
        
        
        
        chatPageBtn.setSize(382,190); 
        chatPageBtn.setLocation(834,220);
        setButtonPaint(chatPageBtn);
        chatPageBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.CHAT_PAGE.ordinal()));
        chatPageBtn.setFont(homeFont);
        homeTestPanel.add(chatPageBtn);
        //
        reportPageBtn.setSize(80,80); 
        reportPageBtn.setLocation(30,10);
        reportPageBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.REPORT_PAGE.ordinal()));
        
        
        reportPageBtn.setIcon(bugReportIcon);
        reportPageBtn.setMargin(new Insets(0,0,0,0));
        reportPageBtn.setBackground(backgroundCol);
        reportPageBtn.setBorderPainted(false);
        
        reportPageBtn.setFocusPainted(false);
        homeTestPanel.add(reportPageBtn);
        
        
        
        
        viewTimetableBtn.setSize(382, 190); 
        viewTimetableBtn.setLocation(30,220); 
        //viewTimetableBtn.addActionListener(AL->controller.getTimetableLogic().viewTimetable(timetableModel, controller.getUserBase().getUserID()));
        viewTimetableBtn.addActionListener(AL->controller.getTimetableLogic().viewTimetable(timetableModel, controller.getUserBase().getUserID()));
        
        viewTimetableBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.VIEW_TIMETABLE_PAGE.ordinal()));
        setButtonPaint(viewTimetableBtn);
        viewTimetableBtn.setFont(homeFont);
        homeTestPanel.add(viewTimetableBtn);
        
        getBalanceBtn.setSize(382, 190); 
        getBalanceBtn.setLocation(1236, 220); 
        setButtonPaint(getBalanceBtn);
        getBalanceBtn.addActionListener(AL->controller.getTransactionLogic().getBalance(controller.getUserBase().getUserID()));
        getBalanceBtn.setFont(homeFont);
        homeTestPanel.add(getBalanceBtn);
        
        
        addBalanceBtn.setSize(382, 190); 
        addBalanceBtn.setLocation(1236,440); 
        addBalanceBtn.addActionListener(AL->controller.getTransactionLogic().addBalance(controller.getUserBase().getUserID(), true,""));
        setButtonPaint(addBalanceBtn);
        addBalanceBtn.setFont(homeFont);
        homeTestPanel.add(addBalanceBtn);
        
        
        canteenPageBtn.setSize(382, 190); 
        canteenPageBtn.setLocation(1236, 660); 
        canteenPageBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.CANTEEN_PAGE.ordinal()));
        setButtonPaint(canteenPageBtn);
        canteenPageBtn.setFont(homeFont);
        homeTestPanel.add(canteenPageBtn);
        
        
        viewStudentLogBtn.setSize(382,190); 
        viewStudentLogBtn.setLocation(432,220); 
        setButtonPaint(viewStudentLogBtn);
        viewStudentLogBtn.setFont(homeFont);
        homeTestPanel.add(viewStudentLogBtn);
        
        addStudentLogBtn.setSize(382,190);  
        addStudentLogBtn.setLocation(432, 440); 
        setButtonPaint(addStudentLogBtn);
        addStudentLogBtn.setFont(homeFont);
        homeTestPanel.add(addStudentLogBtn);
        
        viewStudentLogBtn.addActionListener(AL->controller.populateComboBoxStudents(cbViewLogList));
        viewStudentLogBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.VIEW_STUDENT_LOGS_PAGE.ordinal()));
        addStudentLogBtn.addActionListener(AL->controller.populateComboBoxStudents(cbAddLogList));
        addStudentLogBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.ADD_STUDENT_LOGS_PAGE.ordinal()));
        
        openForumBtn.setSize(382, 190); 
        openForumBtn.setLocation(834,440); 
        //openForumBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.FORUM_PAGE.ordinal()));
        openForumBtn.addActionListener(AL->selectForumGroup(controller.getCurrUserID()));
        setButtonPaint(openForumBtn);
        openForumBtn.setFont(homeFont);
        homeTestPanel.add(openForumBtn);
        
        openSetAssignmentBtn.setSize(382, 190);
        openSetAssignmentBtn.setLocation(432, 660);
        openSetAssignmentBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.SET_ASSIGNMENT_PAGE.ordinal()));
        openSetAssignmentBtn.addActionListener(AL->controller.getAssignmentLogic().readAssignments(setAssignmentModel,controller.getUserBase().getUserID()));
        setButtonPaint(openSetAssignmentBtn);
        openSetAssignmentBtn.setFont(homeFont);
        homeTestPanel.add(openSetAssignmentBtn);
        
        openViewAssignmentsBtn.setSize(382,190);
        openViewAssignmentsBtn.setLocation(30,660);
        openViewAssignmentsBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.VIEW_ASSIGNMENTS_PAGE.ordinal()));
        setButtonPaint(openViewAssignmentsBtn);
        openViewAssignmentsBtn.setFont(homeFont);
        homeTestPanel.add(openViewAssignmentsBtn);
        
        createForumGroupBtn.setSize(382,190);
        createForumGroupBtn.setLocation(834,660);
        createForumGroupBtn.addActionListener(AL->controller.getForumLogic().createForumGroup(controller.getCurrUserID(), controller.getRecordData().getStudentRecords()));
        setButtonPaint(createForumGroupBtn);
        createForumGroupBtn.setFont(homeFont);
        homeTestPanel.add(createForumGroupBtn);
        
    }
    
    String[] studentRecordColumns = {"ID","FirstName","LastName", "Attendance", "DoB", "Address", "Year Group", "Year Leaving", "Mobile Contact Number"};
    String[] teacherRecordColumns = {"ID","FirstName","LastName", "Subject", "Date of Birth"};
    String[] adminRecordColumns = {"ID","FirstName","LastName"};
    
    // STUDENT TABLE
    
    DefaultTableModel studentRecordModel = new DefaultTableModel(studentRecordColumns,0);
    JTable studentRecordTable = new JTable(studentRecordModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            
            Component comp = super.prepareRenderer(renderer, row, column);
            comp.setBackground(backgroundCol);
            comp.setForeground(foregroundCol);
            return comp;
        }
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane studentRecordTableScroll = new JScrollPane(studentRecordTable);
    
    // TEACHER TABLE
    
    DefaultTableModel teacherRecordModel = new DefaultTableModel(teacherRecordColumns,0);
    JTable teacherRecordTable = new JTable(teacherRecordModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            
            Component comp = super.prepareRenderer(renderer, row, column);
            comp.setBackground(backgroundCol);
            comp.setForeground(foregroundCol);
            return comp;
        }
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    
    JScrollPane teacherRecordTableScroll = new JScrollPane(teacherRecordTable);
    
    // ADMIN TABLE
    
    DefaultTableModel adminRecordModel = new DefaultTableModel(adminRecordColumns,0);
    JTable adminRecordTable = new JTable(adminRecordModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            
            Component comp = super.prepareRenderer(renderer, row, column);
            comp.setBackground(backgroundCol);
            comp.setForeground(foregroundCol);
            return comp;
        }
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane adminRecordTableScroll = new JScrollPane(adminRecordTable);
    
    //
    JComboBox cbUserTypeSel = new JComboBox(userTypes);
    
    //# new stuff
    
    
    JButton studentRecordsOptionBtn = new JButton("Students");
    JButton teacherRecordsOptionBtn = new JButton("Teachers");
    JButton adminRecordsOptionBtn = new JButton("Admins");
    
    public void initRecordsPanel(){
        studentRecordsOptionBtn.setSize(400,200);
        teacherRecordsOptionBtn.setSize(400,200);
        adminRecordsOptionBtn.setSize(400,200);
        setButtonPaint(studentRecordsOptionBtn);
        setButtonPaint(teacherRecordsOptionBtn);
        setButtonPaint(adminRecordsOptionBtn);
        
        studentRecordsOptionBtn.setLocation(250,400);
        teacherRecordsOptionBtn.setLocation(750,400);
        adminRecordsOptionBtn.setLocation(1250,400);
        
        studentRecordsOptionBtn.addActionListener(AL->studentRecordsPagePopup());
        teacherRecordsOptionBtn.addActionListener(AL->teacherRecordsPagePopup());
        adminRecordsOptionBtn.addActionListener(AL->adminRecordsPagePopup());
        
        controller.getRecordsLogic().readRecords(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel);
        
        recordsHomeBtn.setSize(80,80);
        recordsHomeBtn.setLocation(1700,900);
        recordsHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        recordsPagePanel.add(recordsHomeBtn);
        
        recordsPagePanel.setBackground(backgroundCol);
        
        recordsPagePanel.add(studentRecordsOptionBtn);
        recordsPagePanel.add(teacherRecordsOptionBtn);
        recordsPagePanel.add(adminRecordsOptionBtn);
        
        controller.getRecordsLogic().readRecords(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel);
        controller.getRecordsLogic().chatUsers(controller.getRecordData(),cbUsers);
        
    }
    
    public void setTextFieldPaint(JTextField textField){
        textField.setBackground(backgroundCol);
        textField.setForeground(foregroundCol);
        textField.setBorder(BorderFactory.createLineBorder(foregroundCol));
    }
    
    
    public void studentRecordsPagePopup(){
        
        
        
        JOptionPane popupPane = new JOptionPane();
        JPanel popupPanel = new JPanel(null);
        popupPanel.setSize(1600,900);
        popupPanel.setMinimumSize(new Dimension(1600,900));
        popupPanel.setPreferredSize(new Dimension(1600,900));
        popupPanel.setBackground(backgroundCol);
        
        studentRecordTable.getTableHeader().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                StudentRecordFields attribute = StudentRecordFields.values()[studentRecordTable.columnAtPoint(e.getPoint())];
                controller.getRecordsLogic().sortStudents(controller.getRecordData().getStudentRecords(), attribute);
                updateStudentTable();
            }
        });
        
        studentRecordTable.getTableHeader().setReorderingAllowed(false);
        studentRecordTable.getTableHeader().setBackground(backgroundCol);
        studentRecordTable.getTableHeader().setForeground(foregroundCol);
        studentRecordTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        studentRecordTable.setFillsViewportHeight(true);
        
        
        studentRecordTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JOptionPane editBox = new JOptionPane();
                int result = editBox.showConfirmDialog(null, "Edit " + studentRecordTable.getValueAt(studentRecordTable.getSelectedRow(), 1) +"'s record?", "Edit", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    controller.getRecordsLogic().editRecord(controller.getRecordData(),studentRecordTable.getValueAt(studentRecordTable.getSelectedRow(), 0).toString(), studentRecordModel);
                }else{
                    editBox.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
        
        studentRecordTableScroll.setSize(1400,620);
        studentRecordTableScroll.setMinimumSize(new Dimension(1400,620));
        studentRecordTableScroll.setPreferredSize(new Dimension(1400,620));
        
        JButton searchStudentsBtn = new JButton("Search");
        setButtonPaint(searchStudentsBtn);
        JTextField tfSearchKey = new JTextField();
        setTextFieldPaint(tfSearchKey);
        
        tfSearchKey.setSize(400,30);
        tfSearchKey.setLocation(100,650);
        popupPanel.add(tfSearchKey);
        
        searchStudentsBtn.setSize(80,30);
        searchStudentsBtn.setLocation(0,650);
        searchStudentsBtn.addActionListener(AL->controller.getRecordsLogic().searchStudents(controller.getRecordData(), tfSearchKey.getText(), studentRecordModel));
        setButtonPaint(searchStudentsBtn);
        popupPanel.add(searchStudentsBtn);
        
        popupPanel.add(studentRecordTableScroll);
        
        JButton createRecordBtn = new JButton("Create Record");
        createRecordBtn.setSize(160,30);
        createRecordBtn.setLocation(1200,650);
        createRecordBtn.addActionListener(AL->controller.getRecordsLogic().createStudentRecordPopup());
        setButtonPaint(createRecordBtn);
        popupPanel.add(createRecordBtn);
        
        popupPane.showMessageDialog(null, popupPanel, "Student Records", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void updateStudentTable(){
        studentRecordModel.setRowCount(0);
        for(StudentStruct student : controller.getRecordData().getStudentRecords()){
            studentRecordModel.addRow(student.toString().split(","));
        }
    }
    
    public void updateTeacherTable(){
        teacherRecordModel.setRowCount(0);
        for(TeacherStruct teacher : controller.getRecordData().getTeacherRecords()){
            teacherRecordModel.addRow(teacher.toString().split(","));
        }
    }
    
    public void updateAdminTable(){
        adminRecordModel.setRowCount(0);
        for(AdminStruct admin : controller.getRecordData().getAdminRecords()){
            adminRecordModel.addRow(admin.toString().split(","));
        }
    }
    
    public void teacherRecordsPagePopup(){
        JOptionPane popupPane = new JOptionPane();
        JPanel popupPanel = new JPanel(null);
        popupPanel.setSize(1600,900);
        popupPanel.setMinimumSize(new Dimension(1600,900));
        popupPanel.setPreferredSize(new Dimension(1600,900));
        
        teacherRecordTable.getTableHeader().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                TeacherRecordFields attribute = TeacherRecordFields.values()[teacherRecordTable.columnAtPoint(e.getPoint())];
                controller.getRecordsLogic().sortTeachers(controller.getRecordData().getTeacherRecords(), attribute);
                updateTeacherTable();
            }
        });
        
        teacherRecordTable.getTableHeader().setReorderingAllowed(false);
        teacherRecordTable.setBackground(backgroundCol);
        teacherRecordTable.setFillsViewportHeight(true);
        teacherRecordTable.getTableHeader().setBackground(backgroundCol);
        teacherRecordTable.getTableHeader().setForeground(foregroundCol);
        teacherRecordTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        
        teacherRecordTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JOptionPane popupBox = new JOptionPane();
                
                if(controller.getUserAuth() == UserAuthEnum.ADMIN){
                    int result = popupBox.showConfirmDialog(null, "Edit " +teacherRecordTable.getValueAt(teacherRecordTable.getSelectedRow(), 1) +"'s record?", "Edit", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        System.out.println("DOING");
                        controller.getRecordsLogic().editTeacherRecord(controller.getRecordData(), teacherRecordTable.getValueAt(teacherRecordTable.getSelectedRow(),0).toString(), teacherRecordModel);
                    }else{
                        popupBox.showMessageDialog(null, "Cancelled", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    popupBox.showMessageDialog(null, "Only Admin users can modify teacher records", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        teacherRecordTableScroll.setSize(1400,620);
        teacherRecordTableScroll.setMinimumSize(new Dimension(1400,620));
        teacherRecordTableScroll.setPreferredSize(new Dimension(1400,620));
        
        JButton searchTeachersBtn = new JButton("Search");
        setButtonPaint(searchTeachersBtn);
        
        JTextField tfSearchKey = new JTextField();
        setTextFieldPaint(tfSearchKey);
        
        tfSearchKey.setSize(400,30);
        tfSearchKey.setLocation(100,650);
        popupPanel.add(tfSearchKey);
        
        searchTeachersBtn.setSize(80,30);
        searchTeachersBtn.setLocation(0,650);
        searchTeachersBtn.addActionListener(AL->controller.getRecordsLogic().searchTeachers(controller.getRecordData(), tfSearchKey.getText(), teacherRecordModel));
        popupPanel.add(searchTeachersBtn);
        popupPanel.setBackground(backgroundCol);
        popupPanel.add(teacherRecordTableScroll);
        
        popupPane.showMessageDialog(null, popupPanel, "Teacher Records", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void adminRecordsPagePopup(){
        JOptionPane popupPane = new JOptionPane();
        JPanel popupPanel = new JPanel(null);
        popupPanel.setSize(1600,900);
        popupPanel.setMinimumSize(new Dimension(1600,900));
        popupPanel.setPreferredSize(new Dimension(1600,900));
        
        adminRecordTable.getTableHeader().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                AdminRecordFields attribute = AdminRecordFields.values()[adminRecordTable.columnAtPoint(e.getPoint())];
                controller.getRecordsLogic().sortAdmins(controller.getRecordData().getAdminRecords(), attribute);
                updateAdminTable();
            }
        });
        adminRecordTable.getTableHeader().setReorderingAllowed(false);
        adminRecordTable.setBackground(backgroundCol);
        adminRecordTable.setFillsViewportHeight(true);
        adminRecordTable.getTableHeader().setBackground(backgroundCol);
        adminRecordTable.getTableHeader().setForeground(foregroundCol);
        adminRecordTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        adminRecordTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JOptionPane popupBox = new JOptionPane();
                
                if(controller.getUserAuth() == UserAuthEnum.ADMIN){
                    int result = popupBox.showConfirmDialog(null, "Edit " +adminRecordTable.getValueAt(adminRecordTable.getSelectedRow(), 1) +"'s record?", "Edit", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        controller.getRecordsLogic().editAdminRecord(controller.getRecordData(), adminRecordTable.getValueAt(adminRecordTable.getSelectedRow(), 0).toString(), adminRecordModel);
                    }
                }else{
                    popupBox.showMessageDialog(null, "Only Admin users can modify admin records", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        adminRecordTableScroll.setSize(1400,620);
        adminRecordTableScroll.setMinimumSize(new Dimension(1400,620));
        adminRecordTableScroll.setPreferredSize(new Dimension(1400,620));
        
        JButton searchAdminsBtn = new JButton("Search");
        setButtonPaint(searchAdminsBtn);
        JTextField tfSearchKey = new JTextField();
        setTextFieldPaint(tfSearchKey);
        tfSearchKey.setSize(400,30);
        tfSearchKey.setLocation(100,650);
        popupPanel.add(tfSearchKey);
        popupPanel.setBackground(backgroundCol);
        searchAdminsBtn.setSize(80,30);
        searchAdminsBtn.setLocation(0,650);
        searchAdminsBtn.addActionListener(AL->controller.getRecordsLogic().searchAdmins(controller.getRecordData(), tfSearchKey.getText(), adminRecordModel));
        popupPanel.add(searchAdminsBtn);
        
        popupPanel.add(adminRecordTableScroll);
        
        popupPane.showMessageDialog(null, popupPanel, "Admin Records", JOptionPane.INFORMATION_MESSAGE);

    }
    
    
    // public void initRecordsPanel(){
        // studentRecordTableScroll.setSize(600,300); 
        // studentRecordTableScroll.setLocation(50,10);
        // teacherRecordTableScroll.setSize(600,300); 
        // teacherRecordTableScroll.setLocation(50,320);
        // adminRecordTableScroll.setSize(600,300); 
        // adminRecordTableScroll.setLocation(50,630);
        // recordsPagePanel.add(studentRecordTableScroll); 
        // recordsPagePanel.add(teacherRecordTableScroll); recordsPagePanel.add(adminRecordTableScroll);
        
        // studentRecordTable.setEnabled(false);
        
        // readRecordsBtn.setSize(120,20); 
        // readRecordsBtn.setLocation(830,370);
        // recordsPagePanel.add(readRecordsBtn);
        
        // clearRecordsBtn.setSize(120,20); 
        // clearRecordsBtn.setLocation(700,370);
        // recordsPagePanel.add(clearRecordsBtn);
        
        
        
        // createRecordBtn.setSize(120,20); 
        // createRecordBtn.setLocation(960,370);
        // recordsPagePanel.add(createRecordBtn);
        
        // tfRecordSearch.setSize(150,20); 
        // tfRecordSearch.setLocation(750,410);
        // lblRecordSearch.setSize(120,20); 
        // lblRecordSearch.setLocation(700,410);
        // recordsPagePanel.add(tfRecordSearch); 
        // recordsPagePanel.add(lblRecordSearch);
        
        // recordSearchBtn.setSize(80,20); 
        // recordSearchBtn.setLocation(910,410);
        
        // recordsPagePanel.add(recordSearchBtn);
        
        // String[] strRecordFields = {"ID", "FirstName", "LastName"};
        // cbSortSelect = new JComboBox(strRecordFields);
        // cbSortSelect.setSize(120,20); 
        // cbSortSelect.setLocation(700,180);
        // recordSortBtn.setSize(80,20); 
        // recordSortBtn.setLocation(830,180);
        
        // recordsPagePanel.add(cbSortSelect); recordsPagePanel.add(recordSortBtn); 
        
        // recordHomeBtn.setSize(70,70); 
        // recordHomeBtn.setLocation(1700,900); 
        // recordHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        // recordsPagePanel.add(recordHomeBtn);
        
        // //cbUserTypeSel.setSize(120,20); cbUserTypeSel.setLocation(700,240); recordsPagePanel.add(cbUserTypeSel);
        
        // readRecordsBtn.addActionListener(AL->controller.getRecordsLogic().chatUsers(controller.getRecordData(),cbUsers));
        // readRecordsBtn.addActionListener(AL->controller.getRecordsLogic().readRecords(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel));
        
        // clearRecordsBtn.addActionListener(AL->controller.getRecordsLogic().chatUsers(controller.getRecordData(),cbUsers));
        // clearRecordsBtn.addActionListener(AL->controller.getRecordsLogic().clearRecords(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel));
        
        // // createRecordBtn.addActionListener(AL->controller.getRecordsLogic().createStudentRecord(tfFirstName.getText(), tfLastName.getText(), tfAttendance.getText()
                                                                                               // // ,tfDoB.getText(), tfAddress.getText(), tfYearGroup.getText(), 
                                                                                                // // tfYearLeaving.getText())); 
        // createRecordBtn.addActionListener(AL->controller.getRecordsLogic().createStudentRecordPopup());
        // recordSearchBtn.addActionListener(AL->controller.getRecordsLogic().searchRecords(controller.getRecordData(),tfRecordSearch.getText(), studentRecordModel, teacherRecordModel, adminRecordModel));
        
        // recordSortBtn.addActionListener(AL->controller.getRecordsLogic().updateRecordTables(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel));
        // recordSortBtn.addActionListener(AL->controller.getRecordsLogic().sortRecords(controller.getRecordData(),UserRecordFields.values()[cbSortSelect.getSelectedIndex()]));
        
    // }
    
    public void initChatPanel(){
        chatBox.setEditable(false);
        chatBox.setLineWrap(true);
        chatBox.setSize(1600,700); 
        chatBox.setLocation(50,50); 
        setTextAreaPaint(chatBox);
        chatScroll.setSize(1600,700); 
        chatScroll.setLocation(50,50); 
        chatPanel.add(chatScroll);
        msgBox.setSize(1600, 80); 
        msgBox.setLocation(50,860); 
        setTextAreaPaint(msgBox);
        msgScroll.setSize(1600,80); 
        msgScroll.setLocation(50,760); 
        
        chatPanel.add(msgScroll);
        msgBox.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\r\n");
        msgBox.setLineWrap(true);
        sendMsgBtn.setSize(200,30); sendMsgBtn.setLocation(50,970);
        chatPanel.add(sendMsgBtn);
        
        cbUsers.setSize(200,20); 
        cbUsers.setLocation(1670,60); 
        cbUsers.setBackground(backgroundCol);
        cbUsers.setForeground(foregroundCol);
        cbUsers.setBorder(BorderFactory.createLineBorder(foregroundCol));
        chatPanel.add(cbUsers); 
        chatPanel.setBackground(backgroundCol);
        loadConvoBtn.setSize(150,20); loadConvoBtn.setLocation(1685,90); 
        
        chatPanel.add(loadConvoBtn);
        
        chatToHomeBtn.setSize(70,70); chatToHomeBtn.setLocation(1700,900); 
        chatPanel.add(chatToHomeBtn);
        setButtonPaint(sendMsgBtn);
        sendMsgBtn.addActionListener(AL->controller.getChatLogic().sendMsg(msgBox.getText(),chatBox,msgBox));
        loadConvoBtn.addActionListener(AL->controller.getChatLogic().initConvo(controller.getUserBase().getUserID(),cbUsers.getSelectedItem().toString().split(",")[0],controller.getUserBase().getFullName(),(cbUsers.getSelectedItem().toString().split(",")[1] +" "+ cbUsers.getSelectedItem().toString().split(",")[2]),chatBox)); //hard 2 read 
        chatToHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
    }
    
    public void initReportPanel(){
        reportContentScroll.setSize(1000, 800); 
        reportContentScroll.setLocation(460,50);
        setTextAreaPaint(taReportContent);
        reportPanel.add(reportContentScroll);
        createReportBtn.setSize(300,40); 
        createReportBtn.setLocation(810, 870); 
        setButtonPaint(createReportBtn);
        reportPanel.add(createReportBtn);
        reportPanel.setBackground(backgroundCol);
        createReportBtn.addActionListener(AL->controller.getReportLogic().makeReport(taReportContent, controller.getUserBase().getUserID()));
        createReportHomeBtn.setSize(80,80);
        
        createReportHomeBtn.setLocation(1700,900); 
        reportPanel.add(createReportHomeBtn);
        createReportHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
    }
    
    public void initLoginPanel(){
        loginPanel.setBackground(backgroundCol);
        
        loginLogoLbl.setSize(250,250);
        loginLogoLbl.setLocation(835,230);
        loginLogoLbl.setIcon(westLancsLogo);
        loginPanel.add(loginLogoLbl);
        
        lblLogUsername.setForeground(foregroundCol);
        lblLogPassword.setForeground(foregroundCol);
        
        tfLogUsername.setForeground(foregroundCol);
        tfLogUsername.setBackground(backgroundCol);
        tfLogPassword.setForeground(foregroundCol);
        tfLogPassword.setBackground(backgroundCol);
        
        tfLogUsername.setBorder(BorderFactory.createLineBorder(foregroundCol));
        tfLogPassword.setBorder(BorderFactory.createLineBorder(foregroundCol));
        
        logInBtn.setForeground(foregroundCol);
        logInBtn.setBackground(backgroundCol);
        logInBtn.setBorder(BorderFactory.createLineBorder(foregroundCol));
        
        tfLogUsername.setSize(400,30); 
        tfLogUsername.setLocation(760,480); 
        loginPanel.add(tfLogUsername);
        
        lblLogUsername.setSize(150,20); 
        lblLogUsername.setLocation(760,450); 
        loginPanel.add(lblLogUsername);
        
        tfLogPassword.setSize(400,30); 
        tfLogPassword.setLocation(760,560); 
        loginPanel.add(tfLogPassword);
        
        lblLogPassword.setSize(150,20); 
        lblLogPassword.setLocation(760,530); 
        loginPanel.add(lblLogPassword);
        
        logInBtn.setSize(100,30); 
        logInBtn.setLocation(910,610); 
        loginPanel.add(logInBtn);
        logInBtn.addActionListener(AL->doLogIn());
    }
    
    public void doLogIn(){
        boolean result = controller.getLoginLogic().loginUser(tfLogUsername.getText(), tfLogPassword.getText(), controller, screenTabs);
        if(result){
           
            initHomePanel();
            
            setUserAccess(controller.getUserAuth());
            initRecordsPanel();
            initChatPanel();
            initReportPanel();
            initViewReportsPanel();
            initViewTimetablePanel();
            initCanteenPanel();
            initStudentLogPanels();
            initForumPanel();
            initSetAssignmentPanel();
            initViewAssignmentsPanel();
            
            
            
            
            //controller.getRecordsLogic().readRecords(controller.getRecordData(),studentRecordModel,teacherRecordModel,adminRecordModel);
            //controller.getAssignmentLogic().readAssignments(setAssignmentModel,controller.getUserBase().getUserID());
        }
    }
    
    public void initViewReportsPanel(){
        taViewReport.setEditable(false); 
        setTextAreaPaint(taViewReport);
        viewReportScroll.setSize(1000,800); 
        viewReportScroll.setLocation(50,50); 
        viewReportsPanel.add(viewReportScroll);
        cbReportList.setSize(200,20); 
        cbReportList.setLocation(1060,50); 
        viewReportsPanel.add(cbReportList);
        cbReportList.setBackground(backgroundCol);
        cbReportList.setForeground(foregroundCol);
        cbReportList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        openReportBtn.setSize(120,40); 
        openReportBtn.setLocation(1060,80); 
        setButtonPaint(openReportBtn);
        viewReportsPanel.add(openReportBtn);
        viewReportsPanel.setBackground(backgroundCol);
        taViewReport.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\r\n");
        taViewReport.setWrapStyleWord(true);
        
        controller.getReportLogic().initReports(cbReportList);
        
        openReportBtn.addActionListener(AL->controller.getReportLogic().openReport(cbReportList.getSelectedIndex(), taViewReport));
        
        viewReportHomeBtn.setSize(80,80); 
        viewReportHomeBtn.setLocation(1700,900); 
        viewReportsPanel.add(viewReportHomeBtn);
        viewReportHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
    }
    
    JScrollPane userTimetableScroll = new JScrollPane();
    
    public void initViewTimetablePanel(){
        
        userTimetable.setSize(1200,810);  
        userTimetable.setFocusable(false); 
        //userTimetable.setLocation(50,50); 
        //viewTimetablePanel.add(userTimetable);
        userTimetable.setRowHeight(115);
        userTimetable.setBackground(backgroundCol);
        //userTimetable.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        userTimetable.setEnabled(false);
        viewTimetableHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        viewTimetableHomeBtn.setSize(80,80); 
        viewTimetableHomeBtn.setLocation(1700,900); 
        
        userTimetable.getTableHeader().setBackground(backgroundCol);
        userTimetable.getTableHeader().setForeground(foregroundCol);
        userTimetable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        userTimetable.getTableHeader().setReorderingAllowed(false);
        
        userTimetableScroll.setSize(1210,828);
        userTimetableScroll.add(userTimetable);
        userTimetableScroll.setLocation(50,50);
        userTimetableScroll.setViewportView(userTimetable);
        userTimetableScroll.setBackground(backgroundCol);
        viewTimetablePanel.add(userTimetableScroll);
        
        viewTimetablePanel.add(viewTimetableHomeBtn);
        viewTimetablePanel.setBackground(backgroundCol);
    }
    
    JButton transactionHomeBtn = new JButton();
    JButton clearBtn = new JButton("Clear");
    
    public void initCanteenPanel(){
        taItemsAdded.setSize(500,850); taItemsAdded.setLocation(1380,30); canteenPanel.add(taItemsAdded);
        taItemsAdded.setFocusable(false); taItemsAdded.setEditable(false);  
        setTextAreaPaint(taItemsAdded);
        lblTotal.setSize(120,80); 
        lblTotal.setLocation(1540,880); 
        lblTotal.setForeground(foregroundCol);
        canteenPanel.add(lblTotal);
        
        lblItem1.setSize(120, 80); 
        lblItem1.setLocation(125,40); 
        lblItem1.setForeground(foregroundCol);
        canteenPanel.add(lblItem1);
        lblItem2.setSize(120,80); 
        lblItem2.setLocation(125,130); 
        lblItem2.setForeground(foregroundCol);
        canteenPanel.add(lblItem2);
        lblItem3.setSize(120,80); 
        lblItem3.setLocation(125,220); 
        lblItem3.setForeground(foregroundCol);
        canteenPanel.add(lblItem3);
        
        addItem1.setSize(60,60); 
        addItem1.setLocation(60,50); 
        canteenPanel.add(addItem1); 
        addItem1.addActionListener(AL->controller.getTransactionLogic().addItem("Item1", "2.70", taItemsAdded,lblTotal));
        addItem2.setSize(60,60); 
        addItem2.setLocation(60,140); 
        canteenPanel.add(addItem2); 
        addItem2.addActionListener(AL->controller.getTransactionLogic().addItem("Item2", "3.50", taItemsAdded,lblTotal));
        addItem3.setSize(60,60); 
        addItem3.setLocation(60,230); 
        canteenPanel.add(addItem3); 
        addItem3.addActionListener(AL->controller.getTransactionLogic().addItem("Item3", "1.80", taItemsAdded,lblTotal));
        setButtonPaint(addItem1);
        setButtonPaint(addItem2);
        setButtonPaint(addItem3);
        transactionHomeBtn.setSize(80,80);
        transactionHomeBtn.setLocation(1700,900);
        transactionHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        setButtonPaint(transactionHomeBtn);
        canteenPanel.add(transactionHomeBtn);
        canteenPanel.setBackground(backgroundCol);
        finishTransactionBtn.setSize(60,60); 
        finishTransactionBtn.setLocation(1380,890); canteenPanel.add(finishTransactionBtn);    
        setButtonPaint(finishTransactionBtn);
        finishTransactionBtn.addActionListener(AL->controller.getTransactionLogic().finishTransaction(controller.getUserBase().getUserID(), taItemsAdded, lblTotal));
        
        clearBtn.setSize(60,60);
        setButtonPaint(clearBtn);
        clearBtn.setLocation(1460,890);
        clearBtn.addActionListener(AL->controller.getTransactionLogic().clearTransaction(taItemsAdded, lblTotal));
        canteenPanel.add(clearBtn);
    }
    
    public void setTextAreaPaint(JTextArea ta){
        ta.setBackground(backgroundCol);
        ta.setForeground(foregroundCol);
        ta.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    
    public void initStudentLogPanels(){
        taStudentLogs.setSize(900,700); 
        taStudentLogs.setLocation(50,50); 
        taStudentLogs.setWrapStyleWord(true);
        setTextAreaPaint(taStudentLogs);
        viewStudentLogPanel.add(taStudentLogs);
        cbViewLogList.setSize(250,20); 
        cbViewLogList.setLocation(980,60); 
        cbViewLogList.setBackground(new Color(40,40,40));
        cbViewLogList.setForeground(Color.WHITE);
        cbViewLogList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        viewStudentLogPanel.setBackground(backgroundCol);
        viewStudentLogPanel.add(cbViewLogList);
        openSelectedUserLogBtn.setSize(200,30); 
        openSelectedUserLogBtn.setLocation(1005,90); 
        setButtonPaint(openSelectedUserLogBtn);
        viewStudentLogPanel.add(openSelectedUserLogBtn);
        
        openSelectedUserLogBtn.addActionListener(AL->controller.getStudentLogLogic().readLog(cbViewLogList.getSelectedItem().toString().split(",")[1],taStudentLogs));
        
        viewUserLogHomeBtn.setSize(80,80); viewUserLogHomeBtn.setLocation(1700,900); 
        viewStudentLogPanel.add(viewUserLogHomeBtn);
        
        taAddStudentLog.setSize(1000,800); 
        taAddStudentLog.setLocation(460,50); 
        setTextAreaPaint(taAddStudentLog);
        addStudentLogPanel.setBackground(backgroundCol);
        addStudentLogPanel.add(taAddStudentLog);
        cbAddLogList.setSize(250,20); 
        cbAddLogList.setLocation(1490, 60); 
        
        cbAddLogList.setBackground(new Color(40,40,40));
        cbAddLogList.setForeground(Color.WHITE);
        cbAddLogList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        addStudentLogPanel.add(cbAddLogList);
        
        addSelectedUserLogBtn.setSize(300,40); 
        addSelectedUserLogBtn.setLocation(810,870); 
        setButtonPaint(addSelectedUserLogBtn);
        addStudentLogPanel.add(addSelectedUserLogBtn);
        
        addSelectedUserLogBtn.addActionListener(AL->controller.getStudentLogLogic().createLog(cbAddLogList.getSelectedItem().toString().split(",")[1], taAddStudentLog));
        
        addUserLogHomeBtn.setSize(80,80); addUserLogHomeBtn.setLocation(1700,900); 
        addStudentLogPanel.add(addUserLogHomeBtn);
        
        viewUserLogHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        addUserLogHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        
        taStudentLogs.setEditable(false);
    }
    
    
    
    public void replyBack(PostF replyPost){
        replyIndex--;
        System.out.println(replyIndex);
        if(replyIndex <= 1){
            replyStack.remove(replyIndex);
            PostF tempPost = replyStack.get(replyIndex-1);
            replyStack = new ArrayList<PostF>();
            replyIndex = 0;
            showReplies(tempPost);
        }else{
            replyStack.remove(replyIndex);
            PostF tempPost = replyStack.get(replyIndex-1);
            replyStack.remove(replyIndex-1);
            replyIndex--;
            nextReplies(tempPost);
        }
    }
    
    public void nextReplies(PostF replyPost){
        replyStack.add(replyPost);
        replyIndex++;
        
        
        JPanel dialogPanel = new JPanel(null);
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setSize(796,900);
        dialogPanel.setBackground(Color.RED);
        
        JPanel bufferPanel = new JPanel(null);
        bufferPanel.setSize(766,40);
        bufferPanel.setMinimumSize(new Dimension(766,40));
        bufferPanel.setMaximumSize(new Dimension(766,40));
        bufferPanel.setPreferredSize(new Dimension(766,40));
        bufferPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(AL->replyBack(replyPost));
        
        backBtn.setSize(80,20);
        backBtn.setLocation(10,10);
        
        
        bufferPanel.add(backBtn);
        
        dialogPanel.add(bufferPanel);
        
        ArrayList<PostF> replyReplies = replyPost.getReplies();
        for(int i = 0; i < replyReplies.size(); i++){
            JPanel replyReplyPanel = new JPanel(null);
            replyReplyPanel.setSize(766,100);
            replyReplyPanel.setMinimumSize(new Dimension(766,300));
            replyReplyPanel.setMaximumSize(new Dimension(766,300));
            replyReplyPanel.setPreferredSize(new Dimension(766,300));
            replyReplyPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            PostF replyReply = replyReplies.get(i);
            replyReply.setPrevReply(replyPost);
            
            JLabel opReplyName = new JLabel(replyReply.getOpName());

            opReplyName.setSize(400,20);
            opReplyName.setLocation(10,10);
            replyReplyPanel.add(opReplyName);
            
            JTextArea replyReplyPostContent = new JTextArea();
            replyReplyPostContent.setSize(500,240);
            replyReplyPostContent.setLocation(10,50);
            replyReplyPostContent.setText(replyReply.getPostContent());
            replyReplyPanel.add(replyReplyPostContent);
            replyReplyPostContent.setEditable(false);
            JButton openRepliesBtn = new JButton("Replies: " + replyReply.getNumReplies());
            openRepliesBtn.setSize(100,30);
            openRepliesBtn.setLocation(530,260);            
            
            openRepliesBtn.addActionListener(AL->nextReplies(replyReply));
            replyReplyPanel.add(openRepliesBtn);
            
            JButton replyToBtn = new JButton("Reply");
            replyToBtn.setSize(100,30);
            replyToBtn.setLocation(530,200);
            replyToBtn.addActionListener(AL->constructReply(replyReply));
            replyReplyPanel.add(replyToBtn);
            
            dialogPanel.add(replyReplyPanel);
        }
        
        
        JScrollPane dialogScroll = new JScrollPane(dialogPanel);
        
        dialogScroll.setViewportView(dialogPanel);
        dialogScroll.setSize(800,900);
        dialogScroll.setMaximumSize(new Dimension(800,900));
        dialogScroll.setMinimumSize(new Dimension(800,900));
        dialogScroll.setPreferredSize(new Dimension(800,900));
        dialogScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        

        
        
        
        replyPopup.getContentPane().removeAll();
        replyPopup.getContentPane().add(dialogScroll);
        dialogScroll.getVerticalScrollBar().setSize(new Dimension(7,dialogScroll.getHeight()));
        dialogScroll.getViewport().setViewPosition(new Point(0,0));
        
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                dialogScroll.getVerticalScrollBar().setValue(0);
            }
        });
        
        
        replyPopup.setVisible(true);
    }
    
    
    public void showReplies(PostF rootPost){
        replyStack = new ArrayList<PostF>();
        replyIndex = 0;
        replyStack.add(rootPost);
        replyIndex++;
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setSize(796,900);
        JScrollPane dialogScroll = new JScrollPane(dialogPanel);
        
        dialogScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        dialogScroll.setViewportView(dialogPanel);
        dialogScroll.setSize(800,900);
        dialogScroll.setMaximumSize(new Dimension(800,900));
        ArrayList<PostF> postReplies = rootPost.getReplies();
        
        for(int i = 0; i < postReplies.size(); i++){
            
            JPanel currReply = new JPanel(null);
            
            PostF replyPost = postReplies.get(i);
            currReply.setSize(766,300);
            currReply.setMaximumSize(new Dimension(766,300));
            currReply.setMinimumSize(new Dimension(766,300));
            currReply.setPreferredSize(new Dimension(766,300));
            currReply.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            JLabel opName = new JLabel(replyPost.getOpName());
            opName.setSize(400,20);
            opName.setLocation(10,10);
            currReply.add(opName);
            
            JTextArea currPostContent = new JTextArea();
            currPostContent.setSize(500,240);
            currPostContent.setLocation(10,50);
            currPostContent.setText(replyPost.getPostContent());
            currReply.add(currPostContent);
            currPostContent.setEditable(false);
            
            JButton openRepliesBtn = new JButton("Replies: " + postReplies.get(i).getNumReplies());
            openRepliesBtn.setSize(100,30);
            openRepliesBtn.setLocation(530,260);            
            
            openRepliesBtn.addActionListener(AL->nextReplies(replyPost));
            
            JButton replyToBtn = new JButton("Reply");
            replyToBtn.setSize(100,30);
            replyToBtn.setLocation(530,200);
            replyToBtn.addActionListener(AL->constructReply(replyPost));
            currReply.add(replyToBtn);
            
            
            
            currReply.add(openRepliesBtn);
            dialogPanel.add(currReply);
        }
        
        
        
        replyPopup.getContentPane().removeAll();
        replyPopup.getContentPane().add(dialogScroll);
        dialogScroll.getVerticalScrollBar().setSize(new Dimension(7,dialogScroll.getHeight()));
        
        replyPopup.setLocation(560,50);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                dialogScroll.getVerticalScrollBar().setValue(0);
            }
        });
        
        replyPopup.setVisible(true);
        
        
    }
    
    
    public void constructReply(PostF op){
        JTextArea ta = new JTextArea();
        
        JPanel tPanel = new JPanel(null);
        ta.setSize(640,240);
        ta.setMinimumSize(new Dimension(640,240));
        ta.setPreferredSize(new Dimension(640,240));
        ta.setMaximumSize(new Dimension(640,240));
        tPanel.add(ta);
        tPanel.setSize(640,240);
        tPanel.setMinimumSize(new Dimension(640,240));
        tPanel.setPreferredSize(new Dimension(640,240));
        tPanel.setMaximumSize(new Dimension(640,240));
        tPanel.setLayout(new BorderLayout());
        
        int result = replyInputPane.showConfirmDialog(null,tPanel,"Reply",JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION){
            String textContent = ta.getText();
            ProfanityFilter pf = new ProfanityFilter();
            if(pf.isProfane(textContent)){
                JOptionPane.showMessageDialog(null, "Text contains profanity and will not be posted", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.getForumLogic().writeReply(textContent, controller.getUserBase().getUserID(), op);
            ArrayList<PostF> tempReplyStack = replyStack;
            replyStack = new ArrayList<PostF>();
            
            int tempReplyIndex = 0;
            
            renderForum();
            //NEW FORUM CONTENT IN forumContent
            if(tempReplyStack.size() > 0){
                System.out.println("TEMP REPLY STACK INITIALISED");
                ArrayList<PostF> nextReplies = new ArrayList<PostF>();
                for(int i = 0; i < forumContent.size();i++){
                    System.out.println("ITERATING OVER NEW FORUM CONTENT");
                    if(tempReplyStack.get(0).getPostID().equals(forumContent.get(i).getPostID())){
                        replyIndex = 0;
                        System.out.println("FOUND START OF STACK ITEM");
                        showReplies(forumContent.get(i));
                        nextReplies = forumContent.get(i).getReplies();                        
                    }
                }
                
                tempReplyIndex = 1;
                
                while(nextReplies.size() > 0 && (tempReplyIndex < tempReplyStack.size())){

                    for(int i = 0; i < nextReplies.size(); i++){
                        if(tempReplyIndex < tempReplyStack.size()){
                            if(tempReplyStack.get(tempReplyIndex).getPostID().equals(nextReplies.get(i).getPostID())){
                                nextReplies(nextReplies.get(i));
                                nextReplies = nextReplies.get(i).getReplies();
                                tempReplyIndex++;
                            }
                        }
                    }
                }
                
            }
            
        }else{
            replyInputPane.showMessageDialog(null, "Cancelled Reply");
            return;
        }
    }
    
    
    
    public void renderForum(){
        //# CHANGE THIS TO MAKE IT GET STUFF FOR CURRENT USER
        forumContent = controller.getForumLogic().getPostHistory(selectedForumGroup);
        postBox.removeAll();
        
        postBox.setLayout(new BoxLayout(postBox, BoxLayout.Y_AXIS));
        
        postBox.setSize(787,900);
        pbScroll.add(postBox);
        
        for(int i = 0; i < forumContent.size(); i++){
            JPanel currBox = new JPanel(null);
            currBox.setMaximumSize(new Dimension(796,300));
            currBox.setMinimumSize(new Dimension(796,300));
            currBox.setPreferredSize(new Dimension(796,300));
            currBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            PostF currentPost = forumContent.get(i);
            
            JLabel opName = new JLabel(currentPost.getOpName());
            
            opName.setSize(400,20);
            opName.setLocation(10,10);
            currBox.add(opName);
            
            JTextArea currPostContent = new JTextArea();
            currPostContent.setSize(640,240);
            currPostContent.setLocation(10,50);
            currPostContent.setText(currentPost.getPostContent());
            currPostContent.setEditable(false);
            
            JButton openRepliesBtn = new JButton("Replies: " + currentPost.getNumReplies());
            
            
            openRepliesBtn.setSize(100,30);
            openRepliesBtn.setLocation(660,260);
            
            openRepliesBtn.addActionListener(AL->showReplies(currentPost));
            
            currBox.add(openRepliesBtn);
            
            JButton replyToBtn = new JButton("Reply");
            replyToBtn.setSize(100,30);
            replyToBtn.setLocation(660,200);
            replyToBtn.addActionListener(AL->constructReply(currentPost));
            currBox.add(replyToBtn);
            
            currBox.add(currPostContent);
            currBox.setLayout(null);
            postBox.add(currBox);
            
            
            
            
        }
        pbScroll.setViewportView(postBox);
        pbScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, pbScroll.getHeight()));
            
    }
    
    
    
    public void initForumPanel(){
        //tempRead.setSize(200,50);tempRead.setLocation(900,490);forumPanel.add(tempRead);
        //tempRead.addActionListener(AL->renderForum());
        
        
        
        postBox.setLayout(new BoxLayout(postBox, BoxLayout.Y_AXIS));
        
        postBox.setSize(787,900);
        
        postBox.setBackground(backgroundCol);
        postBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        
        pbScroll.setSize(800,900);
        
        pbScroll.setLocation(20,20);
        
        
        //postBoxFrame.add(postBox);
        
        pbScroll.add(postBox);
        
        pbScroll.setViewportView(postBox);
        pbScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, pbScroll.getHeight()));
        
        replyPopup.setSize(800,900);
        replyPopup.setMaximumSize(new Dimension(800,900));
        replyPopup.setMinimumSize(new Dimension(800,900));
        replyPopup.setPreferredSize(new Dimension(800,900));
        
        replyPopup.setResizable(false);
        replyPopup.setVisible(false);
        
        postRootBtn.setSize(200,50);
        postRootBtn.setLocation(900,490);
        postRootBtn.addActionListener(AL->controller.getForumLogic().createRootPost(selectedForumGroup, controller.getCurrUserID()));
        setButtonPaint(postRootBtn);
        forumPanel.add(postRootBtn);
        
        forumPanel.setBackground(backgroundCol);
        
        forumHomeBtn.setSize(80,80);
        forumHomeBtn.setLocation(1700,900);
        forumHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        forumPanel.add(forumHomeBtn);
        
        forumPanel.add(pbScroll); 
    }
        
    public void initSetAssignmentPanel(){
        
        setAssignmentTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
                
            }
            @Override
            public void mousePressed(MouseEvent e){
                controller.getAssignmentLogic().viewAssignmentTeacher(setAssignmentModel.getValueAt(setAssignmentTable.getSelectedRow(),4).toString());
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
        setAssignmentTable.getTableHeader().setReorderingAllowed(false);
        setAssignmentTable.setSize(700,850);
        setAssignmentTable.setLocation(10,60);
        setAssignmentTableScroll.setSize(700,850);
        setAssignmentTableScroll.setLocation(10,60);
        setAssignmentTable.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        setAssignmentTable.getTableHeader().setBackground(backgroundCol);
        setAssignmentTable.getTableHeader().setForeground(foregroundCol);
        setAssignmentTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        setAssignmentTable.setFillsViewportHeight(true);
        setAssignmentTable.setBackground(backgroundCol);
        setAssignmentPanel.add(setAssignmentTableScroll);
        setAssignmentLbl.setSize(100,30);
        setAssignmentLbl.setLocation(10,20);
        setAssignmentPanel.add(setAssignmentLbl);
        readAssignmentsBtn.setSize(150,30);
        readAssignmentsBtn.setLocation(720, 870);
        readAssignmentsBtn.addActionListener(AL->controller.getAssignmentLogic().readAssignments(setAssignmentModel,controller.getUserBase().getUserID())); //# REPLACE WITH CURR USER ID LATER
        
        setButtonPaint(readAssignmentsBtn);
        setAssignmentPanel.add(readAssignmentsBtn);
        
        setAssignmentBtn.setSize(150,30);
        setAssignmentBtn.setLocation(720,820);
        setButtonPaint(setAssignmentBtn);
        setAssignmentBtn.addActionListener(AL->controller.getAssignmentLogic().readAssignments(setAssignmentModel,controller.getUserBase().getUserID())); 
        setAssignmentBtn.addActionListener(AL->controller.getAssignmentLogic().setAssignment());
        
        setAssignmentPanel.add(setAssignmentBtn);
        
        assignmentHomeBtn.setSize(80,80);
        assignmentHomeBtn.setLocation(1700,900);
        assignmentHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        setAssignmentPanel.add(assignmentHomeBtn);
        
        createNewAssignmentGroupBtn.setSize(150,30);
        createNewAssignmentGroupBtn.setLocation(720, 80);
        createNewAssignmentGroupBtn.addActionListener(AL->controller.getAssignmentLogic().newAssignmentGroup(controller.getRecordData().getStudentRecords(), controller.getUserBase().getUserID()));
        setButtonPaint(createNewAssignmentGroupBtn);
        setAssignmentPanel.setBackground(backgroundCol);
        setAssignmentPanel.add(createNewAssignmentGroupBtn);
        
    }
    
    // This page is just for students to view the assignments they have been set
    //viewAssignmentsPanel
    
    JComboBox cbViewGroups = new JComboBox();
    JButton openGroupBtn = new JButton("Open Group");
    String[] viewModelHeadings = {"Class", "Assignment Title", "Due", "Attached","AssignmentID"};
    DefaultTableModel viewAssignmentModel = new DefaultTableModel(viewModelHeadings,0);
    JTable viewAssignmentTable = new JTable(viewAssignmentModel){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
            Component comp = super.prepareRenderer(renderer,row,column);
            comp.setBackground(backgroundCol);
            comp.setForeground(foregroundCol);
            return comp;
            
        }
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane viewAssignmentTableScroll = new JScrollPane(viewAssignmentTable);
    
    
    
    
    public void initViewAssignmentsPanel(){
        //GET ASSIGNMENT GROUPS ASSOCIATED WITH THE CURRENT USER
        viewAssignmentsPanel.setSize(1920,1080);
        
        viewAssignmentHomeBtn.setSize(70,70); 
        viewAssignmentHomeBtn.setLocation(1700,900); 
        viewAssignmentHomeBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal()));
        viewAssignmentsPanel.add(viewAssignmentHomeBtn);
                
        viewAssignmentTable.setSize(900,600);
        viewAssignmentTable.setMinimumSize(new Dimension(900,600));
        viewAssignmentTableScroll.setSize(900,620);
        viewAssignmentTableScroll.setMinimumSize(new Dimension(900,620));
        viewAssignmentTableScroll.setLocation(10,80);
        viewAssignmentTable.getTableHeader().setBackground(backgroundCol);
        viewAssignmentTable.getTableHeader().setForeground(foregroundCol);
        viewAssignmentTable.getTableHeader().setBorder(BorderFactory.createLineBorder(foregroundCol));
        viewAssignmentTable.setBackground(backgroundCol);
        viewAssignmentTable.setFillsViewportHeight(true);
        viewAssignmentsPanel.setBackground(backgroundCol);
        controller.getAssignmentLogic().readAssignmentsStudent(viewAssignmentModel,  cbViewGroups,controller.getUserBase().getUserID()); //# CHANGE TO USE CURR USER ID -DONE
        
        viewAssignmentTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
                
            }
            @Override
            public void mousePressed(MouseEvent e){
                System.out.println(controller.getUserBase().getUserID());
                controller.getAssignmentLogic().viewAssignmentStudent(viewAssignmentModel.getValueAt((viewAssignmentTable.getSelectedRow()),4).toString(), controller.getUserBase().getUserID());
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
        viewAssignmentsPanel.add(viewAssignmentTableScroll);
        
        
        
    }
    
    public void setUserAccess(UserAuthEnum access){
        switch(access){
            case STUDENT:
                setVisibilityStudent();
                System.out.println("SETTING STUDENT VISIBILITY");
                break;
            case TEACHER:
                setVisibilityTeacher();
                System.out.println("SETTING TEACHER VISIBILITY");
                break;
            
            case ADMIN:
                setVisibilityAdmin();
                System.out.println("SETTING ADMIN VISIBILITY");
                break;
                
            case STAFF:
                
                break;
        }
    }
    
    public void setVisibilityStudent(){
        openSetAssignmentBtn.setVisible(false);
        canteenPageBtn.setVisible(false);
        viewStudentLogBtn.setVisible(false);
        addStudentLogBtn.setVisible(false);
        recordsPageBtn.setVisible(false);
        viewReportsPageBtn.setVisible(false);
        openViewAssignmentsBtn.setLocation(432,220);
    }
    
    public void setVisibilityTeacher(){
        viewTimetableBtn.setVisible(false);
        try{
            homeTestPanel.remove(viewTimetableBtn);
            
            openViewAssignmentsBtn.setVisible(false);
            recordsPageBtn.setLocation(30,220);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println("SDone");
    }
    
    public void setVisibilityAdmin(){
        openViewAssignmentsBtn.setVisible(false);
        viewStudentLogBtn.setVisible(false);
        addStudentLogBtn.setVisible(false);
        openSetAssignmentBtn.setVisible(false);
        chatPageBtn.setVisible(false);
        openForumBtn.setVisible(false);
        createForumGroupBtn.setVisible(false);
        getBalanceBtn.setVisible(false);
        addBalanceBtn.setVisible(false);
        canteenPageBtn.setVisible(false);
        viewTimetableBtn.setVisible(false);
        recordsPageBtn.setLocation(553,600);
        viewReportsPageBtn.setSize(382,190); 
        viewReportsPageBtn.setLocation(985,600); 
        viewReportsPageBtn.addActionListener(AL->screenTabs.setSelectedIndex(PageTabIndex.VIEW_REPORTS_PAGE.ordinal()));
        viewReportsPageBtn.setFont(homeFont);
        homeTestPanel.add(viewReportsPageBtn);
    }
}
