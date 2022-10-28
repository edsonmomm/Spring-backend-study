INSERT INTO SAMPLE VALUES('1', 'VALUE1');
INSERT INTO SAMPLE VALUES('2', 'VALUE2');

------------------------------
-- Create few types of device
INSERT INTO DEVICE_TYPE VALUES(null,'WINDOWS');
SET @WINDOWS = identity();

INSERT INTO DEVICE_TYPE VALUES(null,'WINDOWS SERVER');
SET @WINDOWS_SERVER = identity();

INSERT INTO DEVICE_TYPE VALUES(null,'MAC');
SET @MAC = identity();

-- End of devices
------------------------------


------------------------------
-- Create services by device
-- Services for windows
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Device Value', 4, 1, @WINDOWS); -- Device value is mandatory
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Antivirus', 5, 0, @WINDOWS);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Backup', 3, 0, @WINDOWS);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'PSA', 2, 0, @WINDOWS);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Screen Share', 1, 0, @WINDOWS);

-- Services for windows server
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Device Value', 4, 1, @WINDOWS_SERVER); -- Device value is mandatory
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Antivirus', 5, 0, @WINDOWS_SERVER);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Backup', 3, 0, @WINDOWS_SERVER);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'PSA', 2, 0, @WINDOWS_SERVER);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Screen Share', 1, 0, @WINDOWS_SERVER);

-- Services for MAC
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Device Value', 4, 1, @MAC); -- Device value is mandatory
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Antivirus', 7, 0, @MAC);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Backup', 3, 0, @MAC);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'PSA', 2, 0, @MAC);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Screen Share', 1, 0, @MAC);

-- End of services
------------------------------


------------------------------
-- For testing, create some windows devices
INSERT INTO DEVICE VALUES (null,'FIRST WIN SYSTEM',@WINDOWS);
INSERT INTO DEVICE VALUES (null,'SECOND WIN SYSTEM',@WINDOWS);

-- For testing, create some windows server devices
INSERT INTO DEVICE VALUES (null,'FIRST WINSERVER SYSTEM',@WINDOWS_SERVER);

-- For testing, create some mac devices
INSERT INTO DEVICE VALUES (null,'FIRST MAC SYSTEM',@MAC);
INSERT INTO DEVICE VALUES (null,'SECOND MAC SYSTEM',@MAC);
INSERT INTO DEVICE VALUES (null,'THIRD MAC SYSTEM',@MAC);

-- End of devices
------------------------------
