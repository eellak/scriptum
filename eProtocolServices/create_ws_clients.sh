#/bin/bash

okmhost=http://office.uit.gr:8888/OpenKM 
 
wsimport -p com.openkm.ws.client $okmhost/OKMAuth?wsdl
wsimport -p com.openkm.ws.client $okmhost/OKMDocument?wsdl
wsimport -p com.openkm.ws.client $okmhost/OKMFolder?wsdl
wsimport -p com.openkm.ws.client $okmhost/OKMSearch?wsdl
wsimport -p com.openkm.ws.client $okmhost/OKMNotification?wsdl
wsimport -p com.openkm.ws.client $okmhost/OKMRepository?wsdl
 
jar cvf okm-ws-client.jar com
 
rm -rf com