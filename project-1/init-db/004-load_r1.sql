delete from r1;

create or replace function load_r1(table_size integer, b_max integer, c_max integer) 
    returns void as $$
begin
    for i in 1..table_size loop
        insert into r1 (a,b,c)
            values(
                i, 
                floor(random()*b_max+1),
                floor(random()*c_max)+1 
            );
    end loop;
end;

$$ language plpgsql;
select load_r1(1000000, 100, 500000);