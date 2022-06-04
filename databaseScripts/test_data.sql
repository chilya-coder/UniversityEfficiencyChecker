INSERT INTO users(first_name, last_name, patronymic, department_id, email, position_id)
VALUES ('Анна', 'Марченко', 'Вікторівна', 1, 'a.marchenko@socio.sumdu.edu.ua', 1),
       ('Віра', 'Шендрик', 'Вікторівна', 1, 'v.shendryk@cs.sumdu.edu.ua', 1);

INSERT INTO user_credentials(login, password, user_id, user_role_id, is_user_enabled)
VALUES ('Anna Marchenko', 'test', 1, 0, 1, 0),
       ('Vira Viktorivna', 'admin', 2, 1, 1, 0);

insert into positions(name)
values ('Доцент'),
       ('Професор'),
       ('Старший викладач'),
       ('Провідний фахівець');

insert into departments(name, faculty_id)
values ('Інформаційні технології проектування', 0),
       ('Інформаційні технології', 0);