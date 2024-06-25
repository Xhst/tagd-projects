#!/bin/bash

# extract the benchmarking archive
cd /root/benchbase-2023/target
tar xvzf benchbase-postgres.tgz

# change scale factor
FILE="/root/benchbase-2023/target/benchbase-postgres/config/postgres/sample_tpch_config.xml"
SCALE_FACTOR=2
cd benchbase-postgres/config/postgres
sed -i.bak "s|<scalefactor>0.1</scalefactor>|<scalefactor>$SCALE_FACTOR</scalefactor>|" "$FILE"