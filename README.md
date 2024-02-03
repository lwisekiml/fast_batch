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

<br/>

### 02. JobExecutionListener_5

<br/>

### 03. FlatFileItemReader
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=flatFileJob

### 04. StepListener\
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=advancedJob

<br/>

### 05. ItemProcessorAdapter
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=flatFileJob

<br/>

### 06. FlatFileItemWriter

<br/>

## Ch 05. Spring Batch 병렬처리
### 01. Muti Thread Step
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=multiThreadStepJob

<br/>

### 02. Parallel Step
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=parallelJob

<br/>

### 03. Remote Chunking

<br/>

### 04. Partitioning
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=partitioningJob

<br/>

- Muti Thread Step : chunk단위로 병렬 처리 - 파일 순서 뒤죽박죽이나 속도를 빠르다.  
- Parallel Step : Step을 동시에 실행, flow를 지정해서 taskExecutor로 동시에 실행, 파일을 실행하는 Step은 flowAmountFileStep으로 처리하고 다른 처리(로그출력)는 flowAnotherStep으로 처리  
- Partitioning : 위와 같이 Step을 동시에 시행하고 싶은데 처리해야할 Step의 양이 많거나 위와같이 지정된 step말고 동적으로 스텝을 생성해서 설정을 해주어야 하는 경우에 사용
어떤 값에 따라서 다르게 step을 생성하고 싶을 때 사용