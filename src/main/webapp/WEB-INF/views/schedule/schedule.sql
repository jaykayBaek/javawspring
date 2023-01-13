show databases;

CREATE table schedule2 (
	idx int not null auto_increment primary key,
    mid varchar(20) not null,
    sDate datetime not null, -- 일정 등록
    part varchar(20) not null, -- 1. 모임 2.엄무, 3. 학원, 4.여행, 0. 기타...
    content text not null -- 일정 삭제
);

insert into schedule values(default, 'hkd1234', '2023-01-13', '모임', '가족모임');
insert into schedule values(default, 'hkd1234', '2023-01-30', '여행', '휴가');
insert into schedule values(default, 'kms1234', '2023-01-14', '모임', '신년모임');
insert into schedule values(default, 'kms1234', '2023-01-22', '여행', '가족여행');
insert into schedule values(default, 'jaykay', '2023-01-05', '모임', '가족모임');
insert into schedule values(default, 'jaykay', '2023-01-13', '업무', '사석미팅');
insert into schedule values(default, 'admin', '2023-01-10', '여행', '가족여행');
insert into schedule values(default, 'admin', '2023-01-14', '학습', '스프링 학습');
insert into schedule values(default, 'abcd1234', '2023-01-11', '업무', '워크숍');
insert into schedule values(default, 'abcd1234', '2023-01-23', '모임', '해외여행');