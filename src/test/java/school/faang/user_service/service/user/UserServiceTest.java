package school.faang.user_service.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.UserProfilePic;
import school.faang.user_service.mapper.UserMapperImpl;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.service.diceBear.DiceBearService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Spy
    private UserMapperImpl userMapper;
    @Mock
    private DiceBearService diceBearService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("test")
                .build();

        User user = User.builder()
                .id(1L)
                .username("test")
                .build();

        UserProfilePic userProfilePic = UserProfilePic.builder()
                .name(userDto.getUsername() + userDto.getId())
                .build();

        Mockito.when(userMapper.toEntity(userDto))
                .thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        userService.createUser(userDto);

        Mockito.verify(userMapper, Mockito.times(1))
                .toEntity(userDto);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
        Mockito.verify(diceBearService, Mockito.times(1))
                        .createAvatar(userProfilePic);
        Mockito.verify(userMapper, Mockito.times(1))
                .toDto(user);

    }

    @Test
    public void testAddCreateData() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("test")
                .build();
        User user = new User();
        Mockito.when(userMapper.toEntity(userDto))
                .thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        userService.createUser(userDto);

        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now()));
        assertEquals(user.getUserProfilePic().getName(),
                user.getUsername() + user.getId());
    }

    @Test
    void areOwnedSkills() {
        assertTrue(userService.areOwnedSkills(1L, List.of()));
    }

    @Test
    void areOwnedSkillsFalse() {
        Mockito.when(userRepository.countOwnedSkills(1L, List.of(2L))).thenReturn(3);
        assertFalse(userService.areOwnedSkills(1L, List.of(2L)));
    }

    @Test
    void getUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        userService.getUser(1L);
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getUsersByIds() {
        Mockito.when(userRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(User.builder().id(1L).build(), User.builder().id(2L).build()));
        List<UserDto> users = userService.getUsersByIds(List.of(1L, 2L));
        assertEquals(2, users.size());
    }
}