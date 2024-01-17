package com.asalih.orderservice.service;

import com.asalih.orderservice.dto.OrderLineItemsDto;
import com.asalih.orderservice.dto.OrderRequest;
import com.asalih.orderservice.model.Order;
import com.asalih.orderservice.model.OrderLineItems;
import com.asalih.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder().orderNumber(UUID.randomUUID().toString()).build();
        List<OrderLineItems> orderLineItemsList =
                orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::mapToDto)
                        .toList();
        order.setOrderLineItemsList(orderLineItemsList);

        // Call inventory service, and place order if product is in stock


        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
