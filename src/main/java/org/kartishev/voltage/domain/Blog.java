package org.kartishev.voltage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kartishev.voltage.domain.enumeration.Language;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class Blog extends BaseEntity {

    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "jhi_body", nullable = false)
    private String body;

    @Size(min = 0, max = 4000)
    @Column(name = "image_url", length = 4000)
    private String imageUrl;

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

    public Blog created(ZonedDateTime created) {
        setCreated(created);
        return this;
    }

    public Blog version(Long version) {
        setVersion(version);
        return this;
    }

    public Blog updated(ZonedDateTime updated) {
        setUpdated(updated);
        return this;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Blog imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        if (blog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", version='" + getVersion() + "'" +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
