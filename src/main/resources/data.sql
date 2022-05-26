-- SAMPLE COURIER DEFS
insert into courier (id, name, latest_latitude, latest_longitude, total_distance, created_date, created_by)
values (1, 'can', 0, 0, 0, CURRENT_TIMESTAMP(), 'init_script');

-- STORES
insert into store (id, name, latitude, longitude, created_date, created_by)
values (1, 'Ataşehir MMM Migros', '40.9923307', '29.1244229', CURRENT_TIMESTAMP(), 'init_script');
insert into store (id, name, latitude, longitude, created_date, created_by)
values (2, 'Novada MMM Migros', '40.986106', '29.1161293', CURRENT_TIMESTAMP(), 'init_script');
insert into store (id, name, latitude, longitude, created_date, created_by)
values (3, 'Beylikdüzü 5M Migros', '41.0066851', '28.6552262', CURRENT_TIMESTAMP(), 'init_script');
insert into store (id, name, latitude, longitude, created_date, created_by)
values (4, 'Ortaköy MMM Migros', '41.055783', '29.0210292', CURRENT_TIMESTAMP(), 'init_script');
insert into store (id, name, latitude, longitude, created_date, created_by)
values (5, 'Caddebostan MMM Migros', '40.9632463', '29.0630908', CURRENT_TIMESTAMP(), 'init_script');