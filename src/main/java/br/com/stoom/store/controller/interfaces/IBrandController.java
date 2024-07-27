package br.com.stoom.store.controller.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/brands")
public interface IBrandController {
    @Operation(
            summary = "Activate Brand REST API",
            description = "Activate Brand REST API is used to activate a brand"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Brand activated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Brand not found")
    })
    @PatchMapping("/{brandId}/activate")
    ResponseEntity<Void> activateBrand(@PathVariable Long brandId);

    @Operation(
            summary = "Deactivate Brand REST API",
            description = "Deactivate Brand REST API is used to deactivate a brand"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Brand deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Brand not found")
    })
    @PatchMapping("/{brandId}/deactivate")
    ResponseEntity<Void> deactivateBrand(@PathVariable Long brandId);
}
