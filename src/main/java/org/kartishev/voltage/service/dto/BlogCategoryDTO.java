package org.kartishev.voltage.service.dto;


import org.kartishev.voltage.domain.enumeration.Language;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


public class BlogCategoryDTO implements Serializable {

    private Long id;

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

        if (!Objects.equals(id, blogCategoryDTO.id)) {
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
        return "BlogCategoryDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
