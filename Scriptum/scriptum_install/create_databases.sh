#!/bin/bash
rootpass=xxx
#-------------------------------------------
echo Creating the SCRIPTUM databases
echo Connect as root using password $rootpass
mysql -u root --password=$rootpass < scriptum_users.sql
mysql -u root --password=$rootpass < ellak.sql
mysql -u root --password=$rootpass < okm_app.sql
mysql -u root --password=$rootpass < okm_repo.sql