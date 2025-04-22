package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.CreateSupplierRequest;
import com.project.travelExperts.data.dto.request.UpdateSupplierRequest;
import com.project.travelExperts.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService ;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.findAllSuppliers());
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable @Valid @NotNull(message = "Seller Id is required") long supplierId) {
        return ResponseEntity.ok(supplierService.findSupplierById(supplierId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSupplier(@RequestBody @Valid CreateSupplierRequest createSupplierRequest) {
        return ResponseEntity.status(201).body(supplierService.createNewSupplier(createSupplierRequest));
    }

    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable @Valid @NotNull(message = "Supplier Id is required") long supplierId, @RequestBody UpdateSupplierRequest createSupplierRequest) {
        return ResponseEntity.ok(supplierService.updateSupplier(supplierId, createSupplierRequest));
    }

    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable @Valid @NotNull(message = "Supplier Id is required") long supplierId) {
        return ResponseEntity.ok(supplierService.deleteSupplierBySupplierId(supplierId));
    }
}
