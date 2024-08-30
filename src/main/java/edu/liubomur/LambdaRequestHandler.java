package edu.liubomur;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;
import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class LambdaRequestHandler implements RequestHandler<APIGatewayV2HTTPEvent, Void> {

    LambdaLogger logger;
    Gson gson = new Gson();

    @Override
    public Void handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        logger = context.getLogger();

        logger.log("Event body: " + event.getBody(), LogLevel.INFO);

        String botApiToken = System.getenv("bot_api_token");

        TelegramBot bot = new TelegramBot(botApiToken);

        Update lambdaUpdate = gson.fromJson(event.getBody(), Update.class);

        SendMessage sendMessageRequest = new SendMessage(lambdaUpdate.message().chat().id(), lambdaUpdate.message().text())
                .parseMode(ParseMode.HTML)
                .disableNotification(false)
                .replyToMessageId(lambdaUpdate.message().messageId())
                .replyMarkup(new ForceReply());

        SendResponse messageResponse = bot.execute(sendMessageRequest);

        logger.log("Telegram response: " + gson.toJson(messageResponse), LogLevel.INFO);

        return null;
    }
}
