package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.sunilb.datasetu.entities.DateRange;

import java.util.*;

public class GoogleAnalyticsSpecification {

    private final Map<String, String> params;
    private String viewId;
    private final List<String> metrics;
    private final List<String> dimensions;
    private final List<DateRange> dateRanges;
    private int pageSize;
    private String nextPageToken;

    private GoogleAnalyticsSpecification() {
        this.params = new HashMap<>();
        this.metrics = new ArrayList<>();
        this.dimensions = new ArrayList<>();
        this.dateRanges = new ArrayList<>();
        this.pageSize = 5; //TODO: Find the default page size and set it to that.
        this.nextPageToken = "";
    }

    public static GoogleAnalyticsSpecification Builder() {
        return new GoogleAnalyticsSpecification();
    }

    public GoogleAnalyticsSpecification params(String key, String value) {
        this.params.put(key, value);
        return this;
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

    public GoogleAnalyticsSpecification metrics(String... metrics) {
        Collections.addAll(this.metrics, metrics);
        return this;
    }

    public GoogleAnalyticsSpecification dimensions(String... dimensions) {
        Collections.addAll(this.dimensions, dimensions);
        return this;
    }

    public GoogleAnalyticsRequest build() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode reportRequestArrayNode = rootNode.putArray("reportRequests");
        ObjectNode reportRequestNode = mapper.createObjectNode();
        reportRequestNode.put("viewId", this.viewId);

        ArrayNode reportDateRanges = reportRequestNode.putArray("dateRanges");
        ObjectNode reportDateRangeNode = null;
        for(DateRange dateRange: dateRanges) {
            reportDateRangeNode = reportDateRanges.addObject();
            reportDateRangeNode.put("startDate", dateRange.getStartDate());
            reportDateRangeNode.put("endDate", dateRange.getEndDate());
        }

        ArrayNode metricsNode = reportRequestNode.putArray("metrics");
        ObjectNode metricsObject = null;

        for(String metric: metrics) {
            metricsObject = metricsNode.addObject();
            metricsObject.put("expression", metric);
        }


        ArrayNode dimensionsNode = reportRequestNode.putArray("dimensions");
        ObjectNode dimensionsObject = null;

        for(String dimension: dimensions) {
            dimensionsObject = dimensionsNode.addObject();
            dimensionsObject.put("name", dimension);
        }

        reportRequestNode.put("pageSize", this.pageSize);

        if(!this.nextPageToken.isEmpty()) {
            reportRequestNode.put("pageToken", this.nextPageToken);
        }

        reportRequestArrayNode.add(reportRequestNode);

        String gaRequestJsonBody = mapper.writeValueAsString(rootNode);
        GoogleAnalyticsRequest gaRequest = new GoogleAnalyticsRequest(gaRequestJsonBody);

        return gaRequest;
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
