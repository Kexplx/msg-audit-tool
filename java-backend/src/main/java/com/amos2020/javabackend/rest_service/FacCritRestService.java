package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.FacCritController;
import com.amos2020.javabackend.rest_service.response.BasicFacCritResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Factor / Criteria", description = "The endpoints for the facCrit resource")
public class FacCritRestService {

    final FacCritController facCritController;

    public FacCritRestService(FacCritController facCritController) {
        this.facCritController = facCritController;
    }

    /**
     * GET Endpoint for receiving all existing facCrits
     *
     * @return ResponseEntity with a List of the facCrits
     */
    @Operation(summary = "Get all facCrits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive all existing facCrits")
    })
    @GetMapping("/faccrits")
    public ResponseEntity<List<BasicFacCritResponse>> getAllFacCrits() {
        return ResponseEntity.ok(facCritController.getAllFacCrits());
    }
}
