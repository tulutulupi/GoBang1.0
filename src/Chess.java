import java.util.Date;

public class Chess {
    private int xp;//棋子x坐标
    private int yp;//棋子Y坐标
    private Date time;//落子时间
    private int color;//棋子的黑白
    private int shu;//棋子的数字编号

    public int getShu() {
        return shu;
    }

    public void setShu(int shu) {
        this.shu = shu;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getYp() {
        return yp;
    }

    public void setYp(int yp) {
        this.yp = yp;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public Chess(){

    }
    public Chess(int xp,int yp,Date time,int color,int shu){
        this.color = color;
        this.xp = xp;
        this.yp = yp;
        this.time = time;
        this.shu = shu;
    }

}
