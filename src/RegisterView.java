import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView {
    private  static RegisterView rev = new RegisterView();
    private RegisterView(){

    }
    public static RegisterView getInstance(){
        return rev;
    }

    private JFrame jf;
    private JPasswordField pwd;
    private String username;
    private String key;
    private int width,height;
    private JTextField acount;
    private JTextField keyMessage;
    private JTextField phone;

    AccountList al = AccountList.getInstance();

    /**
     * @wbp.parser.entryPoint
     */
    public void addView(){
        width = Toolkit.getDefaultToolkit().getScreenSize().width; //»ñÈ¡ÆÁÄ»¿í¶È
        height = Toolkit.getDefaultToolkit().getScreenSize().height; //»ñÈ¡ÆÁÄ»¸ß¶È
        int w = 800;int h = 470;int chuShu = 2;
        jf = new JFrame("×¢²áÕË»§");
        jf.setBounds((width - w) / 2, (height - h)/2, 661, 428);
        jf.getContentPane().setLayout(null);
        JLabel lblNewLabel = new JLabel("\u65B0\u7684\u8D26\u6237\uFF1A");
        lblNewLabel.setFont(new Font("·ÂËÎ", Font.PLAIN, 19));
        lblNewLabel.setBounds(126, 128, 105, 29);
        jf.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
        lblNewLabel_1.setFont(new Font("·ÂËÎ", Font.PLAIN, 19));
        lblNewLabel_1.setBounds(126, 183, 105, 29);
        jf.getContentPane().add(lblNewLabel_1);

        acount = new JTextField();
        acount.setBounds(241, 128, 188, 27);
        jf.getContentPane().add(acount);
        acount.setColumns(10);

        keyMessage = new JTextField();
        keyMessage.setColumns(10);
        keyMessage.setBounds(241, 185, 188, 27);
        jf.getContentPane().add(keyMessage);

        JButton sure = new JButton("\u786E\u8BA4\u6CE8\u518C");
        sure.setFont(new Font("Ó×Ô²", Font.PLAIN, 22));
        sure.setBounds(104, 307, 127, 49);
        jf.getContentPane().add(sure);
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.duDang();
                al.addAccount(acount.getText(),keyMessage.getText(),phone.getText());
                JOptionPane.showMessageDialog(jf,"×¢²á³É¹¦£¡");
                jf.dispose();
            }
        });

        JButton back = new JButton("\u8FD4\u56DE");
        back.setFont(new Font("Ó×Ô²", Font.PLAIN, 22));
        back.setBounds(444, 307, 127, 49);
        jf.getContentPane().add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);
        
        JLabel lblNewLabel_1_1 = new JLabel("\u624B\u673A\u53F7\u7801\uFF1A");
        lblNewLabel_1_1.setFont(new Font("·ÂËÎ", Font.PLAIN, 19));
        lblNewLabel_1_1.setBounds(126, 233, 105, 29);
        jf.getContentPane().add(lblNewLabel_1_1);
        
        phone = new JTextField();
        phone.setColumns(10);
        phone.setBounds(241, 239, 188, 27);
        jf.getContentPane().add(phone);
    }
}

