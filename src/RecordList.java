import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecordList {
    private static RecordList rl = new RecordList();
    private RecordList(){
    }
    public static RecordList getInstance(){
        return rl;
    }
    private static int contestNum=0;

    ArrayList<GameRecord> gameRecords = new ArrayList<>();

    public void addRecord(String accountName,String player1, String player2, ArrayList<Chess> records, Date startTime, int outcome){
        contestNum ++;
        GameRecord gr = new GameRecord(accountName, player1, player2,records,startTime,outcome,false,contestNum);
        gameRecords.add(gr);
        Save();
    }

    public void Save(){
        File file = new File("mu/Records.json");
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(file,gameRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameRecord> Read() throws JsonParseException, JsonMappingException, IOException{
            File file = new File("mu/Records.json");
            ObjectMapper om = new ObjectMapper();
            return om.readValue(file, new TypeReference<ArrayList<GameRecord>>(){});
    }
    public void DuDang(){
        try {
            this.gameRecords = Read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contestNum = gameRecords.size();
    }



}
