-- This is an example for MySQL

drop TABLE TRANS_LOG;

CREATE TABLE TRANS_LOG (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	broker VARCHAR(100) NOT NULL,
  message_flow VARCHAR(100) NOT NULL,
  component VARCHAR(100),
  level VARCHAR(10) NOT NULL,
	message TEXT,
  message_id VARCHAR(100),
  exception TEXT,
  update_time DATETIME
);

drop TABLE TRANS_LOG_BUSINESS;

CREATE TABLE TRANS_LOG_BUSINESS (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  log_id INT NOT NULL,
  business_id VARCHAR(100) NOT NULL
);

-- TODO foreign key
-- TODO index

INSERT INTO TRANS_LOG (broker, message_flow, level, message, message_id, update_time) values('WMB1', 'MyFlow', 'INFO', 'Hello world', 'Msg123', '2009:02:16T08:43:00');
