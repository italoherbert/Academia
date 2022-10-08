

delete from matmod where id = (select min(id) from matmod where mat_id=293);
delete from matmod where id = (select min(id) from matmod where mat_id=691);
delete from matmod where id = (select min(id) from matmod where mat_id=704);
delete from matmod where id = (select min(id) from matmod where mat_id=726);
delete from matmod where id = (select min(id) from matmod where mat_id=911);
delete from matmod where id = (select min(id) from matmod where mat_id=1001);
