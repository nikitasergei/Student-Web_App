create extension if not exists pgcrypto;
update teacher set password = crypt (password, gen_salt('bf', 8));