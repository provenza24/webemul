
package net.thegamesdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "release_date",
    "developers",
    "id",
    "game_title",
    "platform"
})
public class Game_ {

    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("developers")
    private List<Integer> developers = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("game_title")
    private String gameTitle;
    @JsonProperty("platform")
    private Integer platform;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @JsonProperty("developers")
    public List<Integer> getDevelopers() {
        return developers;
    }

    @JsonProperty("developers")
    public void setDevelopers(List<Integer> developers) {
        this.developers = developers;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("game_title")
    public String getGameTitle() {
        return gameTitle;
    }

    @JsonProperty("game_title")
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    @JsonProperty("platform")
    public Integer getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	@Override
	public String toString() {
		return "Game_ [releaseDate=" + releaseDate + ", developers=" + developers + ", id=" + id + ", gameTitle="
				+ gameTitle + ", platform=" + platform + ", additionalProperties=" + additionalProperties + "]";
	}

}
