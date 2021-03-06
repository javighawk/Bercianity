#include "BXCOMM.h"
#include "BXMoveMode.h"
#include "TimeOut.h"
#include "Telemetry.h"
#include "BXCommandMode.h"
#include "I2Cdev.h"
#include "MPU6050.h"
#include "TimeRecord.h"
#include <Adafruit_CC3000.h>
#include <SPI.h>
#include <Wire.h>
#include <NXTI2CDevice.h>
#include <NXTMMX.h>


/*  
  InfoByte doc:
  [LINE_FEED][NONE][DIR_1 / SHCCM][DIR_0][MODE_1][MODE_0][SPEED_1][SPEED_0]
*/

/* Time record objects */
TimeRecord tLoop("LOOP");
TimeRecord tInfoRead("READ");
TimeRecord tAvailable("AVAIL");
TimeRecord tConnected("CNNCT");
TimeRecord tTelemetry("TM");

/* Variables declarations */
int infoByte;

void setup(){
    Serial.begin(SERIAL_BPS);
    pinMode(GREENLEDPIN,OUTPUT);
    pinMode(REDLEDPIN,OUTPUT);
    pinMode(SERIALPIN,OUTPUT);
    digitalWrite(GREENLEDPIN, LOW);
    digitalWrite(REDLEDPIN, LOW);
    digitalWrite(SERIALPIN, LOW);
    MMD_init();
    CMD_init();
    COMM_init();
    TMO_feedTimeOut();
}

void loop(){

    /* Start loop time recording */
    tLoop.TIME_trigger();

    /* Check Telemetry send */
    TM_checkTelemetry();

    /* Check Time Out */
    if( TMO_checkTimeOut() );

    /* Read incoming info */
    if( (infoByte = COMM_read()) != -1 ){
        identifyByte();
    }

    /* Stop loop time recording */
    tLoop.TIME_stop();
}

/*
 *  Identifies an incoming byte.
 */
void identifyByte(){    

    if( infoByte == TM_CONFIRMATION ){
        tTelemetry.TIME_trigger();
        TM_start();
        tTelemetry.TIME_stop();
    }

    else{
        switch(infoByte & MODE_MASK){
    
            case TEXTMODE_ID:
                // TODO: Text mode
                break;
    
            case COMMANDMODE_ID:
                if( bitRead(infoByte,COMMAND_SHORTCUT_BIT) ) 
                    CMD_shortCutCommands(infoByte);
                else            
                    // TODO: Written commands
                    break;
    
            case MOVEMODE_ID:
                MMD_run(infoByte);
                break;

            case SPINMODE_ID:
                MMD_run(infoByte);
                break;
        } 
    }
}
