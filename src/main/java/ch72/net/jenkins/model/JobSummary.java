package ch72.net.jenkins.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by asato on 2016/08/21.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobSummary {

    @JsonProperty("jobs")
    private List<Job> jobList;

    public String toString() {
        String ret = "Jobs String" + System.lineSeparator();
        for (Job job : jobList) {
            ret = ret + job.toString() + System.lineSeparator();
        }
        return ret;
    }

}
