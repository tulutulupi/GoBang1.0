import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AccountList {
    private static AccountList al = new AccountList();
    private AccountList(){

    }
    public static AccountList getInstance(){
        return al;
    }

    ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(String name, String key, String phoneNum){
        Account account = new Account(name,key,phoneNum);
        accounts.add(account);
        Save();
    }
    public void init(){
        addAccount("xxs","123","15229569640");
    }

    public void Save(){
        File file = new File("mu/Account.json");
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(file,accounts);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    public ArrayList<Account> Read()throws JsonParseException, JsonMappingException, IOException{
        File file = new File("mu/Account.json");
        ObjectMapper om = new ObjectMapper();
        return om.readValue(file, new TypeReference<ArrayList<Account>>(){});
    }

    public void duDang(){
        try {
            accounts = Read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
