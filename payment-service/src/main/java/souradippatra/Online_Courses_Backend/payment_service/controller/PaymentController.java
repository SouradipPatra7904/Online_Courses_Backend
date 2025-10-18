package souradippatra.Online_Courses_Backend.payment_service.controller;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import souradippatra.Online_Courses_Backend.payment_service.dto.PaymentDto;
import souradippatra.Online_Courses_Backend.payment_service.service.PaymentService;
import souradippatra.Online_Courses_Backend.payment_service.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<EntityModel<PaymentDto>> create(@RequestBody PaymentDto dto) {
        PaymentDto created = service.initiatePayment(dto);
        EntityModel<PaymentDto> model = EntityModel.of(created);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class)
                .getPayment(created.getId())).withSelfRel());
        return ApiResponse.success("Payment initiated", model);
    }

    @GetMapping
    public ApiResponse<CollectionModel<EntityModel<PaymentDto>>> getAll() {
        List<EntityModel<PaymentDto>> models = service.getAllPayments().stream()
                .map(p -> EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class)
                                .getPayment(p.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return ApiResponse.success("All payments fetched", CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    public ApiResponse<EntityModel<PaymentDto>> getPayment(@PathVariable Long id) {
        PaymentDto dto = service.getPaymentById(id);
        EntityModel<PaymentDto> model = EntityModel.of(dto);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class)
                .getPayment(id)).withSelfRel());
        return ApiResponse.success("Payment details fetched", model);
    }

    @PatchMapping("/{id}/status/{status}")
    public ApiResponse<EntityModel<PaymentDto>> updateStatus(@PathVariable Long id, @PathVariable String status) {
        PaymentDto updated = service.updatePaymentStatus(id, status);
        EntityModel<PaymentDto> model = EntityModel.of(updated);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class)
                .getPayment(id)).withSelfRel());
        return ApiResponse.success("Payment status updated", model);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.deletePayment(id);
        return ApiResponse.success("Payment deleted", null);
    }
}
