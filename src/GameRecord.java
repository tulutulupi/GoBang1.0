import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GameRecord {
    private String player1;
    private String player2;
    private ArrayList<Chess> records;
    private Date StartTime;
    private int outcome;//最后记录1为所记录的账户胜利，2为输，3为平局
    private boolean ifSelected;//是否被选中
    private String accountName;

    public int getContestNum() {
        return ContestNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setContestNum(int contestNum) {
        ContestNum = contestNum;
    }

    private int ContestNum;

    public boolean isIfSelected() {
        return ifSelected;
    }

    public void setIfSelected(boolean ifSelected) {
        this.ifSelected = ifSelected;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public ArrayList<Chess> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Chess> records) {
        this.records = records;
    }
    public GameRecord(String accountName,String player1,String player2,ArrayList<Chess> records,Date startTime,int outcome,boolean ifSelected,int Con){
        this.accountName = accountName;
        this.player1 = player1;
        this.player2 = player2;
        this.records = records;
        this.StartTime = startTime;
        this.outcome = outcome;
        this.ifSelected = ifSelected;
        this.ContestNum = Con;
    }
    public GameRecord(){
        super();//json需要的无参构造器
    }

    @Override
    public String toString() {
        return "record [记录所属账号："+accountName+"对局编号:"+ContestNum +"玩家一:" + player1 + ", 玩家二:" + player2 +"开始时间:"+StartTime+ "具体步法记录:["+records+"]";
    }
}
