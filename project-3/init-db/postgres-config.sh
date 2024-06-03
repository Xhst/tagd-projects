#!/bin/bash

# Define the file to be modified
FILE="/var/lib/postgresql/data/postgresql.conf"

# Use sed to find and replace the line
sed -i.bak "s/#shared_preload_libraries = ''/shared_preload_libraries = 'pg_stat_statements'/" "$FILE"

