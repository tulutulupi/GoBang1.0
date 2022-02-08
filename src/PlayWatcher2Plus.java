import javax.swing.*;

public class PlayWatcher2Plus extends Thread {
    private Thread thread = null; //线程
    private HTimer2Plus hTimer2Plus; // 观察者要持有一个闹钟，不然他看谁？

    // 构造函数（传入一个要被观察的时钟）
    public PlayWatcher2Plus(HTimer2Plus hTimer2Plus) {
        this.hTimer2Plus = hTimer2Plus;
    }

    // 用法说明
    private void test(){
        // 必须：创建一个观察者
        HTimer2Plus hTimer2Plus = new HTimer2Plus(); // 创建一个时钟，这个时钟要传给观察者
        PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // 创建观察者
        // 用法
        // 1.让观察者看着时钟
        playWatcher2Plus.watch();
        // 2.观察者别看了
        playWatcher2Plus.pause();
    }

    // 开始盯着时钟看
    public void watch() {
        // 实例化线程
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        // 开始盯着看
        thread.start();
    }

    // 不要盯着看
    public void pause() {
        // 如果线程存在，才能停下来。
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void kuaiJing() {
        hTimer2Plus.setDoubleSpeed(5);
    }

    public void zanTing(){
        pause();
        hTimer2Plus.stopTimer(hTimer2Plus.getTime());
        System.out.println("暂停");
    }

    public void huiFu(){
        watch();
        hTimer2Plus.startTimer(hTimer2Plus.getTime());
        System.out.println("恢复");
    }

    public boolean isInterrupted(){
        return thread.isInterrupted();
    }



    @Override
    public void run() {
        BoFang bf = BoFang.getInstance();
        System.out.println("线程运行");
        try {
            // 如果观察者没有被人停下来
            while (!thread.isInterrupted()) {
                System.out.println("观察者线程没停");
                if (hTimer2Plus.getTime() <= 0) {
                    System.out.println("闹钟不为零");
                    // 闹钟已经到零了！
                    // start 把这个棋子（棋子A）放下去
                    // 【注意】这里放置自己的落子逻辑
                    // end
                    // 判断刚刚下的棋子（棋子A），是不是最后一个棋子
                    bf.Fang();

                    boolean isLastChess = bf.isLastOne(); // 【注意】请把这句话改成自己的，isLastChess变量代表当前要下的棋子是不是最后一个棋子
                    if (isLastChess) {
                        JOptionPane.showMessageDialog(bf,"对战结束");
                        System.out.println("最后一个棋子");
                        thread.interrupt(); // 观察者不要盯着看了，因为没有棋子要下了
                    } else {
                        System.out.println("将下一个棋子的时间传入");
                        // 还有下一个棋子的话，就让闹钟重新开始倒计时（时间为下一个棋子的落子时间）
                        long nextChessTime = bf.getSleepTime(); // 【注意】请把这句话改成自己的，nextChessTime变量代表下一个棋子的落子时间
                        hTimer2Plus.startTimer(nextChessTime);
                    }
                }
                // 观察者休息一下，不要老是盯着人家看！休息完了，再看看闹钟到点了吗
                sleep(500);
            }
        } catch (Exception e) {
        }

    }
}//end of class