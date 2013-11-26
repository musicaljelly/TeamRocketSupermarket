drop table Member cascade constraints;
drop table Employee cascade constraints;
drop table Product cascade constraints;
drop table Updates cascade constraints;
drop table Transaction cascade constraints;
drop table CashTransaction cascade constraints;
drop table CreditCardTransaction cascade constraints;


CREATE TABLE Member(
		mid number(9,0) PRIMARY KEY,
		name varchar2(30) NOT NULL,
		homeaddress varchar2(60),
		emailaddress varchar2(50),
		phonenumber varchar2(10)
		);
			
CREATE TABLE Employee(
		eid number(9,0) PRIMARY KEY,
		homeaddress varchar2(60) NOT NULL,
		email varchar2(50),
		name varchar2(30) NOT NULL,
		phone varchar2(10) NOT NULL,
		accountnum varchar2(7),
		transitnum varchar2(5),
		institutionnum varchar(3),
		isEmployer number(1,0) NOT NULL
		);
		
CREATE TABLE Product(
		pid number(9,0) PRIMARY KEY,
		name varchar2(30) NOT NULL,
		stock number(6,0) NOT NULL,
		cost number(6,2) NOT NULL,
		memberprice number(6,2),
		price number(6,2) NOT NULL,
		expiry date
		);
-- If check constraints were supported, we would add the following constraint (amongst many possible others) to the above table:
        -- CONSTRAINT chk_stock CHECK (stock >= 0)
		
CREATE TABLE Updates(
		upid number(9,0) PRIMARY KEY,
		udate date NOT NULL,
		eid number(9,0) NOT NULL,
		pid number(9,0) NOT NULL,
		FOREIGN KEY(eid) REFERENCES Employee(eid),
		FOREIGN KEY(pid) REFERENCES Product(pid) ON DELETE CASCADE
		);

CREATE TABLE Transaction(
		tid number(9,0) PRIMARY KEY,
		quantity number(6,0) NOT NULL,
		tdate date NOT NULL,
		amount number(6,2) NOT NULL,
		type varchar2(10),
		refid number(9,0) UNIQUE,    -- might not work, if SQL doesnt allow unique nulls
		pid number(9,0) NOT NULL,
		eid number(9,0) NOT NULL,
		mid number(9,0),
		FOREIGN KEY (refid) REFERENCES Transaction(tid) ON DELETE CASCADE,
		FOREIGN KEY (pid) REFERENCES Product(pid) ON DELETE CASCADE,
		FOREIGN KEY (eid) REFERENCES Employee(eid),
		FOREIGN KEY (mid) REFERENCES Member(mid) ON DELETE SET NULL
		);
	
				
CREATE TABLE CashTransaction(
		tid number(9,0) PRIMARY KEY,
		FOREIGN KEY (tid) REFERENCES Transaction(tid) ON DELETE CASCADE
		);
		
CREATE TABLE CreditCardTransaction(
		tid number(9,0) PRIMARY KEY,
		cardnum varchar(20),
		cardname varchar(50),
		cardexpiry date,
		FOREIGN KEY (tid) REFERENCES Transaction(tid) ON DELETE CASCADE
		);
		
insert into Member (mid,name,homeaddress,emailaddress,phonenumber) 
	values (854112041,'Robert Longo','1238 Seymour Street, Vancouver, BC, V6B3M7','roblongo@gmail.com','7789515221');
insert into Member (mid,name,emailaddress) values (811041429,'Alexis Borrows','alexisb@hotmail.com');
insert into Member (mid,name,homeaddress) values (800125044,'Danielle Sedan', '3721 Balaclava Street, Vancouver, BC, V6K4E6');
insert into Member (mid,name,phonenumber) values (877102011,'Christine Haggens','6049201123');
insert into Member (mid,name,emailaddress,phonenumber) values (800125031,'Henrique Sedan','handsomeboyhenrique@yahoo.com','7786116655');
insert into Member (mid,name,homeaddress,phonenumber) values (891001456,'Regan Kesley','1190 West 7th Avenue, Vancouver, BC, V6H1B5','6041234567');
insert into Member (mid,name,homeaddress,phonenumber) values (810980023,'Jason Kerrison','3650 West 15th Avenue, Vancouver, BC, V6R2Z6','6049876331'); 
insert into Member (mid,name) values (820218429,'Danny Hamhuisen');

insert into Employee (eid,name,homeaddress,email,phone,accountnum,transitnum,institutionnum,isEmployer) 
	values (952348594,'Ash Ketchum','2129 West 4th, Vancouver, BC, V6K1N9','gottacatchemall@gmail.com','6042046842','5147853','23010','002',0);
insert into Employee (eid,name,homeaddress,email,phone,isEmployer) 
	values (968745963,'Brock Geodude','3145 West 9th, Vancouver, BC, V6R2C7','brocklovesrock@gmail.com','7788529453',0);
insert into Employee (eid,name,homeaddress,email,phone,isEmployer) 
	values (948512569,'Misty Starmie','1945 Balaclava St, Vancouver, BC, V6K4B9','watermisty@gmail.com','6049851256',0);
insert into Employee (eid,name,homeaddress,email,phone,accountnum,transitnum,institutionnum,isEmployer) 
	values (945214963,'Samuel Oak','2250 Wesbrook Mall, Vancouver, BC, V6T2B7','professoroak@outlook.com','6047596451','5489336','27420','001',1);
insert into Employee (eid,name,homeaddress,email,phone,accountnum,transitnum,institutionnum,isEmployer) 
	values (924863556,'Augustine Sycamore','5745 Dalhousie Road, Vancouver, BC, V6T2J1','augsycamore@outlook.com','7789634525','7854126','27420','001',1);
insert into Employee (eid,name,homeaddress,email,phone,isEmployer)
	values (975886938,'Aurea Juniper','2459 Alma Street, Vancouver, BC, V6R2B7','ajuniper@gmail.com','6044788877',0);
insert into Employee (eid,name,homeaddress,email,phone,isEmployer)
	values (979542645,'Dawn Hikari','1935 Lower Mall, Vancouver, BC, V6T1X1','cutiehikari23@outlook.com','6042764441',0);

insert into Product (pid,name,stock,cost,price,expiry) values (282058287, 'Fresh Water',100,2.00,8.25,'2015-03-22');
insert into Product (pid,name,stock,cost,price,expiry) values (298681350, 'Lemonade',115,3.00,12.50,'2014-09-12');
insert into Product (pid,name,stock,cost,price,expiry) values (243874386, 'Soda Pop',210,4.00,10.25,'2014-07-15');
insert into Product (pid,name,stock,cost,price,expiry) values (142578963, 'Rare Candy',180,13.75,35.25,'2014-10-09');
insert into Product (pid,name,stock,cost,price,expiry) values (172757275, 'Chesto Berry',30,1.25,5.00,'2014-01-14');
insert into Product (pid,name,stock,cost,price,expiry) values (126452185, 'Rawst Berry',30,3.25,8.00,'2014-01-05');
insert into Product (pid,name,stock,cost,price,expiry) values (175493110, 'Mushroom',45,3.25,7.50,'2014-01-17');
insert into Product (pid,name,stock,cost,price,expiry) values (123493110, 'Carrot',120,2.25,8.50,'2014-02-28');
insert into Product (pid,name,stock,cost,price,expiry) values (145787122, 'Banana',90,2.25,4.50,'2014-01-04');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (211765319, 'Moomoo Milk',50,3.00,11.50,10.25,'2013-12-15');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (201915712, 'Berry Juice',75,3.00,13.50,12.75,'2014-03-12');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (187571272, 'Cheri Berry',40,1.00,5.50,4.00,'2014-01-04');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (138257329, 'Pecha Berry',30,1.00,6.50,5.75,'2014-01-19');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (166123512, 'Honey',70,9.00,28.50,26.75,'2014-11-22');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (111548445, 'Corn',120,2.00,8.00,6.75,'2014-02-14');
insert into Product (pid,name,stock,cost,price,memberprice,expiry) values (197745884, 'Apple',150,2.00,5.50,4.75,'2013-12-31');

insert into Product (pid,name,stock,cost,price) values (298774445, 'Poke Ball',300,8.00,20.25);
insert into Product (pid,name,stock,cost,price) values (211478525, 'Great Ball',250,28.00,60.00);
insert into Product (pid,name,stock,cost,price) values (347411156, 'Escape Rope',120,15.25,55.00);
insert into Product (pid,name,stock,cost,price) values (310014852, 'Max Repel',180,31.25,75.00);
insert into Product (pid,name,stock,cost,price) values (422511230, 'Potion',140,6.75,20.00);
insert into Product (pid,name,stock,cost,price) values (488012019, 'Super Potion',120,15.00,54.25);
insert into Product (pid,name,stock,cost,price,memberprice) values (274783610, 'Ultra Ball',240,5.50,12.50,10.25);
insert into Product (pid,name,stock,cost,price,memberprice) values (388181563, 'Repel',100,1.25,14.50,13.25);
insert into Product (pid,name,stock,cost,price,memberprice) values (344100254, 'Super Repel',120,2.50,38.50,35.25);
insert into Product (pid,name,stock,cost,price,memberprice) values (492014200, 'Hyper Potion',250,30.50,118.25,113.75);
insert into Product (pid,name,stock,cost,price,memberprice) values (455102458, 'Max Potion',300,62.50,210.25,198.00);
insert into Product (pid,name,stock,cost,price,memberprice) values (400149621, 'Revive',300,44.25,154.75,148.75);


insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type) 
	values (1,5,'2013-10-17',23.75,197745884,968745963,854112041,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type) 
	values (3,3,'2013-10-17',6.00,282058287,952348594,877102011,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type) 
	values (4,2,'2013-10-18',13.50,422511230,979542645,811041429,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type,refid) 
	values (2,5,'2013-10-18',23.75,197745884,968745963,854112041,'Return',1);
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type,refid) 
	values (5,1,'2013-10-18',3.00,282058287,952348594,877102011,'Return',3);
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type,refid)
	values (6,2,'2013-10-19',13.50,422511230,979542645,811041429,'Return',4);
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type) 
	values (7,1,'2013-10-20',148.75,400149621,979542645,820218429,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,mid,type,refid) 
	values (8,1,'2013-10-21',148.75,400149621,979542645,820218429,'Return',7);
insert into Transaction(tid,quantity,tdate,amount,pid,eid,type) 
	values (9,3,'2013-10-22',24.00,111548445,952348594,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,type) 
	values (10,1,'2013-10-22',20.25,298774445,945214963,'Purchase');
insert into Transaction(tid,quantity,tdate,amount,pid,eid,type,refid) 
	values (11,3,'2013-10-23',24.00,111548445,952348594,'Return',9);
    
insert into Transaction(tid, quantity, tdate, amount, pid,eid,mid,type)
    values (20, 2, '2013-10-17', 23.75, 282058287,952348594,854112041,'Purchase');

insert into CashTransaction(tid) values (1);
insert into CashTransaction(tid) values (2);
insert into CashTransaction(tid) values (4);
insert into CashTransaction(tid) values (6);
insert into CashTransaction(tid) values (10);

insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (3,'1234567890','Christine H', '2015-10-10');
insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (5,'1234567890','Christine H', '2015-10-10');
insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (7,'1357246805','D Hamhuisen', '2015-03-07');
insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (8,'1357246805','D Hamhuisen', '2015-03-07');
insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (9,'2468135791','C Sheen', '2015-05-17');
insert into CreditCardTransaction(tid,cardnum,cardname,cardexpiry) values (11,'2468135791','C Sheen', '2015-05-17');

insert into Updates(upid,udate,eid,pid) values (1, '2013-10-19', 945214963,298774445);
insert into Updates(upid,udate,eid,pid) values (2, '2013-10-19', 924863556,400149621);
insert into Updates(upid,udate,eid,pid) values (3, '2013-10-20', 945214963,298681350);
insert into Updates(upid,udate,eid,pid) values (4, '2013-10-19', 924863556,138257329);
insert into Updates(upid,udate,eid,pid) values (5, '2013-10-19', 945214963,344100254);

select * from member;
--      MID NAME 			  			  HOMEADDRESS					 EMAILADDRESS					PHONENUMBE
---------- ------------------------------ ------------------------------ ------------------------------ ----------
--		1 	Bob				  			  321 Bob Street		 		 bob@bob.com					6041231234
--		2	Billy							 							 billy@billy.com
--		3 	Donny			  			  333 Donny Road
--		4 	Maria																						6049876543
--		5 	Ohwell							 							ohwell@well.com					7787787788
		
select * from employee;
--     EID HOMEADDRESS				      	  EMAIL						 NAME						    PHONE      ACCOUNTNUM   
---------- ---------------------------------- -------------------------- ------------------------------ ---------- ------------ 
--		 1 123 Happy Street				      Employee1@market.com		 Employee1					    604324141
--		 2 123 Sad Street				      Employee2@market.com		 Employee2					    604324142
--		 3 123 Funny Street				      Employee3@market.com		 Employee3					    604324143
--	 	 4 123 Angry Street				      Employee4@market.com		 Employee4					    604324144
--	 	 5 123 Weird Street				      Employee5@market.com		 Employee5					    604324145

select * from product;
--     PID NAME 		         STOCK	   COST 	  MEMBERPRICE PRICE 	 EXPIRY
---------- --------------------- --------- ---------- ----------- ---------- --------
--		1  Milk 				 100	      2 	 			  5.25 		 13-10-31
--		2  Bread				  50	      1 	  	 		  3.25		 13-10-30
--		3  Eggs 				 500	      3 	  4.00		  4.25		 13-11-30
--		4  Book 				 100	      2 	  			  5.25
--		5  Tissue				 100	      2 	  4.50		  5.25

select * from transaction;
--	   TID QUANTITY   TDATE	   AMOUNT 	  TYPE		 REFID      PID	   EID	  MID
---------- ---------- -------- ---------- ---------- ---------- ------ ------ ------
--	 	1	    	5 13-10-17	    26.25 Purchase					 1	    3	   1
--	 	3	   		3 13-10-17	     9.75 Purchase					 2	    3	   3
--	 	4	    	2 13-10-18	      8.5 Purchase					 3	    5	   5
--	 	2	    	5 13-10-18	    26.25 Return	      	  1 	 1	    3	   1
--	 	5	   		1 13-10-18	     3.25 Return	     	  3 	 2	    3	   3
--	 	6	    	2 13-10-19	      8.5 Return	     	  4 	 3	    5	   5
--		7	   		1 13-10-20	     5.25 Purchase					 4	    3	   4
--	 	8	    	1 13-10-21	     5.25 Return	   		  7 	 4	    5	   4
--	 	9	    	3 13-10-17	     9.75 Purchase			 		 2	    3	   3
--		10	    	2 13-10-18	      8.5 Purchase			 		 3	    5	   5

select * from cashtransaction;
--     TID
----------
--		1
--		2
--		4
--		6
--		10

select * from creditcardtransaction;
--     TID CARDNUM 	  NAME						 EXPIRYDA
---------- ---------- -------------------------- --------
--		 3 1234567890 Donny						 15-10-10
--		 5 1234567890 Donny						 15-10-10
--		 7 1234567844 Maria						 15-03-10
--		 8 1234567844 Maria						 15-03-10
--		 9 1234567890 Donny						 15-10-10

select * from updates;
--    UPID UDATE	EID	      PID
---------- -------- --------- -------
--		 1 13-10-19	     	3		4
--		 2 13-10-19	     	2		1
--		 3 13-10-20	     	5		5
--		 4 13-10-19	     	4		4
--		 5 13-10-19	     	2		4

select m.name,p.name,quantity,amount,refid from (product p join transaction t on p.pid=t.pid) JOIN member m on t.mid=m.mid;
