package com.vintage.infrastructure.adapter.in;

import com.vintage.domain.model.DetailedPurchase;
import com.vintage.domain.model.LoyalCustomer;
import com.vintage.infrastructure.adapter.in.exception.validator.PurchaseYear;
import com.vintage.port.in.CustomerApiInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name= "Customer Api",
        description = "displays information about customers and their purchases."
)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerApiInputAdapter {

    private final CustomerApiInputPort customerApiInputPort;

    @Operation(summary = "Lists the most loyal customers.",
            description = "Lists the most loyal customers based on the number of highest-value purchases. The customer " +
                    "limit is **3** by default, but configurable."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loyal customers listed.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LoyalCustomer.class))
                    )
            })
    })
    @GetMapping("clientes-fieis")
    public ResponseEntity<List<LoyalCustomer>> listMostLoyalCustomers() {
        final List<LoyalCustomer> mostLoyalCustomers = customerApiInputPort.listMostLoyalCustomers();
        return ResponseEntity.ok().body(mostLoyalCustomers);
    }

    @Operation(summary = "Lists customer purchases.",
            description = "Lists customer purchases ordered by total purchase value."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchases listed.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DetailedPurchase.class))
                    )
                    })
    })
    @GetMapping("compras")
    public ResponseEntity<List<DetailedPurchase>> listPurchases() {
        final List<DetailedPurchase> list = customerApiInputPort.listPurchases();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Returns the largest purchase of the year.",
            description = "Returns the largest purchase of the year, considering quantity and product value."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Largest purchase of the year returned.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = DetailedPurchase.class
                            )
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Purchase not found.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Invalid year.",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @GetMapping("maior-compra/{ano}")
    public ResponseEntity<DetailedPurchase> getBiggestYearPurchase(@Parameter(description = "Year of the purchase.")
                                                                   @NotNull(message = "Ano não informado.")
                                                                   @PurchaseYear(message = "Ano inválido.")
                                                                   @PathVariable("ano") final int year) {
        final DetailedPurchase detailedPurchase = customerApiInputPort.getBiggestYearPurchase(year);
        return ResponseEntity.ok().body(detailedPurchase);
    }
}
