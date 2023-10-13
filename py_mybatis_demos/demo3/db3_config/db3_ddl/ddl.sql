CREATE TABLE user3 (
                       "id" INT8 PRIMARY KEY NOT NULL,
                       "name" VARCHAR ( 64 ) NULL
);

CREATE SEQUENCE user3_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

ALTER TABLE user3 ALTER COLUMN "id" SET DEFAULT nextval( 'user3_id_seq' );