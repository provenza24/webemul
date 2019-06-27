package fr.provenzano.webemul.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the Rom entity.
 */
public class RomDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String pathFile;

    private String extension;

    private String pathCover;

    @Lob
    private byte[] cover;
    private String coverContentType;

    private Long consoleId;

    private Set<GenreDTO> genres = new HashSet<>();
    
    private LocalDate releaseDate;

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

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPathCover() {
        return pathCover;
    }

    public void setPathCover(String pathCover) {
        this.pathCover = pathCover;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(Long consoleId) {
        this.consoleId = consoleId;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

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

        RomDTO romDTO = (RomDTO) o;
        if(romDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), romDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RomDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pathFile='" + getPathFile() + "'" +
            ", extension='" + getExtension() + "'" +
            ", pathCover='" + getPathCover() + "'" +
            ", cover='" + getCover() + "'" +
            "}";
    }
}
