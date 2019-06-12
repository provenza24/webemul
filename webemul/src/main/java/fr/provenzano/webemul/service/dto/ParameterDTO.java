package fr.provenzano.webemul.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.provenzano.webemul.domain.enumeration.ParameterType;

/**
 * A DTO for the Parameter entity.
 */
public class ParameterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String label;

    @NotNull
    private String value;

    @NotNull
    private ParameterType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParameterDTO parameterDTO = (ParameterDTO) o;
        if(parameterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parameterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParameterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
