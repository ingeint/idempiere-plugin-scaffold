-- This query reads the partners using as filter "is vendor"
select name
from c_bpartner
where isvendor = ?