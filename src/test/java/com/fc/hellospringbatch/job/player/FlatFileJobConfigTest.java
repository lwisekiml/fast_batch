package com.fc.hellospringbatch.job.player;

import com.fc.hellospringbatch.BatchTestConfig;
import com.fc.hellospringbatch.core.service.PlayerSalaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {FlatFileJobConfig.class, BatchTestConfig.class, PlayerSalaryService.class})
public class FlatFileJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception {
        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);

        // player-list.txt -> player-salary-list.txt로 변환한것이 제대로 변환 되었는지 succeed-player-salary-list.txt 와 비교한다.
        /*
            FlatFileJobConfig 에서 playerFileItemWriter()에 있는
            fieldExtractor.setNames(new String[]{"ID", "firstName", "lastName", "salary"});
            에서 실수로 하나를 빼먹거나 지웠을 경우에 이 테스트를 통해 빼먹거나 지웠다는 것을 알 수 있다.
         */
        AssertFile.assertFileEquals(new FileSystemResource("player-salary-list.txt"),
                new FileSystemResource("succeed-player-salary-list.txt"));
    }
}
