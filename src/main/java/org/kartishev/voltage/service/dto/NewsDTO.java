package org.kartishev.voltage.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import org.kartishev.voltage.domain.enumeration.Language;

/**
 * A DTO for the News entity.
 */
public class NewsDTO implements Serializable {

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

    @Lob
    private byte[] image;
    private String imageContentType;

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
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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

        NewsDTO newsDTO = (NewsDTO) o;

        if ( ! Objects.equals(id, newsDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
            "id=" + id +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            ", version='" + version + "'" +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", image='" + image + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
