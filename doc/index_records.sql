CREATE INDEX radioname_index ON radiology_record(patient_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX radiodiag_index ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX radiodesc_index ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;
