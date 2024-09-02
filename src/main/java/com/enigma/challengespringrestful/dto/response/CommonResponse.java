package com.enigma.challengespringrestful.dto.response;

import lombok.*;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private PagingResponse paging;

    @Override
    public String toString() {
        return "CommonResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", paging=" + paging +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResponse<?> that = (CommonResponse<?>) o;
        return Objects.equals(statusCode, that.statusCode) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data) &&
                Objects.equals(paging, that.paging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message, data, paging);
    }
}
