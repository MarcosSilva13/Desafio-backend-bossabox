package com.desafio.api.controller;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping
    public ResponseEntity<ToolResponseDTO> save(@RequestBody ToolRequestDTO requestDTO) {
        ToolResponseDTO toolResponse = toolService.saveTool(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(toolResponse);
    }
}
