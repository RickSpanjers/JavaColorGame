package helper;

import logging.ILogger;
import logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    private ILogger logger = Logger.getInstance();

    public Connection dbConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi381916;User=dbi381916;Password=Test123Datacon");
        } catch (Exception e) {
            logger.log(e);
        }
        return null;
    }
}
