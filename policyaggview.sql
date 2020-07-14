CREATE OR REPLACE VIEW "vwaggview" AS
SELECT count(policyid) as total
FROM vwflinsurance
GROUP BY  county 
