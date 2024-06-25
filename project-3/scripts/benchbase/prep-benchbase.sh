#!/bin/bash

# extract the benchmarking archive
cd /root/benchbase-2023/target
tar xvzf benchbase-postgres.tgz

# move query files and copy custom samples to the correct directory
mv /root/benchbase-2023/queries /root/benchbase-2023/target/benchbase-postgres
cp /root/samples/* /root/benchbase-2023/target/benchbase-postgres/config/postgres/


# change scale factor
FILE="/root/benchbase-2023/target/benchbase-postgres/config/postgres/sample_tpch_config.xml"
SCALE_FACTOR=2
cd benchbase-postgres/config/postgres
sed -i.bak "s|<scalefactor>0.1</scalefactor>|<scalefactor>$SCALE_FACTOR</scalefactor>|" "$FILE"