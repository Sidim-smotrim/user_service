package school.faang.user_service.controller.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.goal.GoalInvitationDto;
import school.faang.user_service.service.GoalInvitationService;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalInvitationController {
    private final GoalInvitationService goalInvitationService;

    @PostMapping("/createInvitation/{id}")
    public GoalInvitationDto createInvitation(@RequestBody GoalInvitationDto invitationDto) {
        return goalInvitationService.createInvitation(invitationDto);
    }

    @PostMapping("/acceptInvitation/{id}")
    public void acceptGoalInvitation(long id) {
        goalInvitationService.acceptGoalInvitation(id);
    }
}
