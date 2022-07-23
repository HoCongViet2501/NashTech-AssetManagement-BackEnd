package com.nashtech.rookies.java05.AssetManagement.exception;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private String errors;
}
