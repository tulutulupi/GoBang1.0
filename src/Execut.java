import javax.swing.*;

public class Execut {
    public static void main(String[] args) {

        LoginView lv =  LoginView.getInstance();
        lv.login();
//      MyJFrame mj
//      = MyJFrame.getInstance();
//      mj.myJFrame();
//        RecallView rv = RecallView.getInstance();
//        rv.Recall();
//        BoFang bf = BoFang.getInstance();
//        bf.myJFrame();
//        AccountList al = AccountList.getInstance();
//        al.init();
    }


}
/*
程序存在的问题
1.界面来回的跳转，会出现重复页面的情况
2.应该将默认密码显示在界面上面
3.暂停的功能在返回进度的时候，应该有所提示，在信息提示栏里面
4.快进的功能太拉跨
5.放大缩小并不是截屏，所以棋谱查看变得没有了意义
6.多次选择同一个棋局回放，会出现白子消失的情况
7.信息提示栏时长抽风，有时显示有时不显示信息
8.界面美观度不够，打开的窗口过多
 */