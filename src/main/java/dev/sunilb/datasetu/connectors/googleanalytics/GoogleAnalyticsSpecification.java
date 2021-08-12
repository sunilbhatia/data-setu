package dev.sunilb.datasetu.connectors.googleanalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class GoogleAnalyticsSpecification {

    private Map<String, String> params;
    private String viewId;
    private String endDate;
    private String startDate;
    private List<String> metrics;
    private List<String> dimensions;
    private int pageSize;

    private GoogleAnalyticsSpecification() {
        this.params = new HashMap<>();
        this.metrics = new ArrayList<>();
        this.dimensions = new ArrayList<>();
        this.pageSize = 5; //TODO: Find the default page size and set it to that.
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
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String build() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ArrayNode reportRequestArrayNode = rootNode.putArray("reportRequests");
        ObjectNode reportRequestNode = mapper.createObjectNode();
        reportRequestNode.put("viewId", this.viewId);

        ArrayNode reportDateRanges = reportRequestNode.putArray("dateRanges");
        ObjectNode reportDateRangeNode = reportDateRanges.addObject();
        reportDateRangeNode.put("startDate", this.startDate);
        reportDateRangeNode.put("endDate", this.endDate);

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

        reportRequestArrayNode.add(reportRequestNode);

        return mapper.writeValueAsString(rootNode);
    }

    public GoogleAnalyticsSpecification pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
