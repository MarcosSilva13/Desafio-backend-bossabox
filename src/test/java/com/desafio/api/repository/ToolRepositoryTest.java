package com.desafio.api.repository;

import com.desafio.api.entity.Tool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ToolRepositoryTest {

    @Autowired
    private ToolRepository toolRepository;

    private Tool tool;

    @BeforeEach
    void setUp() {
        this.tool = new Tool(1L, "Notion", "https://notion.so",
                "All in one tool to organize teams and ideas. Write, plan, collaborate, and get organized.",
                List.of("organization", "planning", "collaboration", "writing", "calendar"));
    }

    @Test
    @DisplayName("FindAllByTag should return list of tools by a tag name")
    void findAllByTagShouldReturnListOfToolsByTagName() {
        Tool toolSaved = this.toolRepository.save(tool);

        List<Tool> tools = this.toolRepository.findAllByTag("organization");

        Assertions.assertThat(tools).isNotEmpty().contains(toolSaved);
    }
}
