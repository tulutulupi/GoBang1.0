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
                    return 1;//密码正确且账户存在
                else {
                    return 2;//账户存在密码错误
                }
            }
        }
        if(Key.equals("123abc+-*"))//默认密码为123abc+-*
            return 3;//首次登录，使用了正确默认密码
        else return 4;//首次登录，使用了错误的默认密码

    }

}