package noliktava;
/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import noliktava.Noliktava.Data;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class TabbedPane extends JPanel {
	public JTabbedPane tabbedPane;
	public static DefaultTableModel relocmodel = new DefaultTableModel(new Object[][] {}, new String[] {"Nr", "Izvēle",
			"Kods", "Preces Nosaukums", "Daudzums", "Atļautais daudzums", "Realizācijas cena", "Summa" });
	public static int m = 0;
	public static boolean testBoolean = true;
	public static double sumWithTax = 0;
	public static JTextField pvn;
	public static JTextField arPVNKopa;
	public static JTextField summaBezPVN;
	public static JTextField datums;
	public static JComboBox<?> veikaluIzv;
	public static double tax = 1.21;

	public void taxFieldUpdate() {
		pvn.setText(Double.toString(Noliktava.roundToTwo(sumWithTax - (sumWithTax / tax))));
		summaBezPVN.setText(Double.toString(Noliktava.roundToTwo(sumWithTax / tax)));
		arPVNKopa.setText(Double.toString(Noliktava.roundToTwo(sumWithTax)));
	}

	public TabbedPane() {
		super(new GridLayout(1, 1));

		tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("");// images/middle.gif");

		JComponent panel1 = makeTablePanelONE();
		tabbedPane.addTab("Preču atlikums", icon, panel1, "Atlukumu tabula");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeTablePanelTWO();
		tabbedPane.addTab("Beigušās preces", icon, panel2, "Beigušo preču tabula");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = makeRlocetionPanel();
		tabbedPane.addTab("Pārvietot", icon, panel3, "Pārvietot preci");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		/**
		 * JComponent panel4 = makeTextPanel("Pārdot"); panel4.setPreferredSize(new
		 * Dimension(410, 50)); tabbedPane.addTab("Pārdot", icon, panel4, "Pārdot");
		 * tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		 */

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	protected JComponent makeTablePanelONE() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		final JTable filler = new TableFive();
		JButton izveletie = new JButton("Pārvietot izvēlētās preces");
		JButton pievienotPreci = new JButton("Pievienot preci");
		JButton noslegt = new JButton("Noslēgt mēnesi");

		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(filler);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 300;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		c.gridheight = 6;
		panel.add(jScrollPane2, c);

		izveletie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Noliktava.fromTable(filler.getModel());
			}
		});
		c.ipady = 25;
		c.ipadx = 25;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		panel.add(izveletie, c);

		pievienotPreci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Noliktava.pievPreci();
			}
		});
		c.ipady = 25;
		c.ipadx = 25;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		panel.add(pievienotPreci, c);

		noslegt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Noliktava.noslegt();
			}
		});
		c.ipady = 25;
		c.ipadx = 25;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 4;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		panel.add(noslegt, c);

		return panel;
	}

	protected JComponent makeTablePanelTWO() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTable out = new TableFiveSmall();

		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(out);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 300;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		c.gridheight = 2;
		panel.add(jScrollPane2, c);

		return panel;
	}

	protected JComponent makeRlocetionPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 25;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 3;
		JLabel lb = new JLabel(Noliktava.nextNolName, SwingConstants.CENTER);
		lb.setFont(new Font("Serif", Font.PLAIN, 40));
		panel.add(lb, c);

		JLabel lbveikaluIzv = new JLabel("Izvēlieties saņēmēju:");
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 0;
		panel.add(lbveikaluIzv, c);
		veikaluIzv = new JComboBox<Object>(Noliktava.veikaluNos);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		panel.add(veikaluIzv, c);

		JLabel lbDatums = new JLabel("Ievadiet izdošanas datumu: ");
		c.gridy = 1;
		c.gridx = 2;
		panel.add(lbDatums, c);
		datums = new JTextField(10);
		c.gridx = 3;
		panel.add(datums, c);

		JButton dzestIzv = new JButton("Dzēst izvēlēto");
		dzestIzv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int j = 0; j < TabbedPane.relocmodel.getRowCount() + 5; j++) { // varbut var efektivak izdarit, lai
																					// nemtu vera izmaintito rindas
																					// indeksu?
					for (int i = 0; i < TabbedPane.relocmodel.getRowCount(); i++) {
						if ((boolean) ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(i))
								.elementAt(1) == true) { // lasit dokumentacija, lai atkartotu
							TabbedPane.relocmodel.removeRow(i);
							Noliktava.izvPreces.remove(i);
						}
					}
				}
			}
		});
		c.gridy = 2;
		panel.add(dzestIzv, c);

		JLabel lbSummaBezPVN = new JLabel("Summa bez PVN: ", SwingConstants.RIGHT);
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 0;
		panel.add(lbSummaBezPVN, c);
		summaBezPVN = new JTextField(10);
		summaBezPVN.setEditable(false);
		c.gridx = 5;
		panel.add(summaBezPVN, c);

		JLabel lbPVN = new JLabel("PVN: ", SwingConstants.RIGHT);
		c.gridy = 1;
		c.gridx = 4;
		panel.add(lbPVN, c);
		pvn = new JTextField(10);
		pvn.setEditable(false);
		c.gridx = 5;
		panel.add(pvn, c);

		JLabel lbArPVNKopa = new JLabel("Ar PVN kopā: ", SwingConstants.RIGHT);
		c.gridx = 4;
		c.gridy = 2;
		panel.add(lbArPVNKopa, c);
		arPVNKopa = new JTextField(10);
		arPVNKopa.setEditable(false);
		c.gridx = 5;
		panel.add(arPVNKopa, c);

		JTable table = new TableParvietosanas();
		relocmodel = (DefaultTableModel) table.getModel();
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(table);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 450;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 6;
		c.gridheight = 3;
		panel.add(jScrollPane2, c);

		JButton pievVeikalu = new JButton("Pievienot veikalu");
		pievVeikalu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JPanel panel2 = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.ipady = 5;
				c.ipadx = 25;
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 0.5;
				c.weighty = 0.5;
				c.anchor = GridBagConstraints.CENTER;
				c.gridx = 0;
				c.gridy = 0;
				JLabel lbApzimejums = new JLabel("Veikala apzīmējums:");
				panel2.add(lbApzimejums, c);

				JTextField veikApzimejums = new JTextField(10);
				c.gridy = 1;
				panel2.add(veikApzimejums, c);

				JLabel lbAdrese = new JLabel("Veikala adresse:");
				c.gridy = 2;
				panel2.add(lbAdrese, c);

				JTextField veikAdrese = new JTextField(10);
				c.gridy = 3;
				panel2.add(veikAdrese, c);

				int g = -1;
				while (true) {
					String[] options = { "Apstiprināt", "Atcelt" };
					g = JOptionPane.showOptionDialog(null, panel2, "Jauna veikala pievienošana",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (g == 0) {
						boolean neatkartojas = true;
						String faktiskaAdrese = veikAdrese.getText();
						String veikalaApzimejums = veikApzimejums.getText();
						for (int i = 0; i < veikaluIzv.getModel().getSize(); i++) {
							if (veikalaApzimejums.equals(veikaluIzv.getModel().getElementAt(i))) {
								neatkartojas = false;
								break;
							}
						}
						if (neatkartojas && veikalaApzimejums.length() > 0 && faktiskaAdrese.length() > 0) {
							try {
								FileInputStream input = new FileInputStream(Noliktava.excelShops);
								XSSFWorkbook workbook;
								workbook = new XSSFWorkbook(input);
								XSSFSheet sheet = workbook.getSheetAt(0);
								Row ro = sheet.getRow(sheet.getLastRowNum() + 1);
								if (ro == null) {
									ro = sheet.createRow(sheet.getLastRowNum() + 1);
								}
								String[] addShop = new String[2];
								for (int j = 0; j < 2; j++) {
									Cell ce = ro.getCell(j);
									if (ce == null) {
										ce = ro.createCell(j);
									}
									ce.setCellType(CellType.STRING);
									if (j == 0) {
										ce.setCellValue(veikalaApzimejums);
										addShop[0] = veikalaApzimejums;
									}
									if (j == 1) {
										ce.setCellValue(faktiskaAdrese);
										addShop[1] = faktiskaAdrese;
									}

								}
								Noliktava.veikali.add(addShop);
								FileOutputStream file = new FileOutputStream(Noliktava.excelShops);
								workbook.write(file);
								file.flush();
								file.close();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Object[][] tempShopList = new String[Noliktava.veikali.size()][2];
							Object[] newShopList = new String[Noliktava.veikali.size()];
							for (int k = 0; k < Noliktava.veikali.size(); k++) {
								tempShopList[k] = Noliktava.veikali.get(k);
								newShopList[k] = tempShopList[k][0];
							}
							veikaluIzv.setModel(new DefaultComboBoxModel(newShopList));
							break;
						} else {
							if (veikalaApzimejums.length() > 0 && faktiskaAdrese.length() > 0) {
								JFrame frame2 = new JFrame("Uzmanību!");
								JOptionPane.showMessageDialog(frame2, "Šāds veikala apzīmējums jau eksistē!",
										"Uzmanību!", JOptionPane.WARNING_MESSAGE);

							} else {
								JFrame frame = new JFrame("Uzmanību!");
								JOptionPane.showMessageDialog(frame, "Visiem laukiem jābūt aizpildītiem!", "Uzmanību!",
										JOptionPane.WARNING_MESSAGE);
							}

						}

					}
					if (g == 1) {
						break;
					}

				}
			}
		});
		c.ipady = 25;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 2;
		c.gridheight = 1;
		panel.add(pievVeikalu, c);

		c.gridx = 2;
		JButton attirit = new JButton("Attīrīt");
		attirit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (TabbedPane.relocmodel.getRowCount() > 0) {
					int g = -1;
					String[] options = { "Jā", "Nē" };
					g = JOptionPane.showOptionDialog(null, "Vai tiešām vēleties attīrīt šo cīlni?", null,
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (g == 0) {
						TabbedPane.relocmodel.setRowCount(0);
						datums.setText("");
						veikaluIzv.setSelectedIndex(0);
					}
					if (g == 1) {

					}
				}
			}
		});
		panel.add(attirit, c);

		JButton apstipritnat = new JButton("Apstiprināt");
		apstipritnat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (TabbedPane.relocmodel.getRowCount() > 0) {
					boolean not0 = true;
					for (int i = 0; i < TabbedPane.relocmodel.getRowCount(); i++) {
						try {
							if ((int) ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(i))
									.elementAt(4) <= 0) {
								not0 = false;
								break;
							}
						}					
					catch(Exception E) {
						not0=false;
						break;
					}
					}
					
					if (!not0) {
						JFrame frame = new JFrame("Uzmanību!");
						JOptionPane.showMessageDialog(frame, "Preces daudzumam ir jābūt lielākam par 0!", "Uzmanību!",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (datums.getText().length() != 0) {
							JPanel panel = new JPanel(new GridBagLayout());
							GridBagConstraints c = new GridBagConstraints();
							JLabel lbIzdevejs = new JLabel("Izvēlaties izdevēju: ");
							panel.add(lbIzdevejs, c);
							c.gridx = 1;
							Object[] izdeveji = new Object[] { "Atstāt tukšu", "Diāna Pipure", "Rota Leisova",
									"Anna Goršeņina" };
							JComboBox<?> izvIzdeveji = new JComboBox<Object>(izdeveji);
							panel.add(izvIzdeveji, c);
							String[] options = { "Apstiprināt", "Atcelt" };
							int g = JOptionPane.showOptionDialog(null, panel, "Apstipriniet pavadzīmes izveidošanu:",
									JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

							if (g == 0) {
								ArrayList<String> pavNosaukumi = new ArrayList<String>();
								int pavCount = TabbedPane.relocmodel.getRowCount()/30;
								if(TabbedPane.relocmodel.getRowCount()%30 != 0) {
									pavCount +=1;
								}
								
								for(int curPav = 0; curPav<pavCount; curPav++) {
									ArrayList<Object[]> contOfTable = new ArrayList<Object[]>();
									double partSum = 0;
									int h = 0;
									for(int curModelRow = 0 + curPav*30; h<30 && curModelRow<TabbedPane.relocmodel.getRowCount();curModelRow++) {
										Object[] tempRow = new Object[5];
										tempRow[0] = ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(2); //kods
										tempRow[1] = ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(3); //nos
										tempRow[2] = ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(4); //daudz
										tempRow[3] = ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(6);//cena
										tempRow[4] = ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(7);//summa	
										partSum += (double)((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(curModelRow)).elementAt(7);
										contOfTable.add(tempRow);										
										h++;
									}
									h =0;
								
								Noliktava.copyFileFromTo(Noliktava.NOLexampleFile, Noliktava.nextNOL);
								String[] tempAdr;
								tempAdr = Noliktava.veikali.get(veikaluIzv.getSelectedIndex());
								String adrese = tempAdr[1];
								try {
									FileInputStream input;
									input = new FileInputStream(Noliktava.nextNOL);
									XSSFWorkbook workbook = new XSSFWorkbook(input);
									workbook.setSheetName(0, Noliktava.veikali.get(veikaluIzv.getSelectedIndex())[0]); // Nomaina sheet uz izv veikala apzimejumu																													  
									Sheet sheet = workbook.getSheetAt(0);
									if (izvIzdeveji.getSelectedIndex() != 0) {
										sheet.getRow(51).getCell(1)
												.setCellValue((String) izvIzdeveji.getSelectedItem()); // ievadi izvēlēto izdevēju																										
									}
									sheet.getRow(0).getCell(4).setCellValue("PAVADZĪME " + Noliktava.nextNolName); // pavNosaukums
									sheet.getRow(1).getCell(0).setCellValue(datums.getText()); // Datums
									sheet.getRow(53).getCell(1).setCellValue(datums.getText()); // Datums apaksa
									sheet.getRow(12).getCell(2).setCellValue(adrese); // adrese no izv veikala
									sheet.getRow(45).getCell(9).setCellValue(Noliktava.roundToTwo(partSum/1.21));// PVN
									sheet.getRow(46).getCell(9).setCellValue(Noliktava.roundToTwo(partSum-(partSum/1.21)));// bez PVN									
									sheet.getRow(47).getCell(9).setCellValue(Noliktava.roundToTwo(partSum));// kopa
									
									
									
									
									for (int i = 15; i < contOfTable.size() + 15; i++) {
										Row re = sheet.getRow(i);
										for (int j = 1; j < 10; j++) { //NEMAINIT J!!! Pavazime excel cell novietojums!
											Cell cell = re.getCell(j);
											re = sheet.getRow(i);
											cell = re.getCell(j);
											cell.setCellType(CellType.STRING);
											if (j == 1) { // kods
												cell.setCellValue(contOfTable.get(i-15)[0].toString());
											}
											if (j == 2) { // nosaukums
												cell.setCellValue(contOfTable.get(i-15)[1].toString());
											}
											if (j == 7) { // daudzums
												cell.setCellValue(Double.parseDouble(contOfTable.get(i-15)[2].toString()));
											}
											if (j == 8) { // cena
												cell.setCellValue(Double.parseDouble(contOfTable.get(i-15)[3].toString()));
											}
											if (j == 9) { // kopaa
												double kopa =Double.parseDouble(contOfTable.get(i-15)[4].toString());
												cell.setCellValue(kopa);
											}

										}
									}
									FileOutputStream output = new FileOutputStream(Noliktava.nextNOL);
									workbook.write(output);
									workbook.close();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								File pavadzime = Noliktava.desktopPath.toFile();
								pavadzime.mkdirs();
								pavadzime = Noliktava.desktopPath.resolve(Noliktava.nextNolName + ".xlsx").toFile();
								Noliktava.copyFileFromTo(Noliktava.nextNOL, pavadzime);								
								pavNosaukumi.add("Pavadzīme "+Noliktava.nextNolName);
								Noliktava.incNolNr();
								
								}// end of Pav
								
								
								for (int k = 0; k < Noliktava.izvPreces.size(); k++) {
									Data atjPrece = new Data();
									atjPrece.setnr(0);
									atjPrece.setkods(Noliktava.izvPreces.get(k)[1].toString());
									atjPrece.setnosaukums(Noliktava.izvPreces.get(k)[2].toString());
									atjPrece.setatlikums(Integer.parseInt(((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(k)).elementAt(4).toString()));
									atjPrece.setiepCena(Noliktava.roundToTwo(Double.parseDouble(Noliktava.izvPreces.get(k)[6].toString())));
									atjPrece.setparCena(Noliktava.roundToTwo(Double.parseDouble(Noliktava.izvPreces.get(k)[4].toString())));
									atjPrece.setreaCena(Noliktava.roundToTwo(Double.parseDouble(Noliktava.izvPreces.get(k)[5].toString())));									
									int atmCount = Noliktava.data.size();
									String test = atjPrece.getkods();
									for (int i = 0; i < atmCount; i++) {										
										if (test.equals(Noliktava.infoSar[i][1])) {
											if (atjPrece.getiepCena() == (double) Noliktava.infoSar[i][4]
													&& atjPrece.getparCena() == (double) Noliktava.infoSar[i][5]
													&& atjPrece.getreaCena() == (double) Noliktava.infoSar[i][6]) {
												Data rewrite = new Data();
												rewrite.setnr((int) Noliktava.infoSar[i][0]);
												rewrite.setkods((String) Noliktava.infoSar[i][1]);
												rewrite.setnosaukums((String) Noliktava.infoSar[i][2]);
												rewrite.setatlikums((int)Noliktava.infoSar[i][3] - atjPrece.getatlikums());
												rewrite.setiepCena((double) Noliktava.infoSar[i][4]);
												rewrite.setparCena((double) Noliktava.infoSar[i][5]);
												rewrite.setreaCena((double) Noliktava.infoSar[i][6]);
												rewrite.setpiegad((String) Noliktava.infoSar[i][7]);
												Noliktava.data.set(i, rewrite);
												break;
											}
										}
									}
								}
								JPanel namePanel = new JPanel(new GridBagLayout());
								GridBagConstraints constr = new GridBagConstraints();
								for(int pan = 0; pan < pavNosaukumi.size();pan++) {
									JLabel lb = new JLabel(pavNosaukumi.get(pan));
									constr.gridy=pan;
									namePanel.add(lb,constr);									
								}
								JOptionPane.showOptionDialog(null, namePanel, "Izveidotas sekojošas pavadzīmes:",
										JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
								
								
								TabbedPane.relocmodel.setRowCount(0);
								datums.setText("");
								veikaluIzv.setSelectedIndex(0);
								Noliktava.toExcel(Noliktava.excel, Noliktava.data);								
								Noliktava.restart();

							}
						} else {
							JFrame frame = new JFrame("Uzmanību!");
							JOptionPane.showMessageDialog(frame, "Nepieciešams ievadīt datumu!", "Uzmanību!",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		c.gridx = 4;
		panel.add(apstipritnat, c);

		TabbedPane.relocmodel.addTableModelListener(new TableModelListener() { // Sarezditi bet strada
			@Override
			public void tableChanged(TableModelEvent e) {
				/*
				 * if (TabbedPane.relocmodel.getRowCount() == 30 ||
				 * TabbedPane.relocmodel.getRowCount() == 0) { pievPreci.setEnabled(false); }
				 * else { pievPreci.setEnabled(true); }
				 */
				if (testBoolean) {
					for (; m < TabbedPane.relocmodel.getRowCount();) {
						testBoolean = false;
						int count = 0;
						int limit = Integer.parseInt(
								(String) ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(m)).elementAt(5));
						try {
							count = (int) ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(m)).elementAt(4);
						} catch (Exception exception) {
							count = 0;
						}

						if (count > limit) {
							JFrame frame = new JFrame("Uzmanību!");
							JOptionPane.showMessageDialog(frame,
									"Preces atļautais daudzums pārsnoegts, ievadītais skaits aizstāts ar autļauto daudzumu!",
									"Uzmanību!", JOptionPane.WARNING_MESSAGE);
							TabbedPane.relocmodel.setValueAt(limit, m, 4);
						}
						double price = Double.parseDouble(
								(String) ((Vector<?>) TabbedPane.relocmodel.getDataVector().elementAt(m)).elementAt(6));

						double sum = Noliktava.roundToTwo(count * price);
						sumWithTax += sum;
						TabbedPane.m = m + 1;

						TabbedPane.relocmodel.setValueAt(sum, m - 1, 7);
					}
					m = 0;
					testBoolean = true;
					taxFieldUpdate();
					sumWithTax = 0;
				}
			}
		});
		taxFieldUpdate();
		return panel;

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TabbedPane.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */

}
