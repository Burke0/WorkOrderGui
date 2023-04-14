import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Random;
import java.util.Calendar;



/*
* The selected code is a Java class called "WorkOrderGui" that extends the "JPanel" class and implements the "ActionListener" and "ListSelectionListener" interfaces. 
* The purpose of this class is to create a graphical user interface (GUI) for managing work orders.
* The GUI consists of a list of work orders displayed in a scrollable pane, along with several buttons for manipulating the list, including: load, save, save as, add, edit, delete, and exit. 
* Additionally, the menu bar contains similar options, as well as options for adding a new item or a random item, deleting all items, and saving the current list to a file.
* The "actionPerformed" method handles button clicks and performs the appropriate action based on the button that was clicked. 
* For example, if the "Load" button is clicked, the user is prompted to select a file, and if a file is selected, the contents of the file are loaded into the list.
* The "valueChanged" method handles changes to the selection in the list and enables or disables the "Edit" and "Delete" buttons based on whether an item is selected or not.
* The "main" method creates a "WorkOrderGui" object and adds it to a new "JFrame" object to display the GUI to the user.
*/

/*TODO
add bool flag when after editing w/ JDialog to trigger a save prompt upon exiting
focus listeners for better input validation
Format ToString Override better
*/

public class WorkOrderGui extends JPanel implements ActionListener, ListSelectionListener
{
	JList<WorkOrder>						list;
	JFileChooser							chooser;
	DataInputStream 						dis;
	DataOutputStream 						dos;
	WorkOrderList							myWorkOrderList;
	File 									file=null;
	int 									returnVal;
	JButton									editButton, deleteButton;

	public WorkOrderGui()
	{
		myWorkOrderList= 			new WorkOrderList();
		list= 						new JList<WorkOrder>(myWorkOrderList);
		chooser=					new JFileChooser(".");
		JButton loadButton= 		new JButton("Load");
		JButton saveButton=			new JButton("Save");
		JButton saveAsButton=		new JButton("Save As");
		JButton addButton= 			new JButton("Add");
		editButton=					new JButton("Edit");
		deleteButton=			 	new JButton("Delete");
		JButton exitButton= 		new JButton("Exit");
		JPanel buttonPanel= 		new JPanel();
		JScrollPane scrollPane=		new JScrollPane();
		JMenuBar menuBar=			new JMenuBar();
		JMenu fileMenu=				new JMenu("File");
		JMenu itemMenu=				new JMenu("Item");
		JMenuItem load= 			new JMenuItem("Load");
		JMenuItem save=				new JMenuItem("Save");
		JMenuItem saveAs=			new JMenuItem("Save As");
		JMenuItem newItem=			new JMenuItem("New");
		JMenuItem addRandom=		new JMenuItem("Add Random");
		JMenuItem delete=			new JMenuItem("Delete");
		JMenuItem deleteAll=		new JMenuItem("delete All");



		setLayout(new BorderLayout());
		scrollPane.setViewportView(list);

		chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		list.addListSelectionListener(this);
		loadButton.addActionListener(this);
		saveButton.addActionListener(this);
		saveAsButton.addActionListener(this);
		addButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		exitButton.addActionListener(this);
		load.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		newItem.addActionListener(this);
		addRandom.addActionListener(this);
		delete.addActionListener(this);
		deleteAll.addActionListener(this);

		loadButton.setActionCommand("Load");
		saveButton.setActionCommand("Save");
		saveAsButton.setActionCommand("saveAs");
		addButton.setActionCommand("Add");
		editButton.setActionCommand("Edit");
		deleteButton.setActionCommand("Delete");
		exitButton.setActionCommand("Exit");
		load.setActionCommand("Load");
		save.setActionCommand("Save");
		saveAs.setActionCommand("saveAs");
		newItem.setActionCommand("Add");
		delete.setActionCommand("Delete");
		deleteAll.setActionCommand("DeleteAll");
		addRandom.setActionCommand("addRandom");
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);

		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		deleteAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		
		loadButton.setToolTipText("Select File");
		saveButton.setToolTipText("Overwrite File");
		saveAsButton.setToolTipText("Select Save Location");
		buttonPanel.add(loadButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAsButton);
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(exitButton);
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		itemMenu.add(newItem);
		itemMenu.add(addRandom);
		itemMenu.add(delete);
		itemMenu.add(deleteAll);
		menuBar.add(fileMenu);
		menuBar.add(itemMenu);

		add(menuBar, BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

	}

 	public void actionPerformed(ActionEvent e)
 	{
		if(e.getActionCommand().equals("Load"))
		{
			returnVal= chooser.showOpenDialog(this);
			if(returnVal==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					file = chooser.getSelectedFile();
					dis=new DataInputStream(new FileInputStream(file));
					myWorkOrderList= new WorkOrderList(dis);
					list.setModel(myWorkOrderList);
					dis.close();

				}
				catch(FileNotFoundException fnf)
				{JOptionPane.showMessageDialog(null, "Error Opening File");}
				catch(IOException ioe)
				{ioe.printStackTrace();}
			}
		}
		if(e.getActionCommand().equals("Save"))
		{
			try
			{
				if(file!=null)
				{
					dos=new DataOutputStream(new FileOutputStream(file));
					myWorkOrderList.Store(dos);
					dos.close();
				}
				else
				{
					returnVal= chooser.showSaveDialog(this);
					if(returnVal==JFileChooser.APPROVE_OPTION)
						file=chooser.getSelectedFile();
				}
			}
			catch(IOException ioe)
			{ioe.printStackTrace();}
			
		}
		if(e.getActionCommand().equals("saveAs"))
		{
			try
			{
				returnVal= chooser.showSaveDialog(this);
				if(returnVal==JFileChooser.APPROVE_OPTION)
					file=chooser.getSelectedFile();
				dos=new DataOutputStream(new FileOutputStream(file));
				myWorkOrderList.Store(dos);
				dos.close();
			}
			catch(IOException ioe)
			{ioe.printStackTrace();}
		}
		if(e.getActionCommand().equals("Add"))
			new WorkOrderDialog(myWorkOrderList).setVisible(true);;
		if(e.getActionCommand().equals("addRandom"))
			myWorkOrderList.addElement(WorkOrder.getRandom());
		if(e.getActionCommand().equals("Edit"))
			new WorkOrderDialog(myWorkOrderList, list.getSelectedIndex());
		if(e.getActionCommand().equals("Delete"))
		{
			if(list.getSelectedIndices().length>0)
				for(int i=list.getSelectedIndices().length-1; i>=0; i--)
					myWorkOrderList.removeElementAt(list.getSelectedIndices()[i]);
		}
		if(e.getActionCommand().equals("DeleteAll"))
			myWorkOrderList.removeAllElements();
		if(e.getActionCommand().equals("Exit"))
			System.exit(0);
 	}
 	public void valueChanged(ListSelectionEvent e)
 	{
		if(list.isSelectionEmpty())
		{
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
		if(list.getSelectedIndex()!=-1)
		{
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
		}
	}
 	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Work Order Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new WorkOrderGui());
		frame.setSize(500, 500);
		frame.setVisible(true);
  	}
}



//*************************************************************************************************************************************************
//*************************************************************************************************************************************************
