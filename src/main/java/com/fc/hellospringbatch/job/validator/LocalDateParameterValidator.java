package com.fc.hellospringbatch.job.validator;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalDate;

@AllArgsConstructor
public class LocalDateParameterValidator implements JobParametersValidator {

    private String parameterName;

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException { // 값 등을 체크하다 문제가 생기면 exception만 던져주면 된다.
        String localDate = parameters.getString(parameterName);

        if (!StringUtils.hasText(localDate)) {
            throw new JobParametersInvalidException(parameterName + "가 빈 문자열이거나 존재하지 않습니다.");
        }

        try {
            LocalDate.parse(localDate);
        } catch (DateTimeException e) {
            throw new JobParametersInvalidException(parameterName + "가 날짜 형식의 문자열이 아닙니다.");
        }
    }
}
