create
user 'spring5'@'localhost' identified by 'spring5'; -- MySQL DB에 spring5 계정 생성 (암호 : spring5)

create
database spring5fs character set=utf8; -- spring5fs DB 생성

grant all privileges on spring5fs.* to
'spring5'@'localhost'; -- spring5fs DB에 spring5 계정이 접근할 수 있도록 권한 부여

create table spring5fs.MEMBER -- spring5fs DB에 MEMBER 테이블 생성
(
    ID       int auto_increment primary key,
    EMAIL    varchar(255),
    PASSWORD varchar(100),
    NAME     varchar(100),
    REGDATE  datetime,
    unique key (EMAIL)
) engine=InnoDB character set = utf8;
