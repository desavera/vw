CREATE OR REPLACE VIEW "vwview2" AS
SELECT count(policyid) as total
FROM vwflinsurance
GROUP BY  county 
