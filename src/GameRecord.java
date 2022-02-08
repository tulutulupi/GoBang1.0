import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GameRecord {
    private String player1;
    private String player2;
    private ArrayList<Chess> records;
    private Date StartTime;
    private int outcome;//����¼1Ϊ����¼���˻�ʤ����2Ϊ�䣬3Ϊƽ��
    private boolean ifSelected;//�Ƿ�ѡ��
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
        super();//json��Ҫ���޲ι�����
    }

    @Override
    public String toString() {
        return "record [��¼�����˺ţ�"+accountName+"�Ծֱ��:"+ContestNum +"���һ:" + player1 + ", ��Ҷ�:" + player2 +"��ʼʱ��:"+StartTime+ "���岽����¼:["+records+"]";
    }
}
