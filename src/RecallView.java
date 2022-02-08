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
    private int selectCount;//��¼�ڱ����ѡ��ļ�¼����
    private  ArrayList<GameRecord> recordsList = new ArrayList<GameRecord>();
    private ArrayList<GameRecord> modifyList = new ArrayList<GameRecord>();
    RecordTable rt = null;

    RecordList rl = RecordList.getInstance();

    BoFang bf =BoFang.getInstance();

    /**
     * @wbp.parser.entryPoint
     */
    public void Recall(String username){
        //��ȡ��Ļ���
        width = Toolkit.getDefaultToolkit().getScreenSize().width;
        //��ȡ��Ļ�߶�
        height = Toolkit.getDefaultToolkit().getScreenSize().height;

        w = 595;//
        h = 389;//
        jf = new JFrame();
        jf.setBounds((width - w) / 2, (height - h)/2, 766, 502);

        con = jf.getContentPane();
        con.setLayout(null);
        
        table = new JTable();//����table �Ŀ�ܺ�ģ��
        table.setModel(new DefaultTableModel(new Object[][]{},new String[]{"","��ս���","ִ����","ִ����","��սʱ��","��ս���"}));
        table.setBounds(30, 25, 679, 317);

        table.getTableHeader().setReorderingAllowed(false);//�����б�ͷ���ɱ��û������϶�����


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 25, 679, 317);
        jf.getContentPane().add(scrollPane);

        try {
            recordsList = rl.Read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ɸѡ��¼�˻��µĶ�ս��¼

        modifyList.clear();//��ճ�ʼ��
        for(GameRecord gr:recordsList){
            if(gr.getAccountName().equals(username))
                modifyList.add(gr);
        }

        rt = new RecordTable(modifyList);//��ɸѡ�Ժ�����ݸ�ֵ��table��һ��������ȥ

        table.setModel(rt);//ͨ����ֵ���Ķ��󣬽�������ʾ��table

        JButton lookin = new JButton("�ۿ��ط�");
        lookin.setFont(new Font("����", Font.PLAIN, 22));
        lookin.setBounds(30, 386, 127, 45);
        jf.getContentPane().add(lookin);

        lookin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //����¼����������Ҫ������������������������Ҫ��Ȼÿ�εĶ����ǿյģ��޿�
                rl.DuDang();

                //��ʼ��ÿ����¼�ı�ѡ��״̬
                for (GameRecord w: rl.gameRecords){
                    System.out.println(w.getPlayer1());//����
                    w.setIfSelected(false);
                }

                selectCount=0;//��ʼ��ѡ������

                //����
                for(int i=0;i<table.getRowCount();i++){

                    System.out.println(table.getValueAt(i,1));
                    System.out.println(((table.getValueAt(i,1)).getClass()));
                }

                //ͨ��ѭ�����жϱ�ѡ�еļ�¼������
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
                System.out.println("��ѡ�Ծֵ�����"+selectCount);

                if(selectCount!=1){
                    JOptionPane.showMessageDialog(jf,"��ѡ��һ����ֹۿ��ط�");
                }
                else {
                    for (GameRecord w: rl.gameRecords){
                        if(w.isIfSelected()){
                            bf.chuan(w.getRecords(),w.getOutcome(),username);
                            bf.myJFrame();//�������̵Ľ���,���ҽ���Ӧ�Ĳ����������̽���BoFang
                            jf.dispose();
                            break;
                        }
                    }
                }
                selectCount=0;//��ʼ��ѡ������
            }
        });
        
        JButton backView = new JButton("���ز˵�");
        backView.setFont(new Font("����", Font.PLAIN, 22));
        backView.setBounds(582, 386, 127, 45);
        jf.getContentPane().add(backView);

        backView.addActionListener(e -> {
            MeunView mv = MeunView.getInstance();
            mv.view(username);
            jf.dispose();

        });

        JButton qipu = new JButton("\u89C2\u770B\u68CB\u8C31");
        qipu.setFont(new Font("����", Font.PLAIN, 22));
        qipu.setBounds(184, 386, 127, 45);
        jf.getContentPane().add(qipu);

        //�鿴����
        qipu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rl.DuDang();

                //��ʼ��ÿ����¼�ı�ѡ��״̬
                for (GameRecord w: rl.gameRecords){
                    System.out.println(w.getPlayer1());//����
                    w.setIfSelected(false);
                }

                selectCount=0;//��ʼ��ѡ������

                //����
                for(int i=0;i<table.getRowCount();i++){

                    System.out.println(table.getValueAt(i,1));
                    System.out.println(((table.getValueAt(i,1)).getClass()));
                }

                //ͨ��ѭ�����жϱ�ѡ�еļ�¼������
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
                System.out.println("��ѡ�Ծֵ�����"+selectCount);

                if(selectCount!=1){
                    JOptionPane.showMessageDialog(jf,"��ѡ��һ����ֹۿ��ط�");
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
                selectCount=0;//��ʼ��ѡ������
            }

        });

        con.setVisible(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
