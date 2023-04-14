import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/*
* This is a Java code that defines a custom input verifier for a JTextField component. 
* The custom input verifier is named "WorkOrderVerifier" and it extends the InputVerifier class. 
* The verify() method is overridden and takes a JComponent as input parameter. This method is invoked when input focus changes from the current component to another component in the form. 
* The code checks if the input text field is empty, if so it returns true. If the input is not empty, it checks if the input text is equal to "SALES", "HARDWARE", or "ELECTRONICS" (case insensitive), if so it returns true.
* If none of the above conditions are met, it shows a message dialog asking the user to enter "SALES", "HARDWARE", or "ELECTRONICS", or clear the text box to continue, and returns false.
* 
* The purpose of this input verifier is to restrict user input to only certain values and to show an error message if the input is invalid.
*/

class WorkOrderVerifier extends InputVerifier
{
	public boolean verify(JComponent input)
	{
		JTextField tf;
		tf=(JTextField)input;
		if((tf.getText().trim()).isEmpty())
			return true;
		else if(tf.getText().trim().toUpperCase().equals("SALES")||tf.getText().trim().toUpperCase().equals("HARDWARE")||tf.getText().trim().toUpperCase().equals("ELECTRONICS"))
			return true;
		else
		{
			JOptionPane.showMessageDialog(null,"Please Enter: SALES, HARDWARE, ELECTRONICS, or Clear TextBox To Continue ");
			return false;
		}
	}
}