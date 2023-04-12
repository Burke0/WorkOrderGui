import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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