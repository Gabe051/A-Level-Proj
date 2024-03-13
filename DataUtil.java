import java.io.*;
import java.util.*;

public class DataUtil
{
    public ArrayList<String> getSubjects(){
        ArrayList<String> outputSubjects = new ArrayList<String>();
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\TeacherRecords.txt"));
            String nextLine = br.readLine();
            HashSet<String> subjects = new HashSet<String>();
            while(nextLine != null){
                String[] lineParts = nextLine.split(",");
                subjects.add(lineParts[3]);
                nextLine = br.readLine();
            }
            
            for(String subject : subjects){
                outputSubjects.add(subject);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return outputSubjects;
    }
}
