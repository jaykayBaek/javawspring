create table good(
	idx bigint(20) not null AUTO_INCREMENT primary key,
    part varchar(20) not null,
    idx_part bigint(20) not null,
    -- 사용자 아이디
    mid_member varchar(20) not null,
    -- 좋아요 체크(Y), (N)
    goodSw char(1) default 'Y'
);

desc member2;


-----------------
create table good(
	idx bigint(20) not null AUTO_INCREMENT primary key,
    -- 어떤 부분인지. 게시판, 댓글 등등
    part varchar(20) not null,
    idx_part bigint(20),
    -- 사용자 아이디
    mid_member varchar(20) not null
);
