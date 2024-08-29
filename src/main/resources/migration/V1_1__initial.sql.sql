-- V1__create_work_order_timesheet_job_tables.sql

CREATE SEQUENCE IF NOT EXISTS timesheet_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tb_timesheet
(
    id_timesheet   INTEGER PRIMARY KEY DEFAULT nextval('timesheet_seq'),
    timesheet_name VARCHAR(255) NOT NULL,
    timesheet_date TIMESTAMP    NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE IF NOT EXISTS work_order_seq
    START WITH 1
    INCREMENT BY 1;

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

CREATE SEQUENCE IF NOT EXISTS job_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS tb_jobs
(
    id_job        INTEGER PRIMARY KEY DEFAULT nextval('job_sequence'),
    job_name      VARCHAR(255) NOT NULL,
    job_duration  INTEGER      NOT NULL,
    work_order_id INTEGER,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_work_order_id FOREIGN KEY (work_order_id) REFERENCES tb_work_order (id_work_order)
);
