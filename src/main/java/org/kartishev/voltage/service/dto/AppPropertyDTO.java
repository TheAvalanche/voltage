package org.kartishev.voltage.service.dto;


import org.kartishev.voltage.domain.enumeration.AppPropertyType;
import org.kartishev.voltage.domain.enumeration.Language;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AppProperty entity.
 */
public class AppPropertyDTO implements Serializable {

    private Long id;

    private AppPropertyType propertyType;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 2, max = 255)
    private String value;

    private Language language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppPropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(AppPropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        AppPropertyDTO appPropertyDTO = (AppPropertyDTO) o;

        if (!Objects.equals(id, appPropertyDTO.id)) {
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
        return "AppPropertyDTO{" +
            "id=" + id +
            ", type='" + propertyType + "'" +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", language='" + language + "'" +
            '}';
    }
}
