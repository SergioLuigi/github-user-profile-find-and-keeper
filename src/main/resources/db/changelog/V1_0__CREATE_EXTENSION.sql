--liquibase formatted sql
--changeset sergioluigi:create-extension-uuid-ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";