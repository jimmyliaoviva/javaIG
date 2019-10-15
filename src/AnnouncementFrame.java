//105403517
//資管三A
//廖顥軒
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AnnouncementFrame extends JFrame{
	private final JPanel northPnl;
	private final JPanel centerPnl;
	private final JPanel southPnl;
	private final JPanel southrightPnl;
	private final JPanel southleftPnl;
	private static  JPanel showPnl;
	private  Container framePnl;
	private final JButton newpostButton;
	private final JButton editButton;
	private static JLabel likeLabel;
	private static JLabel contentLabel;
	private static JLabel northLabel;
	private final static String [] names = {"like.png","unlike.png"};
	private final  Icon[] icons = {
			new ImageIcon(getClass().getResource(names[0])),
			new ImageIcon(getClass().getResource(names[1])),
	};
	private static ObjectInputStream input;
	public static String str;
	private static boolean islike;
	
	//編輯Panel
	public static EditPanel editPnl;
	
	
	
	public AnnouncementFrame() {
		super("公告系統");
		
		
		//初始化JPanel
		framePnl = new Container();
		showPnl = new JPanel();
		northPnl = new JPanel(new BorderLayout());
		centerPnl = new JPanel();  
		southPnl = new JPanel();
		southrightPnl = new JPanel();
		southleftPnl = new JPanel();
		editPnl = new EditPanel();            //編輯的panel
		//editPnl.setVisible(false);
		
		showPnl.setLayout(new BorderLayout());
		southPnl.setLayout(new BorderLayout());
		framePnl = getContentPane();
		//centerPnl.setBackground(new Color(112,181,127));
		showPnl.setBackground(new Color(112,181,127));
		southleftPnl.setBackground(new Color(219,145,50));
		southPnl.setBackground(new Color(219,145,50));
		southrightPnl.setBackground(new Color(219,145,50));
		//初始化JButton
		newpostButton = new JButton("全新貼文");
		editButton = new JButton("編輯");
		newpostButton.setSize(75, 75);
		//初始化JLabel
		likeLabel = new JLabel();
		contentLabel = new JLabel();
		northLabel = new JLabel();
		
		northPnl.add(northLabel,BorderLayout.WEST);
		southleftPnl.add(likeLabel);
		southrightPnl.add(editButton);
		southrightPnl.add(newpostButton);
		southPnl.add(southrightPnl,BorderLayout.EAST);
		southPnl.add(southleftPnl,BorderLayout.WEST);
		showPnl.add(northPnl,BorderLayout.NORTH);
		showPnl.add(contentLabel,BorderLayout.CENTER);
		showPnl.add(southPnl,BorderLayout.SOUTH);
		//add(editPnl);
		add(showPnl);
		
		//如果回答確認
		if(Main.choice==0) {
			likeLabel.setEnabled(false);
		}
		else if(Main.choice==1) {
			editButton.setVisible(false);
			newpostButton.setVisible(false);
		}//end if
		else if(Main.choice==2) {
			System.exit(0);
		}//end if
		
		likeLabel.addMouseListener(new MouseAdapter()
		{
	public void mouseClicked(MouseEvent e) {
		if(likeLabel.getIcon()==icons[0]) {
			likeLabel.setIcon(icons[1]);	
			islike=false;
		}//end if
		else {
			likeLabel.setIcon(icons[0]);
			islike=true;
		}//end else
		if(Main.choice==1) {
			editPnl.returnStatus(islike);
		}
		
	}//end mouse clicked
	
		});
		
		openFile();
		readRecords();
		closeFile();
		
		
		if(islike) {
			likeLabel.setIcon(icons[0]);	
		}
		else {
			likeLabel.setIcon(icons[1]);
		}
		
		editButton.addActionListener(new ButtonHandler(editButton.getText()));
		newpostButton.addActionListener(new ButtonHandler(newpostButton.getText()));
		
	}//end constructor
	
	public static void openFile() {
		try {
			input = new ObjectInputStream(
					Files.newInputStream(Paths.get("post")));
		}//end try
		catch(IOException ioException){
			//System.err.println("Error opening file.");
			ioException.printStackTrace();
			//System.exit(1);
		}//end catch
	}//end openFile
	
	public static void readRecords() {
		try {
			//while(true) {
				PostSerializable post = (PostSerializable) input.readObject();
				
				contentLabel.setText("<html><pre>"+post.getContent()+"</pre></html>");
				northLabel.setText("<html>進JA助教<br>"+post.getEditTime().toString()+"</html>");
				islike = post.getIsLike();
				
			//}//end while
		}//end try
		catch(EOFException endOfFileException)//資料讀完
		{
			
		}
		catch(ClassNotFoundException classNotFoundException) {
			System.err.println("Invalid type: Terminating");
		}
		catch(IOException ioexception) {
			System.err.println("Error reading from file: Terminating");
		}
		
		
	}//end readRecords
	
	public static void closeFile() {
		try {
			if(input!=null) {
				input.close();
			}//end if
		}//end try
		catch(IOException ioException) {
			System.err.println("Error closing File: Terminating");
			System.exit(1);
		}
		
	}//end closeFile
	
	public class ButtonHandler implements ActionListener {
		public String text;
		public ButtonHandler(String text) {
			this.text = text;
			
		}//end constructor
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			switch(text) {
			case"編輯":
				System.out.println(text);
				//showPnl.setVisible(false);
				//editPnl.setVisible(true);
				remove(showPnl);
				
				add(editPnl);
				
				//invalidate();
				validate();
				repaint();
				//showPnl.setVisible(false);
				//editPnl.setVisible(true);
				//editPnl.repaint();
				break;
			case"全新貼文":
				System.out.println(text);
				remove(showPnl);
				
				add(editPnl);
				editPnl.clearTextField();
				validate();
				repaint();
				break;
			}//switch
			
		}//end actionPerformed
	}//end class buttonHandler
	
	public void changePanel(String first,String second ) {//轉道這個
		
		remove(editPnl);
		//showPnl = new JPanel();
		openFile();  //讓資料切換時可以即時更新
		readRecords();
		closeFile();
		add(showPnl);
	
			System.out.println("show");
		
		validate();
		
		//invalidate();
		repaint();
	}//end changePanel
	
}//end class AnnounceFrame
