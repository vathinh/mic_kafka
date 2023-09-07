-- liquibase formatted sql

-- changeset vanthinh:add-users-titles-table.sql


INSERT INTO titles (name, code, created_by, created_date, modified_date, delete_flag, version)
VALUES
    ('Mr.', 'Mr', 'Admin', NOW(), NOW(), false, 1),
    ('Mrs.', 'Mrs', 'Admin', NOW(), NOW(), false, 2),
    ('Miss', 'Miss', 'Admin', NOW(), NOW(), false, 3),
    ('Dr.', 'Dr', 'Admin', NOW(), NOW(), false, 4),
    ('Prof.', 'Prof', 'Admin', NOW(), NOW(), false, 5);


INSERT INTO users (title_id, first_name, last_name, email, phone, keycloak_id, created_by, created_date, modified_date, delete_flag, version)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '123-456-7890', 'johndoe', 'Admin', NOW(), NOW(), false, 1),
    (2, 'Alice', 'Smith', 'alice.smith@example.com', '987-654-3210', 'alicesmith', 'Admin', NOW(), NOW(), false, 2),
    (3, 'Bob', 'Johnson', 'bob.johnson@example.com', '555-123-4567', 'bobjohnson', 'Admin', NOW(), NOW(), false, 3),
    (4, 'Eve', 'Davis', 'eve.davis@example.com', '111-222-3333', 'evedavis', 'Admin', NOW(), NOW(), false, 4),
    (5, 'Charlie', 'Brown', 'charlie.brown@example.com', '444-555-6666', 'charliebrown', 'Admin', NOW(), NOW(), false, 5);
