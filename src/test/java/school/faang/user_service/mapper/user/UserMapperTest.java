package school.faang.user_service.mapper.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.user.UserCreateDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private UserCreateDto userCreateDto;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userCreateDto = UserCreateDto.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .password("qwerty")
                .countryId(4L)
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .aboutMe("about")
                .countryId(4L)
                .city("LA")
                .experience(1)
                .build();

        user = User.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .password("qwerty")
                .country(Country.builder()
                        .id(4L)
                        .build())
                .build();
    }

    @Test
    void shouldReturnUserDtoWhenGetValidUserWithNonEmptyFields() {
        User user = getTestUser();

        UserDto userDto = userMapper.toDto(user);

        assertEquals(getTestUserDto(), userDto);
    }

    @Test
    void shouldReturnUserWhenGetValidUserDtoWithNonEmptyFields() {
        UserDto userDto = getTestUserDto();

        User user = userMapper.toUser(userDto);

        assertEquals(getTestUser(), user);
    }

    @Test
    void shouldReturnUserDtoWithNullFieldsWhenGetValidUserWithNullFields() {
        User user = getTestNullUser();

        UserDto userDto = userMapper.toDto(user);

        assertEquals(getTestNullUserDto(), userDto);
    }

    @Test
    void shouldReturnUserWithNullFieldsWhenGetValidUserDtoWithNullFields() {
        UserDto userDto = getTestNullUserDto();

        User user = userMapper.toUser(userDto);

        assertEquals(getTestNullUser(), user);
    }

    private User getTestUser() {
        return User.builder()
                .id(1L)
                .username("user1")
                .email("email1")
                .phone("phone1")
                .aboutMe("aboutMe1")
                .country(Country.builder().id(1L).build())
                .build();
    }

    private UserDto getTestUserDto() {
        return UserDto.builder()
                .id(1L)
                .username("user1")
                .email("email1")
                .phone("phone1")
                .aboutMe("aboutMe1")
                .countryId(1L)
                .build();
    }

    private User getTestNullUser() {
        return User.builder()
                .id(null)
                .username(null)
                .email(null)
                .phone(null)
                .aboutMe(null)
                .country(null)
                .build();
    }

    private UserDto getTestNullUserDto() {
        return UserDto.builder()
                .id(null)
                .username(null)
                .email(null)
                .phone(null)
                .aboutMe(null)
                .countryId(null)
                .build();
    }

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private UserCreateDto userCreateDto;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userCreateDto = UserCreateDto.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .password("qwerty")
                .countryId(4L)
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .aboutMe("about")
                .countryId(4L)
                .city("LA")
                .experience(1)
                .build();

        user = User.builder()
                .id(1L)
                .username("Alex")
                .email("mail@mail.com")
                .phone("123456789")
                .password("qwerty")
                .country(Country.builder()
                        .id(4L)
                        .build())
                .build();
    }

    @Test
    void testCorrectlyMapsFromUserEntity() {
        UserCreateDto resultUserCreateDto = userMapper.toUserCreateDto(user);

        assertEquals(userCreateDto, resultUserCreateDto);
    }

    @Test
    void testToEntityValidUserMappingIgnoringAutoGeneratedId() {
        User resultUser = userMapper.toEntity(userCreateDto);

        assertAll("Пользователь совпадает во всём, кроме id",
                () -> assertEquals(resultUser.getUsername(), userCreateDto.getUsername()),
                () -> assertEquals(resultUser.getEmail(), userCreateDto.getEmail()),
                () -> assertEquals(resultUser.getPhone(), userCreateDto.getPhone()),
                () -> assertEquals(resultUser.getPassword(), userCreateDto.getPassword()),
                () -> assertEquals(resultUser.getCountry().getId(), userCreateDto.getCountryId())
        );
    }

    @Test
    void testCorrectlyMapUserToUserDto() {
        user.setExperience(1);
        user.setCity("LA");
        user.setAboutMe("about");
        UserDto resultUserDto = userMapper.toDto(user);

        assertEquals(userDto, resultUserDto);
    }
}