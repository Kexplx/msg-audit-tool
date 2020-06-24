DELETE FROM scope;
DELETE FROM audit;
DELETE FROM fac_crit;
insert into audit (ID, NAME, START_DATE, STATUS, CREATION_DATE) values (1, 'TestAudit', '2020-06-20', 'ACTIVE', '2020-06-20');
insert into fac_crit (ID, NAME) values(1, 'TestFactor');
insert into fac_crit (ID, NAME) values(2, 'AnotherTestFactor');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(3, 1,'TestCriteria');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(4, 1,'RandomTestCriteria');
insert into fac_crit (ID, REFERENCE_ID, NAME,) values(5, 1,'AnotherTestCriteria');
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1,2, false);
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1,3, false)