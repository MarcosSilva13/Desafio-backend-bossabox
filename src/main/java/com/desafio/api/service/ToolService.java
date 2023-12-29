package com.desafio.api.service;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.entity.Tool;
import com.desafio.api.mapper.ToolMapper;
import com.desafio.api.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToolService {

    private final ToolRepository toolRepository;
    private final ToolMapper toolMapper;

    public ToolService(ToolRepository toolRepository, ToolMapper toolMapper) {
        this.toolRepository = toolRepository;
        this.toolMapper = toolMapper;
    }

    @Transactional(readOnly = true)
    public List<ToolResponseDTO> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .map(ToolResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ToolResponseDTO> getAllToolsByTag(String tag) {
        List<Tool> tools = toolRepository.findAllByTag(tag);

        if (tools.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma ferramenta com a tag: " + tag + " foi encontrada.");
        }

        return tools
                .stream()
                .map(ToolResponseDTO::new)
                .toList();
    }

    @Transactional
    public ToolResponseDTO saveTool(ToolRequestDTO requestDTO) {
        Tool tool = toolMapper.toToolEntity(requestDTO);

        return toolMapper.toToolResponseDTO(toolRepository.save(tool));
    }

    @Transactional
    public void deleteTool(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ferramenta não encontrada."));

        toolRepository.delete(tool);
    }
}
