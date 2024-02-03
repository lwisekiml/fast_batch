## Ch 03. Spring Batch 기본
### 01.프로젝트 세팅
> doker-compose up -d

<br/>

실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=hellJob

<br/>

### 02. Spring Batch 실행 환경 - Batch 스케줄링

> ./gradlew bootJar  

> java -jar build/libs/hello-spring-batch-0.0.1-SNAPSHOT.jar --spring.batch.job.names=helloJob
> 
<br/>

### 03. 데이터를 읽고, 처리하고, 쓰기
>use house;

>CREATE table plain_text (  
  id int(11) not null auto_increment,  
  text varchar(100) collate utf8mb4_unicode_ci not null,  
  primary key (id)  
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

>INSERT INTO plain_text(text) values('apple'),('banana'),('carrot'),('dessert'),('egg'),('fish'),('goose');

>UPDATE `house`.`plain_text` SET `id` = '1' WHERE (`id` = '8');  
UPDATE `house`.`plain_text` SET `id` = '2' WHERE (`id` = '9');  
UPDATE `house`.`plain_text` SET `id` = '3' WHERE (`id` = '10');  
UPDATE `house`.`plain_text` SET `id` = '4' WHERE (`id` = '11');  
UPDATE `house`.`plain_text` SET `id` = '5' WHERE (`id` = '12');  
UPDATE `house`.`plain_text` SET `id` = '6' WHERE (`id` = '13');  
UPDATE `house`.`plain_text` SET `id` = '7' WHERE (`id` = '14');  

<br/>

실행 전 Edit Configuration... -> Program arguments  
>--spring.batch.job.names=PlainTextJob

결과  
>Hibernate: select plaintext0_.id as id1_0_, plaintext0_.text as text2_0_ from plain_text plaintext0_ order by plaintext0_.id desc limit ?  
Hibernate: select count(plaintext0_.id) as col_0_0_ from plain_text plaintext0_  
processed goose  
processed fish  
processed egg  
processed dessert  
processed carrot  
==== chunk is finished  
Hibernate: select plaintext0_.id as id1_0_, plaintext0_.text as text2_0_ from plain_text plaintext0_ order by plaintext0_.id desc limit ?, ?  
Hibernate: select plaintext0_.id as id1_0_, plaintext0_.text as text2_0_ from plain_text plaintext0_ order by plaintext0_.id desc limit ?, ?  
Hibernate: select count(plaintext0_.id) as col_0_0_ from plain_text plaintext0_  
processed banana  
processed apple  
==== chunk is finished  

<br/>

### 04. Spring Batch 테스트
>CREATE table result_text (  
  id int(11) not null auto_increment,  
  text varchar(100) collate utf8mb4_unicode_ci not null,  
  primary key (id)  
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;  

<br/>

## Ch 04. Spring Batch 자세히 살펴보기
### 01. JobParameterValidator
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=advancedJob -targetDate=2021-01-01

>--spring.batch.job.names=advancedJob -targetDate=targetDate

### 02. JobExecutionListener_5
