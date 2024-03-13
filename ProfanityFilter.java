import java.io.*;
import javax.swing.JOptionPane; 
public class ProfanityFilter
{
    public boolean isProfane(String text){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\ProfaneFilterSheet.csv"));
            String nextLine = "";
            while((nextLine = br.readLine()) != null){
                String[] splitLine = nextLine.split(",");
                
                if(splitLine.length == 0){
                    continue;
                }
                
                if(text.toLowerCase().contains(splitLine[0].toLowerCase())){
                    return true;
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return false;
    }
}
