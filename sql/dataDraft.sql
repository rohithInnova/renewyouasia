insert into POC_DB.TBL_ROLE (role_name)
values ('Customer'),('RM'),('Admin');

select * from POC_DB.TBL_ROLE;

insert into POC_DB.TBL_USER (first_name,last_name,user_name,password,role_id)
values ('admin','admin','admin','admin',(select id from POC_DB.TBL_ROLE where role_name='admin'));

select * from POC_DB.TBL_USER;

