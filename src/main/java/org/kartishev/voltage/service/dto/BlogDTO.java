package org.kartishev.voltage.service.dto;


import org.kartishev.voltage.domain.enumeration.Language;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BlogDTO implements Serializable {

    private Long id;

    private ZonedDateTime created;

    @NotNull
    @Size(min = 0, max = 255)
    private String title;

    @NotNull
    @Lob
    private String body;

    @Size(min = 0, max = 4000)
    private String imageUrl;

    @NotNull
    private Language language;

    private Set<BlogCategoryDTO> blogCategories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<BlogCategoryDTO> getBlogCategories() {
        return blogCategories;
    }

    public void setBlogCategories(Set<BlogCategoryDTO> blogCategories) {
        this.blogCategories = blogCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogDTO blogDTO = (BlogDTO) o;

        if (!Objects.equals(id, blogDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
            "id=" + id +
            ", created='" + created + "'" +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
