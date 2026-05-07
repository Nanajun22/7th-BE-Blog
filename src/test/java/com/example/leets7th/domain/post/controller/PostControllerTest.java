    package com.example.leets7th.domain.post.controller;


    import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.example.leets7th.domain.post.dto.PostRequestDto;
    import com.example.leets7th.domain.post.dto.PostResponseDto;
    import com.example.leets7th.domain.post.service.PostService;
    import com.example.leets7th.global.code.ErrorCode;
    import com.example.leets7th.global.code.SuccessCode;
    import com.example.leets7th.global.error.GlobalException;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Nested;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;
    import org.springframework.test.web.servlet.MockMvc;
    import com.epages.restdocs.apispec.*;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;


    import static org.mockito.ArgumentMatchers.*;
    import static org.mockito.BDDMockito.given;
    import static org.mockito.Mockito.*;
    import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
    import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
    import static org.springframework.restdocs.payload.PayloadDocumentation.*;
    import static org.springframework.restdocs.request.RequestDocumentation.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


    @WebMvcTest(PostController.class)
    @AutoConfigureRestDocs
    class PostControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @MockitoBean
        PostService postService;

        private static final Long POST_ID = 1L;
        private static final Long USER_ID = 1L;
        private static final LocalDateTime FIXED_TIME = LocalDateTime.of(2026, 1, 1, 0, 0, 1);
        private static final String FIXED_TIME_STR = "2026-01-01T00:00:01";



        @Nested
        @DisplayName("게시글 상세 조회")
        class GetPost {

            private ResourceSnippetParametersBuilder commonBuilder() {
                return ResourceSnippetParameters.builder()
                        .tag("Post")
                        .summary("게시글 조회 API")
                        .description("게시글을 조회합니다.")

                        .queryParameters(
                                parameterWithName("userId").description("요청한 유저 ID")
                        )

                        .responseSchema(Schema.schema("PostReadRes"));
            }


            @Test
            @DisplayName("게시글 상세 조회 성공 : 200")
            void success() throws Exception {
                //given

                PostResponseDto.ReadPost response = new PostResponseDto.ReadPost(
                        "제목",
                        "내용",
                        "홍길동",
                        FIXED_TIME,
                        FIXED_TIME
                );

                given(postService.getPost(POST_ID))
                        .willReturn(response);


                //when&&then
                mockMvc.perform(
                                get("/api/posts/{postId}", POST_ID)
                                        .param("userId", USER_ID.toString())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.isSuccess").value(true))
                        .andExpect(jsonPath("$.code").value(SuccessCode.POST_READ_OK.getCode()))
                        .andExpect(jsonPath("$.message").value(SuccessCode.POST_READ_OK.getMessage()))
                        .andExpect(jsonPath("$.data.title").value(response.title()))
                        .andExpect(jsonPath("$.data.content").value(response.content()))
                        .andExpect(jsonPath("$.data.nickname").value(response.nickname()))
                        .andExpect(jsonPath("$.data.createdAt").value(FIXED_TIME_STR))
                        .andExpect(jsonPath("$.data.updatedAt").value(FIXED_TIME_STR))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-read-success",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data.title").description("게시글 제목"),
                                                fieldWithPath("data.content").description("게시글 내용"),
                                                fieldWithPath("data.nickname").description("작성자 닉네임"),
                                                fieldWithPath("data.createdAt").description("게시글 생성 일시"),
                                                fieldWithPath("data.updatedAt").description("게시글 수정 일시")
                                        )
                                        .build()
                                )));


            }

            @Test
            @DisplayName("게시글 존재하지 않음 : 404")
            void error_notFound() throws Exception {
                //given

                given(postService.getPost(999L)).willThrow(new GlobalException(ErrorCode.POST_NOT_FOUND));


                //when&&then
                mockMvc.perform(
                                get("/api/posts/{postId}", 999L)
                                        .param("userId", USER_ID.toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.isSuccess").value(false))
                        .andExpect(jsonPath("$.code").value(ErrorCode.POST_NOT_FOUND.getCode()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.POST_NOT_FOUND.getMessage()))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-read-postNotFound",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));

            }

        }

        @Nested
        @DisplayName("게시글 목록 조회")
        class GetPostList {

            @Test
            @DisplayName("게시글 목록 조회 성공 : 200")
            void getPostListApi() throws Exception {
                //given
                PostResponseDto.ReadPostList postDto = new PostResponseDto.ReadPostList(
                        POST_ID,
                        "제목",
                        "홍길동",
                        FIXED_TIME,
                        FIXED_TIME);

                List<PostResponseDto.ReadPostList> postList = new ArrayList<>();
                postList.add(postDto);


                given(postService.getPostList()).willReturn(postList);


                //when&&then


                mockMvc.perform(
                                get("/api/posts")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.isSuccess").value(true))
                        .andExpect(jsonPath("$.code").value(SuccessCode.POST_LIST_READ_OK.getCode()))
                        .andExpect(jsonPath("$.message").value(SuccessCode.POST_LIST_READ_OK.getMessage()))
                        .andExpect(jsonPath("$.data.length()").value(1))
                        .andExpect(jsonPath("$.data[0].postId").value(1L))
                        .andExpect(jsonPath("$.data[0].title").value("제목"))
                        .andExpect(jsonPath("$.data[0].nickname").value("홍길동"))
                        .andExpect(jsonPath("$.data[0].createdAt").value(FIXED_TIME_STR))
                        .andExpect(jsonPath("$.data[0].updatedAt").value(FIXED_TIME_STR))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-readList-success",
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Post")
                                        .summary("게시글 목록 조회")
                                        .description("게시글을 목록을 조회합니다.")

                                        .responseSchema(Schema.schema("PostreadListRes"))

                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data[].postId").description("게시글 ID"),
                                                fieldWithPath("data[].title").description("게시글 제목"),
                                                fieldWithPath("data[].nickname").description("작성자 닉네임"),
                                                fieldWithPath("data[].createdAt").description("게시글 생성 일시"),
                                                fieldWithPath("data[].updatedAt").description("게시글 수정 일시")

                                        )
                                        .build()
                                )));


            }

        }

        @Nested
        @DisplayName("게시글 생성")
        class createPost {

            private PostResponseDto.CreatePost createResponse() {
                return new PostResponseDto.CreatePost(1L,"제목","내용","홍길동",FIXED_TIME,FIXED_TIME);
            }

            private ResourceSnippetParametersBuilder commonBuilder() {
                return ResourceSnippetParameters
                        .builder()
                        .tag("Post").description("게시글 API")
                        .summary("게시글 생성").description("새로운 게시글을 생성합니다.")

                        .requestSchema(Schema.schema("PostCreateReq"))
                        .responseSchema(Schema.schema("PostCreateRes"))


                        .queryParameters(
                                parameterWithName("userId").description("요청한 유저의 ID")
                        )
                        .requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        );


            }

            private PostRequestDto.PostCreateReq createRequest() {
                return new PostRequestDto.PostCreateReq("제목","내용");
            }

            @Test
            @DisplayName("게시글 생성 성공 : 201")
            void success() throws Exception {
                //given
                PostResponseDto.CreatePost response = createResponse();

                given(postService.createPost(any(PostRequestDto.PostCreateReq.class),eq(USER_ID)))
                        .willReturn(response);

                //when&&then
                mockMvc.perform(
                        post("/api/posts")
                                .param("userId",USER_ID.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createRequest())))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.isSuccess").value(true))
                        .andExpect(jsonPath("$.code").value(SuccessCode.POST_CREATED.getCode()))
                        .andExpect(jsonPath("$.message").value(SuccessCode.POST_CREATED.getMessage()))
                        .andExpect(jsonPath("$.data.postId").value(response.postId()))
                        .andExpect(jsonPath("$.data.title").value(response.title()))
                        .andExpect(jsonPath("$.data.content").value(response.content()))
                        .andExpect(jsonPath("$.data.nickname").value(response.nickname()))
                        .andExpect(jsonPath("$.data.createdAt").value(FIXED_TIME_STR))
                        .andExpect(jsonPath("$.data.updatedAt").value(FIXED_TIME_STR))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-create-success",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data.postId").description("게시글 ID"),
                                                fieldWithPath("data.title").description("게시글 제목"),
                                                fieldWithPath("data.content").description("게시글 내용"),
                                                fieldWithPath("data.nickname").description("게시글 작성자 닉네임"),
                                                fieldWithPath("data.createdAt").description("게시글 생성 일시"),
                                                fieldWithPath("data.updatedAt").description("게시글 수정 일시")
                                        )
                                        .build()
                                )

                        ));

            }

            @Test
            @DisplayName("게시글 제목 공백: 400")
            void error_blankTitle() throws Exception {
                //given
                PostRequestDto.PostCreateReq request = new PostRequestDto.PostCreateReq(
                        "",
                        "내용");


                //when&&then
                mockMvc.perform(
                        post("/api/posts")
                                .param("userId",USER_ID.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.isSuccess").value(false))
                        .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_ERROR.getCode()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR.getMessage()))
                        .andExpect(jsonPath("$.data[0].field").value("title"))
                        .andExpect(jsonPath("$.data[0].value").value(request.title()))
                        .andExpect(jsonPath("$.data[0].reason").value("제목을 입력해주세요."))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-create-validataion",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data[].field").description("입력값이 잘못된 필드"),
                                                fieldWithPath("data[].value").description("실제 들어온 입력값"),
                                                fieldWithPath("data[].reason").description("에러 원인")

                                        )
                                        .build()
                                )));
            }

            @Test
            @DisplayName("존재하지 않는 유저 ID 로 게시글 생성 요청 : 404")
            void error_userNotFound() throws Exception {
                //given
                given(postService.createPost(any(),eq(999L))).willThrow(new GlobalException(ErrorCode.USER_NOT_FOUND));


                //when&&then
                mockMvc.perform(
                        post("/api/posts")
                                .param("userId","999")
                                .content(objectMapper.writeValueAsString(createRequest()))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.isSuccess").value(false))
                        .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                        .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-create-userNotFound",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                        )));

            }



        }

        @Nested
        @DisplayName("게시글 수정")
        class updatePost {

            private ResourceSnippetParametersBuilder commonBuilder() {
                return ResourceSnippetParameters.builder()
                        .tag("Post")
                        .summary("게시글 수정 API")
                        .description("기존의 게시글을 수정합니다.")

                        .pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                        .queryParameters(
                                parameterWithName("userId").description("요청한 유저 ID")
                        )

                        .requestFields(
                                fieldWithPath("title").description("수정된 게시글 제목"),
                                fieldWithPath("content").description("수정된 게시글 내용")
                        )
                        .requestSchema(
                                Schema.schema("PostUpdateReq")
                        )
                        .responseSchema(
                                Schema.schema("PostUpdateRes")
                        );
            }

            @Test
            @DisplayName("게시글 수정 성공:200")
            void success() throws Exception {
                //given
                PostRequestDto.PostUpdateReq request = new PostRequestDto.PostUpdateReq("수정된 제목","수정된 내용");
                PostResponseDto.UpdatePost response = new PostResponseDto.UpdatePost(
                        "수정된 제목",
                        "수정된 내용",
                        "홍길동",
                        FIXED_TIME,
                        FIXED_TIME);

                given(postService.updatePost(any(PostRequestDto.PostUpdateReq.class),eq(POST_ID),eq(USER_ID)))
                        .willReturn(response);

                //when&&then
                mockMvc.perform(
                        patch("/api/posts/{postId}",POST_ID)
                                .param("userId",USER_ID.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-update-success",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data.title").description("수정된 게시글 제목"),
                                                fieldWithPath("data.content").description("수정된 게시글 내용"),
                                                fieldWithPath("data.nickname").description("게시글 작성자 닉네임"),
                                                fieldWithPath("data.createdAt").description("게시글 생성 일시"),
                                                fieldWithPath("data.updatedAt").description("게시글 수정 일시")
                                        )
                                        .build()
                                )));


            }

            @Test
            @DisplayName("게시글 제목과 내용이 공백 :400")
            void blankValidation() throws Exception {
                //given
                PostRequestDto.PostUpdateReq request = new PostRequestDto.PostUpdateReq("","");

                given(postService.updatePost(any(PostRequestDto.PostUpdateReq.class),eq(POST_ID),eq(USER_ID)))
                        .willThrow(new GlobalException(ErrorCode.POST_INPUT_NO_VALIDATION));

                //when&&then
                mockMvc.perform(
                        patch("/api/posts/{postId}",POST_ID)
                                .param("userId",USER_ID.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-update-validationError",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지"),
                                                fieldWithPath("data[].field").description("validation 에러 변수"),
                                                fieldWithPath("data[].value").description("요청 입력값"),
                                                fieldWithPath("data[].reason").description("validation 에러 원인")
                                        )
                                        .build()
                                )));

            }


            @Test
            @DisplayName("수정하려는 게시글이 존재하지 않음:400")
            void postNotFound() throws Exception {

                //given
                PostRequestDto.PostUpdateReq request = new PostRequestDto.PostUpdateReq("수정된 게시글 제목","수정된 게시글 내용");

                given(postService.updatePost(any(PostRequestDto.PostUpdateReq.class),eq(999L),eq(USER_ID)))
                        .willThrow(new GlobalException(ErrorCode.POST_NOT_FOUND));

                //when&&then
                mockMvc.perform(
                                patch("/api/posts/{postId}",999L)
                                        .param("userId",USER_ID.toString())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request))
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-update-postNotFound",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));
            }

            @Test
            @DisplayName("게시글 수정 권한 없음: 403")
            void noPermission() throws Exception {
                //given
                PostRequestDto.PostUpdateReq request = new PostRequestDto.PostUpdateReq("수정된 게시글 제목","수정된 게시글 내용");

                given(postService.updatePost(any(PostRequestDto.PostUpdateReq.class),eq(POST_ID),eq(2L)))
                        .willThrow(new GlobalException(ErrorCode.POST_UPDATE_NO_PERMISSION));

                //when&&then
                mockMvc.perform(
                                patch("/api/posts/{postId}",POST_ID)
                                        .param("userId","2")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request))
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-update-noPermission",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));
            }


        }

        @Nested
        @DisplayName("게시글 삭제")
        class deletePost {

            private ResourceSnippetParametersBuilder commonBuilder() {
                return ResourceSnippetParameters.builder()
                        .tag("Post")
                        .summary("게시글 삭제 API")
                        .description("기존의 게시글을 삭제합니다.")

                        .pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                        .queryParameters(
                                parameterWithName("userId").description("요청한 유저 ID")
                        );

            }

            @Test
            @DisplayName("게시글 삭제 성공 : 200")
            void success() throws Exception {
                //given

                doNothing().when(postService).deletePost(POST_ID,USER_ID);

                //when&&then

                mockMvc.perform(
                        delete("/api/posts/{postId}",POST_ID)
                                .queryParam("userId",USER_ID.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-delete-success",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));
            }

            @Test
            @DisplayName("게시글 삭제 권한 없음 : 403")
            void noPermission() throws Exception {

                //given

                doThrow(new GlobalException(ErrorCode.POST_DELETE_NO_PERMISSION)).when(postService).deletePost(POST_ID,2L);

                //when&&then

                mockMvc.perform(
                                delete("/api/posts/{postId}",POST_ID)
                                        .queryParam("userId","2")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-delete-noPermission",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));
            }

            @Test
            @DisplayName("게시글 존재하지 않음 :404")
            void notFound() throws Exception {

                //given

                doThrow(new GlobalException(ErrorCode.POST_NOT_FOUND)).when(postService).deletePost(999L,USER_ID);

                //when&&then

                mockMvc.perform(
                                delete("/api/posts/{postId}",999)
                                        .queryParam("userId",USER_ID.toString())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andDo(MockMvcRestDocumentationWrapper.document("post-delete-notFound",
                                resource(commonBuilder()
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("API 성공 여부"),
                                                fieldWithPath("code").description("API 응답 코드"),
                                                fieldWithPath("message").description("API 응답 메세지")
                                        )
                                        .build()
                                )));

            }




        }


    }
