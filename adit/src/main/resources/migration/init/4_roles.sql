insert into role(role_id, name)
values(1, 'admin'),
      (2,'client');

insert into user_role(user_id, role_id)
values(1,1),
      (2,2),
      (3,2),
      (4,2),
      (5,2);