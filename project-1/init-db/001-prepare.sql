/* 
Create the tables r1(a,b,c) r2(d,e,f) r3(g,h,i)
all the fields are numeric with a, b and g as primary keys
*/
drop table if exists r1;
create table r1 (a numeric not null primary key, b numeric, c numeric);

drop table if exists r2;
create table r2 (d numeric not null primary key, e numeric, f numeric);

drop table if exists r3;
create table r3 (g numeric not null primary key, h numeric, i numeric);

/*
Constraints
*/
alter table r1 add constraint fk1 foreign key (c) references r2 (d);
alter table r2 add constraint fk2 foreign key (f) references r3 (g);