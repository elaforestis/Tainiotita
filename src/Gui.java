import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.UIManager;



public class Gui extends JFrame implements KeyListener,ComponentListener, TreeSelectionListener, MouseListener{
	public static Point newLocation;
	public static JScrollPane scrollPane;
	public static JTable table;
	public static String[] columnNames = {"Movie Name","Movie Series", "Resolution", "Availability"};
	Object[][] data = {{"-","-","-","-"}};
	public static JTree t;
	public static String rawSeries;
	static DefaultMutableTreeNode prevdmtn = new DefaultMutableTreeNode(" ");

	
	public Gui(){
		super("Tainiotita - Movie Mode");
		URL iconURL = getClass().getResource("resources/Tainiotita.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	 
		addWindowListener( new WindowAdapter(){
		    public void windowClosing(WindowEvent e)
		    {
		        JFrame f = (JFrame)e.getSource();
		        if(PublicValues.changed){
			        int result = JOptionPane.showConfirmDialog(
			            f,
			            "Are you sure you want to exit without saving?",
			            "Exit Application",
			            JOptionPane.YES_NO_OPTION);
			 
			        if (result == JOptionPane.YES_OPTION){
			            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        }
		        }else{
		        	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        }
		    }
		});
		
		setSize(1000,700);
		setResizable(false);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 994, 672);
		getContentPane().add(scrollPane);
		
		

		table = new JTable(data,columnNames){
			public boolean isCellEditable(int row,int column){
			    return false;
			}
		};
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "none");
		table.getTableHeader().setReorderingAllowed(false);
		resizeAndAlignTable();
		table.addKeyListener(this);
		table.addMouseListener(this);
		scrollPane.setViewportView(table);
		
		
		setLocationRelativeTo(null);
		
	}


	public static void loadDB() {
        try {
	        
	        FileReader fr2 = new FileReader("tDatabase.db");
	        BufferedReader br2 = new BufferedReader(fr2);
	        String[] rawData = new String[5];
	        for (int i=0;i<5;i++){
		        rawData[i] = br2.readLine();
	        }
	        try{
	        	rawSeries = br2.readLine();
	        	int series = rawSeries.lastIndexOf('$');
	        	rawSeries = rawSeries.substring(7,series);
	        	String[] seriez = rawSeries.split("\\$");
	        	PublicValues.seriesPath.clear();
	        	for (int i =0;i<seriez.length;i++){
	        		PublicValues.seriesPath.add(seriez[i]);
	        	}
	        }catch(Exception e){
	        	System.out.println("Series not found");
	        }
	        br2.close();
    		int names = rawData[0].lastIndexOf('$');
    		rawData[0] = rawData[0].substring(11, names);

    		int series = rawData[1].lastIndexOf('$');
    		rawData[1] = rawData[1].substring(12, series);
    		
    		int reso = rawData[2].lastIndexOf('$');
    		rawData[2] = rawData[2].substring(11,reso);

    		int avail = rawData[3].lastIndexOf('$');
    		rawData[3] = rawData[3].substring(13, avail);

    		int path = rawData[4].lastIndexOf('$');
    		rawData[4] = rawData[4].substring(10, path);
    		
/*        		System.out.println(rawData[0]);	//debugging
    		System.out.println(rawData[1]);	//
    		System.out.println(rawData[2]);	//
    		System.out.println(rawData[3]);	//
    		System.out.println(rawData[4]);	//
*/
    		String[] moviez = rawData[0].split("\\$");
    		String[] seriez = rawData[1].split("\\$");
    		String[] resoz = rawData[2].split("\\$");
    		String[] availz = rawData[3].split("\\$");
    		String[] pathz = rawData[4].split("\\$");
    		
    		PublicValues.movieNames.clear();
    		PublicValues.movieSeries.clear();
    		PublicValues.resolution.clear();
    		PublicValues.availability.clear();
    		PublicValues.moviePath.clear();
    			
    		for (int i =0;i<moviez.length;i++){
    			PublicValues.movieNames.add(moviez[i]);
    			PublicValues.movieSeries.add(seriez[i]);
    			PublicValues.resolution.add(resoz[i]);
    			File f = new File(pathz[i]);
    			if(f.exists() && !(f.isDirectory())){
        			PublicValues.moviePath.add(pathz[i]);
        			PublicValues.availability.add(availz[i]);
    			}else if(pathz[i].equals("0")){
        			PublicValues.availability.add(availz[i]);
    				PublicValues.moviePath.add("0");
    			}else{
        			PublicValues.availability.add("*****Invalid Path*****");
    				PublicValues.moviePath.add("1");
    			}
    		}
    		PublicValues.updatePublicValues();		
    		initializeTable(true,null);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void resizeAndAlignTable(){
		TableColumn col = new TableColumn();
		col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(400);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyChar()==KeyEvent.VK_ENTER){
			ControlPanel.btnPlay.doClick();
		}else if(evt.getKeyChar()==KeyEvent.VK_DELETE){
			ControlPanel.btnDelete.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {

		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void selectNewItem(String x){
		int index = 0;
		boolean found = false;
		do{
			found = (x.equals(table.getValueAt(index, 0)));
			index++;
		}while(!found);
		table.setRowSelectionInterval(index-1, index-1);
	}
	public static int getNewItemIndex(String x){
		int index = 0;
		boolean found = false;
		do{
			found = (x.equals(table.getValueAt(index, 0)));
			index++;
		}while(!found);
		return index-1;
	}
	public static void initializeTable(boolean autosort, List previousSort){
		table = new JTable(PublicValues.newData,columnNames){
			  public boolean isCellEditable(int row,int column){
				    return false;
			  }
			  public Component prepareRenderer(TableCellRenderer r,int rw,int col){
				  Component c=super.prepareRenderer(r, rw, col);
				  int index = PublicValues.movieNames.indexOf(table.getValueAt(rw,0));
				  if(AddMovieGui.getInfo(table.getValueAt(rw,0),index)[4].equals("1")){
					  c.setBackground(Color.WHITE);
					  if(col==3){
						  c.setForeground(Color.RED);
						  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
					  }else{
						  c.setForeground(Color.BLACK);
					  }
					  if(isRowSelected(rw)){
						  c.setBackground(Color.RED);
						  c.setForeground(Color.WHITE);
						  if(col==3){
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }
					  }
				  }else if(isRowSelected(rw) && (AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Available") || AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Available+Downloading"))){
						  c.setBackground(Color.GREEN);
						  c.setForeground(Color.BLACK);
						  if(col==3){
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }
				  }else if(isRowSelected(rw) && AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Downloading")){
						  c.setBackground(Color.ORANGE);
						  c.setForeground(Color.BLACK);
						  if(col==3){
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }
				  }else if(isRowSelected(rw) && AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].toString().contains("Unavailable")){
					  c.setBackground(Color.BLACK);
					  c.setForeground(Color.WHITE);
					  if(col==3){
						  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
					  }
				  }else{
					  c.setBackground(Color.WHITE);
					  c.setForeground(Color.BLACK);
					  if(col==3){
						  if((AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Available") || AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Available+Downloading"))){
							  c.setBackground(Color.GREEN);
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }else if(AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].equals("Downloading")){  
							  c.setBackground(Color.ORANGE);
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }else if(AddMovieGui.getInfo(table.getValueAt(rw,0),index)[3].toString().contains("Unavailable")){  
							  c.setBackground(Color.BLACK);
							  c.setForeground(Color.WHITE);
							  c.setFont(new Font(c.getFont().getFamily(),Font.BOLD,c.getFont().getSize()));
						  }
					  }
				  }
				  return c;
			  }
			  
		};

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "none");
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);
		resizeAndAlignTable();
		if(autosort){
			table.getRowSorter().toggleSortOrder(0);
		}else{
			table.getRowSorter().setSortKeys(previousSort);
		}
		scrollPane.setViewportView(table);
		scrollPane.revalidate();
		scrollPane.repaint();
		table.addKeyListener(Main.x);
		table.addMouseListener(Main.x);
	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentMoved(ComponentEvent arg0) {
		Main.y.setLocation(getLocation().x-210, getLocation().y);
		
	}


	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void initializeTree(){
		table.clearSelection();
		table.setVisible(false);
		t = new JTree(getJTreeRoot());
		t.addTreeSelectionListener(Main.x);
		t.setRootVisible(false);
		t.addKeyListener(Main.x);
		t.addMouseListener(Main.x);
		scrollPane.setViewportView(t);
	}


	private static DefaultMutableTreeNode getJTreeRoot() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		for(int i =0;i<PublicValues.seriesPath.size();i++){
			root.add(getAllSubfiles((String) PublicValues.seriesPath.get(i)));
		}
		return root;
	}


	private static DefaultMutableTreeNode getAllSubfiles(String s) {
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(new NodeData(s.substring(s.lastIndexOf('\\')+1),s));
		File[] subs = new File(s).listFiles();
		if(subs!=null){
			for (int i=0;i<subs.length;i++){
				DefaultMutableTreeNode deepstuff = new DefaultMutableTreeNode(subs[i].getAbsolutePath());
				DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode(getAllSubfiles(subs[i].getAbsolutePath()));
				if(deepstuff.toString().substring(deepstuff.toString().lastIndexOf('\\')+1).equals(prevdmtn.toString().substring(prevdmtn.toString().lastIndexOf('\\')+1))){
					dmtn.add(prevdmtn);
				}else{
					deepstuff.add(dmtn2);
					dmtn.add(deepstuff);
				}
			}
		}
		prevdmtn = dmtn;
		return dmtn;
	}
	
	private static DefaultMutableTreeNode getAllSubfilesAbsolutePaths(String s) {
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(s);
		File[] subs = new File(s).listFiles();
		if(subs!=null){
			for (int i=0;i<subs.length;i++){
				DefaultMutableTreeNode deepstuff = new DefaultMutableTreeNode(subs[i].getAbsolutePath());
				DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode(getAllSubfilesAbsolutePaths(subs[i].getAbsolutePath()));
				if(deepstuff.toString().equals(prevdmtn.toString())){
					dmtn.add(prevdmtn);
				}else{
					deepstuff.add(dmtn2);
					dmtn.add(deepstuff);
				}
			}
		}
		prevdmtn = dmtn;
		return dmtn;
	}


	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) t.getLastSelectedPathComponent();
	    if (node.isLeaf()) {
	    	String selectedSeriesNode = node.getPath()[1].toString();							//name of selected series
	    	String selectedNode = node.getPath()[node.getPath().length-1].toString();			//name of selected node
	    	int selectedNodeLevel = node.getLevel();											//depth of selected node
	    	DefaultMutableTreeNode rootnode = (DefaultMutableTreeNode) t.getModel().getRoot(); //root node
	    	int childrenNum = t.getModel().getChildCount(rootnode);								//number of series added
	    	int i;																				//store index of selected series node
	    	for(i = 0;i<childrenNum;i++){													//find index of selected series in vector
	    		String ithseries = ((DefaultMutableTreeNode) (t.getModel().getChild(rootnode, i))).getPath()[1].toString(); //name of i-th series
	    		if(selectedSeriesNode==ithseries){												//if selected series found at i					
	    			break;																		//break out of for(index stored)
	    		}
	    	}
	    	String seriesPath = (String) PublicValues.seriesPath.get(i);					//get series path from vector
	    	DefaultMutableTreeNode absolutePathNode = getAllSubfilesAbsolutePaths(seriesPath);	//get series absolute path node
	    	String absolutePathSelected = absolutePathNode.toString();
	    	//need to get to the node inside absolutePathNode that corresponds to selected node
	    	String[] parentsOfSelectedNode = new String[selectedNodeLevel];
	    	//System.out.println(selectedNodeLevel);
	    	while(node.getParent()!=null && node.getParent().toString()!=selectedSeriesNode){
	    		parentsOfSelectedNode[selectedNodeLevel-1] = node.getParent().toString();
	    		node = (DefaultMutableTreeNode) node.getParent();
	    		selectedNodeLevel--;
	    	}
	    	for (int j=0;j<parentsOfSelectedNode.length;j++){
	    		if (parentsOfSelectedNode[j]!=null){
		    		absolutePathSelected+="\\"+parentsOfSelectedNode[j];
	    		}
	    	}
	    	absolutePathSelected+="\\"+selectedNode;
	    	PublicValues.currentSelectedTreeNode = absolutePathSelected;
	    }
		
	}


	@Override
	public void mouseClicked(MouseEvent event) {		
		if (event.getClickCount()==2){
			if(PublicValues.seriesMode){
				if(((DefaultMutableTreeNode)t.getLastSelectedPathComponent()).isLeaf()){
					ControlPanel.btnPlay.doClick();
				}
			}else{
				ControlPanel.btnPlay.doClick();
			}
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
