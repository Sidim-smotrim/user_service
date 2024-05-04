package school.faang.user_service.service.event;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.filter.EventFilterDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.event.Event;
import school.faang.user_service.exception.DataGettingException;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.EventMapper;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;
import school.faang.user_service.repository.event.EventRepository;
import school.faang.user_service.service.event.filter.EventFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static school.faang.user_service.exception.ExceptionMessage.INAPPROPRIATE_OWNER_SKILLS_EXCEPTION;
import static school.faang.user_service.exception.ExceptionMessage.NO_SUCH_EVENT_EXCEPTION;

@Service
@AllArgsConstructor
@Setter
public class EventService {
    private final EventRepository eventRepository;
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final EventMapper eventMapper;
    private List<EventFilter> filters;


    public EventDto create(EventDto eventDto) {
        checkOwnerSkills(eventDto.getOwnerId(), eventDto.getRelatedSkills());

        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
    }

    private void checkOwnerSkills(Long ownerId, List<SkillDto> relatedSkills) {
        var ownerSkills = new HashSet<>(skillMapper.toDto(skillRepository.findAllByUserId(ownerId)));

        if (!ownerSkills.containsAll(relatedSkills)) {
            throw new DataValidationException(INAPPROPRIATE_OWNER_SKILLS_EXCEPTION.getMessage());
        }
    }

    public EventDto getEvent(long eventId) {
        return eventMapper.toDto(getEventById(eventId)
                .orElseThrow(() -> new DataGettingException(NO_SUCH_EVENT_EXCEPTION.getMessage())));
    }

    public List<EventDto> getEventsByFilter(EventFilterDto filter) {
        var allEvents = eventRepository.findAll().stream();

        return filters.stream()
                .filter(eventFilter -> eventFilter.isApplicable(filter))
                .flatMap(eventFilter -> eventFilter.apply(allEvents, filter))
                .distinct()
                .map(eventMapper::toDto)
                .toList();
    }

    public void deleteEvent(long eventId) {
        eventRepository.delete(getEventById(eventId)
                .orElseThrow(() -> new DataGettingException(NO_SUCH_EVENT_EXCEPTION.getMessage())));
    }

    private Optional<Event> getEventById(long eventId) {
        return eventRepository.findById(eventId);
    }
}
