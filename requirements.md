# Requirements List

This list will be updated with the behavior needed from the system and also a few edge cases that the project wishes to cover.

### For Records
- ~~Should not allow to create duplicate field names~~
- ~~Addition of fields after the records are created is not allowed~~
- ~~Should provide the count of rows~~
- ~~Allow to get field name given a position number~~
- ~~Allow to get field position given a field name~~
- ~~Allow to insert a new row to be inserted into records~~
- Should not allow creating a row with varying fields
- Should not allow creating records with empty fields list
- ~~Should provide count of fields~~
- ~~Should allow to get a row given a record number~~

### For Rows
- ~~Should allow fields to be accessed via either Field Name or Field Position~~
- Support Rows to have data types (Low priority)

### For Google Analytics:
- ~~Get fixed json response from google analytics and convert it to records - 1 Json File Mocked~~
- ~~Get dynamic json response from google analytics and convert it to records (map from dynamic fields to Rows) - 3 Json Files Mocked with Different Dimensions & Metrics~~
- ~~Should return empty Records object when either no more records are available or when query yeilds no results~~
- ~~Paginate through the results~~
- Auto refresh the token and make a callback to client for update in their database
- In case auto refresh is disabled, then raise an exception for token expiry and after a new token is provided, continue paginating or querying
- Raise exception for Google Analytics HTTP Responses (403s, 5xx, Rate Limits, etc)
