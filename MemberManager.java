import java.io.*;
class MemberManager
{
private static final String DATA_FILE="member.data";
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("Please specify an operation");
System.out.println("Operations are : [add,update,getAll,getByCourse,getByContactNumber]");
return;
}
String operation=gg[0];
if(!isOperationValid(operation))
{
System.out.println("Invalid operation : "+operation);
System.out.println("Valid operations are : [C,C++,Java,Python,J2EE]");
return;
}
if(operation.equalsIgnoreCase("add"))
{
add(gg);
}else
if(operation.equalsIgnoreCase("update"))
{
update(gg);
}else
if(operation.equalsIgnoreCase("getAll"))
{
getAll(gg);
}else
if(operation.equalsIgnoreCase("getByCourse"))
{
getByCourse(gg);
}else
if(operation.equalsIgnoreCase("getByContactNumber"))
{
getByContactNumber(gg);
}else
if(operation.equalsIgnoreCase("remove"))
{
remove(gg);
}
} //main ends
//operation functions
private static void add(String [] data)
{
if(data.length!=5)
{
System.out.println("Not enough arguments");
return;
}
String mobileNumber=data[1];
String name=data[2];
String course=data[3];
if(!isCourseValid(course))
{
System.out.println("Invalid course : "+course);
System.out.println("Courses are : [C,C++,Java,Python,J2EE]");
return;
}
int fee;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException numberFormatException)
{
System.out.println("Fee should be an integer type");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
String fmobileNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fmobileNumber=randomAccessFile.readLine();
if(fmobileNumber.equalsIgnoreCase(mobileNumber))
{
randomAccessFile.close();
System.out.println(mobileNumber+" exists.");
return;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.writeBytes(mobileNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(name);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(course);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(fee));
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
System.out.println("Member Added");
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void update(String [] data)
{
if(data.length!=5)
{
System.out.println("Not enough arguments update");
System.out.println("Usage : [contact number,name,course,fee]");
return;
}
String contactNumber=data[1];
String name=data[2];
String course=data[3];
if(!isCourseValid(course))
{
System.out.println("Invalid course : "+course);
System.out.println("Courses are : [C,C++,Java,Python,J2EE]");
return;
}
int fee;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException numberFormatException)
{
System.out.println("Fee should be an integer type");
return;
}
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("Invlaid contact number : "+contactNumber);
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members");
return;
}
String fContactNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fContactNumber.equalsIgnoreCase(contactNumber))
{
found=true;
break;
}
}
if(found==false)
{
System.out.println("Invalid contact number : "+contactNumber);
randomAccessFile.close();
return;
}
System.out.println("Updating data of : "+contactNumber);
System.out.println("Name of candidate is : "+fName);
File tmpFile=new File("tmpFile.tmp");
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(!fContactNumber.equalsIgnoreCase(contactNumber))
{
tmpRandomAccessFile.writeBytes(fContactNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(fFee+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(contactNumber+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(course+"\n");
tmpRandomAccessFile.writeBytes(fee+"\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
System.out.println("Data updated");
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
private static void getAll(String [] data)
{
if(data.length!=1)
{
System.out.println("No arguments required");
return;
}
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("No members");
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members");
return;
}
String mobileNumber;
String name;
String course;
int fee;
int memberCount=0;
int totalFee=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
mobileNumber=randomAccessFile.readLine();
name=randomAccessFile.readLine();
course=randomAccessFile.readLine();
fee=Integer.parseInt(randomAccessFile.readLine());
System.out.printf("%s %s %s %d\n",mobileNumber,name,course,fee);
totalFee+=fee;
memberCount++;
}
randomAccessFile.close();
System.out.println("Total registration :"+memberCount);
System.out.println("Total Fee Collected : "+totalFee);
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void getByCourse(String [] data)
{
if(data.length!=2)
{
System.out.println("Not enough arguments");
System.out.println("Courses are : [C,C++,Java,Python,J2EE]");
return;
}
String course=data[1];
if(!isCourseValid(course))
{
System.out.println("Invalid course : "+course);
System.out.println("Courses are : [C,C++,Java,Python,J2EE]");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
System.out.println("No registration against course : "+course);
randomAccessFile.close();
return;
}
String fMobileNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(course.equalsIgnoreCase(fCourse))
{
System.out.println("Contact number : "+fMobileNumber);
System.out.println("Name : "+fName);
System.out.println("Course : "+fCourse);
System.out.println("Fee : "+fFee);
found=true;
}
}
randomAccessFile.close();
if(found==false) 
{
System.out.println("No registration against course : "+course);
return;
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void getByContactNumber(String [] data)
{
if(data.length!=2)
{
System.out.println("Not enough arguments");
System.out.println("Courses are : [C,C++,Java,Python,J2EE]");
return;
}
String contactNumber=data[1];
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
System.out.println("No Members");
return;
}
String fContactNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(contactNumber.equalsIgnoreCase(fContactNumber))
{
System.out.println("Contact number : "+fContactNumber);
System.out.println("Name : "+fName);
System.out.println("Course : "+fCourse);
System.out.println("Fee : "+fFee);
found=true;
break;
}
}
randomAccessFile.close();
if(found==false)
{
System.out.println("No registration against contact number : "+contactNumber);
return;
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
private static void remove(String [] data)
{
if(data.length!=2)
{
System.out.println("Not enough arguments update");
System.out.println("Usage : [contact number]");
return;
}
String contactNumber=data[1];
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("Invlaid contact number : "+contactNumber);
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members");
return;
}
String fContactNumber="";
String fName="";
String fCourse="";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fContactNumber.equalsIgnoreCase(contactNumber))
{
found=true;
break;
}
}
if(found==false)
{
System.out.println("Invalid contact number : "+contactNumber);
randomAccessFile.close();
return;
}
System.out.println("Removing data of : "+contactNumber);
System.out.println("Name of candidate is : "+fName);
File tmpFile=new File("tmpFile.tmp");
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fContactNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(!fContactNumber.equalsIgnoreCase(contactNumber))
{
tmpRandomAccessFile.writeBytes(fContactNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(fFee+"\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
System.out.println("Data removed");
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
//helper functions
private static boolean isOperationValid(String operation)
{
operation=operation.trim();
String operations[]={"add","update","getAll","getByCourse","getByContactNumber","remove"};
for(int e=0;e<operations.length;e++)
{
return true;
}
return false;
}
private static boolean isCourseValid(String course)
{
course=course.trim();
String courses[]={"c","c++","java","python","j2ee"};
for(int e=0;e<courses.length;e++)
{
return true;
}
return false;
}
}