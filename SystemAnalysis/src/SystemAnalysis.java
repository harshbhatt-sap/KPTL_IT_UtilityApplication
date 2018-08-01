import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

public class SystemAnalysis 
{
	
	private static final Logger log = Logger.getLogger(SystemAnalysis.class);
	
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	
	private static Pattern pattern;
	private Matcher matcher;
	
	static JProgressBar progressBar =  new JProgressBar();
	static JPanel contentPane;
	static JTextField txtemp;
	static JComboBox locList;
	
	static String[] options = { "OK" };
	int i = 0;
	static String txt_email;
	static String stremail="";
	static String locname = "", chklocname = "", empid="" ,chkempid="";
	static String filePath="";
	static String path="";
		
	public static void main(String args[]) 
	{
		log.info(":::::::::::::::Main() Method:::::::::::::::::::");
		
		path = System.getenv("USERPROFILE");
		filePath=path+"\\Downloads\\ ";
		
		String  strid;
				
		SystemAnalysis l=new SystemAnalysis();
		
		final JFrame myJFrame = new JFrame("System Analysis _ KPTL-IT _ Gandhinagar");
		final JPanel panel = new JPanel();
		final Container cp = myJFrame.getContentPane();
		
		panel.setLayout(new GridLayout(3, 2));
		
		JLabel lblemp = new JLabel("Enter Employee Id: ");
		txtemp = new JTextField(10);
		txtemp.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
		});
		panel.add(lblemp);
		panel.add(txtemp);
		cp.add(panel);
		
		JLabel lbl = new JLabel("Enter Your Email-ID: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		cp.add(panel);
		
		JLabel lblloc = new JLabel("Select Location: ");
		String[] locStrings = { "Select","Gandhinagar", "Mumbai", "Raipur", "Noida", "Hydrabad" };
		locList = new JComboBox(locStrings);
		locList.setSelectedIndex(0);
		panel.add(lblloc);
		panel.add(locList);
		cp.add(panel);
		
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "KPTL - IT", JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (selectedOption == 0 ) 
		{
			
			log.info(":::::::::::::::All Information Enter ------ Click OK!!!!!!!!!!!!!!");
			
			String email = txt.getText();
			txt_email = email;
			stremail = "Employee Email ID  : "+email;
			log.info(":::::::::::: Email Id ====="+email);
						
			chklocname = (String) locList.getSelectedItem();
			locname = "Location :"+ chklocname;
			log.info(":::::::::::: Location Name ====="+chklocname);
			
			strid=txtemp.getText();
			chkempid = strid;
			empid = "Employee ID  : "+txtemp.getText();
			log.info(":::::::::::::::: Employee Id ========"+strid);
			
			
			if (isValid(email) && chkempid.trim() != "" && chklocname != "Select")
			{
				log.info(":::::::::::: IF Condition Execute ::::::::::::::::::::");
				
				try 
				{
					String value = System.getenv("USERPROFILE");
					log.info("::::::::::::::: User directory ======================"+value);
					
					
					log.info(":::::::::::: call getSystemdata() :::::::::::::::::::::::::");
					l.getSystemData();
					
					
					log.info(":::::::::::: call sendMail() :::::::::::::::::::::::::");
					l.sendMail();
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				log.info("::::::::::::::::::: Execute Design Code :::::::::::::::::::::::::");
				
				String test = "Process is Runing";
				
				contentPane = new JPanel();
				contentPane.setForeground(Color.WHITE);
				contentPane.setBackground(Color.gray);
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
				JLabel lblImageHeader = new JLabel("");
				lblImageHeader.setBackground(Color.WHITE);
				ImageIcon image = new ImageIcon(SystemAnalysis.class.getClassLoader().getResource("kptl.png"));
				lblImageHeader.setIcon(image);
				contentPane.add(lblImageHeader);
				
				JLabel lblprocessdetails = new JLabel("<html><br>"+ test +"&nbsp;&nbsp;.&nbsp;&nbsp;.&nbsp;&nbsp;.</html>");
				lblprocessdetails.setHorizontalAlignment(SwingConstants.CENTER);
				contentPane.add(lblprocessdetails);
				
				progressBar = new JProgressBar(0,2000);
				progressBar.setPreferredSize(new Dimension(380,30));
				progressBar.setStringPainted(true);
				progressBar.setValue(0);
				contentPane.add(progressBar);
				
				cp.add(contentPane);
			
				System.out.println("Yes");
				
				myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				myJFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\logo.png"));
				myJFrame.setTitle("System Analysis _ KPTL - IT _ Gandhinagar");
				myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				myJFrame.setBounds(100, 100, 420, 250);
				myJFrame.setUndecorated(true);
				myJFrame.setLocationRelativeTo(null);
				myJFrame.setVisible(true);
				
				
				log.info(":::::::::::: call iterate() :::::::::::::::::::::::::");
				l.iterate();

			}
			else
			{
				Panel panel1 = new Panel();
				
				log.info(":::::::::::::::::::::::::Error Message due to invalid value enter ::::::::::::::::::::::::::::::::");
				
				JLabel lbl1 = new JLabel("<html> <b>Note:</b> <br> <ul><li>Please Enter EmpID only numeric value. <li>Please Enter Valid Email Address. <li>Please select valid location.</ul></html>");

				panel1.add(lbl1);

				cp.add(panel);

				int selectedOption1 = JOptionPane.showOptionDialog(null, panel1, "KPTL - IT", JOptionPane.NO_OPTION,
						JOptionPane.ERROR_MESSAGE, null, options, options[0]);

				if (selectedOption1 == 0)
				{
					log.info("::::::::::::: Application Close due to incorrect value enter ::::::::::::::::::::::");
					
					System.exit(0);
				}
			}

		}
		else
		{
			log.info("::::::::::::: Application Close due to Esc or Close button click ::::::::::::::::::::::");
			
			System.exit(0);
		}	
	}
	
	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		
		Pattern pat = Pattern.compile(emailRegex);
		
		if (email == null)
			return false;
		
		log.info("::::: Email Validate =============== isValid()");
		return pat.matcher(email).matches();
	}
	
	public void iterate() 
	{    
		
		while(i<=2000)
		{    
			
			progressBar.setValue(i);    
			
			i=i+20;    
		
			try
			{
			  Thread.sleep(150);
			 
			  displayMessage();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}    
		}
	
	}

	public void displayMessage()
	{
		if(i==2000)
		{
			int selectedOption1 = JOptionPane.showOptionDialog(null, "Process Succsefully Completed", "KPTL - IT", JOptionPane.NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			
			if(selectedOption1==0)
			{
				log.info(":::::: Process is Completed ==== Application Close::::::::");
				
				System.exit(0);
			}
			
		}
	}
	
	public void getSystemData()throws IOException
	{
		List<String> list = new ArrayList<String>();
				
		 String[] commandList = {"powershell.exe",
					"\nSet-Content '"+filePath.concat("userInput.csv")+"' '" + empid +"';"
					+"Add-Content '"+filePath.concat("userInput.csv")+"'  '"+ locname +"';"
					+"Add-Content '"+filePath.concat("userInput.csv")+"'  '"+ stremail +"';"
					+"Get-CimInstance CIM_ComputerSystem|Select-Object Username,Domain,Manufacturer,Model,Name,PrimaryOwnerName,TotalPhysicalMemory |Format-List > '"+filePath.concat("BasicData.csv")+"';"
					+"Get-CimInstance CIM_ComputerSystem|Select-Object Username;"
			 		+"Get-CimInstance CIM_BIOSElement|Format-List >'"+filePath.concat("BiosData.csv")+"';"
			 		+"Get-CimInstance CIM_OperatingSystem|Format-List > '"+filePath.concat("OsData.csv")+"';"
			 		+"Get-ItemProperty HKLM:\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\* |  Select-Object DisplayName, DisplayVersion|Format-List >'"+filePath.concat("InstallSoft.csv")+"';"
			 		+"Get-WmiObject Win32_Processor > '"+filePath.concat("Processer.csv")+"';"
			 		+"Get-CimInstance Win32_LogicalDisk|Format-List >'"+filePath.concat("HDD.csv")+"';"
			 		+"Get-Content '"+filePath.concat("userInput.csv")+"'|Set-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("BasicData.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("BiosData.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("OsData.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("Processer.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("HDD.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Get-Content '"+filePath.concat("InstallSoft.csv")+"'|Add-Content '"+filePath.concat("AllData.csv")+"';"
			 		+"Remove-Item '"+filePath.concat("BasicData.csv")+"';"
			 		+"Remove-Item '"+filePath.concat("BiosData.csv")+"';"
			 		+"Remove-Item '"+filePath.concat("OsData.csv")+"';"
			 		+"Remove-Item  '"+filePath.concat("Processer.csv")+"';"
			 		+"Remove-Item '"+filePath.concat("HDD.csv")+"';"
			 		+"Remove-Item '"+filePath.concat("InstallSoft.csv")+"'"
			 		
			 		};  

	        ProcessBuilder pb = new ProcessBuilder(commandList);  

	        Process powerShellProcess = pb.start();  

	        String line;
	        
	        log.info(":::::::::: Get System Data Output ::::::::::::::");
		  
	        BufferedReader stdout = new BufferedReader(new InputStreamReader(
				 
		    powerShellProcess.getInputStream()));
		  
	        while ((line = stdout.readLine()) != null)
	        {
	        	list.add(line);
			    log.info("::::: Print Data :::::::"+line);
	        }
		 
			Iterator itr=list.iterator();  
			
			while(itr.hasNext())
			{  
			   log.info(" ::::::::::::::::: Data Is show ::::::::::::::"+itr.next());  
			}  
			
			stdout.close();
			
			log.info("::::::: Standard Error :::::::::::::");
			
			BufferedReader stderr = new BufferedReader(new InputStreamReader(
			
			powerShellProcess.getErrorStream()));
			
			while ((line = stderr.readLine()) != null)
			{
				   log.info(" :::::: Print Error ::::::::::::"+line);
			}
			
			stderr.close();
			
			log.info(" :::::::::: Complete process getSystemData() ::::::::::::::::");
	}
	
	public void sendMail() throws NoSuchProviderException 
	{
		log.info("::::::::: Start Send Mail() ::::::::::::::");
		
		log.info("::::: System User Name ::::::::::::::::"+System.getProperty("user.name"));
		
		String to="harsh.bhatt@kalpatarupower.com";
		
		String form=txt_email;
		
		String host = "10.22.10.175";
		
		log.info("::::: To Address ::::::::::::::::"+to);
		log.info("::::: Send Address ::::::::::::::::"+form);
		log.info("::::: Host Name ::::::::::::::::"+host);
		
		
		Properties properties=System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		
		try 
		{
		
			MimeMessage message=new MimeMessage(session);
		 
			message.setFrom(new InternetAddress(form));  
         
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
         
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
         	Date date = new Date(); 
            String msgsubject=formatter.format(date);
           	message.setSubject("KPTL Asset Capture :"+msgsubject);   
            log.info(":::::::::: Subject Line ==================="+message.getSubject());
           	
           	MimeBodyPart messageBodyPart2 = new MimeBodyPart();       
         
           	String filename = filePath.concat("AllData.csv");//change accordingly
           	log.info(":::::::: Final File Path ======================"+filename);
           	DataSource source = new FileDataSource(filename);  
           	messageBodyPart2.setDataHandler(new DataHandler(source));  
           	messageBodyPart2.setFileName("AllData.csv");
         
           	BodyPart messageBodyPart1 = new MimeBodyPart();  
            messageBodyPart1.setText("Dear Sir,\n\n Please find below details & attachment for Asset Capture Report.\n\n"+empid+"\n"+locname+"\n"+stremail+"\n\nThanks.."); 
         
           	Multipart multipart = new MimeMultipart(); 
           	multipart.addBodyPart(messageBodyPart1); 
           	multipart.addBodyPart(messageBodyPart2);  
           	message.setContent(multipart );  
         
           	// Send message  
           	Transport.send(message);  
         
           	log.info("::::::::::::::::::: Message sent successfully.... :::::::::::::::::::");
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		
	}

}



