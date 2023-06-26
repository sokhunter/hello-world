DROP TABLE IF EXISTS `ORDER_ENTITY`;
CREATE TABLE `ORDER_ENTITY` (
    id              int auto_increment primary key,
    source          varchar(5)         null,
    target          varchar(5)         null,
    price           double             null,
    `date`          datetime           null
);