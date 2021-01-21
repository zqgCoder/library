package com.example.busonline;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestfulResponse {
    // 判断操作是否成功
    private Boolean isOk;
    // 状态码
    private int statusCode;
    // 返回的提示消息
    private String message;
    // 具体数据，可以是user，list，map
    private Object data;
}
