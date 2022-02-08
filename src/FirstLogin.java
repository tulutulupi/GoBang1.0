import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FirstLogin {
    private static FirstLogin fl = new FirstLogin();
    private FirstLogin(){

    }
    public static FirstLogin getInstance(){
        return fl;
    }

    private JFrame jf;
    private String key,key2;
    private JTextField phoneNums;
    private String phone;
    private int width,height,w,h;

    MeunView mv = MeunView.getInstance();
    AccountList al = AccountList.getInstance();
    private JPasswordField newKey;
    private JPasswordField newKey1;
    /**
     * @wbp.parser.entryPoint
     */
    public void first(String username){
        width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取屏幕宽度
        height = Toolkit.getDefaultToolkit().getScreenSize().height; //获取屏幕高度
        w = 800;//
        h = 470;//

        final String regex = "(?=(?:.*[A-Za-z]){1,})(?=(?:.*\\d){1,})(?=(?:.*[!@#$%^&*()\\-_=+\\{\\};:," +
                "<.>]){0,})([A-Za-z0-9!@#$%^&*()\\-_=+\\{\\};:,<.>]{7,20})";
        final String regexs = "(13[3-9]|14[7-8]|15[0-27-9]|178|18[2-47-8]|198|13[0-2]|14[5-6]|15[5-6]|166|17[5-6]|" +
                "18[5-6]|133|149|153|17[37]|18[0-19]|199|17[0-1])[0-9]{8}";
        jf = new JFrame();
        jf.setBounds((width - w) / 2, (height - h)/2, 583, 470);

        jf.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("首次登录请修改密码并保留手机号");
        lblNewLabel.setFont(new Font("幼圆", Font.PLAIN, 20));
        lblNewLabel.setBounds(104, 37, 312, 53);
        jf.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
        lblNewLabel_1.setFont(new Font("幼圆", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(53, 116, 88, 33);
        jf.getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("\u624B\u673A\u53F7\uFF1A");
        lblNewLabel_1_1.setFont(new Font("幼圆", Font.PLAIN, 20));
        lblNewLabel_1_1.setBounds(53, 220, 88, 33);
        jf.getContentPane().add(lblNewLabel_1_1);
        
        phoneNums = new JTextField();
        phoneNums.setColumns(10);
        phoneNums.setBounds(171, 222, 217, 33);
        jf.getContentPane().add(phoneNums);
        
        JButton OK = new JButton("\u4FEE\u6539\u5B8C\u6210");
        OK.setFont(new Font("幼圆", Font.PLAIN, 18));
        OK.setBounds(147, 299, 241, 33);
        jf.getContentPane().add(OK);
        
        newKey = new JPasswordField();
        newKey.setEchoChar('*');
        newKey.setFont(new Font("幼圆", Font.PLAIN, 15));
        newKey.setBounds(171, 169, 217, 33);
        jf.getContentPane().add(newKey);
        
        JLabel lblNewLabel_1_2 = new JLabel("\u518D\u6B21\u786E\u8BA4\uFF1A");
        lblNewLabel_1_2.setFont(new Font("幼圆", Font.PLAIN, 20));
        lblNewLabel_1_2.setBounds(53, 167, 108, 33);
        jf.getContentPane().add(lblNewLabel_1_2);
        
        newKey1 = new JPasswordField();
        newKey1.setEchoChar('*');
        newKey1.setFont(new Font("幼圆", Font.PLAIN, 15));
        newKey1.setBounds(171, 116, 217, 33);
        jf.getContentPane().add(newKey1);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                key = newKey.getText();
                key2 = newKey1.getText();
                phone = phoneNums.getText();
                if(key.equals(key2)) {
                	if(key.matches(regex))//判断格式是否正确
                    {
                    if(phone.matches(regexs)){
                        al.duDang();
                        al.addAccount(username,key,phone);
                        mv.view(username);
                        jf.dispose();
                    }else{
                        JOptionPane.showMessageDialog(jf,"您输入的电话号码不合规范","错误提示",1);
                    }
                }else{
                    JOptionPane.showMessageDialog(jf,"您输入的密码不符合要求","错误提示",1);
                }
                }else {
                	JOptionPane.showMessageDialog(jf,"您两次输入的密码不一致","错误提示",1);
                }
                
            }
        });

        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
