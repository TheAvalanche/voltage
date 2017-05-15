package org.kartishev.voltage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kartishev.voltage.domain.enumeration.AppPropertyType;
import org.kartishev.voltage.domain.enumeration.Language;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AppProperty.
 */
@Entity
@Table(name = "app_property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppProperty extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private AppPropertyType propertyType;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "jhi_value", length = 255, nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    public AppProperty version(Long version) {
        setVersion(version);
        return this;
    }

    public AppProperty created(ZonedDateTime created) {
        setCreated(created);
        return this;
    }

    public AppProperty updated(ZonedDateTime updated) {
        setUpdated(updated);
        return this;
    }

    public AppPropertyType getPropertyType() {
        return propertyType;
    }

    public AppProperty type(AppPropertyType type) {
        this.propertyType = type;
        return this;
    }

    public void setPropertyType(AppPropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getName() {
        return name;
    }

    public AppProperty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public AppProperty value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public AppProperty language(Language language) {
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
        AppProperty appProperty = (AppProperty) o;
        if (appProperty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appProperty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppProperty{" +
            "id=" + getId() +
            ", type='" + propertyType + "'" +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
