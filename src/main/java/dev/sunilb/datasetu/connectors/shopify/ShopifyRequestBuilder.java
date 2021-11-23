package dev.sunilb.datasetu.connectors.shopify;

import dev.sunilb.datasetu.exceptions.DataSetuException;

public class ShopifyRequestBuilder {

    private String queryRoot;
    private String scanFrom;
    private long scanRows;
    private String fields;
    private String queryString;
    private String nextPageCursor;

    private ShopifyRequestBuilder() {
        this.scanRows = -1;
    }

    public static ShopifyRequestBuilder Builder() {
        ShopifyRequestBuilder builder = new ShopifyRequestBuilder();
        return builder;
    }

    public ShopifyRequestBuilder withQueryRoot(String queryRoot) {
        this.queryRoot = queryRoot;
        return this;
    }

    public String build() {

        if (queryRoot == null || scanFrom == null || fields == null)
            throw new DataSetuException("Cannot build Shopify Request Object as mandatory objects are not instantiated");

        StringBuilder requestObject = new StringBuilder();
        requestObject.append("{");
        requestObject.append("\"query\":\"");
        requestObject.append("{");
        requestObject.append(this.queryRoot);
        requestObject.append(this.getArguments());
        requestObject.append(this.getEdgesNode());
        requestObject.append("}\"");
        requestObject.append("}");

        return requestObject.toString();

    }

    private String getEdgesNode() {
        return "{pageInfo {hasNextPage, hasPreviousPage} edges {cursor node {" + fields + "}}}";
    }

    private String getArguments() {

        StringBuilder arguments = new StringBuilder();

        if (scanFrom.equals("first")) arguments.append("(first:" + scanRows);
        else if (scanFrom.equals("last")) arguments.append("(last:" + scanRows);

        if(this.nextPageCursor != null) {
            arguments.append(",after:\\\"" + this.nextPageCursor + "\\\"");
        }

        if (queryString != null) {
            arguments.append(",query:\\\"" + queryString + "\\\"");
        }

        arguments.append(")");

        return arguments.toString();
    }

    public ShopifyRequestBuilder first(long scanRows) {
        this.scanFrom = "first";
        this.scanRows = scanRows;
        return this;
    }

    public ShopifyRequestBuilder last(long scanRows) {
        this.scanFrom = "last";
        this.scanRows = scanRows;
        return this;
    }

    public ShopifyRequestBuilder withFields(String fields) {
        this.fields = fields;
        return this;
    }

    public ShopifyRequestBuilder withQuery(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public ShopifyRequestBuilder withNextPageCursor(String nextPageCursor) {
        this.nextPageCursor = nextPageCursor;
        return this;
    }
}
