package domain.factories;

import delilah.domain.exceptions.groupEvent.InvalidGroupEventSizeException;
import delilah.domain.factories.GroupEventFactory;
import delilah.domain.models.groupEvent.Activity;
import delilah.domain.models.groupEvent.GroupEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;

public class GroupEventFactoryTest {

    private static final int GROUP_SIZE_TOO_SMALL = 0;
    private static final int GROUP_SIZE_TOO_BIG = 25;
    private static final int VALID_GROUP_SIZE = 6;
    private static final Activity ACTIVITY = new Activity("Last Wish", "lw");
    private static final String ID = "1234566780";
    private static final String DESCRIPTION = "hello there";
    private static final Instant START_TIME = Instant.ofEpochMilli(0);

    private GroupEventFactory groupEventFactory;

    private Clock clockMock = Mockito.mock(Clock.class);

    @BeforeEach
    public void setup() {
        Mockito.reset(clockMock);
        Mockito.when(clockMock.instant()).thenReturn(START_TIME);

        groupEventFactory = new GroupEventFactory(clockMock);
    }

    @Test
    public void givenValidParameters_whenCreatingGroup_thenGroupIsCreated() {

        GroupEvent groupEvent = groupEventFactory.createEventGroup(ID, ID, ACTIVITY, DESCRIPTION, VALID_GROUP_SIZE, START_TIME);

        Assertions.assertNotNull(groupEvent);
    }

    @Test
    public void givenGroupSizeTooSmall_whenCreatingGroup_thenThrowsInvalidGroupEventSizeException() {

        Assertions.assertThrows(InvalidGroupEventSizeException.class, () ->
                groupEventFactory.createEventGroup(ID, ID, ACTIVITY, DESCRIPTION, GROUP_SIZE_TOO_SMALL, START_TIME));
    }

    @Test
    public void givenGroupSizeTooBig_whenCreatingGroup_thenThrowsInvalidGroupEventSizeException() {

        Assertions.assertThrows(InvalidGroupEventSizeException.class, () ->
                groupEventFactory.createEventGroup(ID, ID, ACTIVITY, DESCRIPTION, GROUP_SIZE_TOO_BIG, START_TIME));
    }
}
