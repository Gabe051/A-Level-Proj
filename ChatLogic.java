    import java.io.*;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class ChatLogic
{
    private String userID;
    private String recipientID;
    private String userName;
    private String recipientName;
    private String chatID;

    
    public String getRecipientID(){
        return this.recipientID;
    }
    
    public ChatLogic(){
        this.chatID = "";
    }
    
    public void initConvo(String userID, String recipientID, String userName, String recipientName, JTextArea taContent){
        this.userID = userID; this.recipientID = recipientID; this.userName = userName; this.recipientName = recipientName;
        retrieveChatID();
        String chatContent = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\ChatHistory.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                
                if(nextLine.substring(0,6).equals(this.chatID)){
                    String[] chatHistory = nextLine.substring(7).split("~}@");
                    for(int i = 0; i < chatHistory.length; i++){
                        
                        chatContent += chatHistory[i] + "\n";
                    }
                }
                nextLine = br.readLine();
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        taContent.setText(chatContent);
    }
    
    private void retrieveChatID(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\UserUserChat.txt"));
            String nextLine = br.readLine();
            //# Get chat ID from the users
            while(nextLine != null){
                String[] lineData = nextLine.split(",");
                if(lineData[0].equals(userID) && lineData[1].equals(recipientID)){
                    this.chatID = lineData[2];
                    return;
                }    
                else if(lineData[0].equals(recipientID) && lineData[1].equals(userID)){
                    this.chatID = lineData[2];
                    return;
                }
                nextLine = br.readLine();
            }
            br.close();
            //# If user chat doesnt exist, create one
            createChatEntry();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void createChatEntry(){
        try{
            String tempChatID = "";
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\InitialisationData.txt"));
            String fileContent = "";
            String nextLine = br.readLine();
            //# Get the chat count and make a new chatID
            while(nextLine != null){
                String[] lineData = nextLine.split(",");
                if(lineData[0].equals("chatCount")){
                    int chatCount = Integer.parseInt(lineData[1]) + 1;
                    
                    String tempChatCount = ""+(chatCount);
                    
                    while(tempChatCount.length() < 5){
                        tempChatCount = "0" + tempChatCount;
                    }
                    tempChatID = "C" + tempChatCount;
                    
                    fileContent += ("chatCount," + tempChatCount +"\n");
                }else{
                    fileContent += (nextLine + "\n");
                }
                nextLine = br.readLine();
                //# New chatID made
            }
            br.close(); 
            this.chatID = tempChatID;
            String[] splitFileContent = fileContent.split("\n");
            BufferedWriter bw = new BufferedWriter( new FileWriter("TextFiles\\InitialisationData.txt", false));
            for(String line : splitFileContent){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
            bw = new BufferedWriter(new FileWriter("TextFiles\\UserUserChat.txt", true));
            bw.write(""+this.userID+","+this.recipientID+","+this.chatID); 
            bw.newLine();
            bw.close();
            bw = new BufferedWriter(new FileWriter("TextFiles\\ChatHistory.txt", true));
            bw.write(this.chatID+",");
            bw.newLine();
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void sendMsg(String msgContent, JTextArea taContent,JTextArea toClear){
        if(this.chatID.equals("")){
            JOptionPane.showMessageDialog(null, "Select a chat first");
            return;
        }
        msgContent = msgContent.replace("\n","~}@");
        msgContent = msgContent + "~}@";
        ProfanityFilter pf = new ProfanityFilter();
        if(pf.isProfane(msgContent)){
            JOptionPane.showMessageDialog(null, "Message contains profanity and will not be sent", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\ChatHistory.txt"));
            String nextLine = br.readLine();
            String fileContent = "";
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                if(splitLine[0].equals(this.chatID)){
                    fileContent += (nextLine + (this.userName+": "+msgContent) + "\n");
                }else{
                    fileContent += (nextLine + "\n");
                }
                nextLine = br.readLine();
            }
            br.close();
            BufferedWriter bw = new BufferedWriter( new FileWriter("TextFiles\\ChatHistory.txt", false));
            for(String line : fileContent.split("\n")){
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        initConvo(this.userID, this.recipientID, this.userName, this.recipientName,taContent);
        toClear.setText("");
    }
    
}
