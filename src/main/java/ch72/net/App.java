package ch72.net;

import ch72.net.jenkins.model.Job;
import ch72.net.jenkins.model.JobSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App 
{
    private static final HttpAuthenticationFeature authFeature = getAuthFeature();

    public static void main( String[] args ) throws IOException {

        JobSummary jobSummary = getJobSummary();
        List<Job> jobList = getJobList(jobSummary);

        jobList.forEach(System.out::println);
    }

    private static String jsonResponse(String path) {
        return target(path)
                .path("api/json").queryParam("pretty", "true")
                .register(authFeature)
                .request(MediaType.TEXT_HTML_TYPE)
                .get(String.class);
    }

    // TODO error handling

    private static JobSummary getJobSummary() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String res = jsonResponse(rootUrl());
            return mapper.readValue(res, JobSummary.class);
        } catch (IOException e) {
            throw e;
        }
    }

    private static Job getJob(String path) throws IOException {
        String res = jsonResponse(path);
        System.out.println(res);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(res, Job.class);
        } catch (IOException e) {
            throw e;
        }
    }

    private static List<Job> getJobList(JobSummary summary) throws IOException {

        List<Job> ret = new ArrayList();
        for (Job elem : summary.getJobList()) {
            try {
                ret.add(getJob(elem.getUrl()));
            } catch (IOException e) {
                throw e;
            }
        }
        return ret;
    }

    // TODO 毎回生成すると遅いかもしれない
    private static WebTarget target(String path) {
        return ClientBuilder.newClient().target(path);
    }

    private static String rootUrl() {
        String host = System.getProperty("host");
        String port = System.getProperty("port");
        return "http://" + host + ":" + port;
    }

    private static HttpAuthenticationFeature getAuthFeature() {
        String userName = System.getProperty("userName");
        String password = System.getProperty("password");
        return HttpAuthenticationFeature.basic(userName, password);
    }

}
