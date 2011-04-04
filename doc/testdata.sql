-- Test Data 1

@setup.sql;


-- users(user_name,password,class,date_registered)
insert into users values ('a1', 'a1', 'a', '1/jan/2011');
insert into users values ('a2', 'a2', 'a', '1/jan/2011');
insert into users values ('r1', 'r1', 'r', '10/jan/2011');
insert into users values ('r2', 'r2', 'r', '11/jan/2011');
insert into users values ('d1', 'd1', 'd', '1/feb/2011');
insert into users values ('d2', 'd2', 'd', '2/feb/2011');
insert into users values ('d3', 'd3', 'd', '3/feb/2011');
insert into users values ('p1', 'p1', 'p', '1/mar/2011');
insert into users values ('p2', 'p2', 'p', '1/mar/2011');
insert into users values ('p3', 'p3', 'p', '1/mar/2011');
insert into users values ('Steven', 'Steven', 'p', '2/mar/2011');
insert into users values ('Oscar', 'Oscar', 'p', '2/mar/2011');
insert into users values ('Camden', 'Camden', 'p', '3/mar/2011');

-- persons(user_name,first_name,last_name,address,email,phone)
insert into persons values ('a1', 'Admin', 'One', '1 Admin Road', 'a1@radiology.com', '7801111111');
insert into persons values ('a2', 'Admin', 'Two', '2 Admin Road', 'a2@radiology.com', '7801112222');
insert into persons values ('r1', 'Radio', 'One', '1 Radio Ave', 'r1@radiology.com', '7802221111');
insert into persons values ('r2', 'Radio', 'Two', '2 Radio Ave', 'r2@radiology.com', '7802222222');
insert into persons values ('d1', 'Doctor', 'One', '1 Doctor Street', 'd1@radiology.com', '7803331111');
insert into persons values ('d2', 'Doctor', 'Two', '2 Doctor Street', 'd2@radiology.com', '7803332222');
insert into persons values ('d3', 'Doctor', 'Three', '3 Doctor Street', 'd3@radiology.com', '7803333333');
insert into persons values ('p1', 'Patient', 'One', '1 Patient Rd', 'p1@radiology.com', '7804441111');
insert into persons values ('p2', 'Patient', 'Two', '2 Patient Rd', 'p2@radiology.com', '7804442222');
insert into persons values ('p3', 'Patient', 'Three', '3 Patient Rd', 'p3@radiology.com', '7804443333');
insert into persons values ('Steven', 'Steven', 'Maschmeyer', '4255 42 AVE', 'maschmey@ualberta.ca', '7809869063');
insert into persons values ('Oscar', 'Oscar', 'Ramirez', '2 Db Street', 'oramirez@ualberta.ca', '7804445555');
insert into persons values ('Camden', 'Camden', 'Narzt', '3 Db Street', 'narzt@ualberta.ca', '7804446666');

-- family_doctor(doctor_name,patient_name
insert into family_doctor values ('d1', 'p1');
insert into family_doctor values ('d1', 'Steven');
insert into family_doctor values ('d1', 'Oscar');
insert into family_doctor values ('d2', 'p2');
insert into family_doctor values ('d2', 'Camden');
insert into family_doctor values ('d3', 'p3');

-- radiology_record(record_id,patient_name,doctor_name,radiologist_name,test_type,prescribing_date,test_date,diagnosis, description)
insert into radiology_record values (1, 'p1', 'd1', 'r1', 'MRI', '4/mar/2011', '4/mar/2011', 'no cancer', 'Screening for brain cancer.');
insert into radiology_record values (2, 'p1', 'd1', 'r1', 'CAT', '4/mar/2011', '4/mar/2011', 'cancer', 'Screening for lung cancer.');
insert into radiology_record values (3, 'p1', 'd1', 'r1', 'US', '4/mar/2011', '4/mar/2011', 'heart ok', 'Screening for heart problems.');
insert into radiology_record values (4, 'p1', 'd1', 'r1', 'MRI', '4/mar/2011', '4/mar/2011', 'sinitus', 'Screening for sinitus.');
insert into radiology_record values (5, 'p1', 'd2', 'r2', 'CAT', '5/mar/2011', '5/mar/2011', 'kidney stones', 'Screening for kidney stones.');
insert into radiology_record values (6, 'p1', 'd2', 'r2', 'CAT', '5/mar/2011', '5/mar/2011', 'fractured rib', 'Complaining of chest pain - has fractured rib.');
insert into radiology_record values (7, 'p1', 'd1', 'r1', 'MRI', '6/mar/2011', '6/mar/2011', 'no cancer', '2 Screening for brain cancer.');
insert into radiology_record values (8, 'p1', 'd1', 'r1', 'CAT', '7/mar/2011', '7/mar/2011', 'cancer', '2 Screening for lung cancer.');
insert into radiology_record values (9, 'p1', 'd1', 'r1', 'US', '8/mar/2011', '9/mar/2011', 'heart ok', '2 Screening for heart problems.');
insert into radiology_record values (10, 'p1', 'd1', 'r1', 'MRI', '9/mar/2011', '10/mar/2011', 'sinitus', '2 Screening for sinitus.');
insert into radiology_record values (11, 'p1', 'd2', 'r2', 'CAT', '10/mar/2011', '11/mar/2011', 'kidney stones', '2 Screening for kidney stones.');
insert into radiology_record values (12, 'p1', 'd2', 'r2', 'CAT', '11/mar/2011', '12/mar/2011', 'fractured rib', '2 Complaining of chest pain - has fractured rib.');



insert into radiology_record values (13, 'p3', 'd3', 'r1', 'MRI', '7/mar/2011', '7/mar/2011', 'no sinitus', 'Screening for sinitus.');
insert into radiology_record values (14, 'p3', 'd3', 'r1', 'MRI', '4/mar/2011', '4/mar/2011', 'no abnormalities', 'Brain is free from abnormalities.');
insert into radiology_record values (15, 'Steven', 'd2', 'r1', 'MRI', '5/mar/2011', '5/mar/2011', 'arachnoid cyst', 'Determining cause of head pain.');
insert into radiology_record values (16, 'Steven', 'd2', 'r2', 'CAT', '6/mar/2011', '6/mar/2011', 'fractured cranium', 'Determine cause of head inflamation.');
insert into radiology_record values (17, 'Oscar', 'd1', 'r1', 'US', '2/mar/2011', '2/mar/2011', 'heart ok', 'Screening for heart problems.');
insert into radiology_record values (18, 'Camden', 'd2', 'r2', 'CAT', '3/mar/2011', '3/mar/2011', 'sinitus', 'Screening for sinitus.');

-- pacs_images(record_id,image_id,thumbnail,regular_size,full_size)
