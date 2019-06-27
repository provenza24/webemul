package fr.provenzano.webemul.domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Rom.
 */
@Entity
@Table(name = "rom")
public class Rom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "path_file", nullable = false)
    private String pathFile;

    @Column(name = "extension")
    private String extension;

    @Column(name = "path_cover")
    private String pathCover;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @ManyToOne
    private Console console;

    @ManyToMany
    @JoinTable(name = "rom_genres",
               joinColumns = @JoinColumn(name="roms_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="genres_id", referencedColumnName="id"))
    private Set<Genre> genres = new HashSet<>();
    
    @Column(name = "release_date")
    private LocalDate releaseDate;

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

    public Rom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathFile() {
        return pathFile;
    }

    public Rom pathFile(String pathFile) {
        this.pathFile = pathFile;
        return this;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getExtension() {
        return extension;
    }

    public Rom extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPathCover() {
        return pathCover;
    }

    public Rom pathCover(String pathCover) {
        this.pathCover = pathCover;
        return this;
    }

    public void setPathCover(String pathCover) {
        this.pathCover = pathCover;
    }

    public byte[] getCover() {
        return cover;
    }

    public Rom cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public Rom coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Console getConsole() {
        return console;
    }

    public Rom console(Console console) {
        this.console = console;
        return this;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Rom genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Rom addGenres(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public Rom removeGenres(Genre genre) {
        this.genres.remove(genre);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rom rom = (Rom) o;
        if (rom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pathFile='" + getPathFile() + "'" +
            ", extension='" + getExtension() + "'" +
            ", pathCover='" + getPathCover() + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + getCoverContentType() + "'" +
            "}";
    }
}
