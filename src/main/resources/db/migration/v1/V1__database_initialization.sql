CREATE TABLE employee
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100),
    surname    VARCHAR(100),
    patronymic VARCHAR(100),
    position   VARCHAR(100)
);

CREATE TABLE project
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100),
    description VARCHAR(2000)
);

CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    status VARCHAR(100),
    start DATE,
    finish DATE,
    estimate INTEGER,
    employee_id INTEGER REFERENCES employee(id) ON DELETE CASCADE,
    project_id INTEGER REFERENCES project(id) ON DELETE CASCADE
)