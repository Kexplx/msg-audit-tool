DELETE FROM scope;
DELETE FROM audit;
DELETE FROM fac_crit;
insert into audit (ID, NAME, START_DATE, STATUS, CREATION_DATE) values (1001, 'TestAudit', '2020-06-20', 'ACTIVE', '2020-06-20');
insert into fac_crit (ID, NAME) values(1001, 'TestFactor');
insert into fac_crit (ID, NAME) values(1002, 'AnotherTestFactor');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(1003, 1001,'TestCriteria');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(1004, 1001,'RandomTestCriteria');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(1005, 1001,'AnotherTestCriteria');
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1001,1002, false);
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1001,1003, false)