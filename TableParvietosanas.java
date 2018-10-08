package noliktava;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;





public class TableParvietosanas extends JTable {
	@Override
	public Class getColumnClass(int column) {
		switch (column) {		
		case 1:
			return Boolean.class;
		case 4:
			return Integer.class;
		case 5:
			return Integer.class;
		default:
			return String.class;
		}
	}
    
    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
        case 1:
        	return true;
        case 4:
        	return true;
        default:
            return false;
        }
	
    }
    public static DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();    
    {
    
	this.setShowHorizontalLines(true);
    this.setShowVerticalLines(true);
    this.setModel(TabbedPane.relocmodel);
    this.getColumnModel().getColumn(0).setPreferredWidth(5);    
    this.getColumnModel().getColumn(1).setPreferredWidth(5);
    this.getColumnModel().getColumn(2).setPreferredWidth(15);
    this.getColumnModel().getColumn(3).setPreferredWidth(550);
    this.getColumnModel().getColumn(4).setPreferredWidth(50);
    this.getColumnModel().getColumn(5).setPreferredWidth(50);
    this.getColumnModel().getColumn(6).setPreferredWidth(50);
    this.setAutoCreateRowSorter(false);
    this.setEditingColumn(0);
    this.setEditingRow(0);
    this.setEnabled(true);
    this.getTableHeader().setReorderingAllowed(false);
    
    
    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
    this.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
    
}
	
	
	

}
