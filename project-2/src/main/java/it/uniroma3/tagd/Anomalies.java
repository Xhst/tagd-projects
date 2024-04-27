package it.uniroma3.tagd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used to simulate different types of database anomalies.
 */
public class Anomalies {

    /**
     * This method is used to test a specific anomaly.
     * @param isolationLevel This is the isolation level of the transaction.
     * @param anomaly This is the type of anomaly to be tested.
     */
    public static void testAnomaly(IsolationLevel isolationLevel, Anomaly anomaly) {
        try (Connection connection1 = Database.getConnection();
             Connection connection2 = Database.getConnection();
             Connection connection3 = Database.getConnection()) {

            connection1.setAutoCommit(false);
            connection2.setAutoCommit(false);

            connection1.setTransactionIsolation(isolationLevel.getLevel());
            connection2.setTransactionIsolation(isolationLevel.getLevel());

            System.out.println();
            System.out.println("Testing " + isolationLevel.getName() + " isolation level with " + anomaly.getName() + " anomaly");

            switch (anomaly) {
                case LOST_UPDATE:
                    simulateLostUpdate(connection1, connection2);
                    break;
                case DIRTY_READ:
                    simulateDirtyRead(connection1, connection2);
                    break;
                case GHOST_UPDATE:
                    simulateGhostUpdate(connection1, connection2);
                    break;
                case INCONSISTENT_READ:
                    simulateInconsistentRead(connection1, connection2);
                    break;
                case PHANTOM_READ:
                    simulatePhantomRead(connection1, connection2);
                    break;

            }

            System.out.println(getBankAccountString(connection3));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Database.reset();
    }

    /**
     * This method is used to get the bank account information.
     * @param connection This is the connection to the database.
     * @return A string containing the bank account information.
     * @throws SQLException If a database access error occurs.
     */
    private static String getBankAccountString(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM bank_account WHERE id = 1");

            if (resultSet.next()) {
                int balance = resultSet.getInt(1);
                return "Final balance: " + balance;
            }
        }
        return "";
    }

    /**
     * This method is used to simulate the Lost Update anomaly.
     * @param connection1 This is the first connection.
     * @param connection2 This is the second connection.
     * @throws SQLException If a database access error occurs.
     */
    private static void simulateLostUpdate(Connection connection1, Connection connection2) throws SQLException {

        System.out.println("Operation 1: balance +100; Operation 2: balance -300");

        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();

        executor1.execute(() -> {
            try (Statement statement = connection1.createStatement()) {
                statement.setQueryTimeout(3);

                statement.executeUpdate("UPDATE bank_account SET balance = balance + 100 WHERE id = 1");

                Thread.sleep(2000);

                connection1.commit();
                System.out.println("Operation 1 committed");
            } catch (Exception ignored) {
                System.out.println("Error on first operation");
            }
        });

        executor2.execute(() -> {
            try (Statement statement = connection2.createStatement()) {
                statement.setQueryTimeout(3);

                statement.executeUpdate("UPDATE bank_account SET balance = balance - 300 WHERE id = 1");

                connection2.commit();
                System.out.println("Operation 2 committed");
            } catch (Exception ignored) {
                System.out.println("Error on second operation");
            }
        });

        try {
            executor1.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
            executor2.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
            executor1.shutdown();
            executor2.shutdown();
        } catch (InterruptedException ignored) { }
    }

    /**
     * This method is used to simulate the Dirty Read anomaly.
     * @param connection1 This is the first connection.
     * @param connection2 This is the second connection.
     * @throws SQLException If a database access error occurs.
     */
    private static void simulateDirtyRead(Connection connection1, Connection connection2) throws SQLException {
        try (Statement statement1 = connection1.createStatement();
             Statement statement2 = connection2.createStatement()) {

            statement1.setQueryTimeout(5);
            statement2.setQueryTimeout(5);

            statement1.executeUpdate("UPDATE bank_account SET balance = balance + 500 WHERE id = 1");

            // Dirty read: connection2 reads uncommitted data
            ResultSet resultSet = statement2.executeQuery("SELECT balance FROM bank_account WHERE id = 1");
            resultSet.next();
            int balance = resultSet.getInt(1);
            connection2.commit();

            connection1.rollback();

            System.out.println("Balance: " + balance);

        }
    }

    /**
     * This method is used to simulate the Ghost Update anomaly.
     * @param connection1 This is the first connection.
     * @param connection2 This is the second connection.
     * @throws SQLException If a database access error occurs.
     */
    private static void simulateGhostUpdate(Connection connection1, Connection connection2) throws SQLException {
        int id = 0;
        int balance = 0;

        try (Statement statement1 = connection1.createStatement();
             Statement statement2 = connection2.createStatement()) {

            statement1.setQueryTimeout(5);
            statement2.setQueryTimeout(5);

            statement1.executeUpdate("INSERT INTO bank_account (id, balance) VALUES (2, 100)");

            // Ghost update: connection2 reads data that doesn't exist
            ResultSet resultSet = statement2.executeQuery("SELECT id, balance FROM bank_account WHERE id = 2");
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                balance = resultSet.getInt(2);
            }
            connection2.commit();

            connection1.rollback();

            if (id != 0) {
                System.out.println("ID: " + id + ", Balance: " + balance);
            } else {
                System.out.println("No ghost update");
            }
        }
    }

    /**
     * This method is used to simulate the Inconsistent Read anomaly.
     * @param connection1 This is the first connection.
     * @param connection2 This is the second connection.
     * @throws SQLException If a database access error occurs.
     */
    private static void simulateInconsistentRead(Connection connection1, Connection connection2) throws SQLException {
        try (Statement statement1 = connection1.createStatement();
             Statement statement2 = connection2.createStatement()) {

            statement1.setQueryTimeout(5);
            statement2.setQueryTimeout(5);

            ResultSet resultSet1 = statement1.executeQuery("SELECT balance FROM bank_account WHERE id = 1");
            resultSet1.next();
            int balance1 = resultSet1.getInt(1);

            statement2.executeUpdate("UPDATE bank_account SET balance = balance + 100 WHERE id = 1");
            connection2.commit();

            // Inconsistent read: connection2 reads data that has been modified
            ResultSet resultSet2 = statement1.executeQuery("SELECT balance FROM bank_account WHERE id = 1");
            resultSet2.next();
            int balance2 = resultSet2.getInt(1);

            connection1.commit();

            System.out.println("First read balance: " + balance1);
            System.out.println("Second read balance: " + balance2);

        }
    }

    /**
     * This method is used to simulate the Phantom Read anomaly.
     * @param connection1 This is the first connection.
     * @param connection2 This is the second connection.
     * @throws SQLException If a database access error occurs.
     */
    private static void simulatePhantomRead(Connection connection1, Connection connection2) throws SQLException {
        try (Statement statement1 = connection1.createStatement();
             Statement statement2 = connection2.createStatement()) {

            statement1.setQueryTimeout(5);
            statement2.setQueryTimeout(5);

            ResultSet resultSet1 = statement1.executeQuery("SELECT COUNT(*) FROM bank_account WHERE balance > 50");
            resultSet1.next();
            int count1 = resultSet1.getInt(1);

            statement2.executeUpdate("INSERT INTO bank_account (id, balance) VALUES (2, 200)");
            connection2.commit();

            // Phantom read: connection2 reads data that has been inserted
            ResultSet resultSet2 = statement1.executeQuery("SELECT COUNT(*) FROM bank_account WHERE balance > 50");
            resultSet2.next();
            int count2 = resultSet2.getInt(1);

            connection1.commit();

            System.out.println("First read count: " + count1);
            System.out.println("Second read count: " + count2);

        }
    }
}