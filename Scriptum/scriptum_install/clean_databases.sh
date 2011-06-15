#!/bin/bash
rootpass=xxx
#-------------------------------------------
echo Cleaning the SCRIPTUM databases
echo Connect as root using password $rootpass
mysql -u root --password=$rootpass < clean.sql
