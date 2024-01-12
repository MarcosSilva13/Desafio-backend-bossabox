package com.desafio.api.service;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.entity.Tool;
import com.desafio.api.mapper.ToolMapper;
import com.desafio.api.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @InjectMocks
    private ToolService toolService;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ToolMapper toolMapper;

    private ToolRequestDTO request;

    private ToolResponseDTO response;

    private Tool tool;

    @BeforeEach
    void setUp() {
        this.tool = new Tool(1L, "Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));

        request = new ToolRequestDTO("Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));

        response = new ToolResponseDTO(1L, "Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));
    }

    @Test
    @DisplayName("GetAllTools should return list of ToolResponseDTO")
    void getAllTools_ShouldReturnListOfToolResponseDTO_WhenSuccessful() {
        when(toolRepository.findAll()).thenReturn(List.of(tool));

        List<ToolResponseDTO> responseDTOS = toolService.getAllTools();

        Assertions.assertThat(responseDTOS).isNotEmpty().isNotNull().hasSize(1);

        Assertions.assertThat(responseDTOS.get(0)).isEqualTo(response);

        verify(toolRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GetAllToolsByTag should return list of ToolResponseDTO by tag name")
    void getAllToolsByTag_ShouldReturnListOfToolResponseDTOByTagName_WhenSuccessful() {
        when(toolRepository.findAllByTag("organization")).thenReturn(List.of(tool));

        List<ToolResponseDTO> responseDTOS = toolService.getAllToolsByTag("organization");

        Assertions.assertThat(responseDTOS).isNotEmpty().isNotNull().hasSize(1);

        Assertions.assertThat(responseDTOS.get(0)).isEqualTo(response);

        verify(toolRepository, times(1)).findAllByTag("organization");
    }

    @Test
    @DisplayName("GetAllToolsByTag should throw EntityNotFoundException when tool is not found")
    void getAllToolsByTag_ShouldThrowEntityNotFoundException_WhenToolIsNotFound() {
        String tag = "test";
        when(toolRepository.findAllByTag(tag)).thenReturn(Collections.emptyList());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> toolService.getAllToolsByTag(tag))
                .withMessage("Nenhuma ferramenta com a tag: " + tag + " foi encontrada.");

        verify(toolRepository, times(1)).findAllByTag(tag);
    }

    @Test
    @DisplayName("SaveTool should return ToolResponseDTO when successful")
    void saveTool_ShouldReturnToolResponseDTO_WhenSuccessful() {
        when(toolRepository.save(tool)).thenReturn(tool);

        when(toolMapper.toToolEntity(request)).thenReturn(tool);

        when(toolMapper.toToolResponseDTO(tool)).thenReturn(response);

        ToolResponseDTO toolResponse = toolService.saveTool(request);

        Assertions.assertThat(toolResponse).isNotNull().isEqualTo(response);

        verify(toolRepository, times(1)).save(tool);

        verify(toolMapper, times(1)).toToolEntity(request);

        verify(toolMapper, times(1)).toToolResponseDTO(tool);
    }

    @Test
    @DisplayName("DeleteTool should remove tool when successful")
    void deleteTool_ShouldRemoveTool_WhenSuccessful() {
        when(toolRepository.findById(tool.getId())).thenReturn(Optional.of(tool));

        Assertions.assertThatCode(() -> toolService.deleteTool(tool.getId()))
                .doesNotThrowAnyException();

        verify(toolRepository, times(1)).findById(tool.getId());

        verify(toolRepository, times(1)).delete(tool);
    }
}
