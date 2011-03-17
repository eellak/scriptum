rem set your javahome and your openKM host
set javahome=C:\jdk1.6.0_21
set okmhost=http://office.uit.gr:8888/OpenKM
rem -----------------------------------------



echo Creating OpenKM Clients
set wsimport=%javahome%\bin\wsimport.exe
set jar=%javahome%\bin\jar.exe 

echo Deleting old clients if exist.
del /f okm-ws-client.jar

%wsimport% -p com.openkm.ws.client %okmhost%/OKMAuth?wsdl
%wsimport% -p com.openkm.ws.client %okmhost%/OKMDocument?wsdl
%wsimport% -p com.openkm.ws.client %okmhost%/OKMFolder?wsdl
%wsimport% -p com.openkm.ws.client %okmhost%/OKMSearch?wsdl
%wsimport% -p com.openkm.ws.client %okmhost%/OKMNotification?wsdl
%wsimport% -p com.openkm.ws.client %okmhost%/OKMRepository?wsdl

echo Packaging to a jar 
%jar% cvf okm-ws-client.jar com

echo Deleting temporary directory  
del /f /s /q com