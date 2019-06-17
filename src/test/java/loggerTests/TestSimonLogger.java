package loggerTests;

import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestSimonLogger {

    private ILogger logger = Logger.getInstance();

    @Test
    public void logExceptionFatalTest()
    {
        Exception e = new Exception();
        logger.log(e);
        Assert.assertEquals(LogLevel.FATAL, logger.getLastLog().getLogLevel());
    }

    @Test
    public void logExceptionWarningTest()
    {
        Exception e = new Exception();
        logger.log(e.getMessage(), LogLevel.WARNING);
        Assert.assertEquals(LogLevel.WARNING, logger.getLastLog().getLogLevel());
    }

    @Test
    public void logExceptionErrorTest()
    {
        Exception e = new Exception();
        logger.log(e.getMessage(), LogLevel.ERROR);
        Assert.assertEquals(LogLevel.ERROR, logger.getLastLog().getLogLevel());
    }

    @Test
    public void logExceptionDebugTest()
    {
        Exception e = new Exception();
        logger.log(e.getMessage(), LogLevel.DEBUG);
        Assert.assertEquals(LogLevel.DEBUG, logger.getLastLog().getLogLevel());
    }
}
