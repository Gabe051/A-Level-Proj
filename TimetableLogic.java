import javax.swing.table.*;
import java.io.*;
import java.awt.event.*;

public class TimetableLogic
{
    
    
    public void viewTimetable(DefaultTableModel timetableModel, String userID){
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\UserTimetable.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(userID)){
                    initTimetableContent(timetableModel,nextLine.split(",")[1]);
                }
                nextLine = br.readLine();
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println("Read Textfiles");
    }
    
    private void initTimetableContent(DefaultTableModel timetableModel, String timetableID){
        String[][] timetableContent = new String[5][7];
        try{
            BufferedReader br = new BufferedReader(new FileReader("TextFiles\\Timetables.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                if(nextLine.split(",")[0].equals(timetableID)){
                    String timetableParts = nextLine.split(",")[1];
                    
                    String[] columns = timetableParts.split(";");
                    for(int i = 0; i < columns.length; i++){
                        
                        String[] row = columns[i].split("@");
                        for(int k = 0; k < row.length; k++){
                            String cellContent = "";
                            if(row[k].contains(".")){
                                String[] cell = row[k].split("\\.");
                                for(String item : cell){
                                    cellContent += (item + "\n");
                                }
                                cellContent = cellContent.substring(0, cellContent.length()-2);
                            }else{
                                cellContent = row[k];
                            }
                            timetableContent[i][k] = cellContent;
                        }
                        
                    }
                    
                }
                nextLine = br.readLine();
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        for(int i = 0; i < timetableContent[0].length; i++){
            String[] row = new String[5];
            for(int k = 0; k < timetableContent.length; k++){
                row[k] = timetableContent[k][i];
            }
            timetableModel.addRow(row);
        }
    }
}
