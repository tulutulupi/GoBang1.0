import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window.Type;

public class LoginView {
    private static LoginView LV = new LoginView();
    private JFrame jf;
    private Container con;
    private JTextField usernameText;
    private JPasswordField pwd;
    private String username;
    private String key;
    private int width,height,w,h;
    private LoginView(){

    }
    public static LoginView getInstance(){
        return LV;
    }

    LoginController lc = LoginController.getInstance();
    MeunView mv = MeunView.getInstance();
    FirstLogin fl = FirstLogin.getInstance();

    /**
     * @wbp.parser.entryPoint
     */
    public void login(){
        width = Toolkit.getDefaultToolkit().getScreenSize().width; //��ȡ��Ļ���
        height = Toolkit.getDefaultToolkit().getScreenSize().height; //��ȡ��Ļ�߶�
        w = 800;//
        h = 470;//
        jf = new JFrame();
        jf.getContentPane().setFont(new Font("��Բ", Font.PLAIN, 9));
        jf.setType(Type.POPUP);
        jf.setResizable(false);//���ڲ����϶�
        jf.setBounds((width - w) / 2, (height - h)/2, w, h);

        ImageIcon img = new ImageIcon("mu/backGround1.jpg");
        JLabel jl = new JLabel(img);
        jf.getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));
        jl.setBounds(0, 0, w, h);
        JPanel jp1 = (JPanel) jf.getContentPane();
        jp1.setOpaque(false);

        con = jf.getContentPane();

        con.setLayout(null);

        JLabel firstTitleT = new JLabel("�������սϵͳ");
        firstTitleT.setBounds(201, 56, 375, 58);
        con.add(firstTitleT);

        firstTitleT.setVerticalAlignment(SwingConstants.TOP);
        firstTitleT.setHorizontalAlignment(SwingConstants.CENTER);
        firstTitleT.setForeground(Color.BLUE);
        firstTitleT.setFont(new Font("����", Font.BOLD, 50));

        JLabel label1 = new JLabel("��¼����");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("��Բ", Font.PLAIN, 18));
        label1.setBounds(213, 160, 75, 33);
        con.add(label1);

        usernameText = new JTextField("",20);
        usernameText.setFont(new Font("��Բ", Font.PLAIN, 18));
        usernameText.setBounds(323, 159, 233, 33);
        con.add(usernameText);

        JLabel label2 = new JLabel("���룺");
        label2.setFont(new Font("��Բ", Font.PLAIN, 18));
        label2.setBounds(213, 217, 75, 33);
        con.add(label2);

        pwd = new JPasswordField("",20);
        pwd.setEchoChar('*');
        pwd.setFont(new Font("����", Font.PLAIN, 18));
        pwd.setBounds(323, 218, 233, 33);
        con.add(pwd);


        JButton jb1 = new JButton("��¼");
        jb1.setFont(new Font("��Բ", Font.PLAIN, 20));
        jb1.setBounds(125, 316, 100, 40);
        con.add(jb1);

        jb1.setBackground(new Color(22,184,152));
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameText.getText();
                key = pwd.getText();

                if(!username.equals("")){
                    if (lc.Login(username, key) == 1) {
                        mv.view(username);
                        jf.dispose();
                    }
                    if (lc.Login(username, key) == 2) {
                        JOptionPane.showMessageDialog(jf, "�˺��������", "��¼����", JOptionPane.WARNING_MESSAGE);
                        setFiledNull();
                    }
                    if (lc.Login(username, key) == 3) {
                        fl.first(username);
                        jf.dispose();
                        //���״ε�¼�����Ҫ��ǿ�Ƹ�����
                    }
                    if (lc.Login(username, key) == 4)
                        JOptionPane.showMessageDialog(jf, "��������ȷĬ�����룡", "MessageIncorrect", 2);
                }
                else JOptionPane.showMessageDialog(jf,"�������˺������ٵ�¼","shabiceshi",JOptionPane.YES_OPTION);
            }
        });
        JButton jb2 = new JButton("����");
        jb2.setFont(new Font("��Բ", Font.PLAIN, 20));
        jb2.setBounds(624, 316, 100, 40);
        con.add(jb2);

        jb2.setBackground(new Color(22, 184, 152));
        jb2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton jb3 = new JButton("\u6CE8\u518C");
        jb3.setFont(new Font("��Բ", Font.PLAIN, 20));
        jb3.setBackground(new Color(22, 184, 152));
        jb3.setBounds(290, 316, 100, 40);
        jf.getContentPane().add(jb3);
        
        JButton fandkey = new JButton("\u627E\u56DE\u5BC6\u7801");
        fandkey.setFont(new Font("��Բ", Font.PLAIN, 15));
        fandkey.setBackground(new Color(22, 184, 152));
        fandkey.setBounds(456, 316, 100, 40);
        jf.getContentPane().add(fandkey);
        
        JLabel lblNewLabel = new JLabel("\u9ED8\u8BA4\u5BC6\u7801\uFF1A123abc+-*");
        lblNewLabel.setFont(new Font("��Բ", Font.PLAIN, 16));
        lblNewLabel.setBounds(323, 261, 168, 26);
        jf.getContentPane().add(lblNewLabel);
        fandkey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jf, "���ܴ�����");
            }
        });

        
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView rev = RegisterView.getInstance();
                rev.addView();

            }
        });

        con.setVisible(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void setFiledNull(){
        this.usernameText.setText("");
        this.pwd.setText("");
    }
}
