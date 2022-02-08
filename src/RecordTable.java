import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecordTable  extends AbstractTableModel {
    private String[] columnsName = {"","对局编号","执黑者","执白着","对战时间","对战结果"};
    private Object[][] data;

    public RecordTable(ArrayList<GameRecord> list){
        data = new Object[list.size()][6];
        for(int i = 0;i<list.size();i++){
            data[i][0] = new Boolean(false);
            data[i][1] = list.get(i).getContestNum();
            data[i][2] = list.get(i).getPlayer1();
            data[i][3] = list.get(i).getPlayer2();
            SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            data[i][4] = s.format(list.get(i).getStartTime());
            if(list.get(i).getOutcome()==1) {
                data[i][5] = "黑胜";
            }
            if(list.get(i).getOutcome()==2) {
                data[i][5] = "白胜";
            }
            if(list.get(i).getOutcome()==3) {
                data[i][5] = "平局";
            }
        }
    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return columnsName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    @Override
    public String getColumnName(int column) {
        return columnsName[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        /* 通知监听器数据单元数据已经改变 */
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }
}
