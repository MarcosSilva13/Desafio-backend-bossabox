package com.desafio.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ToolRequestDTO(
        @NotBlank(message = "O titulo não pode ser vazio ou nulo.")
        String title,

        @NotBlank(message = "O link não pode ser vazio ou nulo.")
        String link,

        @NotBlank(message = "A descrição não pode ser vazia ou nula.")
        String description,

        @NotEmpty(message = "Deve ter pelo menos uma tag.")
        List<String> tags) {
}
