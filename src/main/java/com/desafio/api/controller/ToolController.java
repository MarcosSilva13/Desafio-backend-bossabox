package com.desafio.api.controller;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAll() {
        return ResponseEntity.ok().body(toolService.getAllTools());
    }

    @GetMapping("/findByTag")
    public ResponseEntity<List<ToolResponseDTO>> getByTag(@RequestParam(name = "tag") String tag) {
        return ResponseEntity.ok().body(toolService.getAllToolsByTag(tag));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        toolService.deleteTool(id);

        return ResponseEntity.noContent().build();
    }
}
