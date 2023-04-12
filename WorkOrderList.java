import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.DefaultListModel;

class WorkOrderList extends DefaultListModel<WorkOrder>
{
	public WorkOrderList()
	{
		int numWorkOrders=0;
	}
	public WorkOrderList(DataInputStream dis) throws IOException
	{
		int numWorkOrders;
		numWorkOrders=dis.readInt();
		for(int n=0; n<numWorkOrders; n++)
			addElement(new WorkOrder(dis));
	}
	void Store(DataOutputStream dos)throws IOException
	{
		dos.writeInt(size());
		for(int n=0; n<size(); n++)
			elementAt(n).Store(dos);
	}
}