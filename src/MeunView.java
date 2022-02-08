import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeunView {
    private static MeunView mv = new MeunView();
    private MeunView(){

    }
    public static MeunView getInstance(){
        if(mv!=null)
        return mv;
        else return new MeunView();
    }

    private JFrame jf;
    private Container con;
    private int width,height,w,h;

    RecallView rv = RecallView.getInstance();
    PlayerInput pi = PlayerInput.getInstance();

    /**
     * @wbp.parser.entryPoint
     */
    public void view(String username){
        width = Toolkit.getDefaultToolkit().getScreenSize().width; //��ȡ��Ļ���
        height = Toolkit.getDefaultToolkit().getScreenSize().height; //��ȡ��Ļ�߶�
        w = 463;//
        h = 332;//
        jf = new JFrame();
        jf.getContentPane().setFont(new Font("���Ŀ���", Font.PLAIN, 21));
        jf.setBounds((width - w) / 2, (height - h)/2,493,357);

        con = jf.getContentPane();
        con.setLayout(null);
        
        JButton BeginGame = new JButton("\u5F00\u59CB\u5BF9\u6218");
        BeginGame.setBackground(SystemColor.activeCaption);
        BeginGame.setFont(new Font("���Ŀ���", Font.PLAIN, 21));
        BeginGame.setBounds(161, 56, 130, 58);
        jf.getContentPane().add(BeginGame);

        BeginGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pi.view(username);//ǿ�������ս����Ϣ
                jf.dispose();

            }
        });
        
        JButton Record = new JButton("\u5BF9\u6218\u8BB0\u5F55");
        Record.setBackground(SystemColor.activeCaption);
        Record.setFont(new Font("���Ŀ���", Font.PLAIN, 21));
        Record.setBounds(161, 157, 130, 58);
        jf.getContentPane().add(Record);

        Record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                rv.Recall(username);//����طŽ���
                jf.dispose();

            }
        });

        con.setVisible(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
}
