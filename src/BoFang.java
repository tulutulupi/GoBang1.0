import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ������
 */
public class BoFang extends JFrame implements MouseListener {
    //����ģʽ
    private static BoFang bf = new BoFang();
    private BoFang(){}
    public static BoFang getInstance(){
//        if(bf==null){
//            bf = new BoFang();
//        }
         return bf;
    }

    int qx = 20, qy = 40, qw = 525, qh = 525; //����λ�á����
    int bw = 150, bh = 50, bx = 570, by = 150; //��ť��ߡ�λ��
    //���̹��
    int da = 16;

    int[][] SaveGame = new int[da][da]; //����ÿ������.
    int [][] shuZ  = new int[da][da];
    int qc = 1;//��¼����=2������=1


//    boolean canplay = true; //�жϻط��Ƿ�ʼ�ͽ���
    String go = "��������"; //��Ϸ��Ϣ
    int bq = 0, hq = 0;//��¼�ڰ����ӵ�����
    int ge = 35;//������֮��ľ���


    private static ArrayList<Chess> stepRd = new ArrayList<>();
    private static int WinLose=0;
    private static String name;

    private static int sleepTimeSize ;
    private static long st[];
    private static int ji ;
    private static int zu ;

    HTimer2Plus hTimer2Plus = new HTimer2Plus(); // ����һ��ʱ�ӣ����ʱ��Ҫ�����۲���
    PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // �����۲���

    public void chuan(ArrayList<Chess> stepRd,int wl,String name){
        BoFang.stepRd = stepRd;//��ѡ�еļ�¼��ֵ��������������Ӧ����
        WinLose = wl;//������Ӯ���
        BoFang.name = name;
    }

    public void myJFrame() {
        int widths = 760;int heights = 585;
        this.setTitle("������"); //����
        this.setSize(widths, heights); //���ڴ�С
        this.setResizable(false); //�����Ƿ���Ըı��С=��
        this.setDefaultCloseOperation(MyJFrame.DISPOSE_ON_CLOSE); //���ڹرշ�ʽΪ�رմ��ڷ�����һ������

        int width = Toolkit.getDefaultToolkit().getScreenSize().width; //��ȡ��Ļ���
        int height = Toolkit.getDefaultToolkit().getScreenSize().height; //��ȡ��Ļ�߶�
        System.out.println("��ȣ�"+width);//����
        System.out.println("�߶ȣ�"+height);//����

        this.setLocation((width - widths) / 2, (height - heights) / 2); //���ô���Ĭ��λ������Ļ����

        this.addMouseListener(this);

        this.setVisible(true); //�����Ƿ���ʾ=��

        Initialize();//���ȳ�ʼ��

        sleepTimeSize = stepRd.size()-1;
        st = new long[sleepTimeSize];
        for(int i=0;i<sleepTimeSize;i++){
            st[i] = stepRd.get(i+1).getTime().getTime() -  stepRd.get(i).getTime().getTime();
        }

    }


    //��дpaint����,���ƽ���
    @Override
    public void paint(Graphics g) {

        int w = 850;int h = 600;
        //˫���弼����ֹ��Ļ��˸
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bi.createGraphics();

        //��ȡͼƬ·��
        BufferedImage image = null;
        try {
            //��ȡ��Ŀ�ļ���·��
            File directory = new File("");
            //·��ƴ��
            image = ImageIO.read(new File(directory.getAbsolutePath() + "/mu/wzqbj.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int xs = 5;int ys = 0;
        //��ʾͼƬ
        g2.drawImage(image, xs, ys, this);

        g2.setColor(Color.BLACK);//���û�����ɫ
        int size = 45;
        g2.setFont(new Font("���Ŀ���", Font.PLAIN, size)); //��������
        xs = 560;ys = 100;
        g2.drawString("��ս�ط�", xs, ys); //�����ַ�

        //����
        g2.setColor(Color.orange); //���û�����ɫ
        g2.fillRect(qx, qy, qw, qh); //�������̱�������

        //��ʼ��ť
        g2.setColor(Color.WHITE); //���û�����ɫ
        g2.fillRect(bx, by, bw, bh); //���ƿ�ʼ��ť
        size = 25;
        g2.setFont(new Font("���Ŀ���", Font.PLAIN, size)); //��������
        g2.setColor(Color.black); //���û�����ɫ
        xs = 600;ys = 185;
        g2.drawString("��ʼ�ط�", xs, ys); //�����ַ�

        //�����ͣ��ť
        g2.setColor(Color.LIGHT_GRAY); //���û�����ɫ
        g2.fillRect(bx, by + 60, 70, bh); //���ƿ����ť
        g2.fillRect(bx+80,by+60,70,bh); //������ͣ

        g2.setFont(new Font("���Ŀ���", 10, 20)); //��������
        g2.setColor(Color.BLUE); //���û�����ɫ
        g2.drawString("���", 585, 240); //�����ַ�
        g2.drawString("��ͣ",665,240);
        //���ذ�ť
        g2.setColor(Color.GRAY); //���û�����ɫ
        int gape = 120;
        g2.fillRect(bx, by + gape, bw, bh); //�������䰴ť
        size = 30;
        g2.setFont(new Font("���Ŀ���", Font.PLAIN, size)); //��������
        g2.setColor(Color.WHITE); //���û�����ɫ
        xs = 610;ys = 305;
        g2.drawString("����", xs, ys); //�����ַ�

        //��Ϸ��Ϣ��
        int hi = 30;
        float h1 = (float) 0.10;
        float h2 = (float) 0.90;
        g2.setColor(Color.getHSBColor(hi, (float) h1, (float) h2)); //���û�����ɫ
       int x = 570,y = 350;
       int we1 =150,he1 = 100;
        g2.fillRect(x, y, we1, he1); //������Ϸ״̬����
        g2.setColor(Color.black); //���û�����ɫ
         size = 25;
        g2.setFont(new Font("���Ŀ���", Font.PLAIN, size)); //��������
        x = 600;y = 380;
        g2.drawString("��Ϸ��Ϣ", x, y); //�����ַ�
        x = 600;y = 410;
        g2.drawString(go, x, y); //�����ַ�


        g2.setColor(Color.BLACK); //���û�����ɫ
        Graphics2D g1 = (Graphics2D)g2;
        g1.setStroke(new BasicStroke(2.0f));
        //�������̸���

        for (int i = 0; i <= qw; i += ge) {

            g1.drawLine(qx, i + qy, qw + qx, i + qy); //����һ������
            g1.drawLine(i + qx, qy, i + qx, qh + qy); //����һ������
        }

        //���Ʊ�ע��
        int a = 3,b = 11,c = 4,d= 5,e = 9;
        for (int i = a; i <= b; i += c) {
            for (int y1 = a; y1 <= b; y1 += c) {
                //����ʵ��Բ
                g2.fillOval(ge * i + qx - d, ge * y1 + qy - d, e, e);
            }
        }


        //��������
        for (int i = 0; i < da; i++) {
            for (int j = 0; j < da; j++) {
                size = 15;
                g2.setFont(new Font("TimesRoman",Font.PLAIN,size));
                if (SaveGame[i][j] == 1) //����
                {
                    int sx = i * ge + qx;
                    int sy = j * ge + qy;
                    g2.setColor(Color.BLACK);
                    int yuan = 16;
                    g2.fillOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //����ʵ��Բ
                    hq++;
                    //��������
                    g2.setColor(Color.white);
                    int es = 5;
                    g2.drawString(Integer.toString(shuZ[i][j]),sx-es,sy+es);
                }
                if (SaveGame[i][j] == 2) //����
                {
                    int sx = i * ge + qx;
                    int sy = j * ge + qy;
                    g2.setColor(Color.WHITE);
                    int yuan = 16;
                    g2.fillOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //����ʵ��Բ
                    g2.setColor(Color.BLACK);
                    g2.drawOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //���ƿ���Բ
                    bq++;

                    //��������ʾ��������
                    g2.setColor(Color.black);

                    int es = 5;
                    g2.drawString(Integer.toString(shuZ[i][j]),sx-es,sy+es);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);


//        g.drawRect(20, 20, 20, 20);//���ƿ��ľ���
    }

    //---------------------------------------------------------------------------------------------------------------------
    //��ʼ����Ϸ
    public void Initialize() {
        //��������ʼ������
        for (int i = 0; i < da; i++) {
            for (int j = 0; j < da; j++) {
                SaveGame[i][j] = 0;
                shuZ[i][j] = 0;
                sleepTimeSize = 0;
            }
        }

        qc = 1;//��������
        go = "��������";
        ji = 0;//�ط�ִ�еĴ�����ʼ��
        zu = 0;
    }

    //--------------------------------------------------------------
    //ʹ���߳���������
    public void Fang(){
        System.out.println("kaishihuifang");

        //�������ʱ����ת����long�͵�����,ת�浽������
//        int sleepTimeSize ;
//        sleepTimeSize = stepRd.size()-1;
//        long []st = new long[sleepTimeSize];
//        for(int i=0;i<sleepTimeSize;i++){
//            st[i] = stepRd.get(i+1).getTime().getTime() -  stepRd.get(i).getTime().getTime();
//        }
//        int count=0;
//        for(Chess a:stepRd){
//            SaveGame[a.getXp()][a.getYp()]=a.getColor();//��һ��������ݴ���
//            shuZ[a.getXp()][a.getYp()] = a.getShu();//�����Ӷ�Ӧ���ִ���
//            //������Ϣ��
//            if(a.getColor()==1) {
//                go = "�ֵ�����";
//            } else {
//                go = "�ֵ�����";
//            }
//            //��һ�����ӻ�������
//            this.paint(getGraphics());
//            if(count<sleepTimeSize){
//            //�ó���˯��ͬ��ʱ����
//            try {
//                Thread.sleep(st[count]);//ͨ��˯���̵߳ķ�����ͬ������ʱ���ʱ������
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            count++;
//            }
//        }

        Chess a2 = stepRd.get(ji);
        SaveGame[a2.getXp()][a2.getYp()]=a2.getColor();//��һ��������ݴ���
        shuZ[a2.getXp()][a2.getYp()] = a2.getShu();//�����Ӷ�Ӧ���ִ���
        //������Ϣ��
        if(a2.getColor()==1) {
            go = "�ֵ�����";
        } else {
            go = "�ֵ�����";
        }

        if(WinLose == 1) {
            go = "���ӻ�ʤ";
        }

        if (WinLose == 2) {
            go = "���ӻ�ʤ";
        }

        if(WinLose == 3) {
            go = "ƽ��";
        }
        this.paint(getGraphics()); //��һ�����ӻ�������
        ji++;

//        JOptionPane.showMessageDialog(this,"�طŽ���","��ܰ��ʾ",1);
    }

    public boolean isLastOne() {
        if(ji==stepRd.size())
            return true;
        else return false;
    }

    public long getSleepTime(){

        if(zu <=sleepTimeSize){
            return st[zu];
        }
        zu++;

        return 0;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

//        HTimer2Plus hTimer2Plus = new HTimer2Plus(); // ����һ��ʱ�ӣ����ʱ��Ҫ�����۲���
//        PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // �����۲���

        //�ж��Ƿ������ز˵���ť
        int gape = 120;
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + gape && e.getY() < by + gape + bh) {
            RecallView rv = RecallView.getInstance();
            rv.Recall(name);
            bf.dispose();

        }

        //�ж��Ƿ�����ʼ��ť
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {

            Initialize();
            this.repaint();
            //ִ�лط�
            // Fang();
            playWatcher2Plus.watch();
//            Thread thread = new Thread(new PlayWatcher2Plus(hTimer2Plus));
//            thread.start();
            System.out.println("kaishi");
        }
        //��������ťbx, by + 60, 70, bh
        if(e.getX() > bx && e.getX() < bx+70 && e.getY()>by+60 && e.getY()<by+60+bh){
            playWatcher2Plus.kuaiJing();
            System.out.println("kuaijin");
        }
        //�����ͣ��ť
        if(e.getX() > bx+80 && e.getX() < bx+150 && e.getY()>by+60 && e.getY()<by+60+bh){

            if(!playWatcher2Plus.isInterrupted()){
            playWatcher2Plus.zanTing();
            }
            else {
                playWatcher2Plus.huiFu();

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
