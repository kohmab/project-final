package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL_PROFILE = ProfileRestController.REST_URL;


    @Autowired
    private ProfileRepository repository;

    @Test
    @WithAnonymousUser
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));

    }

    // TODO COMBINE THIS TWO ↕ ?
    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getGuest() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(GUEST_PROFILE_EMPTY_TO));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateNoBody() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isMethodNotAllowed());
    }


    @ParameterizedTest
    @WithUserDetails(value = USER_MAIL)
    @MethodSource("getInvalidProfileTos")
    void updateWithBadTo(ProfileTo invalidTo) throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        ProfileTo updatedTo = ProfileTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andExpect(status().isNoContent())
                .andDo(print());
        //todo compare with repository data
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUpdated() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andExpect(status().isNoContent());
        PROFILE_MATCHER.assertMatch(
                repository.getExisted(USER_ID),
                ProfileTestData.getUpdated(USER_ID)
        );
    }

    private static Stream<ProfileTo> getInvalidProfileTos() {
        return Stream.of(
                ProfileTestData.getInvalidTo(),
                ProfileTestData.getWithUnknownNotificationTo(),
                ProfileTestData.getWithUnknownContactTo(),
                ProfileTestData.getWithContactHtmlUnsafeTo()
        );
    }


}