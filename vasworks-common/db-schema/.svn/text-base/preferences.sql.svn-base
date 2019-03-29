drop table if exists StringPreference;
drop table if exists BooleanPreference;
drop table if exists DatePreference;
drop table if exists NumberPreference;
drop table if exists Preference;

create table Preference (
    preferenceType integer not null,
    id bigint not null auto_increment,
    createdBy varchar(255),
    createdDate datetime,
    lastUpdated datetime,
    lastUpdatedBy varchar(255),
    description varchar(255),
    preferenceKey varchar(255),
    boolVal bit,
    charVal char(1),
    doubleVal double precision,
    longVal bigint,
    stringVal varchar(255),
    intVal integer,
    bigVal decimal(19,2),
    dateVal datetime,
    userId bigint,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table Preference add index FK1FCE98FB36E9810D (userId), add constraint FK1FCE98FB36E9810D foreign key (userId) references User (id);
