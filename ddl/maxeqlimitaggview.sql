CREATE OR REPLACE VIEW "vweqsitelimitaggview" AS
SELECT mx.policyID,mx.county,t.max_eq_site_limit
FROM (
    SELECT county,max(eq_site_limit) as max_eq_site_limit
    FROM vwflinsurance
    GROUP BY county
    ) t JOIN vwflinsurance mx ON mx.county = t.county AND t.max_eq_site_limit = mx.eq_site_limit

