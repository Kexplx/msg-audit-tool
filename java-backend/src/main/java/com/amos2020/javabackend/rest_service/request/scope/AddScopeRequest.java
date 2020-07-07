package com.amos2020.javabackend.rest_service.request.scope;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;
/**
 * Request object for adding elements to the scope of an existing audit project.
 */
public class AddScopeRequest extends BasicRequest {

    @Getter
    @Setter
    @Schema(type = "Array", name = "scope", example = "[1]", required = true)
    private List<@Min(1) Integer> scope;
}
