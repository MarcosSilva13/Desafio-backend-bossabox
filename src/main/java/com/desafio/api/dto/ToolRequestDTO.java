package com.desafio.api.dto;

import java.util.List;

public record ToolRequestDTO(String title, String link, String description, List<String> tags) {

}
