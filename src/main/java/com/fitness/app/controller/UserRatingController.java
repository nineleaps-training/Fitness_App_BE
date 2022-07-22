package com.fitness.app.controller;


import com.fitness.app.dto.requestDtos.RatingModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.RatingServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

/**
 * The type User rating controller.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rating")
public class UserRatingController {
    private final RatingServiceImpl ratingServiceImpl;

    /**
     * Rate vendor response entity.
     *
     * @param rating the rating
     * @return the response entity
     */
//Rating Controller for vendor, user and gym
    @PostMapping("/rate-something")
    @ApiOperation(value = "Rate Someone ", notes = "Rate user of Fitness center.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response =String.class),
    })
    @Validated
    public ResponseEntity<Boolean> rateVendor(@RequestBody @Valid RatingModel rating) {
        return new ResponseEntity<>(ratingServiceImpl.ratingService(rating), HttpStatus.OK);
    }

    /**
     * Gets rating.
     *
     * @param gymId the gym id
     * @return the rating
     * @throws DataNotFoundException the data not found exception
     */
//Fetching the rating of the gym by gymId
    @GetMapping("/get-rating-of-fitness/{gymId}")
    @ApiOperation(value = "Get Rating ", notes = "Get rating of a fitness center.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Rating value", response =ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response =DataNotFoundException.class)
    })
    @Validated
    public ApiResponse getRating(@PathVariable @Valid String gymId) throws DataNotFoundException {
        try {
            return new ApiResponse(HttpStatus.OK, ratingServiceImpl.getRating(gymId));
        } catch (Exception e) {
            log.error("UserRatingController ::-> getRating :: Exception found due to: {}", e.getMessage());
            throw new DataNotFoundException("No fitness center with Exist:");
        }
    }

    /**
     * Gets rating of person.
     *
     * @param email the email
     * @return the rating of person
     */
//Fetching the rating of the user by email id
    @GetMapping("/get-rating-person/{email}")
    @ApiOperation(value = "Get Rating ", notes = "Get rating of a person.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Rating Value", response =ApiResponse.class),
            @io.swagger.annotations.ApiResponse(code = 401, message = "Not found or bad request ", response =DataNotFoundException.class)
    })
    @Validated
    public ApiResponse getRatingOfPerson(@PathVariable @Valid @Email String email) {

        return new ApiResponse(HttpStatus.OK, ratingServiceImpl.getRatingOfPerson(email));

    }

}


