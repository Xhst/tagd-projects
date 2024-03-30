--- Block Size
SHOW block_size;
SELECT current_setting('block_size');

--- Space occupied by a value of a certain type
SELECT pg_column_size(1::numeric);

--- Space occupied by a table in MB
SELECT pg_size_pretty (pg_table_size('r1'));

--- Space occupied by indexes of a table in MB
SELECT pg_size_pretty (pg_indexes_size('r2'));

--- Number of records and number of blocks in a table
ANALYZE VERBOSE r3