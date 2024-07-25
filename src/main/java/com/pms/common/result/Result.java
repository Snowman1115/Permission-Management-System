package com.pms.common.result;

import com.pms.common.constants.ResultConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result Class
 * @author Sn0w_15
 * @since 2024-07-24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success() {
        return new Result(ResultConstants.OK, "", null);
    }

    public static Result success(Object data) {
        return new Result(ResultConstants.OK, "", data);
    }

    public static Result success(Object data, String message) {
        return new Result(ResultConstants.OK, message, data);
    }

    public static Result error() {
        return new Result(ResultConstants.INTERNAL_SERVER_ERROR, "System Error", null);
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message, null);
    }

}
