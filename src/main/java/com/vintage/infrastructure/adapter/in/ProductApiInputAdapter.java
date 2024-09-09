package com.vintage.infrastructure.adapter.in;

import com.vintage.domain.model.Product;
import com.vintage.port.in.ProductApiInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name= "Product Api",
        description = "displays information about products."
)
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductApiInputAdapter {

    private final ProductApiInputPort productApiInputPort;

    @Operation(summary = "Recommends a wine.",
            description = "Recommends a wine based on the types of wine the customer buys the most."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommended wine.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Product.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Wine or Customer not found.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @GetMapping("recomendacao/{cliente}/tipo")
    public ResponseEntity<Product> recommend(@Parameter(description = "Name or CPF of the customer.")
                                                 @NotBlank(message = "Cliente não informado.")
                                                 @PathVariable("cliente") final String customerInfo) {
        return ResponseEntity.ok().body(productApiInputPort.recommend(customerInfo));
    }
}