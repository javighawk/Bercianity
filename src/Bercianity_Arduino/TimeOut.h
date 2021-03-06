#ifndef TimeOut_h
#define TimeOut_h

/*****************************************/
/**************** INCLUDES ***************/
/*****************************************/

#include "BXC.h"
#include "BXCOMM.h"
#include "BXMoveMode.h"

/*****************************************/
/**************** DEFINES ****************/
/*****************************************/

#define TIMEOUT_EXPIRED       2000


/*****************************************/
/**************** FUNCTIONS **************/
/*****************************************/

void TMO_feedTimeOut();
int TMO_checkTimeOut();
unsigned long TMO_getCurrentTimeOut();

#endif
