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
 * A Slide.
 */
@Entity
@Table(name = "slide")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Slide extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Size(min = 0, max = 4000)
    @Column(name = "image_url", length = 4000, nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;


    public Slide version(Long version) {
        setVersion(version);
        return this;
    }

    public Slide created(ZonedDateTime created) {
        setCreated(created);
        return this;
    }

    public Slide updated(ZonedDateTime updated) {
        setUpdated(updated);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Slide title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Slide description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Slide imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Language getLanguage() {
        return language;
    }

    public Slide language(Language language) {
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
        Slide slide = (Slide) o;
        if (slide.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slide.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Slide{" +
            "id=" + getId() +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", imageUrl='" + imageUrl + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
