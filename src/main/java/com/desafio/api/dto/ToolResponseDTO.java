package com.desafio.api.dto;

import com.desafio.api.entity.Tool;

import java.util.List;

public record ToolResponseDTO(Long id, String title, String link, String description, List<String> tags) {

    public ToolResponseDTO(Tool tool) {
        this(tool.getId(), tool.getTitle(), tool.getLink(), tool.getDescription(), tool.getTags());
    }
}
