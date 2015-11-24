package arbiter;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUITest
{

	
	public static void showComponent(JComponent component){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.add(component);
		frame.setVisible(true);
		
	}
	
	
	public static void showComponent(JFrame frame){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.setVisible(true);
		
	}
}