delete from r2;

create or replace function load_r2(table_size integer, e_max integer, f_max integer) 
    returns void as $$
begin
    for i in 1..table_size loop
        insert into r2 (d,e,f)
            values(
                i, 
                floor(random()*e_max+1),
                floor(random()*f_max)+1 
            );
    end loop;
end;

$$ language plpgsql;
select load_r2(500000, 2000, 300000);