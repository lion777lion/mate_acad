package com.example.botma;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotFile extends TelegramLongPollingBot{
    
    public BotFile(){
        super(System.getenv("bot_key"));
    }

    Vacancy vacancy = new Vacancy();
    List<Vacancy> vacancies = vacancy.generateData();
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
        List<Vacancy> result = new ArrayList<>();

        switch (callBackData) {
            case "junior":
                result = vacancies.stream()
                .filter(item -> item.level.equals("junior"))
                .collect(Collectors.toList());
                handleVacancyMenu(result, update);
                break;
        
            case "middle":
                result = vacancies.stream()
                .filter(item -> item.level.equals("middle"))
                .collect(Collectors.toList());
                handleVacancyMenu(result, update);
                break;

            case "start":
                handleStartMenuCommands(update);
                break;

            case "senior":
                result = vacancies.stream()
                .filter(item -> item.level.equals("senior"))
                .collect(Collectors.toList());
                handleVacancyMenu(result, update);
                break;

            default: 
                Vacancy resultVacancy = new Vacancy();
                
                for (Vacancy vacancy : vacancies) {
                    if(vacancy.id.equals(callBackData)){
                        resultVacancy = vacancy;
                    }
                }
                handleVacancyDescription(resultVacancy, update);
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

    private void handleVacancyMenu(List<Vacancy> result, Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Available vacancies is:");
        sendMessage.setReplyMarkup(getVacancyMenu(result));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard getVacancyMenu(List<Vacancy> result) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Vacancy vacancy : result) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(vacancy.name);
            button.setCallbackData(vacancy.id);
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

    private void handleVacancyDescription(Vacancy vacancy, Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText(vacancy.description + ". Vacancy ID is " + vacancy.id);
        sendMessage.setReplyMarkup(getVacancyDescription(vacancy));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup getVacancyDescription(Vacancy vacancy) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton apply = new InlineKeyboardButton();
        apply.setText("Apply");
        
        apply.setCallbackData(vacancy.level);
        buttons.add(apply);

        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData(vacancy.level);
        buttons.add(back);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(buttons));
        return keyboard;
    }


}
