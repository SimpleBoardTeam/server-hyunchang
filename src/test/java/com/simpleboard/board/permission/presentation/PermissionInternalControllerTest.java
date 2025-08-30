package com.simpleboard.board.permission.presentation;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simpleboard.board.permission.application.query.PermissionQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PermissionInternalController.class)
@AutoConfigureMockMvc(addFilters = false)
class PermissionInternalControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PermissionQueryService permissionQueryService;

  @Test
  @DisplayName("삭제 권한이 있는 경우 true 반환")
  void checkBoardDeletePermission_true() throws Exception {
    // given
    Mockito.when(permissionQueryService.checkBoardDeletePermission(anyLong(), anyLong()))
        .thenReturn(true);

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/internal/permissions/boards/1/delete")
                .param("userId", "100"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canDelete").value(true));
  }

  @Test
  @DisplayName("삭제 권한이 없는 경우 false 반환")
  void checkBoardDeletePermission_false() throws Exception {
    // given
    Mockito.when(permissionQueryService.checkBoardDeletePermission(anyLong(), anyLong()))
        .thenReturn(false);

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/internal/permissions/boards/1/delete")
                .param("userId", "100"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canDelete").value(false));
  }
}
