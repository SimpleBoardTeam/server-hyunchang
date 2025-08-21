package com.simpleboard.board.permission.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleboard.board.permission.application.command.PermissionCommandService;
import com.simpleboard.board.permission.domain.vo.RoleName;
import com.simpleboard.board.permission.presentation.dto.DelegateRoleForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PermissionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PermissionControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private PermissionCommandService permissionCommandService;

  @Test
  @DisplayName("권한 위임 요청 시 200 응답 반환")
  void delegateRole() throws Exception {
    // given
    Long boardId = 1L;
    DelegateRoleForm form = new DelegateRoleForm(10L, "receiverUser", RoleName.BOARD_ADMIN);

    // when & then
    mockMvc
        .perform(
            post("/permissions/boards/{boardId}/delegate", boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
        .andExpect(status().isOk());

    // verify service was called
    Mockito.verify(permissionCommandService).delegateRole(Mockito.eq(boardId), Mockito.any());
  }
}
