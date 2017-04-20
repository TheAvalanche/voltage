package org.kartishev.voltage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kartishev.voltage.domain.enumeration.Language;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blog implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "blog_blog_category",
               joinColumns = @JoinColumn(name="blogs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="blog_categories_id", referencedColumnName="id"))
    private Set<BlogCategory> blogCategories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Blog created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public Blog updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Integer getVersion() {
        return version;
    }

    public Blog version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public Blog title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public Blog body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Language getLanguage() {
        return language;
    }

    public Blog language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<BlogCategory> getBlogCategories() {
        return blogCategories;
    }

    public Blog blogCategories(Set<BlogCategory> blogCategories) {
        this.blogCategories = blogCategories;
        return this;
    }

    public Blog addBlogCategory(BlogCategory blogCategory) {
        this.blogCategories.add(blogCategory);
        //blogCategory.getBlogs().add(this);
        return this;
    }

    public Blog removeBlogCategory(BlogCategory blogCategory) {
        this.blogCategories.remove(blogCategory);
        //blogCategory.getBlogs().remove(this);
        return this;
    }

    public void setBlogCategories(Set<BlogCategory> blogCategories) {
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
        Blog blog = (Blog) o;
        if (blog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Blog{" +
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
