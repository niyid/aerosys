alter table Notification drop foreign key FK2D45DD0B89944C95;
alter table Notification drop foreign key FK2D45DD0BB588B853;
drop table if exists Notification;
drop table if exists Broadcast;

create table Notification (
        id bigint not null auto_increment,
        createdBy varchar(255),
        createdDate datetime,
        lastUpdated datetime,
        lastUpdatedBy varchar(255),
        message longtext,
        originAppName varchar(200),
        `read` bit,
        title longtext,
        subscriberNotificationId bigint,
        primary key (id)
) type=InnoDB;

alter table Notification 
        add index FK2D45DD0B89944C95 (subscriberNotificationId), 
        add constraint FK2D45DD0B89944C95 
        foreign key (subscriberNotificationId) 
        references User (id);

        
alter table UserNotification  drop foreign key FKC31EB4765FF93CF6;
drop table if exists UserNotification;
create table UserNotification (
        id bigint not null auto_increment,
        createdBy varchar(255),
        createdDate datetime,
        lastUpdated datetime,
        lastUpdatedBy varchar(255),
        version integer not null,
        eventName varchar(200) not null,
        formatString longtext,
        frequency varchar(100),
        priority varchar(100),
        subscribed bit not null,
        user_id bigint not null,
        primary key (id)
) type=InnoDB;

alter table UserNotification add index FKC31EB4765FF93CF6 (user_id), add constraint FKC31EB4765FF93CF6  foreign key (user_id) references User (id);

