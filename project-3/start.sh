docker compose up -d

# change permissions for extraction of benchbase archive
docker exec -it tagd-postgres chmod 777 /root
docker exec -it tagd-postgres chmod 777 /root/benchbase-2023
docker exec -it tagd-postgres chmod 777 /root/benchbase-2023/target
docker exec -it tagd-postgres chmod 777 /root/benchbase-2023/target/benchbase-postgres.tgz

# extract benchbase and set scale factor of tpch benchmark to 5
docker exec -it tagd-postgres /benchbase-scripts/prep-benchbase.sh

# connect to container
docker exec -it -w /root/benchbase-2023/target/benchbase-postgres tagd-postgres sh
