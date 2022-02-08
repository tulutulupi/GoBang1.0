import javax.swing.*;

public class PlayWatcher2Plus extends Thread {
    private Thread thread = null; //�߳�
    private HTimer2Plus hTimer2Plus; // �۲���Ҫ����һ�����ӣ���Ȼ����˭��

    // ���캯��������һ��Ҫ���۲��ʱ�ӣ�
    public PlayWatcher2Plus(HTimer2Plus hTimer2Plus) {
        this.hTimer2Plus = hTimer2Plus;
    }

    // �÷�˵��
    private void test(){
        // ���룺����һ���۲���
        HTimer2Plus hTimer2Plus = new HTimer2Plus(); // ����һ��ʱ�ӣ����ʱ��Ҫ�����۲���
        PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // �����۲���
        // �÷�
        // 1.�ù۲��߿���ʱ��
        playWatcher2Plus.watch();
        // 2.�۲��߱���
        playWatcher2Plus.pause();
    }

    // ��ʼ����ʱ�ӿ�
    public void watch() {
        // ʵ�����߳�
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        // ��ʼ���ſ�
        thread.start();
    }

    // ��Ҫ���ſ�
    public void pause() {
        // ����̴߳��ڣ�����ͣ������
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
        System.out.println("��ͣ");
    }

    public void huiFu(){
        watch();
        hTimer2Plus.startTimer(hTimer2Plus.getTime());
        System.out.println("�ָ�");
    }

    public boolean isInterrupted(){
        return thread.isInterrupted();
    }



    @Override
    public void run() {
        BoFang bf = BoFang.getInstance();
        System.out.println("�߳�����");
        try {
            // ����۲���û�б���ͣ����
            while (!thread.isInterrupted()) {
                System.out.println("�۲����߳�ûͣ");
                if (hTimer2Plus.getTime() <= 0) {
                    System.out.println("���Ӳ�Ϊ��");
                    // �����Ѿ������ˣ�
                    // start ��������ӣ�����A������ȥ
                    // ��ע�⡿��������Լ��������߼�
                    // end
                    // �жϸո��µ����ӣ�����A�����ǲ������һ������
                    bf.Fang();

                    boolean isLastChess = bf.isLastOne(); // ��ע�⡿�����仰�ĳ��Լ��ģ�isLastChess��������ǰҪ�µ������ǲ������һ������
                    if (isLastChess) {
                        JOptionPane.showMessageDialog(bf,"��ս����");
                        System.out.println("���һ������");
                        thread.interrupt(); // �۲��߲�Ҫ���ſ��ˣ���Ϊû������Ҫ����
                    } else {
                        System.out.println("����һ�����ӵ�ʱ�䴫��");
                        // ������һ�����ӵĻ��������������¿�ʼ����ʱ��ʱ��Ϊ��һ�����ӵ�����ʱ�䣩
                        long nextChessTime = bf.getSleepTime(); // ��ע�⡿�����仰�ĳ��Լ��ģ�nextChessTime����������һ�����ӵ�����ʱ��
                        hTimer2Plus.startTimer(nextChessTime);
                    }
                }
                // �۲�����Ϣһ�£���Ҫ���Ƕ����˼ҿ�����Ϣ���ˣ��ٿ������ӵ�������
                sleep(500);
            }
        } catch (Exception e) {
        }

    }
}//end of class