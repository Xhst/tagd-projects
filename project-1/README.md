# Project 1

# Setup

Run `docker-compose up`
> [!NOTE]
> This will setup two services, one with Postgres and one with PgAdmin.
> 
> The SQL files inside the folder `init-db` are executed to prepare and populate the tables.

Open [pgAdmin](http://localhost:5050) from your browser.

**Register a new Server** with the following connection parameters (if the *docker-compose.yml* is left unchanged):
- **Host name/address**: tagd-postgres
- **port**: 5432
- **username**: postgres
- **password**: postgres
