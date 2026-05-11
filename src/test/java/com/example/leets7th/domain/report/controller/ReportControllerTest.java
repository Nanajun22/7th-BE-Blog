package com.example.leets7th.domain.report.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.dto.ReportRequestDto;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.error.ReportException;
import com.example.leets7th.domain.report.service.ReportService;
import com.example.leets7th.global.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@AutoConfigureRestDocs
public class ReportControllerTest {



    @MockitoBean
    private ReportService reportService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final Long CONTENT_ID = 1L;

    private static final Long REPORTER_ID = 1L;

    private static final Long REPORT_ID = 1L;


    @Nested
    @DisplayName("일반 사용자 신고 접수 API")
    class createReport {

        private ResourceSnippetParametersBuilder commonBuilder() {
            return ResourceSnippetParameters.builder()
                    .tag("Report")
                    .summary("신고 접수 API")
                    .description("다른 사용자의 컨텐츠를 신고합니다.")


                    .requestSchema(Schema.schema("ReportCreateReq"))
                    .responseSchema(Schema.schema("ReportCreateRes"))
                    .queryParameters(
                            parameterWithName("userId").description("신고자 ID")
                    )
                    .requestFields(
                            fieldWithPath("reason").description("신고 사유"),
                            fieldWithPath("contentType").description("신고된 컨텐츠 타입"),
                            fieldWithPath("contentId").description("신고된 컨텐츠 ID")
                    );
        }


        @Test
        @DisplayName("신고 접수 성공 :200")
        void success() throws Exception {
            //given
            ReportRequestDto.ReportCreateReq request = new ReportRequestDto.ReportCreateReq("욕설", ReportContentType.COMMENT,CONTENT_ID);
            ReportResponseDto.ReportCreateRes response = new ReportResponseDto.ReportCreateRes("욕설",ReportContentType.COMMENT,CONTENT_ID);

            given(reportService.createReport(request,REPORTER_ID))
                    .willReturn(response);


            //when&&then
            mockMvc.perform(
                    post("/api/reports")
                            .accept(MediaType.APPLICATION_JSON)
                            .param("userId",REPORTER_ID.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-create-success",
                            resource(commonBuilder()

                                    .responseFields(
                                            fieldWithPath("isSuccess").description("API 성공 여부"),
                                            fieldWithPath("code").description("API 응답 코드"),
                                            fieldWithPath("message").description("API 응답 메세지"),
                                            fieldWithPath("data.reason").description("신고 사유"),
                                            fieldWithPath("data.contentType").description("신고된 컨텐츠 타입"),
                                            fieldWithPath("data.contentId").description("신고된 컨텐츠 ID")
                                    )
                                    .build()
                    ))
            );



        }


        @Test
        @DisplayName("자기 자신 신고 : 400")
        void fail_selfReport() throws Exception {

            //given
            ReportRequestDto.ReportCreateReq request = new ReportRequestDto.ReportCreateReq("욕설", ReportContentType.COMMENT,CONTENT_ID);
            ReportResponseDto.ReportCreateRes response = new ReportResponseDto.ReportCreateRes("욕설",ReportContentType.COMMENT,CONTENT_ID);

            given(reportService.createReport(request,REPORTER_ID))
                    .willThrow(new ReportException(ErrorCode.SELF_REPORT));





            //when&&then
            mockMvc.perform(
                            post("/api/reports")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .param("userId",REPORTER_ID.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-create-selfReport",
                            resource(commonBuilder()

                                    .responseFields(
                                            fieldWithPath("isSuccess").description("API 성공 여부"),
                                            fieldWithPath("code").description("API 응답 코드"),
                                            fieldWithPath("message").description("API 응답 메세지")
                                    )
                                    .build()
                            ))
                    );

        }

        @Test
        @DisplayName("신고 중복 접수 : 400")
        void fail_reportAlreadyExist() throws Exception{

            //given
            ReportRequestDto.ReportCreateReq request = new ReportRequestDto.ReportCreateReq("욕설", ReportContentType.COMMENT,CONTENT_ID);
            ReportResponseDto.ReportCreateRes response = new ReportResponseDto.ReportCreateRes("욕설",ReportContentType.COMMENT,CONTENT_ID);

            given(reportService.createReport(request,REPORTER_ID))
                    .willThrow(new ReportException(ErrorCode.REPORT_ALREADY_EXIST));



            //when&&then
            mockMvc.perform(
                            post("/api/reports")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .param("userId",REPORTER_ID.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-create-alreadyExist",
                            resource(commonBuilder()

                                    .responseFields(
                                            fieldWithPath("isSuccess").description("API 성공 여부"),
                                            fieldWithPath("code").description("API 응답 코드"),
                                            fieldWithPath("message").description("API 응답 메세지")
                                    )
                                    .build()
                            ))
                    );
        }



        }



    @Nested
    @DisplayName("일반 사용자 신고 취소 API")
    class deleteReport {

        private ResourceSnippetParametersBuilder commonBuilder() {
            return ResourceSnippetParameters.builder()
                    .tag("Report")
                    .summary("신고 취소 API")
                    .description("기존의 신고를 취소합니다.")

                    .requestSchema(Schema.schema("ReportDeleteReq"))
                    .responseSchema(Schema.schema("ReportDeleteRes"))

                    .queryParameters(
                            parameterWithName("userId").description("요청한 유저 ID")
                    )
                    .pathParameters(
                            parameterWithName("reportId").description("삭제할 신고 ID")
                    )

                    .responseFields(
                            fieldWithPath("isSuccess").description("API 성공 여부"),
                            fieldWithPath("code").description("API 응답 코드"),
                            fieldWithPath("message").description("API 응답 메세지")
                    );

        }

        @Test
        @DisplayName("신고 취소 성공 : 200")
        void success() throws Exception {
            //given
            doNothing().when(reportService).deleteReport(REPORT_ID,REPORTER_ID);

            //when&&then
            mockMvc.perform(
                    delete("/api/reports/{reportId}",REPORT_ID)
                            .queryParam("userId",REPORTER_ID.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-delete-success",
                            resource(commonBuilder().build())));



        }

        @Test
        @DisplayName("이미 처리된 신고 : 400")
        void alreadyHandled() throws Exception {

            //given
            doThrow(new ReportException(ErrorCode.REPORT_ALREADY_HANDLED)).when(reportService).deleteReport(REPORT_ID,REPORTER_ID);

            //when&&then
            mockMvc.perform(
                            delete("/api/reports/{reportId}",REPORT_ID)
                                    .queryParam("userId",REPORTER_ID.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-delete-alreadyHandled",
                            resource(commonBuilder().build())));
        }


        @Test
        @DisplayName("신고 취소 권한 없음 : 403")
        void noPermission() throws Exception{
            //given
            doThrow(new ReportException(ErrorCode.REPORT_DELETE_NO_PERMISSION)).when(reportService).deleteReport(REPORT_ID,REPORTER_ID);

            //when&&then
            mockMvc.perform(
                            delete("/api/reports/{reportId}",REPORT_ID)
                                    .queryParam("userId",REPORTER_ID.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-delete-noPermission",
                            resource(commonBuilder().build())));
        }

    }


}
