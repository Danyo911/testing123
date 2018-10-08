package noliktava;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableFiveSmall extends JTable {

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            default:
                return String.class;
        }


    }
    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 0:
                return true;
            default:
                return false;
        }
    }
    
    public Object[][] outOfStock = new Object[Noliktava.eamptyCell][];
	{		
		int ind = 0;
		for (int i = 0; i < Noliktava.data.size(); i++) {
			int test = (Integer)Noliktava.fullList[i][3];
			if (test == 0)
			{
				outOfStock[ind] = Noliktava.fullSmallList[i];
				ind++;
			}
		}
	}
    


    public TableFiveSmall() {
        // TODO Auto-generated constructor stub
        this.setShowHorizontalLines(true);
        this.setShowVerticalLines(true);
        this.setModel(new DefaultTableModel(
        			outOfStock,
                    new String [] {
                        "Kods", "Preces Nosaukums", "Pārdošanas cena", "Realizācijas cena", "Piegādātājs"
                    }));
        this.getColumnModel().getColumn(0).setPreferredWidth(15);
        this.getColumnModel().getColumn(1).setPreferredWidth(15);
        this.getColumnModel().getColumn(2).setPreferredWidth(250);
        this.getColumnModel().getColumn(3).setPreferredWidth(30);
        this.getColumnModel().getColumn(4).setPreferredWidth(50);
        this.setAutoCreateRowSorter(false);
        this.setEditingColumn(0);
        this.setEditingRow(0);
        this.setEnabled(false);
        this.getTableHeader().setReorderingAllowed(false);
    }
}
