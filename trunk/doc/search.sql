SELECT (6*score(1) + 3*score(2) + score(3)) as myscore, record_id, description
FROM radiology_record
WHERE contains(patient_name, 'Steven', 1) > 0 or contains (diagnosis, 'Steven', 2) > 0 or contains(description, 'Steven', 3) > 0
order by myscore desc
