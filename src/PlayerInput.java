import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInput {

    private static PlayerInput pi = new PlayerInput();
    private PlayerInput(){

    }
    public static PlayerInput getInstance(){
        if(pi!=null)
        return pi;
        else
            return new PlayerInput();
    }

    private JFrame jf;
    private String player1;
    private String player2;
    private JTextField blackOne;
    private JTextField whiteOne;
    private int width,height,w,h;
    private static int  backValue =0;
    /**
     * @wbp.parser.entryPoint
     */
    MyJFrame mjf = MyJFrame.getInstance();
    private JTextField contantName;
    private JTextField contantNum;
    private JTextField contantLocal;
    private JTextField referee;
    /**
     * @wbp.parser.entryPoint
     */
    public int view(String username){

        width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取屏幕宽度
        height = Toolkit.getDefaultToolkit().getScreenSize().height; //获取屏幕高度
        w = 400;//
        h = 400;//
        jf = new JFrame("比赛选手信息录入");
        jf.setBounds((width - w) / 2, (height - h)/2, 553, 532);
        jf.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("\u6267\u9ED1\u8005\uFF1A");
        lblNewLabel.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel.setBounds(64, 82, 77, 35);
        jf.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("\u6267\u767D\u8005\uFF1A");
        lblNewLabel_1.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(64, 140, 77, 35);
        jf.getContentPane().add(lblNewLabel_1);
        
        blackOne = new JTextField();
        blackOne.setFont(new Font("幼圆", Font.PLAIN, 18));
        blackOne.setBounds(151, 82, 192, 29);
        jf.getContentPane().add(blackOne);
        blackOne.setColumns(10);
        
        whiteOne = new JTextField();
        whiteOne.setFont(new Font("幼圆", Font.PLAIN, 18));
        whiteOne.setColumns(10);
        whiteOne.setBounds(151, 146, 192, 29);
        jf.getContentPane().add(whiteOne);
        
        
        
        JLabel lblNewLabel_2 = new JLabel("\u8BF7\u8F93\u5165\u6BD4\u8D5B\u4FE1\u606F");
        lblNewLabel_2.setFont(new Font("幼圆", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(167, 21, 165, 35);
        jf.getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_1_1 = new JLabel("\u6BD4\u8D5B\u540D\u79F0");
        lblNewLabel_1_1.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(64, 199, 77, 35);
        jf.getContentPane().add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("\u6BD4\u8D5B\u573A\u5730");
        lblNewLabel_1_1_1.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel_1_1_1.setBounds(64, 258, 77, 35);
        jf.getContentPane().add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_1_1_1 = new JLabel("\u6BD4\u8D5B\u573A\u6B21");
        lblNewLabel_1_1_1_1.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel_1_1_1_1.setBounds(64, 312, 77, 35);
        jf.getContentPane().add(lblNewLabel_1_1_1_1);
        
        JLabel lblNewLabel_1_1_1_1_1 = new JLabel("\u6BD4\u8D5B\u88C1\u5224");
        lblNewLabel_1_1_1_1_1.setFont(new Font("幼圆", Font.PLAIN, 18));
        lblNewLabel_1_1_1_1_1.setBounds(64, 365, 77, 35);
        jf.getContentPane().add(lblNewLabel_1_1_1_1_1);
        
        contantName = new JTextField();
        contantName.setFont(new Font("幼圆", Font.PLAIN, 18));
        contantName.setColumns(10);
        contantName.setBounds(151, 199, 192, 29);
        jf.getContentPane().add(contantName);
        
        contantNum = new JTextField();
        contantNum.setFont(new Font("幼圆", Font.PLAIN, 18));
        contantNum.setColumns(10);
        contantNum.setBounds(151, 315, 192, 29);
        jf.getContentPane().add(contantNum);
        
        contantLocal = new JTextField();
        contantLocal.setFont(new Font("幼圆", Font.PLAIN, 18));
        contantLocal.setColumns(10);
        contantLocal.setBounds(151, 258, 192, 29);
        jf.getContentPane().add(contantLocal);
        
        referee = new JTextField();
        referee.setFont(new Font("幼圆", Font.PLAIN, 18));
        referee.setColumns(10);
        referee.setBounds(151, 365, 192, 29);
        jf.getContentPane().add(referee);
        
        JButton sure = new JButton("\u4FE1\u606F\u786E\u8BA4");
        sure.setFont(new Font("幼圆", Font.PLAIN, 18));
        sure.setBounds(151, 435, 158, 35);
        jf.getContentPane().add(sure);

        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(blackOne.getText().equals("")||whiteOne.getText().equals("")||referee.getText().equals("")||contantNum.getText().equals("")||contantName.getText().equals("")||contantLocal.getText().equals("")){
                    //在此处将对战的信息输入信息表
                    JOptionPane.showMessageDialog(jf,"如果不输入完整信息将无法进入比赛！","系统提示",2);
                    backValue = 1;
                }
                else {
                    mjf.myJFrame();
                    mjf.setUN(username,blackOne.getText(),whiteOne.getText(),contantNum.getText(),referee.getText(),contantName.getText(),contantLocal.getText());
                    jf.dispose();
                    backValue = 2;
                }
            }
        });

        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return backValue;
    }
}
