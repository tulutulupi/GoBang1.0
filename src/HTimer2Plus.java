public class HTimer2Plus extends Thread {
    enum TYPE{
        UP,
        DOWN
    }

    private Thread thread = null; // �߳�
    private String min, sec, ns, sspTime; // �ֱ�洢����ʱ��
    private long time = 0; // �洢������
    private int doubleSpeed = 1; // ���٣�Ĭ��Ϊ1
    private TYPE type = TYPE.DOWN; // Ĭ��Ϊ����ʱ

    public HTimer2Plus() {
        min = ("00"); // ��ʼ��
        sec = ("00"); // ��ʼ��
        ns = ("00"); // ��ʼ��
        sspTime = ("00:00:00"); // ��ʼ��
    }

    // �÷�˵��
    private void test() {
        // ����һ��ʱ�ӣ����룩
        HTimer2Plus hTimer2Plus = new HTimer2Plus();
        // ���Դ����������������������ϡ�
        // 1. ��ʱ�ӿ�ʼ��ʱ��Ĭ��Ϊ����ʱ��
        hTimer2Plus.startTimer(60 * 1000); // �������Ǻ�������60��������
        // 2. ������ͣ����������ʱ���0
        hTimer2Plus.stopTimer(0);
        // 3. ������ͣ������ͣ������ʱ��ʱ���Ƕ��پ��Ƕ��٣�
        hTimer2Plus.stopTimer(hTimer2Plus.getTime());
        // 4. ��ȡ���ӵ�ʱ��
        long time = hTimer2Plus.getTime(); // ���غ�����
        // 5. ��ȡ���ӵ��ı�
        String timeText = hTimer2Plus.getSspTime(); // ����String,��ʽ����00:00:00�� ��ȡ������ʽ����ͨ��4��ȡʱ������д���
        // 6. �趨���ӵı���
        int speed = 2;
        hTimer2Plus.setDoubleSpeed(speed);  // �趨Ϊ2���٣�������������֧�ָ������������Ҫʵ��1.25����֮��ģ�ֻ��Ҫ��doubleSpeed�������޸�Ϊdouble��
        // 7. �趨����������ʱ���ǵ���ʱ
        hTimer2Plus.setType(TYPE.UP); // �趨Ϊ����ʱ
        hTimer2Plus.setType(TYPE.DOWN); // �趨Ϊ����ʱ
        // ע�⣺������ʵʱͬ�����������ʱ�������ڵ���ʱ��ͻȻʹ�ô˷���������������ͻȻ�������ʱ��

        // ע�⣺������û��ʹ��֧�ְ󶨵����ԡ������Ҫ���ӵ�ʱ��仯ʱ�Զ�����ĳ����������֣��뽫sspTime�����޸�Ϊ֧�ְ󶨵����ԡ�
    }

    // ��ʱ�ӿ�ʼ����ʱ
    public void startTimer(long time) {
        this.time = time; // �趨ʱ�ӵ�ʱ��
        thread = new Thread(this); // �����߳�
        thread.setPriority(Thread.MIN_PRIORITY); // �趨���ȼ�
        thread.start(); // ��ʼ������
    }

    // ��ʱ��ͣ����
    public void stopTimer(long time) {
        // ���ͣ����֮ǰ�����ܵģ���ô��������ͣ����
        if (thread != null) {
            thread.interrupt(); // �߳���ֹ
        }
        this.time = time; // �趨ʱ�䣨�Ӳ����ж��룩
        setTime(time); // ����ʱ�ӵ��ı�
    }

    public void setTime(long time) {
        this.time = time; // �趨ʱ�ӵ�ʱ��
        // ��������
        int seconds = (int) ((time / 1000) % 60);
        if (seconds <= 9) {
            this.sec = ("0" + seconds);
        } else {
            this.sec = ("" + seconds);
        }
        // ���������
        int minutes = ((int) ((time / 1000) / 60));
        if (minutes <= 9) {
            this.min = ("0" + minutes);
        } else {
            this.min = ("" + minutes);
        }
        // �������������Ǹ���
        int ns = ((int) ((time % 1000) / 10));
        if (ns <= 9) {
            this.ns = ("0" + ns);
        } else {
            this.ns = ("" + ns);
        }
        // ����һ��ʱ�ӵ��ı�
        this.sspTime = (this.min + ":" + this.sec + ":" + this.ns);
    }

    // �趨ʱ�ӵ��ٶ� 1������
    public void setDoubleSpeed(int doubleSpeed) {
        this.doubleSpeed = doubleSpeed;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    // ��ȡʱ�ӵĺ�����
    public long getTime() {
        return time;
    }

    // ��ȡʱ�ӵ��ı�
    public String getSspTime() {
        return sspTime;
    }

    public boolean isInterrupt(){
        return thread.isInterrupted();
    }

    // �趨���ʱ�ӵ�ÿһ�μ��
    private static final int SLEEPTIME = 65; // ʹ��const����Magic Number

    // ���ʱ����������ʱ��Ҫ��������
    @Override
    public void run() {
        try {
            // ���ʱ��û�б���ͣ����
            while (!thread.isInterrupted()) {
                // ���ʱ�ӵ�ʱ��<=0�ˣ���ôʱ�ӱ����ˡ�����Ҫ�为����
                if (time <= 0 && this.type == TYPE.DOWN) {
                    // �趨ʱ�Ӻ�����Ϊ0����λ��
                    setTime(0);
                    // ʱ�Ӱ��Լ�ͣ����
                    thread.interrupt();
                    // ����while
                    break;
                }
                // ����ʱ���ı�
                setTime(this.time);
                sleep(SLEEPTIME);
                // ��ʱ�ӵ�ʱ����٣����ϱ�������ʵ�ֱ��١�
                if (this.type == TYPE.UP){
                    // ����ʱ
                    this.time = this.time + (SLEEPTIME * doubleSpeed);
                }else{
                    // ����ʱ
                    this.time = this.time - (SLEEPTIME * doubleSpeed);
                }
            }
        } catch (Exception e) {
        }

    }
}//end of class
