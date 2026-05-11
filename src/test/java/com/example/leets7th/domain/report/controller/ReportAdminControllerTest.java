package com.example.leets7th.domain.report.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.example.leets7th.domain.report.domain.ReportContentType;
import com.example.leets7th.domain.report.domain.ReportStatus;
import com.example.leets7th.domain.report.dto.ReportResponseDto;
import com.example.leets7th.domain.report.error.ReportException;
import com.example.leets7th.domain.report.service.ReportAdminService;
import com.example.leets7th.global.code.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportAdminController.class)
@AutoConfigureRestDocs
public class ReportAdminControllerTest {

    @MockitoBean
    private ReportAdminService reportAdminService;

    @Autowired
    private MockMvc mockMvc;

    private static final Long REPORT_ID = 1L;
    private static final Long CONTENT_ID = 1L;


    @Nested
    @DisplayName("신고 목록 조회 API")
    class getReportList {

        @Test
        @DisplayName("신고 목록 조회 성공 : 200")
        void success() throws Exception {
            //given

            ReportResponseDto.ReportListRes response = new ReportResponseDto.ReportListRes(
                    REPORT_ID, ReportContentType.POST,CONTENT_ID,ReportStatus.PENDING,"홍길동");

            List<ReportResponseDto.ReportListRes> responseList = new ArrayList<>();

            responseList.add(response);
            given(reportAdminService.getReportList(ReportStatus.PENDING))
                    .willReturn(responseList);


            //when&&then
            mockMvc.perform(
                    get("/api/admin/reports")
                            .accept(MediaType.APPLICATION_JSON)
                            .param("status",ReportStatus.PENDING.toString()))
                    .andExpect(status().isOk())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-getList-success",
                            resource(ResourceSnippetParameters.builder()
                                    .tag("ReportAdmin")
                                    .summary("신고 목록 조회 API")
                                    .description("상태 별로 신고 목록을 조회한다")

                                    .responseSchema(Schema.schema("ReportListRes"))
                                    .queryParameters(
                                            parameterWithName("status").description("신고 상태")
                                    )
                                    .responseFields(
                                            fieldWithPath("isSuccess").description("API 성공 여부"),
                                            fieldWithPath("code").description("API 응답 코드"),
                                            fieldWithPath("message").description("API 응답 메세지"),
                                            fieldWithPath("data[].reportId").description("신고 ID"),
                                            fieldWithPath("data[].contentType").description("신고된 컨텐츠 타입"),
                                            fieldWithPath("data[].contentId").description("신고된 컨텐츠 ID"),
                                            fieldWithPath("data[].status").description("신고 상태"),
                                            fieldWithPath("data[].reporterName").description("신고자 닉네임")

                                    )
                                    .build()

                            )));
        }

    }


    @Nested
    @DisplayName("신고 승인 API")
    class approveReport {
        private ResourceSnippetParametersBuilder commonBuilder() {
            return ResourceSnippetParameters.builder()
                    .tag("ReportAdmin")
                    .summary("신고 승인 API")
                    .description("대기 상태에 있는 신고를 승인한다.")

                    .pathParameters(
                            parameterWithName("reportId").description("신고 ID")
                    )
                    .responseFields(
                            fieldWithPath("isSuccess").description("API 성공 여부"),
                            fieldWithPath("code").description("API 응답 코드"),
                            fieldWithPath("message").description("API 응답 메세지")
                    );
        }


        @Test
        @DisplayName("신고 승인 성공 :200")
        void success() throws Exception {

            //given
            doNothing().when(reportAdminService).approveReport(REPORT_ID);


            //when&&then
            mockMvc.perform(
                            post("/api/admin/reports/{reportId}/approve",REPORT_ID)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-approve-success",
                            resource(commonBuilder()
                                    .build()
                            )));
        }

        @Test
        @DisplayName("신고가 존재하지 않음 :404")
        void reportNotFound() throws Exception{

            //given
            doThrow(new ReportException(ErrorCode.REPORT_NOT_FOUND)).when(reportAdminService).approveReport(REPORT_ID);


            //when&&then
            mockMvc.perform(
                            post("/api/admin/reports/{reportId}/approve",REPORT_ID)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-approve-notFound",
                            resource(commonBuilder()
                                    .build()
                            )));

        }
    }


    @Nested
    @DisplayName("신고 반려 API")
    class rejectReport {
        private ResourceSnippetParametersBuilder commonBuilder() {
            return ResourceSnippetParameters.builder()
                    .tag("ReportAdmin")
                    .summary("신고 반려 API")
                    .description("대기 상태에 있는 신고를 반려한다.")

                    .pathParameters(
                            parameterWithName("reportId").description("신고 ID")
                    )
                    .responseFields(
                            fieldWithPath("isSuccess").description("API 성공 여부"),
                            fieldWithPath("code").description("API 응답 코드"),
                            fieldWithPath("message").description("API 응답 메세지")
                    );
        }

        @Test
        @DisplayName("신고 반려 성공 : 200")
        void success() throws Exception {

            //given
            doNothing().when(reportAdminService).rejectReport(REPORT_ID);


            //when&&then
            mockMvc.perform(
                            post("/api/admin/reports/{reportId}/reject",REPORT_ID)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-reject-success",
                            resource(commonBuilder()
                                    .build()
                            )));
        }


        @Test
        @DisplayName("신고가 존재하지 않음 :404")
        void reportNotFound() throws Exception {

            //given
            doThrow(new ReportException(ErrorCode.REPORT_NOT_FOUND)).when(reportAdminService).rejectReport(REPORT_ID);


            //when&&then
            mockMvc.perform(
                            post("/api/admin/reports/{reportId}/reject",REPORT_ID)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(MockMvcRestDocumentationWrapper.document("report-reject-reportNotFound",
                            resource(commonBuilder()
                                    .build()
                            )));
        }

    }

}
