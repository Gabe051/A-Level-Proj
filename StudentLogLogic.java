import java.io.*;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
public class StudentLogLogic
{
    public void readLog(String userID, JTextArea taLogContent){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\StudentLog.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(userID)){
                    String logContent = nextLine.substring(7);
                    logContent = logContent.replace("~}@","\n");
                    taLogContent.setText(logContent);
                    br.close();
                    JOptionPane.showMessageDialog(null, "Log History Opened");
                    return;
                }
                nextLine = br.readLine();
            }
            br.close();
            taLogContent.setText("");
            JOptionPane.showMessageDialog(null, "No Log Entries");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void createLog(String userID, JTextArea taLogContent){
        try{
            boolean flag = false;
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\StudentLog.txt"));
            String nextLine = br.readLine();
            String fileContent = "";
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(userID)){
                    fileContent += (nextLine + "Log:" + taLogContent.getText().replace("\n", "~}@"))+ "~}@"+"\n";
                    flag = true;
                }else{
                    fileContent += nextLine + "\n";
                }
                nextLine = br.readLine();
            }
            if(!flag){
                fileContent += userID+",Log:" +taLogContent.getText().replace("\n","~}@") + "~}@"+  "\n";
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("TextFiles\\StudentLog.txt",false));
            for(String line : fileContent.split("\\n")){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
