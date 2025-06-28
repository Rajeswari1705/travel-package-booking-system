package com.booking.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a standard API response.
 * This class is used to encapsulate the response data, success status, and message for API endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	
    /**Indicates whether the API call was successful.*/
    private boolean success;

    /**The message associated with the API response.*/
    private String message;

    /**The data returned by the API call.*/
    private Object data;

}
