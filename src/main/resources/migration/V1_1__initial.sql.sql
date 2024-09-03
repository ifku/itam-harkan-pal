-- Drop the existing sequence if it exists
DROP SEQUENCE IF EXISTS timesheet_seq;
DROP SEQUENCE IF EXISTS work_order_seq;
DROP SEQUENCE IF EXISTS job_seq;


-- Create the sequence again
CREATE SEQUENCE timesheet_seq
    START WITH 101
    INCREMENT BY 1;

CREATE SEQUENCE work_order_seq
    START WITH 101
    INCREMENT BY 1;

CREATE SEQUENCE job_seq
    START WITH 101
    INCREMENT BY 1;

-- Create the table with the sequence
CREATE TABLE IF NOT EXISTS tb_timesheet
(
    id_timesheet   INTEGER PRIMARY KEY DEFAULT nextval('timesheet_seq'),
    timesheet_name VARCHAR(255) NOT NULL,
    timesheet_date TIMESTAMP    NOT NULL,
    created_at     TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS tb_work_order
(
    id_work_order       INTEGER PRIMARY KEY DEFAULT nextval('work_order_seq'),
    work_order_code     INTEGER      NOT NULL,
    work_order_name     VARCHAR(255) NOT NULL,
    work_order_duration INTEGER      NOT NULL,
    timesheet_id        INTEGER,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_timesheet_id FOREIGN KEY (timesheet_id) REFERENCES tb_timesheet (id_timesheet)
);

CREATE TABLE IF NOT EXISTS tb_jobs
(
    id_job        INTEGER PRIMARY KEY DEFAULT nextval('job_seq'),
    job_name      VARCHAR(255) NOT NULL,
    job_duration  INTEGER      NOT NULL,
    work_order_id INTEGER,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_work_order_id FOREIGN KEY (work_order_id) REFERENCES tb_work_order (id_work_order)
);
