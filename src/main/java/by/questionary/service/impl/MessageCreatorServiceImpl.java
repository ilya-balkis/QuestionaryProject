package by.questionary.service.impl;

import by.questionary.domain.User;
import by.questionary.service.MessageCreatorService;
import org.springframework.stereotype.Service;

@Service
public class MessageCreatorServiceImpl implements MessageCreatorService {

    public String createEmailMessage(User user) {

        return String.format(
                "Hello, %s! "
                        + "\n"
                        + "Welcome to Questionary. Please, visit our link: http://localhost:8080/activate/%s",
                user.getName(),
                user.getActivationCode()
        );
    }
}
