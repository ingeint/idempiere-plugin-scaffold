#!/bin/bash
if  [ "$1" != "" ]; then
  database=$1
else
  echo "Example: ./databaseBackup.sh [database name]"
  exit
fi
datename=$(date +"%Y%m%d%H%M") 
filename=$database"_"$datename".backup"
echo "Backup name: "$filename
read -p "Press any key to continue..."
pg_dump -i -U adempiere -F c -b -v -f $filename $database
