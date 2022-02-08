import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    private static LoginController lc = new LoginController();
    private LoginController(){

    }
    public static LoginController getInstance(){
        return lc;
    }

    AccountList al = AccountList.getInstance();

    public int  Login(String name ,String Key){
        ArrayList<Account> accounts = null;
        try {
            accounts = al.Read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(accounts);

        for(Account a:accounts){
            if(a.getName().equals(name)){
                if(a.getKey().equals(Key))
                    return 1;//������ȷ���˻�����
                else {
                    return 2;//�˻������������
                }
            }
        }
        if(Key.equals("123abc+-*"))//Ĭ������Ϊ123abc+-*
            return 3;//�״ε�¼��ʹ������ȷĬ������
        else return 4;//�״ε�¼��ʹ���˴����Ĭ������

    }

}