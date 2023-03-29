\c clevertec

INSERT INTO market.product(
	description, price, quantity, is_discount)
	VALUES
        ('Loren Ipsum',1.55,10,true),
        ('Dolor',2.34,10,false),
        ('Sir amet',3.32,10,false),
        ('Consectetur adiping',10.50,10,false),
        ('Elit',3.12,10,false),
        ('Suspendisse eget',0.45,10,false),
        ('Placerat massa',37.34,10,false),
        ('Aenean vulputate',17.43,10,true),
        ('Quam ac eleifend',1.50,10,false),
        ('Pharetra',0.99,10,false),
        ('Integer',4.23,10,false),
        ('Magna in',32.45,10,false),
        ('Loren scelerisque',54.30,10,false),
        ('Efficitur',2.25,10,false),
        ('Aliquam',3.50,10,false),
        ('Erat volutpat',18.50,10,false),
        ('Etian bibendum',9.75,10,false),
        ('Mauris mauris',0.65,10,false),
        ('Eget eleifend',1.75,10,false),
        ('Justo pulvinar',10.12,10,true),
        ('Quisque',8.17,10,false),
        ('Ullamcorper',5.63,10,false),
        ('At velit',17.80,10,false),
        ('In feugiat',9.99,10,false),
        ('Aliquet venenatis',7.55,10,false),
        ('Nisi id',0.45,10,false),
        ('Malesuada vestibu mi',10.35,10,false),
        ('Sictum lacus',53.70,10,false),
        ('Nunc tempor',0.25,10,false);

INSERT INTO market.discount_card (card_number, discount)
	VALUES
        (1234, 10),
        (2345, 10),
        (3456, 10);

insert into  market.alcohol (name, country, vol, price, quantity)
values
	('Whisky', 'USA', 40.0, 123.4, 10),
	('Vino', 'Moldova', 20.0, 55.3, 10),
	('Vodka', 'Russia', 40.0, 1.4, 10),
	('Night', 'Russia', 35.0, 14.2, 10),
	('Absent', 'Belarus', 65.0, 99.9, 10),
	('Moonshine', 'Ukraine', 40.0, 999.9, 10),
	('Tekla', 'Mexico', 41.0, 76, 10),
	('Shamanism', 'Belarus', 13.0, 16, 10),
	('Every Year', 'Belarus', 15.0, 14, 10),
	('ChaCha', 'Georgia', 99.0, 0.5, 10),
	('Rom', 'Portugal', 40.0, 52, 10);
