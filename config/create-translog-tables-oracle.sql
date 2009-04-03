-- This is an example for Oracle

drop TABLE TRANS_LOG;

CREATE TABLE TRANS_LOG (
  id INT NOT NULL PRIMARY KEY,
  broker VARCHAR(100) NOT NULL,
  message_flow VARCHAR(100) NOT NULL,
  component VARCHAR(100),
  log_level VARCHAR(10) NOT NULL,
  message VARCHAR(2000),
  message_id VARCHAR(100),
  exception CLOB,
  update_time TIMESTAMP(6)
);

drop TABLE TRANS_LOG_BUSINESS;

CREATE TABLE TRANS_LOG_BUSINESS (
  id INT NOT NULL PRIMARY KEY,
  log_id INT NOT NULL,
  business_id VARCHAR(100) NOT NULL
);

CREATE SEQUENCE TRANS_LOG_SEQ;

-- TODO foreign key
-- TODO index
