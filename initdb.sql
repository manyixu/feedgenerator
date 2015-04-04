/* only testet with mysql */
create database feed charset utf8 collate utf8_swedish_ci;
grant all on feed.* to 'feed'@'localhost' identified by 'feed';
use feed;

CREATE TABLE channel (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description varchar(1024),
  url varchar(255),
  author varchar(255),
  category varchar(255),
  generator varchar(255),
  ttlminutes INT,
  pub_date_yyyymmddhhmmss varchar(16),
  image_url varchar(255),
  image_title varchar(255),
  image_link varchar(255)
);

CREATE TABLE rssitem (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  channel_id int not null,
  title VARCHAR(255) NOT NULL,
  description varchar(2048),
  category varchar(255),
  url varchar(255),
  pub_date_yyyymmddhhmmss varchar(16),
  foreign key ch_id (channel_id) references channel(id)
);

