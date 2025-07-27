package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Process the OAuth2User
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // Extract user details from OAuth2User
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String googleId = (String) attributes.get("sub"); // Google's user ID
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        // Check if user exists in ClientUser repository
        Optional<ClientUser> clientUserOptional = clientUserRepository.findByEmail(email);

        // Check if user exists in RestaurantUser repository
        Optional<RestaurantUser> restaurantUserOptional = restaurantUserRepository.findByEmail(email);

        // Determine user type based on request parameters or other logic
        String userType = determineUserType(userRequest);

        if (userType.equals("client")) {
            // Handle ClientUser authentication
            if (clientUserOptional.isPresent()) {
                // User exists, update Google ID if needed
                ClientUser existingUser = clientUserOptional.get();
                if (existingUser.getGoogleId() == null) {
                    existingUser.setGoogleId(googleId);
                    existingUser.setIsGoogleUser(true);
                    clientUserRepository.save(existingUser);
                }
            } else if (restaurantUserOptional.isEmpty()) {
                // Create new ClientUser only if email doesn't exist in either repository
                ClientUser newUser = new ClientUser(
                    firstName,
                    lastName,
                    email,
                    googleId,
                    true
                );
                clientUserRepository.save(newUser);
            }
        } else if (userType.equals("restaurant")) {
            // Handle RestaurantUser authentication
            if (restaurantUserOptional.isPresent()) {
                // User exists, update Google ID if needed
                RestaurantUser existingUser = restaurantUserOptional.get();
                if (existingUser.getGoogleId() == null) {
                    existingUser.setGoogleId(googleId);
                    existingUser.setIsGoogleUser(true);
                    restaurantUserRepository.save(existingUser);
                }
            } else if (clientUserOptional.isEmpty()) {
                // Create new RestaurantUser only if email doesn't exist in either repository
                // For restaurant users, we need a restaurant name
                String restaurantName = firstName + "'s Restaurant"; // Default name, can be changed later

                RestaurantUser newUser = new RestaurantUser(
                    restaurantName,
                    email,
                    googleId,
                    true,
                    null, // latitude
                    null  // longitude
                );
                restaurantUserRepository.save(newUser);
            }
        }

        return oAuth2User;
    }

    // Helper method to determine user type from request
    private String determineUserType(OAuth2UserRequest userRequest) {
        // Default to client user
        String userType = "client";

        // Try to determine user type from request parameters
        // This could be based on the redirect URI, additional parameters, or other logic
        String redirectUri = userRequest.getClientRegistration().getRedirectUri();
        if (redirectUri != null && redirectUri.contains("restaurant")) {
            userType = "restaurant";
        }

        return userType;
    }
}
