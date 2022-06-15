insert into positions(name)
values ('Доцент'),
       ('Професор'),
       ('Старший викладач'),
       ('Провідний фахівець');

insert into degrees(name)
values ('Кандидат технічних наук');

insert into scientific_titles(name)
values ('Доцент');

INSERT INTO faculties(name)
VALUES ('Електроніки та інформаційних технологій'),
       ('Технічних систем та енергоефективних технологій');
INSERT INTO departments(name, faculty_id)
VALUES ('Інформаційні технології проектування', 1),
       ('Інформаційні технології', 1);
INSERT INTO users(first_name, last_name, patronymic, department_id, email, position_id, degree_id, scientific_title_id)
VALUES ('Анна', 'Марченко', 'Вікторівна', 1, 'a.marchenko@socio.sumdu.edu.ua', 1, 1, 1),
       ('Віра', 'Шендрик', 'Вікторівна', 1, 'v.shendryk@cs.sumdu.edu.ua', 1, 1, 1);
INSERT INTO user_credentials(login, password, user_id, user_role_id, is_user_enabled)
VALUES ('Anna Marchenko', 'test', 3, 0, 0),
       ('Vira Viktorivna', 'admin', 4, 1, 0);