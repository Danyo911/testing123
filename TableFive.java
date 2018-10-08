package noliktava;



import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableFive extends JTable {
	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return Boolean.class;
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

	public Object[][] leftover = new Object[Noliktava.fullCell][];
	{
		int ind = 0;
		for (int i = 0; i < Noliktava.data.size(); i++) {
			int test = (Integer) Noliktava.fullList[i][3];
			if (test != 0) {
				leftover[ind] = Noliktava.fullList[i];
				ind++;
			}
		}
	}

	public TableFive() {
		// TODO Auto-generated constructor stub

		this.setShowHorizontalLines(true);
		this.setShowVerticalLines(true);
		this.setModel(new DefaultTableModel(

				leftover,
				new String[] { "Izvēle", "Kods", "Preces nosaukums", "Atlikums", "Iepirkšanas cena",
						"Iepirkšanas summa", "Pārdošanas cena", "Pārdošanas summa", "Realizācijas cena",
						"Realizācijas summa", "Piegādātājs" }));

		this.getColumnModel().getColumn(0).setPreferredWidth(2);
		this.getColumnModel().getColumn(1).setPreferredWidth(15);
		this.getColumnModel().getColumn(2).setPreferredWidth(250);
		this.getColumnModel().getColumn(3).setPreferredWidth(30);
		this.getColumnModel().getColumn(4).setPreferredWidth(30);
		this.getColumnModel().getColumn(5).setPreferredWidth(50);
		this.getColumnModel().getColumn(6).setPreferredWidth(30);//par cena  (30)
		this.getColumnModel().getColumn(7).setPreferredWidth(50);//par Summa (50)
		this.getColumnModel().getColumn(8).setPreferredWidth(30);
		this.getColumnModel().getColumn(9).setPreferredWidth(50);
		this.getColumnModel().getColumn(10).setPreferredWidth(30);
		this.setAutoCreateRowSorter(true);
		this.setEditingColumn(0);
		this.setEditingRow(0);
		this.getTableHeader().setReorderingAllowed(false);
		
		if(!Noliktava.showParCena) {
		this.removeColumn(this.getColumnModel().getColumn(6));
		this.removeColumn(this.getColumnModel().getColumn(6));
		}
		
	}
}
