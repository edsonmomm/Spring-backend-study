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
SET @service_device_windows = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Antivirus', 5, 0, @WINDOWS);
SET @service_antivirus_windows = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Backup', 3, 0, @WINDOWS);
SET @service_backup_windows = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'PSA', 2, 0, @WINDOWS);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Screen Share', 1, 0, @WINDOWS);
SET @service_screen_windows = identity();

-- Services for windows server
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Device Value', 4, 1, @WINDOWS_SERVER); -- Device value is mandatory
SET @service_device_windowss = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Antivirus', 5, 0, @WINDOWS_SERVER);
SET @service_antivirus_windowss = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Backup', 3, 0, @WINDOWS_SERVER);
SET @service_backup_windowss = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'PSA', 2, 0, @WINDOWS_SERVER);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES (NULL,'Screen Share', 1, 0, @WINDOWS_SERVER);
SET @service_screen_windowss = identity();

-- Services for MAC
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Device Value', 4, 1, @MAC); -- Device value is mandatory
SET @service_device_mac = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Antivirus', 7, 0, @MAC);
SET @service_antivirus_mac = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Backup', 3, 0, @MAC);
SET @service_backup_mac = identity();
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'PSA', 2, 0, @MAC);
INSERT INTO SERVICE (ID, DESCRIPTION, VALUE, MANDATORY, DEVICE_TYPE_ID) VALUES(NULL,'Screen Share', 1, 0, @MAC);
SET @service_screen_mac = identity();

-- End of services
------------------------------


------------------------------
-- For testing, create some windows devices
INSERT INTO DEVICE VALUES (null,'FIRST WIN SYSTEM',@WINDOWS);
SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_windows, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_windows, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_windows, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_windows, @device);

--INSERT INTO DEVICE VALUES (null,'SECOND WIN SYSTEM',@WINDOWS);
--SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
--INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_windows, @device);
--INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_windows, @device);
--INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_windows, @device);
--INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_windows, @device);

-- For testing, create some windows server devices
INSERT INTO DEVICE VALUES (null,'FIRST WINSERVER SYSTEM',@WINDOWS_SERVER);
SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_windowss, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_windowss, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_windowss, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_windowss, @device);

-- For testing, create some mac devices
INSERT INTO DEVICE VALUES (null,'FIRST MAC SYSTEM',@MAC);
SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_mac, @device);

INSERT INTO DEVICE VALUES (null,'SECOND MAC SYSTEM',@MAC);
SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_mac, @device);

INSERT INTO DEVICE VALUES (null,'THIRD MAC SYSTEM',@MAC);
SET @device = identity();
-- Insert the services chosen for the device (service psa is not included)
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_device_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_antivirus_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_backup_mac, @device);
INSERT INTO DEVICE_SERVICE VALUES (NULL,@service_screen_mac, @device);

-- End of devices
------------------------------
