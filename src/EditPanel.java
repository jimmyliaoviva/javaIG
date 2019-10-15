//105403517
//資管三A
//廖顥軒
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.scene.text.Font;

	public class EditPanel extends JPanel {

		private final JPanel northPnl;
		
		private final JPanel southPnl;
		private final JPanel southrightPnl;
		private final JPanel southleftPnl;
		private final JPanel showPnl;

		private final JButton saveButton;
		private final JButton savenewfileButton;
		private final JButton importButton;
		private final JButton cancelButton;
		private static JTextArea contentTextArea;
		private static JLabel northLabel;
		private final static String [] names = {"like.png","unlike.png"};
		

		private static ObjectInputStream input;
		private static ObjectOutputStream output;
		public static String str;
		private static boolean islike;


		public EditPanel() {
			//setVisible(false);  //剛開始是看不到的
	
	
	//初始化JPanel
			showPnl = new JPanel();
			northPnl = new JPanel(new BorderLayout());
			
			southPnl = new JPanel();
			southrightPnl = new JPanel();
			southleftPnl = new JPanel();
			setLayout(new BorderLayout());
			southPnl.setLayout(new BorderLayout());
			//初始化JButton
			saveButton = new JButton("儲存");
			savenewfileButton = new JButton("另存內容");
			importButton = new JButton("匯入內容");
			cancelButton = new JButton("取消");
			//初始化JLabel
			
			contentTextArea = new JTextArea();
			northLabel = new JLabel();
			
			contentTextArea.setBackground(new Color(112,181,127));
			southleftPnl.setBackground(new Color(219,145,50));
			southPnl.setBackground(new Color(219,145,50));
			southrightPnl.setBackground(new Color(219,145,50));
	
			northPnl.add(northLabel,BorderLayout.WEST);
			//southleftPnl.add(likeLabel);
			southrightPnl.add(saveButton);
			southrightPnl.add(savenewfileButton);
			southrightPnl.add(importButton);
			southrightPnl.add(cancelButton);
	
			southPnl.add(southrightPnl,BorderLayout.EAST);
			southPnl.add(southleftPnl,BorderLayout.WEST);
			add(northPnl,BorderLayout.NORTH);
			add(contentTextArea,BorderLayout.CENTER);
			add(southPnl,BorderLayout.SOUTH);
			//add(showPnl);
	
	
	
			openRead();
			readRecords();
			closeRead();
	
			saveButton.addActionListener(new ButtonHandler(saveButton.getText()));
			savenewfileButton.addActionListener(new ButtonHandler(savenewfileButton.getText()));
			importButton.addActionListener(new ButtonHandler(importButton.getText()));
			cancelButton.addActionListener(new ButtonHandler(cancelButton.getText()));
		}//end constructor

		public static void openRead() {
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
		
		public static void openWrite() {
			try {
				output = new ObjectOutputStream(
						Files.newOutputStream(Paths.get("post")));
			}//end try
			catch(IOException ioException){
				//System.err.println("Error opening file.");
				ioException.printStackTrace();
				//System.exit(1);
			}//end catch
		}//end openWrite
		

		public static void readRecords() {
			try {
				while(true) {
					PostSerializable post = (PostSerializable) input.readObject();
					//str += post.getContent() + "\n";
					contentTextArea.setText(post.getContent());
					northLabel.setText("<html>進JA助教<br>"+post.getEditTime().toString()+"</html>");
					islike = post.getIsLike();
			
				}//end while
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
		
		public static void saveRecords() {
			
			
			try {
				PostSerializable post = new PostSerializable();
				
				post.setContent(contentTextArea.getText());
				post.setEditTime(new Date());
				
				output.writeObject(post);
				output.flush();
				System.out.println("saveRecord");
			}
			catch(Exception e) {
				e.printStackTrace();
			}//end 
			
		}//end writeRecords
		
		public static void saveLike() {
			try {
				PostSerializable post = new PostSerializable();
				post.setIsLike(islike);
				output.writeObject(post);
				output.flush();
				System.out.println("saveLike");
			}
			catch(Exception e) {
				e.printStackTrace();
			}//end 
		}//end setLike
		

		public static void closeRead() {
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
		
		public static void closeWrite() {
			try {
				if(output!=null) {
					output.close();
				}//end if
			}//end try
			catch(IOException ioException) {
				System.err.println("Error closing File: Terminating");
				System.exit(1);
			}
	
		}//end closeWrite

		public class ButtonHandler implements ActionListener {
			public String text;
			public ButtonHandler(String text) {
				this.text = text;
		
			}//end constructor
	
			@Override 
			public void actionPerformed(ActionEvent e) {
				System.out.println(text);
				switch(text) {
				case"儲存":
					//System.out.println(text);
					openWrite();
					saveRecords();
					closeWrite();
					Main.announcementFrame.changePanel("edit","show");
					
					//validate();
					break;
				case"另存內容":
					savenewFile();
					break;
				case"匯入內容":
					importFile();
					break;
				case"取消":
					Main.announcementFrame.changePanel("edit","show");
					break;
				}//switch
				
				
			}//end actionPerformed
		}//end class buttonHandler
		
		public void clearTextField() {
			contentTextArea.setText("");
			System.out.println("清空textfield");
		}//end clearTextField
		
		public void savenewFile() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(
					JFileChooser.FILES_AND_DIRECTORIES);  //儲存按鈕與開啟方式
			int result = fileChooser.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				 File file = fileChooser.getSelectedFile();
				 String str= file.getAbsolutePath();
				 System.out.println(str);
				 try {
				FileWriter fileWriter = new FileWriter(str);
				fileWriter.write(contentTextArea.getText());
				fileWriter.close();
				 }
				 catch(Exception e) {
					 
				 }
			}//end if
		}//end savenewFile
		
		public void importFile() {
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(
					JFileChooser.FILES_AND_DIRECTORIES);  //儲存按鈕與開啟方式
			int result = fileChooser.showOpenDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				 File file = fileChooser.getSelectedFile();
				 String str= file.getAbsolutePath();
				 System.out.println(str);
				 String s="";
				 try {
				 Scanner scanner = new Scanner(file);
				 while(scanner.hasNext()) {
					 s += scanner.nextLine() + "\r\n";
					 System.out.println(s);
				 }//end while
				 }
				 catch(Exception e) {
					 
				 }//end catch
				 System.out.println(s);
				 contentTextArea.setText(s);
			}
			
		}//end importFile
		
		public  void returnStatus(boolean setlike) {
			 islike = setlike;
			openWrite();
			saveLike();
			closeWrite();
		}//end returnStatus

}//end class AnnounceFrame
