package com.mirco.notes.auth.services.jwt;

import com.mirco.notes.auth.model.entities.SystemUser;

import java.util.Date;

public interface IJwtService {

    /**
     * Generates a JWT token for the given user.
     *
     * @param systemUser The system user for whom the token is generated.
     * @return A JWT token as a String.
     */
    String generateToken(final SystemUser systemUser);

    /**
     * Extracts the username from the given JWT token.
     *
     * @param jwt The JWT token from which to extract the username.
     * @return The username as a String.
     */
    String extractUsername(String jwt);

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param jwt The JWT token from which to extract the expiration date.
     * @return The expiration date.
     */
    Date extractExpirationDate(String jwt);

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token The JWT token to check.
     * @return True if the token is expired, false otherwise.
     */
    Boolean isTokenExpired(String token);

    /**
     * Validates that the given JWT token is valid.
     *
     * @param jwt The JWT token to validate.
     * @param systemUser The system user to validate against.
     * @return True if the token is valid for the user, false otherwise.
     */
    Boolean isTokenValid(String jwt, SystemUser systemUser);

}