package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipService {
    private final UserService userService;
    private final UserMapper userMapper;

    public List<UserDto> getMentees(Long userId) {
        User user = userService.getUserById(userId);
        List<User> mentees = user.getMentees();
        return userMapper.toDto(mentees);
    }

    public List<UserDto> getMentors(Long userId) {
        User user = userService.getUserById(userId);
        List<User> mentors = user.getMentors();
        return userMapper.toDto(mentors);
    }

    public void removeMentorsMentee(Long mentorId, Long menteeId) {
        User mentor = userService.getUserById(mentorId);
        User mentee = userService.getUserById(menteeId);
        mentor.getMentees().remove(mentee);
    }

    public void removeMentorOfMentee(Long mentorId, Long menteeId) {
        User mentor = userService.getUserById(mentorId);
        User mentee = userService.getUserById(menteeId);
        mentee.getMentors().remove(mentor);
    }
}
