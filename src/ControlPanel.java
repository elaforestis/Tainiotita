import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.JButton;





















import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.SystemColor;
import java.awt.Color;


public class ControlPanel extends JDialog implements ComponentListener,ActionListener, FocusListener, KeyListener{

	private JPanel contentPane;
	public static JButton btnPlay;
	public static JButton btnAdd;
	public static JButton btnEdit;
	public static JButton btnSave;
	public static JButton btnDelete;
	public static JToggleButton tglSeries;
	public static JTextField txtSearchDatabase;
	public static JButton btnX;
	
	//dynamic search:
	String searchText;
	TableRowSorter<TableModel> rowSorter;
	List prevSort;
	//end
	
	public ControlPanel() {
		super(Main.x);
		setSize(200,700);
		setTitle("Tainiotita - Control Panel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		btnPlay = new JButton("Play!");
		btnPlay.setForeground(new Color(34, 139, 34));
		btnPlay.setBounds(0, 0, 194, 49);
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 21));
		getContentPane().add(btnPlay);
		
		btnAdd = new JButton("Add Item...");
		btnAdd.setBounds(0, 49, 194, 49);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(btnAdd);
		
		btnEdit = new JButton("Edit Item...");
		btnEdit.setBounds(0, 98, 194, 49);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(btnEdit);
		
		btnSave = new JButton("Save Database");
		btnSave.setBounds(0, 147, 194, 49);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(btnSave);
		
		btnDelete = new JButton("Delete Item");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(0, 196, 194, 49);
		getContentPane().add(btnDelete);
		
		tglSeries = new JToggleButton("Series Mode");
		tglSeries.setForeground(new Color(0, 0, 255));
		tglSeries.setFont(new Font("Tahoma", Font.BOLD, 16));
		tglSeries.setBounds(0, 245, 194, 49);
		getContentPane().add(tglSeries);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSearch.setBounds(0, 616, 194, 14);
		getContentPane().add(lblSearch);
		
		txtSearchDatabase = new JTextField();
		txtSearchDatabase.setFont(new Font("Tahoma", Font.ITALIC, 11));
		txtSearchDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearchDatabase.setText("Search Database...");
		txtSearchDatabase.setBounds(0, 641, 173, 20);
		getContentPane().add(txtSearchDatabase);
		txtSearchDatabase.setColumns(10);
		txtSearchDatabase.addKeyListener(this);
		txtSearchDatabase.addFocusListener(this);

		
		btnX = new JButton("X");
		btnX.setFont(new Font("LilyUPC", Font.PLAIN, 28));
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSearchDatabase.setText("");
				txtSearchDatabase.grabFocus();
				rowSorter = new TableRowSorter<>(Gui.table.getModel());
				Gui.table.setRowSorter(rowSorter);
				rowSorter.setRowFilter(null);
				rowSorter.toggleSortOrder(0);
			}
		});
		btnX.setBounds(173, 641, 20, 19);
		btnX.setMargin(new Insets(0,0,0,0));
		getContentPane().add(btnX);

		
		btnPlay.addActionListener(this);
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		tglSeries.addActionListener(this);
		
		setLocationRelativeTo(null);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent evt) {
		Main.x.setLocation(getLocation().x+210, getLocation().y);
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if(source==btnAdd){
			if(PublicValues.seriesMode){
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setMultiSelectionEnabled(true);
				fc.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
				int returnVal = fc.showOpenDialog(this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	for(int i=0;i<fc.getSelectedFiles().length;i++){
			        	PublicValues.seriesPath.add(fc.getSelectedFiles()[i].getAbsolutePath());
		        	}
			        Gui.initializeTree();
					PublicValues.changed = true;
		        }
			}else{
				AddMovieGui a = new AddMovieGui();
			}
		}else if(source ==btnEdit){
			if(Gui.table.getSelectedRow()==-1){
				//JOptionPane.showMessageDialog(null, "Please select a movie to edit!");
				//do nothing
			}else{
				AddMovieGui e = new AddMovieGui(Gui.table.getValueAt(Gui.table.getSelectedRow(), 0));
			}
		}else if(source == btnPlay){
			if(PublicValues.seriesMode){
				if(Gui.t.isSelectionEmpty() || !((DefaultMutableTreeNode) Gui.t.getLastSelectedPathComponent()).isLeaf()){
					//JOptionPane.showMessageDialog(null, "Please select an episode to play!");
					//do nothing
				}else{
					try {
						Desktop.getDesktop().open(new File(PublicValues.currentSelectedTreeNode));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
			if(Gui.table.getSelectedRow()==-1){
				//JOptionPane.showMessageDialog(null, "Please select a movie to play!");
				//do nothing
			}else{
				int index = PublicValues.movieNames.indexOf(Gui.table.getValueAt(Gui.table.getSelectedRow(),0));
				try {
					System.out.println((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[4]); //debugging
					if(((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[2]).equals("~")){
						JOptionPane.showMessageDialog(null, "Movie unavailable!");
					}else if(((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[4]).equals("0")){
						JOptionPane.showMessageDialog(null, "No Path inputted for this movie!");
					}else if(((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[4]).equals("1")){
						JOptionPane.showMessageDialog(null, "Movie path no longer exists, please enter the new path.");
					}else{
						Desktop.getDesktop().open(new File((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[4]));
					}
				} catch (IOException e) {
					System.out.println("File type not supported, opening with Notepad..."); //debugging
					try {
						Desktop.getDesktop().edit(new File((String) AddMovieGui.getInfo(Gui.table.getValueAt(Gui.table.getSelectedRow(),0),index)[4]));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			}
		}else if(source ==btnDelete){
			if(PublicValues.seriesMode){
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) Gui.t.getLastSelectedPathComponent();
				if(Gui.t.isSelectionEmpty() || !(node.toString().equals(node.getPath()[1].toString()))){
					JOptionPane.showMessageDialog(null, "Please select series main folder to delete from the database!");
				}else{

			        int result = JOptionPane.showConfirmDialog(
			            Main.x,
			            "Are you sure you want to delete this series folder from the database?",
			            "Exit Application",
			            JOptionPane.YES_NO_OPTION);
			 
			        if (result == JOptionPane.YES_OPTION){
						String selectedNode = node.getPath()[node.getPath().length-1].toString();
						for(int i =0;i<PublicValues.seriesPath.size();i++){
							if(PublicValues.seriesPath.get(i).toString().contains(selectedNode)){
								PublicValues.seriesPath.remove(i);
								PublicValues.changed = true;
								Gui.initializeTree();
								break;
							}
						}
					}
			        }
		    }else{
				if(Gui.table.getSelectedRow()==-1){
					//JOptionPane.showMessageDialog(null, "Please select a movie to delete!");
					//do nothing
				}else{

			        int result = JOptionPane.showConfirmDialog(
			           Main.x,
			            "Are you sure you want to delete this movie from the database?",
			            "Exit Application",
			            JOptionPane.YES_NO_OPTION);
			 
			        if (result == JOptionPane.YES_OPTION){
			        	List prevSort = Gui.table.getRowSorter().getSortKeys();
						int index = PublicValues.movieNames.indexOf(Gui.table.getValueAt(Gui.table.getSelectedRow(),0));
						PublicValues.movieNames.remove(index);
						PublicValues.movieSeries.remove(index);
						PublicValues.resolution.remove(index);
						PublicValues.availability.remove(index);
						PublicValues.moviePath.remove(index);
							
							
						PublicValues.updatePublicValues();
						PublicValues.changed = true;
						Gui.initializeTable(true,null);
						
						String searchText = ControlPanel.txtSearchDatabase.getText();
						if (searchText.trim().length() ==0 || searchText.trim().equals("Search Database...")){
							Gui.table.getRowSorter().setSortKeys(prevSort);
						}else{
							TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Gui.table.getModel());
							Gui.table.setRowSorter(rowSorter);
							rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1}));
							Gui.table.getRowSorter().setSortKeys(prevSort);
						}
			        }
				}
		   }
		}else if(source == btnSave){
				try {
					FileWriter fw = new FileWriter("tDatabase.db");
					fw.write("movieNames:");
					for(int i=0;i<PublicValues.movieNames.size();i++){
						fw.write((String)(PublicValues.movieNames.get(i)) + "$");
					}
					fw.write("\nmovieSeries:");
					for(int i=0;i<PublicValues.movieSeries.size();i++){
						fw.write((String)(PublicValues.movieSeries.get(i)) + "$");
					}
					fw.write("\nresolution:");
					for(int i=0;i<PublicValues.resolution.size();i++){
						fw.write((String)(PublicValues.resolution.get(i)) + "$");
					}
					fw.write("\navailability:");
					for(int i=0;i<PublicValues.availability.size();i++){
						fw.write((String)(PublicValues.availability.get(i)) + "$");
					}
					fw.write("\nmoviePath:");
					for(int i=0;i<PublicValues.moviePath.size();i++){
						fw.write((String)(PublicValues.moviePath.get(i)) + "$");
					}
					fw.write("\nseries:");
					for(int i=0;i<PublicValues.seriesPath.size();i++){
						fw.write((String)(PublicValues.seriesPath.get(i)) + "$");
					}
					PublicValues.changed = false;
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		else if(source ==tglSeries){
			if(Gui.table.isVisible()){
				PublicValues.seriesMode=true;
				btnX.setEnabled(false);
				txtSearchDatabase.setEnabled(false);
				btnEdit.setEnabled(false);
				Gui.initializeTree();
				Main.x.setTitle("Tainiotita - Series mode");
			}else{
				PublicValues.seriesMode=false;
				Gui.t.setVisible(false);
				btnX.setEnabled(true);
				Gui.table.setVisible(true);
				txtSearchDatabase.setEnabled(true);
				Gui.scrollPane.setViewportView(Gui.table);
				btnEdit.setEnabled(true);
				Main.x.setTitle("Tainiotita - Movie mode");
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent evt) {
		Object source = evt.getSource();
		if(source==txtSearchDatabase && txtSearchDatabase.getText().equals("Search Database...")){
			txtSearchDatabase.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent evt) {
		Object source = evt.getSource();
		if(source==txtSearchDatabase && txtSearchDatabase.getText().equals("")){
			txtSearchDatabase.setText("Search Database...");
		}
	}

	@Override
	public void keyPressed(KeyEvent evt) {

		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
			prevSort = Gui.table.getRowSorter().getSortKeys();
			searchText = txtSearchDatabase.getText();
			rowSorter = new TableRowSorter<>(Gui.table.getModel());
			Gui.table.setRowSorter(rowSorter);
			if (searchText.trim().length() ==0){
				Gui.table.getRowSorter().setSortKeys(prevSort);
			}else{
				rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText,new int[]{0,1,3}));
				Gui.table.getRowSorter().setSortKeys(prevSort);
			}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		
	}
}
