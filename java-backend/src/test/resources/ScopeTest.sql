DELETE FROM scope;
DELETE FROM audit;
DELETE FROM fac_crit;
insert into audit (ID, NAME, START_DATE, STATUS, CREATION_DATE) values (1001, 'TestAudit', '2020-06-20', 'ACTIVE', '2020-06-20');
insert into fac_crit (ID, NAME, GOAL) values(1001, 'TestFactor', 'TestGoal');
insert into fac_crit (ID, NAME, GOAL) values(1002, 'AnotherTestFactor', 'AnotherTestGoal');
insert into fac_crit (ID, REFERENCE_ID, NAME, GOAL) values(1003, 1001,'TestCriteria', 'TestCriteriaGoal');
insert into fac_crit (ID, REFERENCE_ID, NAME, GOAL) values(1004, 1001,'RandomTestCriteria', 'RandomTestCriteriaGoal');
insert into fac_crit (ID, REFERENCE_ID, NAME, GOAL) values(1005, 1001,'AnotherTestCriteria', 'AnotherTestCriteriaGoal');
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1001,1002, false);
insert into scope (AUDIT_ID, FACCRIT_ID, REMOVED) values(1001,1003, false)