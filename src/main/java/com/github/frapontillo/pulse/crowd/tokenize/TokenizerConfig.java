package com.github.frapontillo.pulse.crowd.tokenize;

import com.github.frapontillo.pulse.crowd.data.entity.Message;
import com.github.frapontillo.pulse.spi.IPluginConfig;
import com.github.frapontillo.pulse.spi.PluginConfigHelper;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * The options from this class can be set to configure the tokenizer pre-processing phase, with
 * regards to how to clean the {@link Message} text before tokenizing it.
 *
 * @author Francesco Pontillo
 */
public class TokenizerConfig implements IPluginConfig<TokenizerConfig> {

    /**
     * Tokenize all messages coming from the stream.
     */
    public static final String ALL = "all";

    /**
     * Tokenize the messages with no tokens (property is null).
     */
    public static final String NEW = "new";


    private Integer minChars;
    private boolean urls;
    private boolean hashtags;
    private boolean mentions;
    private boolean numbers;
    private List<String> regexes;
    private String calculate;

    @Override public TokenizerConfig buildFromJsonElement(JsonElement json) {
        return PluginConfigHelper.buildFromJson(json, TokenizerConfig.class);
    }

    public Integer getMinChars() {
        return minChars;
    }

    public void setMinChars(Integer minChars) {
        this.minChars = minChars;
    }

    public boolean isUrls() {
        return urls;
    }

    public void setUrls(boolean urls) {
        this.urls = urls;
    }

    public boolean isHashtags() {
        return hashtags;
    }

    public void setHashtags(boolean hashtags) {
        this.hashtags = hashtags;
    }

    public boolean isMentions() {
        return mentions;
    }

    public void setMentions(boolean mentions) {
        this.mentions = mentions;
    }

    public boolean isNumbers() {
        return numbers;
    }

    public void setNumbers(boolean numbers) {
        this.numbers = numbers;
    }

    public List<String> getRegexes() {
        return regexes;
    }

    public void setRegexes(List<String> regexes) {
        this.regexes = regexes;
    }

    public String getCalculate() {
        return calculate;
    }

    public void setCalculate(String calculate) {
        this.calculate = calculate;
    }
}
