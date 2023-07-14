package school.faang.user_service.controller.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.Exeptions.DataValidationException;
import school.faang.user_service.dto.goal.GoalDto;
import school.faang.user_service.dto.goal.GoalFilterDto;
import school.faang.user_service.entity.goal.Goal;
import school.faang.user_service.service.goal.GoalService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalController {
    private final GoalService service;

    private void validate (Long userId, GoalFilterDto filter) throws DataValidationException {
        if (userId == null) throw new DataValidationException("userId can not be Null");
        if (userId < 1) throw new DataValidationException("userId can not be less than 1");
    }

    public List<Goal> getGoalsByUser(Long userId, GoalFilterDto filter) {
        try {
            validate(userId, filter);
        } catch (DataValidationException e) {
            throw new RuntimeException("Validation Error");
        }
        return service.getGoalsByUser(userId, filter);
    }

    public void createGoal(Long userId, Goal goal) throws DataValidationException {
        if (goal.getTitle() == null || goal.getTitle().isBlank())
            throw new DataValidationException("Title can not be blank or null");
        service.createGoal(userId, goal);
    }

    public void updateGoal(Long goalId, GoalDto goal) throws DataValidationException {
        if (goal.getTitle() == null || goal.getTitle().isBlank())
            throw new DataValidationException("Title can not be blank or null");
        service.updateGoal(goalId, goal);
    }

    public void deleteGoal(Long goalId) throws DataValidationException {
        service.deleteGoal(goalId);
    }
}
