CREATE DATABASE harvestMarketplace;

USE harvestMarketplace;

CREATE TABLE detection (
id INT AUTO_INCREMENT PRIMARY KEY,
capture_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
title VARCHAR(1000),
description VARCHAR(5000),
url VARCHAR (500),
unique (url),
image_url VARCHAR(1000),
item_order INT,
sponsored VARCHAR(1000),
price VARCHAR(1000)
);

drop table detection;  

create table audit(
  id int auto_increment primary key,
  detection_id int,
  altered_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  state enum ('open', 'close'),
  status enum ('new', 'benign', 'enforce'),
  reason_code enum ('brand misuse', 'trademark infringement', 'phishing', 'fair-use'),
  analyst varchar(50),
  foreign key (detection_id) references detection(id)
);

create trigger audit_change after insert on detection
	for each row
    insert into audit 
    set detection_id = last_insert_id()+1,
    state = 'open',
    status = 'new';
    
drop trigger audit_change;
drop table audit;
       
show tables;

describe detection;
describe audit;

select * from detection;

select * from audit;

select d.id, d.title, d.url, a.state, a.status from detection d
join audit a on a.detection_id=d.id
order by d.id;
