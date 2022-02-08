import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecordTable  extends AbstractTableModel {
    private String[] columnsName = {"","�Ծֱ��","ִ����","ִ����","��սʱ��","��ս���"};
    private Object[][] data;

    public RecordTable(ArrayList<GameRecord> list){
        data = new Object[list.size()][6];
        for(int i = 0;i<list.size();i++){
            data[i][0] = new Boolean(false);
            data[i][1] = list.get(i).getContestNum();
            data[i][2] = list.get(i).getPlayer1();
            data[i][3] = list.get(i).getPlayer2();
            SimpleDateFormat s = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
            data[i][4] = s.format(list.get(i).getStartTime());
            if(list.get(i).getOutcome()==1) {
                data[i][5] = "��ʤ";
            }
            if(list.get(i).getOutcome()==2) {
                data[i][5] = "��ʤ";
            }
            if(list.get(i).getOutcome()==3) {
                data[i][5] = "ƽ��";
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
        /* ֪ͨ���������ݵ�Ԫ�����Ѿ��ı� */
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return data[0][columnIndex].getClass();
    }
}
