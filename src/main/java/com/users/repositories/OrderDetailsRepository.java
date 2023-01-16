package com.users.repositories;

import com.users.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-orders
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/04/22
 * Time: 12.08
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
