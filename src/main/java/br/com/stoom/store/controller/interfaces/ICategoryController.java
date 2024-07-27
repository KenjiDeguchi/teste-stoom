package br.com.stoom.store.controller.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/categories")
public interface ICategoryController {

    @Operation(
            summary = "Activate Category REST API",
            description = "Activate Category REST API is used to activate a category"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Category activated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Category not found")
    })
    @PatchMapping("/{categoryId}/activate")
    ResponseEntity<Void> activateCategory(@PathVariable Long categoryId);

    @Operation(
            summary = "Deactivate Category REST API",
            description = "Deactivate Category REST API is used to deactivate a category"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Category deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Category not found")
    })
    @PatchMapping("/{categoryId}/deactivate")
    ResponseEntity<Void> deactivateCategory(@PathVariable Long categoryId);
}
