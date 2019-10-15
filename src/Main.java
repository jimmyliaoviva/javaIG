//105403517
//資管三A
//廖顥軒
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static int choice;
	public static AnnouncementFrame announcementFrame;
	public static void main(String []args) {
		
		choice = JOptionPane.showConfirmDialog(null, "是否為發布者","登入",JOptionPane.YES_NO_CANCEL_OPTION,0);
		announcementFrame = new AnnouncementFrame();
		announcementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		announcementFrame.setSize(1000, 500);
		announcementFrame.setVisible(true);
		
	}//end main
	
}//end class Main
