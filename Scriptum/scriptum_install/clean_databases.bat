set rootpass=root
rem -------------------------------------------
echo Cleaning the SCRIPTUM databases
echo Connect as root using password %rootpass%
mysql -u root --password=%rootpass% < clean.sql
