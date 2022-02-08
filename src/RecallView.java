import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class RecallView {
    private static RecallView rv = new RecallView();
    private RecallView(){

    }
    public static RecallView getInstance(){
        return rv;
    }

    private JFrame jf;
    private Container con;
    private int width,height,w,h;
    private JTable table;
    private JScrollPane scrollPane;
    private int selectCount;//记录在表格中选择的记录数量
    private  ArrayList<GameRecord> recordsList = new ArrayList<GameRecord>();
    private ArrayList<GameRecord> modifyList = new ArrayList<GameRecord>();
    RecordTable rt = null;

    RecordList rl = RecordList.getInstance();

    BoFang bf =BoFang.getInstance();

    /**
     * @wbp.parser.entryPoint
     */
    public void Recall(String username){
        //获取屏幕宽度
        width = Toolkit.getDefaultToolkit().getScreenSize().width;
        //获取屏幕高度
        height = Toolkit.getDefaultToolkit().getScreenSize().height;

        w = 595;//
        h = 389;//
        jf = new JFrame();
        jf.setBounds((width - w) / 2, (height - h)/2, 766, 502);

        con = jf.getContentPane();
        con.setLayout(null);
        
        table = new JTable();//创建table 的框架和模型
        table.setModel(new DefaultTableModel(new Object[][]{},new String[]{"","对战编号","执黑者","执白者","对战时间","对战结果"}));
        table.setBounds(30, 25, 679, 317);

        table.getTableHeader().setReorderingAllowed(false);//设置列表头不可被用户重新拖动排列


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 25, 679, 317);
        jf.getContentPane().add(scrollPane);

        try {
            recordsList = rl.Read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //筛选登录账户下的对战记录

        modifyList.clear();//清空初始化
        for(GameRecord gr:recordsList){
            if(gr.getAccountName().equals(username))
                modifyList.add(gr);
        }

        rt = new RecordTable(modifyList);//将筛选以后的数据赋值给table的一个对象上去

        table.setModel(rt);//通过赋值过的对象，将数据显示到table

        JButton lookin = new JButton("观看回放");
        lookin.setFont(new Font("楷体", Font.PLAIN, 22));
        lookin.setBounds(30, 386, 127, 45);
        jf.getContentPane().add(lookin);

        lookin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //将记录读档，很重要！！！！！！！！！！！！要不然每次的对象都是空的，哭哭
                rl.DuDang();

                //初始化每条记录的被选中状态
                for (GameRecord w: rl.gameRecords){
                    System.out.println(w.getPlayer1());//测试
                    w.setIfSelected(false);
                }

                selectCount=0;//初始化选中数量

                //测试
                for(int i=0;i<table.getRowCount();i++){

                    System.out.println(table.getValueAt(i,1));
                    System.out.println(((table.getValueAt(i,1)).getClass()));
                }

                //通过循环来判断被选中的记录的条数
                for(int i=0;i<table.getRowCount();i++){
                    if((Boolean)table.getValueAt(i,0)){
                        for (GameRecord w: rl.gameRecords){
                            if(w.getContestNum() == (int)table.getValueAt(i,1)){
                                w.setIfSelected(true);
                                selectCount++;
                            }
                        }
                    }
                }
                System.out.println("所选对局的数量"+selectCount);

                if(selectCount!=1){
                    JOptionPane.showMessageDialog(jf,"请选择一盘棋局观看回放");
                }
                else {
                    for (GameRecord w: rl.gameRecords){
                        if(w.isIfSelected()){
                            bf.chuan(w.getRecords(),w.getOutcome(),username);
                            bf.myJFrame();//开启复盘的界面,并且将相应的步数传给复盘界面BoFang
                            jf.dispose();
                            break;
                        }
                    }
                }
                selectCount=0;//初始化选中数量
            }
        });
        
        JButton backView = new JButton("返回菜单");
        backView.setFont(new Font("楷体", Font.PLAIN, 22));
        backView.setBounds(582, 386, 127, 45);
        jf.getContentPane().add(backView);

        backView.addActionListener(e -> {
            MeunView mv = MeunView.getInstance();
            mv.view(username);
            jf.dispose();

        });

        JButton qipu = new JButton("\u89C2\u770B\u68CB\u8C31");
        qipu.setFont(new Font("楷体", Font.PLAIN, 22));
        qipu.setBounds(184, 386, 127, 45);
        jf.getContentPane().add(qipu);

        //查看棋谱
        qipu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rl.DuDang();

                //初始化每条记录的被选中状态
                for (GameRecord w: rl.gameRecords){
                    System.out.println(w.getPlayer1());//测试
                    w.setIfSelected(false);
                }

                selectCount=0;//初始化选中数量

                //测试
                for(int i=0;i<table.getRowCount();i++){

                    System.out.println(table.getValueAt(i,1));
                    System.out.println(((table.getValueAt(i,1)).getClass()));
                }

                //通过循环来判断被选中的记录的条数
                for(int i=0;i<table.getRowCount();i++){
                    if((Boolean)table.getValueAt(i,0)){
                        for (GameRecord w: rl.gameRecords){
                            if(w.getContestNum() == (int)table.getValueAt(i,1)){
                                w.setIfSelected(true);
                                selectCount++;
                            }
                        }
                    }
                }
                System.out.println("所选对局的数量"+selectCount);

                if(selectCount!=1){
                    JOptionPane.showMessageDialog(jf,"请选择一盘棋局观看回放");
                }
                else {
                    for (GameRecord w: rl.gameRecords){
                        if(w.isIfSelected()){
                            QMap qm = QMap.getInstance();
                            qm.myJFrame(w.getRecords(),w.getOutcome(),username);
                            jf.dispose();
                            break;
                        }
                    }
                }
                selectCount=0;//初始化选中数量
            }

        });

        con.setVisible(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
