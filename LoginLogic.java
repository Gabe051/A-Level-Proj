import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.io.*;

public class LoginLogic
{
    
    public Boolean loginUser(String usernameEntered, String passwordEntered, Controller controller,JTabbedPane tabs){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Accounts.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                String[] lineParts = nextLine.split(",");
                if(lineParts[1].equalsIgnoreCase(usernameEntered)){
                    if(lineParts[2].equals(passwordEntered)){
                        initUser(lineParts[0], controller);
                        JOptionPane.showMessageDialog(null, "Logged In");
                        tabs.setSelectedIndex(PageTabIndex.HOME_PAGE.ordinal());
                        
                        return true;
                    }else{
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                        return false;
                    }
                    
                }
                nextLine = br.readLine();
            }
            JOptionPane.showMessageDialog(null, "User does not exist");
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    private void initUser(String userID, Controller controller){
        String[] userParts;
        switch(userID.charAt(0)){
            case 'S':
                controller.setUserAuth(UserAuthEnum.STUDENT);
                userParts = getUserInfo("TextFiles\\StudentRecords.txt", userID);
                controller.setStudentUser(userParts[0], userParts[1], userParts[2],userParts[3], userParts[4], userParts[5], userParts[6], userParts[7], userParts[8]);
                break;
            case 'T':
                controller.setUserAuth(UserAuthEnum.TEACHER);
                userParts = getUserInfo("TextFiles\\TeacherRecords.txt", userID);
                controller.setTeacherUser(userParts[0], userParts[1], userParts[2], userParts[3], userParts[4]);
                break;
            case 'A':
                controller.setUserAuth(UserAuthEnum.ADMIN);
                userParts = getUserInfo("TextFiles\\AdminRecords.txt", userID);
                controller.setAdminUser(userParts[0], userParts[1], userParts[2],"");
                break;
            case 'O':
                //String[] userParts = getUserInfo("TextFiles\\StaffRecords.txt", userID);
                //controller.setStaffUser(userID);
                break;
        }
        
        
        return;
    }
    
    
    private String[] getUserInfo(String filePath,String userID){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String nextLine = br.readLine();
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(userID)){
                    return nextLine.split(",");
                }
                nextLine = br.readLine();
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String[0];
    }
}

