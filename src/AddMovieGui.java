import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;





import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.SwingConstants;

import java.awt.Font;


public class AddMovieGui extends JDialog implements ActionListener, ItemListener, FocusListener{

	JTextField textMovieName;
	JButton btnSelectPath;
	JButton btnOK;
	JButton btnOKEdit;
	static File file;
	JComboBox comboSeries;
	JComboBox comboResolution;
	JComboBox comboDownResolution;
	JCheckBox chbxUnavailable;
	JCheckBox chbxDownloaded;
	JCheckBox chbxDownloading;
	static int index;
	private JTextField seriesid;
	JTextField releaseDate;

	public AddMovieGui() {
		super(Main.x);
		setModal (true);
		setTitle("Tainiotita - Add Movie");
		URL iconURL = getClass().getResource("resources/Tainiotita.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400,305);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 394, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Movie Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 41, 152, 14);
		panel.add(lblNewLabel);
		
		textMovieName = new JTextField();
		textMovieName.setBounds(169, 38, 179, 20);
		panel.add(textMovieName);
		textMovieName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Movie Series");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 69, 152, 14);
		panel.add(lblNewLabel_1);
		
		comboSeries = new JComboBox(removeDuplicates((String[]) PublicValues.movieSeries.toArray(new String[0])));
		comboSeries.setEditable(true);
		comboSeries.setBounds(169, 66, 179, 20);
		comboSeries.setSelectedItem("");
		panel.add(comboSeries);
		
		JLabel lblNewLabel_2 = new JLabel("Available Resolution");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 97, 152, 14);
		panel.add(lblNewLabel_2);
		
		comboResolution = new JComboBox(getResolutions());
		comboResolution.setEditable(true);
		comboResolution.setBounds(169, 94, 179, 20);
		comboResolution.setEnabled(false);
		panel.add(comboResolution);
		
		chbxDownloaded = new JCheckBox("Downloaded");
		chbxDownloaded.setHorizontalAlignment(SwingConstants.CENTER);
		chbxDownloaded.setBounds(0, 153, 131, 23);
		chbxDownloaded.addItemListener(this);
		panel.add(chbxDownloaded);
		
		chbxDownloading = new JCheckBox("Downloading");
		chbxDownloading.setHorizontalAlignment(SwingConstants.CENTER);
		chbxDownloading.setBounds(131, 153, 131, 23);
		chbxDownloading.addItemListener(this);
		panel.add(chbxDownloading);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(152, 229, 89, 23);
		btnOK.addActionListener(this);
		panel.add(btnOK);
		
		btnSelectPath = new JButton("Select Movie");
		btnSelectPath.setBounds(139, 195, 115, 23);
		btnSelectPath.addActionListener(this);
		panel.add(btnSelectPath);
		
		JLabel lblDownloadingResolution = new JLabel("Downloading Resolution");
		lblDownloadingResolution.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownloadingResolution.setBounds(10, 125, 152, 14);
		panel.add(lblDownloadingResolution);
		
		comboDownResolution = new JComboBox(getResolutions());
		comboDownResolution.setEditable(true);
		comboDownResolution.setBounds(169, 122, 179, 20);
		comboDownResolution.setEnabled(false);
		panel.add(comboDownResolution);
		
		seriesid = new JTextField();
		seriesid.setToolTipText("The number of this movie in the series");
		seriesid.setHorizontalAlignment(SwingConstants.CENTER);
		seriesid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		seriesid.setText("#");
		seriesid.setBounds(353, 66, 35, 20);
		panel.add(seriesid);
		seriesid.setColumns(10);
		seriesid.addFocusListener(this);
		
		chbxUnavailable = new JCheckBox("Unavailable");
		chbxUnavailable.addItemListener(this);
		chbxUnavailable.setHorizontalAlignment(SwingConstants.CENTER);
		chbxUnavailable.setBounds(265, 153, 131, 23);
		panel.add(chbxUnavailable);
		
		releaseDate = new JTextField();
		releaseDate.setEnabled(false);
		releaseDate.setFont(new Font("Tahoma", Font.ITALIC, 11));
		releaseDate.setHorizontalAlignment(SwingConstants.CENTER);
		releaseDate.setText("Release date");
		releaseDate.setBounds(287, 180, 86, 20);
		panel.add(releaseDate);
		releaseDate.setColumns(10);
		releaseDate.addFocusListener(this);
		
			
		setVisible(true);
	}


	public AddMovieGui(Object movieSelected) {
		setModal (true);
		setAlwaysOnTop (true);
		index = PublicValues.movieNames.indexOf(movieSelected);
		setTitle("Tainiotita - Edit Movie");
		URL iconURL = getClass().getResource("resources/Tainiotita.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400,305);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 394, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Movie Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 41, 152, 14);
		panel.add(lblNewLabel);
		
		textMovieName = new JTextField();
		textMovieName.setText((String) getInfo(movieSelected)[0]);
		textMovieName.setBounds(169, 38, 179, 20);
		panel.add(textMovieName);
		textMovieName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Movie Series");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 69, 152, 14);
		panel.add(lblNewLabel_1);
		
		comboSeries = new JComboBox(removeDuplicates((String[]) PublicValues.movieSeries.toArray(new String[0])));
		comboSeries.setEditable(true);
		if (getInfo(movieSelected)[1].toString().equals(" ")){
			comboSeries.setSelectedItem("");
		}else{
			if(getInfo(movieSelected)[1].toString().contains("#")){
				comboSeries.setSelectedItem(getInfo(movieSelected)[1].toString().substring(0, getInfo(movieSelected)[1].toString().indexOf('#')-1));
			}else if(getInfo(movieSelected)[1].toString().contains("#")){
				comboSeries.setSelectedItem(getInfo(movieSelected)[1].toString().substring(0, getInfo(movieSelected)[1].toString().indexOf('#')-1));
			}
		}
		comboSeries.setBounds(169, 66, 179, 20);
		panel.add(comboSeries);
		
		JLabel lblNewLabel_2 = new JLabel("Available Resolution");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 97, 152, 14);
		panel.add(lblNewLabel_2);
		
		comboResolution = new JComboBox(getResolutions());
		comboResolution.setEditable(true);
		comboResolution.setBounds(169, 94, 179, 20);
		comboResolution.setEnabled(false);
		if(!(getInfo(movieSelected)[2].toString().contains("--->"))){
			if(getInfo(movieSelected)[2].toString().equals("~")){
				comboResolution.setSelectedItem("");
			}else{
				comboResolution.setSelectedItem(getInfo(movieSelected)[2]);
			}
		}else{
			comboResolution.setSelectedItem(removeArrows(getInfo(movieSelected)[2],1));
		}
		panel.add(comboResolution);
		
		
		btnOKEdit = new JButton("OK");
		btnOKEdit.setBounds(152, 229, 89, 23);
		btnOKEdit.addActionListener(this);
		panel.add(btnOKEdit);
		
		btnSelectPath = new JButton("Select Movie");
		btnSelectPath.setBounds(139, 195, 115, 23);
		btnSelectPath.addActionListener(this);
		panel.add(btnSelectPath);

		JLabel lblDownloadingResolution = new JLabel("Downloading Resolution");
		lblDownloadingResolution.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownloadingResolution.setBounds(10, 125, 152, 14);
		panel.add(lblDownloadingResolution);//
		
		comboDownResolution = new JComboBox(getResolutions());
		comboDownResolution.setEditable(true);
		comboDownResolution.setBounds(169, 122, 179, 20);
		comboDownResolution.setEnabled(false);
		if(!(getInfo(movieSelected)[2].toString().contains("--->"))){
			if(getInfo(movieSelected)[2].toString().equals("~")){
				comboDownResolution.setSelectedItem("");
			}else{
				comboDownResolution.setSelectedItem(getInfo(movieSelected)[2]);
			}
		}else{
			comboDownResolution.setSelectedItem(removeArrows(getInfo(movieSelected)[2],2));
		}
		panel.add(comboDownResolution);
		
		chbxDownloaded = new JCheckBox("Downloaded");
		chbxDownloaded.addItemListener(this);
		chbxDownloaded.setHorizontalAlignment(SwingConstants.CENTER);
		chbxDownloaded.setBounds(0, 153, 131, 23);
		panel.add(chbxDownloaded);

		chbxDownloading = new JCheckBox("Downloading");
		chbxDownloading.addItemListener(this);
		chbxDownloading.setHorizontalAlignment(SwingConstants.CENTER);
		chbxDownloading.setBounds(131, 153, 131, 23);
		panel.add(chbxDownloading);
		
		chbxUnavailable = new JCheckBox("Unavailable");
		chbxUnavailable.addItemListener(this);
		chbxUnavailable.setHorizontalAlignment(SwingConstants.CENTER);
		chbxUnavailable.setBounds(265, 153, 131, 23);
		panel.add(chbxUnavailable);
		
		releaseDate = new JTextField();
		releaseDate.setEnabled(false);
		releaseDate.setFont(new Font("Tahoma", Font.ITALIC, 11));
		releaseDate.setHorizontalAlignment(SwingConstants.CENTER);
		if (getInfo(movieSelected)[3].toString().contains("Unavailable")){
			releaseDate.setText(getInfo(movieSelected)[3].toString().substring(getInfo(movieSelected)[3].toString().indexOf('(')+1,getInfo(movieSelected)[3].toString().lastIndexOf(')')));
		}else{
			releaseDate.setText("Release date");
		}
		releaseDate.setBounds(287, 180, 86, 20);
		panel.add(releaseDate);
		releaseDate.setColumns(10);
		releaseDate.addFocusListener(this);
		
		if (getInfo(movieSelected)[3].equals("Available")){
			chbxDownloaded.setSelected(true);
		}
		if (getInfo(movieSelected)[3].equals("Downloading")){
			chbxDownloading.setSelected(true);
		}
		if (getInfo(movieSelected)[3].equals("Available+Downloading")){
			chbxDownloading.setSelected(true);
			chbxDownloaded.setSelected(true);
		}
		if (getInfo(movieSelected)[3].toString().contains("Unavailable")){
			chbxUnavailable.setSelected(true);
		}

		file = new File((String) getInfo(movieSelected)[4]);
	
		seriesid = new JTextField();
		seriesid.setToolTipText("The number of this movie in the series");
		seriesid.setHorizontalAlignment(SwingConstants.CENTER);
		seriesid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		if (getInfo(movieSelected)[1].toString().equals(" ")){
			seriesid.setText("#");
		}else{
			if(getInfo(movieSelected)[1].toString().contains("#")){
				seriesid.setText((getInfo(movieSelected)[1].toString().substring(getInfo(movieSelected)[1].toString().indexOf('#')+1)));
			}else if(getInfo(movieSelected)[1].toString().contains("#")){
				seriesid.setText((getInfo(movieSelected)[1].toString().substring(getInfo(movieSelected)[1].toString().indexOf('#')+1)));
			}
		}
		seriesid.setBounds(353, 66, 35, 20);
		panel.add(seriesid);
		seriesid.setColumns(10);
		seriesid.addFocusListener(this);
		
		setVisible(true);
	}



	public static String[] getResolutions() {
		String[] a = removeDuplicates((String[]) PublicValues.resolution.toArray(new String[0]));
		String[] b = new String[]{"Unknown","DVD","720p","1080p"};
		String[] c = new String[a.length+b.length];
		System.arraycopy(b,0,c,0,b.length);
		System.arraycopy(a, 0, c, b.length, a.length);
		return removeArrows(removeDuplicates(c));
	}
	
	
	public static String[] removeArrows(String[] x){		//AND Unavailable PLACEHOLDER ~
		Vector y = new Vector();
		for(int i = 0;i<x.length;i++){
			if(!(x[i].contains("--->")) && !(x[i].equals("~"))){
				y.add(x[i]);
			}
		}
		return (String[])(y.toArray(new String[0]));
	}
	public static String removeArrows(Object x,int firstSecond){
		String y = (String)x;
		if (firstSecond==2){
			int indexArrowEnd = y.indexOf('>');
			y = y.substring(indexArrowEnd+1);
		}else if(firstSecond ==1){
			int indexArrowStart = y.indexOf('-');
			y = y.substring(0,indexArrowStart);
		}
		return y.replace("--->","");
	}


	public static Object[] getInfo(Object movieSelected) {
		Object[] returnArray = new Object[5];
		returnArray[0] = movieSelected;
		returnArray[1] = PublicValues.movieSeries.get(index);
		returnArray[2] = PublicValues.resolution.get(index);
		returnArray[3] = PublicValues.availability.get(index);
		returnArray[4] = PublicValues.moviePath.get(index);
		return returnArray;
	}
	public static Object[] getInfo(Object movieSelected,int i) {
		Object[] returnArray = new Object[5];
		returnArray[0] = movieSelected;
		returnArray[1] = PublicValues.movieSeries.get(i);
		returnArray[2] = PublicValues.resolution.get(i);
		returnArray[3] = PublicValues.availability.get(i);
		returnArray[4] = PublicValues.moviePath.get(i);
		return returnArray;
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == btnSelectPath){
			JFileChooser fc = new JFileChooser();
			if(file==null){
				fc.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
			}else{
				fc.setCurrentDirectory(file);
			}
			int returnVal = fc.showOpenDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	           file = fc.getSelectedFile();
	        }
		}
		if(source==btnOK){
		if(!(textMovieName.getText().equals("")) && !(comboResolution.getSelectedItem()==null) && (chbxDownloading.isSelected() || chbxDownloaded.isSelected())){
			if((file!=null || (file==null && chbxDownloading.isSelected() && chbxDownloaded.isSelected()==false))&& !(!(comboSeries.getSelectedItem().equals("")) && seriesid.getText().equals("#"))){
				if(!movieExists(textMovieName.getText())){
					List prevSort = Gui.table.getRowSorter().getSortKeys();
					PublicValues.movieNames.add(textMovieName.getText());
					if(comboSeries.getSelectedItem().equals("")){
						PublicValues.movieSeries.add(" ");
					}else{
						if(Integer.parseInt(seriesid.getText()) <10){
							PublicValues.movieSeries.add(comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}else{
							PublicValues.movieSeries.add(comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}
					}
					if(chbxDownloading.isSelected() && chbxDownloaded.isSelected()){
						PublicValues.availability.add("Available+Downloading");
						PublicValues.resolution.add(comboResolution.getSelectedItem() + "--->" + comboDownResolution.getSelectedItem());
					}else if(chbxDownloading.isSelected()){
						PublicValues.availability.add("Downloading");
						PublicValues.resolution.add(comboDownResolution.getSelectedItem());
					}else if(chbxDownloaded.isSelected()){
						PublicValues.availability.add("Available");
						PublicValues.resolution.add(comboResolution.getSelectedItem());
					}
					if (file!=null){
						//System.out.println(file.getPath());		//debugging
						PublicValues.moviePath.add(file.getPath());
					}else{
						//System.out.println("0");	//debugging
						PublicValues.moviePath.add("0");
					}
					PublicValues.updatePublicValues();
					Gui.initializeTable(false,Gui.table.getRowSorter().getSortKeys());
					PublicValues.changed = true;
					this.dispose();
					Gui.scrollPane.getViewport().scrollRectToVisible(Gui.table.getCellRect(Gui.getNewItemIndex(textMovieName.getText()), 0, false));
					
					String searchText = ControlPanel.txtSearchDatabase.getText();
					if (searchText.trim().length() ==0 || searchText.trim().equals("Search Database...")){
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}else{
						TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Gui.table.getModel());
						Gui.table.setRowSorter(rowSorter);
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1}));
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}
					
					Gui.selectNewItem(textMovieName.getText());
					Gui.table.grabFocus();
				}else{
					JOptionPane optionPane = new JOptionPane("Movie already exists in database!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}
			}else if(comboSeries.getSelectedItem()!=null && (seriesid.getText().equals("#") || seriesid.getText().equals(""))){
				JOptionPane optionPane = new JOptionPane("Please fill the number of this movie in the series!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
			}else if(file==null && chbxDownloading.isSelected()==true && chbxDownloaded.isSelected()==true){
				JOptionPane optionPane = new JOptionPane("Please load the movie path!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
			}
			else{
				JOptionPane optionPane = new JOptionPane("Please use all fields!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
			}
		}else if(!(textMovieName.getText().equals("")) && chbxUnavailable.isSelected()){ 
			if(!((seriesid.getText().equals("#") && !comboSeries.getSelectedItem().equals("")) || (seriesid.getText().equals("") && !comboSeries.getSelectedItem().equals("")))){
				if(!movieExists(textMovieName.getText())){
					List prevSort = Gui.table.getRowSorter().getSortKeys();
					PublicValues.movieNames.add(textMovieName.getText());
					if(comboSeries.getSelectedItem().equals("")){
						PublicValues.movieSeries.add(" ");
					}else{
						if(Integer.parseInt(seriesid.getText()) <10){
							PublicValues.movieSeries.add(comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}else{
							PublicValues.movieSeries.add(comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}
					}
					if(releaseDate.getText().equals("Release date") || releaseDate.getText().equals("") || releaseDate.getText().equals("n/a")){
						PublicValues.availability.add("Unavailable (n/a)");
					}else{
						PublicValues.availability.add("Unavailable ("+releaseDate.getText()+")");
					}
					PublicValues.resolution.add("~");
					PublicValues.moviePath.add("0");
					PublicValues.updatePublicValues();
					Gui.initializeTable(false,Gui.table.getRowSorter().getSortKeys());
					PublicValues.changed = true;
					this.dispose();
					Gui.scrollPane.getViewport().scrollRectToVisible(Gui.table.getCellRect(Gui.getNewItemIndex(textMovieName.getText()), 0, false));
					
					String searchText = ControlPanel.txtSearchDatabase.getText();
					if (searchText.trim().length() ==0 || searchText.trim().equals("Search Database...")){
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}else{
						TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Gui.table.getModel());
						Gui.table.setRowSorter(rowSorter);
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1}));
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}
					
					Gui.selectNewItem(textMovieName.getText());
					Gui.table.grabFocus();
				}else{
					JOptionPane optionPane = new JOptionPane("Movie already exists in database!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}
			}else{
				JOptionPane optionPane = new JOptionPane("Please fill the number of this movie in the series!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
			}
		}else{
				JOptionPane optionPane = new JOptionPane("Please use all fields!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
		}
		}if(source==btnOKEdit){
			if(!(textMovieName.getText().equals(""))  && !(comboResolution.getSelectedItem()==null) && (chbxDownloading.isSelected() || chbxDownloaded.isSelected())){
				if((file!=null || (file==null && chbxDownloading.isSelected() && chbxDownloaded.isSelected()==false))&& !(!(comboSeries.getSelectedItem().equals("")) && seriesid.getText().equals("#"))){
					List prevSort = Gui.table.getRowSorter().getSortKeys();
						PublicValues.movieNames.set(index,textMovieName.getText());
						if(comboSeries.getSelectedItem().equals("")){
							PublicValues.movieSeries.set(index," ");
						}else{
							if(Integer.parseInt(seriesid.getText()) <10){
								PublicValues.movieSeries.set(index,comboSeries.getSelectedItem()+" #"+seriesid.getText());
							}else{
								PublicValues.movieSeries.set(index,comboSeries.getSelectedItem()+" #"+seriesid.getText());
							}
						}
						if(chbxDownloading.isSelected() && chbxDownloaded.isSelected()){
							PublicValues.availability.set(index,"Available+Downloading");
							PublicValues.resolution.set(index,comboResolution.getSelectedItem() + "--->" + comboDownResolution.getSelectedItem());
						}else if(chbxDownloading.isSelected()){
							PublicValues.availability.set(index,"Downloading");
							PublicValues.resolution.set(index,comboDownResolution.getSelectedItem());
						}else if(chbxDownloaded.isSelected()){
							PublicValues.availability.set(index,"Available");
							PublicValues.resolution.set(index,comboResolution.getSelectedItem());
						}
						if (file!=null){
							PublicValues.moviePath.set(index,file.getPath());
						}else{
							PublicValues.moviePath.set(index,"0");
						}
						Point p = Gui.scrollPane.getViewport().getViewPosition();
						PublicValues.updatePublicValues();
						Gui.initializeTable(false,Gui.table.getRowSorter().getSortKeys());
						PublicValues.changed = true;
						this.dispose();
						Gui.scrollPane.getViewport().setViewPosition(p);
						
						String searchText = ControlPanel.txtSearchDatabase.getText();
						if (searchText.trim().length() ==0 || searchText.trim().equals("Search Database...")){
							Gui.table.getRowSorter().setSortKeys(prevSort);
						}else{
							TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Gui.table.getModel());
							Gui.table.setRowSorter(rowSorter);
							rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1}));
							Gui.table.getRowSorter().setSortKeys(prevSort);
						}
						
						Gui.selectNewItem(textMovieName.getText());
						Gui.table.grabFocus();
						
				}else if(comboSeries.getSelectedItem()!=null && (seriesid.getText().equals("#") || seriesid.getText().equals(""))){
					JOptionPane optionPane = new JOptionPane("Please fill the number of this movie in the series!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}else if(file==null && chbxDownloading.isSelected()==true && chbxDownloaded.isSelected()==true){
					JOptionPane optionPane = new JOptionPane("Please load the movie path!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}
				else{
					JOptionPane optionPane = new JOptionPane("Please use all fields!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}
			}else if(!(textMovieName.getText().equals("")) && chbxUnavailable.isSelected()){
				if(!((seriesid.getText().equals("#") && !comboSeries.getSelectedItem().equals("")) || (seriesid.getText().equals("") && !comboSeries.getSelectedItem().equals("")))){
					List prevSort = Gui.table.getRowSorter().getSortKeys();
					PublicValues.movieNames.set(index,textMovieName.getText());
					if(comboSeries.getSelectedItem().equals("")){
						PublicValues.movieSeries.set(index," ");
					}else{
						if(Integer.parseInt(seriesid.getText()) <10){
							PublicValues.movieSeries.set(index,comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}else{
							PublicValues.movieSeries.set(index,comboSeries.getSelectedItem()+" #"+seriesid.getText());
						}
					}
					if(releaseDate.getText().equals("Release date") || releaseDate.getText().equals("") || releaseDate.getText().equals("n/a")){
						PublicValues.availability.set(index,"Unavailable (n/a)");
					}else{
						PublicValues.availability.set(index,"Unavailable ("+releaseDate.getText()+")");
					}
					PublicValues.resolution.set(index,"~");
					PublicValues.moviePath.set(index,"0");
					PublicValues.updatePublicValues();
					Gui.initializeTable(false,Gui.table.getRowSorter().getSortKeys());
					PublicValues.changed = true;
					this.dispose();
					Gui.scrollPane.getViewport().scrollRectToVisible(Gui.table.getCellRect(Gui.getNewItemIndex(textMovieName.getText()), 0, false));
					
					String searchText = ControlPanel.txtSearchDatabase.getText();
					if (searchText.trim().length() ==0 || searchText.trim().equals("Search Database...")){
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}else{
						TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Gui.table.getModel());
						Gui.table.setRowSorter(rowSorter);
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1}));
						Gui.table.getRowSorter().setSortKeys(prevSort);
					}
					
					Gui.selectNewItem(textMovieName.getText());
					Gui.table.grabFocus();
				}else{
					JOptionPane optionPane = new JOptionPane("Please fill the number of this movie in the series!", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(this, "Error");
					dialog.setModal (true);
					dialog.setAlwaysOnTop (true);
					dialog.setVisible(true);
				}
			}else{
				JOptionPane optionPane = new JOptionPane("Please use all fields!", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(this, "Error");
				dialog.setModal (true);
				dialog.setAlwaysOnTop (true);
				dialog.setVisible(true);
			}
			}
		
	}
	private boolean movieExists(String x) {
		for(int i =0;i<PublicValues.movieNames.size();i++){
			if(x.equals(PublicValues.movieNames.get(i))){
				return true;
			}
		}
		return false;
	}


	public static String[] removeDuplicates(String[] x){
		for(int i = 0;i<x.length;i++){
			if (x[i].contains("#")){
				x[i] = x[i].substring(0,x[i].indexOf('#')-1);
			}
		}
		List<String> tmpList = Arrays.asList(x);
		TreeSet<String> unique = new TreeSet<String>(tmpList);
		return unique.toArray(new String[unique.size()]);
	}


	@Override
	public void itemStateChanged(ItemEvent evt) {
		Object source = evt.getSource();
		if(!chbxUnavailable.isSelected()){
			
			chbxDownloading.setEnabled(true);
			chbxDownloaded.setEnabled(true);
			releaseDate.setText("Release date");
			releaseDate.setEnabled(false);
			comboResolution.setEnabled(true);
			comboDownResolution.setEnabled(true);
			btnSelectPath.setEnabled(true);
			
			if(chbxDownloading.isSelected() && !(chbxDownloaded.isSelected())){
				comboDownResolution.setEnabled(true);
				comboResolution.setEnabled(false);
				btnSelectPath.setEnabled(false);
			}else if(!(chbxDownloading.isSelected()) || (chbxDownloading.isSelected() && chbxDownloaded.isSelected()) ){
				btnSelectPath.setEnabled(true);
			}if(chbxDownloaded.isSelected() && !(chbxDownloading.isSelected())){
				comboResolution.setEnabled(true);
				comboDownResolution.setEnabled(false);
			}if(chbxDownloading.isSelected() && chbxDownloaded.isSelected()){
				comboResolution.setEnabled(true);
				comboDownResolution.setEnabled(true);
			}if(chbxDownloading.isSelected()==false && chbxDownloaded.isSelected()==false){
				comboResolution.setEnabled(false);
				comboDownResolution.setEnabled(false);
			}
		}else{
			chbxDownloading.setSelected(false);
			chbxDownloading.setEnabled(false);
			chbxDownloaded.setSelected(false);
			chbxDownloaded.setEnabled(false);
			btnSelectPath.setEnabled(false);
			comboResolution.setEnabled(false);
			comboDownResolution.setEnabled(false);
			releaseDate.setEnabled(true);
		}
	}


	@Override
	public void focusGained(FocusEvent evt) {
		Object source = evt.getSource();
		if(source==seriesid && seriesid.getText().equals("#")){
			seriesid.setText("");
		}
		if(source==releaseDate && releaseDate.getText().equals("Release date")){
			releaseDate.setText("");
		}
		
	}


	@Override
	public void focusLost(FocusEvent evt) {
		Object source = evt.getSource();
		if(source==seriesid && seriesid.getText().equals("")){
			seriesid.setText("#");
		}
		if(source==releaseDate && releaseDate.getText().equals("")){
			releaseDate.setText("Release date");
		}
		
	}
}
