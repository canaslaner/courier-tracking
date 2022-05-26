package tr.com.migros.tracking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tr.com.migros.testutil.MvcUtils;
import tr.com.migros.tracking.controller.dto.LocationDto;
import tr.com.migros.tracking.service.CourierService;

@SpringBootTest
public class CourierControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CourierService courierService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Mockito.doReturn(10d).when(courierService).getCourierTotalDistance(ArgumentMatchers.anyLong());
        Mockito.doReturn(null).when(courierService).updateLocation(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(LocationDto.class));
    }

    @Test
    void testLogLocation_whenRequestIsValid_thenCreateUserSuccessfully() throws Exception {
        // given
        final var request = LocationDto.builder()
                .longitude(29.1244229).latitude(40.9923307)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/courier/1/location")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MvcUtils.createJsonContent(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "1, , 29.1244229, latitude, must not be null",
            "1, 40.9923307, , longitude, must not be null",
    })
    void testLogLocation_whenFieldIsInvalid_thenReturnBadRequest(final String id, final Double latitude, final Double longitude,
                                                                 final String expectedFieldValue,
                                                                 final String expectedMessageValue) throws Exception {
        // given
        final var request = LocationDto.builder()
                .longitude(longitude).latitude(latitude)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/courier/" + id + "/location")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MvcUtils.createJsonContent(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[0].field").value(expectedFieldValue))
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[0].message").value(expectedMessageValue));
    }

    @Test
    void testLogLocation_whenIdIsInvalid_thenReturnBadRequest() throws Exception {
        // given
        final var request = LocationDto.builder().build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/courier/null/location")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MvcUtils.createJsonContent(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetTotalDistance_whenRequestIsValid_thenCreateUserSuccessfully() throws Exception {
        // given && when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/courier/1/distance")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalDistance").value(10));
    }

    @Test
    void testGetTotalDistance_whenIdIsInvalid_thenReturnBadRequest() throws Exception {
        // given && when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/courier/null/distance")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
