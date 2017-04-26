package org.kartishev.voltage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kartishev.voltage.domain.enumeration.Language;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BlogCategory.
 */
@Entity
@Table(name = "blog_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogCategory extends BaseEntity {

    @NotNull
    @Size(min = 0, max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    public BlogCategory created(ZonedDateTime created) {
        setCreated(created);
        return this;
    }

    public BlogCategory version(Long version) {
        setVersion(version);
        return this;
    }

    public BlogCategory updated(ZonedDateTime updated) {
        setUpdated(updated);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlogCategory title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return language;
    }

    public BlogCategory language(Language language) {
        this.language = language;
        return this;
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
        BlogCategory blogCategory = (BlogCategory) o;
        if (blogCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blogCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlogCategory{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", version='" + getVersion() + "'" +
            ", title='" + title + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
