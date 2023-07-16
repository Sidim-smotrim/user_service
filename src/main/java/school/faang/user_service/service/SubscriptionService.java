package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public int getFollowersCount(long followeeId) {
        return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
    }
}
