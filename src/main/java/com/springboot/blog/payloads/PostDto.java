package com.springboot.blog.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Schema(
        description = "PostDTO Model Information"
)
public class PostDto {
    private Long id;
    // title should not be null  or empty
    // title should have at least 3 characters
    @Schema(
            description = "Blog post title"
    )
    @NotEmpty
    @Size(min = 3, message = "Post title should have at least 3 characters")
    private String title;
    // post description should be not null or empty
    // post description should have at least 10 characters
    @Schema(
            description = "Blog post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @Schema(
            description = "Blog post content"
    )
    @NotEmpty
    private String content;
    @Schema(
            description = "Blog post categoryId"
    )
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
