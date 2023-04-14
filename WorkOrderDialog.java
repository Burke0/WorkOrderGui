import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
/*The code is an implementation of a dialog window for creating or editing Work Orders. 
* The dialog contains text fields for the name, department, date of initial request, date fulfilled, description and billing rate of a work order. 
* It has buttons for saving, canceling and adding another work order. 
* The actionPerformed method uses ActionCommands to determine which button was clicked and to execute the corresponding method accordingly. 
* The FocusListeners are used to validate the data from the text fields. Any errors that occur are displayed in red text below the text fields. 
*/

class WorkOrderDialog extends JDialog implements ActionListener, FocusListener
{
	JTextField 				nameField, departmentField,dateOfInitialRequestField, dateFulfilledField, reqDescriptionField, billingRateField;
	JLabel					nlError, dlError, dateInitialError, dateFulfillError, descriptionError, billError;
	JButton 					save, cancel, addAnother;
	WorkOrderList 			myWorkOrderList;
	int						workOrderListIndex;
	boolean 				fieldsValid;
	public WorkOrderDialog(WorkOrderList myWorkOrderList)
	{
		this.setLayout(new GridLayout(0,3));
		this.myWorkOrderList=myWorkOrderList;
		save=					new JButton("Save");
		addAnother=				new JButton("Add Another");
		cancel=					new JButton("Cancel");
		
		save.addActionListener(this);
		addAnother.addActionListener(this);
		cancel.addActionListener(this);

		save.setActionCommand("SAVE");
		addAnother.setActionCommand("ADD_ANOTHER");
		cancel.setActionCommand("CANCEL");


		JLabel nameLabel=					new JLabel("Name");
		JLabel departmentLabel=				new JLabel("Department");
		JLabel dateOfInitialRequestLabel=	new JLabel("Date of Initial Request");
		JLabel dateFulfilledLabel=			new JLabel("Date Fufilled");
		JLabel descriptionLabel=			new JLabel("Description");
		JLabel billingRateLabel=			new JLabel("Billing Rate");
		
		
		

		nlError=							new JLabel("Can't be blank");
		dlError=							new JLabel("SALES, HARDWARE, OR ELECTRONICS");
		dateInitialError=					new JLabel("Invalid Date(s): mm/dd/yyyy format");
		dateFulfillError=					new JLabel("Fulfilled Date can't be before Initial");
		descriptionError=					new JLabel("");
		billError=							new JLabel("Invalid Rate");

		nlError.setForeground(Color.RED);
		dlError.setForeground(Color.RED);
		dateInitialError.setForeground(Color.RED);
		dateFulfillError.setForeground(Color.RED);
		descriptionError.setForeground(Color.RED);
		billError.setForeground(Color.RED);

		nlError.setVisible(false);
		dlError.setVisible(false);
		dateInitialError.setVisible(false);
		dateFulfillError.setVisible(false);
		descriptionError.setVisible(false);
		billError.setVisible(false);

		nameField= 							new JTextField();
		departmentField= 					new JTextField();
		departmentField.setInputVerifier(new WorkOrderVerifier());
		dateOfInitialRequestField=			new JTextField();
		dateFulfilledField= 				new JTextField();
		reqDescriptionField= 				new JTextField();
		billingRateField= 					new JTextField();

		nameField.addFocusListener(this);
		departmentField.addFocusListener(this);
		dateOfInitialRequestField.addFocusListener(this);
		dateFulfilledField.addFocusListener(this);
		reqDescriptionField.addFocusListener(this);
		billingRateField.addFocusListener(this);

		this.add(nameLabel);
		this.add(nameField);
		this.add(nlError);
		this.add(departmentLabel);
		this.add(departmentField);
		this.add(dlError);
		this.add(dateOfInitialRequestLabel);
		this.add(dateOfInitialRequestField);
		this.add(dateInitialError);
		this.add(dateFulfilledLabel);
		this.add(dateFulfilledField);
		this.add(dateFulfillError);
		this.add(descriptionLabel);
		this.add(reqDescriptionField);
		this.add(descriptionError);
		this.add(billingRateLabel);
		this.add(billingRateField);
		this.add(billError);
		this.add(save);
		this.add(addAnother);
		this.add(cancel);
		this.setSize(700,200);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/10);
		this.setModal(true);

	}
	public WorkOrderDialog(WorkOrderList myWorkOrderList, int index)
	{
		this(myWorkOrderList);
		workOrderListIndex=index;
		save.setActionCommand("SAVEEDIT");
		addAnother.setEnabled(false);
		nameField.setText(myWorkOrderList.elementAt(index).name);
		departmentField.setText(myWorkOrderList.elementAt(index).department);
		dateOfInitialRequestField.setText(myWorkOrderList.elementAt(index).dateOfInitialRequest);
		dateFulfilledField.setText(myWorkOrderList.elementAt(index).dateFulfilled);
		reqDescriptionField.setText(myWorkOrderList.elementAt(index).reqDescription);
		billingRateField.setText(String.valueOf(myWorkOrderList.elementAt(index).billingRate));
		this.setVisible(true);
	}
	public static boolean isDouble(String num)
	{
		try
		{
			double d= Double.parseDouble(num);
		}
		catch(NumberFormatException|NullPointerException nfe)
		{
			System.out.println("Couldn't parse double billing rate");
			return false;
		}
		return true;
	}
	public void dumpFieldsToConsole()
	{
		System.out.println("name "+nameField.getText().trim() +" department " + departmentField.getText().trim().toUpperCase() + " dateInitial "
		+ dateOfInitialRequestField.getText().trim() +	" dateFulfilled " + dateFulfilledField.getText().trim() + " reqDescription "
		+ reqDescriptionField.getText().trim() + " billingRate " + Double.parseDouble(billingRateField.getText().trim()));
	}
	public void validateTextFields()
	{
		String dateInitial=dateOfInitialRequestField.getText().trim();
		String dateFulfilled=dateFulfilledField.getText().trim();
		fieldsValid=true;
		Calendar initial =  Calendar.getInstance(), fulfilled = Calendar.getInstance();
		nlError.setVisible(false);
		dlError.setVisible(false);
		dateInitialError.setVisible(false);
		dateFulfillError.setVisible(false);
		descriptionError.setVisible(false);
		billError.setVisible(false);
		if(nameField.getText().isEmpty())
		{
			nlError.setVisible(true);
			fieldsValid=false;
		}
		if(!(departmentField.getText().trim().toUpperCase().equals("SALES")||departmentField.getText().trim().toUpperCase().equals("HARDWARE")||departmentField.getText().trim().toUpperCase().equals("ELECTRONICS")))
			{
				dlError.setVisible(true);
				fieldsValid=false;
			}
		if(!(WorkOrderDialog.isDouble(billingRateField.getText())))
		{
			billError.setVisible(true);
			fieldsValid=false;
		}
		try
		{
			int monthI=Integer.parseInt(dateInitial.substring(0,2));
			int dayI=Integer.parseInt(dateInitial.substring(3,5));
			int yearI=Integer.parseInt(dateInitial.substring(6));
			if(dateInitial==null||dateInitial.length()!=10||dateInitial.charAt(2)!='/'||dateInitial.charAt(5)!='/')
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if(monthI>12||dayI>31||yearI<1970)
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if ((monthI==4||monthI==6||monthI==9||monthI==11)&&dayI>30)
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if(monthI==2&&dayI>28)
			{
				dateInitialError.setVisible(true);
				fieldsValid=false;
			}
			int monthF=Integer.parseInt(dateFulfilled.substring(0,2));
			int dayF=Integer.parseInt(dateFulfilled.substring(3,5));
			int yearF=Integer.parseInt(dateFulfilled.substring(6));
			if(dateFulfilled==null||dateFulfilled.length()!=10||dateFulfilled.charAt(2)!='/'||dateFulfilled.charAt(5)!='/')
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if(monthF>12||dayF>31||yearF<1970)
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if ((monthF==4||monthF==6||monthF==9||monthF==11)&&dayF>30)
			{
				fieldsValid=false;
				dateInitialError.setVisible(true);
			}
			else if(monthF==2&&dayF>28)
			{
				dateInitialError.setVisible(true);
				fieldsValid=false;
			}
			else
			{
				initial.set(Integer.parseInt(dateInitial.substring(6)),Integer.parseInt(dateInitial.substring(0,2)),Integer.parseInt(dateInitial.substring(3,5)));
				fulfilled.set(Integer.parseInt(dateFulfilled.substring(6)),Integer.parseInt(dateFulfilled.substring(0,2)),Integer.parseInt(dateFulfilled.substring(3,5)));
				if(fulfilled.before(initial))
				{
					dateFulfillError.setVisible(true);
					fieldsValid=false;
				}
			}
	
	
		}
		catch(NumberFormatException|StringIndexOutOfBoundsException nfe)
		{
			dateInitialError.setVisible(true);
			fieldsValid=false;
		}
	}
	public void actionPerformed(ActionEvent e)
	 {
		if(e.getActionCommand().equals("ADD_ANOTHER"))
		{
			validateTextFields();
			if (fieldsValid==true)
			{
				dumpFieldsToConsole();
				myWorkOrderList.addElement(new WorkOrder(nameField.getText().trim(),departmentField.getText().trim().toUpperCase(),dateOfInitialRequestField.getText().trim(),dateFulfilledField.getText().trim(),reqDescriptionField.getText().trim(),Double.parseDouble(billingRateField.getText().trim())));
				nlError.setVisible(false);
				dlError.setVisible(false);
				dateInitialError.setVisible(false);
				dateFulfillError.setVisible(false);
				descriptionError.setVisible(false);
				billError.setVisible(false);
			}
		}

		if(e.getActionCommand().equals("SAVE"))
		{
			addAnother.doClick();
			if(fieldsValid==true)
				dispose();
		}
		if(e.getActionCommand().equals("SAVEEDIT"))
		{
			validateTextFields();
			if(fieldsValid==true)
			{
				myWorkOrderList.setElementAt(new WorkOrder(nameField.getText().trim(),departmentField.getText().trim().toUpperCase(),dateOfInitialRequestField.getText().trim(),dateFulfilledField.getText().trim(),reqDescriptionField.getText().trim(),Double.parseDouble(billingRateField.getText().trim())), workOrderListIndex );
				dispose();
			}
		}
		if(e.getActionCommand().equals("CANCEL"))
			dispose();
	}
	public void focusLost(FocusEvent e)
	{

	}
	public void focusGained(FocusEvent e)
	{}
}