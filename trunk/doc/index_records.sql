DROP INDEX radioname_index;
DROP INDEX radiodiag_index;
DROP INDEX radiodesc_index;

CREATE INDEX radioname_index ON radiology_record(patient_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX radiodiag_index ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX radiodesc_index ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;

DROP INDEX uname_index;
DROP INDEX fname_index;
DROP INDEX lname_index;

CREATE INDEX uname_index ON persons(user_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX fname_index ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX lname_index ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT;