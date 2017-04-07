package org.kartishev.voltage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.kartishev.voltage.domain.enumeration.Language;

/**
 * A News.
 */
@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "version")
    private Integer version;

    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "jhi_body", nullable = false)
    private String body;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
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

    public News created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public News updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Integer getVersion() {
        return version;
    }

    public News version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public News title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public News body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getImage() {
        return image;
    }

    public News image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public News imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Language getLanguage() {
        return language;
    }

    public News language(Language language) {
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
        News news = (News) o;
        if (news.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "News{" +
            "id=" + id +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            ", version='" + version + "'" +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
