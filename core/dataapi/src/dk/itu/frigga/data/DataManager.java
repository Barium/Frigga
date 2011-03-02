/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.itu.frigga.data;

import dk.itu.frigga.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Tommy
 */
public class DataManager extends Singleton
{
    private final HashMap<String, DataConnection> connections = new HashMap<String, DataConnection>();
    private static DataManager instance;

    private DataManager()
    {
        // Singleton, we don't want outsiders to create an instance of this
        // class.

        // @TODO (tommy): Load stored connections from configuration.
    }

    public static synchronized DataManager getInstance()
    {
        if (instance == null)
        {
            instance = new DataManager();
        }

        return instance;
    }

    /**
     * Creates a new data connection with a given group name. Use this method to
     * create connections to different databases. For instance if you want a
     * connection to a database in SQLite called log.db, which you will use for
     * logging, give it a group saying name (log could be an option) and add
     * the connection like this:
     * {@code
     * DataManager manager = DataManager.getInstance();
     * manager.addConnection("log",
     *     new DataConnection("jdbc", "sqlite", "log.db", "org.sqlite.JDBC"));
     * boolean found = manager.hasConnection("log"); // this is now true.
     * }
     *
     * @param group      The name of the group this database connection belongs
     *                   to. This has to be unique, if a group with the given
     *                   name already exists, it will be overwritten with this
     *                   new one.
     * @param connection The connection to associate with the group. An instance
     *                   of the underlying JDBC connection will be returned when
     *                   a connection of this type is requested.
     */
    public void addConnection(final String group, DataConnection connection)
    {
        connections.put(group, connection);
    }

    /**
     * Finds out whether a group with the given name has been registered in the
     * DatabaseManager.
     *
     * @param group The name of the group to retrieve.
     * @return Returns true if the connection is registered, false if not.
     */
    public boolean hasConnection(final String group)
    {
        return connections.containsKey(group);
    }

    /**
     * Requests a connection to a database for the given group, use this to 
     * access the database, run queries and generally interact with the
     * database.
     * 
     * @param group The name of the group, whose database connection to
     *              retrieve.
     * @return Returns a Connection object, with an open connection to the
     *         specified group datatbase.
     * @throws SQLException
     * @throws DataGroupNotFoundException
     * @throws UnknownDataDriverException
     */
    public Connection requestConnection(final String group) throws SQLException, DataGroupNotFoundException, UnknownDataDriverException
    {
        if (connections.containsKey(group))
        {
            DataConnection connection = connections.get(group);

            // This call does nothing if the connection is already initialized.
            connection.initialize();

            return DriverManager.getConnection(connection.getConnectionUrl());
        }

        throw new DataGroupNotFoundException(group);
    }
}
