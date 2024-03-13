
public class ReportStruct
{
    private String reportID;
    private String reporterID;
    private String reportContent;
    
    public ReportStruct(String reportID, String userID, String reportContent){
        this.reportID = reportID; this.reporterID = userID; this.reportContent = reportContent;
    }
    
    public void setReportID(String reportID){
        this.reportID = reportID;
    }
    
    public void setReportContent(String reportContent){
        this.reportContent = reportContent;
    }
    
    public String getReportID(){
        return this.reportID;
    }
    
    public String getReportContent(){
        return this.reportContent;
    }
    
}
