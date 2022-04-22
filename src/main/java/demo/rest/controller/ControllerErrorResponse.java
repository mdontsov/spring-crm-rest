package demo.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// WIll return a JSON error response
public class ControllerErrorResponse {

    private int status;
    private String message;
    private long timeStamp;
}
