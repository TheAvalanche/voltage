package org.kartishev.voltage.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import org.kartishev.voltage.domain.enumeration.Language;

/**
 * A DTO for the Blog entity.
 */
public class BlogDTO implements Serializable {

    private Long id;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private Integer version;

    @NotNull
    @Size(min = 0, max = 255)
    private String title;

    @NotNull
    @Lob
    private String body;

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
    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

        if ( ! Objects.equals(id, blogDTO.id)) { return false; }

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
            ", updated='" + updated + "'" +
            ", version='" + version + "'" +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
