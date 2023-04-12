import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

class WorkOrder
{
	String name, department, dateOfInitialRequest, dateFulfilled, reqDescription;
	double billingRate;

	public WorkOrder(String name,String department,String dateOfInitialRequest,String dateFulfilled,String reqDescription, double billingRate)
	{
		this.name=name;
		this.department=department;
		this.dateOfInitialRequest=dateOfInitialRequest;
		this.dateFulfilled=dateFulfilled;
		this.reqDescription=reqDescription;
		this.billingRate=billingRate;
	}
	public WorkOrder()
	{
		name="";
		department="";
		dateOfInitialRequest="";
		dateFulfilled="";
		reqDescription="";
		billingRate=0;
	}
	public WorkOrder(DataInputStream dis) throws IOException
	{
			name=dis.readUTF();
			department=dis.readUTF();
			dateOfInitialRequest=dis.readUTF();
			dateFulfilled=dis.readUTF();
			reqDescription=dis.readUTF();
			billingRate=dis.readDouble();

	}
	public void Store(DataOutputStream dos) throws IOException
	{
		dos.writeUTF(name);
		dos.writeUTF(department);
		dos.writeUTF(dateOfInitialRequest);
		dos.writeUTF(dateFulfilled);
		dos.writeUTF(reqDescription);
		dos.writeDouble(billingRate);

	}
	static WorkOrder getRandom()
	{
		Random rand=new Random();
		String[] nameList={"Liam","Noah","Shelby","Gerald","Martin","Felipe","Nancy","Ned","Trevor","Juan","Johnny", "Joeseph", "Ted","Justin", "Tim"};
		String[] departmentList={"SALES","HARDWARE","ELECTRONICS"};
		String[] descriptionList={"broken toilet","squeaky door", "broken pipe", "AC frozen", "disable self-destruct", "fuze blown"};
		WorkOrder wo=new WorkOrder();
		wo.name=nameList[rand.nextInt(15)];
		wo.department=departmentList[rand.nextInt(3)];
		wo.dateOfInitialRequest="01/01/2019"; //replace this later
		wo.dateFulfilled="10/09/2019";
		wo.reqDescription=descriptionList[rand.nextInt(6)];
		wo.billingRate=(float)(rand.nextInt(10000)/100.0);
		return wo;
	}
	@Override
	public String toString()
	{
		return this.name+" "+this.department+" "+this.dateOfInitialRequest+" "+this.dateFulfilled+" "+this.reqDescription+" "+this.billingRate;
	}

}



