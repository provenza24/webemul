
package net.thegamesdb;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "allowance_refresh_timer",
    "code",
    "pages",
    "data",
    "extra_allowance",
    "remaining_monthly_allowance",
    "status"
})
public class Game {

    @JsonProperty("allowance_refresh_timer")
    private Integer allowanceRefreshTimer;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("pages")
    private Pages pages;
    @JsonProperty("data")
    private Data data;
    @JsonProperty("extra_allowance")
    private Integer extraAllowance;
    @JsonProperty("remaining_monthly_allowance")
    private Integer remainingMonthlyAllowance;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("allowance_refresh_timer")
    public Integer getAllowanceRefreshTimer() {
        return allowanceRefreshTimer;
    }

    @JsonProperty("allowance_refresh_timer")
    public void setAllowanceRefreshTimer(Integer allowanceRefreshTimer) {
        this.allowanceRefreshTimer = allowanceRefreshTimer;
    }

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("pages")
    public Pages getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Pages pages) {
        this.pages = pages;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    @JsonProperty("extra_allowance")
    public Integer getExtraAllowance() {
        return extraAllowance;
    }

    @JsonProperty("extra_allowance")
    public void setExtraAllowance(Integer extraAllowance) {
        this.extraAllowance = extraAllowance;
    }

    @JsonProperty("remaining_monthly_allowance")
    public Integer getRemainingMonthlyAllowance() {
        return remainingMonthlyAllowance;
    }

    @JsonProperty("remaining_monthly_allowance")
    public void setRemainingMonthlyAllowance(Integer remainingMonthlyAllowance) {
        this.remainingMonthlyAllowance = remainingMonthlyAllowance;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
