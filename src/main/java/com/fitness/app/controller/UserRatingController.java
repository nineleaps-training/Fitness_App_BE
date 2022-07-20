package com.fitness.app.controller;


import com.fitness.app.dto.RatingModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.service.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> rateVendor(@RequestBody RatingModel rating) {
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
    public ApiResponse getRating(@PathVariable String gymId) throws DataNotFoundException {
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
    public ApiResponse getRatingOfPerson(@PathVariable String email) {

        return new ApiResponse(HttpStatus.OK, ratingServiceImpl.getRatingOfPerson(email));

    }

}


