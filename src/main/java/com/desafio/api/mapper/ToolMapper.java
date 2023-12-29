package com.desafio.api.mapper;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.entity.Tool;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    Tool toToolEntity(ToolRequestDTO requestDTO);

    ToolResponseDTO toToolResponseDTO(Tool tool);
}
