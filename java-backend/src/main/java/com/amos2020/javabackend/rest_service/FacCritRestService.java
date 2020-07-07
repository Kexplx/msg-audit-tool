package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.FacCritController;
import com.amos2020.javabackend.rest_service.response.BasicFacCritResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
/**
 * Provides endpoints for the facCrit resource under /faccrits
 */
@RestController
@Validated
@Tag(name = "Factor / Criteria", description = "The endpoints for the facCrit resource")
@CrossOrigin
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

    /**
     * GET Endpoint for all distinct used Faccrits in a specific Interview
     *
     * @param interviewId
     * @return ResponseEntity with a sorted List of Faccrits
     */
    @Operation(summary = "Get all distinct Faccrits that are used in a specific Interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive all existing facCrits"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @GetMapping("/faccrits/interview/{id}")
    public ResponseEntity<List<BasicFacCritResponse>> getAllFacCritsByInterviewId(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int interviewId) {
        List<BasicFacCritResponse> response;
        try{
            response = facCritController.getAllFacCritsByInterviewId(interviewId);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}
