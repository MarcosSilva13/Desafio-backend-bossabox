package com.desafio.api.controller;

import com.desafio.api.dto.ToolRequestDTO;
import com.desafio.api.dto.ToolResponseDTO;
import com.desafio.api.service.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tools")
@Tag(name = "Tool")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @Operation(summary = "Get all tools", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAll() {
        return ResponseEntity.ok().body(toolService.getAllTools());
    }

    @Operation(summary = "Get all tools by tag name", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Data not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/findByTag")
    public ResponseEntity<List<ToolResponseDTO>> getByTag(@RequestParam(name = "tag") String tag) {
        return ResponseEntity.ok().body(toolService.getAllToolsByTag(tag));
    }

    @Operation(summary = "Save new tool", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<ToolResponseDTO> save(@RequestBody @Valid ToolRequestDTO requestDTO) {
        ToolResponseDTO toolResponse = toolService.saveTool(requestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(toolResponse);
    }

    @Operation(summary = "Delete tool", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Data not found", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        toolService.deleteTool(id);

        return ResponseEntity.noContent().build();
    }
}
