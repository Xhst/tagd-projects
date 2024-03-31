delete from r3;

create or replace function load_r3(table_size integer, h_max integer, i_max integer) 
    returns void as $$
begin
    for i in 1..table_size loop
        insert into r3 (g,h,i)
            values(
                i, 
                floor(random()*h_max+1),
                floor(random()*i_max)+1 
            );
    end loop;
end;

$$ language plpgsql;
select load_r3(300000, 5000, 500);