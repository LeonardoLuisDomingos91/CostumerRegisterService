INSERT INTO profile(name) VALUES ('ADMIN');
INSERT INTO profile(name) VALUES ('GERENTE');
INSERT INTO profile(name) VALUES ('USUARIO');

INSERT INTO user_application(email, login, password)
    VALUES ('admin@gmail.com', 'admin',
        '$2a$10$0Q7s.jPoVc9RLnB4EjqMGO0btiVgpyilCP8H3b1sHH89s6XlQpL42');

INSERT INTO user_application(email, login, password)
    VALUES ('gerente@gmail.com', 'gerente',
        '$2a$10$vj4AkTt.N9pcpSJGTANAbOo5P9r47V6Z2fqTqdjyqKRwF783vo7Lu');

INSERT INTO user_application(email, login, password)
    VALUES ('usuario@gmail.com', 'usuario',
        '$2a$10$vVnkuXVzsLuJ0J3d4kVC0unJiqDmHrC8O3Xr4iPutKxx6Iw4CD/PK');

INSERT INTO user_profile(user_id, profile_id) VALUES (1, 1);
INSERT INTO user_profile(user_id, profile_id) VALUES (2, 2);
INSERT INTO user_profile(user_id, profile_id) VALUES (3, 3);