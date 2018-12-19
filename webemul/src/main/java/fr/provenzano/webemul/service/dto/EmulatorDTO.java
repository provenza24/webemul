package fr.provenzano.webemul.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Emulator entity.
 */
public class EmulatorDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String version;

    private String pathIcon;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPathIcon() {
        return pathIcon;
    }

    public void setPathIcon(String pathIcon) {
        this.pathIcon = pathIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmulatorDTO emulatorDTO = (EmulatorDTO) o;
        if(emulatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emulatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmulatorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", pathIcon='" + getPathIcon() + "'" +
            "}";
    }
}
