DROP SEQUENCE pic_id_sequence;
DROP SEQUENCE rec_id_sequence;

DROP INDEX radioname_index;
DROP INDEX radiodiag_index;
DROP INDEX radiodesc_index;

DROP INDEX uname_index;
DROP INDEX fname_index;
DROP INDEX lname_index;

COL maxid NEW_VALUE _maxid
SELECT NVL(MAX(record_id),0)+1 maxid from radiology_record;
CREATE SEQUENCE rec_id_sequence NOMAXVALUE START WITH &&_maxid INCREMENT BY 1;

COL maxid NEW_VALUE _maxid
SELECT NVL(MAX(image_id),0)+1 maxid FROM pacs_images;
CREATE SEQUENCE pic_id_sequence NOMAXVALUE START WITH &&_maxid INCREMENT BY 1;

CREATE INDEX radioname_index ON radiology_record(patient_name) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');
CREATE INDEX radiodiag_index ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');
CREATE INDEX radiodesc_index ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');

CREATE INDEX uname_index ON persons(user_name) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');
CREATE INDEX fname_index ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');
CREATE INDEX lname_index ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS ('SYNC ( ON COMMIT)');