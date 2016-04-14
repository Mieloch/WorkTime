INSERT INTO user_roles (
  id, type)
VALUES (1, 'USER');

INSERT INTO users (
  id, active, email, login, password, registration_date)
VALUES (1, TRUE, 'demo@demo.pl', 'demo', 'lAfX+fOl9sWOy5Eha3BrMQW4n/YYXRjr', localtimestamp);
INSERT INTO users_user_roles (
  users_id, user_roles_id)
VALUES (1, 1);
INSERT INTO security (
  id, key, name)
VALUES (1, 'fokabezoka', 'TEXT_ENCRYPTION_KEY');

