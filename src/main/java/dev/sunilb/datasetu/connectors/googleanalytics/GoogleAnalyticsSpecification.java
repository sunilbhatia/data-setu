package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.sunilb.datasetu.entities.DateRange;

import java.util.*;

public class GoogleAnalyticsSpecification {

    private String viewId;
    private final List<String> metrics;
    private final List<String> dimensions;
    private final List<DateRange> dateRanges;
    private int pageSize;
    private String nextPageToken;
    private GoogleAuthentication googleAuthentication;
    private final HashMap<String, String> headers;

    private GoogleAnalyticsSpecification() {
        this.metrics = new ArrayList<>();
        this.dimensions = new ArrayList<>();
        this.dateRanges = new ArrayList<>();
        this.pageSize = 5; //TODO: Find the default page size and set it to that.
        this.nextPageToken = "";
        this.headers = new HashMap<>();
        initializeDefaultHeaders();
    }

    private void initializeDefaultHeaders() {
        this.headers.put("Content-Type", "application/json");
    }

    public static GoogleAnalyticsSpecification Builder() {
        return new GoogleAnalyticsSpecification();
    }

    public GoogleAnalyticsSpecification forView(String viewId) {
        this.viewId = viewId;
        return this;
    }

    public GoogleAnalyticsSpecification forDateRange(String startDate, String endDate) {

        DateRange dateRange = new DateRange(startDate, endDate);
        this.dateRanges.add(dateRange);
        return this;
    }

    public GoogleAnalyticsSpecification metrics(List<String> metrics) {
        metrics.forEach(s -> this.metrics.add(s.trim()));
        return this;
    }

    public GoogleAnalyticsSpecification dimensions(List<String> dimensions) {
        dimensions.forEach(s -> this.dimensions.add(s.trim()));
        return this;
    }

    public GoogleAnalyticsSpecification withAuthentication(GoogleAuthentication googleAuthentication) {
        this.googleAuthentication = googleAuthentication;
        return this;
    }

    public GoogleAnalyticsRequest build() throws JsonProcessingException {
        String gaRequestJsonBody = getGARequestJsonBody();
        String gaAPIURL = getGAAPIURLPath();
        Map<String, String> headers = getGAHeaders();

        GoogleAnalyticsRequest gaRequest = new GoogleAnalyticsRequest(gaRequestJsonBody, gaAPIURL, headers);

        //TODO: Raise exception if all mandatory initializations have not been done

        return gaRequest;
    }

    public void updateGoogleAuthentication(GoogleAuthentication gaAuthentication) {
        this.googleAuthentication = gaAuthentication;
    }

    private Map<String, String> getGAHeaders() {
        return (Map<String, String>) this.headers.clone();
    }

    private String getGAAPIURLPath() {

        String accessToken = "";

        if (this.googleAuthentication != null) accessToken = this.googleAuthentication.getAccessToken();

        return "https://analyticsreporting.googleapis.com/v4/reports:batchGet?access_token=" + accessToken;
    }

    private String getGARequestJsonBody() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode reportRequestArrayNode = rootNode.putArray("reportRequests");
        ObjectNode reportRequestNode = mapper.createObjectNode();
        reportRequestNode.put("viewId", this.viewId);

        ArrayNode reportDateRanges = reportRequestNode.putArray("dateRanges");
        ObjectNode reportDateRangeNode = null;
        for (DateRange dateRange : dateRanges) {
            reportDateRangeNode = reportDateRanges.addObject();
            reportDateRangeNode.put("startDate", dateRange.getStartDate());
            reportDateRangeNode.put("endDate", dateRange.getEndDate());
        }

        ArrayNode metricsNode = reportRequestNode.putArray("metrics");
        ObjectNode metricsObject = null;

        for (String metric : metrics) {
            metricsObject = metricsNode.addObject();
            metricsObject.put("expression", metric);
        }


        ArrayNode dimensionsNode = reportRequestNode.putArray("dimensions");
        ObjectNode dimensionsObject = null;

        for (String dimension : dimensions) {
            dimensionsObject = dimensionsNode.addObject();
            dimensionsObject.put("name", dimension);
        }

        reportRequestNode.put("pageSize", this.pageSize);

        if (!this.nextPageToken.isEmpty()) {
            reportRequestNode.put("pageToken", this.nextPageToken);
        }

        reportRequestArrayNode.add(reportRequestNode);

        String gaRequestJsonBody = mapper.writeValueAsString(rootNode);
        return gaRequestJsonBody;
    }

    public GoogleAnalyticsSpecification pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public GoogleAnalyticsSpecification setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
        return this;
    }
}
