package com.example.jejuiiin.domain;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.MemberRepository;

@SpringBootTest
class CartItemTest {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Disabled
	@DisplayName("장바구니 아이템 100개 설정")
	@Test
	void set100CartItem() {
		Optional<Member> member = memberRepository.findByLoginId("a123");
		Member savedMember = member.get();

		for (int productId = 1; productId <= 100; productId++) {
			CartItem cartItem = CartItem.builder()
				.member(savedMember)
				.productId((long)productId)
				.thumbnail_img_url("test_img_url")
				.name("test_product_name")
				.sellingPrice(1000)
				.quantity(100)
				.summation(100000)
				.build();

			cartRepository.save(cartItem);
			System.out.println("유저 " + savedMember.getName() + " 상품 " + productId + " 담기");
		}

	}

}