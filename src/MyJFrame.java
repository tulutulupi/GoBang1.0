import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyJFrame extends JFrame implements MouseListener {
    private  MyJFrame(){}
    private static MyJFrame mjf = new MyJFrame();
    public static MyJFrame getInstance(){
        return mjf;
    }

    int qx = 20, qy = 40, qw = 525, qh = 525; //����λ�á����
    int bw = 150, bh = 50, bx = 570, by = 150; //��ť��ߡ�λ��
    int x = 0, y = 0; //������������
    //���̵Ĺ�ģ
    int qS = 20;
    int[][] SaveGame = new int[qS][qS]; //����ÿ������,���ҿ��ǵ��߽��ϵ����ӣ���ֹ����Խ��
    int[][] qishu = new int[qS][qS];// ��¼ÿһ�����ӵ������Ӧ������
    int qc = 1;//��¼����=2������=1
    int qn = 0;//�ж������Ƿ��ظ�
    boolean canplay = false; //�ж���Ϸ�Ƿ�ʼ�ͽ���
    String go = "��������"; //��Ϸ��Ϣ
    int bq = 0, hq = 0;//��¼�ڰ����ӵ�����
    boolean reallyOver = false;


    private static int count = 0;

    private static String username;//����¼���˻�����������
    private static String whiteOne;
    private static String blackOne;
    private static String contantNum;
    private static String referee;
    private static String contantName;
    private static String contantLocal;
    public void setUN(String username,String whiteOne,String blackOne,String contantNum,String referee,String contantName,String contantLocal){
        MyJFrame.username = username;
        MyJFrame.whiteOne = whiteOne;
        MyJFrame.blackOne = blackOne;
        MyJFrame.contantNum = contantNum;
        MyJFrame.referee = referee;
        MyJFrame.contantName = contantName;
        MyJFrame.contantLocal = contantLocal;
    }

    //��¼�ͻطŵĹ���
    RecordList rl = RecordList.getInstance();
    ArrayList<Chess> stepRecord = new ArrayList<>();
    MeunView mv = MeunView.getInstance();

    //---------------------------------------------------------------------------------------------------------------------
    //����
    public void myJFrame() {

        this.setTitle("������"); //����
        this.setSize(760, 585); //���ڴ�С
        this.setResizable(false); //�����Ƿ���Ըı��С=��
        this.setDefaultCloseOperation(MyJFrame.DISPOSE_ON_CLOSE); //���ڹرշ�ʽΪ�رմ��ڷ�����һ������

        int width = Toolkit.getDefaultToolkit().getScreenSize().width; //��ȡ��Ļ���
        int height = Toolkit.getDefaultToolkit().getScreenSize().height; //��ȡ��Ļ�߶�
        System.out.println("��ȣ�"+width);//����
        System.out.println("�߶ȣ�"+height);//����

        this.setLocation((width - 760) / 2, (height - 585) / 2); //���ô���Ĭ��λ������Ļ����

        this.addMouseListener(this);
        Initialize();
        this.setVisible(true);

        //�����Ƿ���ʾ=��
        this.paint(getGraphics());
        this.setVisible(true);



    }

    public class Position {
        int listx;
        int listy;
    }

    public class chessUI extends JPanel {
        public Position[] ps = new Position[300];
        int i;
    }

    chessUI ui = new chessUI();
    Position p = new Position();


    //---------------------------------------------------------------------------------------------------------------------
    //��дpaint����,���ƽ���
    @Override
    public void paint(Graphics g) {

        //˫���弼����ֹ��Ļ��˸
        BufferedImage bi = new BufferedImage(850, 600, BufferedImage.TYPE_INT_ARGB);
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
        g2.drawImage(image, 5, 10, this); //��ʾͼƬ

        g2.setColor(Color.BLACK);//���û�����ɫ
        g2.setFont(new Font("���Ŀ���", 10, 50)); //��������
        g2.drawString("������", 575, 100); //�����ַ�

        //����
        g2.setColor(Color.orange); //���û�����ɫ
        g2.fillRect(qx, qy, qw, qh); //�������̱�������

        //��ʼ��ť
        g2.setColor(Color.WHITE); //���û�����ɫ
        g2.fillRect(bx, by, bw, bh); //���ƿ�ʼ��ť
        g2.setFont(new Font("���Ŀ���", 10, 30)); //��������
        g2.setColor(Color.black); //���û�����ɫ
        g2.drawString("��ʼ", 615, 185); //�����ַ�

        //���尴ť
        g2.setColor(Color.LIGHT_GRAY); //���û�����ɫ
        g2.fillRect(bx, by + 60, bw, bh); //���ƻ��尴ť
        g2.setFont(new Font("���Ŀ���", 10, 30)); //��������
        g2.setColor(Color.WHITE); //���û�����ɫ
        g2.drawString("����", 615, 245); //�����ַ�

        //���ذ�ť
        g2.setColor(Color.GRAY); //���û�����ɫ
        g2.fillRect(bx, by + 120, bw, bh); //�������䰴ť
        g2.setFont(new Font("���Ŀ���", 10, 30)); //��������
        g2.setColor(Color.WHITE); //���û�����ɫ
        g2.drawString("��������", 590, 305); //�����ַ�

        //��Ϸ��Ϣ��
        g2.setColor(Color.getHSBColor(30, (float) 0.10, (float) 0.90)); //���û�����ɫ
        g2.fillRect(570, 350, 150, 200); //������Ϸ״̬����
        g2.setColor(Color.black); //���û�����ɫ
        g2.setFont(new Font("���Ŀ���", 10, 25)); //��������
        g2.drawString("��Ϸ��Ϣ", 600, 380); //�����ַ�
        g2.drawString(go, 600, 410); //�����ַ�
        
        g2.setFont(new Font("���Ŀ���",10,20));
        g2.drawString("��������:\n"+contantNum, 570, 430);
        g2.drawString("��������:\n"+contantName, 570, 450);
        g2.drawString("�����ص�:\n"+contantLocal, 570, 470);
        g2.drawString("��������:\n"+referee, 570, 490);
        g2.drawString("ִ����"+blackOne, 570, 510); 
        g2.drawString("ִ����"+whiteOne, 570, 530); 


        g2.setColor(Color.BLACK); //���û�����ɫ
        Graphics2D g1 = (Graphics2D)g2;
        g1.setStroke(new BasicStroke(2.0f));
        //�������̸���
        for (int x = 0; x <= qw; x += 35) {

            g1.drawLine(qx, x + qy, qw + qx, x + qy); //����һ������
            g1.drawLine(x + qx, qy, x + qx, qh + qy); //����һ������
        }

        //���Ʊ�ע��
        for (int i = 3; i <= 11; i += 4) {
            for (int y = 3; y <= 11; y += 4) {
                g2.fillOval(35 * i + qx - 5, 35 * y + qy - 5, 9, 9); //����ʵ��Բ
            }
        }


        //��������

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {

                g2.setFont(new Font("TimesRoman",Font.PLAIN,15));//�ı����ִ�С
                if (SaveGame[i][j] == 1) //����
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;

                    g2.setColor(Color.BLACK);
                    g2.fillOval(sx - 16, sy - 16, 32, 32); //����ʵ��Բ
                    hq++;
                    //��������ʾ��������
                    g2.setColor(Color.white);
                    g2.drawString(Integer.toString(qishu[i][j]),sx-5,sy+5);
                }
                if (SaveGame[i][j] == 2) //����
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;

                    g2.setColor(Color.WHITE);
                    g2.fillOval(sx - 16, sy - 16, 32, 32); //����ʵ��Բ
                    g2.setColor(Color.BLACK);
                    g2.drawOval(sx - 16, sy - 16, 32, 32); //���ƿ���Բ
                    bq++;

                    //��������ʾ��������
                    g2.setColor(Color.black);
                    g2.drawString(Integer.toString(qishu[i][j]),sx-5,sy+5);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);


//        g.drawRect(20, 20, 20, 20);//���ƿ��ľ���
    }


    //---------------------------------------------------------------------------------------------------------------------
    //�ж���Ӯ
    private boolean WinLose() {
        boolean flag = false; //��Ӯ
        int count = 1; //������
        int color = SaveGame[x][y]; //��¼������ɫ

        //�жϺ��������Ƿ�����
        int i = 1; //������
        while (true) {
            if((x + i)>=0&&(x + i)<=16) {
                if(color == SaveGame[x + i][y]){
                count++;
                i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        i = 1; //������
        while (true) {
            if((x - i)>=0&&(x - i)<=16) {
                if(color == SaveGame[x - i][y]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (count >= 5) {
            flag = true;
        }


        //�ж����������Ƿ�����
        count = 1;
        i = 1; //������
        while (true) {
            if((y + i)>=0&&(y + i)<=16) {
                if(color == SaveGame[x][y + i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        i = 1; //������
        while (true) {
            if((y - i)>=0&&(y - i)<=16) {
                if(color == SaveGame[x][y - i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (count >= 5) {
            flag = true;
        }


        //�ж�б�������Ƿ��������������£�
        count = 1;
        i = 1; //������
        while (true) {
            if((y - i)>=0&&(y - i)<=16&&(x - i)>=0&&(x - i)<=16) {
                if(color == SaveGame[x - i][y - i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        i = 1; //������
        while (true) {
            if((y + i)>=0&&(y + i)<=16&&(x + i)>=0&&(x + i)<=16) {
                if(color == SaveGame[x + i][y + i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (count >= 5) {
            flag = true;
        }


        //�ж�б�������Ƿ��������������ϣ�
        count = 1;
        i = 1; //������

        while (true) {
            if((y - i)>=0&&(y - i)<=16&&(x + i)>=0&&(x + i)<=16) {
                if(color == SaveGame[x + i][y - i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        i = 1; //������
        while (true) {
            if((y + i)>=0&&(y + i)<=16&&(x - i)>=0&&(x - i)<=16) {
                if(color == SaveGame[x - i][y + i]){
                    count++;
                    i++;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if (count >= 5) {
            flag = true;
        }

        return flag;
    }


    //---------------------------------------------------------------------------------------------------------------------
    //��ʼ����Ϸ
    public void Initialize() {
        //��������ʼ������
        for (int i = 0; i < qS; i++) {
            for (int j = 0; j < qS; j++) {
                SaveGame[i][j] = 0;
                qishu[i][j] = 0;
            }
        }

        count = 0;
        qc = 1;//��������
        go = "��������";
    }
    public void NotPlay(){
        canplay = false;
    }


    //---------------------------------------------------------------------------------------------------------------------
    @Override //��갴��
    public void mousePressed(MouseEvent e) {
       int xp=0,yp=0;
        //��ȡ�����λ��
        xp = e.getX();
        yp = e.getY();

        //�ж��Ƿ�������
        if (xp > qx && xp < qx + qw && yp > qy && yp < qy + qh) {
            //�ж��Ƿ�ʼ��ս
            if (canplay) {

                //������λ������ĵ�
                if ((xp - qx) % 35 > 17) {
                    x = (xp - qx) / 35 + 1;
                } else {
                    x = (xp - qx) / 35;
                }
                if ((yp - qy) % 35 > 17) {
                    y = (yp - qy) / 35 + 1;
                } else {
                    y = (yp - qy) / 35;
                }

                //�жϵ�ǰλ����û�����ӣ�û�еĻ������ӣ�
                if (SaveGame[x][y] == 0) {
                    SaveGame[x][y] = qc;
                    count++;
                    qishu[x][y] = count;
                    qn = 0;
                } else {
                    qn = 1;
                }
                //�洢����
                System.out.println("�������꣺["+x+","+y+"]");
                Chess oneStep = new Chess(x,y,new Date(),qc,count);//��ÿһ����¼����
                stepRecord.add(oneStep);//����һ���ӽ�����ֲ�����
                //�л�����
                if (qn == 0) {
                    if (qc == 1) {
                        qc = 2;
                        go = "�ֵ�����";
                    } else {
                        qc = 1;
                        go = "�ֵ�����";
                    }
                }

                this.repaint(); //����ִ��һ��paint����

//                ����ʤ���Ի���

                boolean wl = this.WinLose();
                if (wl) {
                    reallyOver = true;
                    rl.DuDang();
                    if(SaveGame[x][y] == 1) {
                        JOptionPane.showMessageDialog(this, "��Ϸ������" + "�ڷ���ʤ" ); //������ʾ�Ի���
                        rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),1);

                        go = "�ڷ���ʤ";
                    }
                    if(SaveGame[x][y] == 2){
                        JOptionPane.showMessageDialog(this, "��Ϸ������" + "�׷���ʤ" ); //������ʾ�Ի���
                        rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),2);
                        System.out.println("shengfupandaing2");
                        go = "�׷���ʤ";
                    }
                    canplay = false;
                    rl.Save();
                }

                //����ƽ�ֶԻ���
                if (bq + hq == 256) {
                    JOptionPane.showMessageDialog(this, "��Ϸ������ƽ�֣�"); //������ʾ�Ի���
                    go = "ƽ��";
                    canplay = false;
                    rl.DuDang();
                    rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),3);
                    rl.Save();
                }
                this.repaint();//����ִ��һ��paint����


//
            } else {
                JOptionPane.showMessageDialog(mjf,"������ʼ��ť����ʼ��ս","",1);
            }
        }



        //ʵ�ֿ�ʼ��ť
        //�ж��Ƿ�����ʼ��ť
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {

           count = 0;//�µĶԾ��ü�������

            //�ж���Ϸ�Ƿ�ʼ
            if (!canplay) {
                //�����Ϸ��������ʼ��Ϸ
                canplay = true;
                JOptionPane.showMessageDialog(this, "��Ϸ��ʼ");
            } else {
                JOptionPane.showMessageDialog(this, "���¿�ʼ");
            }
            Initialize();
            this.repaint(); //����ִ��һ��paint����
        }


        //ʵ�ֱ��水ť
        //�ж��Ƿ������尴ť
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 60 && e.getY() < by + 60 + bh) {
            //�ж���Ϸ�Ƿ�ʼ
//            if (canplay) {
//                //�����������Ƿ�������
//                int z = 0;
//                for (int i = 0; i < 16; i++) {
//                    for (int j = 0; j < 16; j++) {
//                        if (SaveGame[i][j] != 0) {
//                            z++;
//                        }
//                    }
//                }
//                //�ж��Ƿ�������
//                if (z != 0) {
//                    int result = JOptionPane.showConfirmDialog(this, "ȷ��Ҫ������");
//                    if (result == 0) {
//                        int x = ui.ps[ui.i - 1].listx;
//                        int y = ui.ps[ui.i - 1].listy;
//
//                        if (SaveGame[x][y] == 0){
//                            JOptionPane.showMessageDialog(this, "�ѻڹ�һ�����ˣ�");
//                        }else{
//                            count--;
//                            if (SaveGame[x][y] == 1) {
//                                qc = 1;
//                                go = "�ֵ�����";
//                            } else if (SaveGame[x][y] == 2){
//                                qc = 2;
//                                go = "�ֵ�����";
//                            }
//                            SaveGame[x][y] = 0;
//                            qishu[x][y] = 0;
//                            ui.i--;
//                            this.repaint();
//                        }
//
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "��������������");
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(this, "���ȿ�ʼ��Ϸ");
//            }
            if(canplay) {
                if(!this.WinLose()) {
                    JOptionPane.showMessageDialog(this, "�Ծ��Ѿ�����");
                    canplay = false;
                    Initialize();
                    this.paint(getGraphics());
                }
            }else{
                JOptionPane.showMessageDialog(this, "�Ծֻ�δ��ʼ,���߶Ծ��Ѿ�����","ֻ����о�",1);
            }
        }


//        //ʵ�ַ�����һ���˵�
//        //�ж��Ƿ���������һ���˵���ť
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 120 && e.getY() < by + 120 + bh) {



                if(reallyOver) {
                    int out = JOptionPane.showConfirmDialog(this,"����ʱ��Ϊ1.2min,�Ƿ�Ҫ���ع���ѡ����棿","������ʾ",JOptionPane.YES_NO_OPTION);
                    if(out == JOptionPane.YES_OPTION) {
                        mv.view(username);
                        mjf.dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"������δ�ֳ�ʤ��","��ܰ��ʾ",1);
                }






            //�ж���Ϸ�Ƿ�ʼ
//            if (canplay) {
//                //�ж���˭����
//                if (qc == 1) {
//                    JOptionPane.showMessageDialog(this, "�ڷ����䣬�׷���ʤ");
//                    canplay = false;
//                } else if (qc == 2) {
//                    JOptionPane.showMessageDialog(this, "�׷����䣬�ڷ���ʤ");
//                    canplay = false;
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "���ȿ�ʼ��Ϸ");
//            }
        }


    }

    @Override //�����
    public void mouseClicked(MouseEvent e) {

    }

    @Override//���̧��
    public void mouseReleased(MouseEvent e) {

    }

    @Override//������
    public void mouseEntered(MouseEvent e) {

    }

    @Override//����뿪
    public void mouseExited(MouseEvent e) {

    }
}

