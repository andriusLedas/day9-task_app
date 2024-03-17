--Task table
CREATE TABLE task (
                      id BIGINT GENERATED BY DEFAULT AS IDENTITY,
                      short_desc varchar(255) not null,
                      details text,
                      start_date timestamp not null,
                      end_date timestamp
);

create sequence task_sequence start with 1 increment by 1;