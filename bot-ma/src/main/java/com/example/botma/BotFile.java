package com.example.botma;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;

import com.example.botma.dto.VacancyDto;
import com.example.botma.service.VacancyService;

@Component
public class BotFile extends TelegramLongPollingBot{
    
    public BotFile(){
        super(System.getenv("bot_key"));
    }

    @Autowired
    VacancyService vacancyService;

    private HashMap<Long, String> lastAction = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "lion777_ma_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.getMessage() != null){
            handleStartMenuCommands(update);
        } 

        if(update.getCallbackQuery() != null){
            showVacancies(update);
        }
        
    }

    private void showVacancies(Update update) {

        String callBackData = update.getCallbackQuery().getData();
        List<VacancyDto> result = new ArrayList<>();

        switch (callBackData) {

            case "start":
                handleStartMenuCommands(update);
                break;

            case "junior":
                result = vacancyService.getVacancyByLvl(callBackData);
                handleVacancyMenu(result, update);
                break;
        
            case "middle":
                result = vacancyService.getVacancyByLvl(callBackData);
                handleVacancyMenu(result, update);
                break;

            case "senior":
                result = vacancyService.getVacancyByLvl(callBackData);
                handleVacancyMenu(result, update);
                break;

            default: 
                handleVacancyDescription(callBackData, update);
                break;
        }
    }


    private void handleStartMenuCommands(Update update){ 
        SendMessage sendMessage = new SendMessage();
        if(update.getMessage() != null) {
            sendMessage.setChatId(update.getMessage().getChatId());
        } else {
            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        sendMessage.setText("Choose level");
        sendMessage.setReplyMarkup(getStartMenu());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard getStartMenu() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton junior = new InlineKeyboardButton();
        junior.setText("Junior");
        junior.setCallbackData("junior");
        buttons.add(junior);

        InlineKeyboardButton middle = new InlineKeyboardButton();
        middle.setText("Middle");
        middle.setCallbackData("middle");
        buttons.add(middle);

        InlineKeyboardButton senior = new InlineKeyboardButton();
        senior.setText("Senior");
        senior.setCallbackData("senior");
        buttons.add(senior);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(buttons));
        return keyboard;
    }

    private void handleVacancyMenu(List<VacancyDto> result, Update update){
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String level = update.getCallbackQuery().getData();
        lastAction.put(id, level);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Available vacancies is:");
        sendMessage.setReplyMarkup(getVacancyMenu(result));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard getVacancyMenu(List<VacancyDto> result) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (VacancyDto vacancy : result) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(vacancy.getName());
            button.setCallbackData(vacancy.getId());
            buttons.add(button);
        }
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("start");
        buttons.add(back);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(buttons));
        return keyboard;
    }

    private void handleVacancyDescription(String vacancyId, Update update){
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText(vacancyService.getVacancyView(vacancyId));
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setReplyMarkup(getVacancyDescription(vacancyService.getVacancyById(vacancyId).getLink(), userId));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup getVacancyDescription(String link, Long id) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        
        InlineKeyboardButton apply = new InlineKeyboardButton();
        apply.setText("Apply");
        apply.setUrl(link);
        buttons.add(apply);

        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back to vacancies");
        back.setCallbackData(lastAction.get(id));
        buttons.add(back);

        InlineKeyboardButton start = new InlineKeyboardButton();
        start.setText("To start");
        start.setCallbackData("start");
        buttons.add(start);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(buttons));
        return keyboard;
    }


}
