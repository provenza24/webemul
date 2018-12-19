package fr.provenzano.webemul.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Console.
 */
@Entity
@Table(name = "console")
public class Console implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path_icon")
    private String pathIcon;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "generation")
    private Integer generation;

    @Column(name = "bits")
    private Integer bits;

    @Column(name = "resume")
    private String resume;

    @OneToMany(mappedBy = "console")
    @JsonIgnore
    private Set<Rom> roms = new HashSet<>();

    @ManyToOne
    private Emulator defaultEmulator;

    @ManyToMany
    @JoinTable(name = "console_emulators",
               joinColumns = @JoinColumn(name="consoles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="emulators_id", referencedColumnName="id"))
    private Set<Emulator> emulators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Console abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public Console name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathIcon() {
        return pathIcon;
    }

    public Console pathIcon(String pathIcon) {
        this.pathIcon = pathIcon;
        return this;
    }

    public void setPathIcon(String pathIcon) {
        this.pathIcon = pathIcon;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Console manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getGeneration() {
        return generation;
    }

    public Console generation(Integer generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public Integer getBits() {
        return bits;
    }

    public Console bits(Integer bits) {
        this.bits = bits;
        return this;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public String getResume() {
        return resume;
    }

    public Console resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Set<Rom> getRoms() {
        return roms;
    }

    public Console roms(Set<Rom> roms) {
        this.roms = roms;
        return this;
    }

    public Console addRoms(Rom rom) {
        this.roms.add(rom);
        rom.setConsole(this);
        return this;
    }

    public Console removeRoms(Rom rom) {
        this.roms.remove(rom);
        rom.setConsole(null);
        return this;
    }

    public void setRoms(Set<Rom> roms) {
        this.roms = roms;
    }

    public Emulator getDefaultEmulator() {
        return defaultEmulator;
    }

    public Console defaultEmulator(Emulator emulator) {
        this.defaultEmulator = emulator;
        return this;
    }

    public void setDefaultEmulator(Emulator emulator) {
        this.defaultEmulator = emulator;
    }

    public Set<Emulator> getEmulators() {
        return emulators;
    }

    public Console emulators(Set<Emulator> emulators) {
        this.emulators = emulators;
        return this;
    }

    public Console addEmulators(Emulator emulator) {
        this.emulators.add(emulator);
        return this;
    }

    public Console removeEmulators(Emulator emulator) {
        this.emulators.remove(emulator);
        return this;
    }

    public void setEmulators(Set<Emulator> emulators) {
        this.emulators = emulators;
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
        Console console = (Console) o;
        if (console.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), console.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Console{" +
            "id=" + getId() +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", name='" + getName() + "'" +
            ", pathIcon='" + getPathIcon() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", generation=" + getGeneration() +
            ", bits=" + getBits() +
            ", resume='" + getResume() + "'" +
            "}";
    }
}
