create table if not exists hotel(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    location varchar(250),
    rating INT
);
create table if not exists room(
    id INT PRIMARY KEY AUTO_INCREMENT,
    roomNumber varchar(250),
    type varchar(250),
    price double,
    hotelId INT,
    FOREIGN KEY(hotelId) REFERENCES hotel(id)
);
