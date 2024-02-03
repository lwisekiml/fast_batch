package com.fc.hellospringbatch.job;

import com.fc.hellospringbatch.job.validator.LocalDateParameterValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@AllArgsConstructor
@Slf4j
public class AdvancedJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job advancedJob(JobExecutionListener jobExecutionListener,
                           Step advancedStep) {
        return jobBuilderFactory.get("advancedJob")
                .incrementer(new RunIdIncrementer())
                .validator(new LocalDateParameterValidator("targetDate"))
                .listener(jobExecutionListener)
                .start(advancedStep)
                .build();
    }

    @JobScope
    @Bean
    public JobExecutionListener jobExecutionListener() { // Job 시작 전후의 상태를 알 수 있다.
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("[JobExecutionListener#beforeJob] jobExecution is " + jobExecution.getStatus());
            }

            @Override
            public void afterJob(JobExecution jobExecution) { // 실행 결과를 가지고 처리를 하고 싶을 때 사용
                // 실패라면 별도 처리를 해서 이메일 등 알림을 보내는 로직을 구현하기도 한다.
                // advancedTasklet() 에서 run time exception을 발생시켜 if에 걸리도록 하여 테스트
                if (jobExecution.getStatus() == BatchStatus.FAILED) {
                    log.info("[JobExecutionListener#afterJob] JobExecution is FAILED!! RECOVER ASAP");
                }
                log.info("[JobExecutionListener#afterJob] jobExecution is " + jobExecution.getStatus());
            }
        };
    }

    @JobScope
    @Bean
    public Step advancedStep(Tasklet advancedTasklet) {
        return stepBuilderFactory.get("advancedStep")
                .tasklet(advancedTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet advancedTasklet(@Value("#{jobParameters['targetDate']}") String targetDate) {
        return (contribution, chunkContext) -> {
            log.info("[AdvancedJobConfig] JobParameter - targetDate = " + targetDate);
            LocalDate excutionDate = LocalDate.parse(targetDate); // targetDate가 날짜형식이 아닌경우 또는 잘못된 값일 경우 에러 발생(더 빨리 에러를 잡기위해 advancedJob에 validator() 코드 추가)
            // executionDate -> 로직 수행
            log.info("[AdvancedJobConfig] excuted advancedTasklet");

            throw new RuntimeException("ERROR!!!!"); // 에러를 발생
//            return RepeatStatus.FINISHED;
        };
    }

}
