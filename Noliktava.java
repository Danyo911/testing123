package noliktava;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.NumberFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Noliktava {
	public static String noName(File where) {
		String name = null;

		int g = -1;
		while (g < 0) {
			JPanel panel = new JPanel();
			JLabel lb = new JLabel("Ievadiet jaunā faila nosaukumu! (.xlsx)");
			JTextField field = new JTextField(10);
			String[] options = { "Apstiprināt" };
			panel.add(lb);
			panel.add(field);
			g = JOptionPane.showOptionDialog(null, panel, "Ievade", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			name = field.getText();

			if (name.length() > 0 && name != null) {
				g++;
				name = name + ".xlsx";
			} else {
				g = -1;
			}
		}

		FileWriter writer = null;
		boolean b = true;
		try {
			writer = new FileWriter(where, b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.write(name);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;

	}

	public static String readName(File where) {
		crateFile(where);
		Scanner myScanner;
		String got_name = "didn't change";
		try {
			myScanner = new Scanner(where);
			if (myScanner.hasNextLine()) {
				got_name = myScanner.nextLine();
			} else {
				got_name = noName(where);
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			crateFile(where);

		}
		return got_name;
	}

	public static int nextNolNumber(File where) {
		crateFile(where);
		Scanner myScanner;
		int number = -1;
		try {
			myScanner = new Scanner(where);
			if (myScanner.hasNextLine()) {
				number = Integer.parseInt(myScanner.nextLine());
			} else {
				number = noNumber(where);
			}
			myScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			crateFile(where);

		}
		return number;
	}
	public static void incNextNolNumber() {
		String temp = Integer.toString(nextNolNumber(numberNolFile)+1);
		FileWriter writer = null;
		boolean b = false;
		try {
			writer = new FileWriter(numberNolFile, b);
			writer.write(temp);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static int noNumber(File where) {
		String text = null;
		int number = -1;
		int g = -1;
		while (g < 0) {
			JPanel panel = new JPanel();
			JLabel lb = new JLabel("Ievadiet pavadzīmes nummuru! (skat .xlsx)");
			JTextField field = new JTextField(10);
			String[] options = { "Apstiprināt" };
			panel.add(lb);
			panel.add(field);
			g = JOptionPane.showOptionDialog(null, panel, "Ievade", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			text = field.getText();
			if (text.length() > 0 && text != null) {
				number = Integer.parseInt(text);
				if (number > 0) {
					g++;
				} else {
					g = -1;
				}
			} else {
				g = -1;
			}
		}

		FileWriter writer = null;
		boolean b = true;
		try {
			writer = new FileWriter(where, b);
			writer.write(text);
			writer.close();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;

	}

	public static void toExcel(File excel, ArrayList<Data> data) {
		FileInputStream input;

		infoSar = new Object[data.size()][];
		{
			for (int i = 0; i < data.size(); i++) {
				Data newData = data.get(i);
				infoSar[i] = newData.precesInd();
			}
		}

		try {
			input = new FileInputStream(excel);
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < data.size(); i++) {
				Row ro = sheet.getRow(i + 1);
				if (ro == null) {
					ro = sheet.createRow(i + 1);
				}

				for (int j = 0; j < 8; j++) {
					Cell ce = ro.getCell(j);
					if (ce == null) {
						ce = ro.createCell(j);
					}

					if (j == 0) {
						ce.setCellType(CellType.NUMERIC);
						ce.setCellValue(Integer.valueOf(infoSar[i][0].toString()));
					}
					if (j == 1) {
						ce.setCellType(CellType.STRING);
						ce.setCellValue(infoSar[i][1].toString());
					}

					if (j == 2) {
						ce.setCellType(CellType.STRING);
						ce.setCellValue(infoSar[i][2].toString());
					}
					if (j == 3) {
						ce.setCellType(CellType.NUMERIC);
						ce.setCellValue(Integer.valueOf(infoSar[i][3].toString()));
					}

					if (j == 4) {
						ce.setCellType(CellType.NUMERIC);
						ce.setCellValue(Double.parseDouble(infoSar[i][4].toString()));
					}
					if (j == 5) {

						ce.setCellType(CellType.NUMERIC);
						ce.setCellValue(Double.parseDouble(infoSar[i][5].toString()));
					}
					if (j == 6) {
						ce.setCellType(CellType.NUMERIC);
						ce.setCellValue(Double.parseDouble(infoSar[i][6].toString()));
					}
					if (j == 7) {
						ce.setCellType(CellType.STRING);
						ce.setCellValue(infoSar[i][7].toString());
					}

				}
			}

			FileOutputStream file = new FileOutputStream(excel);
			workbook.write(file);
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int nakamaisInd(File excel) {
		int nummurs = 0;
		FileInputStream file;
		try {
			file = new FileInputStream(excel);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row ro = sheet.getRow(sheet.getLastRowNum());
			Cell ce = ro.getCell(ro.getFirstCellNum());
			nummurs = (int) ce.getNumericCellValue() + 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nummurs;
	}

	public static void crateFile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void makeinfoSar() {
		infoSar = new Object[data.size()][];
		{
			for (int i = 0; i < data.size(); i++) {
				Data newData = data.get(i);
				infoSar[i] = newData.precesInd();
			}
		}

	}

	public static void makefullList() {
		fullList = new Object[data.size()][];
		{

			for (int i = 0; i < data.size(); i++) {
				Data newData = data.get(i);
				fullList[i] = newData.convertToTableRowFIVE();
				int test = (Integer) fullList[i][3];
				if (test != 0) {
					fullCell++;
				} else {
					eamptyCell++;
				}
			}
		}

	}

	public static void makefullSmallList() {
		fullSmallList = new Object[data.size()][];
		{

			for (int i = 0; i < data.size(); i++) {
				Data newData = data.get(i);
				fullSmallList[i] = newData.convertToTableRowSmall();
			}
		}

	}

	public static void noExcel() {
		try {
			FileInputStream file = new FileInputStream(excel);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Data e = new Data();
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);
					if (j == 0) {
						ce.setCellType(CellType.NUMERIC);
						int temp = (int) ce.getNumericCellValue();
						e.setnr(temp);
					}
					if (j == 1) {
						ce.setCellType(CellType.STRING);
						e.setkods(ce.getStringCellValue());
					}

					if (j == 2) {
						ce.setCellType(CellType.STRING);
						e.setnosaukums(ce.getStringCellValue());
					}
					if (j == 3) {
						ce.setCellType(CellType.NUMERIC);
						int temp = (int) ce.getNumericCellValue();
						e.setatlikums(temp);
					}
					if (j == 4) {
						ce.setCellType(CellType.NUMERIC);
						e.setiepCena(ce.getNumericCellValue());
					}
					if (j == 5) {
						ce.setCellType(CellType.NUMERIC);
						e.setparCena(ce.getNumericCellValue());
					}
					if (j == 6) {
						ce.setCellType(CellType.NUMERIC);
						e.setreaCena(ce.getNumericCellValue());
					}
					if (j == 7) {
						ce.setCellType(CellType.STRING);
						e.setpiegad(ce.getStringCellValue());
					}

				}
				data.add(e);
			}

			/*
			 * for (Data emp : data) { System.out.println("Nr " + emp.getnr()); }
			 */

			file.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	public static void noExcelVeikali() {
		try {
			veikali.clear();
			FileInputStream file = new FileInputStream(excelShops);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			veikaluNos = new String[sheet.getLastRowNum()];
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {	
				String[] shop = new String[2];
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);

					if (j == 0) {
						ce.setCellType(CellType.STRING);
						shop[j] = ce.getStringCellValue();
						veikaluNos[i-1]=ce.getStringCellValue();
					}
					if (j == 1) {
						ce.setCellType(CellType.STRING);
						shop[j] = ce.getStringCellValue();
					}
					
				}
				veikali.add(shop);				
			}
			file.close();
			workbook.close();
		} catch (FileNotFoundException e1) {

			// TODO Auto-generated catch block
			System.out.println(e1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	public static void noExcelKartinas() {
		try {
			FileInputStream file = new FileInputStream(excelPartners);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Kartinas kar = new Kartinas();
				Row ro = sheet.getRow(i);
				for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
					Cell ce = ro.getCell(j);

					if (j == 0) {
						// If you have Header in text It'll throw exception because it won't get
						// NumericValue
						ce.setCellType(CellType.STRING);
						kar.setnosaukums(ce.getStringCellValue());
					}
					if (j == 1) {
						ce.setCellType(CellType.STRING);
						kar.setregNr(ce.getStringCellValue());
					}

					if (j == 2) {
						ce.setCellType(CellType.STRING);
						kar.setPVNNr(ce.getStringCellValue());
					}
					if (j == 3) {
						// If you have Header in text It'll throw exception because it won't get
						// NumericValue
						ce.setCellType(CellType.STRING);
						kar.setjurAdr(ce.getStringCellValue());
					}

					if (j == 4) {
						ce.setCellType(CellType.STRING);
						kar.setfakAdr(ce.getStringCellValue());
					}
					if (j == 5) {
						// If you have Header in text It'll throw exception because it won't get
						// NumericValue
						ce.setCellType(CellType.STRING);
						kar.setbankasNos(ce.getStringCellValue());
					}
					if (j == 6) {
						ce.setCellType(CellType.STRING);
						kar.setbankasKods(ce.getStringCellValue());
					}
					if (j == 7) {
						ce.setCellType(CellType.STRING);
						kar.setbankasKonts(ce.getStringCellValue());
					}
				}
				kartinas.add(kar);
			}
			file.close();
		} catch (FileNotFoundException e1) {

			// TODO Auto-generated catch block
			System.out.println(e1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	public static void incNolNr() {
		incNextNolNumber();
		nextNolNr = nextNolNumber(numberNolFile);
		String paddedNolNumber = String.format("%04d",nextNolNr);
		nextNolName = "NOL-" + paddedNolNumber;
		nextNOL = pavadPath.resolve(nextNolName + ".xlsx").toFile();
	}
	
	
	public static void restart() {
		
		
		infoSar = null;
		fullList = null;
		fullSmallList = null;
		fullCell = 0;
		eamptyCell = 0;
		data = null;
		data = new ArrayList<>();
		mainframe.dispose();
		noExcel();
		noExcelVeikali();
		makeinfoSar();
		makefullList();
		makefullSmallList();
		mainframe = new JFrame("Program");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TabbedPane tabbedpane = new TabbedPane();
		mainframe.add(tabbedpane, BorderLayout.CENTER);
		mainframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainframe.pack();
		mainframe.setVisible(true);
	}

	public static class Data {
		private int nr;
		private String kods;
		private String nosaukums;
		private int atlikums;
		private double iepCena;
		private double parCena;
		private double reaCena;
		private String piegad;

		public Data() {
		}

		public Data(int nr, String kods, String nosaukums, int atlikums, Double iepCena, Double parCena, Double reaCena,
				String piegad) {
			super();
			this.nr = nr;
			this.kods = kods;
			this.nosaukums = nosaukums;
			this.atlikums = atlikums;
			this.iepCena = iepCena;
			this.parCena = parCena;
			this.reaCena = reaCena;
			this.piegad = piegad;
		}

		public int getnr() {
			return nr;
		}

		public String getkods() {
			return kods;
		}

		public String getnosaukums() {
			return nosaukums;
		}

		public int getatlikums() {
			return atlikums;
		}

		public double getiepCena() {
			return iepCena;
		}

		public double getparCena() {
			return parCena;
		}

		public double getreaCena() {
			return reaCena;
		}

		public String getpiegad() {
			return piegad;
		}

		public void setnr(int nr) {
			this.nr = nr;
		}

		public void setkods(String kods) {
			this.kods = kods;
		}

		public void setnosaukums(String nosaukums) {
			this.nosaukums = nosaukums;
		}

		public void setatlikums(int atlikums) {
			this.atlikums = atlikums;
		}

		public void setiepCena(double iepCena) {
			this.iepCena = iepCena;
		}

		public void setparCena(double parCena) {
			this.parCena = parCena;
		}

		public void setreaCena(double reaCena) {
			this.reaCena = reaCena;
		}

		public void setpiegad(String piegad) {
			this.piegad = piegad;
		}

		public Object[] precesInd() {
			Object[] row = new Object[8];

			row[0] = nr;
			row[1] = kods;
			row[2] = nosaukums;
			row[3] = atlikums;
			row[4] = iepCena;
			row[5] = parCena;
			row[6] = reaCena;
			row[7] = piegad;

			return row;
		}

		public Object[] convertToTableRowFIVE() {
			Object[] row = new Object[11];

			row[0] = false;
			row[1] = kods;
			row[2] = nosaukums;
			row[3] = atlikums;
			row[4] = iepCena;
			row[5] = Math.round(iepCena * atlikums * 100.0) / 100.0; // iepSumma
			row[6] = parCena;
			row[7] = Math.round(parCena * atlikums * 100.0) / 100.0; // parSumma
			row[8] = reaCena;
			row[9] = Math.round(reaCena * atlikums * 100.0) / 100.0; // reaSumma
			row[10]= piegad;

			return row;
		}

		public Object[] convertToTableRowSmall() {
			Object[] row = new Object[10];

			row[0] = kods;
			row[1] = nosaukums;
			row[2] = parCena;
			row[3] = reaCena;
			row[4] = piegad;
			return row;
		}

	}

	public static class Kartinas {
		private String nosaukums;
		private String regNr;
		private String PVNNr;
		private String jurAdr;
		private String fakAdr;
		private String bankasNos;
		private String bankasKods;
		private String bankasKonts;

		public Kartinas() {
		}

		public Kartinas(String nosaukums, String regNr, String PVNNr, String jurAdr, String fakAdr, String bankasNos,
				String bankasKods, String bankasKonts) {
			super();
			this.nosaukums = nosaukums;
			this.regNr = regNr;
			this.PVNNr = PVNNr;
			this.jurAdr = jurAdr;
			this.fakAdr = fakAdr;
			this.bankasNos = bankasNos;
			this.bankasKods = bankasKods;
			this.bankasKonts = bankasKonts;
		}

		public String getnosaukums() {
			return nosaukums;
		}

		public String getregNr() {
			return regNr;
		}

		public String getPVNNr() {
			return PVNNr;
		}

		public String getjurAdr() {
			return jurAdr;
		}

		public String getfakAdr() {
			return fakAdr;
		}

		public String getbankasNos() {
			return bankasNos;
		}

		public String getbankasKods() {
			return bankasKods;
		}

		public String getbankasKonts() {
			return bankasKonts;
		}

		public void setnosaukums(String nosaukums) {
			this.nosaukums = nosaukums;
		}

		public void setregNr(String regNr) {
			this.regNr = regNr;
		}

		public void setPVNNr(String PVNNr) {
			this.PVNNr = PVNNr;
		}

		public void setjurAdr(String jurAdr) {
			this.jurAdr = jurAdr;
		}

		public void setfakAdr(String fakAdr) {
			this.fakAdr = fakAdr;
		}

		public void setbankasNos(String bankasNos) {
			this.bankasNos = bankasNos;
		}

		public void setbankasKods(String bankasKods) {
			this.bankasKods = bankasKods;
		}

		public void setbankasKonts(String bankasKonts) {
			this.bankasKonts = bankasKonts;
		}

		public String[] kartinuSar() {
			String[] row = new String[8];

			row[0] = nosaukums;
			row[1] = regNr;
			row[2] = PVNNr;
			row[3] = jurAdr;
			row[4] = fakAdr;
			row[5] = bankasNos;
			row[6] = bankasKods;
			row[7] = bankasKonts;

			return row;
		}

	}

	public static String[] kartIzv() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 25;
		c.ipadx = 25;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		JLabel lb = new JLabel("Ievadiet nepieciešamos datus: (Ievadīt - ja tukšums)");
		panel.add(lb, c);

		JLabel lbpartneraNos = new JLabel("Nosaukums:");
		c.gridwidth = 1;
		c.gridy = 1;
		panel.add(lbpartneraNos, c);

		JLabel lbregNr = new JLabel("Reģistrācijas nr:");
		c.gridy = 2;
		panel.add(lbregNr, c);

		JLabel lbPVNNr = new JLabel("PVN nr:");
		c.gridy = 3;
		panel.add(lbPVNNr, c);

		JLabel lbjurAdr = new JLabel("Juridiskā adrese:");
		c.gridy = 4;
		panel.add(lbjurAdr, c);

		JLabel lbfakAdr = new JLabel("Faktiskā adrese:");
		c.gridy = 5;
		panel.add(lbfakAdr, c);

		JLabel lbbankasNos = new JLabel("Bankas nosaukums:");
		c.gridy = 6;
		panel.add(lbbankasNos, c);

		JLabel lbbankasKods = new JLabel("Bankas kods:");
		c.gridy = 7;
		panel.add(lbbankasKods, c);

		JLabel lbbankasKonts = new JLabel("Bankas Konts:");
		c.gridy = 8;
		panel.add(lbbankasKonts, c);

		JTextField partneraNos = new JTextField(10);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(partneraNos, c);

		JTextField regNr = new JTextField(10);
		c.gridy = 2;
		panel.add(regNr, c);

		JTextField PVNNr = new JTextField(10);
		c.gridy = 3;
		panel.add(PVNNr, c);

		JTextField jurAdr = new JTextField(10);
		c.gridy = 4;
		panel.add(jurAdr, c);

		JTextField fakAdr = new JTextField(10);
		c.gridy = 5;
		panel.add(fakAdr, c);

		JTextField bankasNos = new JTextField(10);
		c.gridy = 6;
		panel.add(bankasNos, c);

		JTextField bankasKods = new JTextField(10);
		c.gridy = 7;
		panel.add(bankasKods, c);

		JTextField bankasKonts = new JTextField(10);
		c.gridy = 8;
		panel.add(bankasKonts, c);

		String[] options = { "Apstiprināt", "Atcelt" };

		String[] kartina = new String[8];

		int g;
		g = JOptionPane.showOptionDialog(null, panel, "Kartiņas izveide", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (g == 0) { // Ja Apstiprinats

			kartina[0] = partneraNos.getText();
			kartina[1] = regNr.getText();
			kartina[2] = PVNNr.getText();
			kartina[3] = jurAdr.getText();
			kartina[4] = fakAdr.getText();
			kartina[5] = bankasNos.getText();
			kartina[6] = bankasKods.getText();
			kartina[7] = bankasKonts.getText();

		}

		else {
			kartina = null;
		}

		return kartina;

	}

	public static void pievPreci() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 25;
		c.ipadx = 25;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		JLabel lb = new JLabel("Ievadiet nepieciešamos datus: (Ievadīt - ja tukšums)");
		panel.add(lb, c);

		JLabel lbkods = new JLabel("Kods:");
		c.gridwidth = 1;
		c.gridy = 1;
		panel.add(lbkods, c);

		JLabel lbnosaukums = new JLabel("Nosaukums:");
		c.gridy = 2;
		panel.add(lbnosaukums, c);

		JLabel lbskaits = new JLabel("Skaits:");
		c.gridy = 3;
		panel.add(lbskaits, c);

		JLabel lbiepCena = new JLabel("Iepirkšanas cena:");
		c.gridy = 4;
		panel.add(lbiepCena, c);

		JLabel lbparCena = new JLabel("Pārdošanas cena:");
		lbparCena.setVisible(Noliktava.showParCena);
		c.gridy = 5;
		panel.add(lbparCena, c);

		JLabel lbreaCena = new JLabel("Realizācijas cena:");
		c.gridy = 6;
		panel.add(lbreaCena, c);

		JLabel lbpiegadatajs = new JLabel("Piegādātājs:");
		c.gridy = 7;
		panel.add(lbpiegadatajs, c);

		JTextField kods = new JTextField(10);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(kods, c);

		JTextField nosaukums = new JTextField(10);
		nosaukums.setEditable(false);
		c.gridy = 2;
		panel.add(nosaukums, c);

		JTextField skaits = new JTextField();
		skaits.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // ignore event
				}
			}
		});
		c.gridy = 3;
		panel.add(skaits, c);

		JTextField iepCena = new JTextField(10);
		iepCena.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && c != '.') {
					e.consume(); // ignore event
				}
			}
		});
		iepCena.setEditable(false);
		c.gridy = 4;
		panel.add(iepCena, c);

		JTextField parCena = new JTextField(10);
		parCena.setVisible(Noliktava.showParCena);
		if(showParCena) {
		parCena.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && c != '.') {
					e.consume(); // ignore event
				}
			}
		});
		
		parCena.setEditable(false);
		c.gridy = 5;
		panel.add(parCena, c);
		}
		JTextField reaCena = new JTextField(10);
		reaCena.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && c != '.') {
					e.consume(); // ignore event
				}
			}
		});
		reaCena.setEditable(false);
		c.gridy = 6;
		panel.add(reaCena, c);

		JTextField piegadatajs = new JTextField(10);
		piegadatajs.setEditable(false);
		c.gridy = 7;
		panel.add(piegadatajs, c);

		kods.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				searchKods();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				searchKods();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				searchKods();
			}

			public void searchKods() {
				String test = kods.getText();
				boolean b = false;
				for (int i = 0; i < data.size(); i++) {

					if (test.equals(infoSar[i][1])) {
						nosaukums.setText((String) infoSar[i][2]);
						nosaukums.setEditable(false);
						piegadatajs.setText((String) infoSar[i][7]);
						piegadatajs.setEditable(false);
						iepCena.setEditable(true);
							if(showParCena) {
						parCena.setEditable(true);
											}
						reaCena.setEditable(true);
						b = true;
					}

				}
				if (!b) {
					iepCena.setEditable(false);
					if(showParCena) {
					parCena.setEditable(false);
					}
					reaCena.setEditable(false);
					piegadatajs.setText("");
					piegadatajs.setEditable(true);
					nosaukums.setText("");
					nosaukums.setEditable(true);
				}

			}
		});
		nosaukums.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				testEmpty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				testEmpty();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				testEmpty();
			}

			public void testEmpty() {
				String test = nosaukums.getText();
				boolean b = false;

				if (test.length() > 0) {
					b = true;
					iepCena.setEditable(true);
					if(showParCena) {
					parCena.setEditable(true);
					}
					reaCena.setEditable(true);
				}

				if (!b) {
					iepCena.setEditable(false);
					if(showParCena) {
					parCena.setText("");
					parCena.setEditable(false);
					}
					reaCena.setEditable(false);
					iepCena.setText("");					
					reaCena.setText("");
				}

			}
		});

		String[] options = { "Apstiprināt", "Atcelt" };

		Data jaunaPrece = new Data();
		int g2 = 0;
		int g;
		g = JOptionPane.showOptionDialog(null, panel, "Jaunas preces pievienošana", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (g == 0) { // Ja Apstiprinats
			boolean parLeangth = true;
			if(showParCena) {
				if(parCena.getText().length() > 0) {
					parLeangth = true;
				}
				else {
					parLeangth = false;
				}
			}
			else {
				parLeangth = true;
			}
			if (kods.getText().length() > 0 && nosaukums.getText().length() > 0 && skaits.getText().length() > 0
					&& iepCena.getText().length() > 0 && parLeangth
					&&  reaCena.getText().length() > 0 && piegadatajs.getText().length() > 0 && Integer.parseInt(skaits.getText())>0) {
				jaunaPrece.setnr(0);
				jaunaPrece.setkods(kods.getText());
				jaunaPrece.setnosaukums(nosaukums.getText());
				jaunaPrece.setatlikums(Integer.parseInt(skaits.getText()));
				jaunaPrece.setiepCena((double) Math.round(Double.parseDouble(iepCena.getText()) * 100) / 100);
				if(Noliktava.showParCena) {					
					jaunaPrece.setparCena((double) Math.round(Double.parseDouble(parCena.getText()) * 100) / 100);
				}
				else {
					jaunaPrece.setparCena((double) Math.round(Double.parseDouble(reaCena.getText()) * 100) / 100);
				}
				
				jaunaPrece.setreaCena((double) Math.round(Double.parseDouble(reaCena.getText()) * 100) / 100);
				jaunaPrece.setpiegad(piegadatajs.getText());
				int vaiJauns = 0;				
				boolean b = false;
				int atmCount = data.size();
				for (int i = 0; i < atmCount; i++) {
					String test = jaunaPrece.getkods();
					if (test.equals(infoSar[i][1])) {						
						if (jaunaPrece.getiepCena() == (double) infoSar[i][4]
								&& jaunaPrece.getparCena() == (double) infoSar[i][5]

								&& jaunaPrece.getreaCena() == (double) infoSar[i][6]) {
							Data rewrite = new Data();
							rewrite.setnr((int) infoSar[i][0]);
							rewrite.setkods((String) infoSar[i][1]);
							rewrite.setnosaukums((String) infoSar[i][2]);
							rewrite.setatlikums(jaunaPrece.getatlikums()+(int) infoSar[i][3]);
							rewrite.setiepCena((double) infoSar[i][4]);
							rewrite.setparCena((double) infoSar[i][5]);
							rewrite.setreaCena((double) infoSar[i][6]);
							rewrite.setpiegad((String) infoSar[i][7]);
							data.set(i, rewrite);
							toExcel(excel, data);
							restart();
							break;

						} else {
							vaiJauns++;
							if (vaiJauns == data.size() && !b) {
								jaunaPrece.setnr(nakamaisInd(excel));
								data.add(jaunaPrece);
								toExcel(excel, data);
								restart();
								b = true;
							}
						}

					} else {
						vaiJauns++;
						if (vaiJauns == data.size() && !b) {
							jaunaPrece.setnr(nakamaisInd(excel));
							data.add(jaunaPrece);
							toExcel(excel, data);
							b = true;
							restart();
						}
					}
				}

			} // ends if
			
		}

	}
	public static void noslegt() { 
		Path thisPath = path.resolve("Noslegts");
		File f = thisPath.toFile();
		f.mkdirs();
		int g;
		String[] options = {"Apstiprināts", "Atcelt"};
		JPanel panel = new JPanel();
		JLabel lb = new JLabel("Ievadiet faila nosaukumu:");
		JTextField nos = new JTextField(10);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		panel.add(lb,c);
		c.gridx = 1;
		panel.add(nos,c);
		g = JOptionPane.showOptionDialog(null, panel, "Mēneša nobeigšana", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (g==0) {
			if(nos.getText().length()>0) {
				f = thisPath.resolve(nos.getText()+".xlsx").toFile();
				crateExcel(f);
				toExcel(f,data);
				JFrame frame = new JFrame("Uzmanību!");
				JOptionPane.showMessageDialog(frame, "Fails " + nos.getText() + ".xlsx tika veiksmīgi izveidots");
			}
			else {
				JFrame frame = new JFrame("Uzmanību!");
				JOptionPane.showMessageDialog(frame, "Fails netika izveidots, jo netika ievadīts tā nosaukums!");
			}
				
		}
		}
	public static void copyFileFromTo(File source, File dest)   {

        InputStream input = null;
        OutputStream output = null;

        try {
            int buf_size=1024;
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            //System.out.println("Size of the file :"+input.available()+" Byte");
            byte[] buf = new byte[buf_size];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0 ) {
            output.write(buf, 0, bytesRead);
            if(input.available()<buf_size){
                //System.out.println("Availble byte now is : "+input.available()+" so change the size of the array byte the to the same size");
                //if the available byte are <1024  you will copy  a array of 1024 to the file that cause domage to file
                buf= new byte[input.available()];
            }
            }                               
            input.close();
            output.close();
        }

        catch (IOException e) { 
        JOptionPane.showMessageDialog(null, e.getMessage() ); 
        System.exit(-1); 
     } 

} 
	public static void crateExcel(File f) {
		FileOutputStream output;
		String[] virs = {"Nr","Kods", "Nosaukums", "Atlikums", "Iepirkšanas cena", "Pārdošanas Cena", "Realizācijas cena", "Piegadatajs"};
		try {	
			output = new FileOutputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("sheet 1");
			for (int i = 0; i<=data.size();i++) {				
				Row re = sheet.createRow(i);
				for (int j = 0; j < 8;j++) {
					Cell cell = re.createCell(j);	
					if (i == 0) {
						re = sheet.getRow(i);
						cell = re.getCell(j);
						cell.setCellType(CellType.STRING);
						cell.setCellValue(virs[j]);
					}
				}
			}
			workbook.write(output);
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static double roundToTwo(double d) {
		d = d * 100;
		d = Math.round(d);
		d = d/100;
		return d;
	}
	public static void fromTable(TableModel model) {
		izvPreces.clear();
		int izvSkaits = 0;
		for (int i = 0; i < model.getRowCount();i++) {
			boolean izv = (boolean) model.getValueAt(i, 0);
			if (izv) {
				Object[] izvPrece = new Object[7];
				if(showParCena) {				
				izvPrece[0] = i; // index
				izvPrece[1] = model.getValueAt(i,1); //kods
				izvPrece[2] = model.getValueAt(i,2); //nosaukums
				izvPrece[3] = model.getValueAt(i,3); //atlikums
				izvPrece[4] = model.getValueAt(i,6); //parCena
				izvPrece[5] = model.getValueAt(i,8); //reaCena
				izvPrece[6] = model.getValueAt(i,4); //iepCena prieks salidzinasanas
				izvPreces.add(izvPrece);
				izvSkaits++;
				}
				else {
					izvPrece[0] = i; // index
					izvPrece[1] = model.getValueAt(i,1); //kods
					izvPrece[2] = model.getValueAt(i,2); //nosaukums
					izvPrece[3] = model.getValueAt(i,3); //atlikums
					izvPrece[4] = model.getValueAt(i,6); //parCena
					izvPrece[5] = model.getValueAt(i,6); //reaCena
					izvPrece[6] = model.getValueAt(i,4); //iepCena prieks salidzinasanas
					izvPreces.add(izvPrece);
					izvSkaits++;
				}
			}									
		}
		if (izvSkaits>0) {			
		/*	int g = -1;
			String[] options = {"Pārvietot","Pārdot"};
			JPanel panel = new JPanel();
			JLabel lb = new JLabel("Darbība ar izvēlētām precēm:");
			panel.add(lb);
			g = JOptionPane.showOptionDialog(null, panel, "Darbība ar precēm", JOptionPane.YES_NO_OPTION, //Izvele starp "Parvietot" un  "Pardot", !pievienot if(g==1) {}
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);			
			if(g==0) {	*/			
				TabbedPane.relocmodel.setRowCount(0);
				for(int i = 0; i <izvPreces.size();i++) {
					Object[] temp = new Object[8];
					temp[0] = i + 1;
					temp[1] = false;
					temp[2] = izvPreces.get(i)[1].toString();
					temp[3] = izvPreces.get(i)[2].toString();
					temp[4] = 0;
					temp[5] = izvPreces.get(i)[3].toString();
					temp[6] = izvPreces.get(i)[5].toString();
					temp[7] = Double.parseDouble(temp[6].toString())*Integer.parseInt(temp[4].toString());
					TabbedPane.relocmodel.addRow(temp);
				}
				
				//try to open 3rd tab
				try {					
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_ALT);
					r.keyPress(KeyEvent.VK_3);
					r.keyRelease(KeyEvent.VK_ALT);
					r.keyRelease(KeyEvent.VK_3);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//}			
			
		}
	}
	
	public static JFrame mainframe;
	public static Path path;
	public static File nextNOL;
	public static File NOLexampleFile;
	public static File excelName;
	public static File excel;
	public static File excelPartners;
	public static File excelShops;
	public static String excelFileName;
	public static Path pavadPath;
	public static Path examplePath;
	public static Path excelPath;
	public static Path excelPartnerPath;
	public static Path excelShopPath;
	public static Path desktopPath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().toPath();
	public static File numberNolFile;
	public static int nextNolNr;
	public static String nextNolName;
	public static ArrayList<Data> data = new ArrayList<Data>();
	public static ArrayList<Kartinas> kartinas = new ArrayList<>();
	public static ArrayList<String[]> veikali = new ArrayList<>();
	public static String[] veikaluNos;
	public static int fullCell;
	public static int eamptyCell;
	public static Object[][] infoSar;
	public static Object[][] fullList;
	public static Object[][] fullSmallList;
	public static ArrayList<Object[]> izvPreces = new ArrayList<>();
	public static ArrayList<Object[]> neizvPreces = new  ArrayList<>();
	
	public static boolean showParCena = false;
	
	
	

	public static void main(String[] args) {
		String tempPath = desktopPath.toString();
		desktopPath = Paths.get(tempPath, "Izveidotās pavadzīmes");		
		try {
			Path prog = Paths.get(Noliktava.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			path = prog.getParent();
			// System.out.println(path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		excelName = path.resolve("excelName.txt").toFile();
		numberNolFile = path.resolve("nextNolNumber.txt").toFile();

		nextNolNr = nextNolNumber(numberNolFile);
		String paddedNolNumber = String.format("%04d",nextNolNr);
		nextNolName = "NOL-" + paddedNolNumber;
	
		
		excelFileName = readName(excelName);
		examplePath = Paths.get(path.toString() + "\\Piemeri");
		NOLexampleFile = examplePath.toFile();
		NOLexampleFile.mkdirs();
		NOLexampleFile = examplePath.resolve("NOL.xlsx").toFile();
		
		pavadPath = Paths.get(path.toString()+"\\IzveidotasPavadzimes");
		nextNOL = pavadPath.toFile();
		nextNOL.mkdirs();
		nextNOL = pavadPath.resolve(nextNolName + ".xlsx").toFile();
		
		excelPath = Paths.get(path.toString() + "\\excel\\Preces");
		excelPartnerPath = Paths.get(path.toString(), "\\excel\\Kartinas");		
		excelPartners = excelPartnerPath.toFile();
		excel = excelPath.toFile();
		excel.mkdirs();
		excelPartners.mkdirs();
		excel = excelPath.resolve(excelFileName).toFile();
		excelPartnerPath = Paths.get(path.toString(), "\\excel\\Kartinas\\Kartinas.xlsx");
		excelShopPath = Paths.get(path.toString(), "\\excel\\Kartinas\\Veikali.xlsx");
		excelShops = excelShopPath.toFile();
		excelPartners = excelPartnerPath.toFile();		
		noExcel();
		noExcelKartinas();
		noExcelVeikali();
		makeinfoSar();
		makefullList();
		makefullSmallList();
		mainframe = new JFrame("Program");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add content to the window.
		TabbedPane tabbedpane = new TabbedPane();
		mainframe.add(tabbedpane, BorderLayout.CENTER);
		// MenuLook menu = new MenuLook();
		// Display the window.
		//TabbedPane.relocmodel.addRow(new Object[]);
		mainframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainframe.pack();
		mainframe.setVisible(true);
		
	}
}
