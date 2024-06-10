#!/bin/bash

# SHARED PRELOAD LIB
FILE="/var/lib/postgresql/data/postgresql.conf"

sed -i.bak "s/#shared_preload_libraries = ''/shared_preload_libraries = 'pg_stat_statements'/" "$FILE"
sed -i.bak "s/#max_worker_processes = 8/max_worker_processes = 0/" "$FILE"
sed -i.bak "s/#max_parallel_workers = 8/max_parallel_workers = 0/" "$FILE"
sed -i.bak "s/#track_io_timing = off/track_io_timing = on/" "$FILE"
