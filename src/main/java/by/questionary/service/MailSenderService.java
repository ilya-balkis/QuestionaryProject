package by.questionary.service;

import by.questionary.domain.User;

import java.util.concurrent.CompletableFuture;

public interface MailSenderService {

    CompletableFuture<Boolean> sendActivationMessage(User user);

}
