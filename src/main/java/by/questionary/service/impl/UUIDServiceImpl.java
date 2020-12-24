package by.questionary.service.impl;

import by.questionary.service.UUIDService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDServiceImpl implements UUIDService {

    public String createUUID() {
        return UUID.randomUUID().toString();
    }

}
