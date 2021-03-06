import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

public class MainAction{

	public static InputCommThread inputS;
	public static OutputCommThread outputS;
	public static TimeOutThread timeOut;
	
	public static GraphicInterface GUI;
	public static Comm arduino;
	
	public static final Lock InputCOMMLock = new ReentrantLock();
	public static final Lock OutputCOMMLock = new ReentrantLock();

	
	public static void main(String[] args){
		
		/* Initialize GUI */
		GUI = new GraphicInterface();
		GUI.setInitial();
		
		/* Initialize COMM */
		arduino = new Comm();
	}
	
	/*
	 * Show error on GUI
	 */
	public static void showError(String errorMessage){
        JOptionPane.showMessageDialog(GUI,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
	
	public static void showInputIOException(IOException e){
		MainAction.GUI.println("[Server]: IOException caught on Input stream. Pausing system...");
		pauseSystem();
    }
	
	public static void showOutputIOException(IOException e){
		MainAction.GUI.println("[Server]: IOException caught on Output stream. Pausing system...");
		pauseSystem();
    }
	
	public static void connect(){
		
		/* Wait for connection */
		GUI.println("-----Connecting-----");
		arduino.initialize();
		 
		/* Set GUI */
		GUI.setConnected();
		GUI.addKeyListener(new ArduinoKeyListener());
		
		/* Initialize threads */
		inputS = new InputCommThread();
		outputS = new OutputCommThread();
		timeOut = new TimeOutThread();
		
		/* Start threads */
		inputS.start();
		outputS.start();
		timeOut.start();
	}	
	
	public static void pauseSystem(){
		
		/* Pause threads */
		inputS.pause();
		timeOut.pause();
		
		/* Close COMM */
		arduino.closeSockets();
		
		/* Set initial window */
		GUI.setInitial();
	}
}
