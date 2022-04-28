-- liquibase formatted sql
-- changeset tiunchik:3
alter table task_user_relations add constraint const_name unique (task_id, user_id);
-- rollback alter table task_user_relations drop constraint const_name;
