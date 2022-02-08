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
 * @author 邢旭升
 */
public class BoFang extends JFrame implements MouseListener {
    //单例模式
    private static BoFang bf = new BoFang();
    private BoFang(){}
    public static BoFang getInstance(){
//        if(bf==null){
//            bf = new BoFang();
//        }
         return bf;
    }

    int qx = 20, qy = 40, qw = 525, qh = 525; //棋盘位置、宽高
    int bw = 150, bh = 50, bx = 570, by = 150; //按钮宽高、位置
    //棋盘规格
    int da = 16;

    int[][] SaveGame = new int[da][da]; //保存每个棋子.
    int [][] shuZ  = new int[da][da];
    int qc = 1;//记录白棋=2，黑棋=1


//    boolean canplay = true; //判断回放是否开始和结束
    String go = "黑子先行"; //游戏信息
    int bq = 0, hq = 0;//记录黑白棋子的数量
    int ge = 35;//两条线之间的距离


    private static ArrayList<Chess> stepRd = new ArrayList<>();
    private static int WinLose=0;
    private static String name;

    private static int sleepTimeSize ;
    private static long st[];
    private static int ji ;
    private static int zu ;

    HTimer2Plus hTimer2Plus = new HTimer2Plus(); // 创建一个时钟，这个时钟要传给观察者
    PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // 创建观察者

    public void chuan(ArrayList<Chess> stepRd,int wl,String name){
        BoFang.stepRd = stepRd;//将选中的记录赋值给这个类里面的相应变量
        WinLose = wl;//传送输赢情况
        BoFang.name = name;
    }

    public void myJFrame() {
        int widths = 760;int heights = 585;
        this.setTitle("五子棋"); //标题
        this.setSize(widths, heights); //窗口大小
        this.setResizable(false); //窗口是否可以改变大小=否
        this.setDefaultCloseOperation(MyJFrame.DISPOSE_ON_CLOSE); //窗口关闭方式为关闭窗口返回上一个界面

        int width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取屏幕宽度
        int height = Toolkit.getDefaultToolkit().getScreenSize().height; //获取屏幕高度
        System.out.println("宽度："+width);//测试
        System.out.println("高度："+height);//测试

        this.setLocation((width - widths) / 2, (height - heights) / 2); //设置窗口默认位置以屏幕居中

        this.addMouseListener(this);

        this.setVisible(true); //窗口是否显示=是

        Initialize();//首先初始化

        sleepTimeSize = stepRd.size()-1;
        st = new long[sleepTimeSize];
        for(int i=0;i<sleepTimeSize;i++){
            st[i] = stepRd.get(i+1).getTime().getTime() -  stepRd.get(i).getTime().getTime();
        }

    }


    //覆写paint方法,绘制界面
    @Override
    public void paint(Graphics g) {

        int w = 850;int h = 600;
        //双缓冲技术防止屏幕闪烁
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
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
        int xs = 5;int ys = 0;
        //显示图片
        g2.drawImage(image, xs, ys, this);

        g2.setColor(Color.BLACK);//设置画笔颜色
        int size = 45;
        g2.setFont(new Font("华文楷体", Font.PLAIN, size)); //设置字体
        xs = 560;ys = 100;
        g2.drawString("对战回放", xs, ys); //绘制字符

        //棋盘
        g2.setColor(Color.orange); //设置画笔颜色
        g2.fillRect(qx, qy, qw, qh); //绘制棋盘背景矩形

        //开始按钮
        g2.setColor(Color.WHITE); //设置画笔颜色
        g2.fillRect(bx, by, bw, bh); //绘制开始按钮
        size = 25;
        g2.setFont(new Font("华文楷体", Font.PLAIN, size)); //设置字体
        g2.setColor(Color.black); //设置画笔颜色
        xs = 600;ys = 185;
        g2.drawString("开始回放", xs, ys); //绘制字符

        //快进暂停按钮
        g2.setColor(Color.LIGHT_GRAY); //设置画笔颜色
        g2.fillRect(bx, by + 60, 70, bh); //绘制快进按钮
        g2.fillRect(bx+80,by+60,70,bh); //绘制暂停

        g2.setFont(new Font("华文楷体", 10, 20)); //设置字体
        g2.setColor(Color.BLUE); //设置画笔颜色
        g2.drawString("快进", 585, 240); //绘制字符
        g2.drawString("暂停",665,240);
        //返回按钮
        g2.setColor(Color.GRAY); //设置画笔颜色
        int gape = 120;
        g2.fillRect(bx, by + gape, bw, bh); //绘制认输按钮
        size = 30;
        g2.setFont(new Font("华文楷体", Font.PLAIN, size)); //设置字体
        g2.setColor(Color.WHITE); //设置画笔颜色
        xs = 610;ys = 305;
        g2.drawString("返回", xs, ys); //绘制字符

        //游戏信息栏
        int hi = 30;
        float h1 = (float) 0.10;
        float h2 = (float) 0.90;
        g2.setColor(Color.getHSBColor(hi, (float) h1, (float) h2)); //设置画笔颜色
       int x = 570,y = 350;
       int we1 =150,he1 = 100;
        g2.fillRect(x, y, we1, he1); //绘制游戏状态区域
        g2.setColor(Color.black); //设置画笔颜色
         size = 25;
        g2.setFont(new Font("华文楷体", Font.PLAIN, size)); //设置字体
        x = 600;y = 380;
        g2.drawString("游戏信息", x, y); //绘制字符
        x = 600;y = 410;
        g2.drawString(go, x, y); //绘制字符


        g2.setColor(Color.BLACK); //设置画笔颜色
        Graphics2D g1 = (Graphics2D)g2;
        g1.setStroke(new BasicStroke(2.0f));
        //绘制棋盘格线

        for (int i = 0; i <= qw; i += ge) {

            g1.drawLine(qx, i + qy, qw + qx, i + qy); //绘制一条横线
            g1.drawLine(i + qx, qy, i + qx, qh + qy); //绘制一条竖线
        }

        //绘制标注点
        int a = 3,b = 11,c = 4,d= 5,e = 9;
        for (int i = a; i <= b; i += c) {
            for (int y1 = a; y1 <= b; y1 += c) {
                //绘制实心圆
                g2.fillOval(ge * i + qx - d, ge * y1 + qy - d, e, e);
            }
        }


        //绘制棋子
        for (int i = 0; i < da; i++) {
            for (int j = 0; j < da; j++) {
                size = 15;
                g2.setFont(new Font("TimesRoman",Font.PLAIN,size));
                if (SaveGame[i][j] == 1) //黑子
                {
                    int sx = i * ge + qx;
                    int sy = j * ge + qy;
                    g2.setColor(Color.BLACK);
                    int yuan = 16;
                    g2.fillOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //绘制实心圆
                    hq++;
                    //画上数字
                    g2.setColor(Color.white);
                    int es = 5;
                    g2.drawString(Integer.toString(shuZ[i][j]),sx-es,sy+es);
                }
                if (SaveGame[i][j] == 2) //白子
                {
                    int sx = i * ge + qx;
                    int sy = j * ge + qy;
                    g2.setColor(Color.WHITE);
                    int yuan = 16;
                    g2.fillOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //绘制实心圆
                    g2.setColor(Color.BLACK);
                    g2.drawOval(sx - yuan, sy - yuan, yuan*2, yuan*2); //绘制空心圆
                    bq++;

                    //将数字显示在棋子上
                    g2.setColor(Color.black);

                    int es = 5;
                    g2.drawString(Integer.toString(shuZ[i][j]),sx-es,sy+es);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);


//        g.drawRect(20, 20, 20, 20);//绘制空心矩形
    }

    //---------------------------------------------------------------------------------------------------------------------
    //初始化游戏
    public void Initialize() {
        //遍历并初始化数组
        for (int i = 0; i < da; i++) {
            for (int j = 0; j < da; j++) {
                SaveGame[i][j] = 0;
                shuZ[i][j] = 0;
                sleepTimeSize = 0;
            }
        }

        qc = 1;//黑子先行
        go = "黑子先行";
        ji = 0;//回放执行的次数初始化
        zu = 0;
    }

    //--------------------------------------------------------------
    //使用线程来还复盘
    public void Fang(){
        System.out.println("kaishihuifang");

        //将下棋的时间间隔转化成long型的数字,转存到数组里
//        int sleepTimeSize ;
//        sleepTimeSize = stepRd.size()-1;
//        long []st = new long[sleepTimeSize];
//        for(int i=0;i<sleepTimeSize;i++){
//            st[i] = stepRd.get(i+1).getTime().getTime() -  stepRd.get(i).getTime().getTime();
//        }
//        int count=0;
//        for(Chess a:stepRd){
//            SaveGame[a.getXp()][a.getYp()]=a.getColor();//将一步棋的数据存入
//            shuZ[a.getXp()][a.getYp()] = a.getShu();//将棋子对应数字存入
//            //更改信息栏
//            if(a.getColor()==1) {
//                go = "轮到白子";
//            } else {
//                go = "轮到黑子";
//            }
//            //将一步棋子画上棋盘
//            this.paint(getGraphics());
//            if(count<sleepTimeSize){
//            //让程序睡眠同步时间间隔
//            try {
//                Thread.sleep(st[count]);//通过睡眠线程的方法来同步下棋时候的时间间隔。
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            count++;
//            }
//        }

        Chess a2 = stepRd.get(ji);
        SaveGame[a2.getXp()][a2.getYp()]=a2.getColor();//将一步棋的数据存入
        shuZ[a2.getXp()][a2.getYp()] = a2.getShu();//将棋子对应数字存入
        //更改信息栏
        if(a2.getColor()==1) {
            go = "轮到白子";
        } else {
            go = "轮到黑子";
        }

        if(WinLose == 1) {
            go = "黑子获胜";
        }

        if (WinLose == 2) {
            go = "白子获胜";
        }

        if(WinLose == 3) {
            go = "平手";
        }
        this.paint(getGraphics()); //将一步棋子画上棋盘
        ji++;

//        JOptionPane.showMessageDialog(this,"回放结束","温馨提示",1);
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

//        HTimer2Plus hTimer2Plus = new HTimer2Plus(); // 创建一个时钟，这个时钟要传给观察者
//        PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // 创建观察者

        //判断是否点击返回菜单按钮
        int gape = 120;
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + gape && e.getY() < by + gape + bh) {
            RecallView rv = RecallView.getInstance();
            rv.Recall(name);
            bf.dispose();

        }

        //判断是否点击开始按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {

            Initialize();
            this.repaint();
            //执行回放
            // Fang();
            playWatcher2Plus.watch();
//            Thread thread = new Thread(new PlayWatcher2Plus(hTimer2Plus));
//            thread.start();
            System.out.println("kaishi");
        }
        //点击快进按钮bx, by + 60, 70, bh
        if(e.getX() > bx && e.getX() < bx+70 && e.getY()>by+60 && e.getY()<by+60+bh){
            playWatcher2Plus.kuaiJing();
            System.out.println("kuaijin");
        }
        //点击暂停按钮
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
