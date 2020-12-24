package by.questionary.service;

import by.questionary.domain.User;

public interface MessageCreatorService {

    String createEmailMessage(User user);

}
