package com.fitness.app.controller;

import com.fitness.app.entity.Rating;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserRatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * This controller is used for rating vendor, user and gym registered in the
     * application
     * 
     * @param rating - Actual Rating
     * @return - Rating
     */
    @ApiOperation(value = "Rating Vendor", notes = "Users can rate vendors")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Vendor Logged In", response = RatingRequestModel.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Validated
    public Rating rateVendor(@Valid @RequestBody RatingRequestModel rating) {
        return ratingService.ratingService(rating); // Rating vendor, user or gym
    }

    /**
     * This controller is used to fetch the rating of the gym from gym id
     * 
     * @param gymId - Id of the gym
     * @return - Rating of the gym
     */
    @ApiOperation(value = "Fetch Rating", notes = "Fetching the rating of user, vendor or gym")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Rating Fetched", response = Double.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/get-rating/{gymId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Double getRating(@PathVariable String gymId) {
        return ratingService.getRating(gymId); // Fetching the rating of the gym by gymId
    }

    /**
     * This controller is used to fetch the rating of the user from his email
     * 
     * @param email - Email id of the user
     * @return - Rating of the user
     */
    @ApiOperation(value = "Get rating by email", notes = "Fetching rating of person via email")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Rating Fetched", response = Double.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/get-rating-person/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Double getRatingOfPerson(@PathVariable String email) {
        return ratingService.getRatingOfPerson(email); // Fetching the rating of the user by email id
    }

}
