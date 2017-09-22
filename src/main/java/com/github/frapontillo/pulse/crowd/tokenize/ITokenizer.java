package com.github.frapontillo.pulse.crowd.tokenize;

import com.github.frapontillo.pulse.crowd.data.entity.Message;
import com.github.frapontillo.pulse.crowd.data.entity.Token;
import com.github.frapontillo.pulse.rx.PulseSubscriber;
import com.github.frapontillo.pulse.spi.IPlugin;
import rx.Observable;
import rx.Subscriber;

import java.util.List;

/**
 * Generic tokenizer, optionally cleans the message text before starting the tokenization process.
 * The pre-tokenization options are set via a {@link TokenizerConfig} instance.
 *
 * @author Francesco Pontillo
 */
public abstract class ITokenizer extends IPlugin<Message, Message, TokenizerConfig> {
    @Override public TokenizerConfig getNewParameter() {
        return new TokenizerConfig();
    }

    @Override
    protected Observable.Operator<Message, Message> getOperator(TokenizerConfig parameters) {
        return new Observable.Operator<Message, Message>() {
            @Override public Subscriber<? super Message> call(Subscriber<? super Message> subscriber) {
                return new PulseSubscriber<Message>(subscriber) {
                    TextCleaner cleaner = new TextCleaner(parameters);
                    @Override public void onStart() {
                        super.onStart();
                    }

                    @Override public void onNext(Message message) {
                        reportElementAsStarted(message.getId());
                        message = tokenize(message, cleaner);
                        reportElementAsEnded(message.getId());
                        subscriber.onNext(message);
                    }

                    @Override public void onCompleted() {
                        reportPluginAsCompleted();
                        super.onCompleted();
                    }

                    @Override public void onError(Throwable e) {
                        reportPluginAsErrored();
                        super.onError(e);
                    }
                };
            }
        };
    }

    /**
     * Retrieve all the {@link Token}s of the {@link Message}, then sets them to the {@link Message}
     * itself.
     *
     * @param message The {@link Message} to process.
     * @param cleaner The {@link TextCleaner} that will be used to cleanup the text.
     *
     * @return the {@link Message} with newly added {@link Token}s.
     */
    private Message tokenize(Message message, TextCleaner cleaner) {
        // save the original text and clean it up
        String originalText = message.getText();
        message.setText(cleaner.clean(originalText));
        message.setTokens(getTokens(message));
        // reset the original text
        message.setText(originalText);
        return message;
    }

    /**
     * Tokenize the input {@link Message} into chunks represented by {@link Token}s.
     * No extra information will be stored into the retrieved elements except for the {@link
     * Token#text}.
     *
     * @param message The {@link Message} to process.
     *
     * @return a {@link List} of simple {@link Token}s.
     */
    public abstract List<Token> getTokens(Message message);
}
