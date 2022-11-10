create database harvest;

use harvest;

create table marketplaces (
	id int auto_increment primary key,
	name varchar(20)
);

create table items (
	id int auto_increment primary key,
    marketplace_id int,
    details_id int,
    page_position int,
    is_paid boolean,
    price varchar(10),
    name varchar(200),
    image_url varchar(200),
    listing_url varchar(200),
    description varchar(800),
    detection_date datetime
);

create table details (
	id int auto_increment primary key,
    item_id int,
    detection_id int,
    previous_status varchar(10),
    current_status varchar(10),
	previous_state varchar(10),
    current_state varchar(10),
    analyst varchar(30),
    modified_date datetime
);

