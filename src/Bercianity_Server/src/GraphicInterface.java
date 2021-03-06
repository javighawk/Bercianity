import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

public class GraphicInterface extends JFrame{
    
	private static final long serialVersionUID = 1L;
	
	private static final long InputOutputData_nLines = 200;
	
	private static boolean dataCaptionOn = true;
	
	private Lock InOutDataLock = new ReentrantLock();
    
    private JTextArea textAreaCommand;
    private JSlider sliderSpeed;
    private JButton btnConnect;
    private JLabel elblSpeed;
    private JLabel elblMSpeed1;
    private JLabel elblMSpeed2;
    private JButton btnTestM1, btnTestM2;
    private JPanel panelMTesting;
    private JPanel panelSpeedTM;
    private JProgressBar barTime3;
    private JProgressBar barTime2;
    private JProgressBar barTime1;
    private JProgressBar barTime4;
    private JProgressBar barTime5;
    private JProgressBar barTime6;
    private JProgressBar barTime7;
    private JLabel elblTime;
    private JButton btnStartRec;
    private JLabel elblRecStatus;
    private JTextField textFieldPort;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextArea textAreaInputData;
    private JTextArea textAreaOutputData;
    private JLabel lblDataCaption;
    private JSlider sliderDataCaption;
    private JRadioButton rdbtnHexData;
    private JRadioButton rdbtnBinaryData;
    private JLabel elblTimeLabel1;
    private JLabel elblTimeLabel2;
    private JLabel elblTimeLabel3;
    private JLabel elblTimeLabel6;
    private JLabel elblTimeLabel5;
    private JLabel elblTimeLabel4;
    private JLabel elblTimeLabel7;
    private JPanel panelClient;
    private JLabel lblClientIP;
    private JLabel elblClientIP;
    private JLabel elblServerIP;

    
    /**
     * Constructor
     */
    public GraphicInterface(){
        super("BXC");
        setResizable(false);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1100, 600));        
        pack();
        getContentPane().setLayout(null);
        
        JLabel lblHeader = new JLabel("BERCIANITY - GUI CONTROLLER");
        lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblHeader.setBounds(85, 11, 306, 23);
        getContentPane().add(lblHeader);
        
        JPanel panelCommand = new JPanel();
        panelCommand.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Command Window", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelCommand.setBounds(14, 297, 462, 254);
        getContentPane().add(panelCommand);
        panelCommand.setLayout(null);
        
        JScrollPane scrollPaneCommand = new JScrollPane();
        scrollPaneCommand.setAutoscrolls(true);
        scrollPaneCommand.setBounds(6, 16, 446, 227);
        panelCommand.add(scrollPaneCommand);
        
        textAreaCommand = new JTextArea();
        textAreaCommand.setFont(new Font("Courier New", Font.PLAIN, 12));
        scrollPaneCommand.setViewportView(textAreaCommand);
        scrollPaneCommand.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaCommand.setLineWrap(true);
        textAreaCommand.setWrapStyleWord(true);
        textAreaCommand.setEditable(false);
        textAreaCommand.setMargin(new Insets(0,5,0,5));
        
        JPanel panelServer = new JPanel();
        panelServer.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelServer.setBounds(14, 74, 218, 153);
        getContentPane().add(panelServer);
        panelServer.setLayout(null);
        
        btnConnect = new JButton("Connect");
        btnConnect.setBounds(47, 72, 89, 23);
        panelServer.add(btnConnect);
        
        textFieldPort = new JTextField();
        textFieldPort.setFont(new Font("Courier New", Font.PLAIN, 14));
        textFieldPort.setText("4444");
        textFieldPort.setBounds(61, 44, 86, 20);
        panelServer.add(textFieldPort);
        textFieldPort.setColumns(10);
        
        JLabel lblPort = new JLabel("Port:");
        lblPort.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblPort.setBounds(10, 47, 46, 14);
        panelServer.add(lblPort);
        
        JLabel lblServerIP = new JLabel("IP:");
        lblServerIP.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblServerIP.setBounds(10, 22, 46, 14);
        panelServer.add(lblServerIP);
        
        elblServerIP = new JLabel("");
        elblServerIP.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblServerIP.setBounds(61, 22, 130, 14);
        panelServer.add(elblServerIP);
        btnConnect.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		Thread myThread = new Thread(){
        			public void run(){
        				btnConnect.setEnabled(false);
        				textFieldPort.setEnabled(false);
        				MainAction.connect();
        			}
        		};
        		myThread.start();
        	}
        });
        
        JPanel panelTMTC = new JPanel();
        panelTMTC.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "TM/TC", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelTMTC.setBounds(482, 11, 602, 548);
        getContentPane().add(panelTMTC);
        panelTMTC.setLayout(null);
        
        panelSpeedTM = new JPanel();
        panelSpeedTM.setBorder(new TitledBorder(null, "Motors Speeds", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelSpeedTM.setBounds(10, 19, 212, 53);
        panelTMTC.add(panelSpeedTM);
        panelSpeedTM.setLayout(null);
        
        JLabel lblMSpeed1 = new JLabel("M1 Speed:");
        lblMSpeed1.setBounds(10, 17, 104, 14);
        panelSpeedTM.add(lblMSpeed1);
        lblMSpeed1.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        elblMSpeed1 = new JLabel("0");
        elblMSpeed1.setBounds(91, 17, 57, 14);
        panelSpeedTM.add(elblMSpeed1);
        elblMSpeed1.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        JLabel lblMSpeed2 = new JLabel("M2 Speed:");
        lblMSpeed2.setBounds(10, 31, 104, 14);
        panelSpeedTM.add(lblMSpeed2);
        lblMSpeed2.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        elblMSpeed2 = new JLabel("0");
        elblMSpeed2.setBounds(91, 31, 57, 14);
        panelSpeedTM.add(elblMSpeed2);
        elblMSpeed2.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        panelMTesting = new JPanel();
        panelMTesting.setBounds(10, 83, 212, 83);
        panelTMTC.add(panelMTesting);
        panelMTesting.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Motors Testing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMTesting.setLayout(null);
        
        btnTestM1 = new JButton("M1");
        btnTestM1.setBounds(10, 36, 57, 23);
        panelMTesting.add(btnTestM1);
        
        btnTestM2 = new JButton("M2");
        btnTestM2.setBounds(145, 36, 57, 23);
        panelMTesting.add(btnTestM2);
        
        JPanel panelTimeData = new JPanel();
        panelTimeData.setLayout(null);
        panelTimeData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Time data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelTimeData.setBounds(10, 241, 268, 226);
        panelTMTC.add(panelTimeData);
        
        JLabel lblTime = new JLabel("Time:");
        lblTime.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime.setBounds(10, 21, 58, 14);
        panelTimeData.add(lblTime);
        
        elblTime = new JLabel("0");
        elblTime.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTime.setBounds(53, 21, 68, 14);
        panelTimeData.add(elblTime);
        
        barTime1 = new JProgressBar();
        barTime1.setToolTipText("");
        barTime1.setForeground(new Color(255, 165, 0));
        barTime1.setString("0");
        barTime1.setStringPainted(true);
        barTime1.setMaximum(3000);
        barTime1.setBounds(142, 46, 116, 14);
        panelTimeData.add(barTime1);
        
        JLabel lblTime1 = new JLabel("Time 1:");
        lblTime1.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime1.setBounds(10, 46, 58, 14);
        panelTimeData.add(lblTime1);
        
        JLabel lblTime2 = new JLabel("Time 2:");
        lblTime2.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime2.setBounds(10, 71, 58, 14);
        panelTimeData.add(lblTime2);
        
        JLabel lblTime3 = new JLabel("Time 3:");
        lblTime3.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime3.setBounds(10, 96, 58, 14);
        panelTimeData.add(lblTime3);
        
        barTime2 = new JProgressBar();
        barTime2.setString("0");
        barTime2.setStringPainted(true);
        barTime2.setForeground(new Color(255, 165, 0));
        barTime2.setMaximum(3000);
        barTime2.setBounds(142, 71, 116, 14);
        panelTimeData.add(barTime2);
        
        barTime3 = new JProgressBar();
        barTime3.setForeground(new Color(255, 165, 0));
        barTime3.setStringPainted(true);
        barTime3.setString("0\r\n");
        barTime3.setMaximum(3000);
        barTime3.setBounds(142, 96, 116, 14);
        panelTimeData.add(barTime3);
        
        barTime4 = new JProgressBar();
        barTime4.setToolTipText("");
        barTime4.setStringPainted(true);
        barTime4.setString("0");
        barTime4.setMaximum(3000);
        barTime4.setForeground(new Color(255, 165, 0));
        barTime4.setBounds(142, 121, 116, 14);
        panelTimeData.add(barTime4);
        
        barTime5 = new JProgressBar();
        barTime5.setStringPainted(true);
        barTime5.setString("0");
        barTime5.setMaximum(3000);
        barTime5.setForeground(new Color(255, 165, 0));
        barTime5.setBounds(142, 146, 116, 14);
        panelTimeData.add(barTime5);
        
        barTime6 = new JProgressBar();
        barTime6.setStringPainted(true);
        barTime6.setString("0\r\n");
        barTime6.setMaximum(3000);
        barTime6.setForeground(new Color(255, 165, 0));
        barTime6.setBounds(142, 171, 116, 14);
        panelTimeData.add(barTime6);
        
        barTime7 = new JProgressBar();
        barTime7.setStringPainted(true);
        barTime7.setString("0\r\n");
        barTime7.setMaximum(3000);
        barTime7.setForeground(new Color(255, 165, 0));
        barTime7.setBounds(142, 196, 116, 14);
        panelTimeData.add(barTime7);
        
        JLabel lblTime5 = new JLabel("Time 5:");
        lblTime5.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime5.setBounds(10, 146, 58, 14);
        panelTimeData.add(lblTime5);
        
        JLabel lblTime6 = new JLabel("Time 6:");
        lblTime6.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime6.setBounds(10, 171, 58, 14);
        panelTimeData.add(lblTime6);
        
        JLabel lblTime7 = new JLabel("Time 7:");
        lblTime7.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime7.setBounds(10, 196, 58, 14);
        panelTimeData.add(lblTime7);
        
        JLabel lblTime4 = new JLabel("Time 4:");
        lblTime4.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblTime4.setBounds(10, 121, 58, 14);
        panelTimeData.add(lblTime4);
        
        elblTimeLabel1 = new JLabel();
        elblTimeLabel1.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel1.setBounds(67, 46, 65, 14);
        panelTimeData.add(elblTimeLabel1);
        
        elblTimeLabel2 = new JLabel();
        elblTimeLabel2.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel2.setBounds(67, 71, 65, 14);
        panelTimeData.add(elblTimeLabel2);
        
        elblTimeLabel3 = new JLabel();
        elblTimeLabel3.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel3.setBounds(67, 96, 65, 14);
        panelTimeData.add(elblTimeLabel3);
        
        elblTimeLabel6 = new JLabel();
        elblTimeLabel6.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel6.setBounds(67, 171, 65, 14);
        panelTimeData.add(elblTimeLabel6);
        
        elblTimeLabel5 = new JLabel();
        elblTimeLabel5.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel5.setBounds(67, 146, 65, 14);
        panelTimeData.add(elblTimeLabel5);
        
        elblTimeLabel4 = new JLabel();
        elblTimeLabel4.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel4.setBounds(67, 121, 65, 14);
        panelTimeData.add(elblTimeLabel4);
        
        elblTimeLabel7 = new JLabel();
        elblTimeLabel7.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblTimeLabel7.setBounds(67, 196, 65, 14);
        panelTimeData.add(elblTimeLabel7);
        
        JPanel panelRecData = new JPanel();
        panelRecData.setLayout(null);
        panelRecData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Record data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelRecData.setBounds(10, 477, 241, 60);
        panelTMTC.add(panelRecData);
        
        btnStartRec = new JButton("Start recording");
        btnStartRec.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnStartRec.setBounds(10, 22, 129, 23);
        btnStartRec.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
        		if( !Telemetry.isRecordingData() ){
        			Telemetry.startRecordingData();
        			btnStartRec.setText("Stop recording");
        			elblRecStatus.setText("ON");
        			elblRecStatus.setForeground(Color.RED);
        		} else {
        			Telemetry.stopRecordingData();
        			btnStartRec.setText("Start recording");
        			elblRecStatus.setText("OFF");
        			elblRecStatus.setForeground(Color.BLACK);
        		}
        	}
        });
        panelRecData.add(btnStartRec);
        
        elblRecStatus = new JLabel("OFF");
        elblRecStatus.setHorizontalAlignment(SwingConstants.CENTER);
        elblRecStatus.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblRecStatus.setBounds(151, 26, 80, 14);
        panelRecData.add(elblRecStatus);
        
        JPanel panelSpeedTC = new JPanel();
        panelSpeedTC.setBounds(10, 177, 268, 53);
        panelTMTC.add(panelSpeedTC);
        panelSpeedTC.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Speed", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelSpeedTC.setLayout(null);
        
        sliderSpeed = new JSlider();
        sliderSpeed.setBounds(10, 17, 200, 23);
        panelSpeedTC.add(sliderSpeed);
        sliderSpeed.setValue(100);
        sliderSpeed.addMouseListener(new MouseListener(){
        	
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				elblSpeed.setText(Integer.toString(sliderSpeed.getValue()));
			}
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {}
        });
        
        elblSpeed = new JLabel(Integer.toString(sliderSpeed.getValue()));
        elblSpeed.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblSpeed.setBounds(220, 19, 40, 16);
        panelSpeedTC.add(elblSpeed);
        
        JPanel panelData = new JPanel();
        panelData.setLayout(null);
        panelData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelData.setBounds(288, 19, 304, 518);
        panelTMTC.add(panelData);
        
        JScrollPane scrollPaneInputData = new JScrollPane();
        scrollPaneInputData.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneInputData.setAutoscrolls(true);
        scrollPaneInputData.setBounds(10, 58, 127, 412);
        panelData.add(scrollPaneInputData);
        
        textAreaInputData = new JTextArea();
        textAreaInputData.setLineWrap(true);
        textAreaInputData.setWrapStyleWord(true);
        scrollPaneInputData.setViewportView(textAreaInputData);
        textAreaInputData.setFont(new Font("Monospaced", Font.BOLD, 14));
        textAreaInputData.setForeground(Color.RED);
        textAreaInputData.setEditable(false);
        textAreaInputData.setBackground(Color.DARK_GRAY);
        
        JScrollPane scrollPaneOutputData = new JScrollPane();
        scrollPaneOutputData.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOutputData.getVerticalScrollBar().setModel(scrollPaneInputData.getVerticalScrollBar().getModel());
        scrollPaneOutputData.setAutoscrolls(true);
        scrollPaneOutputData.setBounds(167, 58, 127, 412);
        panelData.add(scrollPaneOutputData);
        
        textAreaOutputData = new JTextArea();
        scrollPaneOutputData.setViewportView(textAreaOutputData);
        textAreaOutputData.setForeground(Color.GREEN);
        textAreaOutputData.setFont(new Font("Monospaced", Font.BOLD, 14));
        textAreaOutputData.setEditable(false);
        textAreaOutputData.setBackground(Color.DARK_GRAY);
        
        JLabel lblInputData = new JLabel("INPUT DATA");
        lblInputData.setHorizontalAlignment(SwingConstants.CENTER);
        lblInputData.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblInputData.setBounds(10, 33, 127, 14);
        panelData.add(lblInputData);
        
        JLabel lblOutputData = new JLabel("OUTPUT DATA");
        lblOutputData.setHorizontalAlignment(SwingConstants.CENTER);
        lblOutputData.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblOutputData.setBounds(167, 33, 127, 14);
        panelData.add(lblOutputData);
        
        rdbtnHexData = new JRadioButton("Hex");
        rdbtnHexData.setSelected(true);
        buttonGroup.add(rdbtnHexData);
        rdbtnHexData.setBounds(10, 488, 51, 23);
        panelData.add(rdbtnHexData);
        
        rdbtnBinaryData = new JRadioButton("Bin");
        buttonGroup.add(rdbtnBinaryData);
        rdbtnBinaryData.setBounds(62, 488, 58, 23);
        panelData.add(rdbtnBinaryData);
        
        sliderDataCaption = new JSlider();
        sliderDataCaption.setMaximum(1);
        sliderDataCaption.setValue(1);
        sliderDataCaption.setBounds(167, 481, 64, 23);
        sliderDataCaption.addMouseListener(new MouseListener(){
        	
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if( sliderDataCaption.getValue() == 1 ){
					dataCaptionOn = true;
					lblDataCaption.setText("ON");
					lblDataCaption.setForeground(Color.GREEN);
				} else {
					dataCaptionOn = false;
					lblDataCaption.setText("OFF");
					lblDataCaption.setForeground(Color.RED);
				}
			}
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {}
        });
        panelData.add(sliderDataCaption);
        
        lblDataCaption = new JLabel("ON");
        lblDataCaption.setForeground(Color.GREEN);
        lblDataCaption.setBounds(236, 484, 58, 14);
        panelData.add(lblDataCaption);
        lblDataCaption.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        panelClient = new JPanel();
        panelClient.setLayout(null);
        panelClient.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Client", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelClient.setBounds(258, 74, 218, 153);
        getContentPane().add(panelClient);
        
        lblClientIP = new JLabel("IP:");
        lblClientIP.setFont(new Font("Courier New", Font.PLAIN, 14));
        lblClientIP.setBounds(10, 22, 46, 14);
        panelClient.add(lblClientIP);
        
        elblClientIP = new JLabel("");
        elblClientIP.setFont(new Font("Courier New", Font.PLAIN, 14));
        elblClientIP.setBounds(61, 22, 130, 14);
        panelClient.add(elblClientIP);
        
        
        btnTestM2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
				MainAction.arduino.sendTestM(2);
        	}
        });
        btnTestM1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0){
				MainAction.arduino.sendTestM(1);
        	}
        });
        
        /* Add ArduinoKeyListener to all components */
        getContentPane().addKeyListener(new ArduinoKeyListener());
        btnTestM1.addKeyListener(new ArduinoKeyListener());
        btnTestM2.addKeyListener(new ArduinoKeyListener());
        sliderSpeed.addKeyListener(new ArduinoKeyListener());
        btnStartRec.addKeyListener(new ArduinoKeyListener());
        rdbtnHexData.addKeyListener(new ArduinoKeyListener());
        rdbtnBinaryData.addKeyListener(new ArduinoKeyListener());
        sliderDataCaption.addKeyListener(new ArduinoKeyListener());
        
        setVisible(true);
    }
    
    public void setInitial(){
    	btnConnect.setEnabled(true);
    	btnTestM1.setEnabled(false);
    	btnTestM2.setEnabled(false);
    	sliderSpeed.setEnabled(false);
    	btnStartRec.setEnabled(false);
    	
    	elblMSpeed1.setText("N/A");
    	elblMSpeed2.setText("N/A");
    	elblTime.setText("N/A");
    	elblClientIP.setText("N/A");
    	barTime1.setString("N/A");
    	barTime2.setString("N/A");
    	barTime3.setString("N/A");
    	barTime4.setString("N/A");
    	barTime5.setString("N/A");
    	barTime6.setString("N/A");
    	barTime7.setString("N/A");
    	
    	barTime1.setValue(0);
    	barTime2.setValue(0);
    	barTime3.setValue(0);
    	barTime4.setValue(0);
    	barTime5.setValue(0);
    	barTime6.setValue(0);
    	barTime7.setValue(0);
	}
    
    public void setConnected(){
    	btnConnect.setEnabled(false);
    	btnTestM1.setEnabled(true);
    	btnTestM2.setEnabled(true);
    	sliderSpeed.setEnabled(true);
    	btnStartRec.setEnabled(true); 
    	
    	clearTelemetry();    	
    	
    	sliderSpeed.requestFocus(); 
    }
    
    public void print(String s){
    	textAreaCommand.append(s);
		textAreaCommand.setCaretPosition(textAreaCommand.getDocument().getLength());	
    }
    
    public void println(String s){
    	textAreaCommand.append(s);
    	textAreaCommand.append(Character.toString('\n'));
		textAreaCommand.setCaretPosition(textAreaCommand.getDocument().getLength());
    }
    
    public void clearTelemetry(){
    	elblMSpeed1.setText("0");
    	elblMSpeed2.setText("0");
    	elblTime.setText("00:00");
    	setTimeBarValues(new long[8]);
    }
    
    public void setTimeLabels(String [] labels){
    	
    	if( labels.length > 7 ) return;
    	
    	String [] lb = new String[7];
    	
    	for( int i=0 ; i<labels.length ; i++ )
    		lb[i] = labels[i];
    	
    	elblTimeLabel1.setText(lb[0]);
    	elblTimeLabel2.setText(lb[1]);
    	elblTimeLabel3.setText(lb[2]);
    	elblTimeLabel4.setText(lb[3]);
    	elblTimeLabel5.setText(lb[4]);
    	elblTimeLabel6.setText(lb[5]);
    	elblTimeLabel7.setText(lb[6]);
    }
    
    public void setTimeBarValues(long [] timeRecs){
    	
    	/* Time label */
    	long second = (timeRecs[0] / 1000) % 60;
		long minute = (timeRecs[0] / (1000 * 60));
    	elblTime.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
    	
    	/* Check time variables length */
    	int recsLength = timeRecs.length;
    	if(recsLength > 8) return;
    	
    	/* Initialize colors and values for all bars */
    	Color []clrs = new Color[7];
    	long []tms = new long[7];
    	
    	/* Get color/value for bars */
    	for( int i=0 ; i<recsLength-1 ; i++ ){
    		if( timeRecs[i+1] <= 1000 )
    			clrs[i] = Color.GREEN;
        	else if( timeRecs[i+1] <= 2000 )
        		clrs[i] = Color.ORANGE;
        	else
        		clrs[i] = Color.RED;
    		tms[i] = timeRecs[i+1];
    	}
    	
    	/* Set values */
		barTime1.setValue((int)tms[0]);
		barTime2.setValue((int)tms[1]);
		barTime3.setValue((int)tms[2]);
		barTime4.setValue((int)tms[3]);
		barTime5.setValue((int)tms[4]);
		barTime6.setValue((int)tms[5]);
		barTime7.setValue((int)tms[6]);
    	
		/* Set strings */
    	barTime1.setString(Long.toString(tms[0]) + " us");
    	barTime2.setString(Long.toString(tms[1]) + " us");
    	barTime3.setString(Long.toString(tms[2]) + " us");
    	barTime4.setString(Long.toString(tms[3]) + " us");
    	barTime5.setString(Long.toString(tms[4]) + " us");
    	barTime6.setString(Long.toString(tms[5]) + " us");
    	barTime7.setString(Long.toString(tms[6]) + " us");
    	
    	/* Set colors */
    	barTime1.setForeground(clrs[0]);
    	barTime2.setForeground(clrs[1]);
    	barTime3.setForeground(clrs[2]);
    	barTime4.setForeground(clrs[3]);
    	barTime5.setForeground(clrs[4]);
    	barTime6.setForeground(clrs[5]);
    	barTime7.setForeground(clrs[6]);
    }
    
    public void setSpeeds(int m1, int m2){
    	elblMSpeed1.setText(Integer.toString(m1));
    	elblMSpeed2.setText(Integer.toString(m2));
    	if( m1==0 && m2==0 )
    		setStoppedBotGUI();
    	else
    		setMovingBotGUI();
    }
    
    public void setMovingBotGUI(){
    	btnTestM1.setEnabled(false);
    	btnTestM2.setEnabled(false);
    	sliderSpeed.setEnabled(false);
    }
    
    public void setStoppedBotGUI(){
    	btnTestM1.setEnabled(true);
    	btnTestM2.setEnabled(true);
    	sliderSpeed.setEnabled(true);
    }
    
    public void printInputData(int data){
    	if( !dataCaptionOn ) return;
    	
    	InOutDataLock.lock();
    	
    	String d;
    	if( data < 0 )
    		d = "--\n";
    	else if( rdbtnHexData.isSelected() ){
    		d = "0x" + String.format("%02X", data) + "\n";
    		printOutputData(-1);
    	}else{
    		d = "0b" + String.format("%8s", Integer.toBinaryString(data)).replace(' ', '0') + "\n";
    		printOutputData(-1);
    	}
    	
    	try {
			textAreaInputData.getDocument().insertString(0, d, null);
			int nLines = textAreaInputData.getLineCount();
			if( nLines > InputOutputData_nLines ){
				if( rdbtnHexData.isSelected() )
					textAreaInputData.replaceRange("", textAreaInputData.getText().lastIndexOf("0x"), textAreaInputData.getDocument().getLength());
				else
					textAreaInputData.replaceRange("", textAreaInputData.getText().lastIndexOf("0b"), textAreaInputData.getDocument().getLength());
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			System.out.println("Last Index: " + textAreaInputData.getText().lastIndexOf("0x"));
			System.out.println("Length: " + textAreaInputData.getDocument().getLength());
		}
    	
    	InOutDataLock.unlock();
    }
    
    public void printOutputData(int data){
    	if( !dataCaptionOn ) return;
    	
    	InOutDataLock.lock();
    	
    	String d;
    	if( data < 0 )
    		d = "--\n";
    	else if( rdbtnHexData.isSelected() ){
    		d = "0x" + String.format("%02X", data) + "\n";
			printInputData(-1);
		}
    	else{
    		d = "0b" + String.format("%8s", Integer.toBinaryString(data)).replace(' ', '0') + "\n";
			printInputData(-1);
		}
    	
    	try {
			textAreaOutputData.getDocument().insertString(0, d, null);
			int nLines = textAreaOutputData.getLineCount();
			if( nLines > InputOutputData_nLines ){
				if( rdbtnHexData.isSelected() )
					textAreaOutputData.replaceRange("", textAreaOutputData.getText().lastIndexOf("0x"), textAreaOutputData.getDocument().getLength());
				else
					textAreaOutputData.replaceRange("", textAreaOutputData.getText().lastIndexOf("0b"), textAreaOutputData.getDocument().getLength());
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			System.out.println("Last Index: " + textAreaOutputData.getDocument().getLength());
		}
    	
    	InOutDataLock.unlock();
    }
    
    public void setServerIP(InetAddress addr){
    	elblServerIP.setText(addr.getHostAddress());
    }
    
    public void setClientIP(InetAddress addr){
    	elblClientIP.setText(addr.getHostAddress());
    }
    
    public int getPort(){return Integer.parseInt(textFieldPort.getText());}
    public int getSpeed(){return sliderSpeed.getValue();}
}