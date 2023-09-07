-- liquibase formatted sql

-- changeset vanthinh:add-user-type-to-users-table.sql

alter table users add column user_type varchar(250);