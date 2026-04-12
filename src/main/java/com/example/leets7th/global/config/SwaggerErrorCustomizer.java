package com.example.leets7th.global.config;

import com.example.leets7th.global.annotation.ApiErrorResponse;
import com.example.leets7th.global.code.ErrorCode;
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

    @Override
    public Operation customize(Operation operation,HandlerMethod handlerMethod) {

        ApiErrorResponse apiErrorResponse = handlerMethod.getMethodAnnotation(ApiErrorResponse.class);

        if(apiErrorResponse == null) {
            return operation;
        }

        ApiResponses apiResponses = operation.getResponses();

        for(ErrorCode errorCode : apiErrorResponse.value()) {
            String status = String.valueOf(errorCode.getStatus().value());
            String code = errorCode.getCode();
            String message = errorCode.getMessage();

            Example example = new Example();
            java.util.Map<String, Object> exampleValue = new java.util.LinkedHashMap<>();
            exampleValue.put("success", false);
            exampleValue.put("code", code);
            exampleValue.put("message", message);

            example.setValue(exampleValue);

            example.setDescription(message);


            ApiResponse apiResponse = apiResponses.containsKey(status)
                    ? apiResponses.get(status)
                    : new ApiResponse().description("에러 응답");



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
