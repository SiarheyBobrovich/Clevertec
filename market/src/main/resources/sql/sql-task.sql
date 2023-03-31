-- Вывести к каждому самолету класс обслуживания и количество мест этого класса
-- WITH VIEW
SELECT
    a.model,
    s.fare_conditions,
    COUNT(s.seat_no) AS seats_count
FROM
    aircrafts a
    JOIN seats s USING (aircraft_code)
GROUP BY
    a.model,
    s.fare_conditions;

-- WITHOUT VIEW
SELECT
    ad.model ->> 'ru' AS model,
    s.fare_conditions,
    COUNT(s.seat_no) AS seats_count
FROM
    aircrafts_data ad
    JOIN seats s USING (aircraft_code)
GROUP BY
    ad.model ->> 'ru',
    s.fare_conditions;
    
-- Найти 3 самых вместительных самолета (модель + кол-во мест)
-- WITH VIEW
SELECT
    a.model,
    sc.seats_count
FROM
    aircrafts a
    JOIN (
        SELECT
            s.aircraft_code,
            COUNT(s.seat_no) AS seats_count
        FROM
            seats s
        GROUP BY
            s.aircraft_code) sc USING (aircraft_code)
ORDER BY
    sc.seats_count DESC
LIMIT 3;

-- WITHOUT VIEW
SELECT
    ad.model ->> 'ru' AS model,
    sc.seats_count
FROM
    aircrafts_data ad
    JOIN (
        SELECT
            s.aircraft_code,
            COUNT(s.seat_no) AS seats_count
        FROM
            seats s
        GROUP BY
            s.aircraft_code) sc USING (aircraft_code)
ORDER BY
    sc.seats_count DESC
LIMIT 3;

--Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
-- WITH VIEW
SELECT
    a.aircraft_code,
    a.model,
    s.seat_no
FROM
    aircrafts a
    JOIN seats s USING (aircraft_code)
WHERE
    s.fare_conditions != 'Economy'
    AND a.model = 'Аэробус A321-200'
ORDER BY
    seat_no;

-- WITHOUT VIEW
SELECT
    ad.aircraft_code,
    ad.model ->> 'ru' AS model,
    s.seat_no
FROM
    aircrafts_data ad
    JOIN seats s USING (aircraft_code)
WHERE
    s.fare_conditions != 'Economy'
    AND ad.model ->> 'ru' = 'Аэробус A321-200'
ORDER BY
    seat_no;

-- Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
-- WITH VIEW
SELECT 
	a.airport_code,
	a.airport_name,
	a.city
FROM airports a
	join (
		SELECT
			a.city
		FROM airports a
		GROUP BY a.city
		HAVING COUNT(a.airport_code) > 1
	) adc USING (city);
	
-- WITHOUT VIEW
SELECT 
	ad.airport_code,
	ad.airport_name->>'ru' as airport_name,
	ad.city->>'ru' as city
FROM airports_data ad
	join (
		SELECT
			ad.city
		FROM airports_data ad
		GROUP BY ad.city
		HAVING COUNT(ad.airport_code) > 1
	) adc USING (city);

-- Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
-- WITH VIEW
SELECT
    a.airport_code,
    a.airport_name,
    a.city
FROM
    airports a
    JOIN (
        SELECT
            a.city
        FROM
            airports a
        GROUP BY
            a.city
        HAVING
            COUNT(a.airport_code) > 1) adc USING (city);

-- WITHOUT VIEW
SELECT
    ad.airport_code,
    ad.airport_name ->> 'ru' AS airport_name,
    ad.city ->> 'ru' AS city
FROM
    airports_data ad
    JOIN (
        SELECT
            ad.city
        FROM
            airports_data ad
        GROUP BY
            ad.city
        HAVING
            COUNT(ad.airport_code) > 1) adc USING (city);

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
-- WITH VIEW:
SELECT
    f.flight_id,
    f.flight_no
FROM
    flights_v f
WHERE
    f.departure_city = 'Екатеринбург'
    AND f.arrival_city = 'Москва'
    AND f.status IN ('Scheduled', 'On Time', 'Delayed')
ORDER BY
    f.scheduled_departure
LIMIT 1;

-- WITHOUT VIEW:
SELECT
    f.flight_id,
    f.flight_no
FROM
    flights f
    JOIN airports da ON f.departure_airport = da.airport_code
    JOIN airports aa ON f.arrival_airport = aa.airport_code
WHERE
    da.city = 'Екатеринбург'
    AND aa.city = 'Москва'
    AND status IN ('Scheduled', 'On Time', 'Delayed')
ORDER BY
    f.scheduled_departure
LIMIT 1;

-- Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)
-- Вариант с 2 билетами
WITH mm AS (
    SELECT
        MAX(tf.amount) AS max,
        MIN(tf.amount) AS min
    FROM
        ticket_flights tf) (
        SELECT
            tf.ticket_no,
            tf.amount
        FROM
            ticket_flights tf
        WHERE
            amount = (
                SELECT
                    max
                FROM
                    mm)
            LIMIT 1)
UNION (
    SELECT
        tf2.ticket_no,
        tf2.amount
    FROM
        ticket_flights tf2
    WHERE
        amount = (
            SELECT
                min
            FROM
                mm)
        LIMIT 1);

-- Все самые дорогие и все самые дешевые билеты
WITH am AS (
    SELECT
        MAX(amount) AS max,
        MIN(amount) AS min
    FROM
        ticket_flights
)
SELECT
    tf.ticket_no,
    tf.amount
FROM
    ticket_flights tf
WHERE
    tf.amount = (
        SELECT
            max
        FROM
            am)
    OR amount = (
        SELECT
            min
        FROM
            am);
-- Написать DDL таблицы Customers , должны быть поля id , firstName, LastName, email , phone. Добавить ограничения на поля ( constraints) .
CREATE TABLE IF NOT EXISTS Customers(
id		BIGSERIAL	PRIMARY KEY,
first_name	VARCHAR(20)	NOT NULL,
last_name	VARCHAR(30) 	NOT NULL,
email		TEXT		NOT NULL,
phone		VARCHAR(13)	NOT NULL
);

ALTER TABLE Customers
    ADD CONSTRAINT unq_email UNIQUE (email);

ALTER TABLE Customers
    ADD CONSTRAINT unq_phone UNIQUE (phone);
 
-- Написать DDL таблицы Orders , должен быть id, customerId, quantity. Должен быть внешний ключ на таблицу customers + ограничения
CREATE TABLE IF NOT EXISTS Orders(
id		BIGSERIAL	PRIMARY KEY,
customer_id	BIGINT		NOT NULL,
quantity	INTEGER 	NOT NULL
);

ALTER TABLE Orders
    ADD CONSTRAINT order_c_id_ON_c_id FOREIGN KEY (customer_id) REFERENCES Customers (id);

-- Написать 5 insert в эти таблицы
insert into customers (first_name, last_name, email, phone)
values 	('Ivanov', 'ivan', 'ivan@mail.ru', '+375(29)3129865'),
	('Smirnov', 'Semen', 'semen@yandex.ru', '+375(25)1134567'),
	('Kudrashov', 'Dmitry', 'dima@gmail.com', '+375(33)5437625'),
	('Gulevich', 'Sergey', 'gulia1976@mail.ru', '+375(44)4442444'),
	('Sabakin', 'Schenok', 'nekotov@gmail.com', '+375(29)8762409');
	
insert into Orders (customer_id, quantity)
values 	((SELECT id FROM customers WHERE first_name = 'Ivanov'), 25),
	((SELECT id FROM customers WHERE phone = '+375(29)8762409'), 6),
	((SELECT id FROM customers WHERE last_name  = 'Dmitry'), 999),
	((SELECT id FROM customers WHERE first_name = 'Smirnov'), 20),
	((SELECT id FROM customers WHERE email = 'gulia1976@mail.ru'), 5);

-- удалить таблицы
DROP TABLE customers, orders CASCADE;

-- Написать свой кастомный запрос ( rus + sql)
-- Вывести имена и количество перелётов тех, кто летал в казань на месте 4C больше 1 раза
SELECT
    COUNT(tf.flight_id) AS count,
    t.passenger_name
FROM
    ticket_flights tf
    JOIN tickets t USING (ticket_no)
    JOIN (
        SELECT
            bp.seat_no,
            bp.flight_id,
            bp.ticket_no
        FROM
            boarding_passes bp
        WHERE
            seat_no = '4C') bp USING (ticket_no, flight_id)
JOIN (
    SELECT
        f.flight_id
    FROM
        flights_v f
    WHERE
        f.status IN ('Arrived', 'Departed')
        AND arrival_city = 'Казань') am USING (flight_id)
GROUP BY
    t.passenger_id,
    t.passenger_name
HAVING
    COUNT(tf.flight_id) > 1;
