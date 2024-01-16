package com.desafio.api.controller;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.service.ToolService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolControllerTest {

    @InjectMocks
    private ToolController toolController;

    @Mock
    private ToolService toolService;

    private ToolRequestDTO request;

    private ToolResponseDTO response;

    @BeforeEach
    void setUp() {
        request = new ToolRequestDTO("Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));

        response = new ToolResponseDTO(1L, "Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));
    }


    @Test
    @DisplayName("GetAll should return list of ToolResponseDTO when successful")
    void getAll_ShouldReturnListOfToolResponseDTO_WhenSuccessful() {
        when(toolService.getAllTools()).thenReturn(List.of(response));

        ResponseEntity<List<ToolResponseDTO>> toolsResponse = toolController.getAll();

        Assertions.assertThat(toolsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(toolsResponse.getBody()).isNotEmpty().isNotNull().hasSize(1);

        Assertions.assertThat(toolsResponse.getBody().get(0)).isEqualTo(response);

        verify(toolService, times(1)).getAllTools();
    }

    @Test
    @DisplayName("GetByTag should return list of ToolResponseDTO by tag name when successful")
    void getByTag_ShouldReturnListOfToolResponseDTOByTagName_WhenSuccessful() {
        String tag = "Organization";
        when(toolService.getAllToolsByTag(tag)).thenReturn(List.of(response));

        ResponseEntity<List<ToolResponseDTO>> toolsByTagResponse = toolController.getByTag(tag);

        Assertions.assertThat(toolsByTagResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(toolsByTagResponse.getBody()).isNotEmpty().isNotNull().hasSize(1);

        Assertions.assertThat(toolsByTagResponse.getBody().get(0)).isEqualTo(response);

        verify(toolService, times(1)).getAllToolsByTag(tag);
    }

    @Test
    @DisplayName("Save should return ToolResponseDTO when successful")
    void save_ShouldReturnToolResponseDTO_WhenSuccessful() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setScheme("http");
        mockRequest.setServerName("localhost");
        mockRequest.setServerPort(8080);
        mockRequest.setRequestURI("/tools");

        URI location = ServletUriComponentsBuilder
                .fromRequest(mockRequest)
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        when(toolService.saveTool(request)).thenReturn(response);

        ResponseEntity<ToolResponseDTO> toolResponse = toolController.save(mockRequest,request);

        Assertions.assertThat(toolResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(toolResponse.getHeaders().getLocation()).isEqualTo(location);

        Assertions.assertThat(toolResponse.getBody()).isNotNull().isEqualTo(response);

        verify(toolService, times(1)).saveTool(request);
    }

    @Test
    @DisplayName("Delete should return status no content when successful")
    void delete_ShouldReturnStatusNoContent_WhenSuccessful() {
        Long id = 1L;
        doNothing().when(toolService).deleteTool(id);

        ResponseEntity<Void> responseEntity = toolController.delete(id);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(toolService, times(1)).deleteTool(id);

    }
}
