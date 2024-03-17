--Task table
CREATE TABLE task (
                      id BIGINT GENERATED BY DEFAULT AS IDENTITY,
                      short_desc varchar(255) not null,
                      details text,
                      start_date timestamp not null,
                      end_date timestamp
);

INSERT INTO task (short_desc, details, start_date, end_date)
VALUES ('Complete Task CRUD', 'Get by ID and delete', '2024-03-17 14:39', '2024-03-17 18:00');


create sequence task_sequence start with 1 increment by 1;