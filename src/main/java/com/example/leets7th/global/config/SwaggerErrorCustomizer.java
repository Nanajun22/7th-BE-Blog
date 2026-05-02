package com.example.leets7th.global.config;

import com.example.leets7th.global.annotation.ApiErrorResponse;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.common.BaseCode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerErrorCustomizer implements OperationCustomizer {

    //에러코드 주입
    @Override
    public Operation customize(Operation operation,HandlerMethod handlerMethod) {

        //메서드에서 어노테이션 추출
        ApiErrorResponse apiErrorResponse = handlerMethod.getMethodAnnotation(ApiErrorResponse.class);

        if(apiErrorResponse == null) {
            return operation;
        }


        for(ErrorCode errorCode : apiErrorResponse.value()) {
            //에러 코드 변수 추출
            String status = String.valueOf(errorCode.getStatus().value());
            String code = errorCode.getCode();
            String message = errorCode.getMessage();

            //
            Example example = new Example();
            java.util.Map<String, Object> exampleValue = new java.util.LinkedHashMap<>();
            exampleValue.put("success", false);
            exampleValue.put("code", code);
            exampleValue.put("message", message);

            example.setValue(exampleValue);

            example.setDescription(message);


            //

            ApiResponses apiResponses = operation.getResponses();

            ApiResponse apiResponse = apiResponses.containsKey(status)
                    ? apiResponses.get(status)
                    : new ApiResponse().description("");



            Content content =  apiResponse.getContent() != null ? apiResponse.getContent() : new Content();
            MediaType mediaType = content.containsKey("application/json") ? content.get("application/json") : new MediaType();

            mediaType.addExamples(code,example);
            content.addMediaType("application/json",mediaType);

            apiResponse.setContent(content);

            apiResponses.addApiResponse(status,apiResponse);


        }
        return operation;
    }


}
