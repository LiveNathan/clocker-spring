package dev.nathanlively.clocker_spring;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@Tag("mvc")
class HomeMvcTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ClockRepository clockRepository;
    @MockBean
    ClockEventService clockEventService;
    @MockBean
    EclipseStoreProperties eclipseStoreProperties;
    @MockBean
    EmbeddedStorageFoundationFactory embeddedStorageFoundationFactory;

    @Test
    public void getRequestToIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void postRequestToClockIn() throws Exception {
        mockMvc.perform(post("/clockIn"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void postRequestToClockOut() throws Exception {
        mockMvc.perform(post("/clockOut"))
                .andExpect(status().is3xxRedirection());
    }
}