package ch72.net.jenkins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by asato on 2016/08/21.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {

    private String name;

    private String url;

    public String toString() {
        return "Jenkins job : name = " + name + ", url = " + url;
    }
}
