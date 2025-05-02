package org.example.expert.domain.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserPerformanceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager entityManager;

	private String targetNickname;

	@BeforeEach
	void setUp() {
		int total = 1_000_000;
		int batchSize = 1000;
		List<User> users = new ArrayList<>(batchSize);

		long start = System.currentTimeMillis();

		for (int i = 0; i < total; i++) {
			String uuid = UUID.randomUUID().toString();
			if(i == 120){
				targetNickname = "nick_" + uuid;
			}
			String email = "user_" + uuid + "@test.com";
			String nickname = "nick_" + uuid;

			User user = new User(email, "123", UserRole.USER);
			user.changeNickname(nickname);
			users.add(user);

			if (users.size() == batchSize) {
				userRepository.saveAll(users);
				users.clear();

				entityManager.clear();

				if (i % 1_000 == 0) {
					System.out.println(i + "명 저장 완료");
				}
			}
		}

		if (!users.isEmpty()) {
			userRepository.saveAll(users);
			entityManager.clear();
		}

		long end = System.currentTimeMillis();
		System.out.println("데이터 생성 총 소요 시간(ms): " + (end - start));
	}

	@Test
	void testFindByNicknamePerformance() {
		List<Long> list = new ArrayList<>();
		for(int i = 0 ; i < 100; i++){

			long start = System.currentTimeMillis();
			UserResponse response = userService.getUserByNickname(targetNickname);
			long end = System.currentTimeMillis();

			System.out.println("닉네임 조회 시간(ms): " + (end - start));
			Assertions.assertNotNull(response);
			list.add(end-start);
		}

		Long sum = 0L;
		for(long num : list){
			sum += num;
		}
		System.out.println("닉네임 평균 조회 시간(ms): " + sum/list.size());
	}
}
