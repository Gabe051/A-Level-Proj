import java.util.ArrayList;
import java.io.*;

public class PostF
{
    private String opUserID;
    private String dateTime;
    private String postID;
    private String groupID;
    private String postContent;
    private ArrayList<PostF> replies = new ArrayList<PostF>();
    private PostF previousReply;
    private PostF rootPost;
    
    public String getOpName(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\StudentRecords.txt"));
            String nextLine = "";
            while(nextLine != null){
                
                String[] splitLine = nextLine.split(",");
                
                if(splitLine[0].equals(this.opUserID)){
                    return (splitLine[1] + " " + splitLine[2]);
                }
                
                nextLine = br.readLine();
            }
            
            br.close();
            br = new BufferedReader(new FileReader("TextFiles\\TeacherRecords.txt"));
            nextLine = "";
            
            while(nextLine != null){
                String[] splitLine = nextLine.split(",");
                
                if(splitLine[0].equals(this.opUserID)){
                    return (splitLine[1] + " " + splitLine[2]);
                }
                
                nextLine = br.readLine();
            }
            
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "";
    }
    
    public void setRootPost(PostF root){
        this.rootPost = root;
    }
    
    public PostF getRootPost(){
        return this.rootPost;
    }
    
    public void setPrevReply(PostF reply){
        this.previousReply = reply;
    }
    
    public PostF getPrevReply(){
        return this.previousReply;
    }
    
    public int getNumReplies(){
        return this.replies.size();
    }
    
    public String getOpUserID(){
        return this.opUserID;
    }
    
    public String getPostDate(){
        return this.dateTime;
    }
    
    public String getPostID(){
        return this.postID;
    }
    
    public String getGroupID(){
        return this.groupID;
    }
     
    public String getPostContent(){
        return this.postContent;
    }
    
    public ArrayList<PostF> getReplies(){
        return this.replies;
    }
    
    public PostF(String postID, String opUserID, String dateTime,String postContent, ArrayList<PostF> replies){
        this.postID = postID; this.opUserID = opUserID; this.dateTime = dateTime; this.postContent = postContent;
        this.replies = replies;
    }
    
    public PostF(String postID, String opUserID, String dateTime, String postContent){
        this.postID = postID; this.opUserID = opUserID; this.dateTime = dateTime; this.postContent = postContent;
    }
    
    public PostF(){
        
    }
    
}
