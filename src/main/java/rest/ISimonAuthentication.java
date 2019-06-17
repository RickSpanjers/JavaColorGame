package rest;

import dto.restDTO.AuthenticationDTO;
import dto.restDTO.RegistrationDTO;

public interface ISimonAuthentication {
    String loginUsername(AuthenticationDTO loginRequest);

    String loginMail(AuthenticationDTO loginRequest);

   String register(RegistrationDTO registrationRequest);
}
