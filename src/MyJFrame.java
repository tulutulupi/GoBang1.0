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

    int qx = 20, qy = 40, qw = 525, qh = 525; //棋盘位置、宽高
    int bw = 150, bh = 50, bx = 570, by = 150; //按钮宽高、位置
    int x = 0, y = 0; //保存棋子坐标
    //棋盘的规模
    int qS = 20;
    int[][] SaveGame = new int[qS][qS]; //保存每个棋子,并且考虑到边界上的棋子，防止数组越界
    int[][] qishu = new int[qS][qS];// 记录每一个棋子的坐标对应的数字
    int qc = 1;//记录白棋=2，黑棋=1
    int qn = 0;//判断棋子是否重复
    boolean canplay = false; //判断游戏是否开始和结束
    String go = "黑子先行"; //游戏信息
    int bq = 0, hq = 0;//记录黑白棋子的数量
    boolean reallyOver = false;


    private static int count = 0;

    private static String username;//将登录的账户名称拉进来
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

    //记录和回放的功能
    RecordList rl = RecordList.getInstance();
    ArrayList<Chess> stepRecord = new ArrayList<>();
    MeunView mv = MeunView.getInstance();

    //---------------------------------------------------------------------------------------------------------------------
    //窗体
    public void myJFrame() {

        this.setTitle("五子棋"); //标题
        this.setSize(760, 585); //窗口大小
        this.setResizable(false); //窗口是否可以改变大小=否
        this.setDefaultCloseOperation(MyJFrame.DISPOSE_ON_CLOSE); //窗口关闭方式为关闭窗口返回上一个界面

        int width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取屏幕宽度
        int height = Toolkit.getDefaultToolkit().getScreenSize().height; //获取屏幕高度
        System.out.println("宽度："+width);//测试
        System.out.println("高度："+height);//测试

        this.setLocation((width - 760) / 2, (height - 585) / 2); //设置窗口默认位置以屏幕居中

        this.addMouseListener(this);
        Initialize();
        this.setVisible(true);

        //窗口是否显示=是
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
    //覆写paint方法,绘制界面
    @Override
    public void paint(Graphics g) {

        //双缓冲技术防止屏幕闪烁
        BufferedImage bi = new BufferedImage(850, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bi.createGraphics();

        //获取图片路径
        BufferedImage image = null;
        try {
            //获取项目文件夹路径
            File directory = new File("");
            //路径拼接
            image = ImageIO.read(new File(directory.getAbsolutePath() + "/mu/wzqbj.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(image, 5, 10, this); //显示图片

        g2.setColor(Color.BLACK);//设置画笔颜色
        g2.setFont(new Font("华文楷体", 10, 50)); //设置字体
        g2.drawString("五子棋", 575, 100); //绘制字符

        //棋盘
        g2.setColor(Color.orange); //设置画笔颜色
        g2.fillRect(qx, qy, qw, qh); //绘制棋盘背景矩形

        //开始按钮
        g2.setColor(Color.WHITE); //设置画笔颜色
        g2.fillRect(bx, by, bw, bh); //绘制开始按钮
        g2.setFont(new Font("华文楷体", 10, 30)); //设置字体
        g2.setColor(Color.black); //设置画笔颜色
        g2.drawString("开始", 615, 185); //绘制字符

        //悔棋按钮
        g2.setColor(Color.LIGHT_GRAY); //设置画笔颜色
        g2.fillRect(bx, by + 60, bw, bh); //绘制悔棋按钮
        g2.setFont(new Font("华文楷体", 10, 30)); //设置字体
        g2.setColor(Color.WHITE); //设置画笔颜色
        g2.drawString("保存", 615, 245); //绘制字符

        //返回按钮
        g2.setColor(Color.GRAY); //设置画笔颜色
        g2.fillRect(bx, by + 120, bw, bh); //绘制认输按钮
        g2.setFont(new Font("华文楷体", 10, 30)); //设置字体
        g2.setColor(Color.WHITE); //设置画笔颜色
        g2.drawString("比赛结束", 590, 305); //绘制字符

        //游戏信息栏
        g2.setColor(Color.getHSBColor(30, (float) 0.10, (float) 0.90)); //设置画笔颜色
        g2.fillRect(570, 350, 150, 200); //绘制游戏状态区域
        g2.setColor(Color.black); //设置画笔颜色
        g2.setFont(new Font("华文楷体", 10, 25)); //设置字体
        g2.drawString("游戏信息", 600, 380); //绘制字符
        g2.drawString(go, 600, 410); //绘制字符
        
        g2.setFont(new Font("华文楷体",10,20));
        g2.drawString("比赛场次:\n"+contantNum, 570, 430);
        g2.drawString("比赛名称:\n"+contantName, 570, 450);
        g2.drawString("比赛地点:\n"+contantLocal, 570, 470);
        g2.drawString("比赛裁判:\n"+referee, 570, 490);
        g2.drawString("执黑者"+blackOne, 570, 510); 
        g2.drawString("执白者"+whiteOne, 570, 530); 


        g2.setColor(Color.BLACK); //设置画笔颜色
        Graphics2D g1 = (Graphics2D)g2;
        g1.setStroke(new BasicStroke(2.0f));
        //绘制棋盘格线
        for (int x = 0; x <= qw; x += 35) {

            g1.drawLine(qx, x + qy, qw + qx, x + qy); //绘制一条横线
            g1.drawLine(x + qx, qy, x + qx, qh + qy); //绘制一条竖线
        }

        //绘制标注点
        for (int i = 3; i <= 11; i += 4) {
            for (int y = 3; y <= 11; y += 4) {
                g2.fillOval(35 * i + qx - 5, 35 * y + qy - 5, 9, 9); //绘制实心圆
            }
        }


        //绘制棋子

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {

                g2.setFont(new Font("TimesRoman",Font.PLAIN,15));//改变数字大小
                if (SaveGame[i][j] == 1) //黑子
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;

                    g2.setColor(Color.BLACK);
                    g2.fillOval(sx - 16, sy - 16, 32, 32); //绘制实心圆
                    hq++;
                    //将数字显示在棋子上
                    g2.setColor(Color.white);
                    g2.drawString(Integer.toString(qishu[i][j]),sx-5,sy+5);
                }
                if (SaveGame[i][j] == 2) //白子
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;

                    g2.setColor(Color.WHITE);
                    g2.fillOval(sx - 16, sy - 16, 32, 32); //绘制实心圆
                    g2.setColor(Color.BLACK);
                    g2.drawOval(sx - 16, sy - 16, 32, 32); //绘制空心圆
                    bq++;

                    //将数字显示在棋子上
                    g2.setColor(Color.black);
                    g2.drawString(Integer.toString(qishu[i][j]),sx-5,sy+5);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);


//        g.drawRect(20, 20, 20, 20);//绘制空心矩形
    }


    //---------------------------------------------------------------------------------------------------------------------
    //判断输赢
    private boolean WinLose() {
        boolean flag = false; //输赢
        int count = 1; //相连数
        int color = SaveGame[x][y]; //记录棋子颜色

        //判断横向棋子是否相连
        int i = 1; //迭代数
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

        i = 1; //迭代数
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


        //判断纵向棋子是否相连
        count = 1;
        i = 1; //迭代数
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

        i = 1; //迭代数
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


        //判断斜向棋子是否相连（左上右下）
        count = 1;
        i = 1; //迭代数
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

        i = 1; //迭代数
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


        //判断斜向棋子是否相连（左下右上）
        count = 1;
        i = 1; //迭代数

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

        i = 1; //迭代数
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
    //初始化游戏
    public void Initialize() {
        //遍历并初始化数组
        for (int i = 0; i < qS; i++) {
            for (int j = 0; j < qS; j++) {
                SaveGame[i][j] = 0;
                qishu[i][j] = 0;
            }
        }

        count = 0;
        qc = 1;//黑子先行
        go = "黑子先行";
    }
    public void NotPlay(){
        canplay = false;
    }


    //---------------------------------------------------------------------------------------------------------------------
    @Override //鼠标按下
    public void mousePressed(MouseEvent e) {
       int xp=0,yp=0;
        //获取鼠标点击位置
        xp = e.getX();
        yp = e.getY();

        //判断是否点击棋盘
        if (xp > qx && xp < qx + qw && yp > qy && yp < qy + qh) {
            //判断是否开始对战
            if (canplay) {

                //计算点击位置最近的点
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

                //判断当前位置有没有棋子，没有的话就落子，
                if (SaveGame[x][y] == 0) {
                    SaveGame[x][y] = qc;
                    count++;
                    qishu[x][y] = count;
                    qn = 0;
                } else {
                    qn = 1;
                }
                //存储步数
                System.out.println("棋子坐标：["+x+","+y+"]");
                Chess oneStep = new Chess(x,y,new Date(),qc,count);//将每一步记录下来
                stepRecord.add(oneStep);//将这一步加进总棋局步法里
                //切换棋子
                if (qn == 0) {
                    if (qc == 1) {
                        qc = 2;
                        go = "轮到白子";
                    } else {
                        qc = 1;
                        go = "轮到黑子";
                    }
                }

                this.repaint(); //重新执行一次paint方法

//                弹出胜利对话框

                boolean wl = this.WinLose();
                if (wl) {
                    reallyOver = true;
                    rl.DuDang();
                    if(SaveGame[x][y] == 1) {
                        JOptionPane.showMessageDialog(this, "游戏结束，" + "黑方获胜" ); //弹出提示对话框
                        rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),1);

                        go = "黑方获胜";
                    }
                    if(SaveGame[x][y] == 2){
                        JOptionPane.showMessageDialog(this, "游戏结束，" + "白方获胜" ); //弹出提示对话框
                        rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),2);
                        System.out.println("shengfupandaing2");
                        go = "白方获胜";
                    }
                    canplay = false;
                    rl.Save();
                }

                //弹出平局对话框
                if (bq + hq == 256) {
                    JOptionPane.showMessageDialog(this, "游戏结束，平局！"); //弹出提示对话框
                    go = "平局";
                    canplay = false;
                    rl.DuDang();
                    rl.addRecord(username,blackOne,whiteOne,stepRecord, new Date(),3);
                    rl.Save();
                }
                this.repaint();//重新执行一次paint方法


//
            } else {
                JOptionPane.showMessageDialog(mjf,"请点击开始按钮，开始对战","",1);
            }
        }



        //实现开始按钮
        //判断是否点击开始按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {

           count = 0;//新的对局让计数归零

            //判断游戏是否开始
            if (!canplay) {
                //如果游戏结束，则开始游戏
                canplay = true;
                JOptionPane.showMessageDialog(this, "游戏开始");
            } else {
                JOptionPane.showMessageDialog(this, "重新开始");
            }
            Initialize();
            this.repaint(); //重新执行一次paint方法
        }


        //实现保存按钮
        //判断是否点击悔棋按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 60 && e.getY() < by + 60 + bh) {
            //判断游戏是否开始
//            if (canplay) {
//                //遍历棋盘上是否有棋子
//                int z = 0;
//                for (int i = 0; i < 16; i++) {
//                    for (int j = 0; j < 16; j++) {
//                        if (SaveGame[i][j] != 0) {
//                            z++;
//                        }
//                    }
//                }
//                //判断是否有棋子
//                if (z != 0) {
//                    int result = JOptionPane.showConfirmDialog(this, "确认要悔棋吗？");
//                    if (result == 0) {
//                        int x = ui.ps[ui.i - 1].listx;
//                        int y = ui.ps[ui.i - 1].listy;
//
//                        if (SaveGame[x][y] == 0){
//                            JOptionPane.showMessageDialog(this, "已悔过一次棋了！");
//                        }else{
//                            count--;
//                            if (SaveGame[x][y] == 1) {
//                                qc = 1;
//                                go = "轮到黑子";
//                            } else if (SaveGame[x][y] == 2){
//                                qc = 2;
//                                go = "轮到白子";
//                            }
//                            SaveGame[x][y] = 0;
//                            qishu[x][y] = 0;
//                            ui.i--;
//                            this.repaint();
//                        }
//
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "棋盘上已无棋子");
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(this, "请先开始游戏");
//            }
            if(canplay) {
                if(!this.WinLose()) {
                    JOptionPane.showMessageDialog(this, "对局已经保存");
                    canplay = false;
                    Initialize();
                    this.paint(getGraphics());
                }
            }else{
                JOptionPane.showMessageDialog(this, "对局还未开始,或者对局已经结束","只保存残局",1);
            }
        }


//        //实现返回上一级菜单
//        //判断是否点击返回上一级菜单按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 120 && e.getY() < by + 120 + bh) {



                if(reallyOver) {
                    int out = JOptionPane.showConfirmDialog(this,"比赛时长为1.2min,是否要返回功能选择界面？","返回提示",JOptionPane.YES_NO_OPTION);
                    if(out == JOptionPane.YES_OPTION) {
                        mv.view(username);
                        mjf.dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"比赛还未分出胜负","温馨提示",1);
                }






            //判断游戏是否开始
//            if (canplay) {
//                //判断是谁认输
//                if (qc == 1) {
//                    JOptionPane.showMessageDialog(this, "黑方认输，白方获胜");
//                    canplay = false;
//                } else if (qc == 2) {
//                    JOptionPane.showMessageDialog(this, "白方认输，黑方获胜");
//                    canplay = false;
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "请先开始游戏");
//            }
        }


    }

    @Override //鼠标点击
    public void mouseClicked(MouseEvent e) {

    }

    @Override//鼠标抬起
    public void mouseReleased(MouseEvent e) {

    }

    @Override//鼠标进入
    public void mouseEntered(MouseEvent e) {

    }

    @Override//鼠标离开
    public void mouseExited(MouseEvent e) {

    }
}

