package org.example.javafinalproject.payloads.response;

public class ApiResponseBuilder {
    public static <T> ApiResponse<T> buildSuccessResponse(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> buildSuccessResponse(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static ApiResponse<Void> buildSuccessResponse(String message) {
        return new ApiResponse<>(true, message);
    }

    public static ApiResponse<Void> buildErrorResponse(String message) {
        return new ApiResponse<>(false, message);
    }
}
