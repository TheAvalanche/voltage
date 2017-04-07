package org.kartishev.voltage.service.dto;


import org.kartishev.voltage.domain.enumeration.Language;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the BlogCategory entity.
 */
public class BlogCategoryDTO implements Serializable {

    private Long id;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private Integer version;

    @NotNull
    @Size(min = 0, max = 100)
    private String title;

    @NotNull
    private Language language;

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
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogCategoryDTO blogCategoryDTO = (BlogCategoryDTO) o;

        if ( ! Objects.equals(id, blogCategoryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogCategoryDTO{" +
            "id=" + id +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            ", version='" + version + "'" +
            ", title='" + title + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
