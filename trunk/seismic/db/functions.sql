drop function if exists get_distance;
delimiter $$
create function get_distance(lng1 decimal(20,17),lat1 decimal(20,17),lng2 decimal(20,17),lat2 decimal(20,17))
returns double
begin
	declare s DOUBLE;
    declare radLat1 DOUBLE;
    declare radLat2 DOUBLE;
    declare a DOUBLE;
    declare b DOUBLE;

    set radLat1=lat1*PI()/180.0;
    set radLat2=lat2*PI()/180.0;
    set a=radLat1-radLat2;
    set b=lng1*PI()/180.0-lng2*PI()/180.0;

    set s=2 * ASIN(SQRT(POWER(SIN(a / 2), 2) +
             COS(radLat1) * COS(radLat2) * POWER(SIN(b / 2), 2)));
    set s=s*6378.137;
    set s=ROUND(s*10000,3)/10000;
    return s;
end $$
delimiter ;