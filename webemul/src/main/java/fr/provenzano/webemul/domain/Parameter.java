package fr.provenzano.webemul.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.provenzano.webemul.domain.enumeration.ParameterType;

/**
 * A Parameter.
 */
@Entity
@Table(name = "parameter")
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private ParameterType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Parameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public Parameter label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public Parameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterType getType() {
        return type;
    }

    public Parameter type(ParameterType type) {
        this.type = type;
        return this;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parameter parameter = (Parameter) o;
        if (parameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parameter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
