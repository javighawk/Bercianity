import java.io.*;
import java.net.*;

public class COMM{
	
	/* COMM packages */
	public final static short EOT 				= 0x04;
	public final static short ENDOFTM 			= 0x05;
	public final static short TELEMETRY 		= 0x0E;
	public final static short TM_PETITION		= 0x0F;
	public final static short ESC 				= 0x7E;
	public final static short TM_CONFIRMATION	= 0x80;
	public final static short ENDOFPCK 			= 0x88;
	
	/* Mode identifiers */
	public final static short MODE_MASK 		= 0x0C;
	public final static short MOVE_MODE 		= 0x00;
	public final static short SPIN_MODE 		= 0x0C;
	public final static short TEXT_MODE 		= 0x04;
	public final static short COMMAND_MODE 		= 0x08;
		
	/* Movement direction identifiers */
	public final static short DIRECTION_MASK	= 0x30;
	public final static short DIR_UP 			= 0x00;
	public final static short DIR_DOWN 			= 0x10;
	public final static short DIR_LEFT 			= 0x20;
	public final static short DIR_RIGHT 		= 0x30;
	public final static short SPIN_U_L 			= 0x00;
	public final static short SPIN_D_L 			= 0x10;
	public final static short SPIN_U_R 			= 0x20;
	public final static short SPIN_D_R 			= 0x30;
	
	/* Movement speed identifiers */
	public final static short SPEED_MASK 		= 0x03;
	public final static short SPEED_NOCHANGE 	= 0x00;
	public final static short SPEED_NEWSPEED 	= 0x01;
	public final static short SPEED_STOP 		= 0x02;

	/* Command identifiers */
	public final static short CMD_TESTMOTORS 	= 0x12;
	
	/* Last speed of the bot */
	private static int lastSpeed = 0;
	
	/* Input and output COMM streams */
	private Writer output = null;
    private Reader input = null;
    
    /* Sockets */
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    
    /*
     * Constructor
     */
    public COMM(){
    	try {
			MainAction.GUI.setServerIP(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
    
    
    /*
     * Initialize COMM. Open socket and wait for a client connection
     */
    public void initialize(){
    	
    	// Get port number
    	int portNumber = MainAction.GUI.getPort();
        
        try{
        	
        	// Initialize sockets
        	serverSocket = new ServerSocket(portNumber);
            
        	// Wait for an incoming connection
            MainAction.GUI.println("[Server]: Created socket. Waiting for connection...");
            clientSocket = serverSocket.accept();
            
            // Connection received
            MainAction.GUI.println("[Server]: Client connected!");
            MainAction.GUI.setClientIP(clientSocket.getInetAddress());
            
            // Initialize input and output streams
            output = new OutputStreamWriter(clientSocket.getOutputStream(), "ISO-8859-1");                   
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "ISO-8859-1"));    
            
        } catch (IOException e) {
        	 MainAction.showError("Exception caught when trying to initialize comm");
        	 e.printStackTrace();
        }
    }
    

    //**************************************************************//
    //********************* MOVEMENT FUNCTIONS *********************//
    //**************************************************************//
    
    
    /*
     * Create the command to move the bot and push it to the output queue
     * 
     * @param dir Direction of movement (0 = UP, 1 = DOWN, 2 = LEFT, 3 = RIGHT)
     */
    public void moveBot(int dir){
    	
    	short sendData = 0;
    	sendData = setMove(sendData);
    	switch(dir){
    		case 0: sendData = setDirection(DIR_UP, sendData); break;
    		case 1: sendData = setDirection(DIR_DOWN, sendData); break;
    		case 2: sendData = setDirection(DIR_LEFT, sendData); break;
    		case 3: sendData = setDirection(DIR_RIGHT, sendData); break;
    		default: return;
    	}
    	
    	int newSpeed = MainAction.GUI.getSpeed();

    	if( newSpeed != lastSpeed ){
    		lastSpeed = newSpeed;
    		sendData = setSpeedCommand(SPEED_NEWSPEED, sendData);
    		MainAction.OutputCOMMLock.lock();
    		MainAction.outputS.push( sendData );
    		MainAction.outputS.push( newSpeed );
        	MainAction.OutputCOMMLock.unlock();
    	}
    }
    
    
    /*
     * Create the command to spin the bot and push it to the output queue
     * 
     * @param dir Direction of movement (0 = UP-LEFT, 1 = UP-RIGHT, 2 = DOWN-LEFT, 3 = DOWN-RIGHT)
     */
    public void spinBot(int dir){
    	
    	short sendData = 0;
    	sendData = setSpin(sendData);
    	switch(dir){
			case 0: sendData = setDirection(SPIN_U_L, sendData); break;
			case 1: sendData = setDirection(SPIN_U_R, sendData); break;
			case 2: sendData = setDirection(SPIN_D_L, sendData); break;
			case 3: sendData = setDirection(SPIN_D_R, sendData); break;
			default: return;
    	}
    	
    	int newSpeed = MainAction.GUI.getSpeed();
    	    	
   		if( newSpeed != lastSpeed ){
   			lastSpeed = newSpeed;
    		sendData = setSpeedCommand(SPEED_NEWSPEED, sendData);
    		MainAction.OutputCOMMLock.lock();
    		MainAction.outputS.push( sendData );
    		MainAction.outputS.push( newSpeed );
        	MainAction.OutputCOMMLock.unlock();
    	}
    }
    
    
    /*
     * Stop all motors using the Move mode
     */
    public void stopMotors(){
    	
    	short sendData = 0;
    	sendData = setMove(sendData);
    	sendData = setSpeedCommand(SPEED_STOP, sendData);
    	lastSpeed = 0;

    	MainAction.OutputCOMMLock.lock();
    	MainAction.outputS.push( sendData );	    	
    	MainAction.OutputCOMMLock.unlock();
  	
    }
    
    
    /*
     * Sets Mode bits into Movement mode.
     * 
     * @param data The unsigned byte to be set into Movement mode
     */
    private short setMove(short data){
    	data = (short) (data & ~MODE_MASK);
    	data = (short) (data | MOVE_MODE);
    	return data;
    }
    
    
    /*
     * Sets Mode bits into Spin mode.
     * 
     * @param data The unsigned byte to be set into Spin mode
     */
    private short setSpin(short data){
    	data = (short) (data & ~MODE_MASK);
    	data = (short) (data | SPIN_MODE);
    	return data;
    }
   
    
    /*
     * Sets direction (Only for Movement or Spin mode)
     * 
     * @param direction Direction to set
     * @param data The unsigned byte to be set into the desired direction
     */
    private short setDirection(int direction, short data){
    	direction = (direction & 48);
    	data = (short) (data & ~DIRECTION_MASK);
    	data = (short) (data | direction);
    	return data;
    }
    
    
    /*
     * Sets speed (only for Movement or Spin mode)
     * 
     * @param speed Speed to set
     * @param data The unsigned byte to be set into the desired speed
     */
    private short setSpeedCommand(short speedCMD, short data){
    	speedCMD = (short) (speedCMD & 3);
		data = (short) (data & ~SPEED_MASK);
		data = (short) (data | speedCMD);
		return data;
		
    }
    
    
    //**************************************************************//
    //********************* COMMAND FUNCTIONS **********************//
    //**************************************************************//
    
    
    /*
     * Sends the Test Motors shortcut command to the bot
     * 
     * @param motor The motor to be tested
     */
    public void sendTestM(int motor){
    	// Initial assert
    	if( motor<1 || motor>2 ) return;
    	
    	// Send command
		MainAction.OutputCOMMLock.lock();
		MainAction.outputS.push( COMMAND_MODE );
		MainAction.outputS.push( CMD_TESTMOTORS );
		MainAction.outputS.push( motor );
    	MainAction.OutputCOMMLock.unlock();	
    }
    
    
    //**************************************************************//
    //*********************** COMM FUNCTIONS ***********************//
    //**************************************************************//
    
    
    /*
     * Sends data to Arduino over TCP
     * 
     * @param data Data to send
     * @throws IOException if communication is cut
     */
    public void sendData(short data) throws IOException{
		output.write(data);
		output.flush();
		MainAction.GUI.printOutputData(data);
    }
    
    
    /*
     * Reads data from Arduino over TCP
     * 
     * @return Data read. -1 if nothing available
     * @throws IOException if communication is cut
     */
    public int readData() throws IOException{
    	short data = -1;
    	data = (short) input.read();			
    	
    	//If received byte is a flag, we have to discard it
    	// and take the following byte
    	if( data == ESC )
			while( (data = (short) input.read()) < 0 );
    	
    	if( data >= 0 ) MainAction.GUI.printInputData(data);
    	return data;
    }      

    
    /*
     * Close streams
     */
    public void closeSockets(){
    	try {
    		serverSocket.close();
    		clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }    
}