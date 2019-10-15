import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	public class NewPanel extends JPanel {

		private final JPanel northPnl;
		private final JPanel centerPnl;
		private final JPanel southPnl;
		private final JPanel southrightPnl;
		private final JPanel southleftPnl;
		private final JPanel showPnl;

		private final JButton saveButton;
		private final JButton savenewfileButton;
		private final JButton importButton;
		private final JButton cancelButton;
		private static JTextField contentTextfield;
		private static JLabel northLabel;
		private final static String [] names = {"like.png","unlike.png"};
		

		private static ObjectInputStream input;
		public static String str;
		private static boolean islike;


		public NewPanel() {
			//setVisible(false);  //剛開始是看不到的
	
	
	//初始化JPanel
			showPnl = new JPanel();
			northPnl = new JPanel();
			centerPnl = new JPanel();
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
			
			contentTextfield = new JTextField();
			northLabel = new JLabel();
	
	
			northPnl.add(northLabel);
			//southleftPnl.add(likeLabel);
			southrightPnl.add(saveButton);
			southrightPnl.add(savenewfileButton);
			southrightPnl.add(importButton);
			southrightPnl.add(cancelButton);
	
			southPnl.add(southrightPnl,BorderLayout.EAST);
			southPnl.add(southleftPnl,BorderLayout.WEST);
			add(northPnl,BorderLayout.NORTH);
			add(contentTextfield,BorderLayout.CENTER);
			add(southPnl,BorderLayout.SOUTH);
			//add(showPnl);
	
	
	
			openFile();
			readRecords();
			closeFile();
	
			//editButton.addActionListener(new ButtonHandler(editButton.getText()));
	
	
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
				while(true) {
					PostSerializable post = (PostSerializable) input.readObject();
					//str += post.getContent() + "\n";
					contentTextfield.setText(post.getContent());
					northLabel.setText(post.getEditTime().toString());
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
			
			break;
		case"全新貼文":
			
			break;
		}//switch
		
	}//end actionPerformed
}//end class buttonHandler

}//end class AnnounceFrame
