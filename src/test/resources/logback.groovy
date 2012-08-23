import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.turbo.MarkerFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.spi.FilterReply
import ch.qos.logback.core.status.OnConsoleStatusListener

import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.TRACE
// always a good idea to add an on console status listener
statusListener(OnConsoleStatusListener)

appender("CONSOLE", ConsoleAppender) {
    turboFilter(MarkerFilter) {
        marker = "EVENT"
        onMatch = FilterReply.DENY
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %-6marker %logger{36} - %msg%n"
    }
}

root(INFO, ["CONSOLE"])
logger("com.ibm.broker.plugin", TRACE)