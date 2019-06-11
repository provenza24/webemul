package fr.provenzano.webemul.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Console entity.
 */
public class ConsoleDTO implements Serializable {

    private Long id;

    @NotNull
    private String abbreviation;

    @NotNull
    private String name;

    @NotNull
    private String pathIcon;

    @NotNull
    private String pathControllerIcon;

    private String manufacturer;

    private Integer generation;

    private Integer bits;

    private String resume;

    private String pathRomsFolder;

    @NotNull
    private Integer tgdbId;

    private Long defaultEmulatorId;

    private Set<EmulatorDTO> emulators = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathIcon() {
        return pathIcon;
    }

    public void setPathIcon(String pathIcon) {
        this.pathIcon = pathIcon;
    }

    public String getPathControllerIcon() {
        return pathControllerIcon;
    }

    public void setPathControllerIcon(String pathControllerIcon) {
        this.pathControllerIcon = pathControllerIcon;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public Integer getBits() {
        return bits;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPathRomsFolder() {
        return pathRomsFolder;
    }

    public void setPathRomsFolder(String pathRomsFolder) {
        this.pathRomsFolder = pathRomsFolder;
    }

    public Integer getTgdbId() {
        return tgdbId;
    }

    public void setTgdbId(Integer tgdbId) {
        this.tgdbId = tgdbId;
    }

    public Long getDefaultEmulatorId() {
        return defaultEmulatorId;
    }

    public void setDefaultEmulatorId(Long emulatorId) {
        this.defaultEmulatorId = emulatorId;
    }

    public Set<EmulatorDTO> getEmulators() {
        return emulators;
    }

    public void setEmulators(Set<EmulatorDTO> emulators) {
        this.emulators = emulators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsoleDTO consoleDTO = (ConsoleDTO) o;
        if(consoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsoleDTO{" +
            "id=" + getId() +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", name='" + getName() + "'" +
            ", pathIcon='" + getPathIcon() + "'" +
            ", pathControllerIcon='" + getPathControllerIcon() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", generation=" + getGeneration() +
            ", bits=" + getBits() +
            ", resume='" + getResume() + "'" +
            ", pathRomsFolder='" + getPathRomsFolder() + "'" +
            ", tgdbId=" + getTgdbId() +
            "}";
    }
}
